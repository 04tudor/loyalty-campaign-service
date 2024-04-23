package md.maib.retail.Model.Campaign;


import java.util.Map;
import static java.util.Objects.requireNonNull;

public record CampaignMetaInfo(Map<String, Object> properties) {

    public CampaignMetaInfo{
        requireNonNull(properties, "Properties must not be null.");    }
}
