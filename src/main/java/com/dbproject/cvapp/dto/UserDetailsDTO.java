package com.dbproject.cvapp.dto;

import com.dbproject.cvapp.model.Department;
import com.dbproject.cvapp.model.JobType;
import com.dbproject.cvapp.model.UserDetails;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Transient;

@Data
@Transient
@NoArgsConstructor
public class UserDetailsDTO {
    private Integer userId;
    private JobType jobType;
    private String jobTitle;
    private String jobDescription;
    private Integer noOfVacationDays;
    private Department department;

    UserDetailsDTO(UserDetails userDetails) {
        this.userId = userDetails.getMyUser().getUserID();
        this.jobType = userDetails.getJobType();
        this.jobTitle = userDetails.getJobType().getJobTitle();
        this.jobDescription = userDetails.getJobType().getJobDescription();
        this.department = userDetails.getDepartment();
        this.noOfVacationDays = userDetails.getNoOfVacationDays();
    }
}
