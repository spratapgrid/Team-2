package com.forge.talentAcquisitionEngine.candidateService.resumeParser.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

public class Gpt4oParsingStrategy implements ResumeParsingStrategy{

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper;


    @Value("${forge.ai.openai.api-key:mock-key}")
    private String apiKey;

    public Gpt4oParsingStrategy(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public ExternalCandidate parse(byte[] fileBytes, String originalFilename, Long candidateId) {

        String url = "https://api.openai.com/v1/chat/completions";
        //grpc for automatic
        String base64File = Base64.getEncoder().encodeToString(fileBytes);
        return null;
    }
}
