import md.maib.retail.Model.Campaign.CampaignId;
import md.maib.retail.Model.Campaign.CampaignMetaInfo;

import java.util.UUID;

public class TestCampaignBuilder {

    private CampaignId campaignId=CampaignId.newIdentity();

    public static TestCampaignBuilder randomCampaign() {
        return new TestCampaignBuilder();
    }
    public TestCampaignBuilder withCampaignId(UUID projectId) {
        this.campaignId = CampaignId.valueOf(projectId);
        return this;
    }

    public static void main(String[] args) {
        TestCampaignBuilder randomBuilder = TestCampaignBuilder.randomCampaign();
        System.out.println(randomBuilder.campaignId);
    }
}
