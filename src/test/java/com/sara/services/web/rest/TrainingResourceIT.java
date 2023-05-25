//package com.sara.services.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.sara.services.IntegrationTest;
//import com.sara.services.domain.Training;
//import com.sara.services.domain.enumeration.SourceChannel;
//import com.sara.services.repository.TrainingRepository;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
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
// * Integration tests for the {@link TrainingResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class TrainingResourceIT {
//
//    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
//    private static final String UPDATED_VALUE = "BBBBBBBBBB";
//
//    private static final SourceChannel DEFAULT_SOURCE_CHANNEL = SourceChannel.WHATSAPP;
//    private static final SourceChannel UPDATED_SOURCE_CHANNEL = SourceChannel.TELEGRAM;
//
//    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String DEFAULT_IP = "AAAAAAAAAA";
//    private static final String UPDATED_IP = "BBBBBBBBBB";
//
//    private static final Float DEFAULT_POSTION_X = 1F;
//    private static final Float UPDATED_POSTION_X = 2F;
//
//    private static final Float DEFAULT_POSTION_Y = 1F;
//    private static final Float UPDATED_POSTION_Y = 2F;
//
//    private static final String DEFAULT_SOURCE_INFO = "AAAAAAAAAA";
//    private static final String UPDATED_SOURCE_INFO = "BBBBBBBBBB";
//
//    private static final String ENTITY_API_URL = "/api/trainings";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private TrainingRepository trainingRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restTrainingMockMvc;
//
//    private Training training;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Training createEntity(EntityManager em) {
//        Training training = new Training()
//            .value(DEFAULT_VALUE)
//            .sourceChannel(DEFAULT_SOURCE_CHANNEL)
//            .creationDate(DEFAULT_CREATION_DATE)
//            .ip(DEFAULT_IP)
//            .postionX(DEFAULT_POSTION_X)
//            .postionY(DEFAULT_POSTION_Y)
//            .sourceInfo(DEFAULT_SOURCE_INFO);
//        return training;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Training createUpdatedEntity(EntityManager em) {
//        Training training = new Training()
//            .value(UPDATED_VALUE)
//            .sourceChannel(UPDATED_SOURCE_CHANNEL)
//            .creationDate(UPDATED_CREATION_DATE)
//            .ip(UPDATED_IP)
//            .postionX(UPDATED_POSTION_X)
//            .postionY(UPDATED_POSTION_Y)
//            .sourceInfo(UPDATED_SOURCE_INFO);
//        return training;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        training = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createTraining() throws Exception {
//        int databaseSizeBeforeCreate = trainingRepository.findAll().size();
//        // Create the Training
//        restTrainingMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(training)))
//            .andExpect(status().isCreated());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeCreate + 1);
//        Training testTraining = trainingList.get(trainingList.size() - 1);
//        assertThat(testTraining.getValue()).isEqualTo(DEFAULT_VALUE);
//        assertThat(testTraining.getSourceChannel()).isEqualTo(DEFAULT_SOURCE_CHANNEL);
//        assertThat(testTraining.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
//        assertThat(testTraining.getIp()).isEqualTo(DEFAULT_IP);
//        assertThat(testTraining.getPostionX()).isEqualTo(DEFAULT_POSTION_X);
//        assertThat(testTraining.getPostionY()).isEqualTo(DEFAULT_POSTION_Y);
//        assertThat(testTraining.getSourceInfo()).isEqualTo(DEFAULT_SOURCE_INFO);
//    }
//
//    @Test
//    @Transactional
//    void createTrainingWithExistingId() throws Exception {
//        // Create the Training with an existing ID
//        training.setId(1L);
//
//        int databaseSizeBeforeCreate = trainingRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restTrainingMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(training)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkValueIsRequired() throws Exception {
//        int databaseSizeBeforeTest = trainingRepository.findAll().size();
//        // set the field null
//        training.setValue(null);
//
//        // Create the Training, which fails.
//
//        restTrainingMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(training)))
//            .andExpect(status().isBadRequest());
//
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkIpIsRequired() throws Exception {
//        int databaseSizeBeforeTest = trainingRepository.findAll().size();
//        // set the field null
//        training.setIp(null);
//
//        // Create the Training, which fails.
//
//        restTrainingMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(training)))
//            .andExpect(status().isBadRequest());
//
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkSourceInfoIsRequired() throws Exception {
//        int databaseSizeBeforeTest = trainingRepository.findAll().size();
//        // set the field null
//        training.setSourceInfo(null);
//
//        // Create the Training, which fails.
//
//        restTrainingMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(training)))
//            .andExpect(status().isBadRequest());
//
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllTrainings() throws Exception {
//        // Initialize the database
//        trainingRepository.saveAndFlush(training);
//
//        // Get all the trainingList
//        restTrainingMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(training.getId().intValue())))
//            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
//            .andExpect(jsonPath("$.[*].sourceChannel").value(hasItem(DEFAULT_SOURCE_CHANNEL.toString())))
//            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
//            .andExpect(jsonPath("$.[*].ip").value(hasItem(DEFAULT_IP)))
//            .andExpect(jsonPath("$.[*].postionX").value(hasItem(DEFAULT_POSTION_X.doubleValue())))
//            .andExpect(jsonPath("$.[*].postionY").value(hasItem(DEFAULT_POSTION_Y.doubleValue())))
//            .andExpect(jsonPath("$.[*].sourceInfo").value(hasItem(DEFAULT_SOURCE_INFO)));
//    }
//
//    @Test
//    @Transactional
//    void getTraining() throws Exception {
//        // Initialize the database
//        trainingRepository.saveAndFlush(training);
//
//        // Get the training
//        restTrainingMockMvc
//            .perform(get(ENTITY_API_URL_ID, training.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(training.getId().intValue()))
//            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
//            .andExpect(jsonPath("$.sourceChannel").value(DEFAULT_SOURCE_CHANNEL.toString()))
//            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
//            .andExpect(jsonPath("$.ip").value(DEFAULT_IP))
//            .andExpect(jsonPath("$.postionX").value(DEFAULT_POSTION_X.doubleValue()))
//            .andExpect(jsonPath("$.postionY").value(DEFAULT_POSTION_Y.doubleValue()))
//            .andExpect(jsonPath("$.sourceInfo").value(DEFAULT_SOURCE_INFO));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingTraining() throws Exception {
//        // Get the training
//        restTrainingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingTraining() throws Exception {
//        // Initialize the database
//        trainingRepository.saveAndFlush(training);
//
//        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
//
//        // Update the training
//        Training updatedTraining = trainingRepository.findById(training.getId()).get();
//        // Disconnect from session so that the updates on updatedTraining are not directly saved in db
//        em.detach(updatedTraining);
//        updatedTraining
//            .value(UPDATED_VALUE)
//            .sourceChannel(UPDATED_SOURCE_CHANNEL)
//            .creationDate(UPDATED_CREATION_DATE)
//            .ip(UPDATED_IP)
//            .postionX(UPDATED_POSTION_X)
//            .postionY(UPDATED_POSTION_Y)
//            .sourceInfo(UPDATED_SOURCE_INFO);
//
//        restTrainingMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedTraining.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedTraining))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
//        Training testTraining = trainingList.get(trainingList.size() - 1);
//        assertThat(testTraining.getValue()).isEqualTo(UPDATED_VALUE);
//        assertThat(testTraining.getSourceChannel()).isEqualTo(UPDATED_SOURCE_CHANNEL);
//        assertThat(testTraining.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
//        assertThat(testTraining.getIp()).isEqualTo(UPDATED_IP);
//        assertThat(testTraining.getPostionX()).isEqualTo(UPDATED_POSTION_X);
//        assertThat(testTraining.getPostionY()).isEqualTo(UPDATED_POSTION_Y);
//        assertThat(testTraining.getSourceInfo()).isEqualTo(UPDATED_SOURCE_INFO);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingTraining() throws Exception {
//        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
//        training.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restTrainingMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, training.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(training))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchTraining() throws Exception {
//        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
//        training.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restTrainingMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(training))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamTraining() throws Exception {
//        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
//        training.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restTrainingMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(training)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateTrainingWithPatch() throws Exception {
//        // Initialize the database
//        trainingRepository.saveAndFlush(training);
//
//        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
//
//        // Update the training using partial update
//        Training partialUpdatedTraining = new Training();
//        partialUpdatedTraining.setId(training.getId());
//
//        partialUpdatedTraining
//            .value(UPDATED_VALUE)
//            .sourceChannel(UPDATED_SOURCE_CHANNEL)
//            .ip(UPDATED_IP)
//            .postionX(UPDATED_POSTION_X)
//            .sourceInfo(UPDATED_SOURCE_INFO);
//
//        restTrainingMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedTraining.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraining))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
//        Training testTraining = trainingList.get(trainingList.size() - 1);
//        assertThat(testTraining.getValue()).isEqualTo(UPDATED_VALUE);
//        assertThat(testTraining.getSourceChannel()).isEqualTo(UPDATED_SOURCE_CHANNEL);
//        assertThat(testTraining.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
//        assertThat(testTraining.getIp()).isEqualTo(UPDATED_IP);
//        assertThat(testTraining.getPostionX()).isEqualTo(UPDATED_POSTION_X);
//        assertThat(testTraining.getPostionY()).isEqualTo(DEFAULT_POSTION_Y);
//        assertThat(testTraining.getSourceInfo()).isEqualTo(UPDATED_SOURCE_INFO);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateTrainingWithPatch() throws Exception {
//        // Initialize the database
//        trainingRepository.saveAndFlush(training);
//
//        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
//
//        // Update the training using partial update
//        Training partialUpdatedTraining = new Training();
//        partialUpdatedTraining.setId(training.getId());
//
//        partialUpdatedTraining
//            .value(UPDATED_VALUE)
//            .sourceChannel(UPDATED_SOURCE_CHANNEL)
//            .creationDate(UPDATED_CREATION_DATE)
//            .ip(UPDATED_IP)
//            .postionX(UPDATED_POSTION_X)
//            .postionY(UPDATED_POSTION_Y)
//            .sourceInfo(UPDATED_SOURCE_INFO);
//
//        restTrainingMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedTraining.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTraining))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
//        Training testTraining = trainingList.get(trainingList.size() - 1);
//        assertThat(testTraining.getValue()).isEqualTo(UPDATED_VALUE);
//        assertThat(testTraining.getSourceChannel()).isEqualTo(UPDATED_SOURCE_CHANNEL);
//        assertThat(testTraining.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
//        assertThat(testTraining.getIp()).isEqualTo(UPDATED_IP);
//        assertThat(testTraining.getPostionX()).isEqualTo(UPDATED_POSTION_X);
//        assertThat(testTraining.getPostionY()).isEqualTo(UPDATED_POSTION_Y);
//        assertThat(testTraining.getSourceInfo()).isEqualTo(UPDATED_SOURCE_INFO);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingTraining() throws Exception {
//        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
//        training.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restTrainingMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, training.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(training))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchTraining() throws Exception {
//        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
//        training.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restTrainingMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(training))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamTraining() throws Exception {
//        int databaseSizeBeforeUpdate = trainingRepository.findAll().size();
//        training.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restTrainingMockMvc
//            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(training)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Training in the database
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteTraining() throws Exception {
//        // Initialize the database
//        trainingRepository.saveAndFlush(training);
//
//        int databaseSizeBeforeDelete = trainingRepository.findAll().size();
//
//        // Delete the training
//        restTrainingMockMvc
//            .perform(delete(ENTITY_API_URL_ID, training.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Training> trainingList = trainingRepository.findAll();
//        assertThat(trainingList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
