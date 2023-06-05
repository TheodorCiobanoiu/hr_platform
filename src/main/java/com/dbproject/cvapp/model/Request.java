package com.dbproject.cvapp.model;

import com.dbproject.cvapp.payload.request.GenerateDocumentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private MyUser user;
    private DocumentType requestType;
    private String startDate;
    private String endDate;
    private Integer noOfDays;
    private Status status;
    private Integer fileId;
    private Boolean signed;

    public Request(GenerateDocumentRequest generateDocumentRequest, MyUser user) {
        this.user = user;
        this.requestType = generateDocumentRequest.getDocumentType();
        this.startDate = generateDocumentRequest.getDetails().getStartDate();
        this.endDate = generateDocumentRequest.getDetails().getEndDate();
        this.noOfDays = generateDocumentRequest.getDetails().getNoOfDays();
        this.status = Status.Not_Reviewed;
        this.fileId = null;
    }

}
