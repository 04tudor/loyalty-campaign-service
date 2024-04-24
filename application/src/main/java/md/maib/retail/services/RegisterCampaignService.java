package md.maib.retail.services;

import io.vavr.control.Either;
import md.maib.retail.UseCaseProblemConflict;
import md.maib.retail.manage.RegisterCampaign;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.*;
import md.maib.retail.model.ports.Campaigns;
import md.maib.retail.usecase.RegistrationCampaignUseCase;
import org.threeten.extra.Interval;

import java.time.ZoneOffset;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

public class RegisterCampaignService implements RegistrationCampaignUseCase {
    Campaigns campaigns;

    public RegisterCampaignService(Campaigns campaigns) {
        this.campaigns=campaigns;
    }


    @Override
    public Either<UseCaseProblemConflict, CampaignId> registerCampaign(RegisterCampaign command) {
        var id = CampaignId.newIdentity();
        var metaInfo = new CampaignMetaInfo(command.metaInfo().properties());
        var startInclusive = command.startInclusive().atStartOfDay(ZoneOffset.UTC).toInstant();
        var endExclusive = command.endExclusive().atStartOfDay(ZoneOffset.UTC).toInstant();
        var interval = Interval.of(startInclusive, endExclusive);
        var state = command.state();
        var loyaltyEventType = command.loyaltyEventType();
        var rules = command.rules().stream()
                .map(rule -> {
                    var conditions = rule.getConditions().stream()
                            .map(condition -> {
                                var field = FieldType.valueOf(condition.getValue());
                                var operator = Operator.valueOf(String.valueOf(condition.getOperator()));
                                var value = String.valueOf(condition.getValue());
                                return new Condition(
                                        ConditionId.newIdentity(),
                                        field,
                                        operator,
                                        condition.getValue()
                                );
                            })
                            .toList();
                    return new Rule(RuleId.newIdentity().campaignId(), conditions, null);
                })
                .toList();

        var campaign = new Campaign(id, metaInfo, interval,state,loyaltyEventType, rules);

        if (campaigns.add(campaign)) {
           
            return right(id);
        }
        return left(new UseCaseProblemConflict("CampaignWithSameNameAlreadyExists"));
    }

}
