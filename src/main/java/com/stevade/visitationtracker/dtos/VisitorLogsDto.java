package com.stevade.visitationtracker.dtos;

import lombok.Data;

@Data
public class VisitorLogsDto {
    private Long visitorId;
    private String reasonForVisit;
}
