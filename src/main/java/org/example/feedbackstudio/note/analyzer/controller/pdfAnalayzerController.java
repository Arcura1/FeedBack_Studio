package org.example.feedbackstudio.note.analyzer.controller;


import org.example.feedbackstudio.note.analyzer.service.pdfAnalayzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pdfAnalyzer")
@CrossOrigin(origins = "*")
public class pdfAnalayzerController {
    @Autowired
    public pdfAnalayzerService service;

    @CrossOrigin(origins = "*")
    @PostMapping("/{pdfId}/{userId}")
    public ResponseEntity<String> getPdf(@PathVariable String pdfId,@PathVariable String userId) {

        service.analayzePdf( Long.parseLong(pdfId), Long.parseLong(userId));

        return new ResponseEntity<>("Sıraya Alındı", HttpStatus.OK);
    }
}
