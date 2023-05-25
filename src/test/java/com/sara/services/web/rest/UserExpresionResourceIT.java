//package com.sara.services.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.sara.services.IntegrationTest;
//import com.sara.services.domain.UserExpresion;
//import com.sara.services.domain.enumeration.Priority;
//import com.sara.services.repository.UserExpresionRepository;
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
// * Integration tests for the {@link UserExpresionResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class UserExpresionResourceIT {
//
//    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
//    private static final String UPDATED_VALUE = "BBBBBBBBBB";
//
//    private static final Priority DEFAULT_PRIORITY = Priority.LOW;
//    private static final Priority UPDATED_PRIORITY = Priority.MEDIA;
//
//    private static final String ENTITY_API_URL = "/api/user-expresions";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private UserExpresionRepository userExpresionRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restUserExpresionMockMvc;
//
//    private UserExpresion userExpresion;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static UserExpresion createEntity(EntityManager em) {
//        UserExpresion userExpresion = new UserExpresion().value(DEFAULT_VALUE).priority(DEFAULT_PRIORITY);
//        return userExpresion;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static UserExpresion createUpdatedEntity(EntityManager em) {
//        UserExpresion userExpresion = new UserExpresion().value(UPDATED_VALUE).priority(UPDATED_PRIORITY);
//        return userExpresion;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        userExpresion = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createUserExpresion() throws Exception {
//        int databaseSizeBeforeCreate = userExpresionRepository.findAll().size();
//        // Create the UserExpresion
//        restUserExpresionMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExpresion)))
//            .andExpect(status().isCreated());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeCreate + 1);
//        UserExpresion testUserExpresion = userExpresionList.get(userExpresionList.size() - 1);
//        assertThat(testUserExpresion.getValue()).isEqualTo(DEFAULT_VALUE);
//        assertThat(testUserExpresion.getPriority()).isEqualTo(DEFAULT_PRIORITY);
//    }
//
//    @Test
//    @Transactional
//    void createUserExpresionWithExistingId() throws Exception {
//        // Create the UserExpresion with an existing ID
//        userExpresion.setId(1L);
//
//        int databaseSizeBeforeCreate = userExpresionRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restUserExpresionMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExpresion)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkValueIsRequired() throws Exception {
//        int databaseSizeBeforeTest = userExpresionRepository.findAll().size();
//        // set the field null
//        userExpresion.setValue(null);
//
//        // Create the UserExpresion, which fails.
//
//        restUserExpresionMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExpresion)))
//            .andExpect(status().isBadRequest());
//
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllUserExpresions() throws Exception {
//        // Initialize the database
//        userExpresionRepository.saveAndFlush(userExpresion);
//
//        // Get all the userExpresionList
//        restUserExpresionMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(userExpresion.getId().intValue())))
//            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
//            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())));
//    }
//
//    @Test
//    @Transactional
//    void getUserExpresion() throws Exception {
//        // Initialize the database
//        userExpresionRepository.saveAndFlush(userExpresion);
//
//        // Get the userExpresion
//        restUserExpresionMockMvc
//            .perform(get(ENTITY_API_URL_ID, userExpresion.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(userExpresion.getId().intValue()))
//            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
//            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingUserExpresion() throws Exception {
//        // Get the userExpresion
//        restUserExpresionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingUserExpresion() throws Exception {
//        // Initialize the database
//        userExpresionRepository.saveAndFlush(userExpresion);
//
//        int databaseSizeBeforeUpdate = userExpresionRepository.findAll().size();
//
//        // Update the userExpresion
//        UserExpresion updatedUserExpresion = userExpresionRepository.findById(userExpresion.getId()).get();
//        // Disconnect from session so that the updates on updatedUserExpresion are not directly saved in db
//        em.detach(updatedUserExpresion);
//        updatedUserExpresion.value(UPDATED_VALUE).priority(UPDATED_PRIORITY);
//
//        restUserExpresionMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedUserExpresion.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedUserExpresion))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeUpdate);
//        UserExpresion testUserExpresion = userExpresionList.get(userExpresionList.size() - 1);
//        assertThat(testUserExpresion.getValue()).isEqualTo(UPDATED_VALUE);
//        assertThat(testUserExpresion.getPriority()).isEqualTo(UPDATED_PRIORITY);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingUserExpresion() throws Exception {
//        int databaseSizeBeforeUpdate = userExpresionRepository.findAll().size();
//        userExpresion.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restUserExpresionMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, userExpresion.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(userExpresion))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchUserExpresion() throws Exception {
//        int databaseSizeBeforeUpdate = userExpresionRepository.findAll().size();
//        userExpresion.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restUserExpresionMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(userExpresion))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamUserExpresion() throws Exception {
//        int databaseSizeBeforeUpdate = userExpresionRepository.findAll().size();
//        userExpresion.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restUserExpresionMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userExpresion)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateUserExpresionWithPatch() throws Exception {
//        // Initialize the database
//        userExpresionRepository.saveAndFlush(userExpresion);
//
//        int databaseSizeBeforeUpdate = userExpresionRepository.findAll().size();
//
//        // Update the userExpresion using partial update
//        UserExpresion partialUpdatedUserExpresion = new UserExpresion();
//        partialUpdatedUserExpresion.setId(userExpresion.getId());
//
//        partialUpdatedUserExpresion.value(UPDATED_VALUE).priority(UPDATED_PRIORITY);
//
//        restUserExpresionMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedUserExpresion.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExpresion))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeUpdate);
//        UserExpresion testUserExpresion = userExpresionList.get(userExpresionList.size() - 1);
//        assertThat(testUserExpresion.getValue()).isEqualTo(UPDATED_VALUE);
//        assertThat(testUserExpresion.getPriority()).isEqualTo(UPDATED_PRIORITY);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateUserExpresionWithPatch() throws Exception {
//        // Initialize the database
//        userExpresionRepository.saveAndFlush(userExpresion);
//
//        int databaseSizeBeforeUpdate = userExpresionRepository.findAll().size();
//
//        // Update the userExpresion using partial update
//        UserExpresion partialUpdatedUserExpresion = new UserExpresion();
//        partialUpdatedUserExpresion.setId(userExpresion.getId());
//
//        partialUpdatedUserExpresion.value(UPDATED_VALUE).priority(UPDATED_PRIORITY);
//
//        restUserExpresionMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedUserExpresion.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserExpresion))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeUpdate);
//        UserExpresion testUserExpresion = userExpresionList.get(userExpresionList.size() - 1);
//        assertThat(testUserExpresion.getValue()).isEqualTo(UPDATED_VALUE);
//        assertThat(testUserExpresion.getPriority()).isEqualTo(UPDATED_PRIORITY);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingUserExpresion() throws Exception {
//        int databaseSizeBeforeUpdate = userExpresionRepository.findAll().size();
//        userExpresion.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restUserExpresionMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, userExpresion.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(userExpresion))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchUserExpresion() throws Exception {
//        int databaseSizeBeforeUpdate = userExpresionRepository.findAll().size();
//        userExpresion.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restUserExpresionMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(userExpresion))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamUserExpresion() throws Exception {
//        int databaseSizeBeforeUpdate = userExpresionRepository.findAll().size();
//        userExpresion.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restUserExpresionMockMvc
//            .perform(
//                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userExpresion))
//            )
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the UserExpresion in the database
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteUserExpresion() throws Exception {
//        // Initialize the database
//        userExpresionRepository.saveAndFlush(userExpresion);
//
//        int databaseSizeBeforeDelete = userExpresionRepository.findAll().size();
//
//        // Delete the userExpresion
//        restUserExpresionMockMvc
//            .perform(delete(ENTITY_API_URL_ID, userExpresion.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<UserExpresion> userExpresionList = userExpresionRepository.findAll();
//        assertThat(userExpresionList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
