package com.dbproject.cvapp.model;

import lombok.Data;
import org.springframework.security.core.Transient;

@Transient
@Data
public class MonthlyTimesheetData {
    private Integer noOfFilledTimesheets;
    private Integer noOfVacationDays;
    private Integer noOfHolidays;
}
