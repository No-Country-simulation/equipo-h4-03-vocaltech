package com.vocaltech.api.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class PdfGeneratorService {

    public void generatePdf(String outputPath, String diagnosis, String clientName, String qrCodePath) throws IOException {
        String logoPath = "/src/main/resources/logo/vocaltechLogo.png";

        // Lógica para generar el PDF
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Dibujar el logo
        PDImageXObject logoImage = PDImageXObject.createFromFile(logoPath, document);
        contentStream.drawImage(logoImage, 50, 700, 389, 76);

        // Escribir el título
        PDType1Font titleFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
        contentStream.setFont(titleFont, 16);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 650);
        contentStream.showText("Informe de Diagnóstico para " + clientName);
        contentStream.endText();

        // Escribir el nombre del cliente y la fecha
        PDType1Font dateFont = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        contentStream.setFont(dateFont, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 620);
        contentStream.showText("Fecha: " + LocalDate.now().toString());
        contentStream.endText();

        // Escribir el diagnóstico
        PDType1Font bodyFont = new PDType1Font(Standard14Fonts.FontName.COURIER);
        contentStream.setFont(bodyFont, 10);
        contentStream.beginText();
        contentStream.newLineAtOffset(50, 560);
        contentStream.showText("Diagnóstico: " + diagnosis);
        contentStream.endText();

        // Insertar el código QR
        PDImageXObject qrImage = PDImageXObject.createFromFile(qrCodePath, document);
        contentStream.drawImage(qrImage, 50, 400, 100, 100);

        contentStream.close();
        document.save(outputPath);
        document.close();
    }
}