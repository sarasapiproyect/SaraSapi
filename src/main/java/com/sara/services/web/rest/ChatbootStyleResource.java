package com.sara.services.web.rest;

import com.sara.services.domain.ChatbootStyle;
import com.sara.services.repository.ChatbootStyleRepository;
import com.sara.services.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sara.services.domain.ChatbootStyle}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChatbootStyleResource {

    private final Logger log = LoggerFactory.getLogger(ChatbootStyleResource.class);

    private static final String ENTITY_NAME = "chatbootStyle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChatbootStyleRepository chatbootStyleRepository;

    public ChatbootStyleResource(ChatbootStyleRepository chatbootStyleRepository) {
        this.chatbootStyleRepository = chatbootStyleRepository;
    }

    /**
     * {@code POST  /chatboot-styles} : Create a new chatbootStyle.
     *
     * @param chatbootStyle the chatbootStyle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chatbootStyle, or with status {@code 400 (Bad Request)} if the chatbootStyle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chatboot-styles")
    public ResponseEntity<ChatbootStyle> createChatbootStyle(@Valid @RequestBody ChatbootStyle chatbootStyle) throws URISyntaxException {
        log.debug("REST request to save ChatbootStyle : {}", chatbootStyle);
        if (chatbootStyle.getId() != null) {
            throw new BadRequestAlertException("A new chatbootStyle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChatbootStyle result = chatbootStyleRepository.save(chatbootStyle);
        return ResponseEntity
            .created(new URI("/api/chatboot-styles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chatboot-styles/:id} : Updates an existing chatbootStyle.
     *
     * @param id the id of the chatbootStyle to save.
     * @param chatbootStyle the chatbootStyle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chatbootStyle,
     * or with status {@code 400 (Bad Request)} if the chatbootStyle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chatbootStyle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chatboot-styles/{id}")
    public ResponseEntity<ChatbootStyle> updateChatbootStyle(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ChatbootStyle chatbootStyle
    ) throws URISyntaxException {
        log.debug("REST request to update ChatbootStyle : {}, {}", id, chatbootStyle);
        if (chatbootStyle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chatbootStyle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chatbootStyleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ChatbootStyle result = chatbootStyleRepository.save(chatbootStyle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chatbootStyle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chatboot-styles/:id} : Partial updates given fields of an existing chatbootStyle, field will ignore if it is null
     *
     * @param id the id of the chatbootStyle to save.
     * @param chatbootStyle the chatbootStyle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chatbootStyle,
     * or with status {@code 400 (Bad Request)} if the chatbootStyle is not valid,
     * or with status {@code 404 (Not Found)} if the chatbootStyle is not found,
     * or with status {@code 500 (Internal Server Error)} if the chatbootStyle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chatboot-styles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ChatbootStyle> partialUpdateChatbootStyle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ChatbootStyle chatbootStyle
    ) throws URISyntaxException {
        log.debug("REST request to partial update ChatbootStyle partially : {}, {}", id, chatbootStyle);
        if (chatbootStyle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chatbootStyle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chatbootStyleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ChatbootStyle> result = chatbootStyleRepository
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

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chatbootStyle.getId().toString())
        );
    }

    /**
     * {@code GET  /chatboot-styles} : get all the chatbootStyles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chatbootStyles in body.
     */
    @GetMapping("/chatboot-styles")
    public List<ChatbootStyle> getAllChatbootStyles() {
        log.debug("REST request to get all ChatbootStyles");
        return chatbootStyleRepository.findAll();
    }

    /**
     * {@code GET  /chatboot-styles/:id} : get the "id" chatbootStyle.
     *
     * @param id the id of the chatbootStyle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chatbootStyle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chatboot-styles/{id}")
    public ResponseEntity<ChatbootStyle> getChatbootStyle(@PathVariable Long id) {
        log.debug("REST request to get ChatbootStyle : {}", id);
        Optional<ChatbootStyle> chatbootStyle = chatbootStyleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chatbootStyle);
    }

    /**
     * {@code DELETE  /chatboot-styles/:id} : delete the "id" chatbootStyle.
     *
     * @param id the id of the chatbootStyle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chatboot-styles/{id}")
    public ResponseEntity<Void> deleteChatbootStyle(@PathVariable Long id) {
        log.debug("REST request to delete ChatbootStyle : {}", id);
        chatbootStyleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
