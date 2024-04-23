package md.maib.retail.Model.Campaign;

import lombok.ToString;

import java.util.Map;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@ToString
public class CampaignMetaInfo {

    private final Map<String, String> properties;

    public CampaignMetaInfo(Map<String, String> properties) {
        this.properties = requireNonNull(properties, "Properties must not be null.");

    }

    public static CampaignMetaInfo valueOf(Map<String, String> properties) {
        return new CampaignMetaInfo(properties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CampaignMetaInfo that = (CampaignMetaInfo) o;
        return Objects.equals(properties, that.properties);
    }
}
