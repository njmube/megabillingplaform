package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Tax_transfered;
import org.megapractical.billingplatform.repository.Tax_transferedRepository;
import org.megapractical.billingplatform.service.Tax_transferedService;

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
 * Test class for the Tax_transferedResource REST controller.
 *
 * @see Tax_transferedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Tax_transferedResourceIntTest {


    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Tax_transferedRepository tax_transferedRepository;

    @Inject
    private Tax_transferedService tax_transferedService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTax_transferedMockMvc;

    private Tax_transfered tax_transfered;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tax_transferedResource tax_transferedResource = new Tax_transferedResource();
        ReflectionTestUtils.setField(tax_transferedResource, "tax_transferedService", tax_transferedService);
        this.restTax_transferedMockMvc = MockMvcBuilders.standaloneSetup(tax_transferedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tax_transfered = new Tax_transfered();
        tax_transfered.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createTax_transfered() throws Exception {
        int databaseSizeBeforeCreate = tax_transferedRepository.findAll().size();

        // Create the Tax_transfered

        restTax_transferedMockMvc.perform(post("/api/tax-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_transfered)))
                .andExpect(status().isCreated());

        // Validate the Tax_transfered in the database
        List<Tax_transfered> tax_transfereds = tax_transferedRepository.findAll();
        assertThat(tax_transfereds).hasSize(databaseSizeBeforeCreate + 1);
        Tax_transfered testTax_transfered = tax_transfereds.get(tax_transfereds.size() - 1);
        assertThat(testTax_transfered.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = tax_transferedRepository.findAll().size();
        // set the field null
        tax_transfered.setAmount(null);

        // Create the Tax_transfered, which fails.

        restTax_transferedMockMvc.perform(post("/api/tax-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_transfered)))
                .andExpect(status().isBadRequest());

        List<Tax_transfered> tax_transfereds = tax_transferedRepository.findAll();
        assertThat(tax_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTax_transfereds() throws Exception {
        // Initialize the database
        tax_transferedRepository.saveAndFlush(tax_transfered);

        // Get all the tax_transfereds
        restTax_transferedMockMvc.perform(get("/api/tax-transfereds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tax_transfered.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getTax_transfered() throws Exception {
        // Initialize the database
        tax_transferedRepository.saveAndFlush(tax_transfered);

        // Get the tax_transfered
        restTax_transferedMockMvc.perform(get("/api/tax-transfereds/{id}", tax_transfered.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tax_transfered.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTax_transfered() throws Exception {
        // Get the tax_transfered
        restTax_transferedMockMvc.perform(get("/api/tax-transfereds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTax_transfered() throws Exception {
        // Initialize the database
        tax_transferedService.save(tax_transfered);

        int databaseSizeBeforeUpdate = tax_transferedRepository.findAll().size();

        // Update the tax_transfered
        Tax_transfered updatedTax_transfered = new Tax_transfered();
        updatedTax_transfered.setId(tax_transfered.getId());
        updatedTax_transfered.setAmount(UPDATED_AMOUNT);

        restTax_transferedMockMvc.perform(put("/api/tax-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTax_transfered)))
                .andExpect(status().isOk());

        // Validate the Tax_transfered in the database
        List<Tax_transfered> tax_transfereds = tax_transferedRepository.findAll();
        assertThat(tax_transfereds).hasSize(databaseSizeBeforeUpdate);
        Tax_transfered testTax_transfered = tax_transfereds.get(tax_transfereds.size() - 1);
        assertThat(testTax_transfered.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteTax_transfered() throws Exception {
        // Initialize the database
        tax_transferedService.save(tax_transfered);

        int databaseSizeBeforeDelete = tax_transferedRepository.findAll().size();

        // Get the tax_transfered
        restTax_transferedMockMvc.perform(delete("/api/tax-transfereds/{id}", tax_transfered.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tax_transfered> tax_transfereds = tax_transferedRepository.findAll();
        assertThat(tax_transfereds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
