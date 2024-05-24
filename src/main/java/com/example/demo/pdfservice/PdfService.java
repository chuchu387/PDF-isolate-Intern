package com.example.demo.pdfservice;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@Service
public class PdfService {

    public String extractPageByIccid(String inputPdfPath, String outputPdfPath, String iccid) {
        try (PDDocument document = PDDocument.load(new File(inputPdfPath))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();

            Iterator<PDPage> pageIterator = document.getPages().iterator();
            int pageIndex = 0;

            while (pageIterator.hasNext()) {
                PDPage page = pageIterator.next();
                pdfStripper.setStartPage(pageIndex + 1);
                pdfStripper.setEndPage(pageIndex + 1);
                String pageText = pdfStripper.getText(document);

                if (pageText.contains(iccid)) {
                    try (PDDocument newDocument = new PDDocument()) {
                        newDocument.addPage(page);
                        newDocument.save(outputPdfPath);
                        return "Page containing ICCID " + iccid + " has saved to " + outputPdfPath;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return "Error occurred while saving the extracted page.";
                    }
                }

                pageIndex++;
            }
            return "ICCID " + iccid + " not found in the PDF.";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while processing the PDF: " + e.getMessage();
        }
    }
}
