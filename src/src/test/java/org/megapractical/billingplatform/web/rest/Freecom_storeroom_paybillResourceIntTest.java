package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_storeroom_paybill;
import org.megapractical.billingplatform.repository.Freecom_storeroom_paybillRepository;
import org.megapractical.billingplatform.service.Freecom_storeroom_paybillService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_storeroom_paybillResource REST controller.
 *
 * @see Freecom_storeroom_paybillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_storeroom_paybillResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_TYPE_OPERATION = "AAAAA";
    private static final String UPDATED_TYPE_OPERATION = "BBBBB";
    private static final String DEFAULT_EMPLOYER_REGISTRATION = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_EMPLOYER_REGISTRATION = "BBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    @Inject
    private Freecom_storeroom_paybillRepository freecom_storeroom_paybillRepository;

    @Inject
    private Freecom_storeroom_paybillService freecom_storeroom_paybillService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_storeroom_paybillMockMvc;

    private Freecom_storeroom_paybill freecom_storeroom_paybill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_storeroom_paybillResource freecom_storeroom_paybillResource = new Freecom_storeroom_paybillResource();
        ReflectionTestUtils.setField(freecom_storeroom_paybillResource, "freecom_storeroom_paybillService", freecom_storeroom_paybillService);
        this.restFreecom_storeroom_paybillMockMvc = MockMvcBuilders.standaloneSetup(freecom_storeroom_paybillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_storeroom_paybill = new Freecom_storeroom_paybill();
        freecom_storeroom_paybill.setVersion(DEFAULT_VERSION);
        freecom_storeroom_paybill.setType_operation(DEFAULT_TYPE_OPERATION);
        freecom_storeroom_paybill.setEmployer_registration(DEFAULT_EMPLOYER_REGISTRATION);
        freecom_storeroom_paybill.setAccount_number(DEFAULT_ACCOUNT_NUMBER);
        freecom_storeroom_paybill.setTotal(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createFreecom_storeroom_paybill() throws Exception {
        int databaseSizeBeforeCreate = freecom_storeroom_paybillRepository.findAll().size();

        // Create the Freecom_storeroom_paybill

        restFreecom_storeroom_paybillMockMvc.perform(post("/api/freecom-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_storeroom_paybill)))
                .andExpect(status().isCreated());

        // Validate the Freecom_storeroom_paybill in the database
        List<Freecom_storeroom_paybill> freecom_storeroom_paybills = freecom_storeroom_paybillRepository.findAll();
        assertThat(freecom_storeroom_paybills).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_storeroom_paybill testFreecom_storeroom_paybill = freecom_storeroom_paybills.get(freecom_storeroom_paybills.size() - 1);
        assertThat(testFreecom_storeroom_paybill.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_storeroom_paybill.getType_operation()).isEqualTo(DEFAULT_TYPE_OPERATION);
        assertThat(testFreecom_storeroom_paybill.getEmployer_registration()).isEqualTo(DEFAULT_EMPLOYER_REGISTRATION);
        assertThat(testFreecom_storeroom_paybill.getAccount_number()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testFreecom_storeroom_paybill.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_storeroom_paybillRepository.findAll().size();
        // set the field null
        freecom_storeroom_paybill.setVersion(null);

        // Create the Freecom_storeroom_paybill, which fails.

        restFreecom_storeroom_paybillMockMvc.perform(post("/api/freecom-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_storeroom_paybill)))
                .andExpect(status().isBadRequest());

        List<Freecom_storeroom_paybill> freecom_storeroom_paybills = freecom_storeroom_paybillRepository.findAll();
        assertThat(freecom_storeroom_paybills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkType_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_storeroom_paybillRepository.findAll().size();
        // set the field null
        freecom_storeroom_paybill.setType_operation(null);

        // Create the Freecom_storeroom_paybill, which fails.

        restFreecom_storeroom_paybillMockMvc.perform(post("/api/freecom-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_storeroom_paybill)))
                .andExpect(status().isBadRequest());

        List<Freecom_storeroom_paybill> freecom_storeroom_paybills = freecom_storeroom_paybillRepository.findAll();
        assertThat(freecom_storeroom_paybills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccount_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_storeroom_paybillRepository.findAll().size();
        // set the field null
        freecom_storeroom_paybill.setAccount_number(null);

        // Create the Freecom_storeroom_paybill, which fails.

        restFreecom_storeroom_paybillMockMvc.perform(post("/api/freecom-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_storeroom_paybill)))
                .andExpect(status().isBadRequest());

        List<Freecom_storeroom_paybill> freecom_storeroom_paybills = freecom_storeroom_paybillRepository.findAll();
        assertThat(freecom_storeroom_paybills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_storeroom_paybillRepository.findAll().size();
        // set the field null
        freecom_storeroom_paybill.setTotal(null);

        // Create the Freecom_storeroom_paybill, which fails.

        restFreecom_storeroom_paybillMockMvc.perform(post("/api/freecom-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_storeroom_paybill)))
                .andExpect(status().isBadRequest());

        List<Freecom_storeroom_paybill> freecom_storeroom_paybills = freecom_storeroom_paybillRepository.findAll();
        assertThat(freecom_storeroom_paybills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_storeroom_paybills() throws Exception {
        // Initialize the database
        freecom_storeroom_paybillRepository.saveAndFlush(freecom_storeroom_paybill);

        // Get all the freecom_storeroom_paybills
        restFreecom_storeroom_paybillMockMvc.perform(get("/api/freecom-storeroom-paybills?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_storeroom_paybill.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].type_operation").value(hasItem(DEFAULT_TYPE_OPERATION.toString())))
                .andExpect(jsonPath("$.[*].employer_registration").value(hasItem(DEFAULT_EMPLOYER_REGISTRATION.toString())))
                .andExpect(jsonPath("$.[*].account_number").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_storeroom_paybill() throws Exception {
        // Initialize the database
        freecom_storeroom_paybillRepository.saveAndFlush(freecom_storeroom_paybill);

        // Get the freecom_storeroom_paybill
        restFreecom_storeroom_paybillMockMvc.perform(get("/api/freecom-storeroom-paybills/{id}", freecom_storeroom_paybill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_storeroom_paybill.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.type_operation").value(DEFAULT_TYPE_OPERATION.toString()))
            .andExpect(jsonPath("$.employer_registration").value(DEFAULT_EMPLOYER_REGISTRATION.toString()))
            .andExpect(jsonPath("$.account_number").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_storeroom_paybill() throws Exception {
        // Get the freecom_storeroom_paybill
        restFreecom_storeroom_paybillMockMvc.perform(get("/api/freecom-storeroom-paybills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_storeroom_paybill() throws Exception {
        // Initialize the database
        freecom_storeroom_paybillService.save(freecom_storeroom_paybill);

        int databaseSizeBeforeUpdate = freecom_storeroom_paybillRepository.findAll().size();

        // Update the freecom_storeroom_paybill
        Freecom_storeroom_paybill updatedFreecom_storeroom_paybill = new Freecom_storeroom_paybill();
        updatedFreecom_storeroom_paybill.setId(freecom_storeroom_paybill.getId());
        updatedFreecom_storeroom_paybill.setVersion(UPDATED_VERSION);
        updatedFreecom_storeroom_paybill.setType_operation(UPDATED_TYPE_OPERATION);
        updatedFreecom_storeroom_paybill.setEmployer_registration(UPDATED_EMPLOYER_REGISTRATION);
        updatedFreecom_storeroom_paybill.setAccount_number(UPDATED_ACCOUNT_NUMBER);
        updatedFreecom_storeroom_paybill.setTotal(UPDATED_TOTAL);

        restFreecom_storeroom_paybillMockMvc.perform(put("/api/freecom-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_storeroom_paybill)))
                .andExpect(status().isOk());

        // Validate the Freecom_storeroom_paybill in the database
        List<Freecom_storeroom_paybill> freecom_storeroom_paybills = freecom_storeroom_paybillRepository.findAll();
        assertThat(freecom_storeroom_paybills).hasSize(databaseSizeBeforeUpdate);
        Freecom_storeroom_paybill testFreecom_storeroom_paybill = freecom_storeroom_paybills.get(freecom_storeroom_paybills.size() - 1);
        assertThat(testFreecom_storeroom_paybill.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_storeroom_paybill.getType_operation()).isEqualTo(UPDATED_TYPE_OPERATION);
        assertThat(testFreecom_storeroom_paybill.getEmployer_registration()).isEqualTo(UPDATED_EMPLOYER_REGISTRATION);
        assertThat(testFreecom_storeroom_paybill.getAccount_number()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testFreecom_storeroom_paybill.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void deleteFreecom_storeroom_paybill() throws Exception {
        // Initialize the database
        freecom_storeroom_paybillService.save(freecom_storeroom_paybill);

        int databaseSizeBeforeDelete = freecom_storeroom_paybillRepository.findAll().size();

        // Get the freecom_storeroom_paybill
        restFreecom_storeroom_paybillMockMvc.perform(delete("/api/freecom-storeroom-paybills/{id}", freecom_storeroom_paybill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_storeroom_paybill> freecom_storeroom_paybills = freecom_storeroom_paybillRepository.findAll();
        assertThat(freecom_storeroom_paybills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
