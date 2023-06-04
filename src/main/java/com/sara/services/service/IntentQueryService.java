package com.sara.services.service;

import com.sara.services.domain.*; // for static metamodels
import com.sara.services.domain.Intent;
import com.sara.services.repository.IntentRepository;
import com.sara.services.service.criteria.IntentCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Intent} entities in the database.
 * The main input is a {@link IntentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Intent} or a {@link Page} of {@link Intent} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IntentQueryService extends QueryService<Intent> {

    private final Logger log = LoggerFactory.getLogger(IntentQueryService.class);

    private final IntentRepository intentRepository;

    public IntentQueryService(IntentRepository intentRepository) {
        this.intentRepository = intentRepository;
    }

    /**
     * Return a {@link List} of {@link Intent} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Intent> findByCriteria(IntentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Intent> specification = createSpecification(criteria);
        return intentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Intent} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Intent> findByCriteria(IntentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Intent> specification = createSpecification(criteria);
        return intentRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IntentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Intent> specification = createSpecification(criteria);
        return intentRepository.count(specification);
    }

    /**
     * Function to convert {@link IntentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Intent> createSpecification(IntentCriteria criteria) {
        Specification<Intent> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Intent_.id));
            }
            if (criteria.getIntenType() != null) {
                specification = specification.and(buildSpecification(criteria.getIntenType(), Intent_.intenType));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Intent_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Intent_.description));
            }
            if (criteria.getUrlRequest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlRequest(), Intent_.urlRequest));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), Intent_.enabled));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Intent_.creationDate));
            }
            if (criteria.getLanguajeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getLanguajeId(), root -> root.join(Intent_.languaje, JoinType.LEFT).get(Language_.id))
                    );
            }
            if (criteria.getUserExpresionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserExpresionId(),
                            root -> root.join(Intent_.userExpresions, JoinType.LEFT).get(UserExpresion_.id)
                        )
                    );
            }
            if (criteria.getUserResponseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserResponseId(),
                            root -> root.join(Intent_.userResponses, JoinType.LEFT).get(UserResponse_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
