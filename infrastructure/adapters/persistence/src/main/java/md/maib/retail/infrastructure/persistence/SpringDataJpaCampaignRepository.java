package md.maib.retail.infrastructure.persistence;

import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Repository
public interface SpringDataJpaCampaignRepository extends JpaRepository<CampaignRecord, CampaignId> {
    @Query("SELECT c FROM Campaigns c WHERE :date BETWEEN c.activityInterval.start AND c.activityInterval.end AND c.state = :state")
    Collection<CampaignRecord> findActiveByDate(@Param("date") Instant date, @Param("state") CampaignState state);

    @Query(value = "SELECT * FROM Campaigns c WHERE c.metaInfo ->> :key = :value", nativeQuery = true)
    List<CampaignRecord> findByMetaInfo(@Param("key") String key, @Param("value") String value);

}
