package com.fitcontrol.service.impl;

import com.fitcontrol.dto.EnrollmentDTO;
import com.fitcontrol.exception.BusinessRuleException;
import com.fitcontrol.exception.DuplicateResourceException;
import com.fitcontrol.exception.ForbiddenOperationException;
import com.fitcontrol.exception.ResourceNotFoundException;
import com.fitcontrol.model.Activity;
import com.fitcontrol.model.Enrollment;
import com.fitcontrol.model.Member;
import com.fitcontrol.repository.ActivityRepository;
import com.fitcontrol.repository.EnrollmentRepository;
import com.fitcontrol.repository.MemberRepository;
import com.fitcontrol.service.EnrollmentService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final MemberRepository memberRepository;
    private final ActivityRepository activityRepository;

    public EnrollmentServiceImpl(
        EnrollmentRepository enrollmentRepository,
        MemberRepository memberRepository,
        ActivityRepository activityRepository
    ) {
        this.enrollmentRepository = enrollmentRepository;
        this.memberRepository = memberRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public EnrollmentDTO createEnrollment(EnrollmentDTO enrollmentDTO) {
        validateStatus(enrollmentDTO.getStatus());

        if (enrollmentRepository.existsByMemberIdAndActivityId(enrollmentDTO.getMemberId(), enrollmentDTO.getActivityId())) {
            throw new DuplicateResourceException("El miembro ya esta inscrito en esta actividad");
        }

        Member member = findMember(enrollmentDTO.getMemberId());
        Activity activity = findActivity(enrollmentDTO.getActivityId());

        if (Boolean.FALSE.equals(member.getIsActive())) {
            throw new ForbiddenOperationException("El usuario no ha pagado su cuota anual");
        }

        if (Boolean.FALSE.equals(activity.getIsActive())) {
            throw new BusinessRuleException("No se puede inscribir en una actividad inactiva");
        }

        if (!activity.getActivityDate().isAfter(LocalDateTime.now())) {
            throw new BusinessRuleException("Solo se permiten inscripciones en actividades futuras");
        }

        long futureEnrollments = enrollmentRepository.countFutureActiveEnrollmentsByMember(member.getId(), LocalDateTime.now());
        if (futureEnrollments >= 3) {
            throw new BusinessRuleException("Limite de 3 actividades futuras alcanzado");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setMember(member);
        enrollment.setActivity(activity);
        enrollment.setEnrollmentDate(
            enrollmentDTO.getEnrollmentDate() == null ? LocalDate.now() : enrollmentDTO.getEnrollmentDate()
        );
        enrollment.setStatus(enrollmentDTO.getStatus() == null ? "ACTIVE" : enrollmentDTO.getStatus());

        Enrollment saved = enrollmentRepository.save(enrollment);
        return toDto(saved);
    }

    @Override
    public EnrollmentDTO getEnrollmentById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Inscripcion no encontrada con id " + id));

        return toDto(enrollment);
    }

    @Override
    public List<EnrollmentDTO> getAllEnrollments() {
        return enrollmentRepository.findAllWithDetails().stream().map(this::toDto).toList();
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentsByMember(Long memberId) {
        findMember(memberId);
        return enrollmentRepository.findByMemberId(memberId).stream().map(this::toDto).toList();
    }

    @Override
    public List<EnrollmentDTO> getEnrollmentsByActivity(Long activityId) {
        findActivity(activityId);
        return enrollmentRepository.findByActivityId(activityId).stream().map(this::toDto).toList();
    }

    @Override
    public List<EnrollmentDTO> getFutureEnrollmentsByMember(Long memberId) {
        findMember(memberId);
        return enrollmentRepository.findByMemberId(memberId)
            .stream()
            .filter(enrollment -> enrollment.getActivity().getActivityDate().isAfter(LocalDateTime.now()))
            .map(this::toDto)
            .toList();
    }

    @Override
    public EnrollmentDTO updateEnrollment(Long id, EnrollmentDTO enrollmentDTO) {
        validateStatus(enrollmentDTO.getStatus());

        Enrollment existing = enrollmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Inscripcion no encontrada con id " + id));

        Member member = findMember(enrollmentDTO.getMemberId());
        Activity activity = findActivity(enrollmentDTO.getActivityId());

        boolean relationChanged = !existing.getMember().getId().equals(member.getId())
            || !existing.getActivity().getId().equals(activity.getId());

        if (relationChanged && enrollmentRepository.existsByMemberIdAndActivityId(member.getId(), activity.getId())) {
            throw new DuplicateResourceException("El miembro ya esta inscrito en esta actividad");
        }

        existing.setMember(member);
        existing.setActivity(activity);
        existing.setEnrollmentDate(
            enrollmentDTO.getEnrollmentDate() == null ? existing.getEnrollmentDate() : enrollmentDTO.getEnrollmentDate()
        );
        existing.setStatus(enrollmentDTO.getStatus() == null ? existing.getStatus() : enrollmentDTO.getStatus());

        Enrollment saved = enrollmentRepository.save(existing);
        return toDto(saved);
    }

    @Override
    public void deleteEnrollment(Long id) {
        Enrollment existing = enrollmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Inscripcion no encontrada con id " + id));

        enrollmentRepository.delete(existing);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new ResourceNotFoundException("Miembro no encontrado con id " + memberId));
    }

    private Activity findActivity(Long activityId) {
        return activityRepository.findById(activityId)
            .orElseThrow(() -> new ResourceNotFoundException("Actividad no encontrada con id " + activityId));
    }

    private void validateStatus(String status) {
        if (status == null) {
            return;
        }

        if (!status.equals("ACTIVE") && !status.equals("CANCELLED") && !status.equals("COMPLETED")) {
            throw new BusinessRuleException("Estado invalido. Valores permitidos: ACTIVE, CANCELLED, COMPLETED");
        }
    }

    private EnrollmentDTO toDto(Enrollment enrollment) {
        return new EnrollmentDTO(
            enrollment.getId(),
            enrollment.getMember().getId(),
            enrollment.getMember().getName() + " " + enrollment.getMember().getLastName(),
            enrollment.getActivity().getId(),
            enrollment.getActivity().getTitle(),
            enrollment.getActivity().getActivityDate(),
            enrollment.getActivity().getTeacher().getId(),
            enrollment.getActivity().getTeacher().getName() + " " + enrollment.getActivity().getTeacher().getLastName(),
            enrollment.getEnrollmentDate(),
            enrollment.getStatus()
        );
    }
}
