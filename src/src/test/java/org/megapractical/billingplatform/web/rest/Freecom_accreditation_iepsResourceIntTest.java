package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_accreditation_ieps;
import org.megapractical.billingplatform.repository.Freecom_accreditation_iepsRepository;
import org.megapractical.billingplatform.service.Freecom_accreditation_iepsService;

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
 * Test class for the Freecom_accreditation_iepsResource REST controller.
 *
 * @see Freecom_accreditation_iepsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_accreditation_iepsResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    @Inject
    private Freecom_accreditation_iepsRepository freecom_accreditation_iepsRepository;

    @Inject
    private Freecom_accreditation_iepsService freecom_accreditation_iepsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_accreditation_iepsMockMvc;

    private Freecom_accreditation_ieps freecom_accreditation_ieps;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_accreditation_iepsResource freecom_accreditation_iepsResource = new Freecom_accreditation_iepsResource();
        ReflectionTestUtils.setField(freecom_accreditation_iepsResource, "freecom_accreditation_iepsService", freecom_accreditation_iepsService);
        this.restFreecom_accreditation_iepsMockMvc = MockMvcBuilders.standaloneSetup(freecom_accreditation_iepsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_accreditation_ieps = new Freecom_accreditation_ieps();
        freecom_accreditation_ieps.setVersion(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createFreecom_accreditation_ieps() throws Exception {
        int databaseSizeBeforeCreate = freecom_accreditation_iepsRepository.findAll().size();

        // Create the Freecom_accreditation_ieps

        restFreecom_accreditation_iepsMockMvc.perform(post("/api/freecom-accreditation-ieps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_accreditation_ieps)))
                .andExpect(status().isCreated());

        // Validate the Freecom_accreditation_ieps in the database
        List<Freecom_accreditation_ieps> freecom_accreditation_ieps = freecom_accreditation_iepsRepository.findAll();
        assertThat(freecom_accreditation_ieps).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_accreditation_ieps testFreecom_accreditation_ieps = freecom_accreditation_ieps.get(freecom_accreditation_ieps.size() - 1);
        assertThat(testFreecom_accreditation_ieps.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_accreditation_iepsRepository.findAll().size();
        // set the field null
        freecom_accreditation_ieps.setVersion(null);

        // Create the Freecom_accreditation_ieps, which fails.

        restFreecom_accreditation_iepsMockMvc.perform(post("/api/freecom-accreditation-ieps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_accreditation_ieps)))
                .andExpect(status().isBadRequest());

        List<Freecom_accreditation_ieps> freecom_accreditation_ieps = freecom_accreditation_iepsRepository.findAll();
        assertThat(freecom_accreditation_ieps).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_accreditation_ieps() throws Exception {
        // Initialize the database
        freecom_accreditation_iepsRepository.saveAndFlush(freecom_accreditation_ieps);

        // Get all the freecom_accreditation_ieps
        restFreecom_accreditation_iepsMockMvc.perform(get("/api/freecom-accreditation-ieps?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_accreditation_ieps.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_accreditation_ieps() throws Exception {
        // Initialize the database
        freecom_accreditation_iepsRepository.saveAndFlush(freecom_accreditation_ieps);

        // Get the freecom_accreditation_ieps
        restFreecom_accreditation_iepsMockMvc.perform(get("/api/freecom-accreditation-ieps/{id}", freecom_accreditation_ieps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_accreditation_ieps.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_accreditation_ieps() throws Exception {
        // Get the freecom_accreditation_ieps
        restFreecom_accreditation_iepsMockMvc.perform(get("/api/freecom-accreditation-ieps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_accreditation_ieps() throws Exception {
        // Initialize the database
        freecom_accreditation_iepsService.save(freecom_accreditation_ieps);

        int databaseSizeBeforeUpdate = freecom_accreditation_iepsRepository.findAll().size();

        // Update the freecom_accreditation_ieps
        Freecom_accreditation_ieps updatedFreecom_accreditation_ieps = new Freecom_accreditation_ieps();
        updatedFreecom_accreditation_ieps.setId(freecom_accreditation_ieps.getId());
        updatedFreecom_accreditation_ieps.setVersion(UPDATED_VERSION);

        restFreecom_accreditation_iepsMockMvc.perform(put("/api/freecom-accreditation-ieps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_accreditation_ieps)))
                .andExpect(status().isOk());

        // Validate the Freecom_accreditation_ieps in the database
        List<Freecom_accreditation_ieps> freecom_accreditation_ieps = freecom_accreditation_iepsRepository.findAll();
        assertThat(freecom_accreditation_ieps).hasSize(databaseSizeBeforeUpdate);
        Freecom_accreditation_ieps testFreecom_accreditation_ieps = freecom_accreditation_ieps.get(freecom_accreditation_ieps.size() - 1);
        assertThat(testFreecom_accreditation_ieps.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void deleteFreecom_accreditation_ieps() throws Exception {
        // Initialize the database
        freecom_accreditation_iepsService.save(freecom_accreditation_ieps);

        int databaseSizeBeforeDelete = freecom_accreditation_iepsRepository.findAll().size();

        // Get the freecom_accreditation_ieps
        restFreecom_accreditation_iepsMockMvc.perform(delete("/api/freecom-accreditation-ieps/{id}", freecom_accreditation_ieps.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_accreditation_ieps> freecom_accreditation_ieps = freecom_accreditation_iepsRepository.findAll();
        assertThat(freecom_accreditation_ieps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
