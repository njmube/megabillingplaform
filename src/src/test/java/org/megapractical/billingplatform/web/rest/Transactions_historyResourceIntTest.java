package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Transactions_history;
import org.megapractical.billingplatform.repository.Transactions_historyRepository;
import org.megapractical.billingplatform.service.Transactions_historyService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Transactions_historyResource REST controller.
 *
 * @see Transactions_historyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Transactions_historyResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATE_TRANSACTION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_TRANSACTION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_TRANSACTION_STR = dateTimeFormatter.format(DEFAULT_DATE_TRANSACTION);
    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Transactions_historyRepository transactions_historyRepository;

    @Inject
    private Transactions_historyService transactions_historyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTransactions_historyMockMvc;

    private Transactions_history transactions_history;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Transactions_historyResource transactions_historyResource = new Transactions_historyResource();
        ReflectionTestUtils.setField(transactions_historyResource, "transactions_historyService", transactions_historyService);
        this.restTransactions_historyMockMvc = MockMvcBuilders.standaloneSetup(transactions_historyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        transactions_history = new Transactions_history();
        transactions_history.setDate_transaction(DEFAULT_DATE_TRANSACTION);
        transactions_history.setQuantity(DEFAULT_QUANTITY);
        transactions_history.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTransactions_history() throws Exception {
        int databaseSizeBeforeCreate = transactions_historyRepository.findAll().size();

        // Create the Transactions_history

        restTransactions_historyMockMvc.perform(post("/api/transactions-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transactions_history)))
                .andExpect(status().isCreated());

        // Validate the Transactions_history in the database
        List<Transactions_history> transactions_histories = transactions_historyRepository.findAll();
        assertThat(transactions_histories).hasSize(databaseSizeBeforeCreate + 1);
        Transactions_history testTransactions_history = transactions_histories.get(transactions_histories.size() - 1);
        assertThat(testTransactions_history.getDate_transaction()).isEqualTo(DEFAULT_DATE_TRANSACTION);
        assertThat(testTransactions_history.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTransactions_history.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkDate_transactionIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactions_historyRepository.findAll().size();
        // set the field null
        transactions_history.setDate_transaction(null);

        // Create the Transactions_history, which fails.

        restTransactions_historyMockMvc.perform(post("/api/transactions-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transactions_history)))
                .andExpect(status().isBadRequest());

        List<Transactions_history> transactions_histories = transactions_historyRepository.findAll();
        assertThat(transactions_histories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactions_histories() throws Exception {
        // Initialize the database
        transactions_historyRepository.saveAndFlush(transactions_history);

        // Get all the transactions_histories
        restTransactions_historyMockMvc.perform(get("/api/transactions-histories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(transactions_history.getId().intValue())))
                .andExpect(jsonPath("$.[*].date_transaction").value(hasItem(DEFAULT_DATE_TRANSACTION_STR)))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTransactions_history() throws Exception {
        // Initialize the database
        transactions_historyRepository.saveAndFlush(transactions_history);

        // Get the transactions_history
        restTransactions_historyMockMvc.perform(get("/api/transactions-histories/{id}", transactions_history.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(transactions_history.getId().intValue()))
            .andExpect(jsonPath("$.date_transaction").value(DEFAULT_DATE_TRANSACTION_STR))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTransactions_history() throws Exception {
        // Get the transactions_history
        restTransactions_historyMockMvc.perform(get("/api/transactions-histories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactions_history() throws Exception {
        // Initialize the database
        transactions_historyService.save(transactions_history);

        int databaseSizeBeforeUpdate = transactions_historyRepository.findAll().size();

        // Update the transactions_history
        Transactions_history updatedTransactions_history = new Transactions_history();
        updatedTransactions_history.setId(transactions_history.getId());
        updatedTransactions_history.setDate_transaction(UPDATED_DATE_TRANSACTION);
        updatedTransactions_history.setQuantity(UPDATED_QUANTITY);
        updatedTransactions_history.setDescription(UPDATED_DESCRIPTION);

        restTransactions_historyMockMvc.perform(put("/api/transactions-histories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTransactions_history)))
                .andExpect(status().isOk());

        // Validate the Transactions_history in the database
        List<Transactions_history> transactions_histories = transactions_historyRepository.findAll();
        assertThat(transactions_histories).hasSize(databaseSizeBeforeUpdate);
        Transactions_history testTransactions_history = transactions_histories.get(transactions_histories.size() - 1);
        assertThat(testTransactions_history.getDate_transaction()).isEqualTo(UPDATED_DATE_TRANSACTION);
        assertThat(testTransactions_history.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTransactions_history.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteTransactions_history() throws Exception {
        // Initialize the database
        transactions_historyService.save(transactions_history);

        int databaseSizeBeforeDelete = transactions_historyRepository.findAll().size();

        // Get the transactions_history
        restTransactions_historyMockMvc.perform(delete("/api/transactions-histories/{id}", transactions_history.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Transactions_history> transactions_histories = transactions_historyRepository.findAll();
        assertThat(transactions_histories).hasSize(databaseSizeBeforeDelete - 1);
    }
}
