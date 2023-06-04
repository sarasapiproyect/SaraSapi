package com.sara.services.web.rest;

import com.sara.services.domain.UserExpresion;
import com.sara.services.repository.UserExpresionRepository;
import com.sara.services.service.UserExpresionQueryService;
import com.sara.services.service.UserExpresionService;
import com.sara.services.service.criteria.UserExpresionCriteria;
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
 * REST controller for managing {@link com.sara.services.domain.UserExpresion}.
 */
@RestController
@RequestMapping("/api")
public class UserExpresionResource {

    private final Logger log = LoggerFactory.getLogger(UserExpresionResource.class);

    private static final String ENTITY_NAME = "userExpresion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserExpresionService userExpresionService;

    private final UserExpresionRepository userExpresionRepository;

    private final UserExpresionQueryService userExpresionQueryService;

    public UserExpresionResource(
        UserExpresionService userExpresionService,
        UserExpresionRepository userExpresionRepository,
        UserExpresionQueryService userExpresionQueryService
    ) {
        this.userExpresionService = userExpresionService;
        this.userExpresionRepository = userExpresionRepository;
        this.userExpresionQueryService = userExpresionQueryService;
    }

    /**
     * {@code POST  /user-expresions} : Create a new userExpresion.
     *
     * @param userExpresion the userExpresion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userExpresion, or with status {@code 400 (Bad Request)} if the userExpresion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-expresions")
    public ResponseEntity<UserExpresion> createUserExpresion(@Valid @RequestBody UserExpresion userExpresion) throws URISyntaxException {
        log.debug("REST request to save UserExpresion : {}", userExpresion);
        if (userExpresion.getId() != null) {
            throw new BadRequestAlertException("A new userExpresion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<UserExpresion> userExpresionValue= userExpresionRepository.findByValue(userExpresion.getValue());
        if (!userExpresionValue.isEmpty())
        	throw new BadRequestAlertException("UserExpresion exists", ENTITY_NAME, "userExpresionexists");
        UserExpresion result = userExpresionService.save(userExpresion);
        return ResponseEntity
            .created(new URI("/api/user-expresions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-expresions/:id} : Updates an existing userExpresion.
     *
     * @param id the id of the userExpresion to save.
     * @param userExpresion the userExpresion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExpresion,
     * or with status {@code 400 (Bad Request)} if the userExpresion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userExpresion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-expresions/{id}")
    public ResponseEntity<UserExpresion> updateUserExpresion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserExpresion userExpresion
    ) throws URISyntaxException {
        log.debug("REST request to update UserExpresion : {}, {}", id, userExpresion);
        if (userExpresion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userExpresion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userExpresionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserExpresion result = userExpresionService.update(userExpresion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userExpresion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-expresions/:id} : Partial updates given fields of an existing userExpresion, field will ignore if it is null
     *
     * @param id the id of the userExpresion to save.
     * @param userExpresion the userExpresion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userExpresion,
     * or with status {@code 400 (Bad Request)} if the userExpresion is not valid,
     * or with status {@code 404 (Not Found)} if the userExpresion is not found,
     * or with status {@code 500 (Internal Server Error)} if the userExpresion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-expresions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserExpresion> partialUpdateUserExpresion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserExpresion userExpresion
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserExpresion partially : {}, {}", id, userExpresion);
        if (userExpresion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userExpresion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userExpresionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserExpresion> result = userExpresionService.partialUpdate(userExpresion);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userExpresion.getId().toString())
        );
    }

    /**
     * {@code GET  /user-expresions} : get all the userExpresions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userExpresions in body.
     */
    @GetMapping("/user-expresions")
    public ResponseEntity<List<UserExpresion>> getAllUserExpresions(
        UserExpresionCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UserExpresions by criteria: {}", criteria);
        Page<UserExpresion> page = userExpresionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-expresions/count} : count all the userExpresions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-expresions/count")
    public ResponseEntity<Long> countUserExpresions(UserExpresionCriteria criteria) {
        log.debug("REST request to count UserExpresions by criteria: {}", criteria);
        return ResponseEntity.ok().body(userExpresionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-expresions/:id} : get the "id" userExpresion.
     *
     * @param id the id of the userExpresion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userExpresion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-expresions/{id}")
    public ResponseEntity<UserExpresion> getUserExpresion(@PathVariable Long id) {
        log.debug("REST request to get UserExpresion : {}", id);
        Optional<UserExpresion> userExpresion = userExpresionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userExpresion);
    }

    /**
     * {@code DELETE  /user-expresions/:id} : delete the "id" userExpresion.
     *
     * @param id the id of the userExpresion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-expresions/{id}")
    public ResponseEntity<Void> deleteUserExpresion(@PathVariable Long id) {
        log.debug("REST request to delete UserExpresion : {}", id);
        userExpresionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
