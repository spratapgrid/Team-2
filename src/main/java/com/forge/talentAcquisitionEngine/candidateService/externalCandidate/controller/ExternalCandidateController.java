package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.controller;

import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.dto.CandidateResponse;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.service.ExternalCandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class ExternalCandidateController {

    private final ExternalCandidateService externalCandidateService;


    public ExternalCandidateController(ExternalCandidateService externalCandidateService) {
        this.externalCandidateService = externalCandidateService;
    }

    @PostMapping()
    public ResponseEntity<CandidateResponse> createCandidate(@Valid @RequestBody ExternalCandidate candidate){
        CandidateResponse candidateResponse = externalCandidateService.createCandidate(candidate);
        if (Boolean.TRUE.equals(candidateResponse.getDuplicate())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(candidateResponse);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(candidateResponse);

    }
}
