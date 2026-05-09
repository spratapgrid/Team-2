package com.forge.talentAcquisitionEngine.offer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.forge.talentAcquisitionEngine.application.entity.ApplicationEntity;
import com.forge.talentAcquisitionEngine.offer.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "offer")
public class OfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "offer_id")
    private UUID id;

    @JoinColumn(name = "application_id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private ApplicationEntity application;

    @Column(precision = 19, scale = 4)
    private BigDecimal baseSalary;

    @Column(precision = 19, scale = 4)
    private BigDecimal bonus;

    @Column(precision = 19, scale = 4)
    private BigDecimal equity;

    private String currency;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "approval_chain", columnDefinition = "jsonb")
    private String approvalChain;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "signed_at")
    private LocalDateTime signedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "docu_sign_id")
    private String docuSignId;
}