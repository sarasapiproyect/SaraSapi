package com.sara.services.service;

import com.sara.services.domain.Training;
import com.sara.services.repository.TrainingRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Training}.
 */
@Service
@Transactional
public class TrainingService {

    private final Logger log = LoggerFactory.getLogger(TrainingService.class);

    private final TrainingRepository trainingRepository;

    public TrainingService(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    /**
     * Save a training.
     *
     * @param training the entity to save.
     * @return the persisted entity.
     */
    public Training save(Training training) {
        log.debug("Request to save Training : {}", training);
        return trainingRepository.save(training);
    }

    /**
     * Update a training.
     *
     * @param training the entity to save.
     * @return the persisted entity.
     */
    public Training update(Training training) {
        log.debug("Request to update Training : {}", training);
        return trainingRepository.save(training);
    }

    /**
     * Partially update a training.
     *
     * @param training the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Training> partialUpdate(Training training) {
        log.debug("Request to partially update Training : {}", training);

        return trainingRepository
            .findById(training.getId())
            .map(existingTraining -> {
                if (training.getValue() != null) {
                    existingTraining.setValue(training.getValue());
                }
                if (training.getSourceChannel() != null) {
                    existingTraining.setSourceChannel(training.getSourceChannel());
                }
                if (training.getCreationDate() != null) {
                    existingTraining.setCreationDate(training.getCreationDate());
                }
                if (training.getIp() != null) {
                    existingTraining.setIp(training.getIp());
                }
                if (training.getPostionX() != null) {
                    existingTraining.setPostionX(training.getPostionX());
                }
                if (training.getPostionY() != null) {
                    existingTraining.setPostionY(training.getPostionY());
                }
                if (training.getSourceInfo() != null) {
                    existingTraining.setSourceInfo(training.getSourceInfo());
                }

                return existingTraining;
            })
            .map(trainingRepository::save);
    }

    /**
     * Get all the trainings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Training> findAll(Pageable pageable) {
        log.debug("Request to get all Trainings");
        return trainingRepository.findAll(pageable);
    }

    /**
     * Get one training by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Training> findOne(Long id) {
        log.debug("Request to get Training : {}", id);
        return trainingRepository.findById(id);
    }

    /**
     * Delete the training by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Training : {}", id);
        trainingRepository.deleteById(id);
    }
}
