package com.sara.services.web.rest;

import com.sara.services.domain.Intent;
import com.sara.services.repository.IntentRepository;
import com.sara.services.service.IntentQueryService;
import com.sara.services.service.IntentService;
import com.sara.services.service.criteria.IntentCriteria;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sara.services.domain.Intent}.
 */
@RestController
@RequestMapping("/api")
public class IntentResource {

    private final Logger log = LoggerFactory.getLogger(IntentResource.class);

    private static final String ENTITY_NAME = "intent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntentService intentService;

    private final IntentRepository intentRepository;

    private final IntentQueryService intentQueryService;

    public IntentResource(IntentService intentService, IntentRepository intentRepository, IntentQueryService intentQueryService) {
        this.intentService = intentService;
        this.intentRepository = intentRepository;
        this.intentQueryService = intentQueryService;
    }

    /**
     * {@code POST  /intents} : Create a new intent.
     *
     * @param intent the intent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new intent, or with status {@code 400 (Bad Request)} if the intent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/intents")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Intent> createIntent(@Valid @RequestBody Intent intent) throws URISyntaxException {
        log.debug("REST request to save Intent : {}", intent);
        if (intent.getId() != null) {
            throw new BadRequestAlertException("A new intent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Intent result = intentService.save(intent);
        return ResponseEntity
            .created(new URI("/api/intents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /intents/:id} : Updates an existing intent.
     *
     * @param id the id of the intent to save.
     * @param intent the intent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intent,
     * or with status {@code 400 (Bad Request)} if the intent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the intent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/intents/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Intent> updateIntent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Intent intent
    ) throws URISyntaxException {
        log.debug("REST request to update Intent : {}, {}", id, intent);
        if (intent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Intent result = intentService.update(intent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /intents/:id} : Partial updates given fields of an existing intent, field will ignore if it is null
     *
     * @param id the id of the intent to save.
     * @param intent the intent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated intent,
     * or with status {@code 400 (Bad Request)} if the intent is not valid,
     * or with status {@code 404 (Not Found)} if the intent is not found,
     * or with status {@code 500 (Internal Server Error)} if the intent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/intents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Intent> partialUpdateIntent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Intent intent
    ) throws URISyntaxException {
        log.debug("REST request to partial update Intent partially : {}, {}", id, intent);
        if (intent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, intent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!intentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Intent> result = intentService.partialUpdate(intent);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, intent.getId().toString())
        );
    }

    /**
     * {@code GET  /intents} : get all the intents.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of intents in body.
     */
    @GetMapping("/intents")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Intent>> getAllIntents(
        IntentCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Intents by criteria: {}", criteria);
        Page<Intent> page = intentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /intents/count} : count all the intents.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/intents/count")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Long> countIntents(IntentCriteria criteria) {
        log.debug("REST request to count Intents by criteria: {}", criteria);
        return ResponseEntity.ok().body(intentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /intents/:id} : get the "id" intent.
     *
     * @param id the id of the intent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the intent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/intents/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Intent> getIntent(@PathVariable Long id) {
        log.debug("REST request to get Intent : {}", id);
        Optional<Intent> intent = intentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(intent);
    }

    /**
     * {@code DELETE  /intents/:id} : delete the "id" intent.
     *
     * @param id the id of the intent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/intents/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteIntent(@PathVariable Long id) {
        log.debug("REST request to delete Intent : {}", id);
        intentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
