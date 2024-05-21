package md.maib.retail.infrastructure.rest;

import lombok.Setter;
import md.maib.retail.application.LoyaltyEventsFindById.LoyaltyEffectType.EffectRecord;
import md.maib.retail.application.find_event_type_by_id.FindByIdLoyaltyEventTypeUseCase;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.conditions.Rule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Setter
public record RegisterCampaignRequest(
        CampaignMetaInfo metaInfo,
        LocalDate startInclusive,
        LocalDate endExclusive,
        CampaignState state,
        String loyaltyEventTypeId,
        List<Rule> rules,
        EffectRecord effects
) {
    public Optional<LoyaltyEventType> retrieveLoyaltyEventType(FindByIdLoyaltyEventTypeUseCase findByIdLoyaltyEventTypeUseCase) {
        return findByIdLoyaltyEventTypeUseCase.findById(loyaltyEventTypeId);
    }
}
