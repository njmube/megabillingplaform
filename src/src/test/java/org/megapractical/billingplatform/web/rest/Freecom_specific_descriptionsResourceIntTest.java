package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_specific_descriptions;
import org.megapractical.billingplatform.repository.Freecom_specific_descriptionsRepository;
import org.megapractical.billingplatform.service.Freecom_specific_descriptionsService;

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
 * Test class for the Freecom_specific_descriptionsResource REST controller.
 *
 * @see Freecom_specific_descriptionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_specific_descriptionsResourceIntTest {

    private static final String DEFAULT_BRAND = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_MODEL = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SUBMODEL = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SUBMODEL = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    @Inject
    private Freecom_specific_descriptionsRepository freecom_specific_descriptionsRepository;

    @Inject
    private Freecom_specific_descriptionsService freecom_specific_descriptionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_specific_descriptionsMockMvc;

    private Freecom_specific_descriptions freecom_specific_descriptions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_specific_descriptionsResource freecom_specific_descriptionsResource = new Freecom_specific_descriptionsResource();
        ReflectionTestUtils.setField(freecom_specific_descriptionsResource, "freecom_specific_descriptionsService", freecom_specific_descriptionsService);
        this.restFreecom_specific_descriptionsMockMvc = MockMvcBuilders.standaloneSetup(freecom_specific_descriptionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_specific_descriptions = new Freecom_specific_descriptions();
        freecom_specific_descriptions.setBrand(DEFAULT_BRAND);
        freecom_specific_descriptions.setModel(DEFAULT_MODEL);
        freecom_specific_descriptions.setSubmodel(DEFAULT_SUBMODEL);
        freecom_specific_descriptions.setSerial_number(DEFAULT_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void createFreecom_specific_descriptions() throws Exception {
        int databaseSizeBeforeCreate = freecom_specific_descriptionsRepository.findAll().size();

        // Create the Freecom_specific_descriptions

        restFreecom_specific_descriptionsMockMvc.perform(post("/api/freecom-specific-descriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_specific_descriptions)))
                .andExpect(status().isCreated());

        // Validate the Freecom_specific_descriptions in the database
        List<Freecom_specific_descriptions> freecom_specific_descriptions = freecom_specific_descriptionsRepository.findAll();
        assertThat(freecom_specific_descriptions).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_specific_descriptions testFreecom_specific_descriptions = freecom_specific_descriptions.get(freecom_specific_descriptions.size() - 1);
        assertThat(testFreecom_specific_descriptions.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testFreecom_specific_descriptions.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testFreecom_specific_descriptions.getSubmodel()).isEqualTo(DEFAULT_SUBMODEL);
        assertThat(testFreecom_specific_descriptions.getSerial_number()).isEqualTo(DEFAULT_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_specific_descriptionsRepository.findAll().size();
        // set the field null
        freecom_specific_descriptions.setBrand(null);

        // Create the Freecom_specific_descriptions, which fails.

        restFreecom_specific_descriptionsMockMvc.perform(post("/api/freecom-specific-descriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_specific_descriptions)))
                .andExpect(status().isBadRequest());

        List<Freecom_specific_descriptions> freecom_specific_descriptions = freecom_specific_descriptionsRepository.findAll();
        assertThat(freecom_specific_descriptions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_specific_descriptions() throws Exception {
        // Initialize the database
        freecom_specific_descriptionsRepository.saveAndFlush(freecom_specific_descriptions);

        // Get all the freecom_specific_descriptions
        restFreecom_specific_descriptionsMockMvc.perform(get("/api/freecom-specific-descriptions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_specific_descriptions.getId().intValue())))
                .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
                .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
                .andExpect(jsonPath("$.[*].submodel").value(hasItem(DEFAULT_SUBMODEL.toString())))
                .andExpect(jsonPath("$.[*].serial_number").value(hasItem(DEFAULT_SERIAL_NUMBER.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_specific_descriptions() throws Exception {
        // Initialize the database
        freecom_specific_descriptionsRepository.saveAndFlush(freecom_specific_descriptions);

        // Get the freecom_specific_descriptions
        restFreecom_specific_descriptionsMockMvc.perform(get("/api/freecom-specific-descriptions/{id}", freecom_specific_descriptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_specific_descriptions.getId().intValue()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.submodel").value(DEFAULT_SUBMODEL.toString()))
            .andExpect(jsonPath("$.serial_number").value(DEFAULT_SERIAL_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_specific_descriptions() throws Exception {
        // Get the freecom_specific_descriptions
        restFreecom_specific_descriptionsMockMvc.perform(get("/api/freecom-specific-descriptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_specific_descriptions() throws Exception {
        // Initialize the database
        freecom_specific_descriptionsService.save(freecom_specific_descriptions);

        int databaseSizeBeforeUpdate = freecom_specific_descriptionsRepository.findAll().size();

        // Update the freecom_specific_descriptions
        Freecom_specific_descriptions updatedFreecom_specific_descriptions = new Freecom_specific_descriptions();
        updatedFreecom_specific_descriptions.setId(freecom_specific_descriptions.getId());
        updatedFreecom_specific_descriptions.setBrand(UPDATED_BRAND);
        updatedFreecom_specific_descriptions.setModel(UPDATED_MODEL);
        updatedFreecom_specific_descriptions.setSubmodel(UPDATED_SUBMODEL);
        updatedFreecom_specific_descriptions.setSerial_number(UPDATED_SERIAL_NUMBER);

        restFreecom_specific_descriptionsMockMvc.perform(put("/api/freecom-specific-descriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_specific_descriptions)))
                .andExpect(status().isOk());

        // Validate the Freecom_specific_descriptions in the database
        List<Freecom_specific_descriptions> freecom_specific_descriptions = freecom_specific_descriptionsRepository.findAll();
        assertThat(freecom_specific_descriptions).hasSize(databaseSizeBeforeUpdate);
        Freecom_specific_descriptions testFreecom_specific_descriptions = freecom_specific_descriptions.get(freecom_specific_descriptions.size() - 1);
        assertThat(testFreecom_specific_descriptions.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testFreecom_specific_descriptions.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testFreecom_specific_descriptions.getSubmodel()).isEqualTo(UPDATED_SUBMODEL);
        assertThat(testFreecom_specific_descriptions.getSerial_number()).isEqualTo(UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void deleteFreecom_specific_descriptions() throws Exception {
        // Initialize the database
        freecom_specific_descriptionsService.save(freecom_specific_descriptions);

        int databaseSizeBeforeDelete = freecom_specific_descriptionsRepository.findAll().size();

        // Get the freecom_specific_descriptions
        restFreecom_specific_descriptionsMockMvc.perform(delete("/api/freecom-specific-descriptions/{id}", freecom_specific_descriptions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_specific_descriptions> freecom_specific_descriptions = freecom_specific_descriptionsRepository.findAll();
        assertThat(freecom_specific_descriptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
