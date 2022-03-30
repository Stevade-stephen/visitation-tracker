package com.stevade.visitationtracker.services;

import com.stevade.visitationtracker.dtos.UserDto;
import com.stevade.visitationtracker.models.Member;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SuperAdminService {
    ResponseEntity<Member> createNewStaff(UserDto userDto);
    List<Member> getAllStaffs();
    Member getStaffById(long id);
}
