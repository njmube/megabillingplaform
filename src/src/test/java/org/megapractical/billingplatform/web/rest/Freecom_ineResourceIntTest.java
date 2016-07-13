package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_ine;
import org.megapractical.billingplatform.repository.Freecom_ineRepository;
import org.megapractical.billingplatform.service.Freecom_ineService;

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
 * Test class for the Freecom_ineResource REST controller.
 *
 * @see Freecom_ineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_ineResourceIntTest {

    private static final String DEFAULT_VERSION = "AAA";
    private static final String UPDATED_VERSION = "BBB";

    private static final Integer DEFAULT_IDENT = 1;
    private static final Integer UPDATED_IDENT = 2;

    @Inject
    private Freecom_ineRepository freecom_ineRepository;

    @Inject
    private Freecom_ineService freecom_ineService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_ineMockMvc;

    private Freecom_ine freecom_ine;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_ineResource freecom_ineResource = new Freecom_ineResource();
        ReflectionTestUtils.setField(freecom_ineResource, "freecom_ineService", freecom_ineService);
        this.restFreecom_ineMockMvc = MockMvcBuilders.standaloneSetup(freecom_ineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_ine = new Freecom_ine();
        freecom_ine.setVersion(DEFAULT_VERSION);
        freecom_ine.setIdent(DEFAULT_IDENT);
    }

    @Test
    @Transactional
    public void createFreecom_ine() throws Exception {
        int databaseSizeBeforeCreate = freecom_ineRepository.findAll().size();

        // Create the Freecom_ine

        restFreecom_ineMockMvc.perform(post("/api/freecom-ines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ine)))
                .andExpect(status().isCreated());

        // Validate the Freecom_ine in the database
        List<Freecom_ine> freecom_ines = freecom_ineRepository.findAll();
        assertThat(freecom_ines).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_ine testFreecom_ine = freecom_ines.get(freecom_ines.size() - 1);
        assertThat(testFreecom_ine.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_ine.getIdent()).isEqualTo(DEFAULT_IDENT);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ineRepository.findAll().size();
        // set the field null
        freecom_ine.setVersion(null);

        // Create the Freecom_ine, which fails.

        restFreecom_ineMockMvc.perform(post("/api/freecom-ines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ine)))
                .andExpect(status().isBadRequest());

        List<Freecom_ine> freecom_ines = freecom_ineRepository.findAll();
        assertThat(freecom_ines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_ines() throws Exception {
        // Initialize the database
        freecom_ineRepository.saveAndFlush(freecom_ine);

        // Get all the freecom_ines
        restFreecom_ineMockMvc.perform(get("/api/freecom-ines?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_ine.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].ident").value(hasItem(DEFAULT_IDENT)));
    }

    @Test
    @Transactional
    public void getFreecom_ine() throws Exception {
        // Initialize the database
        freecom_ineRepository.saveAndFlush(freecom_ine);

        // Get the freecom_ine
        restFreecom_ineMockMvc.perform(get("/api/freecom-ines/{id}", freecom_ine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_ine.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.ident").value(DEFAULT_IDENT));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_ine() throws Exception {
        // Get the freecom_ine
        restFreecom_ineMockMvc.perform(get("/api/freecom-ines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_ine() throws Exception {
        // Initialize the database
        freecom_ineService.save(freecom_ine);

        int databaseSizeBeforeUpdate = freecom_ineRepository.findAll().size();

        // Update the freecom_ine
        Freecom_ine updatedFreecom_ine = new Freecom_ine();
        updatedFreecom_ine.setId(freecom_ine.getId());
        updatedFreecom_ine.setVersion(UPDATED_VERSION);
        updatedFreecom_ine.setIdent(UPDATED_IDENT);

        restFreecom_ineMockMvc.perform(put("/api/freecom-ines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_ine)))
                .andExpect(status().isOk());

        // Validate the Freecom_ine in the database
        List<Freecom_ine> freecom_ines = freecom_ineRepository.findAll();
        assertThat(freecom_ines).hasSize(databaseSizeBeforeUpdate);
        Freecom_ine testFreecom_ine = freecom_ines.get(freecom_ines.size() - 1);
        assertThat(testFreecom_ine.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_ine.getIdent()).isEqualTo(UPDATED_IDENT);
    }

    @Test
    @Transactional
    public void deleteFreecom_ine() throws Exception {
        // Initialize the database
        freecom_ineService.save(freecom_ine);

        int databaseSizeBeforeDelete = freecom_ineRepository.findAll().size();

        // Get the freecom_ine
        restFreecom_ineMockMvc.perform(delete("/api/freecom-ines/{id}", freecom_ine.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_ine> freecom_ines = freecom_ineRepository.findAll();
        assertThat(freecom_ines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
