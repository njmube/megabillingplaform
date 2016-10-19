package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Price_concept;
import org.megapractical.billingplatform.repository.Price_conceptRepository;
import org.megapractical.billingplatform.service.Price_conceptService;

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
 * Test class for the Price_conceptResource REST controller.
 *
 * @see Price_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Price_conceptResourceIntTest {


    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    @Inject
    private Price_conceptRepository price_conceptRepository;

    @Inject
    private Price_conceptService price_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPrice_conceptMockMvc;

    private Price_concept price_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Price_conceptResource price_conceptResource = new Price_conceptResource();
        ReflectionTestUtils.setField(price_conceptResource, "price_conceptService", price_conceptService);
        this.restPrice_conceptMockMvc = MockMvcBuilders.standaloneSetup(price_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        price_concept = new Price_concept();
        price_concept.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createPrice_concept() throws Exception {
        int databaseSizeBeforeCreate = price_conceptRepository.findAll().size();

        // Create the Price_concept

        restPrice_conceptMockMvc.perform(post("/api/price-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(price_concept)))
                .andExpect(status().isCreated());

        // Validate the Price_concept in the database
        List<Price_concept> price_concepts = price_conceptRepository.findAll();
        assertThat(price_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Price_concept testPrice_concept = price_concepts.get(price_concepts.size() - 1);
        assertThat(testPrice_concept.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = price_conceptRepository.findAll().size();
        // set the field null
        price_concept.setValue(null);

        // Create the Price_concept, which fails.

        restPrice_conceptMockMvc.perform(post("/api/price-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(price_concept)))
                .andExpect(status().isBadRequest());

        List<Price_concept> price_concepts = price_conceptRepository.findAll();
        assertThat(price_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrice_concepts() throws Exception {
        // Initialize the database
        price_conceptRepository.saveAndFlush(price_concept);

        // Get all the price_concepts
        restPrice_conceptMockMvc.perform(get("/api/price-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(price_concept.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }

    @Test
    @Transactional
    public void getPrice_concept() throws Exception {
        // Initialize the database
        price_conceptRepository.saveAndFlush(price_concept);

        // Get the price_concept
        restPrice_conceptMockMvc.perform(get("/api/price-concepts/{id}", price_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(price_concept.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrice_concept() throws Exception {
        // Get the price_concept
        restPrice_conceptMockMvc.perform(get("/api/price-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrice_concept() throws Exception {
        // Initialize the database
        price_conceptService.save(price_concept);

        int databaseSizeBeforeUpdate = price_conceptRepository.findAll().size();

        // Update the price_concept
        Price_concept updatedPrice_concept = new Price_concept();
        updatedPrice_concept.setId(price_concept.getId());
        updatedPrice_concept.setValue(UPDATED_VALUE);

        restPrice_conceptMockMvc.perform(put("/api/price-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPrice_concept)))
                .andExpect(status().isOk());

        // Validate the Price_concept in the database
        List<Price_concept> price_concepts = price_conceptRepository.findAll();
        assertThat(price_concepts).hasSize(databaseSizeBeforeUpdate);
        Price_concept testPrice_concept = price_concepts.get(price_concepts.size() - 1);
        assertThat(testPrice_concept.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deletePrice_concept() throws Exception {
        // Initialize the database
        price_conceptService.save(price_concept);

        int databaseSizeBeforeDelete = price_conceptRepository.findAll().size();

        // Get the price_concept
        restPrice_conceptMockMvc.perform(delete("/api/price-concepts/{id}", price_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Price_concept> price_concepts = price_conceptRepository.findAll();
        assertThat(price_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
