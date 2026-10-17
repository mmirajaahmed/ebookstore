package com.ebookstore.controller;

import com.ebookstore.model.Member;
import com.ebookstore.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Tag(name = "Members", description = "Member registration, profile management")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "List all members (admin)")
    public ResponseEntity<List<Member>> listAll() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID")
    public ResponseEntity<Member> getById(@PathVariable String id) {
        return ResponseEntity.ok(memberService.getMember(id));
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new member")
    public ResponseEntity<Member> register(@RequestBody Member member) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.registerMember(member));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update member profile")
    public ResponseEntity<Member> update(@PathVariable String id, @RequestBody Member member) {
        return ResponseEntity.ok(memberService.updateMember(id, member));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete member (admin)")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
