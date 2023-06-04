package com.sara.services.web.rest;

import com.sara.services.config.ApplicationProperties;
import com.sara.services.config.Constants;
import com.sara.services.domain.DefaultResponse;
import com.sara.services.repository.DefaultResponseRepository;
import com.sara.services.service.DefaultResponseQueryService;
import com.sara.services.service.DefaultResponseService;
import com.sara.services.service.criteria.DefaultResponseCriteria;
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
 * REST controller for managing {@link com.sara.services.domain.DefaultResponse}.
 */
@RestController
@RequestMapping("/api")
public class DefaultResponseResource {

    private final Logger log = LoggerFactory.getLogger(DefaultResponseResource.class);

    private static final String ENTITY_NAME = "defaultResponse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DefaultResponseService defaultResponseService;

    private final DefaultResponseRepository defaultResponseRepository;

    private final DefaultResponseQueryService defaultResponseQueryService;

    private final ApplicationProperties applicationProperties;
    
    public DefaultResponseResource(
        DefaultResponseService defaultResponseService, DefaultResponseRepository defaultResponseRepository,
        DefaultResponseQueryService defaultResponseQueryService,ApplicationProperties applicationProperties
    ) {
        this.defaultResponseService = defaultResponseService;
        this.defaultResponseRepository = defaultResponseRepository;
        this.defaultResponseQueryService = defaultResponseQueryService;
        this.applicationProperties = applicationProperties;
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
        if (defaultResponse.getMultimedia() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date = new Date();
            String time = String.valueOf(date.getTime());
            byte[] bytesImg = defaultResponse.getMultimedia();

            String nameFile = time + Constants.BASE_PROFILE_IMAGE_TEXT;

            Path rutaCompleta = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile);
            try {
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile;
            defaultResponse.setMultimediaUrl(URL);
        }

        if (defaultResponse.getSaraAnimation() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date2 = new Date();
            String time2 = String.valueOf(date2.getTime());
            byte[] bytesImg2 = defaultResponse.getSaraAnimation();
            String nameFile2 = time2 + Constants.BASE_IMAGE_IMAGE_GIF;
            Path rutaCompleta2 = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile2);
            try {
                Files.write(rutaCompleta2, bytesImg2);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL2 = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile2;
            defaultResponse.setSaraAnimationUrl(URL2);
        }

        if (defaultResponse.getMultimediaVoice() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date3 = new Date();
            String time3 = String.valueOf(date3.getTime());
            byte[] bytesImg3 = defaultResponse.getMultimediaVoice();
            String nameFile3 = time3 + Constants.BASE_IMAGE_IMAGE_MP3;
            Path rutaCompleta3 = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile3);
            try {
                Files.write(rutaCompleta3, bytesImg3);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL3 = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile3;
            defaultResponse.setMultimediaVoiceUrl(URL3);
        }
        DefaultResponse result = defaultResponseService.save(defaultResponse);
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
        if (defaultResponse.getMultimedia() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date = new Date();
            String time = String.valueOf(date.getTime());
            byte[] bytesImg = defaultResponse.getMultimedia();

            String nameFile = time + Constants.BASE_PROFILE_IMAGE_TEXT;

            Path rutaCompleta = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile);
            try {
                Files.write(rutaCompleta, bytesImg);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile;
            defaultResponse.setMultimediaUrl(URL);
        }

        if (defaultResponse.getSaraAnimation() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date2 = new Date();
            String time2 = String.valueOf(date2.getTime());
            byte[] bytesImg2 = defaultResponse.getSaraAnimation();
            String nameFile2 = time2 + Constants.BASE_IMAGE_IMAGE_GIF;
            Path rutaCompleta2 = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile2);
            try {
                Files.write(rutaCompleta2, bytesImg2);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL2 = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile2;
            defaultResponse.setSaraAnimationUrl(URL2);
        }

        if (defaultResponse.getMultimediaVoice() != null) {
            ///////guardando el archivo fisico/////////////////////////////Multimedia
            Date date3 = new Date();
            String time3 = String.valueOf(date3.getTime());
            byte[] bytesImg3 = defaultResponse.getMultimediaVoice();
            String nameFile3 = time3 + Constants.BASE_IMAGE_IMAGE_MP3;
            Path rutaCompleta3 = Paths.get(applicationProperties.getCompliance().getSource_image_profile() + nameFile3);
            try {
                Files.write(rutaCompleta3, bytesImg3);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(UserResponseResource.class.getName()).log(Level.SEVERE, null, ex);
            }
            String URL3 = applicationProperties.getCompliance().getAddress_image_profile() + Constants.SPRING_PATH + nameFile3;
            defaultResponse.setMultimediaVoiceUrl(URL3);
        }
        DefaultResponse result = defaultResponseService.update(defaultResponse);
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

        Optional<DefaultResponse> result = defaultResponseService.partialUpdate(defaultResponse);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, defaultResponse.getId().toString())
        );
    }

    /**
     * {@code GET  /default-responses} : get all the defaultResponses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of defaultResponses in body.
     */
    @GetMapping("/default-responses")
    public ResponseEntity<List<DefaultResponse>> getAllDefaultResponses(
        DefaultResponseCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DefaultResponses by criteria: {}", criteria);
        Page<DefaultResponse> page = defaultResponseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /default-responses/count} : count all the defaultResponses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/default-responses/count")
    public ResponseEntity<Long> countDefaultResponses(DefaultResponseCriteria criteria) {
        log.debug("REST request to count DefaultResponses by criteria: {}", criteria);
        return ResponseEntity.ok().body(defaultResponseQueryService.countByCriteria(criteria));
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
        Optional<DefaultResponse> defaultResponse = defaultResponseService.findOne(id);
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
        defaultResponseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
