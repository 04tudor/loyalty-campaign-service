package md.maib.retail.infrastructure.persistence.test;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import jakarta.persistence.EntityManager;
import md.maib.retail.infrastructure.persistence.CampaignRecord;
import md.maib.retail.infrastructure.persistence.SpringDataJpaProjectRepositoryAdapter;
import md.maib.retail.model.campaign.Campaign;
import md.maib.retail.model.campaign.CampaignId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@PersistenceTest
 class SpringDataJpaCampaignRepositoryAdapterTest {

    @Autowired
    SpringDataJpaProjectRepositoryAdapter repository;

    @Autowired
    EntityManager entityManager;

    @ExpectedDataSet("datasets/campaign.yaml")
    @Test
    void insertion() {
        Map<String, Object> metaInfo = new HashMap<>();
        metaInfo.put("key", "value");

        CampaignRecord campaignRecord = new CampaignRecord(
                UUID.fromString("1e7e7d50-9f9f-4b7c-bd9b-5f5f3d0f7f7f"),
                metaInfo,
                Instant.parse("2024-06-01T00:00:00Z"),
                Instant.parse("2024-06-30T23:59:59Z"),
                true,
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000")

        );

//        Collection<Condition> conditions = new ArrayList<>();
//        conditions.add(new Condition(FieldType.DECIMAL, Operator.GREATER, "10"));
//
//        List<EffectRecord> effectRecords = List.of(new EffectRecord(UUID.fromString("4e1a8086-90de-4796-95e8-121f24412656"), "5"));
//
//        RuleRecord ruleRecord = new RuleRecord(
//                UUID.fromString("44947ade-923d-4ca6-9006-30442779df3f"),
//                campaignRecord,
//                conditions,
//                effectRecords
//        );

        repository.add(campaignRecord.toCampaign());

    }

    @DataSet("/datasets/campaigns.yaml")
    @Test
    void findById() {
        CampaignId campaignId = CampaignId.valueOf(UUID.fromString("1e7e7d50-9f9f-4b7c-bd9b-5f5f3d0f7f7f"));
        Optional<Campaign> optionalCampaign = repository.findById(campaignId);

        assertTrue(optionalCampaign.isPresent(), "Campaign not found");

        Campaign campaign = optionalCampaign.get();
        Assertions.assertEquals(campaignId, campaign.getId());
    }

    @Test
    @DataSet("/datasets/campaigns.yaml")
    void deleteCampaignRecord() {
        CampaignId campaignId = CampaignId.valueOf(UUID.fromString("1e7e7d50-9f9f-4b7c-bd9b-5f5f3d0f7f7f"));

        boolean deleted = repository.delete(campaignId);


        assertTrue(deleted, "Campaign record was not deleted");

    }

    @Test
    @DataSet("/datasets/campaigns.yaml")
     void testFindByMetaInfo() {
        String key = "key";
        String value = "value";

        List<Campaign> campaigns = repository.findByMetaInfo(key, value);

        assertThat(campaigns).isNotEmpty();
        assertThat(campaigns.size()).isEqualTo(1);

    }
    @Test
    @DataSet("/datasets/campaigns.yaml")
     void testFindByLocalDate() {


        List<Campaign> campaigns = repository.listByDate(LocalDate.of(2024,6,8));

        assertThat(campaigns).isNotEmpty();
        assertThat(campaigns.size()).isEqualTo(1);

    }
}
