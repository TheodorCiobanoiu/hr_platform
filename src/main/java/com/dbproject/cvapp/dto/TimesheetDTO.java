package com.dbproject.cvapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Transient;

import java.time.LocalDate;
import java.util.List;

@Data
@Transient
@NoArgsConstructor
public class TimesheetDTO {

    private Integer month;
    private Integer userID;
    private List<LocalDate> workDates;

}
