//package com.sara.services.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.sara.services.IntegrationTest;
//import com.sara.services.domain.UserResponse;
//import com.sara.services.domain.enumeration.Priority;
//import com.sara.services.domain.enumeration.ResponseType;
//import com.sara.services.repository.UserResponseRepository;
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
// * Integration tests for the {@link UserResponseResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class UserResponseResourceIT {
//
//    private static final String DEFAULT_VALUE_RESPONSE = "AAAAAAAAAA";
//    private static final String UPDATED_VALUE_RESPONSE = "BBBBBBBBBB";
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
//    private static final ResponseType DEFAULT_RESPONSE_TYPE = ResponseType.QUERY;
//    private static final ResponseType UPDATED_RESPONSE_TYPE = ResponseType.SERVICIO;
//
//    private static final String DEFAULT_URL = "AAAAAAAAAA";
//    private static final String UPDATED_URL = "BBBBBBBBBB";
//
//    private static final String ENTITY_API_URL = "/api/user-responses";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private UserResponseRepository userResponseRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restUserResponseMockMvc;
//
//    private UserResponse userResponse;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static UserResponse createEntity(EntityManager em) {
//        UserResponse userResponse = new UserResponse()
//            .valueResponse(DEFAULT_VALUE_RESPONSE)
//            .priority(DEFAULT_PRIORITY)
//            .multimedia(DEFAULT_MULTIMEDIA)
//            .multimediaContentType(DEFAULT_MULTIMEDIA_CONTENT_TYPE)
//            .multimediaVoice(DEFAULT_MULTIMEDIA_VOICE)
//            .multimediaVoiceContentType(DEFAULT_MULTIMEDIA_VOICE_CONTENT_TYPE)
//            .saraAnimation(DEFAULT_SARA_ANIMATION)
//            .saraAnimationContentType(DEFAULT_SARA_ANIMATION_CONTENT_TYPE)
//            .isEndConversation(DEFAULT_IS_END_CONVERSATION)
//            .responseType(DEFAULT_RESPONSE_TYPE)
//            .url(DEFAULT_URL);
//        return userResponse;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static UserResponse createUpdatedEntity(EntityManager em) {
//        UserResponse userResponse = new UserResponse()
//            .valueResponse(UPDATED_VALUE_RESPONSE)
//            .priority(UPDATED_PRIORITY)
//            .multimedia(UPDATED_MULTIMEDIA)
//            .multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE)
//            .multimediaVoice(UPDATED_MULTIMEDIA_VOICE)
//            .multimediaVoiceContentType(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE)
//            .saraAnimation(UPDATED_SARA_ANIMATION)
//            .saraAnimationContentType(UPDATED_SARA_ANIMATION_CONTENT_TYPE)
//            .isEndConversation(UPDATED_IS_END_CONVERSATION)
//            .responseType(UPDATED_RESPONSE_TYPE)
//            .url(UPDATED_URL);
//        return userResponse;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        userResponse = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createUserResponse() throws Exception {
//        int databaseSizeBeforeCreate = userResponseRepository.findAll().size();
//        // Create the UserResponse
//        restUserResponseMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userResponse)))
//            .andExpect(status().isCreated());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeCreate + 1);
//        UserResponse testUserResponse = userResponseList.get(userResponseList.size() - 1);
//        assertThat(testUserResponse.getValueResponse()).isEqualTo(DEFAULT_VALUE_RESPONSE);
//        assertThat(testUserResponse.getPriority()).isEqualTo(DEFAULT_PRIORITY);
//        assertThat(testUserResponse.getMultimedia()).isEqualTo(DEFAULT_MULTIMEDIA);
//        assertThat(testUserResponse.getMultimediaContentType()).isEqualTo(DEFAULT_MULTIMEDIA_CONTENT_TYPE);
//        assertThat(testUserResponse.getMultimediaVoice()).isEqualTo(DEFAULT_MULTIMEDIA_VOICE);
//        assertThat(testUserResponse.getMultimediaVoiceContentType()).isEqualTo(DEFAULT_MULTIMEDIA_VOICE_CONTENT_TYPE);
//        assertThat(testUserResponse.getSaraAnimation()).isEqualTo(DEFAULT_SARA_ANIMATION);
//        assertThat(testUserResponse.getSaraAnimationContentType()).isEqualTo(DEFAULT_SARA_ANIMATION_CONTENT_TYPE);
//        assertThat(testUserResponse.getIsEndConversation()).isEqualTo(DEFAULT_IS_END_CONVERSATION);
//        assertThat(testUserResponse.getResponseType()).isEqualTo(DEFAULT_RESPONSE_TYPE);
//        assertThat(testUserResponse.getUrl()).isEqualTo(DEFAULT_URL);
//    }
//
//    @Test
//    @Transactional
//    void createUserResponseWithExistingId() throws Exception {
//        // Create the UserResponse with an existing ID
//        userResponse.setId(1L);
//
//        int databaseSizeBeforeCreate = userResponseRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restUserResponseMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userResponse)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkValueResponseIsRequired() throws Exception {
//        int databaseSizeBeforeTest = userResponseRepository.findAll().size();
//        // set the field null
//        userResponse.setValueResponse(null);
//
//        // Create the UserResponse, which fails.
//
//        restUserResponseMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userResponse)))
//            .andExpect(status().isBadRequest());
//
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllUserResponses() throws Exception {
//        // Initialize the database
//        userResponseRepository.saveAndFlush(userResponse);
//
//        // Get all the userResponseList
//        restUserResponseMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(userResponse.getId().intValue())))
//            .andExpect(jsonPath("$.[*].valueResponse").value(hasItem(DEFAULT_VALUE_RESPONSE)))
//            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
//            .andExpect(jsonPath("$.[*].multimediaContentType").value(hasItem(DEFAULT_MULTIMEDIA_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].multimedia").value(hasItem(Base64Utils.encodeToString(DEFAULT_MULTIMEDIA))))
//            .andExpect(jsonPath("$.[*].multimediaVoiceContentType").value(hasItem(DEFAULT_MULTIMEDIA_VOICE_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].multimediaVoice").value(hasItem(Base64Utils.encodeToString(DEFAULT_MULTIMEDIA_VOICE))))
//            .andExpect(jsonPath("$.[*].saraAnimationContentType").value(hasItem(DEFAULT_SARA_ANIMATION_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].saraAnimation").value(hasItem(Base64Utils.encodeToString(DEFAULT_SARA_ANIMATION))))
//            .andExpect(jsonPath("$.[*].isEndConversation").value(hasItem(DEFAULT_IS_END_CONVERSATION.booleanValue())))
//            .andExpect(jsonPath("$.[*].responseType").value(hasItem(DEFAULT_RESPONSE_TYPE.toString())))
//            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
//    }
//
//    @Test
//    @Transactional
//    void getUserResponse() throws Exception {
//        // Initialize the database
//        userResponseRepository.saveAndFlush(userResponse);
//
//        // Get the userResponse
//        restUserResponseMockMvc
//            .perform(get(ENTITY_API_URL_ID, userResponse.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(userResponse.getId().intValue()))
//            .andExpect(jsonPath("$.valueResponse").value(DEFAULT_VALUE_RESPONSE))
//            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
//            .andExpect(jsonPath("$.multimediaContentType").value(DEFAULT_MULTIMEDIA_CONTENT_TYPE))
//            .andExpect(jsonPath("$.multimedia").value(Base64Utils.encodeToString(DEFAULT_MULTIMEDIA)))
//            .andExpect(jsonPath("$.multimediaVoiceContentType").value(DEFAULT_MULTIMEDIA_VOICE_CONTENT_TYPE))
//            .andExpect(jsonPath("$.multimediaVoice").value(Base64Utils.encodeToString(DEFAULT_MULTIMEDIA_VOICE)))
//            .andExpect(jsonPath("$.saraAnimationContentType").value(DEFAULT_SARA_ANIMATION_CONTENT_TYPE))
//            .andExpect(jsonPath("$.saraAnimation").value(Base64Utils.encodeToString(DEFAULT_SARA_ANIMATION)))
//            .andExpect(jsonPath("$.isEndConversation").value(DEFAULT_IS_END_CONVERSATION.booleanValue()))
//            .andExpect(jsonPath("$.responseType").value(DEFAULT_RESPONSE_TYPE.toString()))
//            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingUserResponse() throws Exception {
//        // Get the userResponse
//        restUserResponseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingUserResponse() throws Exception {
//        // Initialize the database
//        userResponseRepository.saveAndFlush(userResponse);
//
//        int databaseSizeBeforeUpdate = userResponseRepository.findAll().size();
//
//        // Update the userResponse
//        UserResponse updatedUserResponse = userResponseRepository.findById(userResponse.getId()).get();
//        // Disconnect from session so that the updates on updatedUserResponse are not directly saved in db
//        em.detach(updatedUserResponse);
//        updatedUserResponse
//            .valueResponse(UPDATED_VALUE_RESPONSE)
//            .priority(UPDATED_PRIORITY)
//            .multimedia(UPDATED_MULTIMEDIA)
//            .multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE)
//            .multimediaVoice(UPDATED_MULTIMEDIA_VOICE)
//            .multimediaVoiceContentType(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE)
//            .saraAnimation(UPDATED_SARA_ANIMATION)
//            .saraAnimationContentType(UPDATED_SARA_ANIMATION_CONTENT_TYPE)
//            .isEndConversation(UPDATED_IS_END_CONVERSATION)
//            .responseType(UPDATED_RESPONSE_TYPE)
//            .url(UPDATED_URL);
//
//        restUserResponseMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedUserResponse.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedUserResponse))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeUpdate);
//        UserResponse testUserResponse = userResponseList.get(userResponseList.size() - 1);
//        assertThat(testUserResponse.getValueResponse()).isEqualTo(UPDATED_VALUE_RESPONSE);
//        assertThat(testUserResponse.getPriority()).isEqualTo(UPDATED_PRIORITY);
//        assertThat(testUserResponse.getMultimedia()).isEqualTo(UPDATED_MULTIMEDIA);
//        assertThat(testUserResponse.getMultimediaContentType()).isEqualTo(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//        assertThat(testUserResponse.getMultimediaVoice()).isEqualTo(UPDATED_MULTIMEDIA_VOICE);
//        assertThat(testUserResponse.getMultimediaVoiceContentType()).isEqualTo(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE);
//        assertThat(testUserResponse.getSaraAnimation()).isEqualTo(UPDATED_SARA_ANIMATION);
//        assertThat(testUserResponse.getSaraAnimationContentType()).isEqualTo(UPDATED_SARA_ANIMATION_CONTENT_TYPE);
//        assertThat(testUserResponse.getIsEndConversation()).isEqualTo(UPDATED_IS_END_CONVERSATION);
//        assertThat(testUserResponse.getResponseType()).isEqualTo(UPDATED_RESPONSE_TYPE);
//        assertThat(testUserResponse.getUrl()).isEqualTo(UPDATED_URL);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingUserResponse() throws Exception {
//        int databaseSizeBeforeUpdate = userResponseRepository.findAll().size();
//        userResponse.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restUserResponseMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, userResponse.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(userResponse))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchUserResponse() throws Exception {
//        int databaseSizeBeforeUpdate = userResponseRepository.findAll().size();
//        userResponse.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restUserResponseMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(userResponse))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamUserResponse() throws Exception {
//        int databaseSizeBeforeUpdate = userResponseRepository.findAll().size();
//        userResponse.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restUserResponseMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userResponse)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateUserResponseWithPatch() throws Exception {
//        // Initialize the database
//        userResponseRepository.saveAndFlush(userResponse);
//
//        int databaseSizeBeforeUpdate = userResponseRepository.findAll().size();
//
//        // Update the userResponse using partial update
//        UserResponse partialUpdatedUserResponse = new UserResponse();
//        partialUpdatedUserResponse.setId(userResponse.getId());
//
//        partialUpdatedUserResponse
//            .valueResponse(UPDATED_VALUE_RESPONSE)
//            .priority(UPDATED_PRIORITY)
//            .saraAnimation(UPDATED_SARA_ANIMATION)
//            .saraAnimationContentType(UPDATED_SARA_ANIMATION_CONTENT_TYPE)
//            .isEndConversation(UPDATED_IS_END_CONVERSATION)
//            .responseType(UPDATED_RESPONSE_TYPE)
//            .url(UPDATED_URL);
//
//        restUserResponseMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedUserResponse.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserResponse))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeUpdate);
//        UserResponse testUserResponse = userResponseList.get(userResponseList.size() - 1);
//        assertThat(testUserResponse.getValueResponse()).isEqualTo(UPDATED_VALUE_RESPONSE);
//        assertThat(testUserResponse.getPriority()).isEqualTo(UPDATED_PRIORITY);
//        assertThat(testUserResponse.getMultimedia()).isEqualTo(DEFAULT_MULTIMEDIA);
//        assertThat(testUserResponse.getMultimediaContentType()).isEqualTo(DEFAULT_MULTIMEDIA_CONTENT_TYPE);
//        assertThat(testUserResponse.getMultimediaVoice()).isEqualTo(DEFAULT_MULTIMEDIA_VOICE);
//        assertThat(testUserResponse.getMultimediaVoiceContentType()).isEqualTo(DEFAULT_MULTIMEDIA_VOICE_CONTENT_TYPE);
//        assertThat(testUserResponse.getSaraAnimation()).isEqualTo(UPDATED_SARA_ANIMATION);
//        assertThat(testUserResponse.getSaraAnimationContentType()).isEqualTo(UPDATED_SARA_ANIMATION_CONTENT_TYPE);
//        assertThat(testUserResponse.getIsEndConversation()).isEqualTo(UPDATED_IS_END_CONVERSATION);
//        assertThat(testUserResponse.getResponseType()).isEqualTo(UPDATED_RESPONSE_TYPE);
//        assertThat(testUserResponse.getUrl()).isEqualTo(UPDATED_URL);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateUserResponseWithPatch() throws Exception {
//        // Initialize the database
//        userResponseRepository.saveAndFlush(userResponse);
//
//        int databaseSizeBeforeUpdate = userResponseRepository.findAll().size();
//
//        // Update the userResponse using partial update
//        UserResponse partialUpdatedUserResponse = new UserResponse();
//        partialUpdatedUserResponse.setId(userResponse.getId());
//
//        partialUpdatedUserResponse
//            .valueResponse(UPDATED_VALUE_RESPONSE)
//            .priority(UPDATED_PRIORITY)
//            .multimedia(UPDATED_MULTIMEDIA)
//            .multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE)
//            .multimediaVoice(UPDATED_MULTIMEDIA_VOICE)
//            .multimediaVoiceContentType(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE)
//            .saraAnimation(UPDATED_SARA_ANIMATION)
//            .saraAnimationContentType(UPDATED_SARA_ANIMATION_CONTENT_TYPE)
//            .isEndConversation(UPDATED_IS_END_CONVERSATION)
//            .responseType(UPDATED_RESPONSE_TYPE)
//            .url(UPDATED_URL);
//
//        restUserResponseMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedUserResponse.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserResponse))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeUpdate);
//        UserResponse testUserResponse = userResponseList.get(userResponseList.size() - 1);
//        assertThat(testUserResponse.getValueResponse()).isEqualTo(UPDATED_VALUE_RESPONSE);
//        assertThat(testUserResponse.getPriority()).isEqualTo(UPDATED_PRIORITY);
//        assertThat(testUserResponse.getMultimedia()).isEqualTo(UPDATED_MULTIMEDIA);
//        assertThat(testUserResponse.getMultimediaContentType()).isEqualTo(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//        assertThat(testUserResponse.getMultimediaVoice()).isEqualTo(UPDATED_MULTIMEDIA_VOICE);
//        assertThat(testUserResponse.getMultimediaVoiceContentType()).isEqualTo(UPDATED_MULTIMEDIA_VOICE_CONTENT_TYPE);
//        assertThat(testUserResponse.getSaraAnimation()).isEqualTo(UPDATED_SARA_ANIMATION);
//        assertThat(testUserResponse.getSaraAnimationContentType()).isEqualTo(UPDATED_SARA_ANIMATION_CONTENT_TYPE);
//        assertThat(testUserResponse.getIsEndConversation()).isEqualTo(UPDATED_IS_END_CONVERSATION);
//        assertThat(testUserResponse.getResponseType()).isEqualTo(UPDATED_RESPONSE_TYPE);
//        assertThat(testUserResponse.getUrl()).isEqualTo(UPDATED_URL);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingUserResponse() throws Exception {
//        int databaseSizeBeforeUpdate = userResponseRepository.findAll().size();
//        userResponse.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restUserResponseMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, userResponse.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(userResponse))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchUserResponse() throws Exception {
//        int databaseSizeBeforeUpdate = userResponseRepository.findAll().size();
//        userResponse.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restUserResponseMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(userResponse))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamUserResponse() throws Exception {
//        int databaseSizeBeforeUpdate = userResponseRepository.findAll().size();
//        userResponse.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restUserResponseMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userResponse))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the UserResponse in the database
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteUserResponse() throws Exception {
//        // Initialize the database
//        userResponseRepository.saveAndFlush(userResponse);
//
//        int databaseSizeBeforeDelete = userResponseRepository.findAll().size();
//
//        // Delete the userResponse
//        restUserResponseMockMvc
//            .perform(delete(ENTITY_API_URL_ID, userResponse.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<UserResponse> userResponseList = userResponseRepository.findAll();
//        assertThat(userResponseList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
