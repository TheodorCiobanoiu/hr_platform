package com.dbproject.cvapp.controller;

import com.dbproject.cvapp.exception.FileStorageException;
import com.dbproject.cvapp.exception.MyFileNotFoundException;
import com.dbproject.cvapp.exception.NoUserException;
import com.dbproject.cvapp.model.DBFile;
import com.dbproject.cvapp.payload.request.GenerateDocumentRequest;
import com.dbproject.cvapp.payload.response.UploadFileResponse;
import com.dbproject.cvapp.service.DBFileStorageService;
import com.dbproject.cvapp.service.PDFGeneratorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("file")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private final DBFileStorageService dbFileStorageService;
    private final PDFGeneratorService pdfGeneratorService;

    @PostMapping("uploadFile")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or hasRole('EMPLOYEE')")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws FileStorageException {
        DBFile dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId().toString())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize(), dbFile.getId());

    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> {
                    try {
                        return uploadFile(file);
                    } catch (FileStorageException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) throws MyFileNotFoundException {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);
        log.info("The file with id " + dbFile.getId() + ": " + dbFile.getFileName() + "has been sent to user");
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @PostMapping("/generate-file")
    public void generatePDF(HttpServletResponse response, @RequestBody GenerateDocumentRequest generateDocumentRequest) throws IOException, NoUserException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        System.out.println("New file generating...");
        this.pdfGeneratorService.export(response, generateDocumentRequest);
    }
}
