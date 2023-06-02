package com.dbproject.cvapp.dto;

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
    private Integer noOfVacationDays;

    UserDetailsDTO(UserDetails userDetails) {
        this.userId = userDetails.getMyUser().getUserID();
        this.jobType = userDetails.getJobType();
        this.noOfVacationDays = userDetails.getNoOfVacationDays();
    }
}
