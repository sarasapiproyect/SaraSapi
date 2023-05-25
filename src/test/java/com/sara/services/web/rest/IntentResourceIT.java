//package com.sara.services.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.sara.services.IntegrationTest;
//import com.sara.services.domain.Intent;
//import com.sara.services.domain.enumeration.IntentType;
//import com.sara.services.repository.IntentRepository;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.atomic.AtomicLong;
//import javax.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration tests for the {@link IntentResource} REST controller.
// */
//@IntegrationTest
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc
//@WithMockUser
//class IntentResourceIT {
//
//    private static final IntentType DEFAULT_INTEN_TYPE = IntentType.INFO;
//    private static final IntentType UPDATED_INTEN_TYPE = IntentType.BACK_END_INFO;
//
//    private static final String DEFAULT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
//
//    private static final String DEFAULT_URL_REQUEST = "AAAAAAAAAA";
//    private static final String UPDATED_URL_REQUEST = "BBBBBBBBBB";
//
//    private static final Boolean DEFAULT_ENABLED = false;
//    private static final Boolean UPDATED_ENABLED = true;
//
//    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String ENTITY_API_URL = "/api/intents";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private IntentRepository intentRepository;
//
//    @Mock
//    private IntentRepository intentRepositoryMock;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restIntentMockMvc;
//
//    private Intent intent;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Intent createEntity(EntityManager em) {
//        Intent intent = new Intent()
//            .intenType(DEFAULT_INTEN_TYPE)
//            .name(DEFAULT_NAME)
//            .description(DEFAULT_DESCRIPTION)
//            .urlRequest(DEFAULT_URL_REQUEST)
//            .enabled(DEFAULT_ENABLED)
//            .creationDate(DEFAULT_CREATION_DATE);
//        return intent;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Intent createUpdatedEntity(EntityManager em) {
//        Intent intent = new Intent()
//            .intenType(UPDATED_INTEN_TYPE)
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .urlRequest(UPDATED_URL_REQUEST)
//            .enabled(UPDATED_ENABLED)
//            .creationDate(UPDATED_CREATION_DATE);
//        return intent;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        intent = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createIntent() throws Exception {
//        int databaseSizeBeforeCreate = intentRepository.findAll().size();
//        // Create the Intent
//        restIntentMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intent)))
//            .andExpect(status().isCreated());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeCreate + 1);
//        Intent testIntent = intentList.get(intentList.size() - 1);
//        assertThat(testIntent.getIntenType()).isEqualTo(DEFAULT_INTEN_TYPE);
//        assertThat(testIntent.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testIntent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testIntent.getUrlRequest()).isEqualTo(DEFAULT_URL_REQUEST);
//        assertThat(testIntent.getEnabled()).isEqualTo(DEFAULT_ENABLED);
//        assertThat(testIntent.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
//    }
//
//    @Test
//    @Transactional
//    void createIntentWithExistingId() throws Exception {
//        // Create the Intent with an existing ID
//        intent.setId(1L);
//
//        int databaseSizeBeforeCreate = intentRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restIntentMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intent)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = intentRepository.findAll().size();
//        // set the field null
//        intent.setName(null);
//
//        // Create the Intent, which fails.
//
//        restIntentMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intent)))
//            .andExpect(status().isBadRequest());
//
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkDescriptionIsRequired() throws Exception {
//        int databaseSizeBeforeTest = intentRepository.findAll().size();
//        // set the field null
//        intent.setDescription(null);
//
//        // Create the Intent, which fails.
//
//        restIntentMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intent)))
//            .andExpect(status().isBadRequest());
//
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkUrlRequestIsRequired() throws Exception {
//        int databaseSizeBeforeTest = intentRepository.findAll().size();
//        // set the field null
//        intent.setUrlRequest(null);
//
//        // Create the Intent, which fails.
//
//        restIntentMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intent)))
//            .andExpect(status().isBadRequest());
//
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllIntents() throws Exception {
//        // Initialize the database
//        intentRepository.saveAndFlush(intent);
//
//        // Get all the intentList
//        restIntentMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(intent.getId().intValue())))
//            .andExpect(jsonPath("$.[*].intenType").value(hasItem(DEFAULT_INTEN_TYPE.toString())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].urlRequest").value(hasItem(DEFAULT_URL_REQUEST)))
//            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
//            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())));
//    }
//
//    @SuppressWarnings({ "unchecked" })
//    void getAllIntentsWithEagerRelationshipsIsEnabled() throws Exception {
//        when(intentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
//
//        restIntentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());
//
//        verify(intentRepositoryMock, times(1)).findAllWithEagerRelationships(any());
//    }
//
//    @SuppressWarnings({ "unchecked" })
//    void getAllIntentsWithEagerRelationshipsIsNotEnabled() throws Exception {
//        when(intentRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
//
//        restIntentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
//        verify(intentRepositoryMock, times(1)).findAll(any(Pageable.class));
//    }
//
//    @Test
//    @Transactional
//    void getIntent() throws Exception {
//        // Initialize the database
//        intentRepository.saveAndFlush(intent);
//
//        // Get the intent
//        restIntentMockMvc
//            .perform(get(ENTITY_API_URL_ID, intent.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(intent.getId().intValue()))
//            .andExpect(jsonPath("$.intenType").value(DEFAULT_INTEN_TYPE.toString()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
//            .andExpect(jsonPath("$.urlRequest").value(DEFAULT_URL_REQUEST))
//            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
//            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingIntent() throws Exception {
//        // Get the intent
//        restIntentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingIntent() throws Exception {
//        // Initialize the database
//        intentRepository.saveAndFlush(intent);
//
//        int databaseSizeBeforeUpdate = intentRepository.findAll().size();
//
//        // Update the intent
//        Intent updatedIntent = intentRepository.findById(intent.getId()).get();
//        // Disconnect from session so that the updates on updatedIntent are not directly saved in db
//        em.detach(updatedIntent);
//        updatedIntent
//            .intenType(UPDATED_INTEN_TYPE)
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .urlRequest(UPDATED_URL_REQUEST)
//            .enabled(UPDATED_ENABLED)
//            .creationDate(UPDATED_CREATION_DATE);
//
//        restIntentMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedIntent.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedIntent))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
//        Intent testIntent = intentList.get(intentList.size() - 1);
//        assertThat(testIntent.getIntenType()).isEqualTo(UPDATED_INTEN_TYPE);
//        assertThat(testIntent.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testIntent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testIntent.getUrlRequest()).isEqualTo(UPDATED_URL_REQUEST);
//        assertThat(testIntent.getEnabled()).isEqualTo(UPDATED_ENABLED);
//        assertThat(testIntent.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingIntent() throws Exception {
//        int databaseSizeBeforeUpdate = intentRepository.findAll().size();
//        intent.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restIntentMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, intent.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(intent))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchIntent() throws Exception {
//        int databaseSizeBeforeUpdate = intentRepository.findAll().size();
//        intent.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restIntentMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(intent))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamIntent() throws Exception {
//        int databaseSizeBeforeUpdate = intentRepository.findAll().size();
//        intent.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restIntentMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(intent)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateIntentWithPatch() throws Exception {
//        // Initialize the database
//        intentRepository.saveAndFlush(intent);
//
//        int databaseSizeBeforeUpdate = intentRepository.findAll().size();
//
//        // Update the intent using partial update
//        Intent partialUpdatedIntent = new Intent();
//        partialUpdatedIntent.setId(intent.getId());
//
//        partialUpdatedIntent.enabled(UPDATED_ENABLED).creationDate(UPDATED_CREATION_DATE);
//
//        restIntentMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedIntent.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntent))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
//        Intent testIntent = intentList.get(intentList.size() - 1);
//        assertThat(testIntent.getIntenType()).isEqualTo(DEFAULT_INTEN_TYPE);
//        assertThat(testIntent.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testIntent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testIntent.getUrlRequest()).isEqualTo(DEFAULT_URL_REQUEST);
//        assertThat(testIntent.getEnabled()).isEqualTo(UPDATED_ENABLED);
//        assertThat(testIntent.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateIntentWithPatch() throws Exception {
//        // Initialize the database
//        intentRepository.saveAndFlush(intent);
//
//        int databaseSizeBeforeUpdate = intentRepository.findAll().size();
//
//        // Update the intent using partial update
//        Intent partialUpdatedIntent = new Intent();
//        partialUpdatedIntent.setId(intent.getId());
//
//        partialUpdatedIntent
//            .intenType(UPDATED_INTEN_TYPE)
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .urlRequest(UPDATED_URL_REQUEST)
//            .enabled(UPDATED_ENABLED)
//            .creationDate(UPDATED_CREATION_DATE);
//
//        restIntentMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedIntent.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIntent))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
//        Intent testIntent = intentList.get(intentList.size() - 1);
//        assertThat(testIntent.getIntenType()).isEqualTo(UPDATED_INTEN_TYPE);
//        assertThat(testIntent.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testIntent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testIntent.getUrlRequest()).isEqualTo(UPDATED_URL_REQUEST);
//        assertThat(testIntent.getEnabled()).isEqualTo(UPDATED_ENABLED);
//        assertThat(testIntent.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingIntent() throws Exception {
//        int databaseSizeBeforeUpdate = intentRepository.findAll().size();
//        intent.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restIntentMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, intent.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(intent))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchIntent() throws Exception {
//        int databaseSizeBeforeUpdate = intentRepository.findAll().size();
//        intent.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restIntentMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(intent))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamIntent() throws Exception {
//        int databaseSizeBeforeUpdate = intentRepository.findAll().size();
//        intent.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restIntentMockMvc
//            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(intent)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Intent in the database
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteIntent() throws Exception {
//        // Initialize the database
//        intentRepository.saveAndFlush(intent);
//
//        int databaseSizeBeforeDelete = intentRepository.findAll().size();
//
//        // Delete the intent
//        restIntentMockMvc
//            .perform(delete(ENTITY_API_URL_ID, intent.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Intent> intentList = intentRepository.findAll();
//        assertThat(intentList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
