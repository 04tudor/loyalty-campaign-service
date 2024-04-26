package md.maib.retail.application.delete_campaign;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import io.vavr.control.Either;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import md.maib.retail.model.campaign.CampaignId;

import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@Getter
@RequiredArgsConstructor
@ToString
public class DeleteCampaign {
    private final CampaignId id;

    private static final Validator<DeleteCampaign> validator = ValidatorBuilder
            .<DeleteCampaign>of()
            ._object(DeleteCampaign::id, "id", c -> c.notNull().message("Id must not be Null"))
            .build();

    public  Either<ConstraintViolations, DeleteCampaign> create(CampaignId id) {
        var command = new DeleteCampaign(id);
        var violations = validator.validate(command);
        return violations.isValid() ? right(command) : left(violations);
    }
}
