package com.sara.services.service;

import com.sara.services.domain.*; // for static metamodels
import com.sara.services.domain.Training;
import com.sara.services.repository.TrainingRepository;
import com.sara.services.service.criteria.TrainingCriteria;
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
 * Service for executing complex queries for {@link Training} entities in the database.
 * The main input is a {@link TrainingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Training} or a {@link Page} of {@link Training} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrainingQueryService extends QueryService<Training> {

    private final Logger log = LoggerFactory.getLogger(TrainingQueryService.class);

    private final TrainingRepository trainingRepository;

    public TrainingQueryService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    /**
     * Return a {@link List} of {@link Training} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Training> findByCriteria(TrainingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Training} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Training> findByCriteria(TrainingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TrainingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Training> specification = createSpecification(criteria);
        return trainingRepository.count(specification);
    }

    /**
     * Function to convert {@link TrainingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Training> createSpecification(TrainingCriteria criteria) {
        Specification<Training> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Training_.id));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), Training_.value));
            }
            if (criteria.getSourceChannel() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceChannel(), Training_.sourceChannel));
            }
            if (criteria.getCreationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreationDate(), Training_.creationDate));
            }
            if (criteria.getIp() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIp(), Training_.ip));
            }
            if (criteria.getPostionX() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostionX(), Training_.postionX));
            }
            if (criteria.getPostionY() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostionY(), Training_.postionY));
            }
            if (criteria.getSourceInfo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSourceInfo(), Training_.sourceInfo));
            }
        }
        return specification;
    }
}
