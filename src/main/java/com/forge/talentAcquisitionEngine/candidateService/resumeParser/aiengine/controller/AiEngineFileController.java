package com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.controller;

import com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.model.ParsedResumeDTO;
import com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.service.ResumeParseService;
import com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.service.ResumeService;
import com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.service.ResumeStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/v1/aiengine")
public class AiEngineFileController {

    @Autowired
    private ResumeStoreService resumeStoreService;

    @Autowired
    private ResumeParseService resumeParseService;

    @PostMapping
    public ResponseEntity<ParsedResumeDTO> parseAndSaveResume(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (file.getContentType()==null||!file.getContentType().equals("application/pdf")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String fileName = file.getOriginalFilename();
        if (fileName==null||fileName.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        resumeStoreService.saveResume(file);
        ParsedResumeDTO parsedResumeDTO = resumeParseService.parseResume(file);


        return ResponseEntity.status(HttpStatus.CREATED).body(parsedResumeDTO);
    }


}
