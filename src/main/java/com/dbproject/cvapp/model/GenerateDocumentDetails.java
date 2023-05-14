package com.dbproject.cvapp.model;

import lombok.Data;
import org.springframework.security.core.Transient;

@Transient
@Data
public class GenerateDocumentDetails {

    private Integer noOfDays;
    private String startDate;
    private String endDate;
}
