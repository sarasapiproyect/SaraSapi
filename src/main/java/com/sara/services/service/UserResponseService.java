package com.sara.services.service;

import com.sara.services.domain.UserResponse;
import com.sara.services.repository.UserResponseRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserResponse}.
 */
@Service
@Transactional
public class UserResponseService {

    private final Logger log = LoggerFactory.getLogger(UserResponseService.class);

    private final UserResponseRepository userResponseRepository;

    public UserResponseService(UserResponseRepository userResponseRepository) {
        this.userResponseRepository = userResponseRepository;
    }

    /**
     * Save a userResponse.
     *
     * @param userResponse the entity to save.
     * @return the persisted entity.
     */
    public UserResponse save(UserResponse userResponse) {
        log.debug("Request to save UserResponse : {}", userResponse);
        return userResponseRepository.save(userResponse);
    }

    /**
     * Update a userResponse.
     *
     * @param userResponse the entity to save.
     * @return the persisted entity.
     */
    public UserResponse update(UserResponse userResponse) {
        log.debug("Request to update UserResponse : {}", userResponse);
        return userResponseRepository.save(userResponse);
    }

    /**
     * Partially update a userResponse.
     *
     * @param userResponse the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserResponse> partialUpdate(UserResponse userResponse) {
        log.debug("Request to partially update UserResponse : {}", userResponse);

        return userResponseRepository
            .findById(userResponse.getId())
            .map(existingUserResponse -> {
                if (userResponse.getValueResponse() != null) {
                    existingUserResponse.setValueResponse(userResponse.getValueResponse());
                }
                if (userResponse.getPriority() != null) {
                    existingUserResponse.setPriority(userResponse.getPriority());
                }
                if (userResponse.getMultimedia() != null) {
                    existingUserResponse.setMultimedia(userResponse.getMultimedia());
                }
                if (userResponse.getMultimediaContentType() != null) {
                    existingUserResponse.setMultimediaContentType(userResponse.getMultimediaContentType());
                }
                if (userResponse.getMultimediaVoice() != null) {
                    existingUserResponse.setMultimediaVoice(userResponse.getMultimediaVoice());
                }
                if (userResponse.getMultimediaVoiceContentType() != null) {
                    existingUserResponse.setMultimediaVoiceContentType(userResponse.getMultimediaVoiceContentType());
                }
                if (userResponse.getSaraAnimation() != null) {
                    existingUserResponse.setSaraAnimation(userResponse.getSaraAnimation());
                }
                if (userResponse.getSaraAnimationContentType() != null) {
                    existingUserResponse.setSaraAnimationContentType(userResponse.getSaraAnimationContentType());
                }
                if (userResponse.getIsEndConversation() != null) {
                    existingUserResponse.setIsEndConversation(userResponse.getIsEndConversation());
                }
                if (userResponse.getResponseType() != null) {
                    existingUserResponse.setResponseType(userResponse.getResponseType());
                }
                if (userResponse.getUrl() != null) {
                    existingUserResponse.setUrl(userResponse.getUrl());
                }
                if (userResponse.getMultimediaUrl() != null) {
                    existingUserResponse.setMultimediaUrl(userResponse.getMultimediaUrl());
                }
                if (userResponse.getMultimediaVoiceUrl() != null) {
                    existingUserResponse.setMultimediaVoiceUrl(userResponse.getMultimediaVoiceUrl());
                }
                if (userResponse.getSaraAnimationUrl() != null) {
                    existingUserResponse.setSaraAnimationUrl(userResponse.getSaraAnimationUrl());
                }
                if (userResponse.getMultimediaType() != null) {
                    existingUserResponse.setMultimediaType(userResponse.getMultimediaType());
                }
                if (userResponse.getShowMultimedia() != null) {
                    existingUserResponse.setShowMultimedia(userResponse.getShowMultimedia());
                }

                return existingUserResponse;
            })
            .map(userResponseRepository::save);
    }

    /**
     * Get all the userResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable) {
        log.debug("Request to get all UserResponses");
        return userResponseRepository.findAll(pageable);
    }

    /**
     * Get one userResponse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserResponse> findOne(Long id) {
        log.debug("Request to get UserResponse : {}", id);
        return userResponseRepository.findById(id);
    }

    /**
     * Delete the userResponse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserResponse : {}", id);
        userResponseRepository.deleteById(id);
    }
}
