package md.maib.retail.application.register_newcampaign;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.conditions.Rule;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public final class RegisterCampaign {
    private final CampaignMetaInfo metaInfo;
    private final LocalDate startInclusive;
    private final LocalDate endExclusive;
    private final CampaignState state;
    private final LoyaltyEventType loyaltyEventType;
    private final List<Rule> rules;

    private static final String BLANK = "must not be blank";
    private static final String NOTNULL = "must not be null";
    private static final String ACTIVE = "must  be ACTIVE";

    private boolean validateRange() {
        if (startInclusive == null || endExclusive == null) {
            return true;
        }
        return startInclusive.isBefore(endExclusive);
    }

    private static final Validator<RegisterCampaign> validator = ValidatorBuilder
            .<RegisterCampaign>of()
            ._object(RegisterCampaign::metaInfo, "metaInfo", c -> c.notNull().message(NOTNULL))
            .constraint(RegisterCampaign::startInclusive, "startInclusive", c -> c.notNull().message(NOTNULL))
            .constraint(RegisterCampaign::endExclusive, "endExclusive", c -> c.notNull().message(NOTNULL))
            .constraintOnTarget(RegisterCampaign::validateRange,
                    "startInclusive",
                    "startInclusive.isBeforeEndExclusive",
                    "\"startInclusive\" must be before \"endExclusive\"")
            ._object(RegisterCampaign::loyaltyEventType, "loyaltyEventType", c -> c.notNull().message(NOTNULL))
            ._collection(RegisterCampaign::rules, "rules", c -> c.notNull().message(NOTNULL))
            .build();



}