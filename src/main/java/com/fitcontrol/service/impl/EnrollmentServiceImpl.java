package com.fitcontrol.service.impl;

import com.fitcontrol.dto.ActivityDTO;
import com.fitcontrol.dto.EnrollmentDTO;
import com.fitcontrol.dto.MemberDTO;
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

    public EnrollmentServiceImpl(ActivityRepository activityRepository,
                                 MemberRepository memberRepository) {
        this.activityRepository = activityRepository;
        this.memberRepository = memberRepository;
    }

    // =========================
    // ENROLL USER IN ACTIVITY
    // =========================
    @Override
    @Transactional
    public EnrollmentDTO enroll(Long activityId, Long userId) {

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Activity not found with id: " + activityId));

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));

        if (Boolean.FALSE.equals(member.getIsActive())) {
            throw new BusinessRuleException(
                    "User with id " + userId + " is inactive and cannot enroll", 403);
        }

        // VALIDACIÓN COMENTADA: Permite inscribir en actividades pasadas
        // if (activity.getStartDate() == null ||
        //         !activity.getStartDate().isAfter(LocalDateTime.now())) {
        //     throw new BusinessRuleException(
        //             "Only future activities allow enrollment", 409);
        // }

        if (member.getActivities().contains(activity)) {
            throw new BusinessRuleException(
                    "User is already enrolled in this activity", 409);
        }

        long futureCount = activityRepository
                .countFutureActivitiesForMember(userId, LocalDateTime.now());

        if (futureCount >= 3) {
            throw new BusinessRuleException(
                    "User cannot have more than 3 future activities enrolled", 409);
        }

        member.getActivities().add(activity);
        memberRepository.save(member);

        return toEnrollmentDTO(activity, member);
    }

    // =========================
    // UNENROLL USER
    // =========================
    @Override
    @Transactional
    public void unenroll(Long activityId, Long userId) {

        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Activity not found with id: " + activityId));

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));

        if (!member.getActivities().contains(activity)) {
            throw new BusinessRuleException(
                    "User is not enrolled in this activity", 409);
        }

        member.getActivities().remove(activity);
        memberRepository.save(member);
    }

    // =========================
    // ACTIVITIES BY USER
    // =========================
    @Override
    @Transactional(readOnly = true)
    public List<ActivityDTO> findActivitiesByUser(Long userId) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + userId));

        return member.getActivities().stream()
                .map(this::toActivityDTO)
                .collect(Collectors.toList());
    }

    // =========================
    // MEMBERS BY ACTIVITY (FIX PRINCIPAL)
    // =========================
    @Override
    @Transactional(readOnly = true)
    public List<MemberDTO> findMembersByActivity(Long activityId) {

        // ✅ FIX IMPORTANTE: ya NO usamos activity.getMembers()
        List<Member> members = memberRepository.findMembersByActivityId(activityId);

        return members.stream()
                .map(this::toMemberDTO)
                .collect(Collectors.toList());
    }

    // =========================
    // DTO MAPPERS
    // =========================

    private EnrollmentDTO toEnrollmentDTO(Activity a, Member m) {
        return new EnrollmentDTO(
                a.getId(),
                a.getName(),
                a.getSchedule(),
                a.getStartDate(),
                m.getId(),
                m.getName(),
                a.getTeacher() != null ? a.getTeacher().getId() : null,
                a.getTeacher() != null ? a.getTeacher().getName() : null
        );
    }

    private ActivityDTO toActivityDTO(Activity a) {
        return new ActivityDTO(
                a.getId(),
                a.getTitle(),
                a.getName(),
                a.getDescription(),
                a.getPrice(),
                a.getImageUrl(),
                a.getSchedule(),
                a.getCapacity(),
                a.getIsActive(),
                a.getStartDate(),
                a.getTeacher() != null ? a.getTeacher().getId() : null,
                a.getTeacher() != null ? a.getTeacher().getName() : null
        );
    }

    private MemberDTO toMemberDTO(Member m) {
        return new MemberDTO(
                m.getId(),
                m.getName(),
                m.getLastName(),
                m.getDni(),
                m.getRegistrationYear(),
                m.getIsActive(),
                m.getImageUrl(),
                m.getMembershipType()
        );
    }

    @Override
    public ActivityDTO enrollUserInActivity(Long userId, Long activityId) {
        EnrollmentDTO enrollment = enroll(activityId, userId);
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (activity == null) {
            return null;
        }
        return toActivityDTO(activity);
    }
}