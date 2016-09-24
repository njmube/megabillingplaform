package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Taxpayer_ftp_account;
import org.megapractical.billingplatform.repository.Taxpayer_ftp_accountRepository;
import org.megapractical.billingplatform.service.Taxpayer_ftp_accountService;

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
 * Test class for the Taxpayer_ftp_accountResource REST controller.
 *
 * @see Taxpayer_ftp_accountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Taxpayer_ftp_accountResourceIntTest {

    private static final String DEFAULT_SERVER_TYPE = "AAAAA";
    private static final String UPDATED_SERVER_TYPE = "BBBBB";
    private static final String DEFAULT_SERVER_NAME_IP = "AAAAA";
    private static final String UPDATED_SERVER_NAME_IP = "BBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;
    private static final String DEFAULT_USERNAME = "AAAAA";
    private static final String UPDATED_USERNAME = "BBBBB";
    private static final String DEFAULT_PASSWORD = "AAAAA";
    private static final String UPDATED_PASSWORD = "BBBBB";

    @Inject
    private Taxpayer_ftp_accountRepository taxpayer_ftp_accountRepository;

    @Inject
    private Taxpayer_ftp_accountService taxpayer_ftp_accountService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxpayer_ftp_accountMockMvc;

    private Taxpayer_ftp_account taxpayer_ftp_account;

    @PostConstruct
    public void setup() {
        /*
        MockitoAnnotations.initMocks(this);
        Taxpayer_ftp_accountResource taxpayer_ftp_accountResource = new Taxpayer_ftp_accountResource();
        ReflectionTestUtils.setField(taxpayer_ftp_accountResource, "taxpayer_ftp_accountService", taxpayer_ftp_accountService);
        this.restTaxpayer_ftp_accountMockMvc = MockMvcBuilders.standaloneSetup(taxpayer_ftp_accountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();*/
    }

    @Before
    public void initTest() {
        /*
        taxpayer_ftp_account = new Taxpayer_ftp_account();
        taxpayer_ftp_account.setServer_type(DEFAULT_SERVER_TYPE);
        taxpayer_ftp_account.setServer_name_ip(DEFAULT_SERVER_NAME_IP);
        taxpayer_ftp_account.setPort(DEFAULT_PORT);
        taxpayer_ftp_account.setUsername(DEFAULT_USERNAME);
        taxpayer_ftp_account.setPassword(DEFAULT_PASSWORD);
        */
    }

    @Test
    @Transactional
    public void createTaxpayer_ftp_account() throws Exception {
        /*
        int databaseSizeBeforeCreate = taxpayer_ftp_accountRepository.findAll().size();

        // Create the Taxpayer_ftp_account

        restTaxpayer_ftp_accountMockMvc.perform(post("/api/taxpayer-ftp-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_ftp_account)))
                .andExpect(status().isCreated());

        // Validate the Taxpayer_ftp_account in the database
        List<Taxpayer_ftp_account> taxpayer_ftp_accounts = taxpayer_ftp_accountRepository.findAll();
        assertThat(taxpayer_ftp_accounts).hasSize(databaseSizeBeforeCreate + 1);
        Taxpayer_ftp_account testTaxpayer_ftp_account = taxpayer_ftp_accounts.get(taxpayer_ftp_accounts.size() - 1);
        assertThat(testTaxpayer_ftp_account.getServer_type()).isEqualTo(DEFAULT_SERVER_TYPE);
        assertThat(testTaxpayer_ftp_account.getServer_name_ip()).isEqualTo(DEFAULT_SERVER_NAME_IP);
        assertThat(testTaxpayer_ftp_account.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testTaxpayer_ftp_account.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testTaxpayer_ftp_account.getPassword()).isEqualTo(DEFAULT_PASSWORD);*/
    }

    @Test
    @Transactional
    public void checkServer_typeIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = taxpayer_ftp_accountRepository.findAll().size();
        // set the field null
        taxpayer_ftp_account.setServer_type(null);

        // Create the Taxpayer_ftp_account, which fails.

        restTaxpayer_ftp_accountMockMvc.perform(post("/api/taxpayer-ftp-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_ftp_account)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_ftp_account> taxpayer_ftp_accounts = taxpayer_ftp_accountRepository.findAll();
        assertThat(taxpayer_ftp_accounts).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkServer_name_ipIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = taxpayer_ftp_accountRepository.findAll().size();
        // set the field null
        taxpayer_ftp_account.setServer_name_ip(null);

        // Create the Taxpayer_ftp_account, which fails.

        restTaxpayer_ftp_accountMockMvc.perform(post("/api/taxpayer-ftp-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_ftp_account)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_ftp_account> taxpayer_ftp_accounts = taxpayer_ftp_accountRepository.findAll();
        assertThat(taxpayer_ftp_accounts).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkPortIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = taxpayer_ftp_accountRepository.findAll().size();
        // set the field null
        taxpayer_ftp_account.setPort(null);

        // Create the Taxpayer_ftp_account, which fails.

        restTaxpayer_ftp_accountMockMvc.perform(post("/api/taxpayer-ftp-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_ftp_account)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_ftp_account> taxpayer_ftp_accounts = taxpayer_ftp_accountRepository.findAll();
        assertThat(taxpayer_ftp_accounts).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void getAllTaxpayer_ftp_accounts() throws Exception {
        // Initialize the database
        /*
        taxpayer_ftp_accountRepository.saveAndFlush(taxpayer_ftp_account);

        // Get all the taxpayer_ftp_accounts
        restTaxpayer_ftp_accountMockMvc.perform(get("/api/taxpayer-ftp-accounts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taxpayer_ftp_account.getId().intValue())))
                .andExpect(jsonPath("$.[*].server_type").value(hasItem(DEFAULT_SERVER_TYPE.toString())))
                .andExpect(jsonPath("$.[*].server_name_ip").value(hasItem(DEFAULT_SERVER_NAME_IP.toString())))
                .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
                .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));*/
    }

    @Test
    @Transactional
    public void getTaxpayer_ftp_account() throws Exception {
        // Initialize the database
        /*
        taxpayer_ftp_accountRepository.saveAndFlush(taxpayer_ftp_account);

        // Get the taxpayer_ftp_account
        restTaxpayer_ftp_accountMockMvc.perform(get("/api/taxpayer-ftp-accounts/{id}", taxpayer_ftp_account.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taxpayer_ftp_account.getId().intValue()))
            .andExpect(jsonPath("$.server_type").value(DEFAULT_SERVER_TYPE.toString()))
            .andExpect(jsonPath("$.server_name_ip").value(DEFAULT_SERVER_NAME_IP.toString()))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));*/
    }

    @Test
    @Transactional
    public void getNonExistingTaxpayer_ftp_account() throws Exception {
        /*
        // Get the taxpayer_ftp_account
        restTaxpayer_ftp_accountMockMvc.perform(get("/api/taxpayer-ftp-accounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());*/
    }

    @Test
    @Transactional
    public void updateTaxpayer_ftp_account() throws Exception {
        // Initialize the database
        /*
        taxpayer_ftp_accountService.save(taxpayer_ftp_account);

        int databaseSizeBeforeUpdate = taxpayer_ftp_accountRepository.findAll().size();

        // Update the taxpayer_ftp_account
        Taxpayer_ftp_account updatedTaxpayer_ftp_account = new Taxpayer_ftp_account();
        updatedTaxpayer_ftp_account.setId(taxpayer_ftp_account.getId());
        updatedTaxpayer_ftp_account.setServer_type(UPDATED_SERVER_TYPE);
        updatedTaxpayer_ftp_account.setServer_name_ip(UPDATED_SERVER_NAME_IP);
        updatedTaxpayer_ftp_account.setPort(UPDATED_PORT);
        updatedTaxpayer_ftp_account.setUsername(UPDATED_USERNAME);
        updatedTaxpayer_ftp_account.setPassword(UPDATED_PASSWORD);

        restTaxpayer_ftp_accountMockMvc.perform(put("/api/taxpayer-ftp-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTaxpayer_ftp_account)))
                .andExpect(status().isOk());

        // Validate the Taxpayer_ftp_account in the database
        List<Taxpayer_ftp_account> taxpayer_ftp_accounts = taxpayer_ftp_accountRepository.findAll();
        assertThat(taxpayer_ftp_accounts).hasSize(databaseSizeBeforeUpdate);
        Taxpayer_ftp_account testTaxpayer_ftp_account = taxpayer_ftp_accounts.get(taxpayer_ftp_accounts.size() - 1);
        assertThat(testTaxpayer_ftp_account.getServer_type()).isEqualTo(UPDATED_SERVER_TYPE);
        assertThat(testTaxpayer_ftp_account.getServer_name_ip()).isEqualTo(UPDATED_SERVER_NAME_IP);
        assertThat(testTaxpayer_ftp_account.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testTaxpayer_ftp_account.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testTaxpayer_ftp_account.getPassword()).isEqualTo(UPDATED_PASSWORD);*/
    }

    @Test
    @Transactional
    public void deleteTaxpayer_ftp_account() throws Exception {
        // Initialize the database
        /*
        taxpayer_ftp_accountService.save(taxpayer_ftp_account);

        int databaseSizeBeforeDelete = taxpayer_ftp_accountRepository.findAll().size();

        // Get the taxpayer_ftp_account
        restTaxpayer_ftp_accountMockMvc.perform(delete("/api/taxpayer-ftp-accounts/{id}", taxpayer_ftp_account.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Taxpayer_ftp_account> taxpayer_ftp_accounts = taxpayer_ftp_accountRepository.findAll();
        assertThat(taxpayer_ftp_accounts).hasSize(databaseSizeBeforeDelete - 1);*/
    }
}
