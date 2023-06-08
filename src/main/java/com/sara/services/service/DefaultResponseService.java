package com.sara.services.service;

import com.sara.services.domain.DefaultResponse;
import com.sara.services.repository.DefaultResponseRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DefaultResponse}.
 */
@Service
@Transactional
public class DefaultResponseService {

    private final Logger log = LoggerFactory.getLogger(DefaultResponseService.class);

    private final DefaultResponseRepository defaultResponseRepository;

    public DefaultResponseService(DefaultResponseRepository defaultResponseRepository) {
        this.defaultResponseRepository = defaultResponseRepository;
    }

    /**
     * Save a defaultResponse.
     *
     * @param defaultResponse the entity to save.
     * @return the persisted entity.
     */
    public DefaultResponse save(DefaultResponse defaultResponse) {
        log.debug("Request to save DefaultResponse : {}", defaultResponse);
        return defaultResponseRepository.save(defaultResponse);
    }

    /**
     * Update a defaultResponse.
     *
     * @param defaultResponse the entity to save.
     * @return the persisted entity.
     */
    public DefaultResponse update(DefaultResponse defaultResponse) {
        log.debug("Request to update DefaultResponse : {}", defaultResponse);
        return defaultResponseRepository.save(defaultResponse);
    }

    /**
     * Partially update a defaultResponse.
     *
     * @param defaultResponse the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DefaultResponse> partialUpdate(DefaultResponse defaultResponse) {
        log.debug("Request to partially update DefaultResponse : {}", defaultResponse);

        return defaultResponseRepository
            .findById(defaultResponse.getId())
            .map(existingDefaultResponse -> {
                if (defaultResponse.getDefaultValueResponse() != null) {
                    existingDefaultResponse.setDefaultValueResponse(defaultResponse.getDefaultValueResponse());
                }
                if (defaultResponse.getPriority() != null) {
                    existingDefaultResponse.setPriority(defaultResponse.getPriority());
                }
                if (defaultResponse.getMultimedia() != null) {
                    existingDefaultResponse.setMultimedia(defaultResponse.getMultimedia());
                }
                if (defaultResponse.getMultimediaContentType() != null) {
                    existingDefaultResponse.setMultimediaContentType(defaultResponse.getMultimediaContentType());
                }
                if (defaultResponse.getMultimediaVoice() != null) {
                    existingDefaultResponse.setMultimediaVoice(defaultResponse.getMultimediaVoice());
                }
                if (defaultResponse.getMultimediaVoiceContentType() != null) {
                    existingDefaultResponse.setMultimediaVoiceContentType(defaultResponse.getMultimediaVoiceContentType());
                }
                if (defaultResponse.getSaraAnimation() != null) {
                    existingDefaultResponse.setSaraAnimation(defaultResponse.getSaraAnimation());
                }
                if (defaultResponse.getSaraAnimationContentType() != null) {
                    existingDefaultResponse.setSaraAnimationContentType(defaultResponse.getSaraAnimationContentType());
                }
                if (defaultResponse.getIsEndConversation() != null) {
                    existingDefaultResponse.setIsEndConversation(defaultResponse.getIsEndConversation());
                }
                if (defaultResponse.getMultimediaUrl() != null) {
                    existingDefaultResponse.setMultimediaUrl(defaultResponse.getMultimediaUrl());
                }
                if (defaultResponse.getMultimediaVoiceUrl() != null) {
                    existingDefaultResponse.setMultimediaVoiceUrl(defaultResponse.getMultimediaVoiceUrl());
                }
                if (defaultResponse.getSaraAnimationUrl() != null) {
                    existingDefaultResponse.setSaraAnimationUrl(defaultResponse.getSaraAnimationUrl());
                }
                if (defaultResponse.getMultimediaType() != null) {
                    existingDefaultResponse.setMultimediaType(defaultResponse.getMultimediaType());
                }
                if (defaultResponse.getShowMultimedia() != null) {
                    existingDefaultResponse.setShowMultimedia(defaultResponse.getShowMultimedia());
                }

                return existingDefaultResponse;
            })
            .map(defaultResponseRepository::save);
    }

    /**
     * Get all the defaultResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DefaultResponse> findAll(Pageable pageable) {
        log.debug("Request to get all DefaultResponses");
        return defaultResponseRepository.findAll(pageable);
    }

    /**
     * Get one defaultResponse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DefaultResponse> findOne(Long id) {
        log.debug("Request to get DefaultResponse : {}", id);
        return defaultResponseRepository.findById(id);
    }

    /**
     * Delete the defaultResponse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DefaultResponse : {}", id);
        defaultResponseRepository.deleteById(id);
    }
}
