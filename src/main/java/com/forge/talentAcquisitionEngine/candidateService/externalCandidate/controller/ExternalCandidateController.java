package com.forge.talentAcquisitionEngine.candidateService.externalCandidate.controller;

import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.dto.CandidateResponse;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.service.ExternalCandidateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/external-candidates")
public class ExternalCandidateController {

    private final ExternalCandidateService externalCandidateService;

    public ExternalCandidateController(ExternalCandidateService externalCandidateService) {
        this.externalCandidateService = externalCandidateService;
    }

    @PostMapping
    public ResponseEntity<CandidateResponse> createCandidate(
            @Valid @RequestBody ExternalCandidate candidate
    ) {
        CandidateResponse response = externalCandidateService.createCandidate(candidate);

        if (Boolean.TRUE.equals(response.getDuplicate())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable Long candidateId,
                                                              @Valid @RequestBody ExternalCandidate updatedCandidate) {
        CandidateResponse response =
                externalCandidateService.updateCandidate(candidateId, updatedCandidate);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}