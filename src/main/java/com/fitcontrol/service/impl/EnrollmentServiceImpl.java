package com.fitcontrol.service.impl;

import com.fitcontrol.dto.activity.ActivityDTOResponse;
import com.fitcontrol.dto.activity.ActivityMapper;
import com.fitcontrol.dto.enrollment.EnrollmentDTOResponse;
import com.fitcontrol.dto.enrollment.EnrollmentMapper;
import com.fitcontrol.dto.member.MemberDTOResponse;
import com.fitcontrol.dto.member.MemberMapper;
import com.fitcontrol.exception.BusinessRuleException;
import com.fitcontrol.exception.ResourceNotFoundException;
import com.fitcontrol.model.Activity;
import com.fitcontrol.model.Member;
import com.fitcontrol.repository.ActivityRepository;
import com.fitcontrol.repository.MemberRepository;
import com.fitcontrol.service.EnrollmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final ActivityRepository activityRepository;
    private final MemberRepository memberRepository;

    public EnrollmentServiceImpl(ActivityRepository activityRepository, MemberRepository memberRepository) {
        this.activityRepository = activityRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public EnrollmentDTOResponse enroll(Long activityId, Long userId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado ninguna actividad con id: " + activityId));

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado ningún socio con id: " + userId));

        if (Boolean.FALSE.equals(member.getIsActive())) {
            throw new BusinessRuleException(
                "El socio " + member.getName() + " " + member.getLastName() + " no está activo. " +
                "Solo los socios con la cuota al día pueden inscribirse en actividades.", 403);
        }

        if (activity.getStartDate() == null || !activity.getStartDate().isAfter(LocalDateTime.now())) {
            throw new BusinessRuleException(
                "La actividad \"" + activity.getName() + "\" ya ha comenzado o no tiene fecha asignada. " +
                "Solo se puede inscribir en actividades futuras.", 409);
        }

        if (member.getActivities().contains(activity)) {
            throw new BusinessRuleException(
                "El socio " + member.getName() + " " + member.getLastName() +
                " ya está inscrito en la actividad \"" + activity.getName() + "\".", 409);
        }

        long futureCount = activityRepository.countFutureActivitiesForMember(userId, LocalDateTime.now());
        if (futureCount >= 3) {
            throw new BusinessRuleException(
                "El socio " + member.getName() + " " + member.getLastName() +
                " ya tiene 3 actividades futuras contratadas. Debe cancelar alguna antes de poder inscribirse en una nueva.", 409);
        }

        member.getActivities().add(activity);
        memberRepository.save(member);

        return EnrollmentMapper.entity2DTO(activity, member);
    }

    @Override
    @Transactional
    public void unenroll(Long activityId, Long userId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado ninguna actividad con id: " + activityId));

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado ningún socio con id: " + userId));

        if (!member.getActivities().contains(activity)) {
            throw new BusinessRuleException(
                "El socio " + member.getName() + " " + member.getLastName() +
                " no figura inscrito en la actividad \"" + activity.getName() + "\".", 409);
        }

        member.getActivities().remove(activity);
        memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActivityDTOResponse> findActivitiesByUser(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado ningún socio con id: " + userId));

        return member.getActivities().stream()
                .map(ActivityMapper::entity2DTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberDTOResponse> findMembersByActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("No se ha encontrado ninguna actividad con id: " + activityId));

        return activity.getMembers().stream()
                .map(MemberMapper::entity2DTO)
                .collect(Collectors.toList());
    }
}
