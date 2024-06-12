package md.maib.retail.infrastructure.persistence;

import lombok.extern.slf4j.Slf4j;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.ports.Campaigns;
import org.springframework.stereotype.Service;

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
    public Optional<Campaign> getById(CampaignId campaignId) {
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
        Optional<CampaignRecord> campaignOptional = campaignRepository.findById(campaignId.toUUID());
        if (campaignOptional.isPresent()) {
            campaignRepository.delete(CampaignRecord.valueOf(campaignOptional.get().toCampaign(true)));
            return true;
        }
        return false;
    }


    @Override
    public boolean save(Campaign campaign) {
        Optional<CampaignRecord> campaignRecordOptional = campaignRepository.findById(campaign.getId().toUUID());
        if (campaignRecordOptional.isPresent()) {
            CampaignRecord campaignRecord = CampaignRecord.valueOf(campaign);

            if (campaignRecord.isNew()) {
                campaignRecord.setNew(false);
            }
            campaignRepository.save(campaignRecord);
            return true;
        }
        return false;
    }

    @Override
    public List<Campaign> listAll() {
        List<CampaignRecord> entities = campaignRepository.findAll();
        return entities.stream()
                .map(entity -> entity.toCampaign(true))
                .toList();

    }


}
