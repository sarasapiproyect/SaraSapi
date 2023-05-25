//package com.sara.services.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.sara.services.IntegrationTest;
//import com.sara.services.domain.ChatbootStyle;
//import com.sara.services.repository.ChatbootStyleRepository;
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
// * Integration tests for the {@link ChatbootStyleResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class ChatbootStyleResourceIT {
//
//    private static final String DEFAULT_NAME_PROPERTIES = "AAAAAAAAAA";
//    private static final String UPDATED_NAME_PROPERTIES = "BBBBBBBBBB";
//
//    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
//    private static final String UPDATED_VALUE = "BBBBBBBBBB";
//
//    private static final byte[] DEFAULT_MULTIMEDIA = TestUtil.createByteArray(1, "0");
//    private static final byte[] UPDATED_MULTIMEDIA = TestUtil.createByteArray(1, "1");
//    private static final String DEFAULT_MULTIMEDIA_CONTENT_TYPE = "image/jpg";
//    private static final String UPDATED_MULTIMEDIA_CONTENT_TYPE = "image/png";
//
//    private static final String ENTITY_API_URL = "/api/chatboot-styles";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private ChatbootStyleRepository chatbootStyleRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restChatbootStyleMockMvc;
//
//    private ChatbootStyle chatbootStyle;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static ChatbootStyle createEntity(EntityManager em) {
//        ChatbootStyle chatbootStyle = new ChatbootStyle()
//            .nameProperties(DEFAULT_NAME_PROPERTIES)
//            .value(DEFAULT_VALUE)
//            .multimedia(DEFAULT_MULTIMEDIA)
//            .multimediaContentType(DEFAULT_MULTIMEDIA_CONTENT_TYPE);
//        return chatbootStyle;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static ChatbootStyle createUpdatedEntity(EntityManager em) {
//        ChatbootStyle chatbootStyle = new ChatbootStyle()
//            .nameProperties(UPDATED_NAME_PROPERTIES)
//            .value(UPDATED_VALUE)
//            .multimedia(UPDATED_MULTIMEDIA)
//            .multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//        return chatbootStyle;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        chatbootStyle = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createChatbootStyle() throws Exception {
//        int databaseSizeBeforeCreate = chatbootStyleRepository.findAll().size();
//        // Create the ChatbootStyle
//        restChatbootStyleMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chatbootStyle)))
//            .andExpect(status().isCreated());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeCreate + 1);
//        ChatbootStyle testChatbootStyle = chatbootStyleList.get(chatbootStyleList.size() - 1);
//        assertThat(testChatbootStyle.getNameProperties()).isEqualTo(DEFAULT_NAME_PROPERTIES);
//        assertThat(testChatbootStyle.getValue()).isEqualTo(DEFAULT_VALUE);
//        assertThat(testChatbootStyle.getMultimedia()).isEqualTo(DEFAULT_MULTIMEDIA);
//        assertThat(testChatbootStyle.getMultimediaContentType()).isEqualTo(DEFAULT_MULTIMEDIA_CONTENT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void createChatbootStyleWithExistingId() throws Exception {
//        // Create the ChatbootStyle with an existing ID
//        chatbootStyle.setId(1L);
//
//        int databaseSizeBeforeCreate = chatbootStyleRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restChatbootStyleMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chatbootStyle)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkNamePropertiesIsRequired() throws Exception {
//        int databaseSizeBeforeTest = chatbootStyleRepository.findAll().size();
//        // set the field null
//        chatbootStyle.setNameProperties(null);
//
//        // Create the ChatbootStyle, which fails.
//
//        restChatbootStyleMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chatbootStyle)))
//            .andExpect(status().isBadRequest());
//
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkValueIsRequired() throws Exception {
//        int databaseSizeBeforeTest = chatbootStyleRepository.findAll().size();
//        // set the field null
//        chatbootStyle.setValue(null);
//
//        // Create the ChatbootStyle, which fails.
//
//        restChatbootStyleMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chatbootStyle)))
//            .andExpect(status().isBadRequest());
//
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllChatbootStyles() throws Exception {
//        // Initialize the database
//        chatbootStyleRepository.saveAndFlush(chatbootStyle);
//
//        // Get all the chatbootStyleList
//        restChatbootStyleMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(chatbootStyle.getId().intValue())))
//            .andExpect(jsonPath("$.[*].nameProperties").value(hasItem(DEFAULT_NAME_PROPERTIES)))
//            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
//            .andExpect(jsonPath("$.[*].multimediaContentType").value(hasItem(DEFAULT_MULTIMEDIA_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].multimedia").value(hasItem(Base64Utils.encodeToString(DEFAULT_MULTIMEDIA))));
//    }
//
//    @Test
//    @Transactional
//    void getChatbootStyle() throws Exception {
//        // Initialize the database
//        chatbootStyleRepository.saveAndFlush(chatbootStyle);
//
//        // Get the chatbootStyle
//        restChatbootStyleMockMvc
//            .perform(get(ENTITY_API_URL_ID, chatbootStyle.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(chatbootStyle.getId().intValue()))
//            .andExpect(jsonPath("$.nameProperties").value(DEFAULT_NAME_PROPERTIES))
//            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
//            .andExpect(jsonPath("$.multimediaContentType").value(DEFAULT_MULTIMEDIA_CONTENT_TYPE))
//            .andExpect(jsonPath("$.multimedia").value(Base64Utils.encodeToString(DEFAULT_MULTIMEDIA)));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingChatbootStyle() throws Exception {
//        // Get the chatbootStyle
//        restChatbootStyleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingChatbootStyle() throws Exception {
//        // Initialize the database
//        chatbootStyleRepository.saveAndFlush(chatbootStyle);
//
//        int databaseSizeBeforeUpdate = chatbootStyleRepository.findAll().size();
//
//        // Update the chatbootStyle
//        ChatbootStyle updatedChatbootStyle = chatbootStyleRepository.findById(chatbootStyle.getId()).get();
//        // Disconnect from session so that the updates on updatedChatbootStyle are not directly saved in db
//        em.detach(updatedChatbootStyle);
//        updatedChatbootStyle
//            .nameProperties(UPDATED_NAME_PROPERTIES)
//            .value(UPDATED_VALUE)
//            .multimedia(UPDATED_MULTIMEDIA)
//            .multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//
//        restChatbootStyleMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedChatbootStyle.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedChatbootStyle))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeUpdate);
//        ChatbootStyle testChatbootStyle = chatbootStyleList.get(chatbootStyleList.size() - 1);
//        assertThat(testChatbootStyle.getNameProperties()).isEqualTo(UPDATED_NAME_PROPERTIES);
//        assertThat(testChatbootStyle.getValue()).isEqualTo(UPDATED_VALUE);
//        assertThat(testChatbootStyle.getMultimedia()).isEqualTo(UPDATED_MULTIMEDIA);
//        assertThat(testChatbootStyle.getMultimediaContentType()).isEqualTo(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingChatbootStyle() throws Exception {
//        int databaseSizeBeforeUpdate = chatbootStyleRepository.findAll().size();
//        chatbootStyle.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restChatbootStyleMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, chatbootStyle.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(chatbootStyle))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchChatbootStyle() throws Exception {
//        int databaseSizeBeforeUpdate = chatbootStyleRepository.findAll().size();
//        chatbootStyle.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restChatbootStyleMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(chatbootStyle))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamChatbootStyle() throws Exception {
//        int databaseSizeBeforeUpdate = chatbootStyleRepository.findAll().size();
//        chatbootStyle.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restChatbootStyleMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(chatbootStyle)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateChatbootStyleWithPatch() throws Exception {
//        // Initialize the database
//        chatbootStyleRepository.saveAndFlush(chatbootStyle);
//
//        int databaseSizeBeforeUpdate = chatbootStyleRepository.findAll().size();
//
//        // Update the chatbootStyle using partial update
//        ChatbootStyle partialUpdatedChatbootStyle = new ChatbootStyle();
//        partialUpdatedChatbootStyle.setId(chatbootStyle.getId());
//
//        partialUpdatedChatbootStyle.multimedia(UPDATED_MULTIMEDIA).multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//
//        restChatbootStyleMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedChatbootStyle.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChatbootStyle))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeUpdate);
//        ChatbootStyle testChatbootStyle = chatbootStyleList.get(chatbootStyleList.size() - 1);
//        assertThat(testChatbootStyle.getNameProperties()).isEqualTo(DEFAULT_NAME_PROPERTIES);
//        assertThat(testChatbootStyle.getValue()).isEqualTo(DEFAULT_VALUE);
//        assertThat(testChatbootStyle.getMultimedia()).isEqualTo(UPDATED_MULTIMEDIA);
//        assertThat(testChatbootStyle.getMultimediaContentType()).isEqualTo(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateChatbootStyleWithPatch() throws Exception {
//        // Initialize the database
//        chatbootStyleRepository.saveAndFlush(chatbootStyle);
//
//        int databaseSizeBeforeUpdate = chatbootStyleRepository.findAll().size();
//
//        // Update the chatbootStyle using partial update
//        ChatbootStyle partialUpdatedChatbootStyle = new ChatbootStyle();
//        partialUpdatedChatbootStyle.setId(chatbootStyle.getId());
//
//        partialUpdatedChatbootStyle
//            .nameProperties(UPDATED_NAME_PROPERTIES)
//            .value(UPDATED_VALUE)
//            .multimedia(UPDATED_MULTIMEDIA)
//            .multimediaContentType(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//
//        restChatbootStyleMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedChatbootStyle.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedChatbootStyle))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeUpdate);
//        ChatbootStyle testChatbootStyle = chatbootStyleList.get(chatbootStyleList.size() - 1);
//        assertThat(testChatbootStyle.getNameProperties()).isEqualTo(UPDATED_NAME_PROPERTIES);
//        assertThat(testChatbootStyle.getValue()).isEqualTo(UPDATED_VALUE);
//        assertThat(testChatbootStyle.getMultimedia()).isEqualTo(UPDATED_MULTIMEDIA);
//        assertThat(testChatbootStyle.getMultimediaContentType()).isEqualTo(UPDATED_MULTIMEDIA_CONTENT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingChatbootStyle() throws Exception {
//        int databaseSizeBeforeUpdate = chatbootStyleRepository.findAll().size();
//        chatbootStyle.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restChatbootStyleMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, chatbootStyle.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(chatbootStyle))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchChatbootStyle() throws Exception {
//        int databaseSizeBeforeUpdate = chatbootStyleRepository.findAll().size();
//        chatbootStyle.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restChatbootStyleMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(chatbootStyle))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamChatbootStyle() throws Exception {
//        int databaseSizeBeforeUpdate = chatbootStyleRepository.findAll().size();
//        chatbootStyle.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restChatbootStyleMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(chatbootStyle))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the ChatbootStyle in the database
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteChatbootStyle() throws Exception {
//        // Initialize the database
//        chatbootStyleRepository.saveAndFlush(chatbootStyle);
//
//        int databaseSizeBeforeDelete = chatbootStyleRepository.findAll().size();
//
//        // Delete the chatbootStyle
//        restChatbootStyleMockMvc
//            .perform(delete(ENTITY_API_URL_ID, chatbootStyle.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<ChatbootStyle> chatbootStyleList = chatbootStyleRepository.findAll();
//        assertThat(chatbootStyleList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
