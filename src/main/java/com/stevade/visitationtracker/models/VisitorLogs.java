package com.stevade.visitationtracker.models;

import com.stevade.visitationtracker.common.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VisitorLogs extends Base {
    private Long visitorId;
    private Long staffId;
    private LocalDateTime dateOfVisit;
    private String reasonForVisit;
}
