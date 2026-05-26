package com.forge.talentAcquisitionEngine.candidateService.resumeParser.strategy;

import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;


public interface ResumeParsingStrategy {

    ExternalCandidate parse(byte[] fileBytes, String originalFilename, Long candidateId);
}
