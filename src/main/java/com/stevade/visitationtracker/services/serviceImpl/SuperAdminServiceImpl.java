package com.stevade.visitationtracker.services.serviceImpl;

import com.stevade.visitationtracker.dtos.UserDto;
import com.stevade.visitationtracker.enums.Role;
import com.stevade.visitationtracker.exceptions.NoSuchStaffException;
import com.stevade.visitationtracker.models.Member;
import com.stevade.visitationtracker.repositories.UserRepository;
import com.stevade.visitationtracker.services.SuperAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SuperAdminServiceImpl implements SuperAdminService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SuperAdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<Member> createNewStaff(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        Member member = modelMapper.map(userDto, Member.class);
        member.setRole(Role.STAFF);
        String password = generateRandomPassword();
        log.info(password);
        member.setPassword(passwordEncoder.encode(password));

        System.out.println(password);
        return new ResponseEntity<>(userRepository.save(member), HttpStatus.CREATED);
    }

    @Override
    public List<Member> getAllStaffs() {
        Optional<List<Member>> staffList = userRepository.findUsersByRole(Role.STAFF);
        return staffList.orElseGet(ArrayList::new);
    }

    @Override
    public Member getStaffById(long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new NoSuchStaffException("No visitor with id  found" + (id)));
    }

    private String generateRandomPassword() {
        UUID uuid = UUID.randomUUID();
        String password = uuid.toString();
        password = password.substring(0, 10);
        return password;
    }
}
