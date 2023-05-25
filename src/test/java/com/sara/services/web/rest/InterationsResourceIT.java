//package com.sara.services.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.sara.services.IntegrationTest;
//import com.sara.services.domain.Interations;
//import com.sara.services.domain.enumeration.SourceChannel;
//import com.sara.services.repository.InterationsRepository;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//import javax.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration tests for the {@link InterationsResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class InterationsResourceIT {
//
//    private static final String DEFAULT_VALUE_REQUEST = "AAAAAAAAAA";
//    private static final String UPDATED_VALUE_REQUEST = "BBBBBBBBBB";
//
//    private static final String DEFAULT_SOURCE_INFO = "AAAAAAAAAA";
//    private static final String UPDATED_SOURCE_INFO = "BBBBBBBBBB";
//
//    private static final String DEFAULT_VALUE_RESPONSE = "AAAAAAAAAA";
//    private static final String UPDATED_VALUE_RESPONSE = "BBBBBBBBBB";
//
//    private static final SourceChannel DEFAULT_SOURCE_CHANNEL = SourceChannel.WHATSAPP;
//    private static final SourceChannel UPDATED_SOURCE_CHANNEL = SourceChannel.TELEGRAM;
//
//    private static final String ENTITY_API_URL = "/api/interations";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private InterationsRepository interationsRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restInterationsMockMvc;
//
//    private Interations interations;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Interations createEntity(EntityManager em) {
//        Interations interations = new Interations()
//            .valueRequest(DEFAULT_VALUE_REQUEST)
//            .sourceInfo(DEFAULT_SOURCE_INFO)
//            .valueResponse(DEFAULT_VALUE_RESPONSE)
//            .sourceChannel(DEFAULT_SOURCE_CHANNEL);
//        return interations;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Interations createUpdatedEntity(EntityManager em) {
//        Interations interations = new Interations()
//            .valueRequest(UPDATED_VALUE_REQUEST)
//            .sourceInfo(UPDATED_SOURCE_INFO)
//            .valueResponse(UPDATED_VALUE_RESPONSE)
//            .sourceChannel(UPDATED_SOURCE_CHANNEL);
//        return interations;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        interations = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createInterations() throws Exception {
//        int databaseSizeBeforeCreate = interationsRepository.findAll().size();
//        // Create the Interations
//        restInterationsMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interations)))
//            .andExpect(status().isCreated());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeCreate + 1);
//        Interations testInterations = interationsList.get(interationsList.size() - 1);
//        assertThat(testInterations.getValueRequest()).isEqualTo(DEFAULT_VALUE_REQUEST);
//        assertThat(testInterations.getSourceInfo()).isEqualTo(DEFAULT_SOURCE_INFO);
//        assertThat(testInterations.getValueResponse()).isEqualTo(DEFAULT_VALUE_RESPONSE);
//        assertThat(testInterations.getSourceChannel()).isEqualTo(DEFAULT_SOURCE_CHANNEL);
//    }
//
//    @Test
//    @Transactional
//    void createInterationsWithExistingId() throws Exception {
//        // Create the Interations with an existing ID
//        interations.setId(1L);
//
//        int databaseSizeBeforeCreate = interationsRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restInterationsMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interations)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkValueRequestIsRequired() throws Exception {
//        int databaseSizeBeforeTest = interationsRepository.findAll().size();
//        // set the field null
//        interations.setValueRequest(null);
//
//        // Create the Interations, which fails.
//
//        restInterationsMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interations)))
//            .andExpect(status().isBadRequest());
//
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkSourceInfoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = interationsRepository.findAll().size();
//        // set the field null
//        interations.setSourceInfo(null);
//
//        // Create the Interations, which fails.
//
//        restInterationsMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interations)))
//            .andExpect(status().isBadRequest());
//
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkValueResponseIsRequired() throws Exception {
//        int databaseSizeBeforeTest = interationsRepository.findAll().size();
//        // set the field null
//        interations.setValueResponse(null);
//
//        // Create the Interations, which fails.
//
//        restInterationsMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interations)))
//            .andExpect(status().isBadRequest());
//
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllInterations() throws Exception {
//        // Initialize the database
//        interationsRepository.saveAndFlush(interations);
//
//        // Get all the interationsList
//        restInterationsMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(interations.getId().intValue())))
//            .andExpect(jsonPath("$.[*].valueRequest").value(hasItem(DEFAULT_VALUE_REQUEST)))
//            .andExpect(jsonPath("$.[*].sourceInfo").value(hasItem(DEFAULT_SOURCE_INFO)))
//            .andExpect(jsonPath("$.[*].valueResponse").value(hasItem(DEFAULT_VALUE_RESPONSE)))
//            .andExpect(jsonPath("$.[*].sourceChannel").value(hasItem(DEFAULT_SOURCE_CHANNEL.toString())));
//    }
//
//    @Test
//    @Transactional
//    void getInterations() throws Exception {
//        // Initialize the database
//        interationsRepository.saveAndFlush(interations);
//
//        // Get the interations
//        restInterationsMockMvc
//            .perform(get(ENTITY_API_URL_ID, interations.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(interations.getId().intValue()))
//            .andExpect(jsonPath("$.valueRequest").value(DEFAULT_VALUE_REQUEST))
//            .andExpect(jsonPath("$.sourceInfo").value(DEFAULT_SOURCE_INFO))
//            .andExpect(jsonPath("$.valueResponse").value(DEFAULT_VALUE_RESPONSE))
//            .andExpect(jsonPath("$.sourceChannel").value(DEFAULT_SOURCE_CHANNEL.toString()));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingInterations() throws Exception {
//        // Get the interations
//        restInterationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingInterations() throws Exception {
//        // Initialize the database
//        interationsRepository.saveAndFlush(interations);
//
//        int databaseSizeBeforeUpdate = interationsRepository.findAll().size();
//
//        // Update the interations
//        Interations updatedInterations = interationsRepository.findById(interations.getId()).get();
//        // Disconnect from session so that the updates on updatedInterations are not directly saved in db
//        em.detach(updatedInterations);
//        updatedInterations
//            .valueRequest(UPDATED_VALUE_REQUEST)
//            .sourceInfo(UPDATED_SOURCE_INFO)
//            .valueResponse(UPDATED_VALUE_RESPONSE)
//            .sourceChannel(UPDATED_SOURCE_CHANNEL);
//
//        restInterationsMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedInterations.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedInterations))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeUpdate);
//        Interations testInterations = interationsList.get(interationsList.size() - 1);
//        assertThat(testInterations.getValueRequest()).isEqualTo(UPDATED_VALUE_REQUEST);
//        assertThat(testInterations.getSourceInfo()).isEqualTo(UPDATED_SOURCE_INFO);
//        assertThat(testInterations.getValueResponse()).isEqualTo(UPDATED_VALUE_RESPONSE);
//        assertThat(testInterations.getSourceChannel()).isEqualTo(UPDATED_SOURCE_CHANNEL);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingInterations() throws Exception {
//        int databaseSizeBeforeUpdate = interationsRepository.findAll().size();
//        interations.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restInterationsMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, interations.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(interations))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchInterations() throws Exception {
//        int databaseSizeBeforeUpdate = interationsRepository.findAll().size();
//        interations.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restInterationsMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(interations))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamInterations() throws Exception {
//        int databaseSizeBeforeUpdate = interationsRepository.findAll().size();
//        interations.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restInterationsMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interations)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateInterationsWithPatch() throws Exception {
//        // Initialize the database
//        interationsRepository.saveAndFlush(interations);
//
//        int databaseSizeBeforeUpdate = interationsRepository.findAll().size();
//
//        // Update the interations using partial update
//        Interations partialUpdatedInterations = new Interations();
//        partialUpdatedInterations.setId(interations.getId());
//
//        partialUpdatedInterations.sourceInfo(UPDATED_SOURCE_INFO).valueResponse(UPDATED_VALUE_RESPONSE);
//
//        restInterationsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedInterations.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInterations))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeUpdate);
//        Interations testInterations = interationsList.get(interationsList.size() - 1);
//        assertThat(testInterations.getValueRequest()).isEqualTo(DEFAULT_VALUE_REQUEST);
//        assertThat(testInterations.getSourceInfo()).isEqualTo(UPDATED_SOURCE_INFO);
//        assertThat(testInterations.getValueResponse()).isEqualTo(UPDATED_VALUE_RESPONSE);
//        assertThat(testInterations.getSourceChannel()).isEqualTo(DEFAULT_SOURCE_CHANNEL);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateInterationsWithPatch() throws Exception {
//        // Initialize the database
//        interationsRepository.saveAndFlush(interations);
//
//        int databaseSizeBeforeUpdate = interationsRepository.findAll().size();
//
//        // Update the interations using partial update
//        Interations partialUpdatedInterations = new Interations();
//        partialUpdatedInterations.setId(interations.getId());
//
//        partialUpdatedInterations
//            .valueRequest(UPDATED_VALUE_REQUEST)
//            .sourceInfo(UPDATED_SOURCE_INFO)
//            .valueResponse(UPDATED_VALUE_RESPONSE)
//            .sourceChannel(UPDATED_SOURCE_CHANNEL);
//
//        restInterationsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedInterations.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInterations))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeUpdate);
//        Interations testInterations = interationsList.get(interationsList.size() - 1);
//        assertThat(testInterations.getValueRequest()).isEqualTo(UPDATED_VALUE_REQUEST);
//        assertThat(testInterations.getSourceInfo()).isEqualTo(UPDATED_SOURCE_INFO);
//        assertThat(testInterations.getValueResponse()).isEqualTo(UPDATED_VALUE_RESPONSE);
//        assertThat(testInterations.getSourceChannel()).isEqualTo(UPDATED_SOURCE_CHANNEL);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingInterations() throws Exception {
//        int databaseSizeBeforeUpdate = interationsRepository.findAll().size();
//        interations.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restInterationsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, interations.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(interations))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchInterations() throws Exception {
//        int databaseSizeBeforeUpdate = interationsRepository.findAll().size();
//        interations.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restInterationsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(interations))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamInterations() throws Exception {
//        int databaseSizeBeforeUpdate = interationsRepository.findAll().size();
//        interations.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restInterationsMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(interations))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Interations in the database
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteInterations() throws Exception {
//        // Initialize the database
//        interationsRepository.saveAndFlush(interations);
//
//        int databaseSizeBeforeDelete = interationsRepository.findAll().size();
//
//        // Delete the interations
//        restInterationsMockMvc
//            .perform(delete(ENTITY_API_URL_ID, interations.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Interations> interationsList = interationsRepository.findAll();
//        assertThat(interationsList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
