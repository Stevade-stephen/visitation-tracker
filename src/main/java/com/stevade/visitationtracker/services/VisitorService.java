package com.stevade.visitationtracker.services;

import com.stevade.visitationtracker.models.Member;

import java.util.List;

public interface VisitorService {
    List<Member> getAllVisitors();
    Member getVisitorById(long id);
}
