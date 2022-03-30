package com.stevade.visitationtracker.services.serviceImpl;

import com.stevade.visitationtracker.dtos.UserDto;
import com.stevade.visitationtracker.dtos.VisitorLogsDto;
import com.stevade.visitationtracker.enums.Role;
import com.stevade.visitationtracker.models.Member;
import com.stevade.visitationtracker.models.VisitorLogs;
import com.stevade.visitationtracker.repositories.UserRepository;
import com.stevade.visitationtracker.repositories.VisitorLogRepository;
import com.stevade.visitationtracker.services.MailSender;
import com.stevade.visitationtracker.services.StaffService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {
    private final UserRepository userRepository;
    private final VisitorLogRepository visitorLogRepository;
    private final MailSender mailSender;

    public StaffServiceImpl(UserRepository userRepository, VisitorLogRepository visitorLogRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.visitorLogRepository = visitorLogRepository;
        this.mailSender = mailSender;
    }

    @Override
    public ResponseEntity<?> createNewVisitor(UserDto userDto) {
        ModelMapper modelMapper = new ModelMapper();
        Member member = modelMapper.map(userDto, Member.class);
        member.setRole(Role.VISITOR);
        userRepository.save(member);
        return new ResponseEntity<>("New visitor has been successfully created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> logVisit(VisitorLogsDto visitorLogsDto) {
        Optional<Member> visitor = userRepository.findUserById(visitorLogsDto.getVisitorId());

        if(visitor.isPresent()) {
            Member staff = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ModelMapper modelMapper = new ModelMapper();
            VisitorLogs visitorLogs = modelMapper.map(visitorLogsDto, VisitorLogs.class);
            visitorLogs.setDateOfVisit(LocalDateTime.now());
            visitorLogs.setStaffId(staff.getId());

            try {
                String text = "" + visitor.get().getName() + "visited the organization" + "\n"
                        + "Reason : " + "\n" + visitorLogs.getReasonForVisit();
                mailSender.sendEmail(staff.getEmail(), "New Visitation Log", text);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

            return new ResponseEntity<>(visitorLogRepository.save(visitorLogs), HttpStatus.OK);

        }
        else
        return new ResponseEntity<>("No such visitor with id " + visitorLogsDto.getVisitorId(), HttpStatus.NOT_FOUND);
    }


}
