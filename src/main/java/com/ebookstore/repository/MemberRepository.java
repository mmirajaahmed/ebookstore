package com.ebookstore.repository;

import com.ebookstore.config.MockDataStore;
import com.ebookstore.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final MockDataStore store;

    public List<Member> findAll() {
        return Collections.unmodifiableList(store.getMembers());
    }

    public Optional<Member> findById(String id) {
        return store.getMembers().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
    }

    public Optional<Member> findByEmail(String email) {
        return store.getMembers().stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Member save(Member member) {
        if (member.getId() == null || member.getId().isBlank()) {
            member.setId("M" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        List<Member> members = store.getMembers();
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getId().equals(member.getId())) {
                members.set(i, member);
                return member;
            }
        }
        members.add(member);
        return member;
    }

    public boolean deleteById(String id) {
        return store.getMembers().removeIf(m -> m.getId().equals(id));
    }
}
