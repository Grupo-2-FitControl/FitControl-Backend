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

    public EnrollmentServiceImpl(ActivityRepository activityRepository, MemberRepository memberRepository) {
        this.activityRepository = activityRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public EnrollmentDTO enroll(Long activityId, Long memberId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + activityId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        if (!member.getIsActive())
            throw new BusinessRuleException("Member with id " + memberId + " is inactive and cannot enroll", 403);

        if (member.getActivities().contains(activity))
            throw new BusinessRuleException("Member is already enrolled in this activity", 409);

        long futureCount = activityRepository.countFutureActivitiesForMember(memberId, LocalDateTime.now());
        if (futureCount >= 3)
            throw new BusinessRuleException("Member cannot have more than 3 future activities enrolled", 409);

        member.getActivities().add(activity);
        memberRepository.save(member);

        return toEnrollmentDTO(activity, member);
    }

    @Override
    @Transactional
    public void unenroll(Long activityId, Long memberId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + activityId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        if (!member.getActivities().contains(activity))
            throw new BusinessRuleException("Member is not enrolled in this activity", 409);

        member.getActivities().remove(activity);
        memberRepository.save(member);
    }

    @Override
    public List<ActivityDTO> findActivitiesByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        return member.getActivities().stream()
                .map(this::toActivityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberDTO> findMembersByActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Activity not found with id: " + activityId));

        return activity.getMembers().stream()
                .map(this::toMemberDTO)
                .collect(Collectors.toList());
    }

    private EnrollmentDTO toEnrollmentDTO(Activity a, Member m) {
        return new EnrollmentDTO(
                a.getId(),
                a.getName(),
                a.getSchedule(),
                a.getStartDate(),
                m.getId(),
                m.getName(),
                a.getTeacher().getId(),
                a.getTeacher().getName()
        );
    }

    private ActivityDTO toActivityDTO(Activity a) {
        return new ActivityDTO(
                a.getId(),
                a.getName(),
                a.getDescription(),
                a.getSchedule(),
                a.getCapacity(),
                a.getIsActive(),
                a.getStartDate(),
                a.getTeacher().getId(),
                a.getTeacher().getName()
        );
    }

    private MemberDTO toMemberDTO(Member m) {
        return new MemberDTO(m.getId(), m.getName(), m.getDni(), m.getIsActive(), m.getMembershipType());
    }
}
