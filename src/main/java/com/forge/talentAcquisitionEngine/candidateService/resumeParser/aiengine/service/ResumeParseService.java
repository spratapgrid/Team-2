package com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.service;

import com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.model.ParsedResumeDTO;
import org.springframework.web.multipart.MultipartFile;

public class ResumeParseService {

    public ParsedResumeDTO parseResume(MultipartFile resumeFile) {
        return new ParsedResumeDTO();
    }
}
