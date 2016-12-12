package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_data_operation;
import org.megapractical.billingplatform.repository.Freecom_data_operationRepository;
import org.megapractical.billingplatform.service.Freecom_data_operationService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_data_operationResource REST controller.
 *
 * @see Freecom_data_operationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_data_operationResourceIntTest {


    private static final Integer DEFAULT_NOTARIALINSTRUMENT = 1;
    private static final Integer UPDATED_NOTARIALINSTRUMENT = 2;

    private static final LocalDate DEFAULT_DATEINSTNOTARIAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEINSTNOTARIAL = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMOUNTOFOPERATION = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNTOFOPERATION = new BigDecimal(2);

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_IVA = new BigDecimal(2);

    @Inject
    private Freecom_data_operationRepository freecom_data_operationRepository;

    @Inject
    private Freecom_data_operationService freecom_data_operationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_data_operationMockMvc;

    private Freecom_data_operation freecom_data_operation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_data_operationResource freecom_data_operationResource = new Freecom_data_operationResource();
        ReflectionTestUtils.setField(freecom_data_operationResource, "freecom_data_operationService", freecom_data_operationService);
        this.restFreecom_data_operationMockMvc = MockMvcBuilders.standaloneSetup(freecom_data_operationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_data_operation = new Freecom_data_operation();
        freecom_data_operation.setNotarialinstrument(DEFAULT_NOTARIALINSTRUMENT);
        freecom_data_operation.setDateinstnotarial(DEFAULT_DATEINSTNOTARIAL);
        freecom_data_operation.setAmountofoperation(DEFAULT_AMOUNTOFOPERATION);
        freecom_data_operation.setSubtotal(DEFAULT_SUBTOTAL);
        freecom_data_operation.setIva(DEFAULT_IVA);
    }

    @Test
    @Transactional
    public void createFreecom_data_operation() throws Exception {
        int databaseSizeBeforeCreate = freecom_data_operationRepository.findAll().size();

        // Create the Freecom_data_operation

        restFreecom_data_operationMockMvc.perform(post("/api/freecom-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_data_operation)))
                .andExpect(status().isCreated());

        // Validate the Freecom_data_operation in the database
        List<Freecom_data_operation> freecom_data_operations = freecom_data_operationRepository.findAll();
        assertThat(freecom_data_operations).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_data_operation testFreecom_data_operation = freecom_data_operations.get(freecom_data_operations.size() - 1);
        assertThat(testFreecom_data_operation.getNotarialinstrument()).isEqualTo(DEFAULT_NOTARIALINSTRUMENT);
        assertThat(testFreecom_data_operation.getDateinstnotarial()).isEqualTo(DEFAULT_DATEINSTNOTARIAL);
        assertThat(testFreecom_data_operation.getAmountofoperation()).isEqualTo(DEFAULT_AMOUNTOFOPERATION);
        assertThat(testFreecom_data_operation.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testFreecom_data_operation.getIva()).isEqualTo(DEFAULT_IVA);
    }

    @Test
    @Transactional
    public void checkNotarialinstrumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_data_operationRepository.findAll().size();
        // set the field null
        freecom_data_operation.setNotarialinstrument(null);

        // Create the Freecom_data_operation, which fails.

        restFreecom_data_operationMockMvc.perform(post("/api/freecom-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_data_operation)))
                .andExpect(status().isBadRequest());

        List<Freecom_data_operation> freecom_data_operations = freecom_data_operationRepository.findAll();
        assertThat(freecom_data_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateinstnotarialIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_data_operationRepository.findAll().size();
        // set the field null
        freecom_data_operation.setDateinstnotarial(null);

        // Create the Freecom_data_operation, which fails.

        restFreecom_data_operationMockMvc.perform(post("/api/freecom-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_data_operation)))
                .andExpect(status().isBadRequest());

        List<Freecom_data_operation> freecom_data_operations = freecom_data_operationRepository.findAll();
        assertThat(freecom_data_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountofoperationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_data_operationRepository.findAll().size();
        // set the field null
        freecom_data_operation.setAmountofoperation(null);

        // Create the Freecom_data_operation, which fails.

        restFreecom_data_operationMockMvc.perform(post("/api/freecom-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_data_operation)))
                .andExpect(status().isBadRequest());

        List<Freecom_data_operation> freecom_data_operations = freecom_data_operationRepository.findAll();
        assertThat(freecom_data_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubtotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_data_operationRepository.findAll().size();
        // set the field null
        freecom_data_operation.setSubtotal(null);

        // Create the Freecom_data_operation, which fails.

        restFreecom_data_operationMockMvc.perform(post("/api/freecom-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_data_operation)))
                .andExpect(status().isBadRequest());

        List<Freecom_data_operation> freecom_data_operations = freecom_data_operationRepository.findAll();
        assertThat(freecom_data_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_data_operationRepository.findAll().size();
        // set the field null
        freecom_data_operation.setIva(null);

        // Create the Freecom_data_operation, which fails.

        restFreecom_data_operationMockMvc.perform(post("/api/freecom-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_data_operation)))
                .andExpect(status().isBadRequest());

        List<Freecom_data_operation> freecom_data_operations = freecom_data_operationRepository.findAll();
        assertThat(freecom_data_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_data_operations() throws Exception {
        // Initialize the database
        freecom_data_operationRepository.saveAndFlush(freecom_data_operation);

        // Get all the freecom_data_operations
        restFreecom_data_operationMockMvc.perform(get("/api/freecom-data-operations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_data_operation.getId().intValue())))
                .andExpect(jsonPath("$.[*].notarialinstrument").value(hasItem(DEFAULT_NOTARIALINSTRUMENT)))
                .andExpect(jsonPath("$.[*].dateinstnotarial").value(hasItem(DEFAULT_DATEINSTNOTARIAL.toString())))
                .andExpect(jsonPath("$.[*].amountofoperation").value(hasItem(DEFAULT_AMOUNTOFOPERATION.intValue())))
                .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
                .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_data_operation() throws Exception {
        // Initialize the database
        freecom_data_operationRepository.saveAndFlush(freecom_data_operation);

        // Get the freecom_data_operation
        restFreecom_data_operationMockMvc.perform(get("/api/freecom-data-operations/{id}", freecom_data_operation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_data_operation.getId().intValue()))
            .andExpect(jsonPath("$.notarialinstrument").value(DEFAULT_NOTARIALINSTRUMENT))
            .andExpect(jsonPath("$.dateinstnotarial").value(DEFAULT_DATEINSTNOTARIAL.toString()))
            .andExpect(jsonPath("$.amountofoperation").value(DEFAULT_AMOUNTOFOPERATION.intValue()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_data_operation() throws Exception {
        // Get the freecom_data_operation
        restFreecom_data_operationMockMvc.perform(get("/api/freecom-data-operations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_data_operation() throws Exception {
        // Initialize the database
        freecom_data_operationService.save(freecom_data_operation);

        int databaseSizeBeforeUpdate = freecom_data_operationRepository.findAll().size();

        // Update the freecom_data_operation
        Freecom_data_operation updatedFreecom_data_operation = new Freecom_data_operation();
        updatedFreecom_data_operation.setId(freecom_data_operation.getId());
        updatedFreecom_data_operation.setNotarialinstrument(UPDATED_NOTARIALINSTRUMENT);
        updatedFreecom_data_operation.setDateinstnotarial(UPDATED_DATEINSTNOTARIAL);
        updatedFreecom_data_operation.setAmountofoperation(UPDATED_AMOUNTOFOPERATION);
        updatedFreecom_data_operation.setSubtotal(UPDATED_SUBTOTAL);
        updatedFreecom_data_operation.setIva(UPDATED_IVA);

        restFreecom_data_operationMockMvc.perform(put("/api/freecom-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_data_operation)))
                .andExpect(status().isOk());

        // Validate the Freecom_data_operation in the database
        List<Freecom_data_operation> freecom_data_operations = freecom_data_operationRepository.findAll();
        assertThat(freecom_data_operations).hasSize(databaseSizeBeforeUpdate);
        Freecom_data_operation testFreecom_data_operation = freecom_data_operations.get(freecom_data_operations.size() - 1);
        assertThat(testFreecom_data_operation.getNotarialinstrument()).isEqualTo(UPDATED_NOTARIALINSTRUMENT);
        assertThat(testFreecom_data_operation.getDateinstnotarial()).isEqualTo(UPDATED_DATEINSTNOTARIAL);
        assertThat(testFreecom_data_operation.getAmountofoperation()).isEqualTo(UPDATED_AMOUNTOFOPERATION);
        assertThat(testFreecom_data_operation.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testFreecom_data_operation.getIva()).isEqualTo(UPDATED_IVA);
    }

    @Test
    @Transactional
    public void deleteFreecom_data_operation() throws Exception {
        // Initialize the database
        freecom_data_operationService.save(freecom_data_operation);

        int databaseSizeBeforeDelete = freecom_data_operationRepository.findAll().size();

        // Get the freecom_data_operation
        restFreecom_data_operationMockMvc.perform(delete("/api/freecom-data-operations/{id}", freecom_data_operation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_data_operation> freecom_data_operations = freecom_data_operationRepository.findAll();
        assertThat(freecom_data_operations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
