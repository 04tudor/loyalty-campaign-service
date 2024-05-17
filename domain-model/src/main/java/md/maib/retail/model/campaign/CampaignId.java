package md.maib.retail.model.campaign;

import java.util.UUID;
import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;

public record CampaignId (UUID campaignId){

    public CampaignId {
        requireNonNull(campaignId, "Campaign Id must not be null.");
    }

    public static CampaignId newIdentity() {
        return new CampaignId(randomUUID());
    }

    public static CampaignId valueOf(UUID value) {
        return new CampaignId(value);
    }
    public static String stringvalueOf(CampaignId campaignId1) {
        return String.valueOf(campaignId1);
    }
}
