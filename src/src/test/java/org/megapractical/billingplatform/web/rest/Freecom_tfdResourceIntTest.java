package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_tfd;
import org.megapractical.billingplatform.repository.Freecom_tfdRepository;
import org.megapractical.billingplatform.service.Freecom_tfdService;

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
 * Test class for the Freecom_tfdResource REST controller.
 *
 * @see Freecom_tfdResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_tfdResourceIntTest {

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
    private Freecom_tfdRepository freecom_tfdRepository;

    @Inject
    private Freecom_tfdService freecom_tfdService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_tfdMockMvc;

    private Freecom_tfd freecom_tfd;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_tfdResource freecom_tfdResource = new Freecom_tfdResource();
        ReflectionTestUtils.setField(freecom_tfdResource, "freecom_tfdService", freecom_tfdService);
        this.restFreecom_tfdMockMvc = MockMvcBuilders.standaloneSetup(freecom_tfdResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_tfd = new Freecom_tfd();
        freecom_tfd.setVersion(DEFAULT_VERSION);
        freecom_tfd.setUuid(DEFAULT_UUID);
        freecom_tfd.setStamp_date(DEFAULT_STAMP_DATE);
        freecom_tfd.setStamp_cfd(DEFAULT_STAMP_CFD);
        freecom_tfd.setSat_number_certificate(DEFAULT_SAT_NUMBER_CERTIFICATE);
        freecom_tfd.setStamp_sat(DEFAULT_STAMP_SAT);
    }

    @Test
    @Transactional
    public void createFreecom_tfd() throws Exception {
        int databaseSizeBeforeCreate = freecom_tfdRepository.findAll().size();

        // Create the Freecom_tfd

        restFreecom_tfdMockMvc.perform(post("/api/freecom-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tfd)))
                .andExpect(status().isCreated());

        // Validate the Freecom_tfd in the database
        List<Freecom_tfd> freecom_tfds = freecom_tfdRepository.findAll();
        assertThat(freecom_tfds).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_tfd testFreecom_tfd = freecom_tfds.get(freecom_tfds.size() - 1);
        assertThat(testFreecom_tfd.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_tfd.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testFreecom_tfd.getStamp_date()).isEqualTo(DEFAULT_STAMP_DATE);
        assertThat(testFreecom_tfd.getStamp_cfd()).isEqualTo(DEFAULT_STAMP_CFD);
        assertThat(testFreecom_tfd.getSat_number_certificate()).isEqualTo(DEFAULT_SAT_NUMBER_CERTIFICATE);
        assertThat(testFreecom_tfd.getStamp_sat()).isEqualTo(DEFAULT_STAMP_SAT);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_tfdRepository.findAll().size();
        // set the field null
        freecom_tfd.setVersion(null);

        // Create the Freecom_tfd, which fails.

        restFreecom_tfdMockMvc.perform(post("/api/freecom-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tfd)))
                .andExpect(status().isBadRequest());

        List<Freecom_tfd> freecom_tfds = freecom_tfdRepository.findAll();
        assertThat(freecom_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_tfdRepository.findAll().size();
        // set the field null
        freecom_tfd.setUuid(null);

        // Create the Freecom_tfd, which fails.

        restFreecom_tfdMockMvc.perform(post("/api/freecom-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tfd)))
                .andExpect(status().isBadRequest());

        List<Freecom_tfd> freecom_tfds = freecom_tfdRepository.findAll();
        assertThat(freecom_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStamp_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_tfdRepository.findAll().size();
        // set the field null
        freecom_tfd.setStamp_date(null);

        // Create the Freecom_tfd, which fails.

        restFreecom_tfdMockMvc.perform(post("/api/freecom-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tfd)))
                .andExpect(status().isBadRequest());

        List<Freecom_tfd> freecom_tfds = freecom_tfdRepository.findAll();
        assertThat(freecom_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStamp_cfdIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_tfdRepository.findAll().size();
        // set the field null
        freecom_tfd.setStamp_cfd(null);

        // Create the Freecom_tfd, which fails.

        restFreecom_tfdMockMvc.perform(post("/api/freecom-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tfd)))
                .andExpect(status().isBadRequest());

        List<Freecom_tfd> freecom_tfds = freecom_tfdRepository.findAll();
        assertThat(freecom_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSat_number_certificateIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_tfdRepository.findAll().size();
        // set the field null
        freecom_tfd.setSat_number_certificate(null);

        // Create the Freecom_tfd, which fails.

        restFreecom_tfdMockMvc.perform(post("/api/freecom-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tfd)))
                .andExpect(status().isBadRequest());

        List<Freecom_tfd> freecom_tfds = freecom_tfdRepository.findAll();
        assertThat(freecom_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStamp_satIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_tfdRepository.findAll().size();
        // set the field null
        freecom_tfd.setStamp_sat(null);

        // Create the Freecom_tfd, which fails.

        restFreecom_tfdMockMvc.perform(post("/api/freecom-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tfd)))
                .andExpect(status().isBadRequest());

        List<Freecom_tfd> freecom_tfds = freecom_tfdRepository.findAll();
        assertThat(freecom_tfds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_tfds() throws Exception {
        // Initialize the database
        freecom_tfdRepository.saveAndFlush(freecom_tfd);

        // Get all the freecom_tfds
        restFreecom_tfdMockMvc.perform(get("/api/freecom-tfds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_tfd.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
                .andExpect(jsonPath("$.[*].stamp_date").value(hasItem(DEFAULT_STAMP_DATE_STR)))
                .andExpect(jsonPath("$.[*].stamp_cfd").value(hasItem(DEFAULT_STAMP_CFD.toString())))
                .andExpect(jsonPath("$.[*].sat_number_certificate").value(hasItem(DEFAULT_SAT_NUMBER_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].stamp_sat").value(hasItem(DEFAULT_STAMP_SAT.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_tfd() throws Exception {
        // Initialize the database
        freecom_tfdRepository.saveAndFlush(freecom_tfd);

        // Get the freecom_tfd
        restFreecom_tfdMockMvc.perform(get("/api/freecom-tfds/{id}", freecom_tfd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_tfd.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.stamp_date").value(DEFAULT_STAMP_DATE_STR))
            .andExpect(jsonPath("$.stamp_cfd").value(DEFAULT_STAMP_CFD.toString()))
            .andExpect(jsonPath("$.sat_number_certificate").value(DEFAULT_SAT_NUMBER_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.stamp_sat").value(DEFAULT_STAMP_SAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_tfd() throws Exception {
        // Get the freecom_tfd
        restFreecom_tfdMockMvc.perform(get("/api/freecom-tfds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_tfd() throws Exception {
        // Initialize the database
        freecom_tfdService.save(freecom_tfd);

        int databaseSizeBeforeUpdate = freecom_tfdRepository.findAll().size();

        // Update the freecom_tfd
        Freecom_tfd updatedFreecom_tfd = new Freecom_tfd();
        updatedFreecom_tfd.setId(freecom_tfd.getId());
        updatedFreecom_tfd.setVersion(UPDATED_VERSION);
        updatedFreecom_tfd.setUuid(UPDATED_UUID);
        updatedFreecom_tfd.setStamp_date(UPDATED_STAMP_DATE);
        updatedFreecom_tfd.setStamp_cfd(UPDATED_STAMP_CFD);
        updatedFreecom_tfd.setSat_number_certificate(UPDATED_SAT_NUMBER_CERTIFICATE);
        updatedFreecom_tfd.setStamp_sat(UPDATED_STAMP_SAT);

        restFreecom_tfdMockMvc.perform(put("/api/freecom-tfds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_tfd)))
                .andExpect(status().isOk());

        // Validate the Freecom_tfd in the database
        List<Freecom_tfd> freecom_tfds = freecom_tfdRepository.findAll();
        assertThat(freecom_tfds).hasSize(databaseSizeBeforeUpdate);
        Freecom_tfd testFreecom_tfd = freecom_tfds.get(freecom_tfds.size() - 1);
        assertThat(testFreecom_tfd.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_tfd.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testFreecom_tfd.getStamp_date()).isEqualTo(UPDATED_STAMP_DATE);
        assertThat(testFreecom_tfd.getStamp_cfd()).isEqualTo(UPDATED_STAMP_CFD);
        assertThat(testFreecom_tfd.getSat_number_certificate()).isEqualTo(UPDATED_SAT_NUMBER_CERTIFICATE);
        assertThat(testFreecom_tfd.getStamp_sat()).isEqualTo(UPDATED_STAMP_SAT);
    }

    @Test
    @Transactional
    public void deleteFreecom_tfd() throws Exception {
        // Initialize the database
        freecom_tfdService.save(freecom_tfd);

        int databaseSizeBeforeDelete = freecom_tfdRepository.findAll().size();

        // Get the freecom_tfd
        restFreecom_tfdMockMvc.perform(delete("/api/freecom-tfds/{id}", freecom_tfd.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_tfd> freecom_tfds = freecom_tfdRepository.findAll();
        assertThat(freecom_tfds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
