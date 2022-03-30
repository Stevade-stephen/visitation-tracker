package com.stevade.visitationtracker.services.serviceImpl;

import com.stevade.visitationtracker.dtos.UserDto;
import com.stevade.visitationtracker.enums.Role;
import com.stevade.visitationtracker.exceptions.NoSuchStaffException;
import com.stevade.visitationtracker.models.Member;
import com.stevade.visitationtracker.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SuperAdminServiceImplTest {
    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    private SuperAdminServiceImpl superAdminService;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        superAdminService = new SuperAdminServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void canCreateNewStaffSuccessfully() {
        UserDto userDto = new UserDto("John", "08134527896", "john@gmail.com", "Ogidan Barracks");
        ResponseEntity<?> newStaff = superAdminService.createNewStaff(userDto);
        verify(userRepository).save(any());
        assertThat(newStaff.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(newStaff.getBody()).isNotNull();

    }

    @Test
    void shouldGetAllStaffsSuccessfully() {
        superAdminService.getAllStaffs();
        verify(userRepository, atMostOnce()).findUsersByRole(any());
    }

    @Test
    void canGetStaffByIdIfStaffAlreadyExist() {
        Member george = new Member(
                "George",
                "08087625831",
                "george@gmail.com",
                "george",
                "Barracks estate",
                Role.STAFF
        );
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(george));
        Member staff = superAdminService.getStaffById(1L);
        assertThat(staff).isEqualTo(george);

    }

    @Test
    void shouldThrowExceptionTryingToGetAnUnsavedStaff() {
        assertThatThrownBy(()-> superAdminService.getStaffById(1L))
                .isInstanceOf(NoSuchStaffException.class)
                .hasMessage("No visitor with id "+ 1+" found");
    }
}