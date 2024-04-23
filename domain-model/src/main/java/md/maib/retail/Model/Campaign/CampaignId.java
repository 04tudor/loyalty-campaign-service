package md.maib.retail.Model.Campaign;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Objects;
import java.util.UUID;
import static java.util.Objects.requireNonNull;
import static java.util.UUID.randomUUID;
@ToString
@Getter
@Setter
public final class CampaignId {
  private final  UUID campaignId;
    public CampaignId (UUID campaignId){
        this.campaignId = requireNonNull(campaignId, "The value must not be null.");
    }

    public static CampaignId newIdentity() {
        return new CampaignId(randomUUID());
    }

    public static CampaignId valueOf(UUID uuid) {
        return new CampaignId(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CampaignId that = (CampaignId) o;
        return Objects.equals(campaignId, that.campaignId);
    }


}
