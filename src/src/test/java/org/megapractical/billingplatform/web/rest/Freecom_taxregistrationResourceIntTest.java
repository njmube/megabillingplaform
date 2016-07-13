package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_taxregistration;
import org.megapractical.billingplatform.repository.Freecom_taxregistrationRepository;
import org.megapractical.billingplatform.service.Freecom_taxregistrationService;

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
 * Test class for the Freecom_taxregistrationResource REST controller.
 *
 * @see Freecom_taxregistrationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_taxregistrationResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_FOLIO = "AAAAA";
    private static final String UPDATED_FOLIO = "BBBBB";

    @Inject
    private Freecom_taxregistrationRepository freecom_taxregistrationRepository;

    @Inject
    private Freecom_taxregistrationService freecom_taxregistrationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_taxregistrationMockMvc;

    private Freecom_taxregistration freecom_taxregistration;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_taxregistrationResource freecom_taxregistrationResource = new Freecom_taxregistrationResource();
        ReflectionTestUtils.setField(freecom_taxregistrationResource, "freecom_taxregistrationService", freecom_taxregistrationService);
        this.restFreecom_taxregistrationMockMvc = MockMvcBuilders.standaloneSetup(freecom_taxregistrationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_taxregistration = new Freecom_taxregistration();
        freecom_taxregistration.setVersion(DEFAULT_VERSION);
        freecom_taxregistration.setFolio(DEFAULT_FOLIO);
    }

    @Test
    @Transactional
    public void createFreecom_taxregistration() throws Exception {
        int databaseSizeBeforeCreate = freecom_taxregistrationRepository.findAll().size();

        // Create the Freecom_taxregistration

        restFreecom_taxregistrationMockMvc.perform(post("/api/freecom-taxregistrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_taxregistration)))
                .andExpect(status().isCreated());

        // Validate the Freecom_taxregistration in the database
        List<Freecom_taxregistration> freecom_taxregistrations = freecom_taxregistrationRepository.findAll();
        assertThat(freecom_taxregistrations).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_taxregistration testFreecom_taxregistration = freecom_taxregistrations.get(freecom_taxregistrations.size() - 1);
        assertThat(testFreecom_taxregistration.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_taxregistration.getFolio()).isEqualTo(DEFAULT_FOLIO);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_taxregistrationRepository.findAll().size();
        // set the field null
        freecom_taxregistration.setVersion(null);

        // Create the Freecom_taxregistration, which fails.

        restFreecom_taxregistrationMockMvc.perform(post("/api/freecom-taxregistrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_taxregistration)))
                .andExpect(status().isBadRequest());

        List<Freecom_taxregistration> freecom_taxregistrations = freecom_taxregistrationRepository.findAll();
        assertThat(freecom_taxregistrations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolioIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_taxregistrationRepository.findAll().size();
        // set the field null
        freecom_taxregistration.setFolio(null);

        // Create the Freecom_taxregistration, which fails.

        restFreecom_taxregistrationMockMvc.perform(post("/api/freecom-taxregistrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_taxregistration)))
                .andExpect(status().isBadRequest());

        List<Freecom_taxregistration> freecom_taxregistrations = freecom_taxregistrationRepository.findAll();
        assertThat(freecom_taxregistrations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_taxregistrations() throws Exception {
        // Initialize the database
        freecom_taxregistrationRepository.saveAndFlush(freecom_taxregistration);

        // Get all the freecom_taxregistrations
        restFreecom_taxregistrationMockMvc.perform(get("/api/freecom-taxregistrations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_taxregistration.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].folio").value(hasItem(DEFAULT_FOLIO.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_taxregistration() throws Exception {
        // Initialize the database
        freecom_taxregistrationRepository.saveAndFlush(freecom_taxregistration);

        // Get the freecom_taxregistration
        restFreecom_taxregistrationMockMvc.perform(get("/api/freecom-taxregistrations/{id}", freecom_taxregistration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_taxregistration.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.folio").value(DEFAULT_FOLIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_taxregistration() throws Exception {
        // Get the freecom_taxregistration
        restFreecom_taxregistrationMockMvc.perform(get("/api/freecom-taxregistrations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_taxregistration() throws Exception {
        // Initialize the database
        freecom_taxregistrationService.save(freecom_taxregistration);

        int databaseSizeBeforeUpdate = freecom_taxregistrationRepository.findAll().size();

        // Update the freecom_taxregistration
        Freecom_taxregistration updatedFreecom_taxregistration = new Freecom_taxregistration();
        updatedFreecom_taxregistration.setId(freecom_taxregistration.getId());
        updatedFreecom_taxregistration.setVersion(UPDATED_VERSION);
        updatedFreecom_taxregistration.setFolio(UPDATED_FOLIO);

        restFreecom_taxregistrationMockMvc.perform(put("/api/freecom-taxregistrations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_taxregistration)))
                .andExpect(status().isOk());

        // Validate the Freecom_taxregistration in the database
        List<Freecom_taxregistration> freecom_taxregistrations = freecom_taxregistrationRepository.findAll();
        assertThat(freecom_taxregistrations).hasSize(databaseSizeBeforeUpdate);
        Freecom_taxregistration testFreecom_taxregistration = freecom_taxregistrations.get(freecom_taxregistrations.size() - 1);
        assertThat(testFreecom_taxregistration.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_taxregistration.getFolio()).isEqualTo(UPDATED_FOLIO);
    }

    @Test
    @Transactional
    public void deleteFreecom_taxregistration() throws Exception {
        // Initialize the database
        freecom_taxregistrationService.save(freecom_taxregistration);

        int databaseSizeBeforeDelete = freecom_taxregistrationRepository.findAll().size();

        // Get the freecom_taxregistration
        restFreecom_taxregistrationMockMvc.perform(delete("/api/freecom-taxregistrations/{id}", freecom_taxregistration.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_taxregistration> freecom_taxregistrations = freecom_taxregistrationRepository.findAll();
        assertThat(freecom_taxregistrations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
