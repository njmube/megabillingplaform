package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_custom_unit;
import org.megapractical.billingplatform.repository.Freecom_custom_unitRepository;
import org.megapractical.billingplatform.service.Freecom_custom_unitService;

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
 * Test class for the Freecom_custom_unitResource REST controller.
 *
 * @see Freecom_custom_unitResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_custom_unitResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private Freecom_custom_unitRepository freecom_custom_unitRepository;

    @Inject
    private Freecom_custom_unitService freecom_custom_unitService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_custom_unitMockMvc;

    private Freecom_custom_unit freecom_custom_unit;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_custom_unitResource freecom_custom_unitResource = new Freecom_custom_unitResource();
        ReflectionTestUtils.setField(freecom_custom_unitResource, "freecom_custom_unitService", freecom_custom_unitService);
        this.restFreecom_custom_unitMockMvc = MockMvcBuilders.standaloneSetup(freecom_custom_unitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_custom_unit = new Freecom_custom_unit();
        freecom_custom_unit.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createFreecom_custom_unit() throws Exception {
        int databaseSizeBeforeCreate = freecom_custom_unitRepository.findAll().size();

        // Create the Freecom_custom_unit

        restFreecom_custom_unitMockMvc.perform(post("/api/freecom-custom-units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_custom_unit)))
                .andExpect(status().isCreated());

        // Validate the Freecom_custom_unit in the database
        List<Freecom_custom_unit> freecom_custom_units = freecom_custom_unitRepository.findAll();
        assertThat(freecom_custom_units).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_custom_unit testFreecom_custom_unit = freecom_custom_units.get(freecom_custom_units.size() - 1);
        assertThat(testFreecom_custom_unit.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_custom_unitRepository.findAll().size();
        // set the field null
        freecom_custom_unit.setValue(null);

        // Create the Freecom_custom_unit, which fails.

        restFreecom_custom_unitMockMvc.perform(post("/api/freecom-custom-units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_custom_unit)))
                .andExpect(status().isBadRequest());

        List<Freecom_custom_unit> freecom_custom_units = freecom_custom_unitRepository.findAll();
        assertThat(freecom_custom_units).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_custom_units() throws Exception {
        // Initialize the database
        freecom_custom_unitRepository.saveAndFlush(freecom_custom_unit);

        // Get all the freecom_custom_units
        restFreecom_custom_unitMockMvc.perform(get("/api/freecom-custom-units?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_custom_unit.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_custom_unit() throws Exception {
        // Initialize the database
        freecom_custom_unitRepository.saveAndFlush(freecom_custom_unit);

        // Get the freecom_custom_unit
        restFreecom_custom_unitMockMvc.perform(get("/api/freecom-custom-units/{id}", freecom_custom_unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_custom_unit.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_custom_unit() throws Exception {
        // Get the freecom_custom_unit
        restFreecom_custom_unitMockMvc.perform(get("/api/freecom-custom-units/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_custom_unit() throws Exception {
        // Initialize the database
        freecom_custom_unitService.save(freecom_custom_unit);

        int databaseSizeBeforeUpdate = freecom_custom_unitRepository.findAll().size();

        // Update the freecom_custom_unit
        Freecom_custom_unit updatedFreecom_custom_unit = new Freecom_custom_unit();
        updatedFreecom_custom_unit.setId(freecom_custom_unit.getId());
        updatedFreecom_custom_unit.setValue(UPDATED_VALUE);

        restFreecom_custom_unitMockMvc.perform(put("/api/freecom-custom-units")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_custom_unit)))
                .andExpect(status().isOk());

        // Validate the Freecom_custom_unit in the database
        List<Freecom_custom_unit> freecom_custom_units = freecom_custom_unitRepository.findAll();
        assertThat(freecom_custom_units).hasSize(databaseSizeBeforeUpdate);
        Freecom_custom_unit testFreecom_custom_unit = freecom_custom_units.get(freecom_custom_units.size() - 1);
        assertThat(testFreecom_custom_unit.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteFreecom_custom_unit() throws Exception {
        // Initialize the database
        freecom_custom_unitService.save(freecom_custom_unit);

        int databaseSizeBeforeDelete = freecom_custom_unitRepository.findAll().size();

        // Get the freecom_custom_unit
        restFreecom_custom_unitMockMvc.perform(delete("/api/freecom-custom-units/{id}", freecom_custom_unit.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_custom_unit> freecom_custom_units = freecom_custom_unitRepository.findAll();
        assertThat(freecom_custom_units).hasSize(databaseSizeBeforeDelete - 1);
    }
}
