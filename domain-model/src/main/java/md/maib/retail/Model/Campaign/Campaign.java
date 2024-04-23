package md.maib.retail.Model.Campaign;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import md.maib.retail.Model.Conditions.Rule;
import md.maib.retail.Model.Effects.LoyaltyEventType;
import org.threeten.extra.Interval;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

    private CampaignId id;

    private Map<String ,String>metaInfo;

    private Interval activityInterval;

    private CampaignState state;

    private LoyaltyEventType loyaltyEventType;

    private Collection<Rule> rules;


}
