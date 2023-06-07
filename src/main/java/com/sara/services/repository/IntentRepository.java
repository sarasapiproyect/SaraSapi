package com.sara.services.repository;

import com.sara.services.domain.Intent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Intent entity.
 *
 * When extending this class, extend IntentRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface IntentRepository
    extends IntentRepositoryWithBagRelationships, JpaRepository<Intent, Long>, JpaSpecificationExecutor<Intent> {
    default Optional<Intent> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Intent> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Intent> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct intent from Intent intent left join fetch intent.languaje",
        countQuery = "select count(distinct intent) from Intent intent"
    )
    Page<Intent> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct intent from Intent intent left join fetch intent.languaje")
    List<Intent> findAllWithToOneRelationships();

    @Query("select intent from Intent intent left join fetch intent.languaje where intent.id =:id")
    Optional<Intent> findOneWithToOneRelationships(@Param("id") Long id);

    @Query(
        value = "SELECT * FROM SaraBusinessInteligente.intent i WHERE i.id in (SELECT ue.intent_id FROM SaraBusinessInteligente.rel_intent__user_expresion ue WHERE ue.user_expresion_id=:id);",
        nativeQuery = true
    )
    List<Intent> getIntentByUserResponse(@Param("id") Long id);
}
