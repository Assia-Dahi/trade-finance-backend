package com.tradefinance.trade_finance_backend.ocr.impl;

import com.tradefinance.trade_finance_backend.entity.Document;
import com.tradefinance.trade_finance_backend.ocr.OCRService;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class TesseractOCRServiceImpl implements OCRService {

    @Override
    public String extraireTexte(Document document) {
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
        tesseract.setLanguage("fra+eng");

        File fichier = new File(document.getCheminFichier());

        if (!fichier.exists()) {
            throw new RuntimeException("Fichier introuvable : " + fichier.getAbsolutePath());
        }

        String nomFichier = fichier.getName().toLowerCase();

        try {
            if (nomFichier.endsWith(".pdf")) {
                return extraireTextePdf(fichier, tesseract);
            } else {
                // Image directe (jpg, png, tiff...)
                return tesseract.doOCR(fichier);
            }
        } catch (TesseractException | IOException e) {
            throw new RuntimeException("Erreur OCR pour le document " + document.getId(), e);
        }
    }

    private String extraireTextePdf(File fichierPdf, ITesseract tesseract) throws IOException, TesseractException {
        StringBuilder texteComplet = new StringBuilder();

        try (PDDocument pdf = Loader.loadPDF(fichierPdf)) {
            PDFRenderer renderer = new PDFRenderer(pdf);
            int nombrePages = pdf.getNumberOfPages();

            for (int i = 0; i < nombrePages; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300);
                String textePage = tesseract.doOCR(image);
                texteComplet.append(textePage).append("\n");
            }
        }

        return texteComplet.toString();
    }
}