package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Discount_concept;
import org.megapractical.billingplatform.repository.Discount_conceptRepository;
import org.megapractical.billingplatform.service.Discount_conceptService;

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
 * Test class for the Discount_conceptResource REST controller.
 *
 * @see Discount_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Discount_conceptResourceIntTest {


    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    @Inject
    private Discount_conceptRepository discount_conceptRepository;

    @Inject
    private Discount_conceptService discount_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDiscount_conceptMockMvc;

    private Discount_concept discount_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Discount_conceptResource discount_conceptResource = new Discount_conceptResource();
        ReflectionTestUtils.setField(discount_conceptResource, "discount_conceptService", discount_conceptService);
        this.restDiscount_conceptMockMvc = MockMvcBuilders.standaloneSetup(discount_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        discount_concept = new Discount_concept();
        discount_concept.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createDiscount_concept() throws Exception {
        int databaseSizeBeforeCreate = discount_conceptRepository.findAll().size();

        // Create the Discount_concept

        restDiscount_conceptMockMvc.perform(post("/api/discount-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discount_concept)))
                .andExpect(status().isCreated());

        // Validate the Discount_concept in the database
        List<Discount_concept> discount_concepts = discount_conceptRepository.findAll();
        assertThat(discount_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Discount_concept testDiscount_concept = discount_concepts.get(discount_concepts.size() - 1);
        assertThat(testDiscount_concept.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = discount_conceptRepository.findAll().size();
        // set the field null
        discount_concept.setValue(null);

        // Create the Discount_concept, which fails.

        restDiscount_conceptMockMvc.perform(post("/api/discount-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(discount_concept)))
                .andExpect(status().isBadRequest());

        List<Discount_concept> discount_concepts = discount_conceptRepository.findAll();
        assertThat(discount_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiscount_concepts() throws Exception {
        // Initialize the database
        discount_conceptRepository.saveAndFlush(discount_concept);

        // Get all the discount_concepts
        restDiscount_conceptMockMvc.perform(get("/api/discount-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(discount_concept.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }

    @Test
    @Transactional
    public void getDiscount_concept() throws Exception {
        // Initialize the database
        discount_conceptRepository.saveAndFlush(discount_concept);

        // Get the discount_concept
        restDiscount_conceptMockMvc.perform(get("/api/discount-concepts/{id}", discount_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(discount_concept.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscount_concept() throws Exception {
        // Get the discount_concept
        restDiscount_conceptMockMvc.perform(get("/api/discount-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscount_concept() throws Exception {
        // Initialize the database
        discount_conceptService.save(discount_concept);

        int databaseSizeBeforeUpdate = discount_conceptRepository.findAll().size();

        // Update the discount_concept
        Discount_concept updatedDiscount_concept = new Discount_concept();
        updatedDiscount_concept.setId(discount_concept.getId());
        updatedDiscount_concept.setValue(UPDATED_VALUE);

        restDiscount_conceptMockMvc.perform(put("/api/discount-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDiscount_concept)))
                .andExpect(status().isOk());

        // Validate the Discount_concept in the database
        List<Discount_concept> discount_concepts = discount_conceptRepository.findAll();
        assertThat(discount_concepts).hasSize(databaseSizeBeforeUpdate);
        Discount_concept testDiscount_concept = discount_concepts.get(discount_concepts.size() - 1);
        assertThat(testDiscount_concept.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteDiscount_concept() throws Exception {
        // Initialize the database
        discount_conceptService.save(discount_concept);

        int databaseSizeBeforeDelete = discount_conceptRepository.findAll().size();

        // Get the discount_concept
        restDiscount_conceptMockMvc.perform(delete("/api/discount-concepts/{id}", discount_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Discount_concept> discount_concepts = discount_conceptRepository.findAll();
        assertThat(discount_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
