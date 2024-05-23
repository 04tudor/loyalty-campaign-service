package md.maib.retail.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataJpaCampaignRepository extends JpaRepository<CampaignEntity, UUID> {

    @Query("")
    List<CampaignEntity> findByMetaInfo(@Param("key") String key, @Param("value") String value);

    @Query("")
    List<CampaignEntity> findByDate(@Param("date") LocalDate date);
}
