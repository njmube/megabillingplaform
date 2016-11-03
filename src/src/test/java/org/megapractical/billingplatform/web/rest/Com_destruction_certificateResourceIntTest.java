package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_destruction_certificate;
import org.megapractical.billingplatform.repository.Com_destruction_certificateRepository;
import org.megapractical.billingplatform.service.Com_destruction_certificateService;

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
 * Test class for the Com_destruction_certificateResource REST controller.
 *
 * @see Com_destruction_certificateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_destruction_certificateResourceIntTest {

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
    private Com_destruction_certificateRepository com_destruction_certificateRepository;

    @Inject
    private Com_destruction_certificateService com_destruction_certificateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_destruction_certificateMockMvc;

    private Com_destruction_certificate com_destruction_certificate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_destruction_certificateResource com_destruction_certificateResource = new Com_destruction_certificateResource();
        ReflectionTestUtils.setField(com_destruction_certificateResource, "com_destruction_certificateService", com_destruction_certificateService);
        this.restCom_destruction_certificateMockMvc = MockMvcBuilders.standaloneSetup(com_destruction_certificateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_destruction_certificate = new Com_destruction_certificate();
        com_destruction_certificate.setVersion(DEFAULT_VERSION);
        com_destruction_certificate.setNumfoldesveh(DEFAULT_NUMFOLDESVEH);
        com_destruction_certificate.setBrand(DEFAULT_BRAND);
        com_destruction_certificate.setClass_dc(DEFAULT_CLASS_DC);
        com_destruction_certificate.setYear(DEFAULT_YEAR);
        com_destruction_certificate.setModel(DEFAULT_MODEL);
        com_destruction_certificate.setNiv(DEFAULT_NIV);
        com_destruction_certificate.setNo_serie(DEFAULT_NO_SERIE);
        com_destruction_certificate.setNumber_plates(DEFAULT_NUMBER_PLATES);
        com_destruction_certificate.setNumber_engine(DEFAULT_NUMBER_ENGINE);
        com_destruction_certificate.setNumfoltarjcir(DEFAULT_NUMFOLTARJCIR);
    }

    @Test
    @Transactional
    public void createCom_destruction_certificate() throws Exception {
        int databaseSizeBeforeCreate = com_destruction_certificateRepository.findAll().size();

        // Create the Com_destruction_certificate

        restCom_destruction_certificateMockMvc.perform(post("/api/com-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_destruction_certificate)))
                .andExpect(status().isCreated());

        // Validate the Com_destruction_certificate in the database
        List<Com_destruction_certificate> com_destruction_certificates = com_destruction_certificateRepository.findAll();
        assertThat(com_destruction_certificates).hasSize(databaseSizeBeforeCreate + 1);
        Com_destruction_certificate testCom_destruction_certificate = com_destruction_certificates.get(com_destruction_certificates.size() - 1);
        assertThat(testCom_destruction_certificate.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_destruction_certificate.getNumfoldesveh()).isEqualTo(DEFAULT_NUMFOLDESVEH);
        assertThat(testCom_destruction_certificate.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testCom_destruction_certificate.getClass_dc()).isEqualTo(DEFAULT_CLASS_DC);
        assertThat(testCom_destruction_certificate.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCom_destruction_certificate.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testCom_destruction_certificate.getNiv()).isEqualTo(DEFAULT_NIV);
        assertThat(testCom_destruction_certificate.getNo_serie()).isEqualTo(DEFAULT_NO_SERIE);
        assertThat(testCom_destruction_certificate.getNumber_plates()).isEqualTo(DEFAULT_NUMBER_PLATES);
        assertThat(testCom_destruction_certificate.getNumber_engine()).isEqualTo(DEFAULT_NUMBER_ENGINE);
        assertThat(testCom_destruction_certificate.getNumfoltarjcir()).isEqualTo(DEFAULT_NUMFOLTARJCIR);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_destruction_certificateRepository.findAll().size();
        // set the field null
        com_destruction_certificate.setVersion(null);

        // Create the Com_destruction_certificate, which fails.

        restCom_destruction_certificateMockMvc.perform(post("/api/com-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Com_destruction_certificate> com_destruction_certificates = com_destruction_certificateRepository.findAll();
        assertThat(com_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumfoldesvehIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_destruction_certificateRepository.findAll().size();
        // set the field null
        com_destruction_certificate.setNumfoldesveh(null);

        // Create the Com_destruction_certificate, which fails.

        restCom_destruction_certificateMockMvc.perform(post("/api/com-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Com_destruction_certificate> com_destruction_certificates = com_destruction_certificateRepository.findAll();
        assertThat(com_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_destruction_certificateRepository.findAll().size();
        // set the field null
        com_destruction_certificate.setBrand(null);

        // Create the Com_destruction_certificate, which fails.

        restCom_destruction_certificateMockMvc.perform(post("/api/com-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Com_destruction_certificate> com_destruction_certificates = com_destruction_certificateRepository.findAll();
        assertThat(com_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClass_dcIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_destruction_certificateRepository.findAll().size();
        // set the field null
        com_destruction_certificate.setClass_dc(null);

        // Create the Com_destruction_certificate, which fails.

        restCom_destruction_certificateMockMvc.perform(post("/api/com-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Com_destruction_certificate> com_destruction_certificates = com_destruction_certificateRepository.findAll();
        assertThat(com_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_destruction_certificateRepository.findAll().size();
        // set the field null
        com_destruction_certificate.setYear(null);

        // Create the Com_destruction_certificate, which fails.

        restCom_destruction_certificateMockMvc.perform(post("/api/com-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Com_destruction_certificate> com_destruction_certificates = com_destruction_certificateRepository.findAll();
        assertThat(com_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumber_platesIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_destruction_certificateRepository.findAll().size();
        // set the field null
        com_destruction_certificate.setNumber_plates(null);

        // Create the Com_destruction_certificate, which fails.

        restCom_destruction_certificateMockMvc.perform(post("/api/com-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Com_destruction_certificate> com_destruction_certificates = com_destruction_certificateRepository.findAll();
        assertThat(com_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumfoltarjcirIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_destruction_certificateRepository.findAll().size();
        // set the field null
        com_destruction_certificate.setNumfoltarjcir(null);

        // Create the Com_destruction_certificate, which fails.

        restCom_destruction_certificateMockMvc.perform(post("/api/com-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_destruction_certificate)))
                .andExpect(status().isBadRequest());

        List<Com_destruction_certificate> com_destruction_certificates = com_destruction_certificateRepository.findAll();
        assertThat(com_destruction_certificates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_destruction_certificates() throws Exception {
        // Initialize the database
        com_destruction_certificateRepository.saveAndFlush(com_destruction_certificate);

        // Get all the com_destruction_certificates
        restCom_destruction_certificateMockMvc.perform(get("/api/com-destruction-certificates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_destruction_certificate.getId().intValue())))
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
    public void getCom_destruction_certificate() throws Exception {
        // Initialize the database
        com_destruction_certificateRepository.saveAndFlush(com_destruction_certificate);

        // Get the com_destruction_certificate
        restCom_destruction_certificateMockMvc.perform(get("/api/com-destruction-certificates/{id}", com_destruction_certificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_destruction_certificate.getId().intValue()))
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
    public void getNonExistingCom_destruction_certificate() throws Exception {
        // Get the com_destruction_certificate
        restCom_destruction_certificateMockMvc.perform(get("/api/com-destruction-certificates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_destruction_certificate() throws Exception {
        // Initialize the database
        com_destruction_certificateService.save(com_destruction_certificate);

        int databaseSizeBeforeUpdate = com_destruction_certificateRepository.findAll().size();

        // Update the com_destruction_certificate
        Com_destruction_certificate updatedCom_destruction_certificate = new Com_destruction_certificate();
        updatedCom_destruction_certificate.setId(com_destruction_certificate.getId());
        updatedCom_destruction_certificate.setVersion(UPDATED_VERSION);
        updatedCom_destruction_certificate.setNumfoldesveh(UPDATED_NUMFOLDESVEH);
        updatedCom_destruction_certificate.setBrand(UPDATED_BRAND);
        updatedCom_destruction_certificate.setClass_dc(UPDATED_CLASS_DC);
        updatedCom_destruction_certificate.setYear(UPDATED_YEAR);
        updatedCom_destruction_certificate.setModel(UPDATED_MODEL);
        updatedCom_destruction_certificate.setNiv(UPDATED_NIV);
        updatedCom_destruction_certificate.setNo_serie(UPDATED_NO_SERIE);
        updatedCom_destruction_certificate.setNumber_plates(UPDATED_NUMBER_PLATES);
        updatedCom_destruction_certificate.setNumber_engine(UPDATED_NUMBER_ENGINE);
        updatedCom_destruction_certificate.setNumfoltarjcir(UPDATED_NUMFOLTARJCIR);

        restCom_destruction_certificateMockMvc.perform(put("/api/com-destruction-certificates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_destruction_certificate)))
                .andExpect(status().isOk());

        // Validate the Com_destruction_certificate in the database
        List<Com_destruction_certificate> com_destruction_certificates = com_destruction_certificateRepository.findAll();
        assertThat(com_destruction_certificates).hasSize(databaseSizeBeforeUpdate);
        Com_destruction_certificate testCom_destruction_certificate = com_destruction_certificates.get(com_destruction_certificates.size() - 1);
        assertThat(testCom_destruction_certificate.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_destruction_certificate.getNumfoldesveh()).isEqualTo(UPDATED_NUMFOLDESVEH);
        assertThat(testCom_destruction_certificate.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testCom_destruction_certificate.getClass_dc()).isEqualTo(UPDATED_CLASS_DC);
        assertThat(testCom_destruction_certificate.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCom_destruction_certificate.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCom_destruction_certificate.getNiv()).isEqualTo(UPDATED_NIV);
        assertThat(testCom_destruction_certificate.getNo_serie()).isEqualTo(UPDATED_NO_SERIE);
        assertThat(testCom_destruction_certificate.getNumber_plates()).isEqualTo(UPDATED_NUMBER_PLATES);
        assertThat(testCom_destruction_certificate.getNumber_engine()).isEqualTo(UPDATED_NUMBER_ENGINE);
        assertThat(testCom_destruction_certificate.getNumfoltarjcir()).isEqualTo(UPDATED_NUMFOLTARJCIR);
    }

    @Test
    @Transactional
    public void deleteCom_destruction_certificate() throws Exception {
        // Initialize the database
        com_destruction_certificateService.save(com_destruction_certificate);

        int databaseSizeBeforeDelete = com_destruction_certificateRepository.findAll().size();

        // Get the com_destruction_certificate
        restCom_destruction_certificateMockMvc.perform(delete("/api/com-destruction-certificates/{id}", com_destruction_certificate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_destruction_certificate> com_destruction_certificates = com_destruction_certificateRepository.findAll();
        assertThat(com_destruction_certificates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
