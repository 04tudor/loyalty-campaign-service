package md.maib.retail.application.register_newcampaign;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import io.vavr.control.Either;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import md.maib.retail.application.find_effect_type_by_id.EffectTypeRecord;
import md.maib.retail.application.find_effect_type_by_id.FindByIdLoyaltyEffectTypeUseCase;
import md.maib.retail.application.find_event_type_by_id.EventTypeRecord;
import md.maib.retail.application.find_event_type_by_id.FindByIdLoyaltyEventTypeUseCase;
import md.maib.retail.model.campaign.CampaignMetaInfo;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.LoyaltyEventType;
import md.maib.retail.model.conditions.Rule;
import md.maib.retail.model.effects.LoyaltyEffectType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@Getter
@RequiredArgsConstructor
@ToString
public final class RegisterCampaign {
    private final CampaignMetaInfo metaInfo;
    private final LocalDate startInclusive;
    private final LocalDate endExclusive;
    private final CampaignState state;
    private final EventTypeRecord loyaltyEventType;
    private final List<Rule> rules;
    private final EffectTypeRecord loyaltyEffectType;

    private static final String NOTNULL = "must not be null";
    private static final String ACTIVE = "must be ACTIVE";

    private boolean validateRange() {
        return startInclusive != null && endExclusive != null && startInclusive.isBefore(endExclusive);
    }

    private static final Validator<RegisterCampaign> validator = ValidatorBuilder
            .<RegisterCampaign>of()
            ._object(RegisterCampaign::metaInfo, "metaInfo", c -> c.notNull().message(NOTNULL))
            ._object(RegisterCampaign::startInclusive, "startInclusive", c -> c.notNull().message(NOTNULL))
            ._object(RegisterCampaign::endExclusive, "endExclusive", c -> c.notNull().message(NOTNULL))
            .constraintOnTarget(RegisterCampaign::validateRange,
                    "startInclusive",
                    "startInclusive.isBeforeEndExclusive",
                    "\"startInclusive\" must be before \"endExclusive\"")
            ._object(RegisterCampaign::state, "state", c -> c.equalTo(CampaignState.ACTIVE).message("Campaign " + ACTIVE))
            ._object(RegisterCampaign::loyaltyEventType, "loyaltyEventType", c -> c.notNull().message(NOTNULL))
            ._object(RegisterCampaign::loyaltyEffectType, "loyaltyEffectType", c -> c.notNull().message(NOTNULL))
            ._collection(RegisterCampaign::rules, "rules", c -> c.notNull().message(NOTNULL))
            .build();

    public static Either<ConstraintViolations, RegisterCampaign> create(CampaignMetaInfo metaInfo, LocalDate startInclusive, LocalDate endExclusive, CampaignState state, EventTypeRecord loyaltyEventType, List<Rule> rules, EffectTypeRecord loyaltyEffectType) {

        var command = new RegisterCampaign(metaInfo, startInclusive, endExclusive, state, loyaltyEventType, rules, loyaltyEffectType);
        var violations = validator.validate(command);
        return violations.isValid() ? right(command) : left(violations);
    }

    public Optional<LoyaltyEventType> retrieveLoyaltyEventType(FindByIdLoyaltyEventTypeUseCase findByIdLoyaltyEventTypeUseCase) {
        if (loyaltyEventType == null || loyaltyEventType.id() == null) {
            return Optional.empty();
        }
        return findByIdLoyaltyEventTypeUseCase.findById(loyaltyEventType.id());
    }

    public Optional<LoyaltyEffectType> retrieveLoyaltyEffectType(FindByIdLoyaltyEffectTypeUseCase findByIdLoyaltyEffectTypeUseCase) {
        if (loyaltyEffectType == null || loyaltyEffectType.id() == null) {
            return Optional.empty();
        }
        return findByIdLoyaltyEffectTypeUseCase.findById(loyaltyEffectType.id());
    }
}
