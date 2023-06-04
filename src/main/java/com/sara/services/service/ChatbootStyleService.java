package com.sara.services.service;

import com.sara.services.domain.ChatbootStyle;
import com.sara.services.repository.ChatbootStyleRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ChatbootStyle}.
 */
@Service
@Transactional
public class ChatbootStyleService {

    private final Logger log = LoggerFactory.getLogger(ChatbootStyleService.class);

    private final ChatbootStyleRepository chatbootStyleRepository;

    public ChatbootStyleService(ChatbootStyleRepository chatbootStyleRepository) {
        this.chatbootStyleRepository = chatbootStyleRepository;
    }

    /**
     * Save a chatbootStyle.
     *
     * @param chatbootStyle the entity to save.
     * @return the persisted entity.
     */
    public ChatbootStyle save(ChatbootStyle chatbootStyle) {
        log.debug("Request to save ChatbootStyle : {}", chatbootStyle);
        return chatbootStyleRepository.save(chatbootStyle);
    }

    /**
     * Update a chatbootStyle.
     *
     * @param chatbootStyle the entity to save.
     * @return the persisted entity.
     */
    public ChatbootStyle update(ChatbootStyle chatbootStyle) {
        log.debug("Request to update ChatbootStyle : {}", chatbootStyle);
        return chatbootStyleRepository.save(chatbootStyle);
    }

    /**
     * Partially update a chatbootStyle.
     *
     * @param chatbootStyle the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ChatbootStyle> partialUpdate(ChatbootStyle chatbootStyle) {
        log.debug("Request to partially update ChatbootStyle : {}", chatbootStyle);

        return chatbootStyleRepository
            .findById(chatbootStyle.getId())
            .map(existingChatbootStyle -> {
                if (chatbootStyle.getNameProperties() != null) {
                    existingChatbootStyle.setNameProperties(chatbootStyle.getNameProperties());
                }
                if (chatbootStyle.getValue() != null) {
                    existingChatbootStyle.setValue(chatbootStyle.getValue());
                }
                if (chatbootStyle.getMultimedia() != null) {
                    existingChatbootStyle.setMultimedia(chatbootStyle.getMultimedia());
                }
                if (chatbootStyle.getMultimediaContentType() != null) {
                    existingChatbootStyle.setMultimediaContentType(chatbootStyle.getMultimediaContentType());
                }

                return existingChatbootStyle;
            })
            .map(chatbootStyleRepository::save);
    }

    /**
     * Get all the chatbootStyles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ChatbootStyle> findAll(Pageable pageable) {
        log.debug("Request to get all ChatbootStyles");
        return chatbootStyleRepository.findAll(pageable);
    }

    /**
     * Get one chatbootStyle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ChatbootStyle> findOne(Long id) {
        log.debug("Request to get ChatbootStyle : {}", id);
        return chatbootStyleRepository.findById(id);
    }

    /**
     * Delete the chatbootStyle by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ChatbootStyle : {}", id);
        chatbootStyleRepository.deleteById(id);
    }
}
