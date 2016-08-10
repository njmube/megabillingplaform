package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_payer;
import org.megapractical.billingplatform.repository.Freecom_payerRepository;
import org.megapractical.billingplatform.service.Freecom_payerService;

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
 * Test class for the Freecom_payerResource REST controller.
 *
 * @see Freecom_payerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_payerResourceIntTest {

    private static final String DEFAULT_EMITTER_BANK = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_EMITTER_BANK = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TYPE_ACCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TYPE_ACCOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ACCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNT = new BigDecimal(2);
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";

    @Inject
    private Freecom_payerRepository freecom_payerRepository;

    @Inject
    private Freecom_payerService freecom_payerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_payerMockMvc;

    private Freecom_payer freecom_payer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_payerResource freecom_payerResource = new Freecom_payerResource();
        ReflectionTestUtils.setField(freecom_payerResource, "freecom_payerService", freecom_payerService);
        this.restFreecom_payerMockMvc = MockMvcBuilders.standaloneSetup(freecom_payerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_payer = new Freecom_payer();
        freecom_payer.setEmitter_bank(DEFAULT_EMITTER_BANK);
        freecom_payer.setName(DEFAULT_NAME);
        freecom_payer.setType_account(DEFAULT_TYPE_ACCOUNT);
        freecom_payer.setAccount(DEFAULT_ACCOUNT);
        freecom_payer.setRfc(DEFAULT_RFC);
    }

    @Test
    @Transactional
    public void createFreecom_payer() throws Exception {
        int databaseSizeBeforeCreate = freecom_payerRepository.findAll().size();

        // Create the Freecom_payer

        restFreecom_payerMockMvc.perform(post("/api/freecom-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_payer)))
                .andExpect(status().isCreated());

        // Validate the Freecom_payer in the database
        List<Freecom_payer> freecom_payers = freecom_payerRepository.findAll();
        assertThat(freecom_payers).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_payer testFreecom_payer = freecom_payers.get(freecom_payers.size() - 1);
        assertThat(testFreecom_payer.getEmitter_bank()).isEqualTo(DEFAULT_EMITTER_BANK);
        assertThat(testFreecom_payer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFreecom_payer.getType_account()).isEqualTo(DEFAULT_TYPE_ACCOUNT);
        assertThat(testFreecom_payer.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testFreecom_payer.getRfc()).isEqualTo(DEFAULT_RFC);
    }

    @Test
    @Transactional
    public void checkEmitter_bankIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_payerRepository.findAll().size();
        // set the field null
        freecom_payer.setEmitter_bank(null);

        // Create the Freecom_payer, which fails.

        restFreecom_payerMockMvc.perform(post("/api/freecom-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_payer)))
                .andExpect(status().isBadRequest());

        List<Freecom_payer> freecom_payers = freecom_payerRepository.findAll();
        assertThat(freecom_payers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_payerRepository.findAll().size();
        // set the field null
        freecom_payer.setName(null);

        // Create the Freecom_payer, which fails.

        restFreecom_payerMockMvc.perform(post("/api/freecom-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_payer)))
                .andExpect(status().isBadRequest());

        List<Freecom_payer> freecom_payers = freecom_payerRepository.findAll();
        assertThat(freecom_payers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkType_accountIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_payerRepository.findAll().size();
        // set the field null
        freecom_payer.setType_account(null);

        // Create the Freecom_payer, which fails.

        restFreecom_payerMockMvc.perform(post("/api/freecom-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_payer)))
                .andExpect(status().isBadRequest());

        List<Freecom_payer> freecom_payers = freecom_payerRepository.findAll();
        assertThat(freecom_payers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_payerRepository.findAll().size();
        // set the field null
        freecom_payer.setAccount(null);

        // Create the Freecom_payer, which fails.

        restFreecom_payerMockMvc.perform(post("/api/freecom-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_payer)))
                .andExpect(status().isBadRequest());

        List<Freecom_payer> freecom_payers = freecom_payerRepository.findAll();
        assertThat(freecom_payers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_payerRepository.findAll().size();
        // set the field null
        freecom_payer.setRfc(null);

        // Create the Freecom_payer, which fails.

        restFreecom_payerMockMvc.perform(post("/api/freecom-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_payer)))
                .andExpect(status().isBadRequest());

        List<Freecom_payer> freecom_payers = freecom_payerRepository.findAll();
        assertThat(freecom_payers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_payers() throws Exception {
        // Initialize the database
        freecom_payerRepository.saveAndFlush(freecom_payer);

        // Get all the freecom_payers
        restFreecom_payerMockMvc.perform(get("/api/freecom-payers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_payer.getId().intValue())))
                .andExpect(jsonPath("$.[*].emitter_bank").value(hasItem(DEFAULT_EMITTER_BANK.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type_account").value(hasItem(DEFAULT_TYPE_ACCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_payer() throws Exception {
        // Initialize the database
        freecom_payerRepository.saveAndFlush(freecom_payer);

        // Get the freecom_payer
        restFreecom_payerMockMvc.perform(get("/api/freecom-payers/{id}", freecom_payer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_payer.getId().intValue()))
            .andExpect(jsonPath("$.emitter_bank").value(DEFAULT_EMITTER_BANK.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type_account").value(DEFAULT_TYPE_ACCOUNT.intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT.intValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_payer() throws Exception {
        // Get the freecom_payer
        restFreecom_payerMockMvc.perform(get("/api/freecom-payers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_payer() throws Exception {
        // Initialize the database
        freecom_payerService.save(freecom_payer);

        int databaseSizeBeforeUpdate = freecom_payerRepository.findAll().size();

        // Update the freecom_payer
        Freecom_payer updatedFreecom_payer = new Freecom_payer();
        updatedFreecom_payer.setId(freecom_payer.getId());
        updatedFreecom_payer.setEmitter_bank(UPDATED_EMITTER_BANK);
        updatedFreecom_payer.setName(UPDATED_NAME);
        updatedFreecom_payer.setType_account(UPDATED_TYPE_ACCOUNT);
        updatedFreecom_payer.setAccount(UPDATED_ACCOUNT);
        updatedFreecom_payer.setRfc(UPDATED_RFC);

        restFreecom_payerMockMvc.perform(put("/api/freecom-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_payer)))
                .andExpect(status().isOk());

        // Validate the Freecom_payer in the database
        List<Freecom_payer> freecom_payers = freecom_payerRepository.findAll();
        assertThat(freecom_payers).hasSize(databaseSizeBeforeUpdate);
        Freecom_payer testFreecom_payer = freecom_payers.get(freecom_payers.size() - 1);
        assertThat(testFreecom_payer.getEmitter_bank()).isEqualTo(UPDATED_EMITTER_BANK);
        assertThat(testFreecom_payer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFreecom_payer.getType_account()).isEqualTo(UPDATED_TYPE_ACCOUNT);
        assertThat(testFreecom_payer.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testFreecom_payer.getRfc()).isEqualTo(UPDATED_RFC);
    }

    @Test
    @Transactional
    public void deleteFreecom_payer() throws Exception {
        // Initialize the database
        freecom_payerService.save(freecom_payer);

        int databaseSizeBeforeDelete = freecom_payerRepository.findAll().size();

        // Get the freecom_payer
        restFreecom_payerMockMvc.perform(delete("/api/freecom-payers/{id}", freecom_payer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_payer> freecom_payers = freecom_payerRepository.findAll();
        assertThat(freecom_payers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
