package com.sara.services.service;

import com.sara.services.domain.*; // for static metamodels
import com.sara.services.domain.UserResponse;
import com.sara.services.repository.UserResponseRepository;
import com.sara.services.service.criteria.UserResponseCriteria;
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
 * Service for executing complex queries for {@link UserResponse} entities in the database.
 * The main input is a {@link UserResponseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserResponse} or a {@link Page} of {@link UserResponse} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserResponseQueryService extends QueryService<UserResponse> {

    private final Logger log = LoggerFactory.getLogger(UserResponseQueryService.class);

    private final UserResponseRepository userResponseRepository;

    public UserResponseQueryService(UserResponseRepository userResponseRepository) {
        this.userResponseRepository = userResponseRepository;
    }

    /**
     * Return a {@link List} of {@link UserResponse} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserResponse> findByCriteria(UserResponseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserResponse> specification = createSpecification(criteria);
        return userResponseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserResponse} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserResponse> findByCriteria(UserResponseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserResponse> specification = createSpecification(criteria);
        return userResponseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserResponseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserResponse> specification = createSpecification(criteria);
        return userResponseRepository.count(specification);
    }

    /**
     * Function to convert {@link UserResponseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserResponse> createSpecification(UserResponseCriteria criteria) {
        Specification<UserResponse> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserResponse_.id));
            }
            if (criteria.getValueResponse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValueResponse(), UserResponse_.valueResponse));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getPriority(), UserResponse_.priority));
            }
            if (criteria.getIsEndConversation() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEndConversation(), UserResponse_.isEndConversation));
            }
            if (criteria.getResponseType() != null) {
                specification = specification.and(buildSpecification(criteria.getResponseType(), UserResponse_.responseType));
            }
            if (criteria.getUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrl(), UserResponse_.url));
            }
            if (criteria.getMultimediaUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMultimediaUrl(), UserResponse_.multimediaUrl));
            }
            if (criteria.getMultimediaVoiceUrl() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getMultimediaVoiceUrl(), UserResponse_.multimediaVoiceUrl));
            }
            if (criteria.getSaraAnimationUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSaraAnimationUrl(), UserResponse_.saraAnimationUrl));
            }
            if (criteria.getMultimediaType() != null) {
                specification = specification.and(buildSpecification(criteria.getMultimediaType(), UserResponse_.multimediaType));
            }
            if (criteria.getIntentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getIntentId(), root -> root.join(UserResponse_.intents, JoinType.LEFT).get(Intent_.id))
                    );
            }
        }
        return specification;
    }
}
