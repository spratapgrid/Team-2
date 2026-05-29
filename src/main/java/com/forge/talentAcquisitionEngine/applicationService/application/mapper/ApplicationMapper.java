package com.forge.talentAcquisitionEngine.applicationService.application.mapper;

import com.forge.talentAcquisitionEngine.applicationService.application.dto.ApplicationDto;
import com.forge.talentAcquisitionEngine.applicationService.application.entity.Application;
import com.forge.talentAcquisitionEngine.candidateService.externalCandidate.entity.ExternalCandidate;
import com.forge.talentAcquisitionEngine.interviewService.interview.entity.Interview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationMapper {

  private ApplicationMapper() {
  }

  public static Application dtoToEntity(
      ApplicationDto dto,
      ExternalCandidate candidate
//            Demand demand
  ) {

    if (dto == null) {
      return null;
    }

    Application entity = new Application();

    entity.setId(dto.getId());

    entity.setCandidate(candidate);

//        entity.setDemand(demand);

    entity.setSource(dto.getSource());

    entity.setResumeFilePath(
        dto.getResumeFilePath()
    );

    entity.setResumeOriginalFilename(
        dto.getResumeOriginalFilename()
    );

    entity.setMatchedSkills(
        dto.getMatchedSkills()
    );

    entity.setMissingSkills(
        dto.getMissingSkills()
    );

    entity.setAiRationale(
        dto.getAiRationale()
    );

    entity.setFreeNotes(
        dto.getFreeNotes()
    );

    entity.setCurrentStage(
        dto.getCurrentStage()
    );

    entity.setAiScore(
        dto.getAiScore()
    );

    entity.setStageMoveReason(
        dto.getStageMoveReason()
    );

    entity.setAppliedAt(
        dto.getAppliedAt()
    );

    entity.setScreeningAt(
        dto.getScreeningAt()
    );

    entity.setTechnicalAt(
        dto.getTechnicalAt()
    );

    entity.setInterviewAt(
        dto.getInterviewAt()
    );

    entity.setFinalRoundAt(
        dto.getFinalRoundAt()
    );

    entity.setOfferAt(
        dto.getOfferAt()
    );

    entity.setHiredAt(
        dto.getHiredAt()
    );

    entity.setRejectedAt(
        dto.getRejectedAt()
    );

    entity.setRejectionReason(
        dto.getRejectionReason()
    );

    entity.setReferralCode(
        dto.getReferralCode()
    );

    return entity;
  }

  public static ApplicationDto entityToDto(
      Application entity
  ) {

    if (entity == null) {
      return null;
    }

    ApplicationDto dto = new ApplicationDto();

    dto.setId(
        entity.getId()
    );

    if (entity.getCandidate() != null) {

      dto.setCandidateId(
          entity.getCandidate().getCandidateId()
      );
    }

//        if (entity.getDemand() != null) {
//
//            dto.setDemandId(
//                    entity.getDemand().getId()
//            );
//        }

    dto.setSource(
        entity.getSource()
    );

    dto.setResumeFilePath(
        entity.getResumeFilePath()
    );

    dto.setResumeOriginalFilename(
        entity.getResumeOriginalFilename()
    );

    dto.setMatchedSkills(
        entity.getMatchedSkills()
    );

    dto.setMissingSkills(
        entity.getMissingSkills()
    );

    dto.setAiRationale(
        entity.getAiRationale()
    );

    dto.setFreeNotes(
        entity.getFreeNotes()
    );

    dto.setCurrentStage(
        entity.getCurrentStage()
    );

    dto.setAiScore(
        entity.getAiScore()
    );

    dto.setStageMoveReason(
        entity.getStageMoveReason()
    );

    dto.setAppliedAt(
        entity.getAppliedAt()
    );

    dto.setScreeningAt(
        entity.getScreeningAt()
    );

    dto.setTechnicalAt(
        entity.getTechnicalAt()
    );

    dto.setInterviewAt(
        entity.getInterviewAt()
    );

    dto.setFinalRoundAt(
        entity.getFinalRoundAt()
    );

    dto.setOfferAt(
        entity.getOfferAt()
    );

    dto.setHiredAt(
        entity.getHiredAt()
    );

    dto.setRejectedAt(
        entity.getRejectedAt()
    );

    dto.setRejectionReason(
        entity.getRejectionReason()
    );

    dto.setReferralCode(
        entity.getReferralCode()
    );

    if (entity.getInterviews() != null) {

      List<Long> interviewIds =
          entity.getInterviews()
              .stream()
              .map(Interview::getId)
              .collect(Collectors.toList());

      dto.setInterviewIds(
          interviewIds
      );

    } else {

      dto.setInterviewIds(
          new ArrayList<>()
      );
    }

    return dto;
  }
}