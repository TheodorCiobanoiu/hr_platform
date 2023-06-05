package com.dbproject.cvapp.dto;

import com.dbproject.cvapp.model.DocumentType;
import com.dbproject.cvapp.model.Request;
import com.dbproject.cvapp.model.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Transient;

@Data
@Transient
@NoArgsConstructor
public class RequestDTO {
    private Integer id;
    private UserDTO userDTO;
    private DocumentType requestType;
    private String startDate;
    private String endDate;
    private Integer noOfDays;
    private Status status;
    private Integer fileId;
    private Boolean signed;

    public RequestDTO(Request request) {
        this.id = request.getId();
        this.userDTO = new UserDTO(request.getUser());
        this.requestType = request.getRequestType();
        this.startDate = request.getStartDate();
        this.endDate = request.getEndDate();
        this.noOfDays = request.getNoOfDays();
        this.status = request.getStatus();
        this.fileId = request.getFileId();
        this.signed = request.getSigned();
    }
}
