package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_storeroom_paybill;
import org.megapractical.billingplatform.repository.Com_storeroom_paybillRepository;
import org.megapractical.billingplatform.service.Com_storeroom_paybillService;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Com_storeroom_paybillResource REST controller.
 *
 * @see Com_storeroom_paybillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_storeroom_paybillResourceIntTest {

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
    private Com_storeroom_paybillRepository com_storeroom_paybillRepository;

    @Inject
    private Com_storeroom_paybillService com_storeroom_paybillService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_storeroom_paybillMockMvc;

    private Com_storeroom_paybill com_storeroom_paybill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_storeroom_paybillResource com_storeroom_paybillResource = new Com_storeroom_paybillResource();
        ReflectionTestUtils.setField(com_storeroom_paybillResource, "com_storeroom_paybillService", com_storeroom_paybillService);
        this.restCom_storeroom_paybillMockMvc = MockMvcBuilders.standaloneSetup(com_storeroom_paybillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_storeroom_paybill = new Com_storeroom_paybill();
        com_storeroom_paybill.setVersion(DEFAULT_VERSION);
        com_storeroom_paybill.setType_operation(DEFAULT_TYPE_OPERATION);
        com_storeroom_paybill.setEmployer_registration(DEFAULT_EMPLOYER_REGISTRATION);
        com_storeroom_paybill.setAccount_number(DEFAULT_ACCOUNT_NUMBER);
        com_storeroom_paybill.setTotal(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createCom_storeroom_paybill() throws Exception {
        int databaseSizeBeforeCreate = com_storeroom_paybillRepository.findAll().size();

        // Create the Com_storeroom_paybill

        restCom_storeroom_paybillMockMvc.perform(post("/api/com-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_storeroom_paybill)))
                .andExpect(status().isCreated());

        // Validate the Com_storeroom_paybill in the database
        List<Com_storeroom_paybill> com_storeroom_paybills = com_storeroom_paybillRepository.findAll();
        assertThat(com_storeroom_paybills).hasSize(databaseSizeBeforeCreate + 1);
        Com_storeroom_paybill testCom_storeroom_paybill = com_storeroom_paybills.get(com_storeroom_paybills.size() - 1);
        assertThat(testCom_storeroom_paybill.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_storeroom_paybill.getType_operation()).isEqualTo(DEFAULT_TYPE_OPERATION);
        assertThat(testCom_storeroom_paybill.getEmployer_registration()).isEqualTo(DEFAULT_EMPLOYER_REGISTRATION);
        assertThat(testCom_storeroom_paybill.getAccount_number()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCom_storeroom_paybill.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_storeroom_paybillRepository.findAll().size();
        // set the field null
        com_storeroom_paybill.setVersion(null);

        // Create the Com_storeroom_paybill, which fails.

        restCom_storeroom_paybillMockMvc.perform(post("/api/com-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_storeroom_paybill)))
                .andExpect(status().isBadRequest());

        List<Com_storeroom_paybill> com_storeroom_paybills = com_storeroom_paybillRepository.findAll();
        assertThat(com_storeroom_paybills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkType_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_storeroom_paybillRepository.findAll().size();
        // set the field null
        com_storeroom_paybill.setType_operation(null);

        // Create the Com_storeroom_paybill, which fails.

        restCom_storeroom_paybillMockMvc.perform(post("/api/com-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_storeroom_paybill)))
                .andExpect(status().isBadRequest());

        List<Com_storeroom_paybill> com_storeroom_paybills = com_storeroom_paybillRepository.findAll();
        assertThat(com_storeroom_paybills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccount_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_storeroom_paybillRepository.findAll().size();
        // set the field null
        com_storeroom_paybill.setAccount_number(null);

        // Create the Com_storeroom_paybill, which fails.

        restCom_storeroom_paybillMockMvc.perform(post("/api/com-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_storeroom_paybill)))
                .andExpect(status().isBadRequest());

        List<Com_storeroom_paybill> com_storeroom_paybills = com_storeroom_paybillRepository.findAll();
        assertThat(com_storeroom_paybills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_storeroom_paybillRepository.findAll().size();
        // set the field null
        com_storeroom_paybill.setTotal(null);

        // Create the Com_storeroom_paybill, which fails.

        restCom_storeroom_paybillMockMvc.perform(post("/api/com-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_storeroom_paybill)))
                .andExpect(status().isBadRequest());

        List<Com_storeroom_paybill> com_storeroom_paybills = com_storeroom_paybillRepository.findAll();
        assertThat(com_storeroom_paybills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_storeroom_paybills() throws Exception {
        // Initialize the database
        com_storeroom_paybillRepository.saveAndFlush(com_storeroom_paybill);

        // Get all the com_storeroom_paybills
        restCom_storeroom_paybillMockMvc.perform(get("/api/com-storeroom-paybills?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_storeroom_paybill.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].type_operation").value(hasItem(DEFAULT_TYPE_OPERATION.toString())))
                .andExpect(jsonPath("$.[*].employer_registration").value(hasItem(DEFAULT_EMPLOYER_REGISTRATION.toString())))
                .andExpect(jsonPath("$.[*].account_number").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getCom_storeroom_paybill() throws Exception {
        // Initialize the database
        com_storeroom_paybillRepository.saveAndFlush(com_storeroom_paybill);

        // Get the com_storeroom_paybill
        restCom_storeroom_paybillMockMvc.perform(get("/api/com-storeroom-paybills/{id}", com_storeroom_paybill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_storeroom_paybill.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.type_operation").value(DEFAULT_TYPE_OPERATION.toString()))
            .andExpect(jsonPath("$.employer_registration").value(DEFAULT_EMPLOYER_REGISTRATION.toString()))
            .andExpect(jsonPath("$.account_number").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_storeroom_paybill() throws Exception {
        // Get the com_storeroom_paybill
        restCom_storeroom_paybillMockMvc.perform(get("/api/com-storeroom-paybills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_storeroom_paybill() throws Exception {
        // Initialize the database
        com_storeroom_paybillService.save(com_storeroom_paybill);

        int databaseSizeBeforeUpdate = com_storeroom_paybillRepository.findAll().size();

        // Update the com_storeroom_paybill
        Com_storeroom_paybill updatedCom_storeroom_paybill = new Com_storeroom_paybill();
        updatedCom_storeroom_paybill.setId(com_storeroom_paybill.getId());
        updatedCom_storeroom_paybill.setVersion(UPDATED_VERSION);
        updatedCom_storeroom_paybill.setType_operation(UPDATED_TYPE_OPERATION);
        updatedCom_storeroom_paybill.setEmployer_registration(UPDATED_EMPLOYER_REGISTRATION);
        updatedCom_storeroom_paybill.setAccount_number(UPDATED_ACCOUNT_NUMBER);
        updatedCom_storeroom_paybill.setTotal(UPDATED_TOTAL);

        restCom_storeroom_paybillMockMvc.perform(put("/api/com-storeroom-paybills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_storeroom_paybill)))
                .andExpect(status().isOk());

        // Validate the Com_storeroom_paybill in the database
        List<Com_storeroom_paybill> com_storeroom_paybills = com_storeroom_paybillRepository.findAll();
        assertThat(com_storeroom_paybills).hasSize(databaseSizeBeforeUpdate);
        Com_storeroom_paybill testCom_storeroom_paybill = com_storeroom_paybills.get(com_storeroom_paybills.size() - 1);
        assertThat(testCom_storeroom_paybill.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_storeroom_paybill.getType_operation()).isEqualTo(UPDATED_TYPE_OPERATION);
        assertThat(testCom_storeroom_paybill.getEmployer_registration()).isEqualTo(UPDATED_EMPLOYER_REGISTRATION);
        assertThat(testCom_storeroom_paybill.getAccount_number()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCom_storeroom_paybill.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void deleteCom_storeroom_paybill() throws Exception {
        // Initialize the database
        com_storeroom_paybillService.save(com_storeroom_paybill);

        int databaseSizeBeforeDelete = com_storeroom_paybillRepository.findAll().size();

        // Get the com_storeroom_paybill
        restCom_storeroom_paybillMockMvc.perform(delete("/api/com-storeroom-paybills/{id}", com_storeroom_paybill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_storeroom_paybill> com_storeroom_paybills = com_storeroom_paybillRepository.findAll();
        assertThat(com_storeroom_paybills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
