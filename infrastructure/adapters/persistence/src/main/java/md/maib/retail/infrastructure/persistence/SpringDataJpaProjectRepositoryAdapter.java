package md.maib.retail.infrastructure.persistence;

import lombok.extern.slf4j.Slf4j;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SpringDataJpaProjectRepositoryAdapter implements Campaigns {

    private final SpringDataJpaCampaignRepository campaignRepository;
    public SpringDataJpaProjectRepositoryAdapter(SpringDataJpaCampaignRepository repository) {
        this.campaignRepository = repository;
    }

    @Override
    public List<Campaign> listByDate(LocalDate date) {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

        List<CampaignRecord> entities = campaignRepository.findByDate(instant);
        return entities.stream().map(CampaignRecord::toCampaign).toList();
    }

    @Override
    public Optional<Campaign> findById(CampaignId campaignId) {

        return campaignRepository.findById(campaignId.toUUID())
                .map(CampaignRecord::toCampaign);
    }

    @Override
    public List<Campaign> findByMetaInfo(String key, String value) {
        List<CampaignRecord> entities = campaignRepository.findByMetaInfo(key, value);
        return entities.stream().map(CampaignRecord::toCampaign).toList();
    }

    @Override
    public boolean add(Campaign campaign) {
        var entity = CampaignRecord.valueOf(campaign);

        try {
            campaignRepository.saveAndFlush(entity);
        } catch (Exception e ) {
            e.printStackTrace();
                return false;
        }
        return true;
    }

    public boolean delete(CampaignId campaignId) {
        Optional<Campaign> campaignOptional = findById(campaignId);
        if (campaignOptional.isPresent()) {
            campaignRepository.delete(CampaignRecord.valueOf(campaignOptional.get()));
            return true;
        }
        return false;
    }
}
