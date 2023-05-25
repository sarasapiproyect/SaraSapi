package com.sara.services.web.rest;

import com.sara.services.domain.UserResponse;
import com.sara.services.repository.UserResponseRepository;
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
 * REST controller for managing {@link com.sara.services.domain.UserResponse}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserResponseResource {

    private final Logger log = LoggerFactory.getLogger(UserResponseResource.class);

    private static final String ENTITY_NAME = "userResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserResponseRepository userResponseRepository;

    public UserResponseResource(UserResponseRepository userResponseRepository) {
        this.userResponseRepository = userResponseRepository;
    }

    /**
     * {@code POST  /user-responses} : Create a new userResponse.
     *
     * @param userResponse the userResponse to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userResponse, or with status {@code 400 (Bad Request)} if the userResponse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-responses")
    public ResponseEntity<UserResponse> createUserResponse(@Valid @RequestBody UserResponse userResponse) throws URISyntaxException {
        log.debug("REST request to save UserResponse : {}", userResponse);
        if (userResponse.getId() != null) {
            throw new BadRequestAlertException("A new userResponse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserResponse result = userResponseRepository.save(userResponse);
        return ResponseEntity
            .created(new URI("/api/user-responses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-responses/:id} : Updates an existing userResponse.
     *
     * @param id the id of the userResponse to save.
     * @param userResponse the userResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userResponse,
     * or with status {@code 400 (Bad Request)} if the userResponse is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-responses/{id}")
    public ResponseEntity<UserResponse> updateUserResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserResponse userResponse
    ) throws URISyntaxException {
        log.debug("REST request to update UserResponse : {}, {}", id, userResponse);
        if (userResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserResponse result = userResponseRepository.save(userResponse);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userResponse.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-responses/:id} : Partial updates given fields of an existing userResponse, field will ignore if it is null
     *
     * @param id the id of the userResponse to save.
     * @param userResponse the userResponse to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userResponse,
     * or with status {@code 400 (Bad Request)} if the userResponse is not valid,
     * or with status {@code 404 (Not Found)} if the userResponse is not found,
     * or with status {@code 500 (Internal Server Error)} if the userResponse couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-responses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserResponse> partialUpdateUserResponse(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserResponse userResponse
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserResponse partially : {}, {}", id, userResponse);
        if (userResponse.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userResponse.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userResponseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserResponse> result = userResponseRepository
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

                return existingUserResponse;
            })
            .map(userResponseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /user-responses} : get all the userResponses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userResponses in body.
     */
    @GetMapping("/user-responses")
    public List<UserResponse> getAllUserResponses() {
        log.debug("REST request to get all UserResponses");
        return userResponseRepository.findAll();
    }

    /**
     * {@code GET  /user-responses/:id} : get the "id" userResponse.
     *
     * @param id the id of the userResponse to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userResponse, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-responses/{id}")
    public ResponseEntity<UserResponse> getUserResponse(@PathVariable Long id) {
        log.debug("REST request to get UserResponse : {}", id);
        Optional<UserResponse> userResponse = userResponseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userResponse);
    }

    /**
     * {@code DELETE  /user-responses/:id} : delete the "id" userResponse.
     *
     * @param id the id of the userResponse to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-responses/{id}")
    public ResponseEntity<Void> deleteUserResponse(@PathVariable Long id) {
        log.debug("REST request to delete UserResponse : {}", id);
        userResponseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
