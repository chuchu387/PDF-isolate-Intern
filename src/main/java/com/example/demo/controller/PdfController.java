package com.example.demo.pdfservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/extract-iccid")
    public String extractIccidPage(
            @RequestParam String iccid,
            @RequestParam String inputPath,
            @RequestParam String outputPath) throws IOException {
        pdfService.extractPageByIccid(inputPath, outputPath, iccid);
        return "Page containing ICCID " + iccid + " has saved to " + outputPath;
    }
}
