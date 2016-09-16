package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Part_concept;
import org.megapractical.billingplatform.repository.Part_conceptRepository;
import org.megapractical.billingplatform.service.Part_conceptService;

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
 * Test class for the Part_conceptResource REST controller.
 *
 * @see Part_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Part_conceptResourceIntTest {

    private static final String DEFAULT_NO_IDENTIFICATION = "AAAAA";
    private static final String UPDATED_NO_IDENTIFICATION = "BBBBB";

    private static final Integer DEFAULT_QUANITTY = 1;
    private static final Integer UPDATED_QUANITTY = 2;
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final BigDecimal DEFAULT_UNIT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Part_conceptRepository part_conceptRepository;

    @Inject
    private Part_conceptService part_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPart_conceptMockMvc;

    private Part_concept part_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Part_conceptResource part_conceptResource = new Part_conceptResource();
        ReflectionTestUtils.setField(part_conceptResource, "part_conceptService", part_conceptService);
        this.restPart_conceptMockMvc = MockMvcBuilders.standaloneSetup(part_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        part_concept = new Part_concept();
        part_concept.setNo_identification(DEFAULT_NO_IDENTIFICATION);
        part_concept.setQuanitty(DEFAULT_QUANITTY);
        part_concept.setDescription(DEFAULT_DESCRIPTION);
        part_concept.setUnit_value(DEFAULT_UNIT_VALUE);
        part_concept.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createPart_concept() throws Exception {
        int databaseSizeBeforeCreate = part_conceptRepository.findAll().size();

        // Create the Part_concept

        restPart_conceptMockMvc.perform(post("/api/part-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(part_concept)))
                .andExpect(status().isCreated());

        // Validate the Part_concept in the database
        List<Part_concept> part_concepts = part_conceptRepository.findAll();
        assertThat(part_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Part_concept testPart_concept = part_concepts.get(part_concepts.size() - 1);
        assertThat(testPart_concept.getNo_identification()).isEqualTo(DEFAULT_NO_IDENTIFICATION);
        assertThat(testPart_concept.getQuanitty()).isEqualTo(DEFAULT_QUANITTY);
        assertThat(testPart_concept.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPart_concept.getUnit_value()).isEqualTo(DEFAULT_UNIT_VALUE);
        assertThat(testPart_concept.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkQuanittyIsRequired() throws Exception {
        int databaseSizeBeforeTest = part_conceptRepository.findAll().size();
        // set the field null
        part_concept.setQuanitty(null);

        // Create the Part_concept, which fails.

        restPart_conceptMockMvc.perform(post("/api/part-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(part_concept)))
                .andExpect(status().isBadRequest());

        List<Part_concept> part_concepts = part_conceptRepository.findAll();
        assertThat(part_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = part_conceptRepository.findAll().size();
        // set the field null
        part_concept.setDescription(null);

        // Create the Part_concept, which fails.

        restPart_conceptMockMvc.perform(post("/api/part-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(part_concept)))
                .andExpect(status().isBadRequest());

        List<Part_concept> part_concepts = part_conceptRepository.findAll();
        assertThat(part_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPart_concepts() throws Exception {
        // Initialize the database
        part_conceptRepository.saveAndFlush(part_concept);

        // Get all the part_concepts
        restPart_conceptMockMvc.perform(get("/api/part-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(part_concept.getId().intValue())))
                .andExpect(jsonPath("$.[*].no_identification").value(hasItem(DEFAULT_NO_IDENTIFICATION.toString())))
                .andExpect(jsonPath("$.[*].quanitty").value(hasItem(DEFAULT_QUANITTY)))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].unit_value").value(hasItem(DEFAULT_UNIT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getPart_concept() throws Exception {
        // Initialize the database
        part_conceptRepository.saveAndFlush(part_concept);

        // Get the part_concept
        restPart_conceptMockMvc.perform(get("/api/part-concepts/{id}", part_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(part_concept.getId().intValue()))
            .andExpect(jsonPath("$.no_identification").value(DEFAULT_NO_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.quanitty").value(DEFAULT_QUANITTY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.unit_value").value(DEFAULT_UNIT_VALUE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPart_concept() throws Exception {
        // Get the part_concept
        restPart_conceptMockMvc.perform(get("/api/part-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePart_concept() throws Exception {
        // Initialize the database
        part_conceptService.save(part_concept);

        int databaseSizeBeforeUpdate = part_conceptRepository.findAll().size();

        // Update the part_concept
        Part_concept updatedPart_concept = new Part_concept();
        updatedPart_concept.setId(part_concept.getId());
        updatedPart_concept.setNo_identification(UPDATED_NO_IDENTIFICATION);
        updatedPart_concept.setQuanitty(UPDATED_QUANITTY);
        updatedPart_concept.setDescription(UPDATED_DESCRIPTION);
        updatedPart_concept.setUnit_value(UPDATED_UNIT_VALUE);
        updatedPart_concept.setAmount(UPDATED_AMOUNT);

        restPart_conceptMockMvc.perform(put("/api/part-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPart_concept)))
                .andExpect(status().isOk());

        // Validate the Part_concept in the database
        List<Part_concept> part_concepts = part_conceptRepository.findAll();
        assertThat(part_concepts).hasSize(databaseSizeBeforeUpdate);
        Part_concept testPart_concept = part_concepts.get(part_concepts.size() - 1);
        assertThat(testPart_concept.getNo_identification()).isEqualTo(UPDATED_NO_IDENTIFICATION);
        assertThat(testPart_concept.getQuanitty()).isEqualTo(UPDATED_QUANITTY);
        assertThat(testPart_concept.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPart_concept.getUnit_value()).isEqualTo(UPDATED_UNIT_VALUE);
        assertThat(testPart_concept.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deletePart_concept() throws Exception {
        // Initialize the database
        part_conceptService.save(part_concept);

        int databaseSizeBeforeDelete = part_conceptRepository.findAll().size();

        // Get the part_concept
        restPart_conceptMockMvc.perform(delete("/api/part-concepts/{id}", part_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Part_concept> part_concepts = part_conceptRepository.findAll();
        assertThat(part_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
