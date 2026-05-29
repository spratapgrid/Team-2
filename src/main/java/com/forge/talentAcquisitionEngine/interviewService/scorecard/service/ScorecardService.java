package com.forge.talentAcquisitionEngine.interviewService.scorecard.service;

import com.forge.talentAcquisitionEngine.applicationService.application.entity.Application;
import com.forge.talentAcquisitionEngine.applicationService.application.repository.ApplicationRepository;
import com.forge.talentAcquisitionEngine.interviewService.interview.entity.Interview;
import com.forge.talentAcquisitionEngine.interviewService.interview.repository.InterviewRepository;
import com.forge.talentAcquisitionEngine.interviewService.scorecard.dto.ScorecardRequestDto;
import com.forge.talentAcquisitionEngine.interviewService.scorecard.dto.ScorecardResponseDto;
import com.forge.talentAcquisitionEngine.interviewService.scorecard.dto.ScorecardSummaryDto;
import com.forge.talentAcquisitionEngine.interviewService.scorecard.entity.Scorecard;
import com.forge.talentAcquisitionEngine.interviewService.scorecard.repository.ScorecardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScorecardService {

    private final ScorecardRepository scorecardRepository;
    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public ScorecardResponseDto submitScorecard(Long interviewId, ScorecardRequestDto dto) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new EntityNotFoundException("Interview not found with id: " + interviewId));

        Application application = applicationRepository.findById(dto.getApplicationId())
                .orElseThrow(() -> new EntityNotFoundException("Application not found with id: " + dto.getApplicationId()));

        if (scorecardRepository.existsByInterview_IdAndInterviewerId(interviewId, dto.getInterviewerId())) {
            throw new IllegalStateException(
                    "Scorecard already submitted by interviewer " + dto.getInterviewerId()
                            + " for interview " + interviewId);
        }

        Scorecard scorecard = new Scorecard();
        scorecard.setInterview(interview);
        scorecard.setApplication(application);
        scorecard.setInterviewerId(dto.getInterviewerId());
        scorecard.setScore(dto.getScore());
        scorecard.setOverallScore(dto.getOverallScore());
        scorecard.setCompetencyRatings(dto.getCompetencyRatings());
        scorecard.setStrengths(dto.getStrengths());
        scorecard.setConcerns(dto.getConcerns());
        scorecard.setComments(dto.getComments());
        scorecard.setRecommendation(dto.getRecommendation());

        Scorecard saved = scorecardRepository.save(scorecard);
        return toResponseDto(saved);
    }

    @Transactional(readOnly = true)
    public List<ScorecardSummaryDto> getScorecardsByInterview(Long interviewId) {
        if (!interviewRepository.existsById(interviewId)) {
            throw new EntityNotFoundException("Interview not found with id: " + interviewId);
        }
        return scorecardRepository.findAllByInterview_Id(interviewId)
                .stream()
                .map(this::toSummaryDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ScorecardResponseDto getScorecardByInterviewer(Long interviewId, Long interviewerId) {
        if (!interviewRepository.existsById(interviewId)) {
            throw new EntityNotFoundException("Interview not found with id: " + interviewId);
        }
        Scorecard scorecard = scorecardRepository.findByInterview_IdAndInterviewerId(interviewId, interviewerId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Scorecard not found for interviewer " + interviewerId
                                + " in interview " + interviewId));
        return toResponseDto(scorecard);
    }

    @Transactional(readOnly = true)
    public List<ScorecardSummaryDto> getScorecardsByDemand(String demandId) {
        return scorecardRepository.findAll()
                .stream()
//                .filter(sc -> demandId.equals(sc.getApplication().getDemand().getDemandId()))
                .map(this::toSummaryDto)
                .collect(Collectors.toList());
    }

    // ── Mappers ──────────────────────────────────────────────────────────────

    private ScorecardResponseDto toResponseDto(Scorecard sc) {
        return ScorecardResponseDto.builder()
                .scorecardId(sc.getId())
                .interviewId(sc.getInterview().getId())
                .applicationId(sc.getApplication().getApplicationId())
                .interviewerId(sc.getInterviewerId())
                .score(sc.getScore())
                .overallScore(sc.getOverallScore())
                .competencyRatings(sc.getCompetencyRatings())
                .strengths(sc.getStrengths())
                .concerns(sc.getConcerns())
                .comments(sc.getComments())
                .recommendation(sc.getRecommendation())
                .submittedAt(sc.getSubmittedAt())
                .build();
    }

    private ScorecardSummaryDto toSummaryDto(Scorecard sc) {
        return ScorecardSummaryDto.builder()
                .scorecardId(sc.getId())
                .interviewerId(sc.getInterviewerId())
                .score(sc.getScore())
                .recommendation(sc.getRecommendation())
                .build();
    }
}