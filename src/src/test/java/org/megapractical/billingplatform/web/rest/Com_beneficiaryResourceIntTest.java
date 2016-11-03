package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_beneficiary;
import org.megapractical.billingplatform.repository.Com_beneficiaryRepository;
import org.megapractical.billingplatform.service.Com_beneficiaryService;

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
 * Test class for the Com_beneficiaryResource REST controller.
 *
 * @see Com_beneficiaryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_beneficiaryResourceIntTest {

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
    private Com_beneficiaryRepository com_beneficiaryRepository;

    @Inject
    private Com_beneficiaryService com_beneficiaryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_beneficiaryMockMvc;

    private Com_beneficiary com_beneficiary;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_beneficiaryResource com_beneficiaryResource = new Com_beneficiaryResource();
        ReflectionTestUtils.setField(com_beneficiaryResource, "com_beneficiaryService", com_beneficiaryService);
        this.restCom_beneficiaryMockMvc = MockMvcBuilders.standaloneSetup(com_beneficiaryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_beneficiary = new Com_beneficiary();
        com_beneficiary.setReceiver_bank(DEFAULT_RECEIVER_BANK);
        com_beneficiary.setName(DEFAULT_NAME);
        com_beneficiary.setType_account(DEFAULT_TYPE_ACCOUNT);
        com_beneficiary.setAccount(DEFAULT_ACCOUNT);
        com_beneficiary.setRfc(DEFAULT_RFC);
        com_beneficiary.setConcept(DEFAULT_CONCEPT);
        com_beneficiary.setIva(DEFAULT_IVA);
        com_beneficiary.setPayment_amount(DEFAULT_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void createCom_beneficiary() throws Exception {
        int databaseSizeBeforeCreate = com_beneficiaryRepository.findAll().size();

        // Create the Com_beneficiary

        restCom_beneficiaryMockMvc.perform(post("/api/com-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_beneficiary)))
                .andExpect(status().isCreated());

        // Validate the Com_beneficiary in the database
        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeCreate + 1);
        Com_beneficiary testCom_beneficiary = com_beneficiaries.get(com_beneficiaries.size() - 1);
        assertThat(testCom_beneficiary.getReceiver_bank()).isEqualTo(DEFAULT_RECEIVER_BANK);
        assertThat(testCom_beneficiary.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCom_beneficiary.getType_account()).isEqualTo(DEFAULT_TYPE_ACCOUNT);
        assertThat(testCom_beneficiary.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCom_beneficiary.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testCom_beneficiary.getConcept()).isEqualTo(DEFAULT_CONCEPT);
        assertThat(testCom_beneficiary.getIva()).isEqualTo(DEFAULT_IVA);
        assertThat(testCom_beneficiary.getPayment_amount()).isEqualTo(DEFAULT_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkReceiver_bankIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_beneficiaryRepository.findAll().size();
        // set the field null
        com_beneficiary.setReceiver_bank(null);

        // Create the Com_beneficiary, which fails.

        restCom_beneficiaryMockMvc.perform(post("/api/com-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_beneficiaryRepository.findAll().size();
        // set the field null
        com_beneficiary.setName(null);

        // Create the Com_beneficiary, which fails.

        restCom_beneficiaryMockMvc.perform(post("/api/com-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkType_accountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_beneficiaryRepository.findAll().size();
        // set the field null
        com_beneficiary.setType_account(null);

        // Create the Com_beneficiary, which fails.

        restCom_beneficiaryMockMvc.perform(post("/api/com-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_beneficiaryRepository.findAll().size();
        // set the field null
        com_beneficiary.setAccount(null);

        // Create the Com_beneficiary, which fails.

        restCom_beneficiaryMockMvc.perform(post("/api/com-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_beneficiaryRepository.findAll().size();
        // set the field null
        com_beneficiary.setRfc(null);

        // Create the Com_beneficiary, which fails.

        restCom_beneficiaryMockMvc.perform(post("/api/com-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConceptIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_beneficiaryRepository.findAll().size();
        // set the field null
        com_beneficiary.setConcept(null);

        // Create the Com_beneficiary, which fails.

        restCom_beneficiaryMockMvc.perform(post("/api/com-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIvaIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_beneficiaryRepository.findAll().size();
        // set the field null
        com_beneficiary.setIva(null);

        // Create the Com_beneficiary, which fails.

        restCom_beneficiaryMockMvc.perform(post("/api/com-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPayment_amountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_beneficiaryRepository.findAll().size();
        // set the field null
        com_beneficiary.setPayment_amount(null);

        // Create the Com_beneficiary, which fails.

        restCom_beneficiaryMockMvc.perform(post("/api/com-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_beneficiary)))
                .andExpect(status().isBadRequest());

        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_beneficiaries() throws Exception {
        // Initialize the database
        com_beneficiaryRepository.saveAndFlush(com_beneficiary);

        // Get all the com_beneficiaries
        restCom_beneficiaryMockMvc.perform(get("/api/com-beneficiaries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_beneficiary.getId().intValue())))
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
    public void getCom_beneficiary() throws Exception {
        // Initialize the database
        com_beneficiaryRepository.saveAndFlush(com_beneficiary);

        // Get the com_beneficiary
        restCom_beneficiaryMockMvc.perform(get("/api/com-beneficiaries/{id}", com_beneficiary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_beneficiary.getId().intValue()))
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
    public void getNonExistingCom_beneficiary() throws Exception {
        // Get the com_beneficiary
        restCom_beneficiaryMockMvc.perform(get("/api/com-beneficiaries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_beneficiary() throws Exception {
        // Initialize the database
        com_beneficiaryService.save(com_beneficiary);

        int databaseSizeBeforeUpdate = com_beneficiaryRepository.findAll().size();

        // Update the com_beneficiary
        Com_beneficiary updatedCom_beneficiary = new Com_beneficiary();
        updatedCom_beneficiary.setId(com_beneficiary.getId());
        updatedCom_beneficiary.setReceiver_bank(UPDATED_RECEIVER_BANK);
        updatedCom_beneficiary.setName(UPDATED_NAME);
        updatedCom_beneficiary.setType_account(UPDATED_TYPE_ACCOUNT);
        updatedCom_beneficiary.setAccount(UPDATED_ACCOUNT);
        updatedCom_beneficiary.setRfc(UPDATED_RFC);
        updatedCom_beneficiary.setConcept(UPDATED_CONCEPT);
        updatedCom_beneficiary.setIva(UPDATED_IVA);
        updatedCom_beneficiary.setPayment_amount(UPDATED_PAYMENT_AMOUNT);

        restCom_beneficiaryMockMvc.perform(put("/api/com-beneficiaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_beneficiary)))
                .andExpect(status().isOk());

        // Validate the Com_beneficiary in the database
        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeUpdate);
        Com_beneficiary testCom_beneficiary = com_beneficiaries.get(com_beneficiaries.size() - 1);
        assertThat(testCom_beneficiary.getReceiver_bank()).isEqualTo(UPDATED_RECEIVER_BANK);
        assertThat(testCom_beneficiary.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCom_beneficiary.getType_account()).isEqualTo(UPDATED_TYPE_ACCOUNT);
        assertThat(testCom_beneficiary.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCom_beneficiary.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testCom_beneficiary.getConcept()).isEqualTo(UPDATED_CONCEPT);
        assertThat(testCom_beneficiary.getIva()).isEqualTo(UPDATED_IVA);
        assertThat(testCom_beneficiary.getPayment_amount()).isEqualTo(UPDATED_PAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteCom_beneficiary() throws Exception {
        // Initialize the database
        com_beneficiaryService.save(com_beneficiary);

        int databaseSizeBeforeDelete = com_beneficiaryRepository.findAll().size();

        // Get the com_beneficiary
        restCom_beneficiaryMockMvc.perform(delete("/api/com-beneficiaries/{id}", com_beneficiary.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_beneficiary> com_beneficiaries = com_beneficiaryRepository.findAll();
        assertThat(com_beneficiaries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
