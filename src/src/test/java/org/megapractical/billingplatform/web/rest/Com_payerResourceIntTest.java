package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_payer;
import org.megapractical.billingplatform.repository.Com_payerRepository;
import org.megapractical.billingplatform.service.Com_payerService;

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
 * Test class for the Com_payerResource REST controller.
 *
 * @see Com_payerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_payerResourceIntTest {

    private static final String DEFAULT_EMITTER_BANK = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_EMITTER_BANK = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_TYPE_ACCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TYPE_ACCOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ACCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNT = new BigDecimal(2);
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";

    @Inject
    private Com_payerRepository com_payerRepository;

    @Inject
    private Com_payerService com_payerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_payerMockMvc;

    private Com_payer com_payer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_payerResource com_payerResource = new Com_payerResource();
        ReflectionTestUtils.setField(com_payerResource, "com_payerService", com_payerService);
        this.restCom_payerMockMvc = MockMvcBuilders.standaloneSetup(com_payerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_payer = new Com_payer();
        com_payer.setEmitter_bank(DEFAULT_EMITTER_BANK);
        com_payer.setName(DEFAULT_NAME);
        com_payer.setType_account(DEFAULT_TYPE_ACCOUNT);
        com_payer.setAccount(DEFAULT_ACCOUNT);
        com_payer.setRfc(DEFAULT_RFC);
    }

    @Test
    @Transactional
    public void createCom_payer() throws Exception {
        int databaseSizeBeforeCreate = com_payerRepository.findAll().size();

        // Create the Com_payer

        restCom_payerMockMvc.perform(post("/api/com-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_payer)))
                .andExpect(status().isCreated());

        // Validate the Com_payer in the database
        List<Com_payer> com_payers = com_payerRepository.findAll();
        assertThat(com_payers).hasSize(databaseSizeBeforeCreate + 1);
        Com_payer testCom_payer = com_payers.get(com_payers.size() - 1);
        assertThat(testCom_payer.getEmitter_bank()).isEqualTo(DEFAULT_EMITTER_BANK);
        assertThat(testCom_payer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCom_payer.getType_account()).isEqualTo(DEFAULT_TYPE_ACCOUNT);
        assertThat(testCom_payer.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCom_payer.getRfc()).isEqualTo(DEFAULT_RFC);
    }

    @Test
    @Transactional
    public void checkEmitter_bankIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_payerRepository.findAll().size();
        // set the field null
        com_payer.setEmitter_bank(null);

        // Create the Com_payer, which fails.

        restCom_payerMockMvc.perform(post("/api/com-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_payer)))
                .andExpect(status().isBadRequest());

        List<Com_payer> com_payers = com_payerRepository.findAll();
        assertThat(com_payers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_payerRepository.findAll().size();
        // set the field null
        com_payer.setName(null);

        // Create the Com_payer, which fails.

        restCom_payerMockMvc.perform(post("/api/com-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_payer)))
                .andExpect(status().isBadRequest());

        List<Com_payer> com_payers = com_payerRepository.findAll();
        assertThat(com_payers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkType_accountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_payerRepository.findAll().size();
        // set the field null
        com_payer.setType_account(null);

        // Create the Com_payer, which fails.

        restCom_payerMockMvc.perform(post("/api/com-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_payer)))
                .andExpect(status().isBadRequest());

        List<Com_payer> com_payers = com_payerRepository.findAll();
        assertThat(com_payers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_payerRepository.findAll().size();
        // set the field null
        com_payer.setAccount(null);

        // Create the Com_payer, which fails.

        restCom_payerMockMvc.perform(post("/api/com-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_payer)))
                .andExpect(status().isBadRequest());

        List<Com_payer> com_payers = com_payerRepository.findAll();
        assertThat(com_payers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_payerRepository.findAll().size();
        // set the field null
        com_payer.setRfc(null);

        // Create the Com_payer, which fails.

        restCom_payerMockMvc.perform(post("/api/com-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_payer)))
                .andExpect(status().isBadRequest());

        List<Com_payer> com_payers = com_payerRepository.findAll();
        assertThat(com_payers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_payers() throws Exception {
        // Initialize the database
        com_payerRepository.saveAndFlush(com_payer);

        // Get all the com_payers
        restCom_payerMockMvc.perform(get("/api/com-payers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_payer.getId().intValue())))
                .andExpect(jsonPath("$.[*].emitter_bank").value(hasItem(DEFAULT_EMITTER_BANK.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type_account").value(hasItem(DEFAULT_TYPE_ACCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())));
    }

    @Test
    @Transactional
    public void getCom_payer() throws Exception {
        // Initialize the database
        com_payerRepository.saveAndFlush(com_payer);

        // Get the com_payer
        restCom_payerMockMvc.perform(get("/api/com-payers/{id}", com_payer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_payer.getId().intValue()))
            .andExpect(jsonPath("$.emitter_bank").value(DEFAULT_EMITTER_BANK.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type_account").value(DEFAULT_TYPE_ACCOUNT.intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT.intValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_payer() throws Exception {
        // Get the com_payer
        restCom_payerMockMvc.perform(get("/api/com-payers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_payer() throws Exception {
        // Initialize the database
        com_payerService.save(com_payer);

        int databaseSizeBeforeUpdate = com_payerRepository.findAll().size();

        // Update the com_payer
        Com_payer updatedCom_payer = new Com_payer();
        updatedCom_payer.setId(com_payer.getId());
        updatedCom_payer.setEmitter_bank(UPDATED_EMITTER_BANK);
        updatedCom_payer.setName(UPDATED_NAME);
        updatedCom_payer.setType_account(UPDATED_TYPE_ACCOUNT);
        updatedCom_payer.setAccount(UPDATED_ACCOUNT);
        updatedCom_payer.setRfc(UPDATED_RFC);

        restCom_payerMockMvc.perform(put("/api/com-payers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_payer)))
                .andExpect(status().isOk());

        // Validate the Com_payer in the database
        List<Com_payer> com_payers = com_payerRepository.findAll();
        assertThat(com_payers).hasSize(databaseSizeBeforeUpdate);
        Com_payer testCom_payer = com_payers.get(com_payers.size() - 1);
        assertThat(testCom_payer.getEmitter_bank()).isEqualTo(UPDATED_EMITTER_BANK);
        assertThat(testCom_payer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCom_payer.getType_account()).isEqualTo(UPDATED_TYPE_ACCOUNT);
        assertThat(testCom_payer.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCom_payer.getRfc()).isEqualTo(UPDATED_RFC);
    }

    @Test
    @Transactional
    public void deleteCom_payer() throws Exception {
        // Initialize the database
        com_payerService.save(com_payer);

        int databaseSizeBeforeDelete = com_payerRepository.findAll().size();

        // Get the com_payer
        restCom_payerMockMvc.perform(delete("/api/com-payers/{id}", com_payer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_payer> com_payers = com_payerRepository.findAll();
        assertThat(com_payers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
