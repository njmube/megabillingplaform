package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Type_transaction;
import org.megapractical.billingplatform.repository.Type_transactionRepository;
import org.megapractical.billingplatform.service.Type_transactionService;

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
 * Test class for the Type_transactionResource REST controller.
 *
 * @see Type_transactionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Type_transactionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Type_transactionRepository type_transactionRepository;

    @Inject
    private Type_transactionService type_transactionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restType_transactionMockMvc;

    private Type_transaction type_transaction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Type_transactionResource type_transactionResource = new Type_transactionResource();
        ReflectionTestUtils.setField(type_transactionResource, "type_transactionService", type_transactionService);
        this.restType_transactionMockMvc = MockMvcBuilders.standaloneSetup(type_transactionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        type_transaction = new Type_transaction();
        type_transaction.setName(DEFAULT_NAME);
        type_transaction.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createType_transaction() throws Exception {
        int databaseSizeBeforeCreate = type_transactionRepository.findAll().size();

        // Create the Type_transaction

        restType_transactionMockMvc.perform(post("/api/type-transactions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_transaction)))
                .andExpect(status().isCreated());

        // Validate the Type_transaction in the database
        List<Type_transaction> type_transactions = type_transactionRepository.findAll();
        assertThat(type_transactions).hasSize(databaseSizeBeforeCreate + 1);
        Type_transaction testType_transaction = type_transactions.get(type_transactions.size() - 1);
        assertThat(testType_transaction.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testType_transaction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = type_transactionRepository.findAll().size();
        // set the field null
        type_transaction.setName(null);

        // Create the Type_transaction, which fails.

        restType_transactionMockMvc.perform(post("/api/type-transactions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(type_transaction)))
                .andExpect(status().isBadRequest());

        List<Type_transaction> type_transactions = type_transactionRepository.findAll();
        assertThat(type_transactions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllType_transactions() throws Exception {
        // Initialize the database
        type_transactionRepository.saveAndFlush(type_transaction);

        // Get all the type_transactions
        restType_transactionMockMvc.perform(get("/api/type-transactions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(type_transaction.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getType_transaction() throws Exception {
        // Initialize the database
        type_transactionRepository.saveAndFlush(type_transaction);

        // Get the type_transaction
        restType_transactionMockMvc.perform(get("/api/type-transactions/{id}", type_transaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(type_transaction.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingType_transaction() throws Exception {
        // Get the type_transaction
        restType_transactionMockMvc.perform(get("/api/type-transactions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateType_transaction() throws Exception {
        // Initialize the database
        type_transactionService.save(type_transaction);

        int databaseSizeBeforeUpdate = type_transactionRepository.findAll().size();

        // Update the type_transaction
        Type_transaction updatedType_transaction = new Type_transaction();
        updatedType_transaction.setId(type_transaction.getId());
        updatedType_transaction.setName(UPDATED_NAME);
        updatedType_transaction.setDescription(UPDATED_DESCRIPTION);

        restType_transactionMockMvc.perform(put("/api/type-transactions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedType_transaction)))
                .andExpect(status().isOk());

        // Validate the Type_transaction in the database
        List<Type_transaction> type_transactions = type_transactionRepository.findAll();
        assertThat(type_transactions).hasSize(databaseSizeBeforeUpdate);
        Type_transaction testType_transaction = type_transactions.get(type_transactions.size() - 1);
        assertThat(testType_transaction.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testType_transaction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteType_transaction() throws Exception {
        // Initialize the database
        type_transactionService.save(type_transaction);

        int databaseSizeBeforeDelete = type_transactionRepository.findAll().size();

        // Get the type_transaction
        restType_transactionMockMvc.perform(delete("/api/type-transactions/{id}", type_transaction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Type_transaction> type_transactions = type_transactionRepository.findAll();
        assertThat(type_transactions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
