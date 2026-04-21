package com.fitcontrol.controller;

import com.fitcontrol.dto.activity.ActivityDTOResponse;
import com.fitcontrol.dto.member.MemberDTORequest;
import com.fitcontrol.dto.member.MemberDTOResponse;
import com.fitcontrol.service.EnrollmentService;
import com.fitcontrol.service.MemberService;
import jakarta.validation.Valid;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class MemberController {

    private final MemberService memberService;
    private final EnrollmentService enrollmentService;

    public MemberController(MemberService memberService, EnrollmentService enrollmentService) {
        this.memberService = memberService;
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<MemberDTOResponse> createMember(@Valid @RequestBody MemberDTORequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(dto));
    }

    @GetMapping
    public ResponseEntity<List<MemberDTOResponse>> getMembers(
        @RequestParam(name = "activeOnly", defaultValue = "false") boolean activeOnly
    ) {
        List<MemberDTOResponse> members = activeOnly ? memberService.getActiveMembers() : memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/active")
    public ResponseEntity<List<MemberDTOResponse>> getActiveMembers() {
        return ResponseEntity.ok(memberService.getActiveMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTOResponse> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTOResponse> updateMember(@PathVariable Long id, @Valid @RequestBody MemberDTORequest dto) {
        return ResponseEntity.ok(memberService.updateMember(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDTOResponse>> getActivitiesByUser(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.findActivitiesByUser(id));
    }
}
