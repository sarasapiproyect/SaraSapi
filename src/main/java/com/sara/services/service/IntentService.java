package com.sara.services.service;

import com.sara.services.domain.Intent;
import com.sara.services.repository.IntentRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Intent}.
 */
@Service
@Transactional
public class IntentService {

    private final Logger log = LoggerFactory.getLogger(IntentService.class);

    private final IntentRepository intentRepository;

    public IntentService(IntentRepository intentRepository) {
        this.intentRepository = intentRepository;
    }

    /**
     * Save a intent.
     *
     * @param intent the entity to save.
     * @return the persisted entity.
     */
    public Intent save(Intent intent) {
        log.debug("Request to save Intent : {}", intent);
        return intentRepository.save(intent);
    }

    /**
     * Update a intent.
     *
     * @param intent the entity to save.
     * @return the persisted entity.
     */
    public Intent update(Intent intent) {
        log.debug("Request to update Intent : {}", intent);
        return intentRepository.save(intent);
    }

    /**
     * Partially update a intent.
     *
     * @param intent the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Intent> partialUpdate(Intent intent) {
        log.debug("Request to partially update Intent : {}", intent);

        return intentRepository
            .findById(intent.getId())
            .map(existingIntent -> {
                if (intent.getIntenType() != null) {
                    existingIntent.setIntenType(intent.getIntenType());
                }
                if (intent.getName() != null) {
                    existingIntent.setName(intent.getName());
                }
                if (intent.getDescription() != null) {
                    existingIntent.setDescription(intent.getDescription());
                }
                if (intent.getUrlRequest() != null) {
                    existingIntent.setUrlRequest(intent.getUrlRequest());
                }
                if (intent.getEnabled() != null) {
                    existingIntent.setEnabled(intent.getEnabled());
                }
                if (intent.getCreationDate() != null) {
                    existingIntent.setCreationDate(intent.getCreationDate());
                }

                return existingIntent;
            })
            .map(intentRepository::save);
    }

    /**
     * Get all the intents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Intent> findAll(Pageable pageable) {
        log.debug("Request to get all Intents");
        return intentRepository.findAll(pageable);
    }

    /**
     * Get all the intents with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Intent> findAllWithEagerRelationships(Pageable pageable) {
        return intentRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one intent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Intent> findOne(Long id) {
        log.debug("Request to get Intent : {}", id);
        return intentRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the intent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Intent : {}", id);
        intentRepository.deleteById(id);
    }
}
