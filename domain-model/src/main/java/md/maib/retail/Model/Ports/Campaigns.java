package md.maib.retail.Model.Ports;

import md.maib.retail.Model.Campaign.Campaign;
import md.maib.retail.Model.Campaign.CampaignId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface Campaigns {

    List<Campaign> listByDate(LocalDate date);

    Optional<Campaign> findById(CampaignId campaignId);

    Optional<Campaign> findByMetaInfo(String key,String value);

    boolean add(Campaign campaign);
}
