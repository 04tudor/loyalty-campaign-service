package md.maib.retail.model;

import md.maib.retail.junit.UnitTest;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.campaign.Campaign;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@UnitTest
 class CampaignTest {

    @Test
    void twoCampaignsAreEquals() {
        var i1 = Interval.of(Instant.now(), Instant.now().plus(1, ChronoUnit.DAYS));
        var i2 = Interval.startingAt(Instant.now());
        EqualsVerifier.forClass(Campaign.class).withOnlyTheseFields("id").withPrefabValues(Interval.class, i1, i2).verify();
    }

    @Test
    void testBuildWithCampaignId() {
        var uuid = UUID.randomUUID();
        CampaignBuilder campaignBuilder = new CampaignBuilder(uuid);

        assertThat(uuid).withFailMessage("Campaign ID should be set correctly").isEqualTo(
                campaignBuilder.build().campaignId()
        );
    }

    @Test
    void cannotActivateAlreadyActiveCampaign() throws Exception {
        CampaignId campaignId = CampaignId.newIdentity();
        CampaignBuilder campaignBuilder = new CampaignBuilder(campaignId.campaignId());
        Campaign campaign = new Campaign(
                campaignId,
                null,
                null,
                CampaignState.DRAFT,
                null,
                null
        );

        boolean activationResult = campaign.activate();

        assertFalse(!activationResult, "Cannot activate already active campaign");
    }

    @Test
    void canActivateDraftCampaign() throws Exception {
        CampaignId campaignId = CampaignId.newIdentity();
        CampaignBuilder campaignBuilder = new CampaignBuilder(campaignId.campaignId());
        Campaign campaign = new Campaign(
                campaignId,
                null,
                null,
                CampaignState.DRAFT,
                null,
                null
        );

        boolean activationResult = campaign.activate();

        assertTrue(activationResult, "Can activate DRAFT campaign");
        assertEquals(CampaignState.ACTIVE, campaign.getState(), "Campaign state should be ACTIVE after activation");
    }
    @Test
    void testCampaign() throws Exception {
        Campaign campaign = new Campaign(
                CampaignId.newIdentity(),
                null,
                null,
                CampaignState.DRAFT,
                null,
                null
        );

        assertThat(campaign.getMetaInfo()).isNull();

        assertThat(campaign.activate()).isTrue();
        assertThat(campaign.getState()).isEqualTo(CampaignState.ACTIVE);

        assertThat(campaign.activate()).isFalse();
        assertThat(campaign.getState()).isEqualTo(CampaignState.ACTIVE);

        assertThat(campaign.getId()).isNotNull();

        CampaignId campaignId = CampaignId.valueOf(campaign.getId().campaignId());
        assertThat(campaignId).isEqualTo(campaign.getId());
    }

}

