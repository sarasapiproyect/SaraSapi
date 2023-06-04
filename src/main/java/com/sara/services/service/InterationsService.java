package com.sara.services.service;

import com.sara.services.domain.Interations;
import com.sara.services.repository.InterationsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Interations}.
 */
@Service
@Transactional
public class InterationsService {

    private final Logger log = LoggerFactory.getLogger(InterationsService.class);

    private final InterationsRepository interationsRepository;

    public InterationsService(InterationsRepository interationsRepository) {
        this.interationsRepository = interationsRepository;
    }

    /**
     * Save a interations.
     *
     * @param interations the entity to save.
     * @return the persisted entity.
     */
    public Interations save(Interations interations) {
        log.debug("Request to save Interations : {}", interations);
        return interationsRepository.save(interations);
    }

    /**
     * Update a interations.
     *
     * @param interations the entity to save.
     * @return the persisted entity.
     */
    public Interations update(Interations interations) {
        log.debug("Request to update Interations : {}", interations);
        return interationsRepository.save(interations);
    }

    /**
     * Partially update a interations.
     *
     * @param interations the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Interations> partialUpdate(Interations interations) {
        log.debug("Request to partially update Interations : {}", interations);

        return interationsRepository
            .findById(interations.getId())
            .map(existingInterations -> {
                if (interations.getValueRequest() != null) {
                    existingInterations.setValueRequest(interations.getValueRequest());
                }
                if (interations.getSourceInfo() != null) {
                    existingInterations.setSourceInfo(interations.getSourceInfo());
                }
                if (interations.getValueResponse() != null) {
                    existingInterations.setValueResponse(interations.getValueResponse());
                }
                if (interations.getSourceChannel() != null) {
                    existingInterations.setSourceChannel(interations.getSourceChannel());
                }

                return existingInterations;
            })
            .map(interationsRepository::save);
    }

    /**
     * Get all the interations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Interations> findAll(Pageable pageable) {
        log.debug("Request to get all Interations");
        return interationsRepository.findAll(pageable);
    }

    /**
     * Get one interations by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Interations> findOne(Long id) {
        log.debug("Request to get Interations : {}", id);
        return interationsRepository.findById(id);
    }

    /**
     * Delete the interations by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Interations : {}", id);
        interationsRepository.deleteById(id);
    }
}
