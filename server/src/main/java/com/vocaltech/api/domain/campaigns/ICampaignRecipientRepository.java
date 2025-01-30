package com.vocaltech.api.domain.campaigns;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICampaignRecipientRepository extends JpaRepository<CampaignRecipient, UUID> {

    @Query("SELECT cr FROM CampaignRecipient cr WHERE cr.nextEmailDate <= CURRENT_DATE")
    List<CampaignRecipient> findPendingEmails();
}
