package md.maib.retail.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.ports.Campaigns;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class SpringDataJpaCampaignRepositoryAdapter implements Campaigns {
    private final SpringDataJpaCampaignRepository repository;

    @Override
    public List<Campaign> listByDate(LocalDate date) {
        Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Collection<CampaignRecord> campaignRecords = repository.findActiveByDate(instant, CampaignState.ACTIVE);

        return campaignRecords.stream()
                .map(CampaignRecord::toCampaign)
                .collect(Collectors.toList());
    }

    @Override
    public List<Campaign> findByMetaInfo(String key, String value) {
        List<CampaignRecord> campaignRecords = repository.findByMetaInfo(key, value);
        return campaignRecords.stream()
                .map(CampaignRecord::toCampaign)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Campaign> findById(CampaignId campaignId) {
        return repository.findById(campaignId)
                .map(CampaignRecord::toCampaign);
    }


    @Override
    public boolean add(Campaign campaign) {
        if (campaign != null) {
            repository.save(CampaignRecord.valueOf(campaign));
            return true;
        }
        return false;    }

    @Override
    public boolean delete(CampaignId campaignId) {

        Optional<Campaign> optionalCampaign = findById(campaignId);

        if (optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            repository.delete(CampaignRecord.valueOf(campaign));
            return true;
        }

        return false;
    }
}
