package md.maib.retail.infrastructure.rest;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import md.maib.retail.application.delete_campaign.DeleteCampaign;
import md.maib.retail.model.campaign.CampaignId;

@Getter
@RequiredArgsConstructor

@ToString
public class DeleteCampaignRequest    {
        private final CampaignId campaignId;

        public static DeleteCampaignRequest valueOf(DeleteCampaign deleteCampaign) {
            return new DeleteCampaignRequest( deleteCampaign.id());
        }
}
