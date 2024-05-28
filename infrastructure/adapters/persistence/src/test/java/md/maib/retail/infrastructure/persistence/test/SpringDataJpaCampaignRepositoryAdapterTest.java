package md.maib.retail.infrastructure.persistence.test;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import jakarta.persistence.EntityManager;
import lombok.Data;
import md.maib.retail.infrastructure.persistence.CampaignRecord;
import md.maib.retail.infrastructure.persistence.RuleRecord;
import md.maib.retail.infrastructure.persistence.SpringDataJpaProjectRepositoryAdapter;
import md.maib.retail.model.campaign.CampaignId;
import md.maib.retail.model.campaign.FieldType;
import md.maib.retail.model.conditions.Condition;
import md.maib.retail.model.conditions.Operator;
import md.maib.retail.model.effects.Effect;
import md.maib.retail.model.effects.LoyaltyEffectType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@PersistenceTest
 class SpringDataJpaCampaignRepositoryAdapterTest {

    @Autowired
    SpringDataJpaProjectRepositoryAdapter repository;

    @Autowired
    EntityManager entityManager;

    @ExpectedDataSet("datasets/campaigns.yaml")
    @Test
    void insertion() {
        Map<String, Object> metaInfo = new HashMap<>();
        metaInfo.put("key","value");

        CampaignRecord campaignRecord = new CampaignRecord(
                UUID.fromString("1e7e7d50-9f9f-4b7c-bd9b-5f5f3d0f7f7f"),
                metaInfo,
                Instant.parse("2024-06-01T00:00:00Z"),
                Instant.parse("2024-06-30T23:59:59Z"),
                true,
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000")
        );

        repository.add(campaignRecord.toCampaign());

    }

    @DataSet(value = "datasets/campaigns.yaml")
    @Test
    void findById(){
        CampaignId campaignId= CampaignId.valueOf(UUID.fromString("1e7e7d50-9f9f-4b7c-bd9b-5f5f3d0f7f7f"));
        var campaign=repository.findById(campaignId);

        Assertions.assertNotNull(campaign);
        Assertions.assertEquals(campaignId, campaign.get().getId());

    }

}
