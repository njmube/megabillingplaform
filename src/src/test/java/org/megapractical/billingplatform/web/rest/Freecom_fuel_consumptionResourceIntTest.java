package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_fuel_consumption;
import org.megapractical.billingplatform.repository.Freecom_fuel_consumptionRepository;
import org.megapractical.billingplatform.service.Freecom_fuel_consumptionService;

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
 * Test class for the Freecom_fuel_consumptionResource REST controller.
 *
 * @see Freecom_fuel_consumptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_fuel_consumptionResourceIntTest {

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
    private Freecom_fuel_consumptionRepository freecom_fuel_consumptionRepository;

    @Inject
    private Freecom_fuel_consumptionService freecom_fuel_consumptionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_fuel_consumptionMockMvc;

    private Freecom_fuel_consumption freecom_fuel_consumption;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_fuel_consumptionResource freecom_fuel_consumptionResource = new Freecom_fuel_consumptionResource();
        ReflectionTestUtils.setField(freecom_fuel_consumptionResource, "freecom_fuel_consumptionService", freecom_fuel_consumptionService);
        this.restFreecom_fuel_consumptionMockMvc = MockMvcBuilders.standaloneSetup(freecom_fuel_consumptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_fuel_consumption = new Freecom_fuel_consumption();
        freecom_fuel_consumption.setVersion(DEFAULT_VERSION);
        freecom_fuel_consumption.setType_operation(DEFAULT_TYPE_OPERATION);
        freecom_fuel_consumption.setAccount_number(DEFAULT_ACCOUNT_NUMBER);
        freecom_fuel_consumption.setSubtotal(DEFAULT_SUBTOTAL);
        freecom_fuel_consumption.setTotal(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createFreecom_fuel_consumption() throws Exception {
        int databaseSizeBeforeCreate = freecom_fuel_consumptionRepository.findAll().size();

        // Create the Freecom_fuel_consumption

        restFreecom_fuel_consumptionMockMvc.perform(post("/api/freecom-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_fuel_consumption)))
                .andExpect(status().isCreated());

        // Validate the Freecom_fuel_consumption in the database
        List<Freecom_fuel_consumption> freecom_fuel_consumptions = freecom_fuel_consumptionRepository.findAll();
        assertThat(freecom_fuel_consumptions).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_fuel_consumption testFreecom_fuel_consumption = freecom_fuel_consumptions.get(freecom_fuel_consumptions.size() - 1);
        assertThat(testFreecom_fuel_consumption.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_fuel_consumption.getType_operation()).isEqualTo(DEFAULT_TYPE_OPERATION);
        assertThat(testFreecom_fuel_consumption.getAccount_number()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testFreecom_fuel_consumption.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testFreecom_fuel_consumption.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_fuel_consumptionRepository.findAll().size();
        // set the field null
        freecom_fuel_consumption.setVersion(null);

        // Create the Freecom_fuel_consumption, which fails.

        restFreecom_fuel_consumptionMockMvc.perform(post("/api/freecom-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_fuel_consumption)))
                .andExpect(status().isBadRequest());

        List<Freecom_fuel_consumption> freecom_fuel_consumptions = freecom_fuel_consumptionRepository.findAll();
        assertThat(freecom_fuel_consumptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkType_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_fuel_consumptionRepository.findAll().size();
        // set the field null
        freecom_fuel_consumption.setType_operation(null);

        // Create the Freecom_fuel_consumption, which fails.

        restFreecom_fuel_consumptionMockMvc.perform(post("/api/freecom-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_fuel_consumption)))
                .andExpect(status().isBadRequest());

        List<Freecom_fuel_consumption> freecom_fuel_consumptions = freecom_fuel_consumptionRepository.findAll();
        assertThat(freecom_fuel_consumptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccount_numberIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_fuel_consumptionRepository.findAll().size();
        // set the field null
        freecom_fuel_consumption.setAccount_number(null);

        // Create the Freecom_fuel_consumption, which fails.

        restFreecom_fuel_consumptionMockMvc.perform(post("/api/freecom-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_fuel_consumption)))
                .andExpect(status().isBadRequest());

        List<Freecom_fuel_consumption> freecom_fuel_consumptions = freecom_fuel_consumptionRepository.findAll();
        assertThat(freecom_fuel_consumptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_fuel_consumptionRepository.findAll().size();
        // set the field null
        freecom_fuel_consumption.setTotal(null);

        // Create the Freecom_fuel_consumption, which fails.

        restFreecom_fuel_consumptionMockMvc.perform(post("/api/freecom-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_fuel_consumption)))
                .andExpect(status().isBadRequest());

        List<Freecom_fuel_consumption> freecom_fuel_consumptions = freecom_fuel_consumptionRepository.findAll();
        assertThat(freecom_fuel_consumptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_fuel_consumptions() throws Exception {
        // Initialize the database
        freecom_fuel_consumptionRepository.saveAndFlush(freecom_fuel_consumption);

        // Get all the freecom_fuel_consumptions
        restFreecom_fuel_consumptionMockMvc.perform(get("/api/freecom-fuel-consumptions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_fuel_consumption.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].type_operation").value(hasItem(DEFAULT_TYPE_OPERATION.toString())))
                .andExpect(jsonPath("$.[*].account_number").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_fuel_consumption() throws Exception {
        // Initialize the database
        freecom_fuel_consumptionRepository.saveAndFlush(freecom_fuel_consumption);

        // Get the freecom_fuel_consumption
        restFreecom_fuel_consumptionMockMvc.perform(get("/api/freecom-fuel-consumptions/{id}", freecom_fuel_consumption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_fuel_consumption.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.type_operation").value(DEFAULT_TYPE_OPERATION.toString()))
            .andExpect(jsonPath("$.account_number").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_fuel_consumption() throws Exception {
        // Get the freecom_fuel_consumption
        restFreecom_fuel_consumptionMockMvc.perform(get("/api/freecom-fuel-consumptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_fuel_consumption() throws Exception {
        // Initialize the database
        freecom_fuel_consumptionService.save(freecom_fuel_consumption);

        int databaseSizeBeforeUpdate = freecom_fuel_consumptionRepository.findAll().size();

        // Update the freecom_fuel_consumption
        Freecom_fuel_consumption updatedFreecom_fuel_consumption = new Freecom_fuel_consumption();
        updatedFreecom_fuel_consumption.setId(freecom_fuel_consumption.getId());
        updatedFreecom_fuel_consumption.setVersion(UPDATED_VERSION);
        updatedFreecom_fuel_consumption.setType_operation(UPDATED_TYPE_OPERATION);
        updatedFreecom_fuel_consumption.setAccount_number(UPDATED_ACCOUNT_NUMBER);
        updatedFreecom_fuel_consumption.setSubtotal(UPDATED_SUBTOTAL);
        updatedFreecom_fuel_consumption.setTotal(UPDATED_TOTAL);

        restFreecom_fuel_consumptionMockMvc.perform(put("/api/freecom-fuel-consumptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_fuel_consumption)))
                .andExpect(status().isOk());

        // Validate the Freecom_fuel_consumption in the database
        List<Freecom_fuel_consumption> freecom_fuel_consumptions = freecom_fuel_consumptionRepository.findAll();
        assertThat(freecom_fuel_consumptions).hasSize(databaseSizeBeforeUpdate);
        Freecom_fuel_consumption testFreecom_fuel_consumption = freecom_fuel_consumptions.get(freecom_fuel_consumptions.size() - 1);
        assertThat(testFreecom_fuel_consumption.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_fuel_consumption.getType_operation()).isEqualTo(UPDATED_TYPE_OPERATION);
        assertThat(testFreecom_fuel_consumption.getAccount_number()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testFreecom_fuel_consumption.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testFreecom_fuel_consumption.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void deleteFreecom_fuel_consumption() throws Exception {
        // Initialize the database
        freecom_fuel_consumptionService.save(freecom_fuel_consumption);

        int databaseSizeBeforeDelete = freecom_fuel_consumptionRepository.findAll().size();

        // Get the freecom_fuel_consumption
        restFreecom_fuel_consumptionMockMvc.perform(delete("/api/freecom-fuel-consumptions/{id}", freecom_fuel_consumption.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_fuel_consumption> freecom_fuel_consumptions = freecom_fuel_consumptionRepository.findAll();
        assertThat(freecom_fuel_consumptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
