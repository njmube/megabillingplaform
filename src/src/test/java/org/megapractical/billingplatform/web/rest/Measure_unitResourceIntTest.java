package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Measure_unit;
import org.megapractical.billingplatform.repository.Measure_unitRepository;
import org.megapractical.billingplatform.service.Measure_unitService;

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
 * Test class for the Measure_unitResource REST controller.
 *
 * @see Measure_unitResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Measure_unitResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Measure_unitRepository measure_unitRepository;

    @Inject
    private Measure_unitService measure_unitService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMeasure_unitMockMvc;

    private Measure_unit measure_unit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Measure_unitResource measure_unitResource = new Measure_unitResource();
        ReflectionTestUtils.setField(measure_unitResource, "measure_unitService", measure_unitService);
        this.restMeasure_unitMockMvc = MockMvcBuilders.standaloneSetup(measure_unitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        measure_unit = new Measure_unit();
        measure_unit.setName(DEFAULT_NAME);
        measure_unit.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createMeasure_unit() throws Exception {
        int databaseSizeBeforeCreate = measure_unitRepository.findAll().size();

        // Create the Measure_unit

        restMeasure_unitMockMvc.perform(post("/api/measure-units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(measure_unit)))
                .andExpect(status().isCreated());

        // Validate the Measure_unit in the database
        List<Measure_unit> measure_units = measure_unitRepository.findAll();
        assertThat(measure_units).hasSize(databaseSizeBeforeCreate + 1);
        Measure_unit testMeasure_unit = measure_units.get(measure_units.size() - 1);
        assertThat(testMeasure_unit.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMeasure_unit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllMeasure_units() throws Exception {
        /*
        // Initialize the database
        measure_unitRepository.saveAndFlush(measure_unit);

        // Get all the measure_units
        restMeasure_unitMockMvc.perform(get("/api/measure-units?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(measure_unit.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));*/
    }

    @Test
    @Transactional
    public void getMeasure_unit() throws Exception {
        // Initialize the database
        measure_unitRepository.saveAndFlush(measure_unit);

        // Get the measure_unit
        restMeasure_unitMockMvc.perform(get("/api/measure-units/{id}", measure_unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(measure_unit.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMeasure_unit() throws Exception {
        // Get the measure_unit
        restMeasure_unitMockMvc.perform(get("/api/measure-units/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeasure_unit() throws Exception {
        // Initialize the database
        measure_unitService.save(measure_unit);

        int databaseSizeBeforeUpdate = measure_unitRepository.findAll().size();

        // Update the measure_unit
        Measure_unit updatedMeasure_unit = new Measure_unit();
        updatedMeasure_unit.setId(measure_unit.getId());
        updatedMeasure_unit.setName(UPDATED_NAME);
        updatedMeasure_unit.setDescription(UPDATED_DESCRIPTION);

        restMeasure_unitMockMvc.perform(put("/api/measure-units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMeasure_unit)))
                .andExpect(status().isOk());

        // Validate the Measure_unit in the database
        List<Measure_unit> measure_units = measure_unitRepository.findAll();
        assertThat(measure_units).hasSize(databaseSizeBeforeUpdate);
        Measure_unit testMeasure_unit = measure_units.get(measure_units.size() - 1);
        assertThat(testMeasure_unit.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMeasure_unit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteMeasure_unit() throws Exception {
        // Initialize the database
        measure_unitService.save(measure_unit);

        int databaseSizeBeforeDelete = measure_unitRepository.findAll().size();

        // Get the measure_unit
        restMeasure_unitMockMvc.perform(delete("/api/measure-units/{id}", measure_unit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Measure_unit> measure_units = measure_unitRepository.findAll();
        assertThat(measure_units).hasSize(databaseSizeBeforeDelete - 1);
    }
}
