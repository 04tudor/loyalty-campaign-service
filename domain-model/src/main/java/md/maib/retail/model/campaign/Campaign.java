        package md.maib.retail.model.campaign;

        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import md.maib.retail.model.conditions.Rule;
        import org.threeten.extra.Interval;
        import java.util.Collection;


        @Getter
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

        }
