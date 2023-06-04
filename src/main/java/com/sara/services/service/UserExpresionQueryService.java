package com.sara.services.service;

import com.sara.services.domain.*; // for static metamodels
import com.sara.services.domain.UserExpresion;
import com.sara.services.repository.UserExpresionRepository;
import com.sara.services.service.criteria.UserExpresionCriteria;
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
 * Service for executing complex queries for {@link UserExpresion} entities in the database.
 * The main input is a {@link UserExpresionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserExpresion} or a {@link Page} of {@link UserExpresion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserExpresionQueryService extends QueryService<UserExpresion> {

    private final Logger log = LoggerFactory.getLogger(UserExpresionQueryService.class);

    private final UserExpresionRepository userExpresionRepository;

    public UserExpresionQueryService(UserExpresionRepository userExpresionRepository) {
        this.userExpresionRepository = userExpresionRepository;
    }

    /**
     * Return a {@link List} of {@link UserExpresion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserExpresion> findByCriteria(UserExpresionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserExpresion> specification = createSpecification(criteria);
        return userExpresionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserExpresion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserExpresion> findByCriteria(UserExpresionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserExpresion> specification = createSpecification(criteria);
        return userExpresionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserExpresionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserExpresion> specification = createSpecification(criteria);
        return userExpresionRepository.count(specification);
    }

    /**
     * Function to convert {@link UserExpresionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserExpresion> createSpecification(UserExpresionCriteria criteria) {
        Specification<UserExpresion> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserExpresion_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), UserExpresion_.value));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getPriority(), UserExpresion_.priority));
            }
            if (criteria.getIntentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getIntentId(), root -> root.join(UserExpresion_.intents, JoinType.LEFT).get(Intent_.id))
                    );
            }
        }
        return specification;
    }
}
