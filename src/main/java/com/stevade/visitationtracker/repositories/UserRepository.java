package com.stevade.visitationtracker.repositories;

import com.stevade.visitationtracker.enums.Role;
import com.stevade.visitationtracker.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<List<Member>> findUsersByRole(Role role);
    Optional<Member> findUserById(long id);
    Optional<Member> findUserByEmail(String email);
}
