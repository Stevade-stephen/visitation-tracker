package com.stevade.visitationtracker.repositories;

import com.stevade.visitationtracker.enums.Role;
import com.stevade.visitationtracker.models.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private UserRepository underTest;
    private Member ome;
    private Member stephen;
    private Member lawal;
    private Member george;

    @BeforeEach
    void setUp() {
        stephen  = new Member(
                "Stephen",
                "09087665532",
                "ta@gmail.com",
                "testing",
                "Barracks estate",
                Role.STAFF
        );
        george = new Member(
                "George",
                "08087625831",
                "george@gmail.com",
                "george",
                "Barracks estate",
                Role.STAFF
        );

        ome = new Member(
                "Ome",
                "08156862581",
                "ome@gmail.com",
                "omenebele",
                "Barracks estate",
                Role.VISITOR
        );

        lawal = new Member(
                "Lawal",
                "08087625831",
                "lawal@gmail.com",
                "lawani",
                "Barracks estate",
                Role.VISITOR
        );

    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void canFindUsersByRole() {
        underTest.saveAll(List.of(stephen, george, lawal, ome));

        Optional<List<Member>> usersByRole = underTest.findUsersByRole(Role.VISITOR);
        assertThat(usersByRole).isNotNull();
        assertThat(usersByRole.get().size()).isEqualTo(2);

        Optional<List<Member>> staffs = underTest.findUsersByRole(Role.STAFF);
        assertThat(staffs).isNotNull();
        assertThat(staffs.get().size()).isEqualTo(2);
    }

    @Test
    void canFindUserById() {
        underTest.save(stephen);
        Optional<Member> user = underTest.findUserById(1);
        assertThat(user).isNotNull();
    }

    @Test
    void canFindUserByEmail() {
        underTest.save(ome);

        Optional<Member> savedOme = underTest.findUserByEmail("ome@gmail.com");
        assertThat(savedOme.get()).isEqualTo(ome);
    }
}