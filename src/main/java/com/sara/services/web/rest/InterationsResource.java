package com.sara.services.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sapi.services.integration.methods.Sapi;
import com.sapi.services.integration.response.ResponseGeneral;
import com.sara.services.domain.DefaultResponse;
import com.sara.services.domain.Intent;
import com.sara.services.domain.Interations;
import com.sara.services.domain.Training;
import com.sara.services.domain.UserExpresion;
import com.sara.services.domain.UserResponse;
import com.sara.services.domain.enumeration.ResponseType;
import com.sara.services.repository.DefaultResponseRepository;
import com.sara.services.repository.InterationsRepository;
import com.sara.services.repository.TrainingRepository;
import com.sara.services.repository.UserExpresionRepository;
import com.sara.services.service.InterationsQueryService;
import com.sara.services.service.InterationsService;
import com.sara.services.service.UserExpresionService;
import com.sara.services.service.criteria.InterationsCriteria;
import com.sara.services.web.rest.Util.GeneralUtils;
import com.sara.services.web.rest.errors.BadRequestAlertException;
import com.sara.services.web.rest.request.ReceiveMessageRequest;
import com.sara.services.web.rest.response.ResponseMessage;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sara.services.domain.Interations}.
 */
@RestController
@RequestMapping("/api")
public class InterationsResource {

    private final Logger log = LoggerFactory.getLogger(InterationsResource.class);

    private static final String ENTITY_NAME = "interations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterationsService interationsService;

    private final InterationsRepository interationsRepository;

    private final InterationsQueryService interationsQueryService;
    
    private final UserExpresionService userExpresionService;
    
    private final DefaultResponseRepository defaultResponseRepository;
    
    private final TrainingRepository trainingRepository;

    public InterationsResource(
        InterationsService interationsService,InterationsRepository interationsRepository,
        InterationsQueryService interationsQueryService, UserExpresionService userExpresionService,
		DefaultResponseRepository defaultResponseRepository, TrainingRepository trainingRepository
    ) {
        this.interationsService = interationsService;
        this.interationsRepository = interationsRepository;
        this.interationsQueryService = interationsQueryService;
        this.userExpresionService = userExpresionService;
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
        Interations result = interationsService.save(interations);
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

        Interations result = interationsService.update(interations);
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

        Optional<Interations> result = interationsService.partialUpdate(interations);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, interations.getId().toString())
        );
    }

    /**
     * {@code GET  /interations} : get all the interations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interations in body.
     */
    @GetMapping("/interations")
    public ResponseEntity<List<Interations>> getAllInterations(
        InterationsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Interations by criteria: {}", criteria);
        Page<Interations> page = interationsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /interations/count} : count all the interations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/interations/count")
    public ResponseEntity<Long> countInterations(InterationsCriteria criteria) {
        log.debug("REST request to count Interations by criteria: {}", criteria);
        return ResponseEntity.ok().body(interationsQueryService.countByCriteria(criteria));
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
        Optional<Interations> interations = interationsService.findOne(id);
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
        interationsService.delete(id);
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
        String[] fields = request.getValueRequest().split("&");
        List<UserExpresion> userExpresions= userExpresionService.findByValue(fields[0]);
        if (!userExpresions.isEmpty()) {
            log.info("Encontro userExpresion asociada");
            Set<Intent> setIntents = userExpresions.get(0).getIntents();
            if (!setIntents.isEmpty()){
                List<Intent> intents = GeneralUtils.convertToList(setIntents);
                Intent intent = intents.get(0);
                List<UserResponse> userResponses = GeneralUtils.convertToList(intent.getUserResponses());
                UserResponse userResponse =  UserResponse.getRandomElement(userResponses);
                if (userResponse.getResponseType().equals(ResponseType.QUERY)){
                    log.info("UserResponse de tipo Query");
                    interations.setValueResponse(userResponse.getValueResponse());
                }else{           
                    try {
                        log.info("UserResponse de tipo Services");
                        ResponseGeneral response = Sapi.getGeneric(6000,  fields[1], userResponse.getUrl());

                        if (response!=null && response.getResponseData()!=null && response.getResponseData().length>0){
                            log.info("UserResponse respuesta"+ response.toString());
                            log.info("UserResponse cantidad"+ response.getResponseData().length);
                            userResponse.setValueResponse(response.toString());
                            interations.setValueResponse(response.toString());
                        }else{
                            log.info("UserResponse vacio");
                             userResponse.setValueResponse("Informacion no encontrada");
                            interations.setValueResponse("Informacion no encontrada");
                        }
                        log.info("UserResponse sin excepcion");
                    } catch (Exception e) {
                        log.info("UserResponse Informacion no encontrada");
                        userResponse.setValueResponse("Informacion no encontrada");
                        interations.setValueResponse("Informacion no encontrada");
                    }
                }           
                interationsRepository.save(interations);
                return GeneralUtils.covertToResponseMessage(userResponse);
            } else {
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
