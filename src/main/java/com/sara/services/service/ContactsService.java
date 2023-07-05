package com.sara.services.service;

import com.sara.services.domain.Contacts;
import com.sara.services.repository.ContactsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Contacts}.
 */
@Service
@Transactional
public class ContactsService {

    private final Logger log = LoggerFactory.getLogger(ContactsService.class);

    private final ContactsRepository contactsRepository;

    public ContactsService(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    /**
     * Save a contacts.
     *
     * @param contacts the entity to save.
     * @return the persisted entity.
     */
    public Contacts save(Contacts contacts) {
        log.debug("Request to save Contacts : {}", contacts);
        return contactsRepository.save(contacts);
    }

    /**
     * Update a contacts.
     *
     * @param contacts the entity to save.
     * @return the persisted entity.
     */
    public Contacts update(Contacts contacts) {
        log.debug("Request to update Contacts : {}", contacts);
        return contactsRepository.save(contacts);
    }

    /**
     * Partially update a contacts.
     *
     * @param contacts the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Contacts> partialUpdate(Contacts contacts) {
        log.debug("Request to partially update Contacts : {}", contacts);

        return contactsRepository
            .findById(contacts.getId())
            .map(existingContacts -> {
                if (contacts.getPhoneNumber() != null) {
                    existingContacts.setPhoneNumber(contacts.getPhoneNumber());
                }
                if (contacts.getLastDayConnection() != null) {
                    existingContacts.setLastDayConnection(contacts.getLastDayConnection());
                }
                if (contacts.getSourceChannel() != null) {
                    existingContacts.setSourceChannel(contacts.getSourceChannel());
                }

                return existingContacts;
            })
            .map(contactsRepository::save);
    }

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Contacts> findAll(Pageable pageable) {
        log.debug("Request to get all Contacts");
        return contactsRepository.findAll(pageable);
    }

    /**
     * Get one contacts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Contacts> findOne(Long id) {
        log.debug("Request to get Contacts : {}", id);
        return contactsRepository.findById(id);
    }

    /**
     * Delete the contacts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contacts : {}", id);
        contactsRepository.deleteById(id);
    }
}
