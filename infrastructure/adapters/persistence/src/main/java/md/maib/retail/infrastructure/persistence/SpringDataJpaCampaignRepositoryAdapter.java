package md.maib.retail.infrastructure.persistence;

import lombok.extern.slf4j.Slf4j;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SpringDataJpaCampaignRepositoryAdapter implements Campaigns {

    private final SpringDataJpaCampaignRepository campaignRepository;

    public SpringDataJpaCampaignRepositoryAdapter(SpringDataJpaCampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public List<Campaign> listByDate(Instant date) {
        List<CampaignRecord> entities = campaignRepository.findByDate(date);
        return entities.stream()
                .map(entity -> entity.toCampaign(true))
                .toList();
    }

    @Override
    public Optional<Campaign> findById(CampaignId campaignId) {
        return campaignRepository.findById(campaignId.toUUID())
                .map(entity -> entity.toCampaign(true));
    }


    @Override
    public List<Campaign> findByMetaInfo(String key, String value) {
        List<CampaignRecord> entities = campaignRepository.findByMetaInfo(key, value);
        return entities.stream()
                .map(entity -> entity.toCampaign(false))
                .toList();
    }

    @Override
    public boolean add(Campaign campaign) {
        var entity = CampaignRecord.valueOf(campaign);
        try {
            campaignRepository.saveAndFlush(entity);
        } catch (Exception e) {
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

    @Override
    @Transactional
    public boolean activate(Campaign campaign) {
        Optional<CampaignRecord> campaignRecordOptional = campaignRepository.findById(campaign.getId().toUUID());
        if (campaignRecordOptional.isPresent()) {
            CampaignRecord campaignRecord = campaignRecordOptional.get();
            return campaignRecord.activate();
        }
        return false;
    }

    @Override
    public boolean save(Campaign campaign) {
        Optional<CampaignRecord> campaignRecordOptional = campaignRepository.findById(campaign.getId().toUUID());
        if (campaignRecordOptional.isPresent()) {
            CampaignRecord campaignRecord = campaignRecordOptional.get();
            if (campaignRecord.activate()) {
                campaignRepository.save(campaignRecord);
                return true;
            }
        }
        return false;
    }

}
