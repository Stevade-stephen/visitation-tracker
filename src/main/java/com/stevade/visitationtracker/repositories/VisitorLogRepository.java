package com.stevade.visitationtracker.repositories;

import com.stevade.visitationtracker.models.VisitorLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorLogRepository extends JpaRepository<VisitorLogs, Long> {

}
