package com.sara.services.service;

import com.sara.services.domain.UserExpresion;
import com.sara.services.repository.UserExpresionRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserExpresion}.
 */
@Service
@Transactional
public class UserExpresionService {

    private final Logger log = LoggerFactory.getLogger(UserExpresionService.class);

    private final UserExpresionRepository userExpresionRepository;

    public UserExpresionService(UserExpresionRepository userExpresionRepository) {
        this.userExpresionRepository = userExpresionRepository;
    }

    /**
     * Save a userExpresion.
     *
     * @param userExpresion the entity to save.
     * @return the persisted entity.
     */
    public UserExpresion save(UserExpresion userExpresion) {
        log.debug("Request to save UserExpresion : {}", userExpresion);
        return userExpresionRepository.save(userExpresion);
    }

    /**
     * Update a userExpresion.
     *
     * @param userExpresion the entity to save.
     * @return the persisted entity.
     */
    public UserExpresion update(UserExpresion userExpresion) {
        log.debug("Request to update UserExpresion : {}", userExpresion);
        return userExpresionRepository.save(userExpresion);
    }

    /**
     * Partially update a userExpresion.
     *
     * @param userExpresion the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserExpresion> partialUpdate(UserExpresion userExpresion) {
        log.debug("Request to partially update UserExpresion : {}", userExpresion);

        return userExpresionRepository
            .findById(userExpresion.getId())
            .map(existingUserExpresion -> {
                if (userExpresion.getValue() != null) {
                    existingUserExpresion.setValue(userExpresion.getValue());
                }
                if (userExpresion.getPriority() != null) {
                    existingUserExpresion.setPriority(userExpresion.getPriority());
                }

                return existingUserExpresion;
            })
            .map(userExpresionRepository::save);
    }

    /**
     * Get all the userExpresions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserExpresion> findAll(Pageable pageable) {
        log.debug("Request to get all UserExpresions");
        return userExpresionRepository.findAll(pageable);
    }

    /**
     * Get one userExpresion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserExpresion> findOne(Long id) {
        log.debug("Request to get UserExpresion : {}", id);
        return userExpresionRepository.findById(id);
    }

    /**
     * Delete the userExpresion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserExpresion : {}", id);
        userExpresionRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public List<UserExpresion> findByValue(String value) {
        log.debug("Request to get UserExpresion findByValue: {}", value);
        return userExpresionRepository.findByValue(value);
    }
     
}
