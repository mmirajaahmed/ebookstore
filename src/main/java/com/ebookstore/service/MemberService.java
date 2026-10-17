package com.ebookstore.service;

import com.ebookstore.model.Member;
import com.ebookstore.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMember(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Member not found: " + id));
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Member not found for email: " + email));
    }

    public Member registerMember(Member member) {
        memberRepository.findByEmail(member.getEmail()).ifPresent(m -> {
            throw new IllegalStateException("Email already registered: " + member.getEmail());
        });
        member.setRole("CUSTOMER");
        member.setStatus("ACTIVE");
        member.setLoyaltyPoints(0);
        member.setCreatedAt(Instant.now());
        member.setUpdatedAt(Instant.now());
        return memberRepository.save(member);
    }

    public Member updateMember(String id, Member updated) {
        getMember(id); // validates existence
        updated.setId(id);
        updated.setUpdatedAt(Instant.now());
        return memberRepository.save(updated);
    }

    public void deleteMember(String id) {
        if (!memberRepository.deleteById(id)) {
            throw new NoSuchElementException("Member not found: " + id);
        }
    }
}
