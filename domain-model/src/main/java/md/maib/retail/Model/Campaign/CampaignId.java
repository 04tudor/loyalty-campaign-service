package md.maib.retail.Model.Campaign;

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
    public static CampaignId valueOf(UUID uuid) {
        return new CampaignId(uuid);
    }



}
