package com.dbproject.cvapp.payload.request;

import com.dbproject.cvapp.model.DocumentType;
import com.dbproject.cvapp.model.GenerateDocumentDetails;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GenerateDocumentRequest {
    @NotNull
    DocumentType documentType;

    @NotNull
    Integer userId;

    @NotNull
    GenerateDocumentDetails details;
}
