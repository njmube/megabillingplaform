package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.repository.Taxpayer_accountRepository;
import org.megapractical.billingplatform.service.Taxpayer_accountService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Taxpayer_accountResource REST controller.
 *
 * @see Taxpayer_accountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Taxpayer_accountResourceIntTest {

    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";
    private static final String DEFAULT_BUSSINES_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_BUSSINES_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_FAX = "AAAAA";
    private static final String UPDATED_FAX = "BBBBB";
    private static final String DEFAULT_PHONE_1 = "AAAAA";
    private static final String UPDATED_PHONE_1 = "BBBBB";
    private static final String DEFAULT_PHONE_2 = "AAAAA";
    private static final String UPDATED_PHONE_2 = "BBBBB";

    private static final Integer DEFAULT_ACCURACY = 1;
    private static final Integer UPDATED_ACCURACY = 2;

    @Inject
    private Taxpayer_accountRepository taxpayer_accountRepository;

    @Inject
    private Taxpayer_accountService taxpayer_accountService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxpayer_accountMockMvc;

    private Taxpayer_account taxpayer_account;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Taxpayer_accountResource taxpayer_accountResource = new Taxpayer_accountResource();
        ReflectionTestUtils.setField(taxpayer_accountResource, "taxpayer_accountService", taxpayer_accountService);
        this.restTaxpayer_accountMockMvc = MockMvcBuilders.standaloneSetup(taxpayer_accountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        taxpayer_account = new Taxpayer_account();
        taxpayer_account.setRfc(DEFAULT_RFC);
        taxpayer_account.setBussines_name(DEFAULT_BUSSINES_NAME);
        taxpayer_account.setEmail(DEFAULT_EMAIL);
        taxpayer_account.setFax(DEFAULT_FAX);
        taxpayer_account.setPhone1(DEFAULT_PHONE_1);
        taxpayer_account.setPhone2(DEFAULT_PHONE_2);
        taxpayer_account.setAccuracy(DEFAULT_ACCURACY);
    }

    @Test
    @Transactional
    public void createTaxpayer_account() throws Exception {
        int databaseSizeBeforeCreate = taxpayer_accountRepository.findAll().size();

        // Create the Taxpayer_account

        restTaxpayer_accountMockMvc.perform(post("/api/taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_account)))
                .andExpect(status().isCreated());

        // Validate the Taxpayer_account in the database
        List<Taxpayer_account> taxpayer_accounts = taxpayer_accountRepository.findAll();
        assertThat(taxpayer_accounts).hasSize(databaseSizeBeforeCreate + 1);
        Taxpayer_account testTaxpayer_account = taxpayer_accounts.get(taxpayer_accounts.size() - 1);
        assertThat(testTaxpayer_account.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testTaxpayer_account.getBussines_name()).isEqualTo(DEFAULT_BUSSINES_NAME);
        assertThat(testTaxpayer_account.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTaxpayer_account.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testTaxpayer_account.getPhone1()).isEqualTo(DEFAULT_PHONE_1);
        assertThat(testTaxpayer_account.getPhone2()).isEqualTo(DEFAULT_PHONE_2);
        assertThat(testTaxpayer_account.getAccuracy()).isEqualTo(DEFAULT_ACCURACY);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_accountRepository.findAll().size();
        // set the field null
        taxpayer_account.setRfc(null);

        // Create the Taxpayer_account, which fails.

        restTaxpayer_accountMockMvc.perform(post("/api/taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_account> taxpayer_accounts = taxpayer_accountRepository.findAll();
        assertThat(taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBussines_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_accountRepository.findAll().size();
        // set the field null
        taxpayer_account.setBussines_name(null);

        // Create the Taxpayer_account, which fails.

        restTaxpayer_accountMockMvc.perform(post("/api/taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_account> taxpayer_accounts = taxpayer_accountRepository.findAll();
        assertThat(taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_accountRepository.findAll().size();
        // set the field null
        taxpayer_account.setEmail(null);

        // Create the Taxpayer_account, which fails.

        restTaxpayer_accountMockMvc.perform(post("/api/taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_account> taxpayer_accounts = taxpayer_accountRepository.findAll();
        assertThat(taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccuracyIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_accountRepository.findAll().size();
        // set the field null
        taxpayer_account.setAccuracy(null);

        // Create the Taxpayer_account, which fails.

        restTaxpayer_accountMockMvc.perform(post("/api/taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_account)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_account> taxpayer_accounts = taxpayer_accountRepository.findAll();
        assertThat(taxpayer_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxpayer_accounts() throws Exception {
        // Initialize the database
        taxpayer_accountRepository.saveAndFlush(taxpayer_account);

        // Get all the taxpayer_accounts
        restTaxpayer_accountMockMvc.perform(get("/api/taxpayer-accounts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taxpayer_account.getId().intValue())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].bussines_name").value(hasItem(DEFAULT_BUSSINES_NAME.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
                .andExpect(jsonPath("$.[*].phone1").value(hasItem(DEFAULT_PHONE_1.toString())))
                .andExpect(jsonPath("$.[*].phone2").value(hasItem(DEFAULT_PHONE_2.toString())))
                .andExpect(jsonPath("$.[*].accuracy").value(hasItem(DEFAULT_ACCURACY)));
    }

    @Test
    @Transactional
    public void getTaxpayer_account() throws Exception {
        // Initialize the database
        taxpayer_accountRepository.saveAndFlush(taxpayer_account);

        // Get the taxpayer_account
        restTaxpayer_accountMockMvc.perform(get("/api/taxpayer-accounts/{id}", taxpayer_account.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taxpayer_account.getId().intValue()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.bussines_name").value(DEFAULT_BUSSINES_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.phone1").value(DEFAULT_PHONE_1.toString()))
            .andExpect(jsonPath("$.phone2").value(DEFAULT_PHONE_2.toString()))
            .andExpect(jsonPath("$.accuracy").value(DEFAULT_ACCURACY));
    }

    @Test
    @Transactional
    public void getNonExistingTaxpayer_account() throws Exception {
        // Get the taxpayer_account
        restTaxpayer_accountMockMvc.perform(get("/api/taxpayer-accounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxpayer_account() throws Exception {
        // Initialize the database
        taxpayer_accountService.save(taxpayer_account);

        int databaseSizeBeforeUpdate = taxpayer_accountRepository.findAll().size();

        // Update the taxpayer_account
        Taxpayer_account updatedTaxpayer_account = new Taxpayer_account();
        updatedTaxpayer_account.setId(taxpayer_account.getId());
        updatedTaxpayer_account.setRfc(UPDATED_RFC);
        updatedTaxpayer_account.setBussines_name(UPDATED_BUSSINES_NAME);
        updatedTaxpayer_account.setEmail(UPDATED_EMAIL);
        updatedTaxpayer_account.setFax(UPDATED_FAX);
        updatedTaxpayer_account.setPhone1(UPDATED_PHONE_1);
        updatedTaxpayer_account.setPhone2(UPDATED_PHONE_2);
        updatedTaxpayer_account.setAccuracy(UPDATED_ACCURACY);

        restTaxpayer_accountMockMvc.perform(put("/api/taxpayer-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTaxpayer_account)))
                .andExpect(status().isOk());

        // Validate the Taxpayer_account in the database
        List<Taxpayer_account> taxpayer_accounts = taxpayer_accountRepository.findAll();
        assertThat(taxpayer_accounts).hasSize(databaseSizeBeforeUpdate);
        Taxpayer_account testTaxpayer_account = taxpayer_accounts.get(taxpayer_accounts.size() - 1);
        assertThat(testTaxpayer_account.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testTaxpayer_account.getBussines_name()).isEqualTo(UPDATED_BUSSINES_NAME);
        assertThat(testTaxpayer_account.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTaxpayer_account.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testTaxpayer_account.getPhone1()).isEqualTo(UPDATED_PHONE_1);
        assertThat(testTaxpayer_account.getPhone2()).isEqualTo(UPDATED_PHONE_2);
        assertThat(testTaxpayer_account.getAccuracy()).isEqualTo(UPDATED_ACCURACY);
    }

    @Test
    @Transactional
    public void deleteTaxpayer_account() throws Exception {
        // Initialize the database
        taxpayer_accountService.save(taxpayer_account);

        int databaseSizeBeforeDelete = taxpayer_accountRepository.findAll().size();

        // Get the taxpayer_account
        restTaxpayer_accountMockMvc.perform(delete("/api/taxpayer-accounts/{id}", taxpayer_account.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Taxpayer_account> taxpayer_accounts = taxpayer_accountRepository.findAll();
        assertThat(taxpayer_accounts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
