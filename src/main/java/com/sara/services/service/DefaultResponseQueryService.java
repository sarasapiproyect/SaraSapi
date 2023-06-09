package com.sara.services.service;

import com.sara.services.domain.*; // for static metamodels
import com.sara.services.domain.DefaultResponse;
import com.sara.services.repository.DefaultResponseRepository;
import com.sara.services.service.criteria.DefaultResponseCriteria;
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
 * Service for executing complex queries for {@link DefaultResponse} entities in the database.
 * The main input is a {@link DefaultResponseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DefaultResponse} or a {@link Page} of {@link DefaultResponse} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DefaultResponseQueryService extends QueryService<DefaultResponse> {

    private final Logger log = LoggerFactory.getLogger(DefaultResponseQueryService.class);

    private final DefaultResponseRepository defaultResponseRepository;

    public DefaultResponseQueryService(DefaultResponseRepository defaultResponseRepository) {
        this.defaultResponseRepository = defaultResponseRepository;
    }

    /**
     * Return a {@link List} of {@link DefaultResponse} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DefaultResponse> findByCriteria(DefaultResponseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DefaultResponse> specification = createSpecification(criteria);
        return defaultResponseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DefaultResponse} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DefaultResponse> findByCriteria(DefaultResponseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DefaultResponse> specification = createSpecification(criteria);
        return defaultResponseRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DefaultResponseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DefaultResponse> specification = createSpecification(criteria);
        return defaultResponseRepository.count(specification);
    }

    /**
     * Function to convert {@link DefaultResponseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DefaultResponse> createSpecification(DefaultResponseCriteria criteria) {
        Specification<DefaultResponse> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DefaultResponse_.id));
            }
            if (criteria.getDefaultValueResponse() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDefaultValueResponse(), DefaultResponse_.defaultValueResponse));
            }
            if (criteria.getPriority() != null) {
                specification = specification.and(buildSpecification(criteria.getPriority(), DefaultResponse_.priority));
            }
            if (criteria.getIsEndConversation() != null) {
                specification = specification.and(buildSpecification(criteria.getIsEndConversation(), DefaultResponse_.isEndConversation));
            }
            if (criteria.getMultimediaUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMultimediaUrl(), DefaultResponse_.multimediaUrl));
            }
            if (criteria.getMultimediaVoiceUrl() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getMultimediaVoiceUrl(), DefaultResponse_.multimediaVoiceUrl));
            }
            if (criteria.getSaraAnimationUrl() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSaraAnimationUrl(), DefaultResponse_.saraAnimationUrl));
            }
            if (criteria.getMultimediaType() != null) {
                specification = specification.and(buildSpecification(criteria.getMultimediaType(), DefaultResponse_.multimediaType));
            }
            if (criteria.getShowMultimedia() != null) {
                specification = specification.and(buildSpecification(criteria.getShowMultimedia(), DefaultResponse_.showMultimedia));
            }
            if (criteria.getChannelMultimediaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getChannelMultimediaId(),
                            root -> root.join(DefaultResponse_.channelMultimedias, JoinType.LEFT).get(Channel_.id)
                        )
                    );
            }
            if (criteria.getChannelVoiceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getChannelVoiceId(),
                            root -> root.join(DefaultResponse_.channelVoices, JoinType.LEFT).get(Channel_.id)
                        )
                    );
            }
            if (criteria.getChannelAnimationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getChannelAnimationId(),
                            root -> root.join(DefaultResponse_.channelAnimations, JoinType.LEFT).get(Channel_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
