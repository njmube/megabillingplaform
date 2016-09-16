package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Taxpayer_mail_accounts;
import org.megapractical.billingplatform.repository.Taxpayer_mail_accountsRepository;
import org.megapractical.billingplatform.service.Taxpayer_mail_accountsService;

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
 * Test class for the Taxpayer_mail_accountsResource REST controller.
 *
 * @see Taxpayer_mail_accountsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Taxpayer_mail_accountsResourceIntTest {

    private static final String DEFAULT_INCOMING_MAIL_TYPE = "AAAAA";
    private static final String UPDATED_INCOMING_MAIL_TYPE = "BBBBB";
    private static final String DEFAULT_INCOMING_SERVER_NAME = "AAAAA";
    private static final String UPDATED_INCOMING_SERVER_NAME = "BBBBB";

    private static final Integer DEFAULT_INCOMING_PORT = 1;
    private static final Integer UPDATED_INCOMING_PORT = 2;
    private static final String DEFAULT_USERNAME = "AAAAA";
    private static final String UPDATED_USERNAME = "BBBBB";
    private static final String DEFAULT_PASSWORD = "AAAAA";
    private static final String UPDATED_PASSWORD = "BBBBB";

    @Inject
    private Taxpayer_mail_accountsRepository taxpayer_mail_accountsRepository;

    @Inject
    private Taxpayer_mail_accountsService taxpayer_mail_accountsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxpayer_mail_accountsMockMvc;

    private Taxpayer_mail_accounts taxpayer_mail_accounts;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Taxpayer_mail_accountsResource taxpayer_mail_accountsResource = new Taxpayer_mail_accountsResource();
        ReflectionTestUtils.setField(taxpayer_mail_accountsResource, "taxpayer_mail_accountsService", taxpayer_mail_accountsService);
        this.restTaxpayer_mail_accountsMockMvc = MockMvcBuilders.standaloneSetup(taxpayer_mail_accountsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        taxpayer_mail_accounts = new Taxpayer_mail_accounts();
        taxpayer_mail_accounts.setIncoming_mail_type(DEFAULT_INCOMING_MAIL_TYPE);
        taxpayer_mail_accounts.setIncoming_server_name(DEFAULT_INCOMING_SERVER_NAME);
        taxpayer_mail_accounts.setIncoming_port(DEFAULT_INCOMING_PORT);
        taxpayer_mail_accounts.setUsername(DEFAULT_USERNAME);
        taxpayer_mail_accounts.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createTaxpayer_mail_accounts() throws Exception {
        int databaseSizeBeforeCreate = taxpayer_mail_accountsRepository.findAll().size();

        // Create the Taxpayer_mail_accounts

        restTaxpayer_mail_accountsMockMvc.perform(post("/api/taxpayer-mail-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_mail_accounts)))
                .andExpect(status().isCreated());

        // Validate the Taxpayer_mail_accounts in the database
        List<Taxpayer_mail_accounts> taxpayer_mail_accounts = taxpayer_mail_accountsRepository.findAll();
        assertThat(taxpayer_mail_accounts).hasSize(databaseSizeBeforeCreate + 1);
        Taxpayer_mail_accounts testTaxpayer_mail_accounts = taxpayer_mail_accounts.get(taxpayer_mail_accounts.size() - 1);
        assertThat(testTaxpayer_mail_accounts.getIncoming_mail_type()).isEqualTo(DEFAULT_INCOMING_MAIL_TYPE);
        assertThat(testTaxpayer_mail_accounts.getIncoming_server_name()).isEqualTo(DEFAULT_INCOMING_SERVER_NAME);
        assertThat(testTaxpayer_mail_accounts.getIncoming_port()).isEqualTo(DEFAULT_INCOMING_PORT);
        assertThat(testTaxpayer_mail_accounts.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testTaxpayer_mail_accounts.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void checkIncoming_mail_typeIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_mail_accountsRepository.findAll().size();
        // set the field null
        taxpayer_mail_accounts.setIncoming_mail_type(null);

        // Create the Taxpayer_mail_accounts, which fails.

        restTaxpayer_mail_accountsMockMvc.perform(post("/api/taxpayer-mail-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_mail_accounts)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_mail_accounts> taxpayer_mail_accounts = taxpayer_mail_accountsRepository.findAll();
        assertThat(taxpayer_mail_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncoming_server_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_mail_accountsRepository.findAll().size();
        // set the field null
        taxpayer_mail_accounts.setIncoming_server_name(null);

        // Create the Taxpayer_mail_accounts, which fails.

        restTaxpayer_mail_accountsMockMvc.perform(post("/api/taxpayer-mail-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_mail_accounts)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_mail_accounts> taxpayer_mail_accounts = taxpayer_mail_accountsRepository.findAll();
        assertThat(taxpayer_mail_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncoming_portIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_mail_accountsRepository.findAll().size();
        // set the field null
        taxpayer_mail_accounts.setIncoming_port(null);

        // Create the Taxpayer_mail_accounts, which fails.

        restTaxpayer_mail_accountsMockMvc.perform(post("/api/taxpayer-mail-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_mail_accounts)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_mail_accounts> taxpayer_mail_accounts = taxpayer_mail_accountsRepository.findAll();
        assertThat(taxpayer_mail_accounts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxpayer_mail_accounts() throws Exception {
        // Initialize the database
        taxpayer_mail_accountsRepository.saveAndFlush(taxpayer_mail_accounts);

        // Get all the taxpayer_mail_accounts
        restTaxpayer_mail_accountsMockMvc.perform(get("/api/taxpayer-mail-accounts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taxpayer_mail_accounts.getId().intValue())))
                .andExpect(jsonPath("$.[*].incoming_mail_type").value(hasItem(DEFAULT_INCOMING_MAIL_TYPE.toString())))
                .andExpect(jsonPath("$.[*].incoming_server_name").value(hasItem(DEFAULT_INCOMING_SERVER_NAME.toString())))
                .andExpect(jsonPath("$.[*].incoming_port").value(hasItem(DEFAULT_INCOMING_PORT)))
                .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getTaxpayer_mail_accounts() throws Exception {
        // Initialize the database
        taxpayer_mail_accountsRepository.saveAndFlush(taxpayer_mail_accounts);

        // Get the taxpayer_mail_accounts
        restTaxpayer_mail_accountsMockMvc.perform(get("/api/taxpayer-mail-accounts/{id}", taxpayer_mail_accounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taxpayer_mail_accounts.getId().intValue()))
            .andExpect(jsonPath("$.incoming_mail_type").value(DEFAULT_INCOMING_MAIL_TYPE.toString()))
            .andExpect(jsonPath("$.incoming_server_name").value(DEFAULT_INCOMING_SERVER_NAME.toString()))
            .andExpect(jsonPath("$.incoming_port").value(DEFAULT_INCOMING_PORT))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxpayer_mail_accounts() throws Exception {
        // Get the taxpayer_mail_accounts
        restTaxpayer_mail_accountsMockMvc.perform(get("/api/taxpayer-mail-accounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxpayer_mail_accounts() throws Exception {
        // Initialize the database
        taxpayer_mail_accountsService.save(taxpayer_mail_accounts);

        int databaseSizeBeforeUpdate = taxpayer_mail_accountsRepository.findAll().size();

        // Update the taxpayer_mail_accounts
        Taxpayer_mail_accounts updatedTaxpayer_mail_accounts = new Taxpayer_mail_accounts();
        updatedTaxpayer_mail_accounts.setId(taxpayer_mail_accounts.getId());
        updatedTaxpayer_mail_accounts.setIncoming_mail_type(UPDATED_INCOMING_MAIL_TYPE);
        updatedTaxpayer_mail_accounts.setIncoming_server_name(UPDATED_INCOMING_SERVER_NAME);
        updatedTaxpayer_mail_accounts.setIncoming_port(UPDATED_INCOMING_PORT);
        updatedTaxpayer_mail_accounts.setUsername(UPDATED_USERNAME);
        updatedTaxpayer_mail_accounts.setPassword(UPDATED_PASSWORD);

        restTaxpayer_mail_accountsMockMvc.perform(put("/api/taxpayer-mail-accounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTaxpayer_mail_accounts)))
                .andExpect(status().isOk());

        // Validate the Taxpayer_mail_accounts in the database
        List<Taxpayer_mail_accounts> taxpayer_mail_accounts = taxpayer_mail_accountsRepository.findAll();
        assertThat(taxpayer_mail_accounts).hasSize(databaseSizeBeforeUpdate);
        Taxpayer_mail_accounts testTaxpayer_mail_accounts = taxpayer_mail_accounts.get(taxpayer_mail_accounts.size() - 1);
        assertThat(testTaxpayer_mail_accounts.getIncoming_mail_type()).isEqualTo(UPDATED_INCOMING_MAIL_TYPE);
        assertThat(testTaxpayer_mail_accounts.getIncoming_server_name()).isEqualTo(UPDATED_INCOMING_SERVER_NAME);
        assertThat(testTaxpayer_mail_accounts.getIncoming_port()).isEqualTo(UPDATED_INCOMING_PORT);
        assertThat(testTaxpayer_mail_accounts.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testTaxpayer_mail_accounts.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void deleteTaxpayer_mail_accounts() throws Exception {
        // Initialize the database
        taxpayer_mail_accountsService.save(taxpayer_mail_accounts);

        int databaseSizeBeforeDelete = taxpayer_mail_accountsRepository.findAll().size();

        // Get the taxpayer_mail_accounts
        restTaxpayer_mail_accountsMockMvc.perform(delete("/api/taxpayer-mail-accounts/{id}", taxpayer_mail_accounts.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Taxpayer_mail_accounts> taxpayer_mail_accounts = taxpayer_mail_accountsRepository.findAll();
        assertThat(taxpayer_mail_accounts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
