package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Package_transactions;
import org.megapractical.billingplatform.repository.Package_transactionsRepository;
import org.megapractical.billingplatform.service.Package_transactionsService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Package_transactionsResource REST controller.
 *
 * @see Package_transactionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Package_transactionsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_EXPIRY_TIME = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXPIRY_TIME = new BigDecimal(2);
    private static final String DEFAULT_UNIT_TIME = "AAAAA";
    private static final String UPDATED_UNIT_TIME = "BBBBB";

    @Inject
    private Package_transactionsRepository package_transactionsRepository;

    @Inject
    private Package_transactionsService package_transactionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPackage_transactionsMockMvc;

    private Package_transactions package_transactions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Package_transactionsResource package_transactionsResource = new Package_transactionsResource();
        ReflectionTestUtils.setField(package_transactionsResource, "package_transactionsService", package_transactionsService);
        this.restPackage_transactionsMockMvc = MockMvcBuilders.standaloneSetup(package_transactionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        package_transactions = new Package_transactions();
        package_transactions.setName(DEFAULT_NAME);
        package_transactions.setDescription(DEFAULT_DESCRIPTION);
        package_transactions.setPrice(DEFAULT_PRICE);
        package_transactions.setExpiry_time(DEFAULT_EXPIRY_TIME);
        package_transactions.setUnit_time(DEFAULT_UNIT_TIME);
    }

    @Test
    @Transactional
    public void createPackage_transactions() throws Exception {
        int databaseSizeBeforeCreate = package_transactionsRepository.findAll().size();

        // Create the Package_transactions

        restPackage_transactionsMockMvc.perform(post("/api/package-transactions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_transactions)))
                .andExpect(status().isCreated());

        // Validate the Package_transactions in the database
        List<Package_transactions> package_transactions = package_transactionsRepository.findAll();
        assertThat(package_transactions).hasSize(databaseSizeBeforeCreate + 1);
        Package_transactions testPackage_transactions = package_transactions.get(package_transactions.size() - 1);
        assertThat(testPackage_transactions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPackage_transactions.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPackage_transactions.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPackage_transactions.getExpiry_time()).isEqualTo(DEFAULT_EXPIRY_TIME);
        assertThat(testPackage_transactions.getUnit_time()).isEqualTo(DEFAULT_UNIT_TIME);
    }

    @Test
    @Transactional
    public void getAllPackage_transactions() throws Exception {
        // Initialize the database
        package_transactionsRepository.saveAndFlush(package_transactions);

        // Get all the package_transactions
        restPackage_transactionsMockMvc.perform(get("/api/package-transactions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(package_transactions.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].expiry_time").value(hasItem(DEFAULT_EXPIRY_TIME.intValue())))
                .andExpect(jsonPath("$.[*].unit_time").value(hasItem(DEFAULT_UNIT_TIME.toString())));
    }

    @Test
    @Transactional
    public void getPackage_transactions() throws Exception {
        // Initialize the database
        package_transactionsRepository.saveAndFlush(package_transactions);

        // Get the package_transactions
        restPackage_transactionsMockMvc.perform(get("/api/package-transactions/{id}", package_transactions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(package_transactions.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.expiry_time").value(DEFAULT_EXPIRY_TIME.intValue()))
            .andExpect(jsonPath("$.unit_time").value(DEFAULT_UNIT_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPackage_transactions() throws Exception {
        // Get the package_transactions
        restPackage_transactionsMockMvc.perform(get("/api/package-transactions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackage_transactions() throws Exception {
        // Initialize the database
        package_transactionsService.save(package_transactions);

        int databaseSizeBeforeUpdate = package_transactionsRepository.findAll().size();

        // Update the package_transactions
        Package_transactions updatedPackage_transactions = new Package_transactions();
        updatedPackage_transactions.setId(package_transactions.getId());
        updatedPackage_transactions.setName(UPDATED_NAME);
        updatedPackage_transactions.setDescription(UPDATED_DESCRIPTION);
        updatedPackage_transactions.setPrice(UPDATED_PRICE);
        updatedPackage_transactions.setExpiry_time(UPDATED_EXPIRY_TIME);
        updatedPackage_transactions.setUnit_time(UPDATED_UNIT_TIME);

        restPackage_transactionsMockMvc.perform(put("/api/package-transactions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPackage_transactions)))
                .andExpect(status().isOk());

        // Validate the Package_transactions in the database
        List<Package_transactions> package_transactions = package_transactionsRepository.findAll();
        assertThat(package_transactions).hasSize(databaseSizeBeforeUpdate);
        Package_transactions testPackage_transactions = package_transactions.get(package_transactions.size() - 1);
        assertThat(testPackage_transactions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPackage_transactions.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPackage_transactions.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPackage_transactions.getExpiry_time()).isEqualTo(UPDATED_EXPIRY_TIME);
        assertThat(testPackage_transactions.getUnit_time()).isEqualTo(UPDATED_UNIT_TIME);
    }

    @Test
    @Transactional
    public void deletePackage_transactions() throws Exception {
        // Initialize the database
        package_transactionsService.save(package_transactions);

        int databaseSizeBeforeDelete = package_transactionsRepository.findAll().size();

        // Get the package_transactions
        restPackage_transactionsMockMvc.perform(delete("/api/package-transactions/{id}", package_transactions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Package_transactions> package_transactions = package_transactionsRepository.findAll();
        assertThat(package_transactions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
