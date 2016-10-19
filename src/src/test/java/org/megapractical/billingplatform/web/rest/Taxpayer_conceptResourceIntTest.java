package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Taxpayer_concept;
import org.megapractical.billingplatform.repository.Taxpayer_conceptRepository;
import org.megapractical.billingplatform.service.Taxpayer_conceptService;

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
 * Test class for the Taxpayer_conceptResource REST controller.
 *
 * @see Taxpayer_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Taxpayer_conceptResourceIntTest {

    private static final String DEFAULT_NO_IDENTIFICATION = "AAAAA";
    private static final String UPDATED_NO_IDENTIFICATION = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_PREDIAL_NUMBER = "AAAAA";
    private static final String UPDATED_PREDIAL_NUMBER = "BBBBB";

    @Inject
    private Taxpayer_conceptRepository taxpayer_conceptRepository;

    @Inject
    private Taxpayer_conceptService taxpayer_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxpayer_conceptMockMvc;

    private Taxpayer_concept taxpayer_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Taxpayer_conceptResource taxpayer_conceptResource = new Taxpayer_conceptResource();
        ReflectionTestUtils.setField(taxpayer_conceptResource, "taxpayer_conceptService", taxpayer_conceptService);
        this.restTaxpayer_conceptMockMvc = MockMvcBuilders.standaloneSetup(taxpayer_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        taxpayer_concept = new Taxpayer_concept();
        taxpayer_concept.setNo_identification(DEFAULT_NO_IDENTIFICATION);
        taxpayer_concept.setDescription(DEFAULT_DESCRIPTION);
        taxpayer_concept.setPredial_number(DEFAULT_PREDIAL_NUMBER);
    }

    @Test
    @Transactional
    public void createTaxpayer_concept() throws Exception {
        int databaseSizeBeforeCreate = taxpayer_conceptRepository.findAll().size();

        // Create the Taxpayer_concept

        restTaxpayer_conceptMockMvc.perform(post("/api/taxpayer-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_concept)))
                .andExpect(status().isCreated());

        // Validate the Taxpayer_concept in the database
        List<Taxpayer_concept> taxpayer_concepts = taxpayer_conceptRepository.findAll();
        assertThat(taxpayer_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Taxpayer_concept testTaxpayer_concept = taxpayer_concepts.get(taxpayer_concepts.size() - 1);
        assertThat(testTaxpayer_concept.getNo_identification()).isEqualTo(DEFAULT_NO_IDENTIFICATION);
        assertThat(testTaxpayer_concept.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTaxpayer_concept.getPredial_number()).isEqualTo(DEFAULT_PREDIAL_NUMBER);
    }

    @Test
    @Transactional
    public void checkNo_identificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_conceptRepository.findAll().size();
        // set the field null
        taxpayer_concept.setNo_identification(null);

        // Create the Taxpayer_concept, which fails.

        restTaxpayer_conceptMockMvc.perform(post("/api/taxpayer-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_concept)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_concept> taxpayer_concepts = taxpayer_conceptRepository.findAll();
        assertThat(taxpayer_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxpayer_concepts() throws Exception {
        // Initialize the database
        taxpayer_conceptRepository.saveAndFlush(taxpayer_concept);

        // Get all the taxpayer_concepts
        restTaxpayer_conceptMockMvc.perform(get("/api/taxpayer-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taxpayer_concept.getId().intValue())))
                .andExpect(jsonPath("$.[*].no_identification").value(hasItem(DEFAULT_NO_IDENTIFICATION.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].predial_number").value(hasItem(DEFAULT_PREDIAL_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getTaxpayer_concept() throws Exception {
        // Initialize the database
        taxpayer_conceptRepository.saveAndFlush(taxpayer_concept);

        // Get the taxpayer_concept
        restTaxpayer_conceptMockMvc.perform(get("/api/taxpayer-concepts/{id}", taxpayer_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taxpayer_concept.getId().intValue()))
            .andExpect(jsonPath("$.no_identification").value(DEFAULT_NO_IDENTIFICATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.predial_number").value(DEFAULT_PREDIAL_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxpayer_concept() throws Exception {
        // Get the taxpayer_concept
        restTaxpayer_conceptMockMvc.perform(get("/api/taxpayer-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxpayer_concept() throws Exception {
        // Initialize the database
        taxpayer_conceptService.save(taxpayer_concept);

        int databaseSizeBeforeUpdate = taxpayer_conceptRepository.findAll().size();

        // Update the taxpayer_concept
        Taxpayer_concept updatedTaxpayer_concept = new Taxpayer_concept();
        updatedTaxpayer_concept.setId(taxpayer_concept.getId());
        updatedTaxpayer_concept.setNo_identification(UPDATED_NO_IDENTIFICATION);
        updatedTaxpayer_concept.setDescription(UPDATED_DESCRIPTION);
        updatedTaxpayer_concept.setPredial_number(UPDATED_PREDIAL_NUMBER);

        restTaxpayer_conceptMockMvc.perform(put("/api/taxpayer-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTaxpayer_concept)))
                .andExpect(status().isOk());

        // Validate the Taxpayer_concept in the database
        List<Taxpayer_concept> taxpayer_concepts = taxpayer_conceptRepository.findAll();
        assertThat(taxpayer_concepts).hasSize(databaseSizeBeforeUpdate);
        Taxpayer_concept testTaxpayer_concept = taxpayer_concepts.get(taxpayer_concepts.size() - 1);
        assertThat(testTaxpayer_concept.getNo_identification()).isEqualTo(UPDATED_NO_IDENTIFICATION);
        assertThat(testTaxpayer_concept.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTaxpayer_concept.getPredial_number()).isEqualTo(UPDATED_PREDIAL_NUMBER);
    }

    @Test
    @Transactional
    public void deleteTaxpayer_concept() throws Exception {
        // Initialize the database
        taxpayer_conceptService.save(taxpayer_concept);

        int databaseSizeBeforeDelete = taxpayer_conceptRepository.findAll().size();

        // Get the taxpayer_concept
        restTaxpayer_conceptMockMvc.perform(delete("/api/taxpayer-concepts/{id}", taxpayer_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Taxpayer_concept> taxpayer_concepts = taxpayer_conceptRepository.findAll();
        assertThat(taxpayer_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
