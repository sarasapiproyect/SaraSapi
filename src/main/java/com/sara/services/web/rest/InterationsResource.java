package com.sara.services.web.rest;

import com.sara.services.domain.DefaultResponse;
import com.sara.services.domain.Intent;
import com.sara.services.domain.Interations;
import com.sara.services.domain.Training;
import com.sara.services.domain.UserExpresion;
import com.sara.services.domain.UserResponse;
import com.sara.services.repository.DefaultResponseRepository;
import com.sara.services.repository.InterationsRepository;
import com.sara.services.repository.TrainingRepository;
import com.sara.services.repository.UserExpresionRepository;
import com.sara.services.web.rest.Util.GeneralUtils;
import com.sara.services.web.rest.errors.BadRequestAlertException;
import com.sara.services.web.rest.request.ReceiveMessageRequest;
import com.sara.services.web.rest.response.ResponseMessage;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
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
 * REST controller for managing {@link com.sara.services.domain.Interations}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InterationsResource {

    private final Logger log = LoggerFactory.getLogger(InterationsResource.class);

    private static final String ENTITY_NAME = "interations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterationsRepository interationsRepository;
    
    private final UserExpresionRepository userExpresionRepository;
    
    private final DefaultResponseRepository defaultResponseRepository;
    
    private final TrainingRepository trainingRepository;

    public InterationsResource(InterationsRepository interationsRepository, UserExpresionRepository userExpresionRepository,
    		DefaultResponseRepository defaultResponseRepository, TrainingRepository trainingRepository) {
        this.interationsRepository = interationsRepository;
        this.userExpresionRepository = userExpresionRepository;
        this.defaultResponseRepository = defaultResponseRepository;
        this.trainingRepository = trainingRepository;
    }

    /**
     * {@code POST  /interations} : Create a new interations.
     *
     * @param interations the interations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interations, or with status {@code 400 (Bad Request)} if the interations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interations")
    public ResponseEntity<Interations> createInterations(@Valid @RequestBody Interations interations) throws URISyntaxException {
        log.debug("REST request to save Interations : {}", interations);
        if (interations.getId() != null) {
            throw new BadRequestAlertException("A new interations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Interations result = interationsRepository.save(interations);
        return ResponseEntity
            .created(new URI("/api/interations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interations/:id} : Updates an existing interations.
     *
     * @param id the id of the interations to save.
     * @param interations the interations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interations,
     * or with status {@code 400 (Bad Request)} if the interations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interations/{id}")
    public ResponseEntity<Interations> updateInterations(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Interations interations
    ) throws URISyntaxException {
        log.debug("REST request to update Interations : {}, {}", id, interations);
        if (interations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Interations result = interationsRepository.save(interations);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interations.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /interations/:id} : Partial updates given fields of an existing interations, field will ignore if it is null
     *
     * @param id the id of the interations to save.
     * @param interations the interations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interations,
     * or with status {@code 400 (Bad Request)} if the interations is not valid,
     * or with status {@code 404 (Not Found)} if the interations is not found,
     * or with status {@code 500 (Internal Server Error)} if the interations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/interations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Interations> partialUpdateInterations(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Interations interations
    ) throws URISyntaxException {
        log.debug("REST request to partial update Interations partially : {}, {}", id, interations);
        if (interations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, interations.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!interationsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Interations> result = interationsRepository
            .findById(interations.getId())
            .map(existingInterations -> {
                if (interations.getValueRequest() != null) {
                    existingInterations.setValueRequest(interations.getValueRequest());
                }
                if (interations.getSourceInfo() != null) {
                    existingInterations.setSourceInfo(interations.getSourceInfo());
                }
                if (interations.getValueResponse() != null) {
                    existingInterations.setValueResponse(interations.getValueResponse());
                }
                if (interations.getSourceChannel() != null) {
                    existingInterations.setSourceChannel(interations.getSourceChannel());
                }

                return existingInterations;
            })
            .map(interationsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interations.getId().toString())
        );
    }

    /**
     * {@code GET  /interations} : get all the interations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interations in body.
     */
    @GetMapping("/interations")
    public List<Interations> getAllInterations() {
        log.debug("REST request to get all Interations");
        return interationsRepository.findAll();
    }

    /**
     * {@code GET  /interations/:id} : get the "id" interations.
     *
     * @param id the id of the interations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interations/{id}")
    public ResponseEntity<Interations> getInterations(@PathVariable Long id) {
        log.debug("REST request to get Interations : {}", id);
        Optional<Interations> interations = interationsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(interations);
    }

    /**
     * {@code DELETE  /interations/:id} : delete the "id" interations.
     *
     * @param id the id of the interations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interations/{id}")
    public ResponseEntity<Void> deleteInterations(@PathVariable Long id) {
        log.debug("REST request to delete Interations : {}", id);
        interationsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
    @PostMapping("/receiveMessage")
    public ResponseMessage receiveMessage(@Valid @RequestBody ReceiveMessageRequest request, @Context HttpServletRequest ipRequest) throws URISyntaxException {
        log.debug("REST request to receiveMessage : {}", request);
        String ip =  GeneralUtils.getClientIpAddress(ipRequest); 
        Interations interations = new Interations();
        interations.setSourceChannel(GeneralUtils.getOriginAplicationValue(request.getSourceChannel()));
        interations.setSourceInfo(request.getSourceInfo());
        interations.setValueRequest(request.getValueRequest());
        
        List<UserExpresion> userExpresions= userExpresionRepository.findByValue(request.getValueRequest());
        if (!userExpresions.isEmpty()) {
            Set<Intent> setIntents = userExpresions.get(0).getIntents();
        	
            List<Intent> intents = GeneralUtils.convertToList(setIntents);
            Intent intent = intents.get(0);
            List<UserResponse> userResponses = GeneralUtils.convertToList(intent.getUserResponses());
            UserResponse userResponse =  UserResponse.getRandomElement(userResponses);
            interations.setValueResponse(userResponse.getValueResponse());
            interationsRepository.save(interations);
            return GeneralUtils.covertToResponseMessage(userResponse);
        }else {
            Date date = new Date();
            List<DefaultResponse> defaultResponses =defaultResponseRepository.findAll();
            Training training = new Training();
            training.setCreationDate(Instant.ofEpochMilli(date.getTime()));
            training.setValue(request.getValueRequest());
            training.setSourceInfo(request.getSourceInfo());
            training.setSourceChannel(GeneralUtils.getOriginAplicationValue(request.getSourceChannel()));
            training.setIp(ip);
            trainingRepository.save(training);
            DefaultResponse defaultResponse = DefaultResponse.getRandomElement(defaultResponses);
            interations.setValueResponse(defaultResponse.getDefaultValueResponse());
            interationsRepository.save(interations);
            return GeneralUtils.covertToResponseMessage(defaultResponse);
        }
    }
    

}