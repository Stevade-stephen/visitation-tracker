package com.stevade.visitationtracker.controller;


import com.stevade.visitationtracker.dtos.UserDto;
import com.stevade.visitationtracker.dtos.VisitorLogsDto;
import com.stevade.visitationtracker.services.StaffService;
import com.stevade.visitationtracker.services.VisitorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController @RequestMapping("")
public class StaffController {

    private final StaffService staffService;
    private final VisitorService visitorService;

    public StaffController(StaffService staffService, VisitorService visitorService) {
        this.staffService = staffService;
        this.visitorService = visitorService;
    }

    @PostMapping("/visitor")
    public ResponseEntity<?> addNewVisitor(@RequestBody UserDto userDto){
        return staffService.createNewVisitor(userDto);
    }

    @GetMapping("/visitors")
    public ResponseEntity<?> getAllVisitors(){
        return new ResponseEntity<>(visitorService.getAllVisitors(), HttpStatus.OK);
    }

    @GetMapping("/visitor/{id}")
    public ResponseEntity<?> getVisitorById(@PathVariable final long id){
        return new ResponseEntity<>(visitorService.getVisitorById(id), HttpStatus.OK);
    }

    @PostMapping("/visit")
    public ResponseEntity<?> recordVisit(@RequestBody VisitorLogsDto visitorLogsDto){
        try {
            return staffService.logVisit(visitorLogsDto);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Exception was caught while sending mail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
