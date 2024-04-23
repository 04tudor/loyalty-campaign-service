package md.maib.retail.model.junit;

import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.CampaignState;
import md.maib.retail.model.conditions.Rule;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.threeten.extra.Interval;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
public class CampaignTest {

    @Test
    void twoCampaignsAreEquals() {
        var i1 = Interval.of(Instant.now(), Instant.now().plus(1, ChronoUnit.DAYS));
        var i2 = Interval.startingAt(Instant.now());
        EqualsVerifier.forClass(Campaign.class).withOnlyTheseFields("id").withPrefabValues(Interval.class, i1, i2).verify();
    }

    @Test
    void twoRulesAreEquals() {
        EqualsVerifier.forClass(Rule.class).withOnlyTheseFields("id").verify();
    }

    @Test
    void testBuildWithCampaignId() {
        var uuid = UUID.randomUUID();
        CampaignBuilder campaignBuilder = new CampaignBuilder(uuid);


        assertEquals(uuid, campaignBuilder.build().campaignId(), "Campaign ID should be set correctly");
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


}

