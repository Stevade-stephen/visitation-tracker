package com.stevade.visitationtracker.services;

import com.stevade.visitationtracker.dtos.UserDto;
import com.stevade.visitationtracker.enums.Role;
import com.stevade.visitationtracker.models.Member;
import com.stevade.visitationtracker.repositories.UserRepository;
import com.stevade.visitationtracker.repositories.VisitorLogRepository;
import com.stevade.visitationtracker.services.serviceImpl.StaffServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StaffServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private VisitorLogRepository visitorLogRepository;
    @Mock
    private MailSender sender;
    private StaffServiceImpl underTest;

    private Member stephen;
    private Member george;
    private Member ome;




    @BeforeEach
    void setUp() {
        underTest = new StaffServiceImpl(userRepository, visitorLogRepository, sender);
        stephen = new Member(
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

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void canCreateNewVisitorSuccessfully() {
        UserDto userDto = new UserDto("John", "08134527896", "john@gmail.com", "Ogidan Barracks");
        ResponseEntity<?> newVisitor = underTest.createNewVisitor(userDto);
        ArgumentCaptor<Member> userArgumentCaptor = ArgumentCaptor.forClass(Member.class);
        verify(userRepository, atLeastOnce()).save(userArgumentCaptor.capture());
        assertThat(newVisitor.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void canLogNewVisitationSuccessfully() {

    }
}