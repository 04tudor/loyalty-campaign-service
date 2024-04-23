package md.maib.retail.dto;

import md.maib.retail.Model.Campaign.CampaignState;
import md.maib.retail.Model.Conditions.Rule;
import org.threeten.extra.Interval;

import java.util.Collection;
import java.util.Map;

public record NewCampaignDto(
         Map<String ,String> metaInfo,

         Interval activityInterval,

         CampaignState state,

         Collection<Rule>rules

) {
}
