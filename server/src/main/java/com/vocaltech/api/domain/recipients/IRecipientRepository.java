package com.vocaltech.api.domain.recipients;

import com.vocaltech.api.domain.recipients.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IRecipientRepository extends JpaRepository<Recipient, UUID> {

    // Buscar todos los recipients de un tipo específico
    @Query("SELECT r FROM Recipient r WHERE TYPE(r) = :type")
    List<Recipient> findByRecipientType(@Param("type") Class<? extends Recipient> type);

    // Buscar todos los recipients asociados a una campaña específica
    @Query("SELECT r FROM Recipient r JOIN r.campaignRecipients cr WHERE cr.campaign.id = :campaignId")
    List<Recipient> findByCampaignId(@Param("campaignId") UUID campaignId);
}
