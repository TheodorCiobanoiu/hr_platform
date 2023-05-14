package com.dbproject.cvapp.service;

import com.dbproject.cvapp.exception.NoUserException;
import com.dbproject.cvapp.model.MyUser;
import com.dbproject.cvapp.payload.request.GenerateDocumentRequest;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PDFGeneratorService {

    private final MyUserService myUserService;
    @Value("${static.societate}")
    private String SOCIETATE;

    public void export(HttpServletResponse response, GenerateDocumentRequest generateDocumentRequest) throws IOException, NoUserException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
        MyUser user = myUserService.getUserById(generateDocumentRequest.getUserId());
        switch (generateDocumentRequest.getDocumentType()) {
            case CERERE_CONCEDIU_ODIHNA ->
                    generateConcediuOdihna(response, user, generateDocumentRequest, currentDateTime);
        }
    }

    public void generateConcediuOdihna(HttpServletResponse response, MyUser user,
                                       GenerateDocumentRequest documentRequest, String currentDateTime) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
//        TODO: Logo to be added :?
//        Image logo = getLogo();
//        document.add(logo);
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("CERERE", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        paragraph = new Paragraph("CONCEDIU DE ODIHNA", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        paragraph = new Paragraph(
                "   Subsemnatul " + user.getLastName() + " " + user.getFirstName() + ", angajat al societatii  " +
                        SOCIETATE + ", avand functia de " + user.getUserDetails().getJobType().getJobTitle() +
                        " , va rog sa imi aprobati prezenta cerere prin care solicit efectuarea a " +
                        documentRequest.getDetails().getNoOfDays() + " zile din concediul de odihna in perioada " +
                        documentRequest.getDetails().getStartDate() + " - " + documentRequest.getDetails().getEndDate()
                , fontParagraph);
        paragraph.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph.setSpacingBefore(30);
        document.add(paragraph);

        paragraph = new Paragraph("Data: " + currentDateTime, fontParagraph);
        paragraph.setAlignment(Paragraph.ALIGN_LEFT);
        paragraph.setSpacingBefore(300);
        document.add(paragraph);

        paragraph = new Paragraph("Semnatura angajat: ", fontParagraph);
        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(paragraph);


        paragraph = new Paragraph("APROBAT", fontParagraph);
        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
        paragraph.setSpacingBefore(100);
        document.add(paragraph);

        paragraph = new Paragraph("(Nume, Prenume, Semnatura)", fontParagraph);
        paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(paragraph);

        document.close();
    }

    private Image getLogo() throws IOException {

        BufferedImage image = ImageIO.read(new File("D:\\Facultate\\Licenta\\hr_platform_api\\src\\main\\java\\com\\dbproject\\cvapp\\assets\\logo.jpg"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return ImageLoader.getJpegImage(byteArray);
    }
}






















