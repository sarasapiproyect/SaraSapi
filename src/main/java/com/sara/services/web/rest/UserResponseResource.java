package com.sara.services.web.rest;

import com.sara.services.config.ApplicationProperties;
import com.sara.services.config.Constants;
import com.sara.services.domain.UserResponse;
import com.sara.services.repository.UserResponseRepository;
import com.sara.services.service.UserResponseQueryService;
import com.sara.services.service.UserResponseService;
import com.sara.services.service.criteria.UserResponseCriteria;
import com.sara.services.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sara.services.domain.UserResponse}.
 */
@RestController
@RequestMapping("/api")
public class UserResponseResource {

    private final Logger log = LoggerFactory.getLogger(UserResponseResource.class);

    private static final String ENTITY_NAME = "userResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserResponseService userResponseService;

    private final UserResponseRepository userResponseRepository;

    private final UserResponseQueryService userResponseQueryService;

    private final ApplicationProperties applicationProperties;
    
    public UserResponseResource(
        UserResponseService userResponseService, UserResponseRepository userResponseRepository,
        UserResponseQueryService userResponseQueryService, ApplicationProperties applicationProperties
    ) {
        this.userResponseService = userResponseService;
        this.userResponseRepository = userResponseRepository;
        this.userResponseQueryService = userResponseQueryService;
        this.applicationProperties = applicationProperties;
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
        if (userResponse.getMultimedia() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date = new Date();
            String time = String.valueOf(date.getTime());
            byte[] bytesImg = userResponse.getMultimedia();

            String nameFile = time + Constants.BASE_PROFILE_IMAGE_TEXT;

            Path rutaCompleta = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile);
            try {
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile;
            userResponse.setMultimediaUrl(URL);
        }

        if (userResponse.getSaraAnimation() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date2 = new Date();
            String time2 = String.valueOf(date2.getTime());
            byte[] bytesImg2 = userResponse.getSaraAnimation();
            String nameFile2 = time2 + Constants.BASE_IMAGE_IMAGE_GIF;
            Path rutaCompleta2 = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile2);
            try {
                Files.write(rutaCompleta2, bytesImg2);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL2 = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile2;
            userResponse.setSaraAnimationUrl(URL2);
        }

        if (userResponse.getMultimediaVoice() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date3 = new Date();
            String time3 = String.valueOf(date3.getTime());
            byte[] bytesImg3 = userResponse.getMultimediaVoice();
            String nameFile3 = time3 + Constants.BASE_IMAGE_IMAGE_MP3;
            Path rutaCompleta3 = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile3);
            try {
                Files.write(rutaCompleta3, bytesImg3);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL3 = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile3;
            userResponse.setMultimediaVoiceUrl(URL3);
        }
        userResponse.setMultimedia(null);
        userResponse.setMultimediaVoice(null);
        userResponse.setSaraAnimation(null);
        if (!userResponse.getMultimediaContentType().equals("image/png"))
            throw new BadRequestAlertException("Invalid image format", ENTITY_NAME, "extInvalid");
        UserResponse result = userResponseService.save(userResponse);
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
        if (userResponse.getMultimedia() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date = new Date();
            String time = String.valueOf(date.getTime());
            byte[] bytesImg = userResponse.getMultimedia();

            String nameFile = time + Constants.BASE_PROFILE_IMAGE_TEXT;

            Path rutaCompleta = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile);
            try {
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile;
            userResponse.setMultimediaUrl(URL);
        }

        if (userResponse.getSaraAnimation() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date2 = new Date();
            String time2 = String.valueOf(date2.getTime());
            byte[] bytesImg2 = userResponse.getSaraAnimation();
            String nameFile2 = time2 + Constants.BASE_IMAGE_IMAGE_GIF;
            Path rutaCompleta2 = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile2);
            try {
                Files.write(rutaCompleta2, bytesImg2);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL2 = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile2;
            userResponse.setSaraAnimationUrl(URL2);
        }

        if (userResponse.getMultimediaVoice() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date3 = new Date();
            String time3 = String.valueOf(date3.getTime());
            byte[] bytesImg3 = userResponse.getMultimediaVoice();
            String nameFile3 = time3 + Constants.BASE_IMAGE_IMAGE_MP3;
            Path rutaCompleta3 = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile3);
            try {
                Files.write(rutaCompleta3, bytesImg3);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL3 = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile3;
            userResponse.setMultimediaVoiceUrl(URL3);
        }
        userResponse.setMultimedia(null);
        userResponse.setMultimediaVoice(null);
        userResponse.setSaraAnimation(null);
        if (!userResponse.getMultimediaContentType().equals("image/png"))
            throw new BadRequestAlertException("Invalid image format", ENTITY_NAME, "extInvalid");
        UserResponse result = userResponseService.update(userResponse);
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
        userResponse.getMultimediaContentType();
        Optional<UserResponse> result = userResponseService.partialUpdate(userResponse);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /user-responses} : get all the userResponses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userResponses in body.
     */
    @GetMapping("/user-responses")
    public ResponseEntity<List<UserResponse>> getAllUserResponses(
        UserResponseCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserResponses by criteria: {}", criteria);
        Page<UserResponse> page = userResponseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-responses/count} : count all the userResponses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-responses/count")
    public ResponseEntity<Long> countUserResponses(UserResponseCriteria criteria) {
        log.debug("REST request to count UserResponses by criteria: {}", criteria);
        return ResponseEntity.ok().body(userResponseQueryService.countByCriteria(criteria));
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
        Optional<UserResponse> userResponse = userResponseService.findOne(id);
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
        userResponseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
