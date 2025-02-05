package com.vocaltech.api.domain.campaigns;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ICampaignEmailRepository extends JpaRepository<CampaignEmail, UUID> {

    @Query("SELECT ce FROM CampaignEmail ce WHERE ce.campaign.id = :campaignId AND ce.emailStep = :step")
    Optional<CampaignEmail> findByCampaignAndStep(@Param("campaignId") UUID campaignId, @Param("step") int step);
}
