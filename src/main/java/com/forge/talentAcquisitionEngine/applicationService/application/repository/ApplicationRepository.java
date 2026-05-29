package com.forge.talentAcquisitionEngine.applicationService.application.repository;

import com.forge.talentAcquisitionEngine.applicationService.application.entity.Application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


    Page<Application> findByAiScoreGreaterThanEqual(
            Integer aiScore,
            Pageable pageable
    );

}