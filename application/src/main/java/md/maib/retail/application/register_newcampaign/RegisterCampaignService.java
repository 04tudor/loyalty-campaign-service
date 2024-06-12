package md.maib.retail.application.register_newcampaign;

import io.vavr.control.Either;
import md.maib.retail.application.CampaignEntity;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.conditions.Condition;
import md.maib.retail.model.conditions.Rule;
import md.maib.retail.model.conditions.RuleId;
import md.maib.retail.model.effects.Effect;
import md.maib.retail.model.ports.Campaigns;
import md.maib.retail.model.ports.LoyaltyEffectTypes;
import md.maib.retail.model.ports.LoyaltyEventTypes;
import org.threeten.extra.Interval;

import java.util.Objects;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class RegisterCampaignService implements RegistrationCampaignUseCase {
    private final Campaigns campaigns;
    private final LoyaltyEventTypes loyaltyEventTypes;
    private final LoyaltyEffectTypes loyaltyEffectTypes;



    public RegisterCampaignService(Campaigns campaigns, LoyaltyEventTypes loyaltyEventTypes, LoyaltyEffectTypes loyaltyEffectTypes) {
        this.campaigns = Objects.requireNonNull(campaigns, "Campaigns must not be null");
        this.loyaltyEventTypes = Objects.requireNonNull(loyaltyEventTypes, "loyaltyEventTypes must not be null");
        this.loyaltyEffectTypes = Objects.requireNonNull(loyaltyEffectTypes, "loyaltyEffectTypes must not be null");
    }

    @Override
    public Either<UseCaseProblemConflict, CampaignId> registerCampaign(RegisterCampaign command) {
        var id = CampaignId.newIdentity();
        var metaInfo = new CampaignMetaInfo(command.metaInfo().properties());
        var startInclusive = command.startInclusive();
        var endExclusive = command.endExclusive();
        var interval = Interval.of(startInclusive, endExclusive);
        var state = command.state();

        var loyaltyEventType = loyaltyEventTypes.findById(command.loyaltyEventType().id());
        var loyaltyEffectType = loyaltyEffectTypes.findById(command.loyaltyEffectType().id());

        if (loyaltyEventType.isEmpty() || loyaltyEffectType.isEmpty()) {
            return left(new UseCaseProblemConflict("Failed to retrieve LoyaltyEventType or LoyaltyEffectType"));
        }

        var rules = command.rules().stream()
                .map(rule -> {
                    var conditions = rule.getConditions().stream()
                            .map(condition -> new Condition(
                                    condition.getField(),
                                    condition.getOperator(),
                                    condition.getValue()
                            ))
                            .toList();
                    var effects = rule.getEffects().stream()
                            .map(effect -> new Effect(
                                    loyaltyEffectType.get(),
                                    effect.value()
                            ))
                            .toList();
                    return new Rule(RuleId.newIdentity(), conditions, effects);
                })
                .toList();

        Campaign campaign = CampaignEntity.valueOf(new CampaignEntity(id, metaInfo, interval, state, loyaltyEventType.get(), rules));

        if (campaigns.add(campaign)) {
            return right(id);
        }
        return left(new UseCaseProblemConflict("CampaignWithSameIdAlreadyExists"));
    }
}
