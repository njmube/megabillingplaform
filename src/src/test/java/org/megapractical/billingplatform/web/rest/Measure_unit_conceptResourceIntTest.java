package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Measure_unit_concept;
import org.megapractical.billingplatform.repository.Measure_unit_conceptRepository;
import org.megapractical.billingplatform.service.Measure_unit_conceptService;

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
 * Test class for the Measure_unit_conceptResource REST controller.
 *
 * @see Measure_unit_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Measure_unit_conceptResourceIntTest {


    @Inject
    private Measure_unit_conceptRepository measure_unit_conceptRepository;

    @Inject
    private Measure_unit_conceptService measure_unit_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMeasure_unit_conceptMockMvc;

    private Measure_unit_concept measure_unit_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Measure_unit_conceptResource measure_unit_conceptResource = new Measure_unit_conceptResource();
        ReflectionTestUtils.setField(measure_unit_conceptResource, "measure_unit_conceptService", measure_unit_conceptService);
        this.restMeasure_unit_conceptMockMvc = MockMvcBuilders.standaloneSetup(measure_unit_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        measure_unit_concept = new Measure_unit_concept();
    }

    @Test
    @Transactional
    public void createMeasure_unit_concept() throws Exception {
        int databaseSizeBeforeCreate = measure_unit_conceptRepository.findAll().size();

        // Create the Measure_unit_concept

        restMeasure_unit_conceptMockMvc.perform(post("/api/measure-unit-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(measure_unit_concept)))
                .andExpect(status().isCreated());

        // Validate the Measure_unit_concept in the database
        List<Measure_unit_concept> measure_unit_concepts = measure_unit_conceptRepository.findAll();
        assertThat(measure_unit_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Measure_unit_concept testMeasure_unit_concept = measure_unit_concepts.get(measure_unit_concepts.size() - 1);
    }

    @Test
    @Transactional
    public void getAllMeasure_unit_concepts() throws Exception {
        // Initialize the database
        measure_unit_conceptRepository.saveAndFlush(measure_unit_concept);

        // Get all the measure_unit_concepts
        restMeasure_unit_conceptMockMvc.perform(get("/api/measure-unit-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(measure_unit_concept.getId().intValue())));
    }

    @Test
    @Transactional
    public void getMeasure_unit_concept() throws Exception {
        // Initialize the database
        measure_unit_conceptRepository.saveAndFlush(measure_unit_concept);

        // Get the measure_unit_concept
        restMeasure_unit_conceptMockMvc.perform(get("/api/measure-unit-concepts/{id}", measure_unit_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(measure_unit_concept.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMeasure_unit_concept() throws Exception {
        // Get the measure_unit_concept
        restMeasure_unit_conceptMockMvc.perform(get("/api/measure-unit-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeasure_unit_concept() throws Exception {
        // Initialize the database
        measure_unit_conceptService.save(measure_unit_concept);

        int databaseSizeBeforeUpdate = measure_unit_conceptRepository.findAll().size();

        // Update the measure_unit_concept
        Measure_unit_concept updatedMeasure_unit_concept = new Measure_unit_concept();
        updatedMeasure_unit_concept.setId(measure_unit_concept.getId());

        restMeasure_unit_conceptMockMvc.perform(put("/api/measure-unit-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMeasure_unit_concept)))
                .andExpect(status().isOk());

        // Validate the Measure_unit_concept in the database
        List<Measure_unit_concept> measure_unit_concepts = measure_unit_conceptRepository.findAll();
        assertThat(measure_unit_concepts).hasSize(databaseSizeBeforeUpdate);
        Measure_unit_concept testMeasure_unit_concept = measure_unit_concepts.get(measure_unit_concepts.size() - 1);
    }

    @Test
    @Transactional
    public void deleteMeasure_unit_concept() throws Exception {
        // Initialize the database
        measure_unit_conceptService.save(measure_unit_concept);

        int databaseSizeBeforeDelete = measure_unit_conceptRepository.findAll().size();

        // Get the measure_unit_concept
        restMeasure_unit_conceptMockMvc.perform(delete("/api/measure-unit-concepts/{id}", measure_unit_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Measure_unit_concept> measure_unit_concepts = measure_unit_conceptRepository.findAll();
        assertThat(measure_unit_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
