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

        private CampaignMetaInfo metaInfo;

        private Interval activityInterval;

        private CampaignState state;

        private LoyaltyEventType loyaltyEventType;

        private Collection<Rule> rules;

        public boolean activate() throws Exception {
            if (state == CampaignState.DRAFT) {
                this.state = CampaignState.ACTIVE;
                return true;
            } else {
                return false;
            }        }

        public boolean draft() throws Exception {
            if (state == CampaignState.ACTIVE) {
                this.state = CampaignState.DRAFT;
                return true;
            } else {
                return false;
            }        }
    }
