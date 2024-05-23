package md.maib.retail.infrastructure.persistence;

import lombok.extern.slf4j.Slf4j;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SpringDataJpaProjectRepositoryAdapter implements Campaigns {

    private final SpringDataJpaCampaignRepository campaignRepository;
    public SpringDataJpaProjectRepositoryAdapter(SpringDataJpaCampaignRepository repository) {
        this.campaignRepository = repository;
    }

    @Override
    public List<Campaign> listByDate(LocalDate date) {
        List<CampaignEntity> entities = campaignRepository.findByDate(date);
        return entities.stream().map(CampaignEntity::toCampaign).collect(Collectors.toList());
    }

    @Override
    public Optional<Campaign> findById(CampaignId campaignId) {
        return campaignRepository.findById(campaignId.toUUID())
                .map(CampaignEntity::toCampaign);
    }

    @Override
    public List<Campaign> findByMetaInfo(String key, String value) {
        List<CampaignEntity> entities = campaignRepository.findByMetaInfo(key, value);
        return entities.stream().map(CampaignEntity::toCampaign).collect(Collectors.toList());
    }

    @Override
    public boolean add(Campaign campaign) {
        var entity = CampaignEntity.valueOf(campaign);

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
            campaignRepository.delete(CampaignEntity.valueOf(campaignOptional.get()));
            return true;
        }
        return false;
    }
}
