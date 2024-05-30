package md.maib.retail.infrastructure.persistence;

import lombok.extern.slf4j.Slf4j;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.conditions.Rule;
import md.maib.retail.model.effects.Effect;
import md.maib.retail.model.ports.Campaigns;
import org.springframework.stereotype.Service;

import java.time.Instant;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SpringDataJpaCampaignRepositoryAdapter implements Campaigns {

    private final SpringDataJpaCampaignRepository campaignRepository;
    private final LoyaltyEffectTypesAdapter loyaltyEffectTypesAdapter;

    public SpringDataJpaCampaignRepositoryAdapter(SpringDataJpaCampaignRepository campaignRepository, LoyaltyEffectTypesAdapter loyaltyEffectTypesAdapter) {
        this.campaignRepository = campaignRepository;
        this.loyaltyEffectTypesAdapter = loyaltyEffectTypesAdapter;
    }

    @Override
    public List<Campaign> listByDate(Instant date) {

        List<CampaignRecord> entities = campaignRepository.findByDate(date);
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

    public Effect toEffect(EffectRecord effectRecord) {
        return EffectRecord.toEffect(effectRecord, loyaltyEffectTypesAdapter);
    }

    public Rule convertToRule(RuleRecord ruleRecord) {
        return RuleRecord.convertToRule(ruleRecord, loyaltyEffectTypesAdapter);
    }

}
