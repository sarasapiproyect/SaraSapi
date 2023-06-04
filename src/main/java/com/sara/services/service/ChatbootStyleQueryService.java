package com.sara.services.service;

import com.sara.services.domain.*; // for static metamodels
import com.sara.services.domain.ChatbootStyle;
import com.sara.services.repository.ChatbootStyleRepository;
import com.sara.services.service.criteria.ChatbootStyleCriteria;
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
 * Service for executing complex queries for {@link ChatbootStyle} entities in the database.
 * The main input is a {@link ChatbootStyleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ChatbootStyle} or a {@link Page} of {@link ChatbootStyle} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ChatbootStyleQueryService extends QueryService<ChatbootStyle> {

    private final Logger log = LoggerFactory.getLogger(ChatbootStyleQueryService.class);

    private final ChatbootStyleRepository chatbootStyleRepository;

    public ChatbootStyleQueryService(ChatbootStyleRepository chatbootStyleRepository) {
        this.chatbootStyleRepository = chatbootStyleRepository;
    }

    /**
     * Return a {@link List} of {@link ChatbootStyle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ChatbootStyle> findByCriteria(ChatbootStyleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ChatbootStyle> specification = createSpecification(criteria);
        return chatbootStyleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ChatbootStyle} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ChatbootStyle> findByCriteria(ChatbootStyleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ChatbootStyle> specification = createSpecification(criteria);
        return chatbootStyleRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ChatbootStyleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ChatbootStyle> specification = createSpecification(criteria);
        return chatbootStyleRepository.count(specification);
    }

    /**
     * Function to convert {@link ChatbootStyleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ChatbootStyle> createSpecification(ChatbootStyleCriteria criteria) {
        Specification<ChatbootStyle> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ChatbootStyle_.id));
            }
            if (criteria.getNameProperties() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameProperties(), ChatbootStyle_.nameProperties));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), ChatbootStyle_.value));
            }
        }
        return specification;
    }
}
