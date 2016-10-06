package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Taxpayer_transactions;
import org.megapractical.billingplatform.repository.Taxpayer_transactionsRepository;
import org.megapractical.billingplatform.service.Taxpayer_transactionsService;

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
 * Test class for the Taxpayer_transactionsResource REST controller.
 *
 * @see Taxpayer_transactionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Taxpayer_transactionsResourceIntTest {


    private static final Integer DEFAULT_TRANSACTIONS_AVAILABLE = 1;
    private static final Integer UPDATED_TRANSACTIONS_AVAILABLE = 2;

    private static final Integer DEFAULT_TRANSACTIONS_SPENT = 1;
    private static final Integer UPDATED_TRANSACTIONS_SPENT = 2;

    @Inject
    private Taxpayer_transactionsRepository taxpayer_transactionsRepository;

    @Inject
    private Taxpayer_transactionsService taxpayer_transactionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxpayer_transactionsMockMvc;

    private Taxpayer_transactions taxpayer_transactions;

    @PostConstruct
    public void setup() {
        /*
        MockitoAnnotations.initMocks(this);
        Taxpayer_transactionsResource taxpayer_transactionsResource = new Taxpayer_transactionsResource();
        ReflectionTestUtils.setField(taxpayer_transactionsResource, "taxpayer_transactionsService", taxpayer_transactionsService);
        this.restTaxpayer_transactionsMockMvc = MockMvcBuilders.standaloneSetup(taxpayer_transactionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();*/
    }

    @Before
    public void initTest() {
        /*
        taxpayer_transactions = new Taxpayer_transactions();
        taxpayer_transactions.setTransactions_available(DEFAULT_TRANSACTIONS_AVAILABLE);
        taxpayer_transactions.setTransactions_spent(DEFAULT_TRANSACTIONS_SPENT);*/
    }

    @Test
    @Transactional
    public void createTaxpayer_transactions() throws Exception {
        /*
        int databaseSizeBeforeCreate = taxpayer_transactionsRepository.findAll().size();

        // Create the Taxpayer_transactions

        restTaxpayer_transactionsMockMvc.perform(post("/api/taxpayer-transactions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_transactions)))
                .andExpect(status().isCreated());

        // Validate the Taxpayer_transactions in the database
        List<Taxpayer_transactions> taxpayer_transactions = taxpayer_transactionsRepository.findAll();
        assertThat(taxpayer_transactions).hasSize(databaseSizeBeforeCreate + 1);
        Taxpayer_transactions testTaxpayer_transactions = taxpayer_transactions.get(taxpayer_transactions.size() - 1);
        assertThat(testTaxpayer_transactions.getTransactions_available()).isEqualTo(DEFAULT_TRANSACTIONS_AVAILABLE);
        assertThat(testTaxpayer_transactions.getTransactions_spent()).isEqualTo(DEFAULT_TRANSACTIONS_SPENT);*/
    }

    @Test
    @Transactional
    public void checkTransactions_availableIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = taxpayer_transactionsRepository.findAll().size();
        // set the field null
        taxpayer_transactions.setTransactions_available(null);

        // Create the Taxpayer_transactions, which fails.

        restTaxpayer_transactionsMockMvc.perform(post("/api/taxpayer-transactions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_transactions)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_transactions> taxpayer_transactions = taxpayer_transactionsRepository.findAll();
        assertThat(taxpayer_transactions).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkTransactions_spentIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = taxpayer_transactionsRepository.findAll().size();
        // set the field null
        taxpayer_transactions.setTransactions_spent(null);

        // Create the Taxpayer_transactions, which fails.

        restTaxpayer_transactionsMockMvc.perform(post("/api/taxpayer-transactions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_transactions)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_transactions> taxpayer_transactions = taxpayer_transactionsRepository.findAll();
        assertThat(taxpayer_transactions).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void getAllTaxpayer_transactions() throws Exception {
        // Initialize the database
        /*
        taxpayer_transactionsRepository.saveAndFlush(taxpayer_transactions);

        // Get all the taxpayer_transactions
        restTaxpayer_transactionsMockMvc.perform(get("/api/taxpayer-transactions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taxpayer_transactions.getId().intValue())))
                .andExpect(jsonPath("$.[*].transactions_available").value(hasItem(DEFAULT_TRANSACTIONS_AVAILABLE)))
                .andExpect(jsonPath("$.[*].transactions_spent").value(hasItem(DEFAULT_TRANSACTIONS_SPENT)));*/
    }

    @Test
    @Transactional
    public void getTaxpayer_transactions() throws Exception {
        // Initialize the database
        /*
        taxpayer_transactionsRepository.saveAndFlush(taxpayer_transactions);

        // Get the taxpayer_transactions
        restTaxpayer_transactionsMockMvc.perform(get("/api/taxpayer-transactions/{id}", taxpayer_transactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taxpayer_transactions.getId().intValue()))
            .andExpect(jsonPath("$.transactions_available").value(DEFAULT_TRANSACTIONS_AVAILABLE))
            .andExpect(jsonPath("$.transactions_spent").value(DEFAULT_TRANSACTIONS_SPENT));*/
    }

    @Test
    @Transactional
    public void getNonExistingTaxpayer_transactions() throws Exception {
        // Get the taxpayer_transactions
        /*
        restTaxpayer_transactionsMockMvc.perform(get("/api/taxpayer-transactions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());*/
    }

    @Test
    @Transactional
    public void updateTaxpayer_transactions() throws Exception {
        // Initialize the database
        /*
        taxpayer_transactionsService.save(taxpayer_transactions);

        int databaseSizeBeforeUpdate = taxpayer_transactionsRepository.findAll().size();

        // Update the taxpayer_transactions
        Taxpayer_transactions updatedTaxpayer_transactions = new Taxpayer_transactions();
        updatedTaxpayer_transactions.setId(taxpayer_transactions.getId());
        updatedTaxpayer_transactions.setTransactions_available(UPDATED_TRANSACTIONS_AVAILABLE);
        updatedTaxpayer_transactions.setTransactions_spent(UPDATED_TRANSACTIONS_SPENT);

        restTaxpayer_transactionsMockMvc.perform(put("/api/taxpayer-transactions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTaxpayer_transactions)))
                .andExpect(status().isOk());

        // Validate the Taxpayer_transactions in the database
        List<Taxpayer_transactions> taxpayer_transactions = taxpayer_transactionsRepository.findAll();
        assertThat(taxpayer_transactions).hasSize(databaseSizeBeforeUpdate);
        Taxpayer_transactions testTaxpayer_transactions = taxpayer_transactions.get(taxpayer_transactions.size() - 1);
        assertThat(testTaxpayer_transactions.getTransactions_available()).isEqualTo(UPDATED_TRANSACTIONS_AVAILABLE);
        assertThat(testTaxpayer_transactions.getTransactions_spent()).isEqualTo(UPDATED_TRANSACTIONS_SPENT);*/
    }

    @Test
    @Transactional
    public void deleteTaxpayer_transactions() throws Exception {
        /*
        // Initialize the database
        taxpayer_transactionsService.save(taxpayer_transactions);

        int databaseSizeBeforeDelete = taxpayer_transactionsRepository.findAll().size();

        // Get the taxpayer_transactions
        restTaxpayer_transactionsMockMvc.perform(delete("/api/taxpayer-transactions/{id}", taxpayer_transactions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Taxpayer_transactions> taxpayer_transactions = taxpayer_transactionsRepository.findAll();
        assertThat(taxpayer_transactions).hasSize(databaseSizeBeforeDelete - 1);*/
    }
}
