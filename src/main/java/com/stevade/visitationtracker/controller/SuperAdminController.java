package com.stevade.visitationtracker.controller;

import com.stevade.visitationtracker.dtos.UserDto;
import com.stevade.visitationtracker.models.Member;
import com.stevade.visitationtracker.services.SuperAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("")
public class SuperAdminController {
    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }

    @PostMapping("/staff")
    public ResponseEntity<Member> addNewStaff(@RequestBody UserDto userDto){
        return superAdminService.createNewStaff(userDto);
    }

    @GetMapping("/staff")
    public ResponseEntity<?> getAllStaffs(){
        List<Member> allStaffs = superAdminService.getAllStaffs();
        return new ResponseEntity<>(allStaffs, HttpStatus.OK);
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity<?> getStaffById(@PathVariable final long id){
        return new ResponseEntity<>(superAdminService.getStaffById(id), HttpStatus.OK);
    }
}
