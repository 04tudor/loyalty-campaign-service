package md.maib.retail.infrastructure.persistence.test;

import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import jakarta.persistence.EntityManager;
import md.maib.retail.infrastructure.persistence.CampaignRecord;
import md.maib.retail.infrastructure.persistence.SpringDataJpaProjectRepositoryAdapter;
import md.maib.retail.model.campaign.LoyaltyEventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@PersistenceTest
 class SpringDataJpaCampaignRepositoryAdapterTest {

    @Autowired
    SpringDataJpaProjectRepositoryAdapter repository;

    @Autowired
    EntityManager entityManager;

    @ExpectedDataSet("datasets/campaign.yaml")
    @Test
    void insertion() {
        var campaign = new CampaignRecord(
                UUID.fromString("ee5dead7-1e18-4a89-aeb9-8c6bd8f3e262"),
                Map.of("Key", "value"),
                LocalDate.of(2020, 2, 4),
                LocalDate.of(2020, 3, 5),
                true,
                new LoyaltyEventType("23fcf3d1-9d50-426e-ae01-a23076eff31f")
        );

        repository.add(campaign.toCampaign());
    }
}
