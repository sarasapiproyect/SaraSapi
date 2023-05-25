//package com.sara.services.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.sara.services.IntegrationTest;
//import com.sara.services.domain.DefaultResponse;
//import com.sara.services.domain.enumeration.Priority;
//import com.sara.services.repository.DefaultResponseRepository;
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
//import org.springframework.util.Base64Utils;
//
///**
// * Integration tests for the {@link DefaultResponseResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class DefaultResponseResourceIT {
//
//    private static final String DEFAULT_DEFAULT_VALUE_RESPONSE = "AAAAAAAAAA";
//    private static final String UPDATED_DEFAULT_VALUE_RESPONSE = "BBBBBBBBBB";
//
//    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
//    private static final Priority UPDATED_PRIORITY = Priority.MEDIA;
//
//    private static final byte[] DEFAULT_MULTIMEDIA = TestUtil.createByteArray(1, "0");
//    private static final byte[] UPDATED_MULTIMEDIA = TestUtil.createByteArray(1, "1");
//    private static final String DEFAULT_MULTIMEDIA_CONTENT_TYPE = "image/jpg";
//    private static final String UPDATED_MULTIMEDIA_CONTENT_TYPE = "image/png";
//
//    private static final byte[] DEFAULT_MULTIMEDIA_VOICE = TestUtil.createByteArray(1, "0");
//    private static final byte[] UPDATED_MULTIMEDIA_VOICE = TestUtil.createByteArray(1, "1");
//    private static final String DEFAULT_MULTIMEDIA_VOICE_CONTENT_TYPE = "image/jpg";
//    private static final String UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE = "image/png";
//
//    private static final byte[] DEFAULT_SARA_ANIMATION = TestUtil.createByteArray(1, "0");
//    private static final byte[] UPDATED_SARA_ANIMATION = TestUtil.createByteArray(1, "1");
//    private static final String DEFAULT_SARA_ANIMATION_CONTENT_TYPE = "image/jpg";
//    private static final String UPDATED_SARA_ANIMATION_CONTENT_TYPE = "image/png";
//
//    private static final Boolean DEFAULT_IS_END_CONVERSATION = false;
//    private static final Boolean UPDATED_IS_END_CONVERSATION = true;
//
//    private static final String ENTITY_API_URL = "/api/default-responses";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private DefaultResponseRepository defaultResponseRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restDefaultResponseMockMvc;
//
//    private DefaultResponse defaultResponse;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static DefaultResponse createEntity(EntityManager em) {
//        DefaultResponse defaultResponse = new DefaultResponse()
//            .defaultValueResponse(DEFAULT_DEFAULT_VALUE_RESPONSE)
//            .priority(DEFAULT_PRIORITY)
//            .multimedia(DEFAULT_MULTIMEDIA)
//            .multimediaContentType(DEFAULT_MULTIMEDIA_CONTENT_TYPE)
//            .multimediaVoice(DEFAULT_MULTIMEDIA_VOICE)
//            .multimediaVoiceContentType(DEFAULT_MULTIMEDIA_VOICE_CONTENT_TYPE)
//            .saraAnimation(DEFAULT_SARA_ANIMATION)
//            .saraAnimationContentType(DEFAULT_SARA_ANIMATION_CONTENT_TYPE)
//            .isEndConversation(DEFAULT_IS_END_CONVERSATION);
//        return defaultResponse;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static DefaultResponse createUpdatedEntity(EntityManager em) {
//        DefaultResponse defaultResponse = new DefaultResponse()
//            .defaultValueResponse(UPDATED_DEFAULT_VALUE_RESPONSE)
//            .priority(UPDATED_PRIORITY)
//            .multimedia(UPDATED_MULTIMEDIA)
//            .multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE)
//            .multimediaVoice(UPDATED_MULTIMEDIA_VOICE)
//            .multimediaVoiceContentType(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE)
//            .saraAnimation(UPDATED_SARA_ANIMATION)
//            .saraAnimationContentType(UPDATED_SARA_ANIMATION_CONTENT_TYPE)
//            .isEndConversation(UPDATED_IS_END_CONVERSATION);
//        return defaultResponse;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        defaultResponse = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createDefaultResponse() throws Exception {
//        int databaseSizeBeforeCreate = defaultResponseRepository.findAll().size();
//        // Create the DefaultResponse
//        restDefaultResponseMockMvc
//            .perform(
//                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(defaultResponse))
//            )
//            .andExpect(status().isCreated());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeCreate + 1);
//        DefaultResponse testDefaultResponse = defaultResponseList.get(defaultResponseList.size() - 1);
//        assertThat(testDefaultResponse.getDefaultValueResponse()).isEqualTo(DEFAULT_DEFAULT_VALUE_RESPONSE);
//        assertThat(testDefaultResponse.getPriority()).isEqualTo(DEFAULT_PRIORITY);
//        assertThat(testDefaultResponse.getMultimedia()).isEqualTo(DEFAULT_MULTIMEDIA);
//        assertThat(testDefaultResponse.getMultimediaContentType()).isEqualTo(DEFAULT_MULTIMEDIA_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getMultimediaVoice()).isEqualTo(DEFAULT_MULTIMEDIA_VOICE);
//        assertThat(testDefaultResponse.getMultimediaVoiceContentType()).isEqualTo(DEFAULT_MULTIMEDIA_VOICE_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getSaraAnimation()).isEqualTo(DEFAULT_SARA_ANIMATION);
//        assertThat(testDefaultResponse.getSaraAnimationContentType()).isEqualTo(DEFAULT_SARA_ANIMATION_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getIsEndConversation()).isEqualTo(DEFAULT_IS_END_CONVERSATION);
//    }
//
//    @Test
//    @Transactional
//    void createDefaultResponseWithExistingId() throws Exception {
//        // Create the DefaultResponse with an existing ID
//        defaultResponse.setId(1L);
//
//        int databaseSizeBeforeCreate = defaultResponseRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restDefaultResponseMockMvc
//            .perform(
//                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(defaultResponse))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkDefaultValueResponseIsRequired() throws Exception {
//        int databaseSizeBeforeTest = defaultResponseRepository.findAll().size();
//        // set the field null
//        defaultResponse.setDefaultValueResponse(null);
//
//        // Create the DefaultResponse, which fails.
//
//        restDefaultResponseMockMvc
//            .perform(
//                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(defaultResponse))
//            )
//            .andExpect(status().isBadRequest());
//
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllDefaultResponses() throws Exception {
//        // Initialize the database
//        defaultResponseRepository.saveAndFlush(defaultResponse);
//
//        // Get all the defaultResponseList
//        restDefaultResponseMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(defaultResponse.getId().intValue())))
//            .andExpect(jsonPath("$.[*].defaultValueResponse").value(hasItem(DEFAULT_DEFAULT_VALUE_RESPONSE)))
//            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
//            .andExpect(jsonPath("$.[*].multimediaContentType").value(hasItem(DEFAULT_MULTIMEDIA_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].multimedia").value(hasItem(Base64Utils.encodeToString(DEFAULT_MULTIMEDIA))))
//            .andExpect(jsonPath("$.[*].multimediaVoiceContentType").value(hasItem(DEFAULT_MULTIMEDIA_VOICE_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].multimediaVoice").value(hasItem(Base64Utils.encodeToString(DEFAULT_MULTIMEDIA_VOICE))))
//            .andExpect(jsonPath("$.[*].saraAnimationContentType").value(hasItem(DEFAULT_SARA_ANIMATION_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].saraAnimation").value(hasItem(Base64Utils.encodeToString(DEFAULT_SARA_ANIMATION))))
//            .andExpect(jsonPath("$.[*].isEndConversation").value(hasItem(DEFAULT_IS_END_CONVERSATION.booleanValue())));
//    }
//
//    @Test
//    @Transactional
//    void getDefaultResponse() throws Exception {
//        // Initialize the database
//        defaultResponseRepository.saveAndFlush(defaultResponse);
//
//        // Get the defaultResponse
//        restDefaultResponseMockMvc
//            .perform(get(ENTITY_API_URL_ID, defaultResponse.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(defaultResponse.getId().intValue()))
//            .andExpect(jsonPath("$.defaultValueResponse").value(DEFAULT_DEFAULT_VALUE_RESPONSE))
//            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
//            .andExpect(jsonPath("$.multimediaContentType").value(DEFAULT_MULTIMEDIA_CONTENT_TYPE))
//            .andExpect(jsonPath("$.multimedia").value(Base64Utils.encodeToString(DEFAULT_MULTIMEDIA)))
//            .andExpect(jsonPath("$.multimediaVoiceContentType").value(DEFAULT_MULTIMEDIA_VOICE_CONTENT_TYPE))
//            .andExpect(jsonPath("$.multimediaVoice").value(Base64Utils.encodeToString(DEFAULT_MULTIMEDIA_VOICE)))
//            .andExpect(jsonPath("$.saraAnimationContentType").value(DEFAULT_SARA_ANIMATION_CONTENT_TYPE))
//            .andExpect(jsonPath("$.saraAnimation").value(Base64Utils.encodeToString(DEFAULT_SARA_ANIMATION)))
//            .andExpect(jsonPath("$.isEndConversation").value(DEFAULT_IS_END_CONVERSATION.booleanValue()));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingDefaultResponse() throws Exception {
//        // Get the defaultResponse
//        restDefaultResponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingDefaultResponse() throws Exception {
//        // Initialize the database
//        defaultResponseRepository.saveAndFlush(defaultResponse);
//
//        int databaseSizeBeforeUpdate = defaultResponseRepository.findAll().size();
//
//        // Update the defaultResponse
//        DefaultResponse updatedDefaultResponse = defaultResponseRepository.findById(defaultResponse.getId()).get();
//        // Disconnect from session so that the updates on updatedDefaultResponse are not directly saved in db
//        em.detach(updatedDefaultResponse);
//        updatedDefaultResponse
//            .defaultValueResponse(UPDATED_DEFAULT_VALUE_RESPONSE)
//            .priority(UPDATED_PRIORITY)
//            .multimedia(UPDATED_MULTIMEDIA)
//            .multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE)
//            .multimediaVoice(UPDATED_MULTIMEDIA_VOICE)
//            .multimediaVoiceContentType(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE)
//            .saraAnimation(UPDATED_SARA_ANIMATION)
//            .saraAnimationContentType(UPDATED_SARA_ANIMATION_CONTENT_TYPE)
//            .isEndConversation(UPDATED_IS_END_CONVERSATION);
//
//        restDefaultResponseMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedDefaultResponse.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedDefaultResponse))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeUpdate);
//        DefaultResponse testDefaultResponse = defaultResponseList.get(defaultResponseList.size() - 1);
//        assertThat(testDefaultResponse.getDefaultValueResponse()).isEqualTo(UPDATED_DEFAULT_VALUE_RESPONSE);
//        assertThat(testDefaultResponse.getPriority()).isEqualTo(UPDATED_PRIORITY);
//        assertThat(testDefaultResponse.getMultimedia()).isEqualTo(UPDATED_MULTIMEDIA);
//        assertThat(testDefaultResponse.getMultimediaContentType()).isEqualTo(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getMultimediaVoice()).isEqualTo(UPDATED_MULTIMEDIA_VOICE);
//        assertThat(testDefaultResponse.getMultimediaVoiceContentType()).isEqualTo(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getSaraAnimation()).isEqualTo(UPDATED_SARA_ANIMATION);
//        assertThat(testDefaultResponse.getSaraAnimationContentType()).isEqualTo(UPDATED_SARA_ANIMATION_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getIsEndConversation()).isEqualTo(UPDATED_IS_END_CONVERSATION);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingDefaultResponse() throws Exception {
//        int databaseSizeBeforeUpdate = defaultResponseRepository.findAll().size();
//        defaultResponse.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restDefaultResponseMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, defaultResponse.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(defaultResponse))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchDefaultResponse() throws Exception {
//        int databaseSizeBeforeUpdate = defaultResponseRepository.findAll().size();
//        defaultResponse.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restDefaultResponseMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(defaultResponse))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamDefaultResponse() throws Exception {
//        int databaseSizeBeforeUpdate = defaultResponseRepository.findAll().size();
//        defaultResponse.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restDefaultResponseMockMvc
//            .perform(
//                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(defaultResponse))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateDefaultResponseWithPatch() throws Exception {
//        // Initialize the database
//        defaultResponseRepository.saveAndFlush(defaultResponse);
//
//        int databaseSizeBeforeUpdate = defaultResponseRepository.findAll().size();
//
//        // Update the defaultResponse using partial update
//        DefaultResponse partialUpdatedDefaultResponse = new DefaultResponse();
//        partialUpdatedDefaultResponse.setId(defaultResponse.getId());
//
//        partialUpdatedDefaultResponse
//            .multimedia(UPDATED_MULTIMEDIA)
//            .multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE)
//            .multimediaVoice(UPDATED_MULTIMEDIA_VOICE)
//            .multimediaVoiceContentType(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE)
//            .isEndConversation(UPDATED_IS_END_CONVERSATION);
//
//        restDefaultResponseMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedDefaultResponse.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDefaultResponse))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeUpdate);
//        DefaultResponse testDefaultResponse = defaultResponseList.get(defaultResponseList.size() - 1);
//        assertThat(testDefaultResponse.getDefaultValueResponse()).isEqualTo(DEFAULT_DEFAULT_VALUE_RESPONSE);
//        assertThat(testDefaultResponse.getPriority()).isEqualTo(DEFAULT_PRIORITY);
//        assertThat(testDefaultResponse.getMultimedia()).isEqualTo(UPDATED_MULTIMEDIA);
//        assertThat(testDefaultResponse.getMultimediaContentType()).isEqualTo(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getMultimediaVoice()).isEqualTo(UPDATED_MULTIMEDIA_VOICE);
//        assertThat(testDefaultResponse.getMultimediaVoiceContentType()).isEqualTo(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getSaraAnimation()).isEqualTo(DEFAULT_SARA_ANIMATION);
//        assertThat(testDefaultResponse.getSaraAnimationContentType()).isEqualTo(DEFAULT_SARA_ANIMATION_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getIsEndConversation()).isEqualTo(UPDATED_IS_END_CONVERSATION);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateDefaultResponseWithPatch() throws Exception {
//        // Initialize the database
//        defaultResponseRepository.saveAndFlush(defaultResponse);
//
//        int databaseSizeBeforeUpdate = defaultResponseRepository.findAll().size();
//
//        // Update the defaultResponse using partial update
//        DefaultResponse partialUpdatedDefaultResponse = new DefaultResponse();
//        partialUpdatedDefaultResponse.setId(defaultResponse.getId());
//
//        partialUpdatedDefaultResponse
//            .defaultValueResponse(UPDATED_DEFAULT_VALUE_RESPONSE)
//            .priority(UPDATED_PRIORITY)
//            .multimedia(UPDATED_MULTIMEDIA)
//            .multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE)
//            .multimediaVoice(UPDATED_MULTIMEDIA_VOICE)
//            .multimediaVoiceContentType(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE)
//            .saraAnimation(UPDATED_SARA_ANIMATION)
//            .saraAnimationContentType(UPDATED_SARA_ANIMATION_CONTENT_TYPE)
//            .isEndConversation(UPDATED_IS_END_CONVERSATION);
//
//        restDefaultResponseMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedDefaultResponse.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDefaultResponse))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeUpdate);
//        DefaultResponse testDefaultResponse = defaultResponseList.get(defaultResponseList.size() - 1);
//        assertThat(testDefaultResponse.getDefaultValueResponse()).isEqualTo(UPDATED_DEFAULT_VALUE_RESPONSE);
//        assertThat(testDefaultResponse.getPriority()).isEqualTo(UPDATED_PRIORITY);
//        assertThat(testDefaultResponse.getMultimedia()).isEqualTo(UPDATED_MULTIMEDIA);
//        assertThat(testDefaultResponse.getMultimediaContentType()).isEqualTo(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getMultimediaVoice()).isEqualTo(UPDATED_MULTIMEDIA_VOICE);
//        assertThat(testDefaultResponse.getMultimediaVoiceContentType()).isEqualTo(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getSaraAnimation()).isEqualTo(UPDATED_SARA_ANIMATION);
//        assertThat(testDefaultResponse.getSaraAnimationContentType()).isEqualTo(UPDATED_SARA_ANIMATION_CONTENT_TYPE);
//        assertThat(testDefaultResponse.getIsEndConversation()).isEqualTo(UPDATED_IS_END_CONVERSATION);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingDefaultResponse() throws Exception {
//        int databaseSizeBeforeUpdate = defaultResponseRepository.findAll().size();
//        defaultResponse.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restDefaultResponseMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, defaultResponse.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(defaultResponse))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchDefaultResponse() throws Exception {
//        int databaseSizeBeforeUpdate = defaultResponseRepository.findAll().size();
//        defaultResponse.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restDefaultResponseMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(defaultResponse))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamDefaultResponse() throws Exception {
//        int databaseSizeBeforeUpdate = defaultResponseRepository.findAll().size();
//        defaultResponse.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restDefaultResponseMockMvc
//            .perform(
//                patch(ENTITY_API_URL)
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(defaultResponse))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the DefaultResponse in the database
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteDefaultResponse() throws Exception {
//        // Initialize the database
//        defaultResponseRepository.saveAndFlush(defaultResponse);
//
//        int databaseSizeBeforeDelete = defaultResponseRepository.findAll().size();
//
//        // Delete the defaultResponse
//        restDefaultResponseMockMvc
//            .perform(delete(ENTITY_API_URL_ID, defaultResponse.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<DefaultResponse> defaultResponseList = defaultResponseRepository.findAll();
//        assertThat(defaultResponseList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
