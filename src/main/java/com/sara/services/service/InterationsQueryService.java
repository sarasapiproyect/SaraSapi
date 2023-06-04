package com.sara.services.service;

import com.sara.services.domain.*; // for static metamodels
import com.sara.services.domain.Interations;
import com.sara.services.repository.InterationsRepository;
import com.sara.services.service.criteria.InterationsCriteria;
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
 * Service for executing complex queries for {@link Interations} entities in the database.
 * The main input is a {@link InterationsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Interations} or a {@link Page} of {@link Interations} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InterationsQueryService extends QueryService<Interations> {

    private final Logger log = LoggerFactory.getLogger(InterationsQueryService.class);

    private final InterationsRepository interationsRepository;

    public InterationsQueryService(InterationsRepository interationsRepository) {
        this.interationsRepository = interationsRepository;
    }

    /**
     * Return a {@link List} of {@link Interations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Interations> findByCriteria(InterationsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Interations> specification = createSpecification(criteria);
        return interationsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Interations} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Interations> findByCriteria(InterationsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Interations> specification = createSpecification(criteria);
        return interationsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InterationsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Interations> specification = createSpecification(criteria);
        return interationsRepository.count(specification);
    }

    /**
     * Function to convert {@link InterationsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Interations> createSpecification(InterationsCriteria criteria) {
        Specification<Interations> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Interations_.id));
            }
            if (criteria.getValueRequest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueRequest(), Interations_.valueRequest));
            }
            if (criteria.getSourceInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceInfo(), Interations_.sourceInfo));
            }
            if (criteria.getValueResponse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueResponse(), Interations_.valueResponse));
            }
            if (criteria.getSourceChannel() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceChannel(), Interations_.sourceChannel));
            }
        }
        return specification;
    }
}
