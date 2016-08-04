package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_destruction_certificate;
import org.megapractical.billingplatform.repository.Freecom_destruction_certificateRepository;
import org.megapractical.billingplatform.service.Freecom_destruction_certificateService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_destruction_certificateResource REST controller.
 *
 * @see Freecom_destruction_certificateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_destruction_certificateResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_NUMFOLDESVEH = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMFOLDESVEH = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_BRAND = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_CLASS_DC = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CLASS_DC = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final String DEFAULT_MODEL = "AAAAA";
    private static final String UPDATED_MODEL = "BBBBB";
    private static final String DEFAULT_NIV = "AAAAA";
    private static final String UPDATED_NIV = "BBBBB";
    private static final String DEFAULT_NO_SERIE = "AAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NO_SERIE = "BBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NUMBER_PLATES = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_PLATES = "BBBBBBBBBB";
    private static final String DEFAULT_NUMBER_ENGINE = "AAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMBER_ENGINE = "BBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NUMFOLTARJCIR = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMFOLTARJCIR = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private Freecom_destruction_certificateRepository freecom_destruction_certificateRepository;

    @Inject
    private Freecom_destruction_certificateService freecom_destruction_certificateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_destruction_certificateMockMvc;

    private Freecom_destruction_certificate freecom_destruction_certificate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_destruction_certificateResource freecom_destruction_certificateResource = new Freecom_destruction_certificateResource();
        ReflectionTestUtils.setField(freecom_destruction_certificateResource, "freecom_destruction_certificateService", freecom_destruction_certificateService);
        this.restFreecom_destruction_certificateMockMvc = MockMvcBuilders.standaloneSetup(freecom_destruction_certificateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_destruction_certificate = new Freecom_destruction_certificate();
        freecom_destruction_certificate.setVersion(DEFAULT_VERSION);
        freecom_destruction_certificate.setNumfoldesveh(DEFAULT_NUMFOLDESVEH);
        freecom_destruction_certificate.setBrand(DEFAULT_BRAND);
        freecom_destruction_certificate.setClass_dc(DEFAULT_CLASS_DC);
        freecom_destruction_certificate.setYear(DEFAULT_YEAR);
        freecom_destruction_certificate.setModel(DEFAULT_MODEL);
        freecom_destruction_certificate.setNiv(DEFAULT_NIV);
        freecom_destruction_certificate.setNo_serie(DEFAULT_NO_SERIE);
        freecom_destruction_certificate.setNumber_plates(DEFAULT_NUMBER_PLATES);
        freecom_destruction_certificate.setNumber_engine(DEFAULT_NUMBER_ENGINE);
        freecom_destruction_certificate.setNumfoltarjcir(DEFAULT_NUMFOLTARJCIR);
    }

    @Test
    @Transactional
    public void createFreecom_destruction_certificate() throws Exception {
        int databaseSizeBeforeCreate = freecom_destruction_certificateRepository.findAll().size();

        // Create the Freecom_destruction_certificate

        restFreecom_destruction_certificateMockMvc.perform(post("/api/freecom-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_destruction_certificate)))
                .andExpect(status().isCreated());

        // Validate the Freecom_destruction_certificate in the database
        List<Freecom_destruction_certificate> freecom_destruction_certificates = freecom_destruction_certificateRepository.findAll();
        assertThat(freecom_destruction_certificates).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_destruction_certificate testFreecom_destruction_certificate = freecom_destruction_certificates.get(freecom_destruction_certificates.size() - 1);
        assertThat(testFreecom_destruction_certificate.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_destruction_certificate.getNumfoldesveh()).isEqualTo(DEFAULT_NUMFOLDESVEH);
        assertThat(testFreecom_destruction_certificate.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testFreecom_destruction_certificate.getClass_dc()).isEqualTo(DEFAULT_CLASS_DC);
        assertThat(testFreecom_destruction_certificate.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testFreecom_destruction_certificate.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testFreecom_destruction_certificate.getNiv()).isEqualTo(DEFAULT_NIV);
        assertThat(testFreecom_destruction_certificate.getNo_serie()).isEqualTo(DEFAULT_NO_SERIE);
        assertThat(testFreecom_destruction_certificate.getNumber_plates()).isEqualTo(DEFAULT_NUMBER_PLATES);
        assertThat(testFreecom_destruction_certificate.getNumber_engine()).isEqualTo(DEFAULT_NUMBER_ENGINE);
        assertThat(testFreecom_destruction_certificate.getNumfoltarjcir()).isEqualTo(DEFAULT_NUMFOLTARJCIR);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_destruction_certificateRepository.findAll().size();
        // set the field null
        freecom_destruction_certificate.setVersion(null);

        // Create the Freecom_destruction_certificate, which fails.

        restFreecom_destruction_certificateMockMvc.perform(post("/api/freecom-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Freecom_destruction_certificate> freecom_destruction_certificates = freecom_destruction_certificateRepository.findAll();
        assertThat(freecom_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumfoldesvehIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_destruction_certificateRepository.findAll().size();
        // set the field null
        freecom_destruction_certificate.setNumfoldesveh(null);

        // Create the Freecom_destruction_certificate, which fails.

        restFreecom_destruction_certificateMockMvc.perform(post("/api/freecom-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Freecom_destruction_certificate> freecom_destruction_certificates = freecom_destruction_certificateRepository.findAll();
        assertThat(freecom_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_destruction_certificateRepository.findAll().size();
        // set the field null
        freecom_destruction_certificate.setBrand(null);

        // Create the Freecom_destruction_certificate, which fails.

        restFreecom_destruction_certificateMockMvc.perform(post("/api/freecom-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Freecom_destruction_certificate> freecom_destruction_certificates = freecom_destruction_certificateRepository.findAll();
        assertThat(freecom_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClass_dcIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_destruction_certificateRepository.findAll().size();
        // set the field null
        freecom_destruction_certificate.setClass_dc(null);

        // Create the Freecom_destruction_certificate, which fails.

        restFreecom_destruction_certificateMockMvc.perform(post("/api/freecom-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Freecom_destruction_certificate> freecom_destruction_certificates = freecom_destruction_certificateRepository.findAll();
        assertThat(freecom_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_destruction_certificateRepository.findAll().size();
        // set the field null
        freecom_destruction_certificate.setYear(null);

        // Create the Freecom_destruction_certificate, which fails.

        restFreecom_destruction_certificateMockMvc.perform(post("/api/freecom-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Freecom_destruction_certificate> freecom_destruction_certificates = freecom_destruction_certificateRepository.findAll();
        assertThat(freecom_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumber_platesIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_destruction_certificateRepository.findAll().size();
        // set the field null
        freecom_destruction_certificate.setNumber_plates(null);

        // Create the Freecom_destruction_certificate, which fails.

        restFreecom_destruction_certificateMockMvc.perform(post("/api/freecom-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Freecom_destruction_certificate> freecom_destruction_certificates = freecom_destruction_certificateRepository.findAll();
        assertThat(freecom_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumfoltarjcirIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_destruction_certificateRepository.findAll().size();
        // set the field null
        freecom_destruction_certificate.setNumfoltarjcir(null);

        // Create the Freecom_destruction_certificate, which fails.

        restFreecom_destruction_certificateMockMvc.perform(post("/api/freecom-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Freecom_destruction_certificate> freecom_destruction_certificates = freecom_destruction_certificateRepository.findAll();
        assertThat(freecom_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_destruction_certificates() throws Exception {
        // Initialize the database
        freecom_destruction_certificateRepository.saveAndFlush(freecom_destruction_certificate);

        // Get all the freecom_destruction_certificates
        restFreecom_destruction_certificateMockMvc.perform(get("/api/freecom-destruction-certificates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_destruction_certificate.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].numfoldesveh").value(hasItem(DEFAULT_NUMFOLDESVEH.toString())))
                .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
                .andExpect(jsonPath("$.[*].class_dc").value(hasItem(DEFAULT_CLASS_DC.toString())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
                .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
                .andExpect(jsonPath("$.[*].niv").value(hasItem(DEFAULT_NIV.toString())))
                .andExpect(jsonPath("$.[*].no_serie").value(hasItem(DEFAULT_NO_SERIE.toString())))
                .andExpect(jsonPath("$.[*].number_plates").value(hasItem(DEFAULT_NUMBER_PLATES.toString())))
                .andExpect(jsonPath("$.[*].number_engine").value(hasItem(DEFAULT_NUMBER_ENGINE.toString())))
                .andExpect(jsonPath("$.[*].numfoltarjcir").value(hasItem(DEFAULT_NUMFOLTARJCIR.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_destruction_certificate() throws Exception {
        // Initialize the database
        freecom_destruction_certificateRepository.saveAndFlush(freecom_destruction_certificate);

        // Get the freecom_destruction_certificate
        restFreecom_destruction_certificateMockMvc.perform(get("/api/freecom-destruction-certificates/{id}", freecom_destruction_certificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_destruction_certificate.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.numfoldesveh").value(DEFAULT_NUMFOLDESVEH.toString()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.class_dc").value(DEFAULT_CLASS_DC.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.niv").value(DEFAULT_NIV.toString()))
            .andExpect(jsonPath("$.no_serie").value(DEFAULT_NO_SERIE.toString()))
            .andExpect(jsonPath("$.number_plates").value(DEFAULT_NUMBER_PLATES.toString()))
            .andExpect(jsonPath("$.number_engine").value(DEFAULT_NUMBER_ENGINE.toString()))
            .andExpect(jsonPath("$.numfoltarjcir").value(DEFAULT_NUMFOLTARJCIR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_destruction_certificate() throws Exception {
        // Get the freecom_destruction_certificate
        restFreecom_destruction_certificateMockMvc.perform(get("/api/freecom-destruction-certificates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_destruction_certificate() throws Exception {
        // Initialize the database
        freecom_destruction_certificateService.save(freecom_destruction_certificate);

        int databaseSizeBeforeUpdate = freecom_destruction_certificateRepository.findAll().size();

        // Update the freecom_destruction_certificate
        Freecom_destruction_certificate updatedFreecom_destruction_certificate = new Freecom_destruction_certificate();
        updatedFreecom_destruction_certificate.setId(freecom_destruction_certificate.getId());
        updatedFreecom_destruction_certificate.setVersion(UPDATED_VERSION);
        updatedFreecom_destruction_certificate.setNumfoldesveh(UPDATED_NUMFOLDESVEH);
        updatedFreecom_destruction_certificate.setBrand(UPDATED_BRAND);
        updatedFreecom_destruction_certificate.setClass_dc(UPDATED_CLASS_DC);
        updatedFreecom_destruction_certificate.setYear(UPDATED_YEAR);
        updatedFreecom_destruction_certificate.setModel(UPDATED_MODEL);
        updatedFreecom_destruction_certificate.setNiv(UPDATED_NIV);
        updatedFreecom_destruction_certificate.setNo_serie(UPDATED_NO_SERIE);
        updatedFreecom_destruction_certificate.setNumber_plates(UPDATED_NUMBER_PLATES);
        updatedFreecom_destruction_certificate.setNumber_engine(UPDATED_NUMBER_ENGINE);
        updatedFreecom_destruction_certificate.setNumfoltarjcir(UPDATED_NUMFOLTARJCIR);

        restFreecom_destruction_certificateMockMvc.perform(put("/api/freecom-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_destruction_certificate)))
                .andExpect(status().isOk());

        // Validate the Freecom_destruction_certificate in the database
        List<Freecom_destruction_certificate> freecom_destruction_certificates = freecom_destruction_certificateRepository.findAll();
        assertThat(freecom_destruction_certificates).hasSize(databaseSizeBeforeUpdate);
        Freecom_destruction_certificate testFreecom_destruction_certificate = freecom_destruction_certificates.get(freecom_destruction_certificates.size() - 1);
        assertThat(testFreecom_destruction_certificate.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_destruction_certificate.getNumfoldesveh()).isEqualTo(UPDATED_NUMFOLDESVEH);
        assertThat(testFreecom_destruction_certificate.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testFreecom_destruction_certificate.getClass_dc()).isEqualTo(UPDATED_CLASS_DC);
        assertThat(testFreecom_destruction_certificate.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testFreecom_destruction_certificate.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testFreecom_destruction_certificate.getNiv()).isEqualTo(UPDATED_NIV);
        assertThat(testFreecom_destruction_certificate.getNo_serie()).isEqualTo(UPDATED_NO_SERIE);
        assertThat(testFreecom_destruction_certificate.getNumber_plates()).isEqualTo(UPDATED_NUMBER_PLATES);
        assertThat(testFreecom_destruction_certificate.getNumber_engine()).isEqualTo(UPDATED_NUMBER_ENGINE);
        assertThat(testFreecom_destruction_certificate.getNumfoltarjcir()).isEqualTo(UPDATED_NUMFOLTARJCIR);
    }

    @Test
    @Transactional
    public void deleteFreecom_destruction_certificate() throws Exception {
        // Initialize the database
        freecom_destruction_certificateService.save(freecom_destruction_certificate);

        int databaseSizeBeforeDelete = freecom_destruction_certificateRepository.findAll().size();

        // Get the freecom_destruction_certificate
        restFreecom_destruction_certificateMockMvc.perform(delete("/api/freecom-destruction-certificates/{id}", freecom_destruction_certificate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_destruction_certificate> freecom_destruction_certificates = freecom_destruction_certificateRepository.findAll();
        assertThat(freecom_destruction_certificates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
