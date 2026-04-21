package com.fitcontrol.controller;

import com.fitcontrol.dto.ActivityDTO;
import com.fitcontrol.dto.MemberDTO;
import com.fitcontrol.service.EnrollmentService;
import com.fitcontrol.service.MemberService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(memberDTO));
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getMembers(
        @RequestParam(name = "activeOnly", defaultValue = "false") boolean activeOnly
    ) {
        List<MemberDTO> members = activeOnly ? memberService.getActiveMembers() : memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/active")
    public ResponseEntity<List<MemberDTO>> getActiveMembers() {
        return ResponseEntity.ok(memberService.getActiveMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> updateMember(@PathVariable Long id, @Valid @RequestBody MemberDTO memberDTO) {
        return ResponseEntity.ok(memberService.updateMember(id, memberDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDTO>> getActivitiesByUser(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.findActivitiesByUser(id));
    }
}
