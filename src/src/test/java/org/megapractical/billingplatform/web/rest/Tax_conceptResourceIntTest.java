package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Tax_concept;
import org.megapractical.billingplatform.repository.Tax_conceptRepository;
import org.megapractical.billingplatform.service.Tax_conceptService;

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
 * Test class for the Tax_conceptResource REST controller.
 *
 * @see Tax_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Tax_conceptResourceIntTest {


    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    @Inject
    private Tax_conceptRepository tax_conceptRepository;

    @Inject
    private Tax_conceptService tax_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTax_conceptMockMvc;

    private Tax_concept tax_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tax_conceptResource tax_conceptResource = new Tax_conceptResource();
        ReflectionTestUtils.setField(tax_conceptResource, "tax_conceptService", tax_conceptService);
        this.restTax_conceptMockMvc = MockMvcBuilders.standaloneSetup(tax_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tax_concept = new Tax_concept();
        tax_concept.setRate(DEFAULT_RATE);
    }

    @Test
    @Transactional
    public void createTax_concept() throws Exception {
        int databaseSizeBeforeCreate = tax_conceptRepository.findAll().size();

        // Create the Tax_concept

        restTax_conceptMockMvc.perform(post("/api/tax-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_concept)))
                .andExpect(status().isCreated());

        // Validate the Tax_concept in the database
        List<Tax_concept> tax_concepts = tax_conceptRepository.findAll();
        assertThat(tax_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Tax_concept testTax_concept = tax_concepts.get(tax_concepts.size() - 1);
        assertThat(testTax_concept.getRate()).isEqualTo(DEFAULT_RATE);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = tax_conceptRepository.findAll().size();
        // set the field null
        tax_concept.setRate(null);

        // Create the Tax_concept, which fails.

        restTax_conceptMockMvc.perform(post("/api/tax-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_concept)))
                .andExpect(status().isBadRequest());

        List<Tax_concept> tax_concepts = tax_conceptRepository.findAll();
        assertThat(tax_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTax_concepts() throws Exception {
        // Initialize the database
        tax_conceptRepository.saveAndFlush(tax_concept);

        // Get all the tax_concepts
        restTax_conceptMockMvc.perform(get("/api/tax-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tax_concept.getId().intValue())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())));
    }

    @Test
    @Transactional
    public void getTax_concept() throws Exception {
        // Initialize the database
        tax_conceptRepository.saveAndFlush(tax_concept);

        // Get the tax_concept
        restTax_conceptMockMvc.perform(get("/api/tax-concepts/{id}", tax_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tax_concept.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTax_concept() throws Exception {
        // Get the tax_concept
        restTax_conceptMockMvc.perform(get("/api/tax-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTax_concept() throws Exception {
        // Initialize the database
        tax_conceptService.save(tax_concept);

        int databaseSizeBeforeUpdate = tax_conceptRepository.findAll().size();

        // Update the tax_concept
        Tax_concept updatedTax_concept = new Tax_concept();
        updatedTax_concept.setId(tax_concept.getId());
        updatedTax_concept.setRate(UPDATED_RATE);

        restTax_conceptMockMvc.perform(put("/api/tax-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTax_concept)))
                .andExpect(status().isOk());

        // Validate the Tax_concept in the database
        List<Tax_concept> tax_concepts = tax_conceptRepository.findAll();
        assertThat(tax_concepts).hasSize(databaseSizeBeforeUpdate);
        Tax_concept testTax_concept = tax_concepts.get(tax_concepts.size() - 1);
        assertThat(testTax_concept.getRate()).isEqualTo(UPDATED_RATE);
    }

    @Test
    @Transactional
    public void deleteTax_concept() throws Exception {
        // Initialize the database
        tax_conceptService.save(tax_concept);

        int databaseSizeBeforeDelete = tax_conceptRepository.findAll().size();

        // Get the tax_concept
        restTax_conceptMockMvc.perform(delete("/api/tax-concepts/{id}", tax_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tax_concept> tax_concepts = tax_conceptRepository.findAll();
        assertThat(tax_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
