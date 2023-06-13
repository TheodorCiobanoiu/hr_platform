package com.dbproject.cvapp.model;

import lombok.Data;
import org.springframework.security.core.Transient;

@Transient
@Data
public class OverviewMessage {
    private String timeTillNextVacationDay;
    private String noOfOpenRequests;
    private String warningForTimesheet;
}
