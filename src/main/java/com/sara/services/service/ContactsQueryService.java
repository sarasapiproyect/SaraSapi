package com.sara.services.service;

import com.sara.services.domain.*; // for static metamodels
import com.sara.services.domain.Contacts;
import com.sara.services.repository.ContactsRepository;
import com.sara.services.service.criteria.ContactsCriteria;
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
 * Service for executing complex queries for {@link Contacts} entities in the database.
 * The main input is a {@link ContactsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Contacts} or a {@link Page} of {@link Contacts} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContactsQueryService extends QueryService<Contacts> {

    private final Logger log = LoggerFactory.getLogger(ContactsQueryService.class);

    private final ContactsRepository contactsRepository;

    public ContactsQueryService(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    /**
     * Return a {@link List} of {@link Contacts} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Contacts> findByCriteria(ContactsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Contacts> specification = createSpecification(criteria);
        return contactsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Contacts} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Contacts> findByCriteria(ContactsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contacts> specification = createSpecification(criteria);
        return contactsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContactsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contacts> specification = createSpecification(criteria);
        return contactsRepository.count(specification);
    }

    /**
     * Function to convert {@link ContactsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Contacts> createSpecification(ContactsCriteria criteria) {
        Specification<Contacts> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Contacts_.id));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), Contacts_.phoneNumber));
            }
            if (criteria.getLastDayConnection() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastDayConnection(), Contacts_.lastDayConnection));
            }
            if (criteria.getSourceChannel() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceChannel(), Contacts_.sourceChannel));
            }
        }
        return specification;
    }
}
