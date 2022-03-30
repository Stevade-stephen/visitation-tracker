package com.stevade.visitationtracker.services;

import com.stevade.visitationtracker.dtos.UserDto;
import com.stevade.visitationtracker.dtos.VisitorLogsDto;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;

public interface StaffService {
    ResponseEntity<?> createNewVisitor(UserDto userDto);
    ResponseEntity<?> logVisit(VisitorLogsDto visitorLogsDto) throws MessagingException;
}
