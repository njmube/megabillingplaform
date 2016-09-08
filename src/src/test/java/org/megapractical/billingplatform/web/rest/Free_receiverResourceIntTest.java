package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_receiver;
import org.megapractical.billingplatform.repository.Free_receiverRepository;
import org.megapractical.billingplatform.service.Free_receiverService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Free_receiverResource REST controller.
 *
 * @see Free_receiverResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_receiverResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_RFC = "AAAAAAAAAAAA";
    private static final String UPDATED_RFC = "BBBBBBBBBBBB";
    private static final String DEFAULT_BUSINESS_NAME = "AAA";
    private static final String UPDATED_BUSINESS_NAME = "BBB";
    private static final String DEFAULT_EMAIL = "AAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final ZonedDateTime DEFAULT_CREATE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATE_DATE_STR = dateTimeFormatter.format(DEFAULT_CREATE_DATE);
    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";
    private static final String DEFAULT_NO_EXT = "AAAAA";
    private static final String UPDATED_NO_EXT = "BBBBB";
    private static final String DEFAULT_NO_INT = "AAAAA";
    private static final String UPDATED_NO_INT = "BBBBB";
    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";
    private static final String DEFAULT_PHONE = "A";
    private static final String UPDATED_PHONE = "B";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";

    @Inject
    private Free_receiverRepository free_receiverRepository;

    @Inject
    private Free_receiverService free_receiverService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_receiverMockMvc;

    private Free_receiver free_receiver;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_receiverResource free_receiverResource = new Free_receiverResource();
        ReflectionTestUtils.setField(free_receiverResource, "free_receiverService", free_receiverService);
        this.restFree_receiverMockMvc = MockMvcBuilders.standaloneSetup(free_receiverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_receiver = new Free_receiver();
        free_receiver.setRfc(DEFAULT_RFC);
        free_receiver.setBusiness_name(DEFAULT_BUSINESS_NAME);
        free_receiver.setEmail(DEFAULT_EMAIL);
        free_receiver.setActivated(DEFAULT_ACTIVATED);
        free_receiver.setCreate_date(DEFAULT_CREATE_DATE);
        free_receiver.setStreet(DEFAULT_STREET);
        free_receiver.setNo_ext(DEFAULT_NO_EXT);
        free_receiver.setNo_int(DEFAULT_NO_INT);
        free_receiver.setReference(DEFAULT_REFERENCE);
        free_receiver.setPhone(DEFAULT_PHONE);
        free_receiver.setLocation(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void createFree_receiver() throws Exception {
        int databaseSizeBeforeCreate = free_receiverRepository.findAll().size();

        // Create the Free_receiver

        restFree_receiverMockMvc.perform(post("/api/free-receivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_receiver)))
                .andExpect(status().isCreated());

        // Validate the Free_receiver in the database
        List<Free_receiver> free_receivers = free_receiverRepository.findAll();
        assertThat(free_receivers).hasSize(databaseSizeBeforeCreate + 1);
        Free_receiver testFree_receiver = free_receivers.get(free_receivers.size() - 1);
        assertThat(testFree_receiver.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFree_receiver.getBusiness_name()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testFree_receiver.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testFree_receiver.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testFree_receiver.getCreate_date()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testFree_receiver.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testFree_receiver.getNo_ext()).isEqualTo(DEFAULT_NO_EXT);
        assertThat(testFree_receiver.getNo_int()).isEqualTo(DEFAULT_NO_INT);
        assertThat(testFree_receiver.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testFree_receiver.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testFree_receiver.getLocation()).isEqualTo(DEFAULT_LOCATION);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_receiverRepository.findAll().size();
        // set the field null
        free_receiver.setRfc(null);

        // Create the Free_receiver, which fails.

        restFree_receiverMockMvc.perform(post("/api/free-receivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_receiver)))
                .andExpect(status().isBadRequest());

        List<Free_receiver> free_receivers = free_receiverRepository.findAll();
        assertThat(free_receivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBusiness_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_receiverRepository.findAll().size();
        // set the field null
        free_receiver.setBusiness_name(null);

        // Create the Free_receiver, which fails.

        restFree_receiverMockMvc.perform(post("/api/free-receivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_receiver)))
                .andExpect(status().isBadRequest());

        List<Free_receiver> free_receivers = free_receiverRepository.findAll();
        assertThat(free_receivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_receiverRepository.findAll().size();
        // set the field null
        free_receiver.setActivated(null);

        // Create the Free_receiver, which fails.

        restFree_receiverMockMvc.perform(post("/api/free-receivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_receiver)))
                .andExpect(status().isBadRequest());

        List<Free_receiver> free_receivers = free_receiverRepository.findAll();
        assertThat(free_receivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreate_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_receiverRepository.findAll().size();
        // set the field null
        free_receiver.setCreate_date(null);

        // Create the Free_receiver, which fails.

        restFree_receiverMockMvc.perform(post("/api/free-receivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_receiver)))
                .andExpect(status().isBadRequest());

        List<Free_receiver> free_receivers = free_receiverRepository.findAll();
        assertThat(free_receivers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFree_receivers() throws Exception {
        // Initialize the database
        free_receiverRepository.saveAndFlush(free_receiver);

        // Get all the free_receivers
        restFree_receiverMockMvc.perform(get("/api/free-receivers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_receiver.getId().intValue())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].business_name").value(hasItem(DEFAULT_BUSINESS_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
                .andExpect(jsonPath("$.[*].create_date").value(hasItem(DEFAULT_CREATE_DATE_STR)))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].no_ext").value(hasItem(DEFAULT_NO_EXT.toString())))
                .andExpect(jsonPath("$.[*].no_int").value(hasItem(DEFAULT_NO_INT.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getFree_receiver() throws Exception {
        // Initialize the database
        free_receiverRepository.saveAndFlush(free_receiver);

        // Get the free_receiver
        restFree_receiverMockMvc.perform(get("/api/free-receivers/{id}", free_receiver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_receiver.getId().intValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.business_name").value(DEFAULT_BUSINESS_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.create_date").value(DEFAULT_CREATE_DATE_STR))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.no_ext").value(DEFAULT_NO_EXT.toString()))
            .andExpect(jsonPath("$.no_int").value(DEFAULT_NO_INT.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFree_receiver() throws Exception {
        // Get the free_receiver
        restFree_receiverMockMvc.perform(get("/api/free-receivers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFree_receiver() throws Exception {
        // Initialize the database
        free_receiverService.save(free_receiver);

        int databaseSizeBeforeUpdate = free_receiverRepository.findAll().size();

        // Update the free_receiver
        Free_receiver updatedFree_receiver = new Free_receiver();
        updatedFree_receiver.setId(free_receiver.getId());
        updatedFree_receiver.setRfc(UPDATED_RFC);
        updatedFree_receiver.setBusiness_name(UPDATED_BUSINESS_NAME);
        updatedFree_receiver.setEmail(UPDATED_EMAIL);
        updatedFree_receiver.setActivated(UPDATED_ACTIVATED);
        updatedFree_receiver.setCreate_date(UPDATED_CREATE_DATE);
        updatedFree_receiver.setStreet(UPDATED_STREET);
        updatedFree_receiver.setNo_ext(UPDATED_NO_EXT);
        updatedFree_receiver.setNo_int(UPDATED_NO_INT);
        updatedFree_receiver.setReference(UPDATED_REFERENCE);
        updatedFree_receiver.setPhone(UPDATED_PHONE);
        updatedFree_receiver.setLocation(UPDATED_LOCATION);

        restFree_receiverMockMvc.perform(put("/api/free-receivers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_receiver)))
                .andExpect(status().isOk());

        // Validate the Free_receiver in the database
        List<Free_receiver> free_receivers = free_receiverRepository.findAll();
        assertThat(free_receivers).hasSize(databaseSizeBeforeUpdate);
        Free_receiver testFree_receiver = free_receivers.get(free_receivers.size() - 1);
        assertThat(testFree_receiver.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFree_receiver.getBusiness_name()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testFree_receiver.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testFree_receiver.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testFree_receiver.getCreate_date()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testFree_receiver.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testFree_receiver.getNo_ext()).isEqualTo(UPDATED_NO_EXT);
        assertThat(testFree_receiver.getNo_int()).isEqualTo(UPDATED_NO_INT);
        assertThat(testFree_receiver.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testFree_receiver.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testFree_receiver.getLocation()).isEqualTo(UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void deleteFree_receiver() throws Exception {
        // Initialize the database
        free_receiverService.save(free_receiver);

        int databaseSizeBeforeDelete = free_receiverRepository.findAll().size();

        // Get the free_receiver
        restFree_receiverMockMvc.perform(delete("/api/free-receivers/{id}", free_receiver.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_receiver> free_receivers = free_receiverRepository.findAll();
        assertThat(free_receivers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
