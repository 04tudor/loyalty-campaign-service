package md.maib.retail.infrastructure.persistence.test;

import md.maib.retail.infrastructure.persistence.CampaignRecord;
import md.maib.retail.infrastructure.persistence.SpringDataJpaCampaignRepository;
import md.maib.retail.infrastructure.persistence.SpringDataJpaCampaignRepositoryAdapter;
import md.maib.retail.model.campaign.*;
import md.maib.retail.model.conditions.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.threeten.extra.Interval;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SpringDataJpaCampaignRepositoryAdapterTest {

    @Mock
    private SpringDataJpaCampaignRepository repository;

    @InjectMocks
    private SpringDataJpaCampaignRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListByDate() {
        CampaignRecord record = createTestCampaignRecord();
        Instant instant = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
        when(repository.findActiveByDate(any(), eq(CampaignState.ACTIVE))).thenReturn(Collections.singletonList(record));

        List<Campaign> result = adapter.listByDate(LocalDate.now());

        assertEquals(1, result.size());
        assertEquals(record.toCampaign(), result.get(0));
    }

    @Test
    void testFindByMetaInfo() {
        CampaignRecord record = createTestCampaignRecord();
        when(repository.findByMetaInfo("key", "value")).thenReturn(Collections.singletonList(record));

        List<Campaign> result = adapter.findByMetaInfo("key", "value");

        assertEquals(1, result.size());
        assertEquals(record.toCampaign(), result.get(0));
    }

    @Test
    void testFindById() {
        CampaignRecord record = createTestCampaignRecord();
        CampaignId id=record.getId();
        when(repository.findById(id)).thenReturn(Optional.of(record));

        Optional<Campaign> result = adapter.findById(id);

        assertTrue(result.isPresent());
        assertEquals(record.toCampaign(), result.get());
    }

    @Test
    void testAddCampaign() {
        Campaign campaign = createTestCampaignRecord().toCampaign();
        when(repository.save(any(CampaignRecord.class))).thenReturn(CampaignRecord.valueOf(campaign));

        boolean result = adapter.add(campaign);

        assertTrue(result);
        verify(repository, times(1)).save(any(CampaignRecord.class));
    }

    @Test
    void testDeleteCampaign() {
        CampaignRecord record = createTestCampaignRecord();
        CampaignId id=record.getId();

        when(repository.findById(id)).thenReturn(Optional.of(record));
        doNothing().when(repository).delete(any(CampaignRecord.class));

        boolean result = adapter.delete(id);

        assertTrue(result);
        verify(repository, times(1)).delete(any(CampaignRecord.class));
    }

    private CampaignRecord createTestCampaignRecord() {
        CampaignId id = CampaignId.newIdentity();
        CampaignMetaInfo metaInfo = new CampaignMetaInfo(Collections.singletonMap("key", "value"));
        Interval interval = Interval.of(Instant.now(), Instant.now().plusSeconds(3600));
        CampaignState state = CampaignState.ACTIVE;
        List<LoyaltyEventField> fields= new ArrayList<>(Collections.emptyList());
        fields.add(new LoyaltyEventField(UUID.randomUUID(),"test",FieldType.STRING));
        LoyaltyEventType loyaltyEventType = new LoyaltyEventType(UUID.randomUUID(),"test",fields);
        Collection<Rule> rules = Collections.emptyList();

        return new CampaignRecord(id, metaInfo, interval, state, loyaltyEventType, rules);
    }
}
