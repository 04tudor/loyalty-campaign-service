package md.maib.retail.infrastructure.persistence;

import md.maib.retail.model.campaign.CampaignId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaCampaignRepository extends JpaRepository<CampaignRecord, CampaignId> {
}
