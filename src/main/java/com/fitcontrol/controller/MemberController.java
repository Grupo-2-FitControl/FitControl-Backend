package com.fitcontrol.controller;

import com.fitcontrol.dto.ActivityDTO;
import com.fitcontrol.dto.MemberDTO;
import com.fitcontrol.service.EnrollmentService;
import com.fitcontrol.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final EnrollmentService enrollmentService;

    public MemberController(MemberService memberService, EnrollmentService enrollmentService) {
        this.memberService = memberService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<MemberDTO>> getActive() {
        return ResponseEntity.ok(memberService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MemberDTO> create(@Valid @RequestBody MemberDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberDTO> update(@PathVariable Long id, @Valid @RequestBody MemberDTO dto) {
        return ResponseEntity.ok(memberService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityDTO>> getActivitiesByMember(@PathVariable Long id) {
        return ResponseEntity.ok(enrollmentService.findActivitiesByMember(id));
    }
}
