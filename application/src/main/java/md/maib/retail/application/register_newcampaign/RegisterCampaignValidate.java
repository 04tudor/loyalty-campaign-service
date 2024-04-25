package md.maib.retail.application.register_newcampaign;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.conditions.Rule;

import java.time.LocalDate;
import java.util.List;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@Getter
@RequiredArgsConstructor  // Removed AccessLevel.PRIVATE
@ToString
public final class RegisterCampaignValidate {
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

    private static final Validator<RegisterCampaignValidate> validator = ValidatorBuilder
            .<RegisterCampaignValidate>of()
            ._object(RegisterCampaignValidate::metaInfo, "metaInfo", c -> c.notNull().message(BLANK))
            .constraint(RegisterCampaignValidate::startInclusive, "startInclusive", c -> c.notNull().message(NOTNULL))
            .constraint(RegisterCampaignValidate::endExclusive, "endExclusive", c -> c.notNull().message(NOTNULL))
            .constraintOnTarget(RegisterCampaignValidate::validateRange,
                    "startInclusive",
                    "startInclusive.isBeforeEndExclusive",
                    "\"startInclusive\" must be before \"endExclusive\"")
            ._object(RegisterCampaignValidate::loyaltyEventType, "loyaltyEventType", c -> c.notNull().message(NOTNULL))
            ._collection(RegisterCampaignValidate::rules, "rules", c -> c.notNull().message(NOTNULL))
            .build();


    public static Either<ConstraintViolations, RegisterCampaignValidate> create(CampaignMetaInfo metaInfo, LocalDate startInclusive, LocalDate endExclusive, CampaignState state, LoyaltyEventType loyaltyEventType, List<Rule> rules) {
        var command = new RegisterCampaignValidate(metaInfo, startInclusive, endExclusive, state, loyaltyEventType, rules);
        var violations = validator.validate(command);
        return violations.isValid() ? right(command) : left(violations);
    }
}
