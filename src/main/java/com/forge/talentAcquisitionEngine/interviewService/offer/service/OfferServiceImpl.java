package com.forge.talentAcquisitionEngine.interviewService.offer.service;

import com.forge.talentAcquisitionEngine.applicationService.application.entity.Application;
import com.forge.talentAcquisitionEngine.applicationService.application.repository.ApplicationRepository;
import com.forge.talentAcquisitionEngine.interviewService.offer.entity.Offer;
import com.forge.talentAcquisitionEngine.interviewService.offer.enums.Status;
import com.forge.talentAcquisitionEngine.interviewService.offer.repository.OfferRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferServiceImpl.OfferService {

    private final OfferRepository offerRepository;
    private final ApplicationRepository applicationRepository;

    /**
     * Create Offer
     */
    @Override
    public Offer createOffer(Offer offer) {

        /*
         * Validate Application Exists
         */
        Long applicationId =
                offer.getApplication().getId();

        Application application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Application not found with ID: "
                                                + applicationId
                                )
                        );

        offer.setApplication(application);

        /*
         * Default Status
         */
        if (offer.getStatus() == null) {
            offer.setStatus(OfferStatus.DRAFT);
        }

        /*
         * Save Offer
         */
        return offerRepository.save(offer);
    }

    /**
     * Get Offer By ID
     */
    @Override
    public Offer getOfferById(Long id) {

        return offerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Offer not found with ID: " + id
                        )
                );
    }

    /**
     * Get All Offers
     */
    @Override
    public Page<Offer> getAllOffers(
            Long applicationId,
            Status status,
            Pageable pageable
    ) {

        /*
         * Filter By Application + Status
         */
        if (applicationId != null && status != null) {

            return offerRepository
                    .findByApplicationIdAndStatus(
                            applicationId,
                            status,
                            pageable
                    );
        }

        /*
         * Filter By Application
         */
        if (applicationId != null) {

            return offerRepository
                    .findByApplicationId(
                            applicationId,
                            pageable
                    );
        }

        /*
         * Filter By Status
         */
        if (status != null) {

            return offerRepository
                    .findByStatus(
                            status,
                            pageable
                    );
        }

        /*
         * Fetch All
         */
        return offerRepository.findAll(pageable);
    }

    /**
     * Update Offer
     */
    @Override
    public Offer updateOffer(
            Long id,
            Offer updatedOffer
    ) {

        Offer existingOffer =
                getOfferById(id);

        /*
         * Update Allowed Fields
         */
        existingOffer.setBaseSalary(
                updatedOffer.getBaseSalary()
        );

        existingOffer.setBonus(
                updatedOffer.getBonus()
        );

        existingOffer.setJoiningDate(
                updatedOffer.getJoiningDate()
        );

        /*
         * Save Updated Offer
         */
        return offerRepository.save(existingOffer);
    }

    /**
     * Send Offer
     */
    @Override
    public Offer sendOffer(Long id) {

        Offer offer =
                getOfferById(id);

        /*
         * Business Validation
         */
        if (offer.getStatus() != OfferStatus.DRAFT) {

            throw new IllegalStateException(
                    "Only draft offers can be sent"
            );
        }

        offer.setStatus(OfferStatus.SENT);

        return offerRepository.save(offer);
    }

    /**
     * Accept Offer
     */
    @Override
    public Offer acceptOffer(Long id) {

        Offer offer =
                getOfferById(id);

        /*
         * Business Validation
         */
        if (offer.getStatus() != OfferStatus.SENT) {

            throw new IllegalStateException(
                    "Only sent offers can be accepted"
            );
        }

        offer.setStatus(OfferStatus.ACCEPTED);

        return offerRepository.save(offer);
    }

    /**
     * Reject Offer
     */
    @Override
    public Offer rejectOffer(Long id) {

        Offer offer =
                getOfferById(id);

        /*
         * Business Validation
         */
        if (offer.getStatus() != OfferStatus.SENT) {

            throw new IllegalStateException(
                    "Only sent offers can be rejected"
            );
        }

        offer.setStatus(OfferStatus.REJECTED);

        return offerRepository.save(offer);
    }

    /**
     * Expire Offer
     */
    @Override
    public Offer expireOffer(Long id) {

        Offer offer =
                getOfferById(id);

        /*
         * Prevent Expiring Accepted Offer
         */
        if (offer.getStatus() == OfferStatus.ACCEPTED) {

            throw new IllegalStateException(
                    "Accepted offer cannot expire"
            );
        }

        offer.setStatus(OfferStatus.EXPIRED);

        return offerRepository.save(offer);
    }

    /**
     * Delete Offer
     */
    @Override
    public void deleteOffer(Long id) {

        Offer offer =
                getOfferById(id);

        offerRepository.delete(offer);
    }

    public static interface OfferService {

        /**
         * Create Offer
         */
        Offer createOffer(Offer offer);

        /**
         * Get Offer By ID
         */
        Offer getOfferById(Long id);

        /**
         * Get All Offers
         */
        Page<Offer> getAllOffers(
                Long applicationId,
                Status status,
                Pageable pageable
        );

        /**
         * Update Offer
         */
        Offer updateOffer(
                Long id,
                Offer offer
        );

        /**
         * Send Offer
         */
        Offer sendOffer(Long id);

        /**
         * Accept Offer
         */
        Offer acceptOffer(Long id);

        /**
         * Reject Offer
         */
        Offer rejectOffer(Long id);

        /**
         * Expire Offer
         */
        Offer expireOffer(Long id);

        /**
         * Delete Offer
         */
        void deleteOffer(Long id);
    }
}