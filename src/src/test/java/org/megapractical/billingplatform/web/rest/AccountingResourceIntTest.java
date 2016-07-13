package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Accounting;
import org.megapractical.billingplatform.repository.AccountingRepository;
import org.megapractical.billingplatform.service.AccountingService;

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
 * Test class for the AccountingResource REST controller.
 *
 * @see AccountingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class AccountingResourceIntTest {


    private static final Integer DEFAULT_KEYACCOUNTING = 1;
    private static final Integer UPDATED_KEYACCOUNTING = 2;

    @Inject
    private AccountingRepository accountingRepository;

    @Inject
    private AccountingService accountingService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAccountingMockMvc;

    private Accounting accounting;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccountingResource accountingResource = new AccountingResource();
        ReflectionTestUtils.setField(accountingResource, "accountingService", accountingService);
        this.restAccountingMockMvc = MockMvcBuilders.standaloneSetup(accountingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        accounting = new Accounting();
        accounting.setKeyaccounting(DEFAULT_KEYACCOUNTING);
    }

    @Test
    @Transactional
    public void createAccounting() throws Exception {
        int databaseSizeBeforeCreate = accountingRepository.findAll().size();

        // Create the Accounting

        restAccountingMockMvc.perform(post("/api/accountings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accounting)))
                .andExpect(status().isCreated());

        // Validate the Accounting in the database
        List<Accounting> accountings = accountingRepository.findAll();
        assertThat(accountings).hasSize(databaseSizeBeforeCreate + 1);
        Accounting testAccounting = accountings.get(accountings.size() - 1);
        assertThat(testAccounting.getKeyaccounting()).isEqualTo(DEFAULT_KEYACCOUNTING);
    }

    @Test
    @Transactional
    public void checkKeyaccountingIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountingRepository.findAll().size();
        // set the field null
        accounting.setKeyaccounting(null);

        // Create the Accounting, which fails.

        restAccountingMockMvc.perform(post("/api/accountings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accounting)))
                .andExpect(status().isBadRequest());

        List<Accounting> accountings = accountingRepository.findAll();
        assertThat(accountings).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountings() throws Exception {
        // Initialize the database
        accountingRepository.saveAndFlush(accounting);

        // Get all the accountings
        restAccountingMockMvc.perform(get("/api/accountings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(accounting.getId().intValue())))
                .andExpect(jsonPath("$.[*].keyaccounting").value(hasItem(DEFAULT_KEYACCOUNTING)));
    }

    @Test
    @Transactional
    public void getAccounting() throws Exception {
        // Initialize the database
        accountingRepository.saveAndFlush(accounting);

        // Get the accounting
        restAccountingMockMvc.perform(get("/api/accountings/{id}", accounting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(accounting.getId().intValue()))
            .andExpect(jsonPath("$.keyaccounting").value(DEFAULT_KEYACCOUNTING));
    }

    @Test
    @Transactional
    public void getNonExistingAccounting() throws Exception {
        // Get the accounting
        restAccountingMockMvc.perform(get("/api/accountings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccounting() throws Exception {
        // Initialize the database
        accountingService.save(accounting);

        int databaseSizeBeforeUpdate = accountingRepository.findAll().size();

        // Update the accounting
        Accounting updatedAccounting = new Accounting();
        updatedAccounting.setId(accounting.getId());
        updatedAccounting.setKeyaccounting(UPDATED_KEYACCOUNTING);

        restAccountingMockMvc.perform(put("/api/accountings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAccounting)))
                .andExpect(status().isOk());

        // Validate the Accounting in the database
        List<Accounting> accountings = accountingRepository.findAll();
        assertThat(accountings).hasSize(databaseSizeBeforeUpdate);
        Accounting testAccounting = accountings.get(accountings.size() - 1);
        assertThat(testAccounting.getKeyaccounting()).isEqualTo(UPDATED_KEYACCOUNTING);
    }

    @Test
    @Transactional
    public void deleteAccounting() throws Exception {
        // Initialize the database
        accountingService.save(accounting);

        int databaseSizeBeforeDelete = accountingRepository.findAll().size();

        // Get the accounting
        restAccountingMockMvc.perform(delete("/api/accountings/{id}", accounting.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Accounting> accountings = accountingRepository.findAll();
        assertThat(accountings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
