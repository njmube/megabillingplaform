package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_data_operation;
import org.megapractical.billingplatform.repository.Com_data_operationRepository;
import org.megapractical.billingplatform.service.Com_data_operationService;

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
 * Test class for the Com_data_operationResource REST controller.
 *
 * @see Com_data_operationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_data_operationResourceIntTest {


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
    private Com_data_operationRepository com_data_operationRepository;

    @Inject
    private Com_data_operationService com_data_operationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_data_operationMockMvc;

    private Com_data_operation com_data_operation;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_data_operationResource com_data_operationResource = new Com_data_operationResource();
        ReflectionTestUtils.setField(com_data_operationResource, "com_data_operationService", com_data_operationService);
        this.restCom_data_operationMockMvc = MockMvcBuilders.standaloneSetup(com_data_operationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_data_operation = new Com_data_operation();
        com_data_operation.setNotarialinstrument(DEFAULT_NOTARIALINSTRUMENT);
        com_data_operation.setDateinstnotarial(DEFAULT_DATEINSTNOTARIAL);
        com_data_operation.setAmountofoperation(DEFAULT_AMOUNTOFOPERATION);
        com_data_operation.setSubtotal(DEFAULT_SUBTOTAL);
        com_data_operation.setIva(DEFAULT_IVA);
    }

    @Test
    @Transactional
    public void createCom_data_operation() throws Exception {
        int databaseSizeBeforeCreate = com_data_operationRepository.findAll().size();

        // Create the Com_data_operation

        restCom_data_operationMockMvc.perform(post("/api/com-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_data_operation)))
                .andExpect(status().isCreated());

        // Validate the Com_data_operation in the database
        List<Com_data_operation> com_data_operations = com_data_operationRepository.findAll();
        assertThat(com_data_operations).hasSize(databaseSizeBeforeCreate + 1);
        Com_data_operation testCom_data_operation = com_data_operations.get(com_data_operations.size() - 1);
        assertThat(testCom_data_operation.getNotarialinstrument()).isEqualTo(DEFAULT_NOTARIALINSTRUMENT);
        assertThat(testCom_data_operation.getDateinstnotarial()).isEqualTo(DEFAULT_DATEINSTNOTARIAL);
        assertThat(testCom_data_operation.getAmountofoperation()).isEqualTo(DEFAULT_AMOUNTOFOPERATION);
        assertThat(testCom_data_operation.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testCom_data_operation.getIva()).isEqualTo(DEFAULT_IVA);
    }

    @Test
    @Transactional
    public void checkNotarialinstrumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_data_operationRepository.findAll().size();
        // set the field null
        com_data_operation.setNotarialinstrument(null);

        // Create the Com_data_operation, which fails.

        restCom_data_operationMockMvc.perform(post("/api/com-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_data_operation)))
                .andExpect(status().isBadRequest());

        List<Com_data_operation> com_data_operations = com_data_operationRepository.findAll();
        assertThat(com_data_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateinstnotarialIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_data_operationRepository.findAll().size();
        // set the field null
        com_data_operation.setDateinstnotarial(null);

        // Create the Com_data_operation, which fails.

        restCom_data_operationMockMvc.perform(post("/api/com-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_data_operation)))
                .andExpect(status().isBadRequest());

        List<Com_data_operation> com_data_operations = com_data_operationRepository.findAll();
        assertThat(com_data_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountofoperationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_data_operationRepository.findAll().size();
        // set the field null
        com_data_operation.setAmountofoperation(null);

        // Create the Com_data_operation, which fails.

        restCom_data_operationMockMvc.perform(post("/api/com-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_data_operation)))
                .andExpect(status().isBadRequest());

        List<Com_data_operation> com_data_operations = com_data_operationRepository.findAll();
        assertThat(com_data_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubtotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_data_operationRepository.findAll().size();
        // set the field null
        com_data_operation.setSubtotal(null);

        // Create the Com_data_operation, which fails.

        restCom_data_operationMockMvc.perform(post("/api/com-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_data_operation)))
                .andExpect(status().isBadRequest());

        List<Com_data_operation> com_data_operations = com_data_operationRepository.findAll();
        assertThat(com_data_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_data_operationRepository.findAll().size();
        // set the field null
        com_data_operation.setIva(null);

        // Create the Com_data_operation, which fails.

        restCom_data_operationMockMvc.perform(post("/api/com-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_data_operation)))
                .andExpect(status().isBadRequest());

        List<Com_data_operation> com_data_operations = com_data_operationRepository.findAll();
        assertThat(com_data_operations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_data_operations() throws Exception {
        // Initialize the database
        com_data_operationRepository.saveAndFlush(com_data_operation);

        // Get all the com_data_operations
        restCom_data_operationMockMvc.perform(get("/api/com-data-operations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_data_operation.getId().intValue())))
                .andExpect(jsonPath("$.[*].notarialinstrument").value(hasItem(DEFAULT_NOTARIALINSTRUMENT)))
                .andExpect(jsonPath("$.[*].dateinstnotarial").value(hasItem(DEFAULT_DATEINSTNOTARIAL.toString())))
                .andExpect(jsonPath("$.[*].amountofoperation").value(hasItem(DEFAULT_AMOUNTOFOPERATION.intValue())))
                .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
                .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA.intValue())));
    }

    @Test
    @Transactional
    public void getCom_data_operation() throws Exception {
        // Initialize the database
        com_data_operationRepository.saveAndFlush(com_data_operation);

        // Get the com_data_operation
        restCom_data_operationMockMvc.perform(get("/api/com-data-operations/{id}", com_data_operation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_data_operation.getId().intValue()))
            .andExpect(jsonPath("$.notarialinstrument").value(DEFAULT_NOTARIALINSTRUMENT))
            .andExpect(jsonPath("$.dateinstnotarial").value(DEFAULT_DATEINSTNOTARIAL.toString()))
            .andExpect(jsonPath("$.amountofoperation").value(DEFAULT_AMOUNTOFOPERATION.intValue()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_data_operation() throws Exception {
        // Get the com_data_operation
        restCom_data_operationMockMvc.perform(get("/api/com-data-operations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_data_operation() throws Exception {
        // Initialize the database
        com_data_operationService.save(com_data_operation);

        int databaseSizeBeforeUpdate = com_data_operationRepository.findAll().size();

        // Update the com_data_operation
        Com_data_operation updatedCom_data_operation = new Com_data_operation();
        updatedCom_data_operation.setId(com_data_operation.getId());
        updatedCom_data_operation.setNotarialinstrument(UPDATED_NOTARIALINSTRUMENT);
        updatedCom_data_operation.setDateinstnotarial(UPDATED_DATEINSTNOTARIAL);
        updatedCom_data_operation.setAmountofoperation(UPDATED_AMOUNTOFOPERATION);
        updatedCom_data_operation.setSubtotal(UPDATED_SUBTOTAL);
        updatedCom_data_operation.setIva(UPDATED_IVA);

        restCom_data_operationMockMvc.perform(put("/api/com-data-operations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_data_operation)))
                .andExpect(status().isOk());

        // Validate the Com_data_operation in the database
        List<Com_data_operation> com_data_operations = com_data_operationRepository.findAll();
        assertThat(com_data_operations).hasSize(databaseSizeBeforeUpdate);
        Com_data_operation testCom_data_operation = com_data_operations.get(com_data_operations.size() - 1);
        assertThat(testCom_data_operation.getNotarialinstrument()).isEqualTo(UPDATED_NOTARIALINSTRUMENT);
        assertThat(testCom_data_operation.getDateinstnotarial()).isEqualTo(UPDATED_DATEINSTNOTARIAL);
        assertThat(testCom_data_operation.getAmountofoperation()).isEqualTo(UPDATED_AMOUNTOFOPERATION);
        assertThat(testCom_data_operation.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testCom_data_operation.getIva()).isEqualTo(UPDATED_IVA);
    }

    @Test
    @Transactional
    public void deleteCom_data_operation() throws Exception {
        // Initialize the database
        com_data_operationService.save(com_data_operation);

        int databaseSizeBeforeDelete = com_data_operationRepository.findAll().size();

        // Get the com_data_operation
        restCom_data_operationMockMvc.perform(delete("/api/com-data-operations/{id}", com_data_operation.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_data_operation> com_data_operations = com_data_operationRepository.findAll();
        assertThat(com_data_operations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
