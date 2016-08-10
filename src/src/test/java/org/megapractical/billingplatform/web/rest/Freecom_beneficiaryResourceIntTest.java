package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_beneficiary;
import org.megapractical.billingplatform.repository.Freecom_beneficiaryRepository;
import org.megapractical.billingplatform.service.Freecom_beneficiaryService;

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
 * Test class for the Freecom_beneficiaryResource REST controller.
 *
 * @see Freecom_beneficiaryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_beneficiaryResourceIntTest {

    private static final String DEFAULT_RECEIVER_BANK = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_RECEIVER_BANK = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_TYPE_ACCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TYPE_ACCOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ACCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNT = new BigDecimal(2);
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";
    private static final String DEFAULT_CONCEPT = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CONCEPT = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_IVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_IVA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PAYMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYMENT_AMOUNT = new BigDecimal(2);

    @Inject
    private Freecom_beneficiaryRepository freecom_beneficiaryRepository;

    @Inject
    private Freecom_beneficiaryService freecom_beneficiaryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_beneficiaryMockMvc;

    private Freecom_beneficiary freecom_beneficiary;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_beneficiaryResource freecom_beneficiaryResource = new Freecom_beneficiaryResource();
        ReflectionTestUtils.setField(freecom_beneficiaryResource, "freecom_beneficiaryService", freecom_beneficiaryService);
        this.restFreecom_beneficiaryMockMvc = MockMvcBuilders.standaloneSetup(freecom_beneficiaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_beneficiary = new Freecom_beneficiary();
        freecom_beneficiary.setReceiver_bank(DEFAULT_RECEIVER_BANK);
        freecom_beneficiary.setName(DEFAULT_NAME);
        freecom_beneficiary.setType_account(DEFAULT_TYPE_ACCOUNT);
        freecom_beneficiary.setAccount(DEFAULT_ACCOUNT);
        freecom_beneficiary.setRfc(DEFAULT_RFC);
        freecom_beneficiary.setConcept(DEFAULT_CONCEPT);
        freecom_beneficiary.setIva(DEFAULT_IVA);
        freecom_beneficiary.setPayment_amount(DEFAULT_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void createFreecom_beneficiary() throws Exception {
        int databaseSizeBeforeCreate = freecom_beneficiaryRepository.findAll().size();

        // Create the Freecom_beneficiary

        restFreecom_beneficiaryMockMvc.perform(post("/api/freecom-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_beneficiary)))
                .andExpect(status().isCreated());

        // Validate the Freecom_beneficiary in the database
        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_beneficiary testFreecom_beneficiary = freecom_beneficiaries.get(freecom_beneficiaries.size() - 1);
        assertThat(testFreecom_beneficiary.getReceiver_bank()).isEqualTo(DEFAULT_RECEIVER_BANK);
        assertThat(testFreecom_beneficiary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFreecom_beneficiary.getType_account()).isEqualTo(DEFAULT_TYPE_ACCOUNT);
        assertThat(testFreecom_beneficiary.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testFreecom_beneficiary.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFreecom_beneficiary.getConcept()).isEqualTo(DEFAULT_CONCEPT);
        assertThat(testFreecom_beneficiary.getIva()).isEqualTo(DEFAULT_IVA);
        assertThat(testFreecom_beneficiary.getPayment_amount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkReceiver_bankIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_beneficiaryRepository.findAll().size();
        // set the field null
        freecom_beneficiary.setReceiver_bank(null);

        // Create the Freecom_beneficiary, which fails.

        restFreecom_beneficiaryMockMvc.perform(post("/api/freecom-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_beneficiaryRepository.findAll().size();
        // set the field null
        freecom_beneficiary.setName(null);

        // Create the Freecom_beneficiary, which fails.

        restFreecom_beneficiaryMockMvc.perform(post("/api/freecom-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkType_accountIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_beneficiaryRepository.findAll().size();
        // set the field null
        freecom_beneficiary.setType_account(null);

        // Create the Freecom_beneficiary, which fails.

        restFreecom_beneficiaryMockMvc.perform(post("/api/freecom-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_beneficiaryRepository.findAll().size();
        // set the field null
        freecom_beneficiary.setAccount(null);

        // Create the Freecom_beneficiary, which fails.

        restFreecom_beneficiaryMockMvc.perform(post("/api/freecom-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_beneficiaryRepository.findAll().size();
        // set the field null
        freecom_beneficiary.setRfc(null);

        // Create the Freecom_beneficiary, which fails.

        restFreecom_beneficiaryMockMvc.perform(post("/api/freecom-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConceptIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_beneficiaryRepository.findAll().size();
        // set the field null
        freecom_beneficiary.setConcept(null);

        // Create the Freecom_beneficiary, which fails.

        restFreecom_beneficiaryMockMvc.perform(post("/api/freecom-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_beneficiaryRepository.findAll().size();
        // set the field null
        freecom_beneficiary.setIva(null);

        // Create the Freecom_beneficiary, which fails.

        restFreecom_beneficiaryMockMvc.perform(post("/api/freecom-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPayment_amountIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_beneficiaryRepository.findAll().size();
        // set the field null
        freecom_beneficiary.setPayment_amount(null);

        // Create the Freecom_beneficiary, which fails.

        restFreecom_beneficiaryMockMvc.perform(post("/api/freecom-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_beneficiaries() throws Exception {
        // Initialize the database
        freecom_beneficiaryRepository.saveAndFlush(freecom_beneficiary);

        // Get all the freecom_beneficiaries
        restFreecom_beneficiaryMockMvc.perform(get("/api/freecom-beneficiaries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_beneficiary.getId().intValue())))
                .andExpect(jsonPath("$.[*].receiver_bank").value(hasItem(DEFAULT_RECEIVER_BANK.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type_account").value(hasItem(DEFAULT_TYPE_ACCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT.toString())))
                .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA.intValue())))
                .andExpect(jsonPath("$.[*].payment_amount").value(hasItem(DEFAULT_PAYMENT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_beneficiary() throws Exception {
        // Initialize the database
        freecom_beneficiaryRepository.saveAndFlush(freecom_beneficiary);

        // Get the freecom_beneficiary
        restFreecom_beneficiaryMockMvc.perform(get("/api/freecom-beneficiaries/{id}", freecom_beneficiary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_beneficiary.getId().intValue()))
            .andExpect(jsonPath("$.receiver_bank").value(DEFAULT_RECEIVER_BANK.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type_account").value(DEFAULT_TYPE_ACCOUNT.intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT.intValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.concept").value(DEFAULT_CONCEPT.toString()))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA.intValue()))
            .andExpect(jsonPath("$.payment_amount").value(DEFAULT_PAYMENT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_beneficiary() throws Exception {
        // Get the freecom_beneficiary
        restFreecom_beneficiaryMockMvc.perform(get("/api/freecom-beneficiaries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_beneficiary() throws Exception {
        // Initialize the database
        freecom_beneficiaryService.save(freecom_beneficiary);

        int databaseSizeBeforeUpdate = freecom_beneficiaryRepository.findAll().size();

        // Update the freecom_beneficiary
        Freecom_beneficiary updatedFreecom_beneficiary = new Freecom_beneficiary();
        updatedFreecom_beneficiary.setId(freecom_beneficiary.getId());
        updatedFreecom_beneficiary.setReceiver_bank(UPDATED_RECEIVER_BANK);
        updatedFreecom_beneficiary.setName(UPDATED_NAME);
        updatedFreecom_beneficiary.setType_account(UPDATED_TYPE_ACCOUNT);
        updatedFreecom_beneficiary.setAccount(UPDATED_ACCOUNT);
        updatedFreecom_beneficiary.setRfc(UPDATED_RFC);
        updatedFreecom_beneficiary.setConcept(UPDATED_CONCEPT);
        updatedFreecom_beneficiary.setIva(UPDATED_IVA);
        updatedFreecom_beneficiary.setPayment_amount(UPDATED_PAYMENT_AMOUNT);

        restFreecom_beneficiaryMockMvc.perform(put("/api/freecom-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_beneficiary)))
                .andExpect(status().isOk());

        // Validate the Freecom_beneficiary in the database
        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeUpdate);
        Freecom_beneficiary testFreecom_beneficiary = freecom_beneficiaries.get(freecom_beneficiaries.size() - 1);
        assertThat(testFreecom_beneficiary.getReceiver_bank()).isEqualTo(UPDATED_RECEIVER_BANK);
        assertThat(testFreecom_beneficiary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFreecom_beneficiary.getType_account()).isEqualTo(UPDATED_TYPE_ACCOUNT);
        assertThat(testFreecom_beneficiary.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testFreecom_beneficiary.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFreecom_beneficiary.getConcept()).isEqualTo(UPDATED_CONCEPT);
        assertThat(testFreecom_beneficiary.getIva()).isEqualTo(UPDATED_IVA);
        assertThat(testFreecom_beneficiary.getPayment_amount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteFreecom_beneficiary() throws Exception {
        // Initialize the database
        freecom_beneficiaryService.save(freecom_beneficiary);

        int databaseSizeBeforeDelete = freecom_beneficiaryRepository.findAll().size();

        // Get the freecom_beneficiary
        restFreecom_beneficiaryMockMvc.perform(delete("/api/freecom-beneficiaries/{id}", freecom_beneficiary.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_beneficiary> freecom_beneficiaries = freecom_beneficiaryRepository.findAll();
        assertThat(freecom_beneficiaries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
