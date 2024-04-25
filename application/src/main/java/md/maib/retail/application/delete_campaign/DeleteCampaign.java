package md.maib.retail.application.delete_campaign;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import md.maib.retail.application.register_newcampaign.RegisterCampaign;
import md.maib.retail.model.campaign.CampaignId;

@Getter
@RequiredArgsConstructor
@ToString
public class DeleteCampaign {
    private final CampaignId id;

    private static final Validator<DeleteCampaign> validator = ValidatorBuilder
            .<DeleteCampaign>of()
            ._object(DeleteCampaign::id, "id", c -> c.notNull().message("Id must not be Null"))
            .build();


}
