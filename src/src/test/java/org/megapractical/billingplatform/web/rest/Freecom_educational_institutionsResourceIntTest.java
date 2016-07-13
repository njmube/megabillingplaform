package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_educational_institutions;
import org.megapractical.billingplatform.repository.Freecom_educational_institutionsRepository;
import org.megapractical.billingplatform.service.Freecom_educational_institutionsService;

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
 * Test class for the Freecom_educational_institutionsResource REST controller.
 *
 * @see Freecom_educational_institutionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_educational_institutionsResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_NAME_STUDENT = "AAAAA";
    private static final String UPDATED_NAME_STUDENT = "BBBBB";
    private static final String DEFAULT_CURP = "AAAAA";
    private static final String UPDATED_CURP = "BBBBB";
    private static final String DEFAULT_AUTRVOE = "AAAAA";
    private static final String UPDATED_AUTRVOE = "BBBBB";
    private static final String DEFAULT_RFCPAYMENT = "AAAAA";
    private static final String UPDATED_RFCPAYMENT = "BBBBB";

    @Inject
    private Freecom_educational_institutionsRepository freecom_educational_institutionsRepository;

    @Inject
    private Freecom_educational_institutionsService freecom_educational_institutionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_educational_institutionsMockMvc;

    private Freecom_educational_institutions freecom_educational_institutions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_educational_institutionsResource freecom_educational_institutionsResource = new Freecom_educational_institutionsResource();
        ReflectionTestUtils.setField(freecom_educational_institutionsResource, "freecom_educational_institutionsService", freecom_educational_institutionsService);
        this.restFreecom_educational_institutionsMockMvc = MockMvcBuilders.standaloneSetup(freecom_educational_institutionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_educational_institutions = new Freecom_educational_institutions();
        freecom_educational_institutions.setVersion(DEFAULT_VERSION);
        freecom_educational_institutions.setName_student(DEFAULT_NAME_STUDENT);
        freecom_educational_institutions.setCurp(DEFAULT_CURP);
        freecom_educational_institutions.setAutrvoe(DEFAULT_AUTRVOE);
        freecom_educational_institutions.setRfcpayment(DEFAULT_RFCPAYMENT);
    }

    @Test
    @Transactional
    public void createFreecom_educational_institutions() throws Exception {
        int databaseSizeBeforeCreate = freecom_educational_institutionsRepository.findAll().size();

        // Create the Freecom_educational_institutions

        restFreecom_educational_institutionsMockMvc.perform(post("/api/freecom-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_educational_institutions)))
                .andExpect(status().isCreated());

        // Validate the Freecom_educational_institutions in the database
        List<Freecom_educational_institutions> freecom_educational_institutions = freecom_educational_institutionsRepository.findAll();
        assertThat(freecom_educational_institutions).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_educational_institutions testFreecom_educational_institutions = freecom_educational_institutions.get(freecom_educational_institutions.size() - 1);
        assertThat(testFreecom_educational_institutions.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_educational_institutions.getName_student()).isEqualTo(DEFAULT_NAME_STUDENT);
        assertThat(testFreecom_educational_institutions.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testFreecom_educational_institutions.getAutrvoe()).isEqualTo(DEFAULT_AUTRVOE);
        assertThat(testFreecom_educational_institutions.getRfcpayment()).isEqualTo(DEFAULT_RFCPAYMENT);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_educational_institutionsRepository.findAll().size();
        // set the field null
        freecom_educational_institutions.setVersion(null);

        // Create the Freecom_educational_institutions, which fails.

        restFreecom_educational_institutionsMockMvc.perform(post("/api/freecom-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_educational_institutions)))
                .andExpect(status().isBadRequest());

        List<Freecom_educational_institutions> freecom_educational_institutions = freecom_educational_institutionsRepository.findAll();
        assertThat(freecom_educational_institutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkName_studentIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_educational_institutionsRepository.findAll().size();
        // set the field null
        freecom_educational_institutions.setName_student(null);

        // Create the Freecom_educational_institutions, which fails.

        restFreecom_educational_institutionsMockMvc.perform(post("/api/freecom-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_educational_institutions)))
                .andExpect(status().isBadRequest());

        List<Freecom_educational_institutions> freecom_educational_institutions = freecom_educational_institutionsRepository.findAll();
        assertThat(freecom_educational_institutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurpIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_educational_institutionsRepository.findAll().size();
        // set the field null
        freecom_educational_institutions.setCurp(null);

        // Create the Freecom_educational_institutions, which fails.

        restFreecom_educational_institutionsMockMvc.perform(post("/api/freecom-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_educational_institutions)))
                .andExpect(status().isBadRequest());

        List<Freecom_educational_institutions> freecom_educational_institutions = freecom_educational_institutionsRepository.findAll();
        assertThat(freecom_educational_institutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutrvoeIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_educational_institutionsRepository.findAll().size();
        // set the field null
        freecom_educational_institutions.setAutrvoe(null);

        // Create the Freecom_educational_institutions, which fails.

        restFreecom_educational_institutionsMockMvc.perform(post("/api/freecom-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_educational_institutions)))
                .andExpect(status().isBadRequest());

        List<Freecom_educational_institutions> freecom_educational_institutions = freecom_educational_institutionsRepository.findAll();
        assertThat(freecom_educational_institutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_educational_institutions() throws Exception {
        // Initialize the database
        freecom_educational_institutionsRepository.saveAndFlush(freecom_educational_institutions);

        // Get all the freecom_educational_institutions
        restFreecom_educational_institutionsMockMvc.perform(get("/api/freecom-educational-institutions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_educational_institutions.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].name_student").value(hasItem(DEFAULT_NAME_STUDENT.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
                .andExpect(jsonPath("$.[*].autrvoe").value(hasItem(DEFAULT_AUTRVOE.toString())))
                .andExpect(jsonPath("$.[*].rfcpayment").value(hasItem(DEFAULT_RFCPAYMENT.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_educational_institutions() throws Exception {
        // Initialize the database
        freecom_educational_institutionsRepository.saveAndFlush(freecom_educational_institutions);

        // Get the freecom_educational_institutions
        restFreecom_educational_institutionsMockMvc.perform(get("/api/freecom-educational-institutions/{id}", freecom_educational_institutions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_educational_institutions.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.name_student").value(DEFAULT_NAME_STUDENT.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.autrvoe").value(DEFAULT_AUTRVOE.toString()))
            .andExpect(jsonPath("$.rfcpayment").value(DEFAULT_RFCPAYMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_educational_institutions() throws Exception {
        // Get the freecom_educational_institutions
        restFreecom_educational_institutionsMockMvc.perform(get("/api/freecom-educational-institutions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_educational_institutions() throws Exception {
        // Initialize the database
        freecom_educational_institutionsService.save(freecom_educational_institutions);

        int databaseSizeBeforeUpdate = freecom_educational_institutionsRepository.findAll().size();

        // Update the freecom_educational_institutions
        Freecom_educational_institutions updatedFreecom_educational_institutions = new Freecom_educational_institutions();
        updatedFreecom_educational_institutions.setId(freecom_educational_institutions.getId());
        updatedFreecom_educational_institutions.setVersion(UPDATED_VERSION);
        updatedFreecom_educational_institutions.setName_student(UPDATED_NAME_STUDENT);
        updatedFreecom_educational_institutions.setCurp(UPDATED_CURP);
        updatedFreecom_educational_institutions.setAutrvoe(UPDATED_AUTRVOE);
        updatedFreecom_educational_institutions.setRfcpayment(UPDATED_RFCPAYMENT);

        restFreecom_educational_institutionsMockMvc.perform(put("/api/freecom-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_educational_institutions)))
                .andExpect(status().isOk());

        // Validate the Freecom_educational_institutions in the database
        List<Freecom_educational_institutions> freecom_educational_institutions = freecom_educational_institutionsRepository.findAll();
        assertThat(freecom_educational_institutions).hasSize(databaseSizeBeforeUpdate);
        Freecom_educational_institutions testFreecom_educational_institutions = freecom_educational_institutions.get(freecom_educational_institutions.size() - 1);
        assertThat(testFreecom_educational_institutions.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_educational_institutions.getName_student()).isEqualTo(UPDATED_NAME_STUDENT);
        assertThat(testFreecom_educational_institutions.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testFreecom_educational_institutions.getAutrvoe()).isEqualTo(UPDATED_AUTRVOE);
        assertThat(testFreecom_educational_institutions.getRfcpayment()).isEqualTo(UPDATED_RFCPAYMENT);
    }

    @Test
    @Transactional
    public void deleteFreecom_educational_institutions() throws Exception {
        // Initialize the database
        freecom_educational_institutionsService.save(freecom_educational_institutions);

        int databaseSizeBeforeDelete = freecom_educational_institutionsRepository.findAll().size();

        // Get the freecom_educational_institutions
        restFreecom_educational_institutionsMockMvc.perform(delete("/api/freecom-educational-institutions/{id}", freecom_educational_institutions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_educational_institutions> freecom_educational_institutions = freecom_educational_institutionsRepository.findAll();
        assertThat(freecom_educational_institutions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
