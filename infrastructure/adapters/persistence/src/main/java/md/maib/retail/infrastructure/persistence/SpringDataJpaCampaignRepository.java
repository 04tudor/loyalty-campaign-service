package md.maib.retail.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataJpaCampaignRepository extends JpaRepository<CampaignRecord, UUID> {

    @Query("""
            SELECT DISTINCT c FROM CampaignRecord c
            JOIN FETCH c.rules rules
            WHERE JSONB_EXTRACT_PATH_TEXT(c.metaInfo, :key) = :value
            ORDER BY c.id ASC, rules.id ASC
            """)
    List<CampaignRecord> findByMetaInfo(@Param("key") String key, @Param("value") String value);

    @Query("""
            SELECT DISTINCT c FROM CampaignRecord c
            JOIN FETCH c.rules rules
            WHERE c.startInclusive <= :date AND (c.endExclusive IS NULL OR c.endExclusive > :date)
            AND c.isActive = true
            ORDER BY c.id ASC, rules.id ASC
            """)
    List<CampaignRecord> findByDate(@Param("date") Instant date);

}
