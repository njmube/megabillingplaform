package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_tfd;
import org.megapractical.billingplatform.repository.Com_tfdRepository;
import org.megapractical.billingplatform.service.Com_tfdService;

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
 * Test class for the Com_tfdResource REST controller.
 *
 * @see Com_tfdResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_tfdResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_UUID = "AAAAA";
    private static final String UPDATED_UUID = "BBBBB";

    private static final ZonedDateTime DEFAULT_STAMP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_STAMP_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_STAMP_DATE_STR = dateTimeFormatter.format(DEFAULT_STAMP_DATE);
    private static final String DEFAULT_STAMP_CFD = "AAAAA";
    private static final String UPDATED_STAMP_CFD = "BBBBB";
    private static final String DEFAULT_SAT_NUMBER_CERTIFICATE = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SAT_NUMBER_CERTIFICATE = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_STAMP_SAT = "AAAAA";
    private static final String UPDATED_STAMP_SAT = "BBBBB";

    @Inject
    private Com_tfdRepository com_tfdRepository;

    @Inject
    private Com_tfdService com_tfdService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_tfdMockMvc;

    private Com_tfd com_tfd;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_tfdResource com_tfdResource = new Com_tfdResource();
        ReflectionTestUtils.setField(com_tfdResource, "com_tfdService", com_tfdService);
        this.restCom_tfdMockMvc = MockMvcBuilders.standaloneSetup(com_tfdResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_tfd = new Com_tfd();
        com_tfd.setVersion(DEFAULT_VERSION);
        com_tfd.setUuid(DEFAULT_UUID);
        com_tfd.setStamp_date(DEFAULT_STAMP_DATE);
        com_tfd.setStamp_cfd(DEFAULT_STAMP_CFD);
        com_tfd.setSat_number_certificate(DEFAULT_SAT_NUMBER_CERTIFICATE);
        com_tfd.setStamp_sat(DEFAULT_STAMP_SAT);
    }

    @Test
    @Transactional
    public void createCom_tfd() throws Exception {
        int databaseSizeBeforeCreate = com_tfdRepository.findAll().size();

        // Create the Com_tfd

        restCom_tfdMockMvc.perform(post("/api/com-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_tfd)))
                .andExpect(status().isCreated());

        // Validate the Com_tfd in the database
        List<Com_tfd> com_tfds = com_tfdRepository.findAll();
        assertThat(com_tfds).hasSize(databaseSizeBeforeCreate + 1);
        Com_tfd testCom_tfd = com_tfds.get(com_tfds.size() - 1);
        assertThat(testCom_tfd.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_tfd.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testCom_tfd.getStamp_date()).isEqualTo(DEFAULT_STAMP_DATE);
        assertThat(testCom_tfd.getStamp_cfd()).isEqualTo(DEFAULT_STAMP_CFD);
        assertThat(testCom_tfd.getSat_number_certificate()).isEqualTo(DEFAULT_SAT_NUMBER_CERTIFICATE);
        assertThat(testCom_tfd.getStamp_sat()).isEqualTo(DEFAULT_STAMP_SAT);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_tfdRepository.findAll().size();
        // set the field null
        com_tfd.setVersion(null);

        // Create the Com_tfd, which fails.

        restCom_tfdMockMvc.perform(post("/api/com-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_tfd)))
                .andExpect(status().isBadRequest());

        List<Com_tfd> com_tfds = com_tfdRepository.findAll();
        assertThat(com_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_tfdRepository.findAll().size();
        // set the field null
        com_tfd.setUuid(null);

        // Create the Com_tfd, which fails.

        restCom_tfdMockMvc.perform(post("/api/com-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_tfd)))
                .andExpect(status().isBadRequest());

        List<Com_tfd> com_tfds = com_tfdRepository.findAll();
        assertThat(com_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStamp_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_tfdRepository.findAll().size();
        // set the field null
        com_tfd.setStamp_date(null);

        // Create the Com_tfd, which fails.

        restCom_tfdMockMvc.perform(post("/api/com-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_tfd)))
                .andExpect(status().isBadRequest());

        List<Com_tfd> com_tfds = com_tfdRepository.findAll();
        assertThat(com_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStamp_cfdIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_tfdRepository.findAll().size();
        // set the field null
        com_tfd.setStamp_cfd(null);

        // Create the Com_tfd, which fails.

        restCom_tfdMockMvc.perform(post("/api/com-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_tfd)))
                .andExpect(status().isBadRequest());

        List<Com_tfd> com_tfds = com_tfdRepository.findAll();
        assertThat(com_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSat_number_certificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_tfdRepository.findAll().size();
        // set the field null
        com_tfd.setSat_number_certificate(null);

        // Create the Com_tfd, which fails.

        restCom_tfdMockMvc.perform(post("/api/com-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_tfd)))
                .andExpect(status().isBadRequest());

        List<Com_tfd> com_tfds = com_tfdRepository.findAll();
        assertThat(com_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStamp_satIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_tfdRepository.findAll().size();
        // set the field null
        com_tfd.setStamp_sat(null);

        // Create the Com_tfd, which fails.

        restCom_tfdMockMvc.perform(post("/api/com-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_tfd)))
                .andExpect(status().isBadRequest());

        List<Com_tfd> com_tfds = com_tfdRepository.findAll();
        assertThat(com_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_tfds() throws Exception {
        // Initialize the database
        com_tfdRepository.saveAndFlush(com_tfd);

        // Get all the com_tfds
        restCom_tfdMockMvc.perform(get("/api/com-tfds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_tfd.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
                .andExpect(jsonPath("$.[*].stamp_date").value(hasItem(DEFAULT_STAMP_DATE_STR)))
                .andExpect(jsonPath("$.[*].stamp_cfd").value(hasItem(DEFAULT_STAMP_CFD.toString())))
                .andExpect(jsonPath("$.[*].sat_number_certificate").value(hasItem(DEFAULT_SAT_NUMBER_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].stamp_sat").value(hasItem(DEFAULT_STAMP_SAT.toString())));
    }

    @Test
    @Transactional
    public void getCom_tfd() throws Exception {
        // Initialize the database
        com_tfdRepository.saveAndFlush(com_tfd);

        // Get the com_tfd
        restCom_tfdMockMvc.perform(get("/api/com-tfds/{id}", com_tfd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_tfd.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.stamp_date").value(DEFAULT_STAMP_DATE_STR))
            .andExpect(jsonPath("$.stamp_cfd").value(DEFAULT_STAMP_CFD.toString()))
            .andExpect(jsonPath("$.sat_number_certificate").value(DEFAULT_SAT_NUMBER_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.stamp_sat").value(DEFAULT_STAMP_SAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_tfd() throws Exception {
        // Get the com_tfd
        restCom_tfdMockMvc.perform(get("/api/com-tfds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_tfd() throws Exception {
        // Initialize the database
        com_tfdService.save(com_tfd);

        int databaseSizeBeforeUpdate = com_tfdRepository.findAll().size();

        // Update the com_tfd
        Com_tfd updatedCom_tfd = new Com_tfd();
        updatedCom_tfd.setId(com_tfd.getId());
        updatedCom_tfd.setVersion(UPDATED_VERSION);
        updatedCom_tfd.setUuid(UPDATED_UUID);
        updatedCom_tfd.setStamp_date(UPDATED_STAMP_DATE);
        updatedCom_tfd.setStamp_cfd(UPDATED_STAMP_CFD);
        updatedCom_tfd.setSat_number_certificate(UPDATED_SAT_NUMBER_CERTIFICATE);
        updatedCom_tfd.setStamp_sat(UPDATED_STAMP_SAT);

        restCom_tfdMockMvc.perform(put("/api/com-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_tfd)))
                .andExpect(status().isOk());

        // Validate the Com_tfd in the database
        List<Com_tfd> com_tfds = com_tfdRepository.findAll();
        assertThat(com_tfds).hasSize(databaseSizeBeforeUpdate);
        Com_tfd testCom_tfd = com_tfds.get(com_tfds.size() - 1);
        assertThat(testCom_tfd.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_tfd.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testCom_tfd.getStamp_date()).isEqualTo(UPDATED_STAMP_DATE);
        assertThat(testCom_tfd.getStamp_cfd()).isEqualTo(UPDATED_STAMP_CFD);
        assertThat(testCom_tfd.getSat_number_certificate()).isEqualTo(UPDATED_SAT_NUMBER_CERTIFICATE);
        assertThat(testCom_tfd.getStamp_sat()).isEqualTo(UPDATED_STAMP_SAT);
    }

    @Test
    @Transactional
    public void deleteCom_tfd() throws Exception {
        // Initialize the database
        com_tfdService.save(com_tfd);

        int databaseSizeBeforeDelete = com_tfdRepository.findAll().size();

        // Get the com_tfd
        restCom_tfdMockMvc.perform(delete("/api/com-tfds/{id}", com_tfd.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_tfd> com_tfds = com_tfdRepository.findAll();
        assertThat(com_tfds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
