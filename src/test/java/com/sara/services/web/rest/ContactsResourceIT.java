//package com.sara.services.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.sara.services.IntegrationTest;
//import com.sara.services.domain.Contacts;
//import com.sara.services.repository.ContactsRepository;
//import com.sara.services.service.criteria.ContactsCriteria;
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
// * Integration tests for the {@link ContactsResource} REST controller.
// */
//@IntegrationTest
//@AutoConfigureMockMvc
//@WithMockUser
//class ContactsResourceIT {
//
//    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
//    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";
//
//    private static final Instant DEFAULT_LAST_DAY_CONNECTION = Instant.ofEpochMilli(0L);
//    private static final Instant UPDATED_LAST_DAY_CONNECTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);
//
//    private static final String ENTITY_API_URL = "/api/contacts";
//    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
//
//    private static Random random = new Random();
//    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
//
//    @Autowired
//    private ContactsRepository contactsRepository;
//
//    @Autowired
//    private EntityManager em;
//
//    @Autowired
//    private MockMvc restContactsMockMvc;
//
//    private Contacts contacts;
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Contacts createEntity(EntityManager em) {
//        Contacts contacts = new Contacts().phoneNumber(DEFAULT_PHONE_NUMBER).lastDayConnection(DEFAULT_LAST_DAY_CONNECTION);
//        return contacts;
//    }
//
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static Contacts createUpdatedEntity(EntityManager em) {
//        Contacts contacts = new Contacts().phoneNumber(UPDATED_PHONE_NUMBER).lastDayConnection(UPDATED_LAST_DAY_CONNECTION);
//        return contacts;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        contacts = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    void createContacts() throws Exception {
//        int databaseSizeBeforeCreate = contactsRepository.findAll().size();
//        // Create the Contacts
//        restContactsMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contacts)))
//            .andExpect(status().isCreated());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeCreate + 1);
//        Contacts testContacts = contactsList.get(contactsList.size() - 1);
//        assertThat(testContacts.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
//        assertThat(testContacts.getLastDayConnection()).isEqualTo(DEFAULT_LAST_DAY_CONNECTION);
//    }
//
//    @Test
//    @Transactional
//    void createContactsWithExistingId() throws Exception {
//        // Create the Contacts with an existing ID
//        contacts.setId(1L);
//
//        int databaseSizeBeforeCreate = contactsRepository.findAll().size();
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restContactsMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contacts)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    void checkPhoneNumberIsRequired() throws Exception {
//        int databaseSizeBeforeTest = contactsRepository.findAll().size();
//        // set the field null
//        contacts.setPhoneNumber(null);
//
//        // Create the Contacts, which fails.
//
//        restContactsMockMvc
//            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contacts)))
//            .andExpect(status().isBadRequest());
//
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeTest);
//    }
//
//    @Test
//    @Transactional
//    void getAllContacts() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(contacts.getId().intValue())))
//            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
//            .andExpect(jsonPath("$.[*].lastDayConnection").value(hasItem(DEFAULT_LAST_DAY_CONNECTION.toString())));
//    }
//
//    @Test
//    @Transactional
//    void getContacts() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get the contacts
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL_ID, contacts.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(contacts.getId().intValue()))
//            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
//            .andExpect(jsonPath("$.lastDayConnection").value(DEFAULT_LAST_DAY_CONNECTION.toString()));
//    }
//
//    @Test
//    @Transactional
//    void getContactsByIdFiltering() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        Long id = contacts.getId();
//
//        defaultContactsShouldBeFound("id.equals=" + id);
//        defaultContactsShouldNotBeFound("id.notEquals=" + id);
//
//        defaultContactsShouldBeFound("id.greaterThanOrEqual=" + id);
//        defaultContactsShouldNotBeFound("id.greaterThan=" + id);
//
//        defaultContactsShouldBeFound("id.lessThanOrEqual=" + id);
//        defaultContactsShouldNotBeFound("id.lessThan=" + id);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByPhoneNumberIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where phoneNumber equals to DEFAULT_PHONE_NUMBER
//        defaultContactsShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);
//
//        // Get all the contactsList where phoneNumber equals to UPDATED_PHONE_NUMBER
//        defaultContactsShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByPhoneNumberIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
//        defaultContactsShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);
//
//        // Get all the contactsList where phoneNumber equals to UPDATED_PHONE_NUMBER
//        defaultContactsShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByPhoneNumberIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where phoneNumber is not null
//        defaultContactsShouldBeFound("phoneNumber.specified=true");
//
//        // Get all the contactsList where phoneNumber is null
//        defaultContactsShouldNotBeFound("phoneNumber.specified=false");
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByPhoneNumberContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where phoneNumber contains DEFAULT_PHONE_NUMBER
//        defaultContactsShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);
//
//        // Get all the contactsList where phoneNumber contains UPDATED_PHONE_NUMBER
//        defaultContactsShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByPhoneNumberNotContainsSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
//        defaultContactsShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);
//
//        // Get all the contactsList where phoneNumber does not contain UPDATED_PHONE_NUMBER
//        defaultContactsShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastDayConnectionIsEqualToSomething() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastDayConnection equals to DEFAULT_LAST_DAY_CONNECTION
//        defaultContactsShouldBeFound("lastDayConnection.equals=" + DEFAULT_LAST_DAY_CONNECTION);
//
//        // Get all the contactsList where lastDayConnection equals to UPDATED_LAST_DAY_CONNECTION
//        defaultContactsShouldNotBeFound("lastDayConnection.equals=" + UPDATED_LAST_DAY_CONNECTION);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastDayConnectionIsInShouldWork() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastDayConnection in DEFAULT_LAST_DAY_CONNECTION or UPDATED_LAST_DAY_CONNECTION
//        defaultContactsShouldBeFound("lastDayConnection.in=" + DEFAULT_LAST_DAY_CONNECTION + "," + UPDATED_LAST_DAY_CONNECTION);
//
//        // Get all the contactsList where lastDayConnection equals to UPDATED_LAST_DAY_CONNECTION
//        defaultContactsShouldNotBeFound("lastDayConnection.in=" + UPDATED_LAST_DAY_CONNECTION);
//    }
//
//    @Test
//    @Transactional
//    void getAllContactsByLastDayConnectionIsNullOrNotNull() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        // Get all the contactsList where lastDayConnection is not null
//        defaultContactsShouldBeFound("lastDayConnection.specified=true");
//
//        // Get all the contactsList where lastDayConnection is null
//        defaultContactsShouldNotBeFound("lastDayConnection.specified=false");
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is returned.
//     */
//    private void defaultContactsShouldBeFound(String filter) throws Exception {
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(contacts.getId().intValue())))
//            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
//            .andExpect(jsonPath("$.[*].lastDayConnection").value(hasItem(DEFAULT_LAST_DAY_CONNECTION.toString())));
//
//        // Check, that the count call also returns 1
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("1"));
//    }
//
//    /**
//     * Executes the search, and checks that the default entity is not returned.
//     */
//    private void defaultContactsShouldNotBeFound(String filter) throws Exception {
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$").isArray())
//            .andExpect(jsonPath("$").isEmpty());
//
//        // Check, that the count call also returns 0
//        restContactsMockMvc
//            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(content().string("0"));
//    }
//
//    @Test
//    @Transactional
//    void getNonExistingContacts() throws Exception {
//        // Get the contacts
//        restContactsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    void putExistingContacts() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//
//        // Update the contacts
//        Contacts updatedContacts = contactsRepository.findById(contacts.getId()).get();
//        // Disconnect from session so that the updates on updatedContacts are not directly saved in db
//        em.detach(updatedContacts);
//        updatedContacts.phoneNumber(UPDATED_PHONE_NUMBER).lastDayConnection(UPDATED_LAST_DAY_CONNECTION);
//
//        restContactsMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, updatedContacts.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(updatedContacts))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//        Contacts testContacts = contactsList.get(contactsList.size() - 1);
//        assertThat(testContacts.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
//        assertThat(testContacts.getLastDayConnection()).isEqualTo(UPDATED_LAST_DAY_CONNECTION);
//    }
//
//    @Test
//    @Transactional
//    void putNonExistingContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, contacts.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(contacts))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithIdMismatchContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(
//                put(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(TestUtil.convertObjectToJsonBytes(contacts))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void putWithMissingIdPathParamContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contacts)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void partialUpdateContactsWithPatch() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//
//        // Update the contacts using partial update
//        Contacts partialUpdatedContacts = new Contacts();
//        partialUpdatedContacts.setId(contacts.getId());
//
//        partialUpdatedContacts.phoneNumber(UPDATED_PHONE_NUMBER);
//
//        restContactsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedContacts.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContacts))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//        Contacts testContacts = contactsList.get(contactsList.size() - 1);
//        assertThat(testContacts.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
//        assertThat(testContacts.getLastDayConnection()).isEqualTo(DEFAULT_LAST_DAY_CONNECTION);
//    }
//
//    @Test
//    @Transactional
//    void fullUpdateContactsWithPatch() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//
//        // Update the contacts using partial update
//        Contacts partialUpdatedContacts = new Contacts();
//        partialUpdatedContacts.setId(contacts.getId());
//
//        partialUpdatedContacts.phoneNumber(UPDATED_PHONE_NUMBER).lastDayConnection(UPDATED_LAST_DAY_CONNECTION);
//
//        restContactsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, partialUpdatedContacts.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContacts))
//            )
//            .andExpect(status().isOk());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//        Contacts testContacts = contactsList.get(contactsList.size() - 1);
//        assertThat(testContacts.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
//        assertThat(testContacts.getLastDayConnection()).isEqualTo(UPDATED_LAST_DAY_CONNECTION);
//    }
//
//    @Test
//    @Transactional
//    void patchNonExistingContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, contacts.getId())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(contacts))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithIdMismatchContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(
//                patch(ENTITY_API_URL_ID, count.incrementAndGet())
//                    .contentType("application/merge-patch+json")
//                    .content(TestUtil.convertObjectToJsonBytes(contacts))
//            )
//            .andExpect(status().isBadRequest());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void patchWithMissingIdPathParamContacts() throws Exception {
//        int databaseSizeBeforeUpdate = contactsRepository.findAll().size();
//        contacts.setId(count.incrementAndGet());
//
//        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
//        restContactsMockMvc
//            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contacts)))
//            .andExpect(status().isMethodNotAllowed());
//
//        // Validate the Contacts in the database
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    void deleteContacts() throws Exception {
//        // Initialize the database
//        contactsRepository.saveAndFlush(contacts);
//
//        int databaseSizeBeforeDelete = contactsRepository.findAll().size();
//
//        // Delete the contacts
//        restContactsMockMvc
//            .perform(delete(ENTITY_API_URL_ID, contacts.getId()).accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<Contacts> contactsList = contactsRepository.findAll();
//        assertThat(contactsList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
