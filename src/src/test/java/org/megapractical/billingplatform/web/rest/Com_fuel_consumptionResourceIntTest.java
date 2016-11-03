package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_fuel_consumption;
import org.megapractical.billingplatform.repository.Com_fuel_consumptionRepository;
import org.megapractical.billingplatform.service.Com_fuel_consumptionService;

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
 * Test class for the Com_fuel_consumptionResource REST controller.
 *
 * @see Com_fuel_consumptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_fuel_consumptionResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_TYPE_OPERATION = "AAAAA";
    private static final String UPDATED_TYPE_OPERATION = "BBBBB";
    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBB";

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    @Inject
    private Com_fuel_consumptionRepository com_fuel_consumptionRepository;

    @Inject
    private Com_fuel_consumptionService com_fuel_consumptionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_fuel_consumptionMockMvc;

    private Com_fuel_consumption com_fuel_consumption;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_fuel_consumptionResource com_fuel_consumptionResource = new Com_fuel_consumptionResource();
        ReflectionTestUtils.setField(com_fuel_consumptionResource, "com_fuel_consumptionService", com_fuel_consumptionService);
        this.restCom_fuel_consumptionMockMvc = MockMvcBuilders.standaloneSetup(com_fuel_consumptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_fuel_consumption = new Com_fuel_consumption();
        com_fuel_consumption.setVersion(DEFAULT_VERSION);
        com_fuel_consumption.setType_operation(DEFAULT_TYPE_OPERATION);
        com_fuel_consumption.setAccount_number(DEFAULT_ACCOUNT_NUMBER);
        com_fuel_consumption.setSubtotal(DEFAULT_SUBTOTAL);
        com_fuel_consumption.setTotal(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createCom_fuel_consumption() throws Exception {
        int databaseSizeBeforeCreate = com_fuel_consumptionRepository.findAll().size();

        // Create the Com_fuel_consumption

        restCom_fuel_consumptionMockMvc.perform(post("/api/com-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_fuel_consumption)))
                .andExpect(status().isCreated());

        // Validate the Com_fuel_consumption in the database
        List<Com_fuel_consumption> com_fuel_consumptions = com_fuel_consumptionRepository.findAll();
        assertThat(com_fuel_consumptions).hasSize(databaseSizeBeforeCreate + 1);
        Com_fuel_consumption testCom_fuel_consumption = com_fuel_consumptions.get(com_fuel_consumptions.size() - 1);
        assertThat(testCom_fuel_consumption.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_fuel_consumption.getType_operation()).isEqualTo(DEFAULT_TYPE_OPERATION);
        assertThat(testCom_fuel_consumption.getAccount_number()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCom_fuel_consumption.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testCom_fuel_consumption.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_fuel_consumptionRepository.findAll().size();
        // set the field null
        com_fuel_consumption.setVersion(null);

        // Create the Com_fuel_consumption, which fails.

        restCom_fuel_consumptionMockMvc.perform(post("/api/com-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_fuel_consumption)))
                .andExpect(status().isBadRequest());

        List<Com_fuel_consumption> com_fuel_consumptions = com_fuel_consumptionRepository.findAll();
        assertThat(com_fuel_consumptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkType_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_fuel_consumptionRepository.findAll().size();
        // set the field null
        com_fuel_consumption.setType_operation(null);

        // Create the Com_fuel_consumption, which fails.

        restCom_fuel_consumptionMockMvc.perform(post("/api/com-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_fuel_consumption)))
                .andExpect(status().isBadRequest());

        List<Com_fuel_consumption> com_fuel_consumptions = com_fuel_consumptionRepository.findAll();
        assertThat(com_fuel_consumptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccount_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_fuel_consumptionRepository.findAll().size();
        // set the field null
        com_fuel_consumption.setAccount_number(null);

        // Create the Com_fuel_consumption, which fails.

        restCom_fuel_consumptionMockMvc.perform(post("/api/com-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_fuel_consumption)))
                .andExpect(status().isBadRequest());

        List<Com_fuel_consumption> com_fuel_consumptions = com_fuel_consumptionRepository.findAll();
        assertThat(com_fuel_consumptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_fuel_consumptionRepository.findAll().size();
        // set the field null
        com_fuel_consumption.setTotal(null);

        // Create the Com_fuel_consumption, which fails.

        restCom_fuel_consumptionMockMvc.perform(post("/api/com-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_fuel_consumption)))
                .andExpect(status().isBadRequest());

        List<Com_fuel_consumption> com_fuel_consumptions = com_fuel_consumptionRepository.findAll();
        assertThat(com_fuel_consumptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_fuel_consumptions() throws Exception {
        // Initialize the database
        com_fuel_consumptionRepository.saveAndFlush(com_fuel_consumption);

        // Get all the com_fuel_consumptions
        restCom_fuel_consumptionMockMvc.perform(get("/api/com-fuel-consumptions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_fuel_consumption.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].type_operation").value(hasItem(DEFAULT_TYPE_OPERATION.toString())))
                .andExpect(jsonPath("$.[*].account_number").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getCom_fuel_consumption() throws Exception {
        // Initialize the database
        com_fuel_consumptionRepository.saveAndFlush(com_fuel_consumption);

        // Get the com_fuel_consumption
        restCom_fuel_consumptionMockMvc.perform(get("/api/com-fuel-consumptions/{id}", com_fuel_consumption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_fuel_consumption.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.type_operation").value(DEFAULT_TYPE_OPERATION.toString()))
            .andExpect(jsonPath("$.account_number").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_fuel_consumption() throws Exception {
        // Get the com_fuel_consumption
        restCom_fuel_consumptionMockMvc.perform(get("/api/com-fuel-consumptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_fuel_consumption() throws Exception {
        // Initialize the database
        com_fuel_consumptionService.save(com_fuel_consumption);

        int databaseSizeBeforeUpdate = com_fuel_consumptionRepository.findAll().size();

        // Update the com_fuel_consumption
        Com_fuel_consumption updatedCom_fuel_consumption = new Com_fuel_consumption();
        updatedCom_fuel_consumption.setId(com_fuel_consumption.getId());
        updatedCom_fuel_consumption.setVersion(UPDATED_VERSION);
        updatedCom_fuel_consumption.setType_operation(UPDATED_TYPE_OPERATION);
        updatedCom_fuel_consumption.setAccount_number(UPDATED_ACCOUNT_NUMBER);
        updatedCom_fuel_consumption.setSubtotal(UPDATED_SUBTOTAL);
        updatedCom_fuel_consumption.setTotal(UPDATED_TOTAL);

        restCom_fuel_consumptionMockMvc.perform(put("/api/com-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_fuel_consumption)))
                .andExpect(status().isOk());

        // Validate the Com_fuel_consumption in the database
        List<Com_fuel_consumption> com_fuel_consumptions = com_fuel_consumptionRepository.findAll();
        assertThat(com_fuel_consumptions).hasSize(databaseSizeBeforeUpdate);
        Com_fuel_consumption testCom_fuel_consumption = com_fuel_consumptions.get(com_fuel_consumptions.size() - 1);
        assertThat(testCom_fuel_consumption.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_fuel_consumption.getType_operation()).isEqualTo(UPDATED_TYPE_OPERATION);
        assertThat(testCom_fuel_consumption.getAccount_number()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCom_fuel_consumption.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testCom_fuel_consumption.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void deleteCom_fuel_consumption() throws Exception {
        // Initialize the database
        com_fuel_consumptionService.save(com_fuel_consumption);

        int databaseSizeBeforeDelete = com_fuel_consumptionRepository.findAll().size();

        // Get the com_fuel_consumption
        restCom_fuel_consumptionMockMvc.perform(delete("/api/com-fuel-consumptions/{id}", com_fuel_consumption.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_fuel_consumption> com_fuel_consumptions = com_fuel_consumptionRepository.findAll();
        assertThat(com_fuel_consumptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
