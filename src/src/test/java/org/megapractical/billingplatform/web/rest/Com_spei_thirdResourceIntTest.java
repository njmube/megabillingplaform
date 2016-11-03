package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_spei_third;
import org.megapractical.billingplatform.repository.Com_spei_thirdRepository;
import org.megapractical.billingplatform.service.Com_spei_thirdService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Com_spei_thirdResource REST controller.
 *
 * @see Com_spei_thirdResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_spei_thirdResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final LocalDate DEFAULT_DATE_OPERATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OPERATION = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_HOUR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_HOUR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_HOUR_STR = dateTimeFormatter.format(DEFAULT_HOUR);

    private static final Integer DEFAULT_KEY_SPEI = 1;
    private static final Integer UPDATED_KEY_SPEI = 2;
    private static final String DEFAULT_STAMP = "AAAAA";
    private static final String UPDATED_STAMP = "BBBBB";
    private static final String DEFAULT_NUMBER_CERTIFICATE = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMBER_CERTIFICATE = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CDA = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CDA = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private Com_spei_thirdRepository com_spei_thirdRepository;

    @Inject
    private Com_spei_thirdService com_spei_thirdService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_spei_thirdMockMvc;

    private Com_spei_third com_spei_third;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_spei_thirdResource com_spei_thirdResource = new Com_spei_thirdResource();
        ReflectionTestUtils.setField(com_spei_thirdResource, "com_spei_thirdService", com_spei_thirdService);
        this.restCom_spei_thirdMockMvc = MockMvcBuilders.standaloneSetup(com_spei_thirdResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_spei_third = new Com_spei_third();
        com_spei_third.setDate_operation(DEFAULT_DATE_OPERATION);
        com_spei_third.setHour(DEFAULT_HOUR);
        com_spei_third.setKey_spei(DEFAULT_KEY_SPEI);
        com_spei_third.setStamp(DEFAULT_STAMP);
        com_spei_third.setNumber_certificate(DEFAULT_NUMBER_CERTIFICATE);
        com_spei_third.setCda(DEFAULT_CDA);
    }

    @Test
    @Transactional
    public void createCom_spei_third() throws Exception {
        int databaseSizeBeforeCreate = com_spei_thirdRepository.findAll().size();

        // Create the Com_spei_third

        restCom_spei_thirdMockMvc.perform(post("/api/com-spei-thirds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_spei_third)))
                .andExpect(status().isCreated());

        // Validate the Com_spei_third in the database
        List<Com_spei_third> com_spei_thirds = com_spei_thirdRepository.findAll();
        assertThat(com_spei_thirds).hasSize(databaseSizeBeforeCreate + 1);
        Com_spei_third testCom_spei_third = com_spei_thirds.get(com_spei_thirds.size() - 1);
        assertThat(testCom_spei_third.getDate_operation()).isEqualTo(DEFAULT_DATE_OPERATION);
        assertThat(testCom_spei_third.getHour()).isEqualTo(DEFAULT_HOUR);
        assertThat(testCom_spei_third.getKey_spei()).isEqualTo(DEFAULT_KEY_SPEI);
        assertThat(testCom_spei_third.getStamp()).isEqualTo(DEFAULT_STAMP);
        assertThat(testCom_spei_third.getNumber_certificate()).isEqualTo(DEFAULT_NUMBER_CERTIFICATE);
        assertThat(testCom_spei_third.getCda()).isEqualTo(DEFAULT_CDA);
    }

    @Test
    @Transactional
    public void checkDate_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_spei_thirdRepository.findAll().size();
        // set the field null
        com_spei_third.setDate_operation(null);

        // Create the Com_spei_third, which fails.

        restCom_spei_thirdMockMvc.perform(post("/api/com-spei-thirds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_spei_third)))
                .andExpect(status().isBadRequest());

        List<Com_spei_third> com_spei_thirds = com_spei_thirdRepository.findAll();
        assertThat(com_spei_thirds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHourIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_spei_thirdRepository.findAll().size();
        // set the field null
        com_spei_third.setHour(null);

        // Create the Com_spei_third, which fails.

        restCom_spei_thirdMockMvc.perform(post("/api/com-spei-thirds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_spei_third)))
                .andExpect(status().isBadRequest());

        List<Com_spei_third> com_spei_thirds = com_spei_thirdRepository.findAll();
        assertThat(com_spei_thirds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKey_speiIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_spei_thirdRepository.findAll().size();
        // set the field null
        com_spei_third.setKey_spei(null);

        // Create the Com_spei_third, which fails.

        restCom_spei_thirdMockMvc.perform(post("/api/com-spei-thirds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_spei_third)))
                .andExpect(status().isBadRequest());

        List<Com_spei_third> com_spei_thirds = com_spei_thirdRepository.findAll();
        assertThat(com_spei_thirds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStampIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_spei_thirdRepository.findAll().size();
        // set the field null
        com_spei_third.setStamp(null);

        // Create the Com_spei_third, which fails.

        restCom_spei_thirdMockMvc.perform(post("/api/com-spei-thirds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_spei_third)))
                .andExpect(status().isBadRequest());

        List<Com_spei_third> com_spei_thirds = com_spei_thirdRepository.findAll();
        assertThat(com_spei_thirds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumber_certificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_spei_thirdRepository.findAll().size();
        // set the field null
        com_spei_third.setNumber_certificate(null);

        // Create the Com_spei_third, which fails.

        restCom_spei_thirdMockMvc.perform(post("/api/com-spei-thirds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_spei_third)))
                .andExpect(status().isBadRequest());

        List<Com_spei_third> com_spei_thirds = com_spei_thirdRepository.findAll();
        assertThat(com_spei_thirds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCdaIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_spei_thirdRepository.findAll().size();
        // set the field null
        com_spei_third.setCda(null);

        // Create the Com_spei_third, which fails.

        restCom_spei_thirdMockMvc.perform(post("/api/com-spei-thirds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_spei_third)))
                .andExpect(status().isBadRequest());

        List<Com_spei_third> com_spei_thirds = com_spei_thirdRepository.findAll();
        assertThat(com_spei_thirds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_spei_thirds() throws Exception {
        // Initialize the database
        com_spei_thirdRepository.saveAndFlush(com_spei_third);

        // Get all the com_spei_thirds
        restCom_spei_thirdMockMvc.perform(get("/api/com-spei-thirds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_spei_third.getId().intValue())))
                .andExpect(jsonPath("$.[*].date_operation").value(hasItem(DEFAULT_DATE_OPERATION.toString())))
                .andExpect(jsonPath("$.[*].hour").value(hasItem(DEFAULT_HOUR_STR)))
                .andExpect(jsonPath("$.[*].key_spei").value(hasItem(DEFAULT_KEY_SPEI)))
                .andExpect(jsonPath("$.[*].stamp").value(hasItem(DEFAULT_STAMP.toString())))
                .andExpect(jsonPath("$.[*].number_certificate").value(hasItem(DEFAULT_NUMBER_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].cda").value(hasItem(DEFAULT_CDA.toString())));
    }

    @Test
    @Transactional
    public void getCom_spei_third() throws Exception {
        // Initialize the database
        com_spei_thirdRepository.saveAndFlush(com_spei_third);

        // Get the com_spei_third
        restCom_spei_thirdMockMvc.perform(get("/api/com-spei-thirds/{id}", com_spei_third.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_spei_third.getId().intValue()))
            .andExpect(jsonPath("$.date_operation").value(DEFAULT_DATE_OPERATION.toString()))
            .andExpect(jsonPath("$.hour").value(DEFAULT_HOUR_STR))
            .andExpect(jsonPath("$.key_spei").value(DEFAULT_KEY_SPEI))
            .andExpect(jsonPath("$.stamp").value(DEFAULT_STAMP.toString()))
            .andExpect(jsonPath("$.number_certificate").value(DEFAULT_NUMBER_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.cda").value(DEFAULT_CDA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_spei_third() throws Exception {
        // Get the com_spei_third
        restCom_spei_thirdMockMvc.perform(get("/api/com-spei-thirds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_spei_third() throws Exception {
        // Initialize the database
        com_spei_thirdService.save(com_spei_third);

        int databaseSizeBeforeUpdate = com_spei_thirdRepository.findAll().size();

        // Update the com_spei_third
        Com_spei_third updatedCom_spei_third = new Com_spei_third();
        updatedCom_spei_third.setId(com_spei_third.getId());
        updatedCom_spei_third.setDate_operation(UPDATED_DATE_OPERATION);
        updatedCom_spei_third.setHour(UPDATED_HOUR);
        updatedCom_spei_third.setKey_spei(UPDATED_KEY_SPEI);
        updatedCom_spei_third.setStamp(UPDATED_STAMP);
        updatedCom_spei_third.setNumber_certificate(UPDATED_NUMBER_CERTIFICATE);
        updatedCom_spei_third.setCda(UPDATED_CDA);

        restCom_spei_thirdMockMvc.perform(put("/api/com-spei-thirds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_spei_third)))
                .andExpect(status().isOk());

        // Validate the Com_spei_third in the database
        List<Com_spei_third> com_spei_thirds = com_spei_thirdRepository.findAll();
        assertThat(com_spei_thirds).hasSize(databaseSizeBeforeUpdate);
        Com_spei_third testCom_spei_third = com_spei_thirds.get(com_spei_thirds.size() - 1);
        assertThat(testCom_spei_third.getDate_operation()).isEqualTo(UPDATED_DATE_OPERATION);
        assertThat(testCom_spei_third.getHour()).isEqualTo(UPDATED_HOUR);
        assertThat(testCom_spei_third.getKey_spei()).isEqualTo(UPDATED_KEY_SPEI);
        assertThat(testCom_spei_third.getStamp()).isEqualTo(UPDATED_STAMP);
        assertThat(testCom_spei_third.getNumber_certificate()).isEqualTo(UPDATED_NUMBER_CERTIFICATE);
        assertThat(testCom_spei_third.getCda()).isEqualTo(UPDATED_CDA);
    }

    @Test
    @Transactional
    public void deleteCom_spei_third() throws Exception {
        // Initialize the database
        com_spei_thirdService.save(com_spei_third);

        int databaseSizeBeforeDelete = com_spei_thirdRepository.findAll().size();

        // Get the com_spei_third
        restCom_spei_thirdMockMvc.perform(delete("/api/com-spei-thirds/{id}", com_spei_third.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_spei_third> com_spei_thirds = com_spei_thirdRepository.findAll();
        assertThat(com_spei_thirds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
