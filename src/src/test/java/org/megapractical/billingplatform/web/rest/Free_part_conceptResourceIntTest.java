package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_part_concept;
import org.megapractical.billingplatform.repository.Free_part_conceptRepository;
import org.megapractical.billingplatform.service.Free_part_conceptService;

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
 * Test class for the Free_part_conceptResource REST controller.
 *
 * @see Free_part_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_part_conceptResourceIntTest {

    private static final String DEFAULT_NO_IDENTIFICATION = "AAAAA";
    private static final String UPDATED_NO_IDENTIFICATION = "BBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final BigDecimal DEFAULT_UNIT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Free_part_conceptRepository free_part_conceptRepository;

    @Inject
    private Free_part_conceptService free_part_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_part_conceptMockMvc;

    private Free_part_concept free_part_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_part_conceptResource free_part_conceptResource = new Free_part_conceptResource();
        ReflectionTestUtils.setField(free_part_conceptResource, "free_part_conceptService", free_part_conceptService);
        this.restFree_part_conceptMockMvc = MockMvcBuilders.standaloneSetup(free_part_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_part_concept = new Free_part_concept();
        free_part_concept.setNo_identification(DEFAULT_NO_IDENTIFICATION);
        free_part_concept.setQuantity(DEFAULT_QUANTITY);
        free_part_concept.setDescription(DEFAULT_DESCRIPTION);
        free_part_concept.setUnit_value(DEFAULT_UNIT_VALUE);
        free_part_concept.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createFree_part_concept() throws Exception {
        int databaseSizeBeforeCreate = free_part_conceptRepository.findAll().size();

        // Create the Free_part_concept

        restFree_part_conceptMockMvc.perform(post("/api/free-part-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_part_concept)))
                .andExpect(status().isCreated());

        // Validate the Free_part_concept in the database
        List<Free_part_concept> free_part_concepts = free_part_conceptRepository.findAll();
        assertThat(free_part_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Free_part_concept testFree_part_concept = free_part_concepts.get(free_part_concepts.size() - 1);
        assertThat(testFree_part_concept.getNo_identification()).isEqualTo(DEFAULT_NO_IDENTIFICATION);
        assertThat(testFree_part_concept.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testFree_part_concept.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFree_part_concept.getUnit_value()).isEqualTo(DEFAULT_UNIT_VALUE);
        assertThat(testFree_part_concept.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_part_conceptRepository.findAll().size();
        // set the field null
        free_part_concept.setQuantity(null);

        // Create the Free_part_concept, which fails.

        restFree_part_conceptMockMvc.perform(post("/api/free-part-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_part_concept)))
                .andExpect(status().isBadRequest());

        List<Free_part_concept> free_part_concepts = free_part_conceptRepository.findAll();
        assertThat(free_part_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFree_part_concepts() throws Exception {
        // Initialize the database
        free_part_conceptRepository.saveAndFlush(free_part_concept);

        // Get all the free_part_concepts
        restFree_part_conceptMockMvc.perform(get("/api/free-part-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_part_concept.getId().intValue())))
                .andExpect(jsonPath("$.[*].no_identification").value(hasItem(DEFAULT_NO_IDENTIFICATION.toString())))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].unit_value").value(hasItem(DEFAULT_UNIT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getFree_part_concept() throws Exception {
        // Initialize the database
        free_part_conceptRepository.saveAndFlush(free_part_concept);

        // Get the free_part_concept
        restFree_part_conceptMockMvc.perform(get("/api/free-part-concepts/{id}", free_part_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_part_concept.getId().intValue()))
            .andExpect(jsonPath("$.no_identification").value(DEFAULT_NO_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.unit_value").value(DEFAULT_UNIT_VALUE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFree_part_concept() throws Exception {
        // Get the free_part_concept
        restFree_part_conceptMockMvc.perform(get("/api/free-part-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFree_part_concept() throws Exception {
        // Initialize the database
        free_part_conceptService.save(free_part_concept);

        int databaseSizeBeforeUpdate = free_part_conceptRepository.findAll().size();

        // Update the free_part_concept
        Free_part_concept updatedFree_part_concept = new Free_part_concept();
        updatedFree_part_concept.setId(free_part_concept.getId());
        updatedFree_part_concept.setNo_identification(UPDATED_NO_IDENTIFICATION);
        updatedFree_part_concept.setQuantity(UPDATED_QUANTITY);
        updatedFree_part_concept.setDescription(UPDATED_DESCRIPTION);
        updatedFree_part_concept.setUnit_value(UPDATED_UNIT_VALUE);
        updatedFree_part_concept.setAmount(UPDATED_AMOUNT);

        restFree_part_conceptMockMvc.perform(put("/api/free-part-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_part_concept)))
                .andExpect(status().isOk());

        // Validate the Free_part_concept in the database
        List<Free_part_concept> free_part_concepts = free_part_conceptRepository.findAll();
        assertThat(free_part_concepts).hasSize(databaseSizeBeforeUpdate);
        Free_part_concept testFree_part_concept = free_part_concepts.get(free_part_concepts.size() - 1);
        assertThat(testFree_part_concept.getNo_identification()).isEqualTo(UPDATED_NO_IDENTIFICATION);
        assertThat(testFree_part_concept.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testFree_part_concept.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFree_part_concept.getUnit_value()).isEqualTo(UPDATED_UNIT_VALUE);
        assertThat(testFree_part_concept.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteFree_part_concept() throws Exception {
        // Initialize the database
        free_part_conceptService.save(free_part_concept);

        int databaseSizeBeforeDelete = free_part_conceptRepository.findAll().size();

        // Get the free_part_concept
        restFree_part_conceptMockMvc.perform(delete("/api/free-part-concepts/{id}", free_part_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_part_concept> free_part_concepts = free_part_conceptRepository.findAll();
        assertThat(free_part_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
