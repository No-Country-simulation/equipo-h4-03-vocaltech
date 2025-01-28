package com.vocaltech.api.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfGeneratorService {

    private String[] splitTextIntoLines(String text, PDFont font, float fontSize, float maxWidth) throws IOException {
        StringBuilder lineBuilder = new StringBuilder();
        StringBuilder wordBuffer = new StringBuilder();
        List<String> lines = new ArrayList<>();

        for (char c : text.toCharArray()) {
            if (Character.isWhitespace(c)) {
                // Calcular el ancho de la línea actual más la palabra en el buffer
                float lineWidth = font.getStringWidth(lineBuilder + wordBuffer.toString()) / 1000 * fontSize;
                if (lineWidth > maxWidth) {
                    // Si excede el ancho máximo, guardar la línea actual y comenzar una nueva
                    lines.add(lineBuilder.toString());
                    lineBuilder = new StringBuilder();
                } else {
                    // Añadir la palabra al constructor de la línea
                    lineBuilder.append(wordBuffer).append(" ");
                }
                // Limpiar el buffer de la palabra
                wordBuffer.setLength(0);
            } else {
                // Construir la palabra actual
                wordBuffer.append(c);
            }
        }

        // Añadir la última línea si hay contenido restante
        if (lineBuilder.length() > 0 || wordBuffer.length() > 0) {
            lines.add(lineBuilder.append(wordBuffer).toString());
        }

        return lines.toArray(new String[0]);
    }


    public void generatePdf(String outputPath, String diagnosis, String clientName, byte[] qrBytes) throws IOException {
        InputStream logoStream = getClass().getResourceAsStream("/logo/vocaltechLogo.png");
        if (logoStream == null) {
            throw new FileNotFoundException("Logo no encontrado en /logo/vocaltechLogo.png");
        }

        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        float margin = 50;
        float yPosition = 580; // Coordenada Y inicial para escribir el texto
        float leading = 14; // Espaciado entre líneas
        float pageHeight = page.getMediaBox().getHeight();

        PDFont font = new PDType1Font(Standard14Fonts.FontName.COURIER);
        float fontSize = 10;

        PDPageContentStream contentStream = null;

        try {
            contentStream = new PDPageContentStream(document, page);

            // Dibujar el logo
            PDImageXObject logoImage = PDImageXObject.createFromByteArray(document, logoStream.readAllBytes(), "logo");
            contentStream.drawImage(logoImage, margin, 700, 389, 76);

            // Escribir el título
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, 650);
            contentStream.showText("Informe de Diagnóstico para " + clientName);
            contentStream.endText();

            // Escribir la fecha
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, 620);
            contentStream.showText("Fecha: " + LocalDate.now());
            contentStream.endText();

            // Preparar para escribir el diagnóstico
            contentStream.setFont(font, fontSize);

            // Dividir el diagnóstico en líneas manejables
            String[] lines = splitTextIntoLines(diagnosis, font, fontSize, page.getMediaBox().getWidth() - 2 * margin);

            for (String line : lines) {
                if (yPosition <= margin) {
                    // Crear una nueva página si el texto excede el espacio disponible
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(font, fontSize);
                    yPosition = pageHeight - margin;
                }

                // Escribir la línea
                contentStream.beginText();
                contentStream.newLineAtOffset(margin, yPosition);
                contentStream.showText(line);
                contentStream.endText();

                yPosition -= leading; // Mover hacia abajo para la siguiente línea
            }


            // Insertar el código QR
            // Insertar el código QR
            PDImageXObject qrImage = PDImageXObject.createFromByteArray(document, qrBytes, "qrCode");
            if (yPosition - 100 <= margin) {
                // Crear nueva página si no hay espacio suficiente
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                yPosition = pageHeight - margin;
            }
            contentStream.drawImage(qrImage, margin, yPosition - 100, 100, 100);
        } finally {
            if (contentStream != null) {
                contentStream.close();
            }
            document.save(outputPath);
            document.close();
        }
    }
}



