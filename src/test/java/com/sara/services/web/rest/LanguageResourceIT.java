//package com.sara.services.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.sara.services.IntegrationTest;
//import com.sara.services.domain.Language;
//import com.sara.services.repository.LanguageRepository;
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
// * Integration tests for the {@link LanguageResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class LanguageResourceIT {
//
//    private static final String DEFAULT_NAME = "AAAAAAAAAA";
//    private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_ISO_VALUE = "AAAAAAAAAA";
//    private static final String UPDATED_ISO_VALUE = "BBBBBBBBBB";
//
//    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
//
//    private static final String DEFAULT_ICONSRC = "AAAAAAAAAA";
//    private static final String UPDATED_ICONSRC = "BBBBBBBBBB";
//
//    private static final byte[] DEFAULT_IMG_BLOG_ICON = TestUtil.createByteArray(1, "0");
//    private static final byte[] UPDATED_IMG_BLOG_ICON = TestUtil.createByteArray(1, "1");
//    private static final String DEFAULT_IMG_BLOG_ICON_CONTENT_TYPE = "image/jpg";
//    private static final String UPDATED_IMG_BLOG_ICON_CONTENT_TYPE = "image/png";
//
//    private static final String ENTITY_API_URL = "/api/languages";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private LanguageRepository languageRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restLanguageMockMvc;
//
//    private Language language;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Language createEntity(EntityManager em) {
//        Language language = new Language()
//            .name(DEFAULT_NAME)
//            .isoValue(DEFAULT_ISO_VALUE)
//            .description(DEFAULT_DESCRIPTION)
//            .iconsrc(DEFAULT_ICONSRC)
//            .imgBlogIcon(DEFAULT_IMG_BLOG_ICON)
//            .imgBlogIconContentType(DEFAULT_IMG_BLOG_ICON_CONTENT_TYPE);
//        return language;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Language createUpdatedEntity(EntityManager em) {
//        Language language = new Language()
//            .name(UPDATED_NAME)
//            .isoValue(UPDATED_ISO_VALUE)
//            .description(UPDATED_DESCRIPTION)
//            .iconsrc(UPDATED_ICONSRC)
//            .imgBlogIcon(UPDATED_IMG_BLOG_ICON)
//            .imgBlogIconContentType(UPDATED_IMG_BLOG_ICON_CONTENT_TYPE);
//        return language;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        language = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createLanguage() throws Exception {
//        int databaseSizeBeforeCreate = languageRepository.findAll().size();
//        // Create the Language
//        restLanguageMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(language)))
//            .andExpect(status().isCreated());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeCreate + 1);
//        Language testLanguage = languageList.get(languageList.size() - 1);
//        assertThat(testLanguage.getName()).isEqualTo(DEFAULT_NAME);
//        assertThat(testLanguage.getIsoValue()).isEqualTo(DEFAULT_ISO_VALUE);
//        assertThat(testLanguage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testLanguage.getIconsrc()).isEqualTo(DEFAULT_ICONSRC);
//        assertThat(testLanguage.getImgBlogIcon()).isEqualTo(DEFAULT_IMG_BLOG_ICON);
//        assertThat(testLanguage.getImgBlogIconContentType()).isEqualTo(DEFAULT_IMG_BLOG_ICON_CONTENT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void createLanguageWithExistingId() throws Exception {
//        // Create the Language with an existing ID
//        language.setId(1L);
//
//        int databaseSizeBeforeCreate = languageRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restLanguageMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(language)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkNameIsRequired() throws Exception {
//        int databaseSizeBeforeTest = languageRepository.findAll().size();
//        // set the field null
//        language.setName(null);
//
//        // Create the Language, which fails.
//
//        restLanguageMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(language)))
//            .andExpect(status().isBadRequest());
//
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkIsoValueIsRequired() throws Exception {
//        int databaseSizeBeforeTest = languageRepository.findAll().size();
//        // set the field null
//        language.setIsoValue(null);
//
//        // Create the Language, which fails.
//
//        restLanguageMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(language)))
//            .andExpect(status().isBadRequest());
//
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkDescriptionIsRequired() throws Exception {
//        int databaseSizeBeforeTest = languageRepository.findAll().size();
//        // set the field null
//        language.setDescription(null);
//
//        // Create the Language, which fails.
//
//        restLanguageMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(language)))
//            .andExpect(status().isBadRequest());
//
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void checkIconsrcIsRequired() throws Exception {
//        int databaseSizeBeforeTest = languageRepository.findAll().size();
//        // set the field null
//        language.setIconsrc(null);
//
//        // Create the Language, which fails.
//
//        restLanguageMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(language)))
//            .andExpect(status().isBadRequest());
//
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllLanguages() throws Exception {
//        // Initialize the database
//        languageRepository.saveAndFlush(language);
//
//        // Get all the languageList
//        restLanguageMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(language.getId().intValue())))
//            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
//            .andExpect(jsonPath("$.[*].isoValue").value(hasItem(DEFAULT_ISO_VALUE)))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//            .andExpect(jsonPath("$.[*].iconsrc").value(hasItem(DEFAULT_ICONSRC)))
//            .andExpect(jsonPath("$.[*].imgBlogIconContentType").value(hasItem(DEFAULT_IMG_BLOG_ICON_CONTENT_TYPE)))
//            .andExpect(jsonPath("$.[*].imgBlogIcon").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG_BLOG_ICON))));
//    }
//
//    @Test
//    @Transactional
//    void getLanguage() throws Exception {
//        // Initialize the database
//        languageRepository.saveAndFlush(language);
//
//        // Get the language
//        restLanguageMockMvc
//            .perform(get(ENTITY_API_URL_ID, language.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(language.getId().intValue()))
//            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
//            .andExpect(jsonPath("$.isoValue").value(DEFAULT_ISO_VALUE))
//            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
//            .andExpect(jsonPath("$.iconsrc").value(DEFAULT_ICONSRC))
//            .andExpect(jsonPath("$.imgBlogIconContentType").value(DEFAULT_IMG_BLOG_ICON_CONTENT_TYPE))
//            .andExpect(jsonPath("$.imgBlogIcon").value(Base64Utils.encodeToString(DEFAULT_IMG_BLOG_ICON)));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingLanguage() throws Exception {
//        // Get the language
//        restLanguageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingLanguage() throws Exception {
//        // Initialize the database
//        languageRepository.saveAndFlush(language);
//
//        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
//
//        // Update the language
//        Language updatedLanguage = languageRepository.findById(language.getId()).get();
//        // Disconnect from session so that the updates on updatedLanguage are not directly saved in db
//        em.detach(updatedLanguage);
//        updatedLanguage
//            .name(UPDATED_NAME)
//            .isoValue(UPDATED_ISO_VALUE)
//            .description(UPDATED_DESCRIPTION)
//            .iconsrc(UPDATED_ICONSRC)
//            .imgBlogIcon(UPDATED_IMG_BLOG_ICON)
//            .imgBlogIconContentType(UPDATED_IMG_BLOG_ICON_CONTENT_TYPE);
//
//        restLanguageMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedLanguage.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedLanguage))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
//        Language testLanguage = languageList.get(languageList.size() - 1);
//        assertThat(testLanguage.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testLanguage.getIsoValue()).isEqualTo(UPDATED_ISO_VALUE);
//        assertThat(testLanguage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testLanguage.getIconsrc()).isEqualTo(UPDATED_ICONSRC);
//        assertThat(testLanguage.getImgBlogIcon()).isEqualTo(UPDATED_IMG_BLOG_ICON);
//        assertThat(testLanguage.getImgBlogIconContentType()).isEqualTo(UPDATED_IMG_BLOG_ICON_CONTENT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingLanguage() throws Exception {
//        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
//        language.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restLanguageMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, language.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(language))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchLanguage() throws Exception {
//        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
//        language.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restLanguageMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(language))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamLanguage() throws Exception {
//        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
//        language.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restLanguageMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(language)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateLanguageWithPatch() throws Exception {
//        // Initialize the database
//        languageRepository.saveAndFlush(language);
//
//        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
//
//        // Update the language using partial update
//        Language partialUpdatedLanguage = new Language();
//        partialUpdatedLanguage.setId(language.getId());
//
//        partialUpdatedLanguage
//            .name(UPDATED_NAME)
//            .description(UPDATED_DESCRIPTION)
//            .iconsrc(UPDATED_ICONSRC)
//            .imgBlogIcon(UPDATED_IMG_BLOG_ICON)
//            .imgBlogIconContentType(UPDATED_IMG_BLOG_ICON_CONTENT_TYPE);
//
//        restLanguageMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedLanguage.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLanguage))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
//        Language testLanguage = languageList.get(languageList.size() - 1);
//        assertThat(testLanguage.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testLanguage.getIsoValue()).isEqualTo(DEFAULT_ISO_VALUE);
//        assertThat(testLanguage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testLanguage.getIconsrc()).isEqualTo(UPDATED_ICONSRC);
//        assertThat(testLanguage.getImgBlogIcon()).isEqualTo(UPDATED_IMG_BLOG_ICON);
//        assertThat(testLanguage.getImgBlogIconContentType()).isEqualTo(UPDATED_IMG_BLOG_ICON_CONTENT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateLanguageWithPatch() throws Exception {
//        // Initialize the database
//        languageRepository.saveAndFlush(language);
//
//        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
//
//        // Update the language using partial update
//        Language partialUpdatedLanguage = new Language();
//        partialUpdatedLanguage.setId(language.getId());
//
//        partialUpdatedLanguage
//            .name(UPDATED_NAME)
//            .isoValue(UPDATED_ISO_VALUE)
//            .description(UPDATED_DESCRIPTION)
//            .iconsrc(UPDATED_ICONSRC)
//            .imgBlogIcon(UPDATED_IMG_BLOG_ICON)
//            .imgBlogIconContentType(UPDATED_IMG_BLOG_ICON_CONTENT_TYPE);
//
//        restLanguageMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedLanguage.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLanguage))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
//        Language testLanguage = languageList.get(languageList.size() - 1);
//        assertThat(testLanguage.getName()).isEqualTo(UPDATED_NAME);
//        assertThat(testLanguage.getIsoValue()).isEqualTo(UPDATED_ISO_VALUE);
//        assertThat(testLanguage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testLanguage.getIconsrc()).isEqualTo(UPDATED_ICONSRC);
//        assertThat(testLanguage.getImgBlogIcon()).isEqualTo(UPDATED_IMG_BLOG_ICON);
//        assertThat(testLanguage.getImgBlogIconContentType()).isEqualTo(UPDATED_IMG_BLOG_ICON_CONTENT_TYPE);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingLanguage() throws Exception {
//        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
//        language.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restLanguageMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, language.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(language))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchLanguage() throws Exception {
//        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
//        language.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restLanguageMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(language))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamLanguage() throws Exception {
//        int databaseSizeBeforeUpdate = languageRepository.findAll().size();
//        language.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restLanguageMockMvc
//            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(language)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Language in the database
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteLanguage() throws Exception {
//        // Initialize the database
//        languageRepository.saveAndFlush(language);
//
//        int databaseSizeBeforeDelete = languageRepository.findAll().size();
//
//        // Delete the language
//        restLanguageMockMvc
//            .perform(delete(ENTITY_API_URL_ID, language.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Language> languageList = languageRepository.findAll();
//        assertThat(languageList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
