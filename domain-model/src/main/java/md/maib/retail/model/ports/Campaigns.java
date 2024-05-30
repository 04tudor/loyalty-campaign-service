package md.maib.retail.model.ports;

import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface Campaigns {

    List<Campaign> listByDate(Instant date);

    Optional<Campaign> findById(CampaignId campaignId);

    List<Campaign> findByMetaInfo(String key,String value);

    boolean add(Campaign campaign);

    boolean delete(CampaignId campaignId);

}
