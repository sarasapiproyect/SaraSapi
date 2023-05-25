package com.sara.services.web.rest;

import com.sara.services.domain.DefaultResponse;
import com.sara.services.repository.DefaultResponseRepository;
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
 * REST controller for managing {@link com.sara.services.domain.DefaultResponse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DefaultResponseResource {

    private final Logger log = LoggerFactory.getLogger(DefaultResponseResource.class);

    private static final String ENTITY_NAME = "defaultResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DefaultResponseRepository defaultResponseRepository;

    public DefaultResponseResource(DefaultResponseRepository defaultResponseRepository) {
        this.defaultResponseRepository = defaultResponseRepository;
    }

    /**
     * {@code POST  /default-responses} : Create a new defaultResponse.
     *
     * @param defaultResponse the defaultResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new defaultResponse, or with status {@code 400 (Bad Request)} if the defaultResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/default-responses")
    public ResponseEntity<DefaultResponse> createDefaultResponse(@Valid @RequestBody DefaultResponse defaultResponse)
        throws URISyntaxException {
        log.debug("REST request to save DefaultResponse : {}", defaultResponse);
        if (defaultResponse.getId() != null) {
            throw new BadRequestAlertException("A new defaultResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DefaultResponse result = defaultResponseRepository.save(defaultResponse);
        return ResponseEntity
            .created(new URI("/api/default-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /default-responses/:id} : Updates an existing defaultResponse.
     *
     * @param id the id of the defaultResponse to save.
     * @param defaultResponse the defaultResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated defaultResponse,
     * or with status {@code 400 (Bad Request)} if the defaultResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the defaultResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/default-responses/{id}")
    public ResponseEntity<DefaultResponse> updateDefaultResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DefaultResponse defaultResponse
    ) throws URISyntaxException {
        log.debug("REST request to update DefaultResponse : {}, {}", id, defaultResponse);
        if (defaultResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, defaultResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!defaultResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DefaultResponse result = defaultResponseRepository.save(defaultResponse);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, defaultResponse.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /default-responses/:id} : Partial updates given fields of an existing defaultResponse, field will ignore if it is null
     *
     * @param id the id of the defaultResponse to save.
     * @param defaultResponse the defaultResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated defaultResponse,
     * or with status {@code 400 (Bad Request)} if the defaultResponse is not valid,
     * or with status {@code 404 (Not Found)} if the defaultResponse is not found,
     * or with status {@code 500 (Internal Server Error)} if the defaultResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/default-responses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DefaultResponse> partialUpdateDefaultResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DefaultResponse defaultResponse
    ) throws URISyntaxException {
        log.debug("REST request to partial update DefaultResponse partially : {}, {}", id, defaultResponse);
        if (defaultResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, defaultResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!defaultResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DefaultResponse> result = defaultResponseRepository
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

                return existingDefaultResponse;
            })
            .map(defaultResponseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, defaultResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /default-responses} : get all the defaultResponses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of defaultResponses in body.
     */
    @GetMapping("/default-responses")
    public List<DefaultResponse> getAllDefaultResponses() {
        log.debug("REST request to get all DefaultResponses");
        return defaultResponseRepository.findAll();
    }

    /**
     * {@code GET  /default-responses/:id} : get the "id" defaultResponse.
     *
     * @param id the id of the defaultResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the defaultResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/default-responses/{id}")
    public ResponseEntity<DefaultResponse> getDefaultResponse(@PathVariable Long id) {
        log.debug("REST request to get DefaultResponse : {}", id);
        Optional<DefaultResponse> defaultResponse = defaultResponseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(defaultResponse);
    }

    /**
     * {@code DELETE  /default-responses/:id} : delete the "id" defaultResponse.
     *
     * @param id the id of the defaultResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/default-responses/{id}")
    public ResponseEntity<Void> deleteDefaultResponse(@PathVariable Long id) {
        log.debug("REST request to delete DefaultResponse : {}", id);
        defaultResponseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
