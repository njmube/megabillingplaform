package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_tariff_fraction;
import org.megapractical.billingplatform.repository.Freecom_tariff_fractionRepository;
import org.megapractical.billingplatform.service.Freecom_tariff_fractionService;

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
 * Test class for the Freecom_tariff_fractionResource REST controller.
 *
 * @see Freecom_tariff_fractionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_tariff_fractionResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private Freecom_tariff_fractionRepository freecom_tariff_fractionRepository;

    @Inject
    private Freecom_tariff_fractionService freecom_tariff_fractionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_tariff_fractionMockMvc;

    private Freecom_tariff_fraction freecom_tariff_fraction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_tariff_fractionResource freecom_tariff_fractionResource = new Freecom_tariff_fractionResource();
        ReflectionTestUtils.setField(freecom_tariff_fractionResource, "freecom_tariff_fractionService", freecom_tariff_fractionService);
        this.restFreecom_tariff_fractionMockMvc = MockMvcBuilders.standaloneSetup(freecom_tariff_fractionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_tariff_fraction = new Freecom_tariff_fraction();
        freecom_tariff_fraction.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createFreecom_tariff_fraction() throws Exception {
        int databaseSizeBeforeCreate = freecom_tariff_fractionRepository.findAll().size();

        // Create the Freecom_tariff_fraction

        restFreecom_tariff_fractionMockMvc.perform(post("/api/freecom-tariff-fractions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tariff_fraction)))
                .andExpect(status().isCreated());

        // Validate the Freecom_tariff_fraction in the database
        List<Freecom_tariff_fraction> freecom_tariff_fractions = freecom_tariff_fractionRepository.findAll();
        assertThat(freecom_tariff_fractions).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_tariff_fraction testFreecom_tariff_fraction = freecom_tariff_fractions.get(freecom_tariff_fractions.size() - 1);
        assertThat(testFreecom_tariff_fraction.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_tariff_fractionRepository.findAll().size();
        // set the field null
        freecom_tariff_fraction.setValue(null);

        // Create the Freecom_tariff_fraction, which fails.

        restFreecom_tariff_fractionMockMvc.perform(post("/api/freecom-tariff-fractions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tariff_fraction)))
                .andExpect(status().isBadRequest());

        List<Freecom_tariff_fraction> freecom_tariff_fractions = freecom_tariff_fractionRepository.findAll();
        assertThat(freecom_tariff_fractions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_tariff_fractions() throws Exception {
        // Initialize the database
        freecom_tariff_fractionRepository.saveAndFlush(freecom_tariff_fraction);

        // Get all the freecom_tariff_fractions
        restFreecom_tariff_fractionMockMvc.perform(get("/api/freecom-tariff-fractions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_tariff_fraction.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_tariff_fraction() throws Exception {
        // Initialize the database
        freecom_tariff_fractionRepository.saveAndFlush(freecom_tariff_fraction);

        // Get the freecom_tariff_fraction
        restFreecom_tariff_fractionMockMvc.perform(get("/api/freecom-tariff-fractions/{id}", freecom_tariff_fraction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_tariff_fraction.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_tariff_fraction() throws Exception {
        // Get the freecom_tariff_fraction
        restFreecom_tariff_fractionMockMvc.perform(get("/api/freecom-tariff-fractions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_tariff_fraction() throws Exception {
        // Initialize the database
        freecom_tariff_fractionService.save(freecom_tariff_fraction);

        int databaseSizeBeforeUpdate = freecom_tariff_fractionRepository.findAll().size();

        // Update the freecom_tariff_fraction
        Freecom_tariff_fraction updatedFreecom_tariff_fraction = new Freecom_tariff_fraction();
        updatedFreecom_tariff_fraction.setId(freecom_tariff_fraction.getId());
        updatedFreecom_tariff_fraction.setValue(UPDATED_VALUE);

        restFreecom_tariff_fractionMockMvc.perform(put("/api/freecom-tariff-fractions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_tariff_fraction)))
                .andExpect(status().isOk());

        // Validate the Freecom_tariff_fraction in the database
        List<Freecom_tariff_fraction> freecom_tariff_fractions = freecom_tariff_fractionRepository.findAll();
        assertThat(freecom_tariff_fractions).hasSize(databaseSizeBeforeUpdate);
        Freecom_tariff_fraction testFreecom_tariff_fraction = freecom_tariff_fractions.get(freecom_tariff_fractions.size() - 1);
        assertThat(testFreecom_tariff_fraction.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteFreecom_tariff_fraction() throws Exception {
        // Initialize the database
        freecom_tariff_fractionService.save(freecom_tariff_fraction);

        int databaseSizeBeforeDelete = freecom_tariff_fractionRepository.findAll().size();

        // Get the freecom_tariff_fraction
        restFreecom_tariff_fractionMockMvc.perform(delete("/api/freecom-tariff-fractions/{id}", freecom_tariff_fraction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_tariff_fraction> freecom_tariff_fractions = freecom_tariff_fractionRepository.findAll();
        assertThat(freecom_tariff_fractions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
