package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Concept;
import org.megapractical.billingplatform.repository.ConceptRepository;
import org.megapractical.billingplatform.service.ConceptService;

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
 * Test class for the ConceptResource REST controller.
 *
 * @see ConceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class ConceptResourceIntTest {

    private static final String DEFAULT_NO_IDENTIFICATION = "AAAAA";
    private static final String UPDATED_NO_IDENTIFICATION = "BBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final BigDecimal DEFAULT_UNIT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_VALUE = new BigDecimal(2);
    private static final String DEFAULT_PREDIAL_NUMBER = "AAAAA";
    private static final String UPDATED_PREDIAL_NUMBER = "BBBBB";

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private ConceptRepository conceptRepository;

    @Inject
    private ConceptService conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restConceptMockMvc;

    private Concept concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ConceptResource conceptResource = new ConceptResource();
        ReflectionTestUtils.setField(conceptResource, "conceptService", conceptService);
        this.restConceptMockMvc = MockMvcBuilders.standaloneSetup(conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        concept = new Concept();
        concept.setNo_identification(DEFAULT_NO_IDENTIFICATION);
        concept.setQuantity(DEFAULT_QUANTITY);
        concept.setDescription(DEFAULT_DESCRIPTION);
        concept.setUnit_value(DEFAULT_UNIT_VALUE);
        concept.setPredial_number(DEFAULT_PREDIAL_NUMBER);
        concept.setDiscount(DEFAULT_DISCOUNT);
        concept.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createConcept() throws Exception {
        int databaseSizeBeforeCreate = conceptRepository.findAll().size();

        // Create the Concept

        restConceptMockMvc.perform(post("/api/concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(concept)))
                .andExpect(status().isCreated());

        // Validate the Concept in the database
        List<Concept> concepts = conceptRepository.findAll();
        assertThat(concepts).hasSize(databaseSizeBeforeCreate + 1);
        Concept testConcept = concepts.get(concepts.size() - 1);
        assertThat(testConcept.getNo_identification()).isEqualTo(DEFAULT_NO_IDENTIFICATION);
        assertThat(testConcept.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testConcept.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConcept.getUnit_value()).isEqualTo(DEFAULT_UNIT_VALUE);
        assertThat(testConcept.getPredial_number()).isEqualTo(DEFAULT_PREDIAL_NUMBER);
        assertThat(testConcept.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testConcept.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkNo_identificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptRepository.findAll().size();
        // set the field null
        concept.setNo_identification(null);

        // Create the Concept, which fails.

        restConceptMockMvc.perform(post("/api/concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(concept)))
                .andExpect(status().isBadRequest());

        List<Concept> concepts = conceptRepository.findAll();
        assertThat(concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptRepository.findAll().size();
        // set the field null
        concept.setQuantity(null);

        // Create the Concept, which fails.

        restConceptMockMvc.perform(post("/api/concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(concept)))
                .andExpect(status().isBadRequest());

        List<Concept> concepts = conceptRepository.findAll();
        assertThat(concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnit_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = conceptRepository.findAll().size();
        // set the field null
        concept.setUnit_value(null);

        // Create the Concept, which fails.

        restConceptMockMvc.perform(post("/api/concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(concept)))
                .andExpect(status().isBadRequest());

        List<Concept> concepts = conceptRepository.findAll();
        assertThat(concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConcepts() throws Exception {
        // Initialize the database
        conceptRepository.saveAndFlush(concept);

        // Get all the concepts
        restConceptMockMvc.perform(get("/api/concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(concept.getId().intValue())))
                .andExpect(jsonPath("$.[*].no_identification").value(hasItem(DEFAULT_NO_IDENTIFICATION.toString())))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].unit_value").value(hasItem(DEFAULT_UNIT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].predial_number").value(hasItem(DEFAULT_PREDIAL_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getConcept() throws Exception {
        // Initialize the database
        conceptRepository.saveAndFlush(concept);

        // Get the concept
        restConceptMockMvc.perform(get("/api/concepts/{id}", concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(concept.getId().intValue()))
            .andExpect(jsonPath("$.no_identification").value(DEFAULT_NO_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.unit_value").value(DEFAULT_UNIT_VALUE.intValue()))
            .andExpect(jsonPath("$.predial_number").value(DEFAULT_PREDIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingConcept() throws Exception {
        // Get the concept
        restConceptMockMvc.perform(get("/api/concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConcept() throws Exception {
        // Initialize the database
        conceptService.save(concept);

        int databaseSizeBeforeUpdate = conceptRepository.findAll().size();

        // Update the concept
        Concept updatedConcept = new Concept();
        updatedConcept.setId(concept.getId());
        updatedConcept.setNo_identification(UPDATED_NO_IDENTIFICATION);
        updatedConcept.setQuantity(UPDATED_QUANTITY);
        updatedConcept.setDescription(UPDATED_DESCRIPTION);
        updatedConcept.setUnit_value(UPDATED_UNIT_VALUE);
        updatedConcept.setPredial_number(UPDATED_PREDIAL_NUMBER);
        updatedConcept.setDiscount(UPDATED_DISCOUNT);
        updatedConcept.setAmount(UPDATED_AMOUNT);

        restConceptMockMvc.perform(put("/api/concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedConcept)))
                .andExpect(status().isOk());

        // Validate the Concept in the database
        List<Concept> concepts = conceptRepository.findAll();
        assertThat(concepts).hasSize(databaseSizeBeforeUpdate);
        Concept testConcept = concepts.get(concepts.size() - 1);
        assertThat(testConcept.getNo_identification()).isEqualTo(UPDATED_NO_IDENTIFICATION);
        assertThat(testConcept.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testConcept.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConcept.getUnit_value()).isEqualTo(UPDATED_UNIT_VALUE);
        assertThat(testConcept.getPredial_number()).isEqualTo(UPDATED_PREDIAL_NUMBER);
        assertThat(testConcept.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testConcept.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteConcept() throws Exception {
        // Initialize the database
        conceptService.save(concept);

        int databaseSizeBeforeDelete = conceptRepository.findAll().size();

        // Get the concept
        restConceptMockMvc.perform(delete("/api/concepts/{id}", concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Concept> concepts = conceptRepository.findAll();
        assertThat(concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
