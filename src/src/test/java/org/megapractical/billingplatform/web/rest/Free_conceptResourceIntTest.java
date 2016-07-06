package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_concept;
import org.megapractical.billingplatform.repository.Free_conceptRepository;
import org.megapractical.billingplatform.service.Free_conceptService;

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
 * Test class for the Free_conceptResource REST controller.
 *
 * @see Free_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_conceptResourceIntTest {

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

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISCOUNT = new BigDecimal(2);

    @Inject
    private Free_conceptRepository free_conceptRepository;

    @Inject
    private Free_conceptService free_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_conceptMockMvc;

    private Free_concept free_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_conceptResource free_conceptResource = new Free_conceptResource();
        ReflectionTestUtils.setField(free_conceptResource, "free_conceptService", free_conceptService);
        this.restFree_conceptMockMvc = MockMvcBuilders.standaloneSetup(free_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_concept = new Free_concept();
        free_concept.setNo_identification(DEFAULT_NO_IDENTIFICATION);
        free_concept.setQuantity(DEFAULT_QUANTITY);
        free_concept.setDescription(DEFAULT_DESCRIPTION);
        free_concept.setUnit_value(DEFAULT_UNIT_VALUE);
        free_concept.setPredial_number(DEFAULT_PREDIAL_NUMBER);
        free_concept.setAmount(DEFAULT_AMOUNT);
        free_concept.setDiscount(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    public void createFree_concept() throws Exception {
        int databaseSizeBeforeCreate = free_conceptRepository.findAll().size();

        // Create the Free_concept

        restFree_conceptMockMvc.perform(post("/api/free-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_concept)))
                .andExpect(status().isCreated());

        // Validate the Free_concept in the database
        List<Free_concept> free_concepts = free_conceptRepository.findAll();
        assertThat(free_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Free_concept testFree_concept = free_concepts.get(free_concepts.size() - 1);
        assertThat(testFree_concept.getNo_identification()).isEqualTo(DEFAULT_NO_IDENTIFICATION);
        assertThat(testFree_concept.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testFree_concept.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFree_concept.getUnit_value()).isEqualTo(DEFAULT_UNIT_VALUE);
        assertThat(testFree_concept.getPredial_number()).isEqualTo(DEFAULT_PREDIAL_NUMBER);
        assertThat(testFree_concept.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testFree_concept.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    public void checkNo_identificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_conceptRepository.findAll().size();
        // set the field null
        free_concept.setNo_identification(null);

        // Create the Free_concept, which fails.

        restFree_conceptMockMvc.perform(post("/api/free-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_concept)))
                .andExpect(status().isBadRequest());

        List<Free_concept> free_concepts = free_conceptRepository.findAll();
        assertThat(free_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_conceptRepository.findAll().size();
        // set the field null
        free_concept.setQuantity(null);

        // Create the Free_concept, which fails.

        restFree_conceptMockMvc.perform(post("/api/free-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_concept)))
                .andExpect(status().isBadRequest());

        List<Free_concept> free_concepts = free_conceptRepository.findAll();
        assertThat(free_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnit_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_conceptRepository.findAll().size();
        // set the field null
        free_concept.setUnit_value(null);

        // Create the Free_concept, which fails.

        restFree_conceptMockMvc.perform(post("/api/free-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_concept)))
                .andExpect(status().isBadRequest());

        List<Free_concept> free_concepts = free_conceptRepository.findAll();
        assertThat(free_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_conceptRepository.findAll().size();
        // set the field null
        free_concept.setAmount(null);

        // Create the Free_concept, which fails.

        restFree_conceptMockMvc.perform(post("/api/free-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_concept)))
                .andExpect(status().isBadRequest());

        List<Free_concept> free_concepts = free_conceptRepository.findAll();
        assertThat(free_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_conceptRepository.findAll().size();
        // set the field null
        free_concept.setDiscount(null);

        // Create the Free_concept, which fails.

        restFree_conceptMockMvc.perform(post("/api/free-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_concept)))
                .andExpect(status().isBadRequest());

        List<Free_concept> free_concepts = free_conceptRepository.findAll();
        assertThat(free_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFree_concepts() throws Exception {
        // Initialize the database
        free_conceptRepository.saveAndFlush(free_concept);

        // Get all the free_concepts
        restFree_conceptMockMvc.perform(get("/api/free-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_concept.getId().intValue())))
                .andExpect(jsonPath("$.[*].no_identification").value(hasItem(DEFAULT_NO_IDENTIFICATION.toString())))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].unit_value").value(hasItem(DEFAULT_UNIT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].predial_number").value(hasItem(DEFAULT_PREDIAL_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getFree_concept() throws Exception {
        // Initialize the database
        free_conceptRepository.saveAndFlush(free_concept);

        // Get the free_concept
        restFree_conceptMockMvc.perform(get("/api/free-concepts/{id}", free_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_concept.getId().intValue()))
            .andExpect(jsonPath("$.no_identification").value(DEFAULT_NO_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.unit_value").value(DEFAULT_UNIT_VALUE.intValue()))
            .andExpect(jsonPath("$.predial_number").value(DEFAULT_PREDIAL_NUMBER.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFree_concept() throws Exception {
        // Get the free_concept
        restFree_conceptMockMvc.perform(get("/api/free-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFree_concept() throws Exception {
        // Initialize the database
        free_conceptService.save(free_concept);

        int databaseSizeBeforeUpdate = free_conceptRepository.findAll().size();

        // Update the free_concept
        Free_concept updatedFree_concept = new Free_concept();
        updatedFree_concept.setId(free_concept.getId());
        updatedFree_concept.setNo_identification(UPDATED_NO_IDENTIFICATION);
        updatedFree_concept.setQuantity(UPDATED_QUANTITY);
        updatedFree_concept.setDescription(UPDATED_DESCRIPTION);
        updatedFree_concept.setUnit_value(UPDATED_UNIT_VALUE);
        updatedFree_concept.setPredial_number(UPDATED_PREDIAL_NUMBER);
        updatedFree_concept.setAmount(UPDATED_AMOUNT);
        updatedFree_concept.setDiscount(UPDATED_DISCOUNT);

        restFree_conceptMockMvc.perform(put("/api/free-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_concept)))
                .andExpect(status().isOk());

        // Validate the Free_concept in the database
        List<Free_concept> free_concepts = free_conceptRepository.findAll();
        assertThat(free_concepts).hasSize(databaseSizeBeforeUpdate);
        Free_concept testFree_concept = free_concepts.get(free_concepts.size() - 1);
        assertThat(testFree_concept.getNo_identification()).isEqualTo(UPDATED_NO_IDENTIFICATION);
        assertThat(testFree_concept.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testFree_concept.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFree_concept.getUnit_value()).isEqualTo(UPDATED_UNIT_VALUE);
        assertThat(testFree_concept.getPredial_number()).isEqualTo(UPDATED_PREDIAL_NUMBER);
        assertThat(testFree_concept.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testFree_concept.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void deleteFree_concept() throws Exception {
        // Initialize the database
        free_conceptService.save(free_concept);

        int databaseSizeBeforeDelete = free_conceptRepository.findAll().size();

        // Get the free_concept
        restFree_conceptMockMvc.perform(delete("/api/free-concepts/{id}", free_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_concept> free_concepts = free_conceptRepository.findAll();
        assertThat(free_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
