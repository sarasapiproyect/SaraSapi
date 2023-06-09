package com.sara.services.repository;

import com.sara.services.domain.DefaultResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DefaultResponse entity.
 *
 * When extending this class, extend DefaultResponseRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DefaultResponseRepository
    extends DefaultResponseRepositoryWithBagRelationships, JpaRepository<DefaultResponse, Long>, JpaSpecificationExecutor<DefaultResponse> {
    default Optional<DefaultResponse> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<DefaultResponse> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<DefaultResponse> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
