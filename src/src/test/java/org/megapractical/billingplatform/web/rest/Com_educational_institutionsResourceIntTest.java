package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_educational_institutions;
import org.megapractical.billingplatform.repository.Com_educational_institutionsRepository;
import org.megapractical.billingplatform.service.Com_educational_institutionsService;

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
 * Test class for the Com_educational_institutionsResource REST controller.
 *
 * @see Com_educational_institutionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_educational_institutionsResourceIntTest {

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
    private Com_educational_institutionsRepository com_educational_institutionsRepository;

    @Inject
    private Com_educational_institutionsService com_educational_institutionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_educational_institutionsMockMvc;

    private Com_educational_institutions com_educational_institutions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_educational_institutionsResource com_educational_institutionsResource = new Com_educational_institutionsResource();
        ReflectionTestUtils.setField(com_educational_institutionsResource, "com_educational_institutionsService", com_educational_institutionsService);
        this.restCom_educational_institutionsMockMvc = MockMvcBuilders.standaloneSetup(com_educational_institutionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_educational_institutions = new Com_educational_institutions();
        com_educational_institutions.setVersion(DEFAULT_VERSION);
        com_educational_institutions.setName_student(DEFAULT_NAME_STUDENT);
        com_educational_institutions.setCurp(DEFAULT_CURP);
        com_educational_institutions.setAutrvoe(DEFAULT_AUTRVOE);
        com_educational_institutions.setRfcpayment(DEFAULT_RFCPAYMENT);
    }

    @Test
    @Transactional
    public void createCom_educational_institutions() throws Exception {
        int databaseSizeBeforeCreate = com_educational_institutionsRepository.findAll().size();

        // Create the Com_educational_institutions

        restCom_educational_institutionsMockMvc.perform(post("/api/com-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_educational_institutions)))
                .andExpect(status().isCreated());

        // Validate the Com_educational_institutions in the database
        List<Com_educational_institutions> com_educational_institutions = com_educational_institutionsRepository.findAll();
        assertThat(com_educational_institutions).hasSize(databaseSizeBeforeCreate + 1);
        Com_educational_institutions testCom_educational_institutions = com_educational_institutions.get(com_educational_institutions.size() - 1);
        assertThat(testCom_educational_institutions.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_educational_institutions.getName_student()).isEqualTo(DEFAULT_NAME_STUDENT);
        assertThat(testCom_educational_institutions.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testCom_educational_institutions.getAutrvoe()).isEqualTo(DEFAULT_AUTRVOE);
        assertThat(testCom_educational_institutions.getRfcpayment()).isEqualTo(DEFAULT_RFCPAYMENT);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_educational_institutionsRepository.findAll().size();
        // set the field null
        com_educational_institutions.setVersion(null);

        // Create the Com_educational_institutions, which fails.

        restCom_educational_institutionsMockMvc.perform(post("/api/com-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_educational_institutions)))
                .andExpect(status().isBadRequest());

        List<Com_educational_institutions> com_educational_institutions = com_educational_institutionsRepository.findAll();
        assertThat(com_educational_institutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkName_studentIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_educational_institutionsRepository.findAll().size();
        // set the field null
        com_educational_institutions.setName_student(null);

        // Create the Com_educational_institutions, which fails.

        restCom_educational_institutionsMockMvc.perform(post("/api/com-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_educational_institutions)))
                .andExpect(status().isBadRequest());

        List<Com_educational_institutions> com_educational_institutions = com_educational_institutionsRepository.findAll();
        assertThat(com_educational_institutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurpIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_educational_institutionsRepository.findAll().size();
        // set the field null
        com_educational_institutions.setCurp(null);

        // Create the Com_educational_institutions, which fails.

        restCom_educational_institutionsMockMvc.perform(post("/api/com-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_educational_institutions)))
                .andExpect(status().isBadRequest());

        List<Com_educational_institutions> com_educational_institutions = com_educational_institutionsRepository.findAll();
        assertThat(com_educational_institutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAutrvoeIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_educational_institutionsRepository.findAll().size();
        // set the field null
        com_educational_institutions.setAutrvoe(null);

        // Create the Com_educational_institutions, which fails.

        restCom_educational_institutionsMockMvc.perform(post("/api/com-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_educational_institutions)))
                .andExpect(status().isBadRequest());

        List<Com_educational_institutions> com_educational_institutions = com_educational_institutionsRepository.findAll();
        assertThat(com_educational_institutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_educational_institutions() throws Exception {
        // Initialize the database
        com_educational_institutionsRepository.saveAndFlush(com_educational_institutions);

        // Get all the com_educational_institutions
        restCom_educational_institutionsMockMvc.perform(get("/api/com-educational-institutions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_educational_institutions.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].name_student").value(hasItem(DEFAULT_NAME_STUDENT.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
                .andExpect(jsonPath("$.[*].autrvoe").value(hasItem(DEFAULT_AUTRVOE.toString())))
                .andExpect(jsonPath("$.[*].rfcpayment").value(hasItem(DEFAULT_RFCPAYMENT.toString())));
    }

    @Test
    @Transactional
    public void getCom_educational_institutions() throws Exception {
        // Initialize the database
        com_educational_institutionsRepository.saveAndFlush(com_educational_institutions);

        // Get the com_educational_institutions
        restCom_educational_institutionsMockMvc.perform(get("/api/com-educational-institutions/{id}", com_educational_institutions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_educational_institutions.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.name_student").value(DEFAULT_NAME_STUDENT.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.autrvoe").value(DEFAULT_AUTRVOE.toString()))
            .andExpect(jsonPath("$.rfcpayment").value(DEFAULT_RFCPAYMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_educational_institutions() throws Exception {
        // Get the com_educational_institutions
        restCom_educational_institutionsMockMvc.perform(get("/api/com-educational-institutions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_educational_institutions() throws Exception {
        // Initialize the database
        com_educational_institutionsService.save(com_educational_institutions);

        int databaseSizeBeforeUpdate = com_educational_institutionsRepository.findAll().size();

        // Update the com_educational_institutions
        Com_educational_institutions updatedCom_educational_institutions = new Com_educational_institutions();
        updatedCom_educational_institutions.setId(com_educational_institutions.getId());
        updatedCom_educational_institutions.setVersion(UPDATED_VERSION);
        updatedCom_educational_institutions.setName_student(UPDATED_NAME_STUDENT);
        updatedCom_educational_institutions.setCurp(UPDATED_CURP);
        updatedCom_educational_institutions.setAutrvoe(UPDATED_AUTRVOE);
        updatedCom_educational_institutions.setRfcpayment(UPDATED_RFCPAYMENT);

        restCom_educational_institutionsMockMvc.perform(put("/api/com-educational-institutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_educational_institutions)))
                .andExpect(status().isOk());

        // Validate the Com_educational_institutions in the database
        List<Com_educational_institutions> com_educational_institutions = com_educational_institutionsRepository.findAll();
        assertThat(com_educational_institutions).hasSize(databaseSizeBeforeUpdate);
        Com_educational_institutions testCom_educational_institutions = com_educational_institutions.get(com_educational_institutions.size() - 1);
        assertThat(testCom_educational_institutions.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_educational_institutions.getName_student()).isEqualTo(UPDATED_NAME_STUDENT);
        assertThat(testCom_educational_institutions.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testCom_educational_institutions.getAutrvoe()).isEqualTo(UPDATED_AUTRVOE);
        assertThat(testCom_educational_institutions.getRfcpayment()).isEqualTo(UPDATED_RFCPAYMENT);
    }

    @Test
    @Transactional
    public void deleteCom_educational_institutions() throws Exception {
        // Initialize the database
        com_educational_institutionsService.save(com_educational_institutions);

        int databaseSizeBeforeDelete = com_educational_institutionsRepository.findAll().size();

        // Get the com_educational_institutions
        restCom_educational_institutionsMockMvc.perform(delete("/api/com-educational-institutions/{id}", com_educational_institutions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_educational_institutions> com_educational_institutions = com_educational_institutionsRepository.findAll();
        assertThat(com_educational_institutions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
