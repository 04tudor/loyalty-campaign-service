package md.maib.retail.model.campaign;


import java.util.Map;


import static java.util.Objects.requireNonNull;

public record CampaignMetaInfo(Map<String, Object> properties) {

    public CampaignMetaInfo{
        requireNonNull(properties, "Properties must not be null.");    }
    public static CampaignMetaInfo valueOf(Map<String, Object> properties) {
        return new CampaignMetaInfo(properties);
    }

}
