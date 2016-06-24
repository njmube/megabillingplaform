package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Tax_retentions;
import org.megapractical.billingplatform.repository.Tax_retentionsRepository;
import org.megapractical.billingplatform.service.Tax_retentionsService;

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
 * Test class for the Tax_retentionsResource REST controller.
 *
 * @see Tax_retentionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Tax_retentionsResourceIntTest {


    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Tax_retentionsRepository tax_retentionsRepository;

    @Inject
    private Tax_retentionsService tax_retentionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTax_retentionsMockMvc;

    private Tax_retentions tax_retentions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tax_retentionsResource tax_retentionsResource = new Tax_retentionsResource();
        ReflectionTestUtils.setField(tax_retentionsResource, "tax_retentionsService", tax_retentionsService);
        this.restTax_retentionsMockMvc = MockMvcBuilders.standaloneSetup(tax_retentionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tax_retentions = new Tax_retentions();
        tax_retentions.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createTax_retentions() throws Exception {
        int databaseSizeBeforeCreate = tax_retentionsRepository.findAll().size();

        // Create the Tax_retentions

        restTax_retentionsMockMvc.perform(post("/api/tax-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_retentions)))
                .andExpect(status().isCreated());

        // Validate the Tax_retentions in the database
        List<Tax_retentions> tax_retentions = tax_retentionsRepository.findAll();
        assertThat(tax_retentions).hasSize(databaseSizeBeforeCreate + 1);
        Tax_retentions testTax_retentions = tax_retentions.get(tax_retentions.size() - 1);
        assertThat(testTax_retentions.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = tax_retentionsRepository.findAll().size();
        // set the field null
        tax_retentions.setAmount(null);

        // Create the Tax_retentions, which fails.

        restTax_retentionsMockMvc.perform(post("/api/tax-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_retentions)))
                .andExpect(status().isBadRequest());

        List<Tax_retentions> tax_retentions = tax_retentionsRepository.findAll();
        assertThat(tax_retentions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTax_retentions() throws Exception {
        // Initialize the database
        tax_retentionsRepository.saveAndFlush(tax_retentions);

        // Get all the tax_retentions
        restTax_retentionsMockMvc.perform(get("/api/tax-retentions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tax_retentions.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getTax_retentions() throws Exception {
        // Initialize the database
        tax_retentionsRepository.saveAndFlush(tax_retentions);

        // Get the tax_retentions
        restTax_retentionsMockMvc.perform(get("/api/tax-retentions/{id}", tax_retentions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tax_retentions.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTax_retentions() throws Exception {
        // Get the tax_retentions
        restTax_retentionsMockMvc.perform(get("/api/tax-retentions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTax_retentions() throws Exception {
        // Initialize the database
        tax_retentionsService.save(tax_retentions);

        int databaseSizeBeforeUpdate = tax_retentionsRepository.findAll().size();

        // Update the tax_retentions
        Tax_retentions updatedTax_retentions = new Tax_retentions();
        updatedTax_retentions.setId(tax_retentions.getId());
        updatedTax_retentions.setAmount(UPDATED_AMOUNT);

        restTax_retentionsMockMvc.perform(put("/api/tax-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTax_retentions)))
                .andExpect(status().isOk());

        // Validate the Tax_retentions in the database
        List<Tax_retentions> tax_retentions = tax_retentionsRepository.findAll();
        assertThat(tax_retentions).hasSize(databaseSizeBeforeUpdate);
        Tax_retentions testTax_retentions = tax_retentions.get(tax_retentions.size() - 1);
        assertThat(testTax_retentions.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteTax_retentions() throws Exception {
        // Initialize the database
        tax_retentionsService.save(tax_retentions);

        int databaseSizeBeforeDelete = tax_retentionsRepository.findAll().size();

        // Get the tax_retentions
        restTax_retentionsMockMvc.perform(delete("/api/tax-retentions/{id}", tax_retentions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tax_retentions> tax_retentions = tax_retentionsRepository.findAll();
        assertThat(tax_retentions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
