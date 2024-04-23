import md.maib.retail.Model.Campaign.CampaignId;

import java.util.UUID;

public class TestCampaignBuilder {

    private CampaignId campaignId=CampaignId.newIdentity();

    public static TestCampaignBuilder randomCampaign() {
        return new TestCampaignBuilder();
    }
    public TestCampaignBuilder withCampaignId(UUID campaignId) {
        this.campaignId = CampaignId.valueOf(campaignId);
        return this;
    }

    public static void main(String[] args) {
        TestCampaignBuilder randomBuilder = TestCampaignBuilder.randomCampaign();
        System.out.println(randomBuilder.campaignId);
    }
}
