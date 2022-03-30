package com.stevade.visitationtracker.services.serviceImpl;

import com.stevade.visitationtracker.enums.Role;
import com.stevade.visitationtracker.exceptions.NoVisitorFoundException;
import com.stevade.visitationtracker.models.Member;
import com.stevade.visitationtracker.repositories.UserRepository;
import com.stevade.visitationtracker.services.VisitorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VisitorServiceImpl implements VisitorService {
    private final UserRepository userRepository;

    public VisitorServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Member> getAllVisitors() {
        Optional<List<Member>> visitors = userRepository.findUsersByRole(Role.VISITOR);
        return visitors.orElseGet(ArrayList::new);
    }

    @Override
    public Member getVisitorById(long id) {
        return userRepository.findUserById(id)
                .orElseThrow(()-> new NoVisitorFoundException("No visitor with id " + id + " found"));

//        return userRepository.findUserById(id)
//                .orElseThrow(()-> new NoVisitorFoundException("No visitor with %d found".formatted(id)));
    }
}
