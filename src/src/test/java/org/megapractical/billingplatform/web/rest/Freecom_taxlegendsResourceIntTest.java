package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_taxlegends;
import org.megapractical.billingplatform.repository.Freecom_taxlegendsRepository;
import org.megapractical.billingplatform.service.Freecom_taxlegendsService;

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
 * Test class for the Freecom_taxlegendsResource REST controller.
 *
 * @see Freecom_taxlegendsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_taxlegendsResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    @Inject
    private Freecom_taxlegendsRepository freecom_taxlegendsRepository;

    @Inject
    private Freecom_taxlegendsService freecom_taxlegendsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_taxlegendsMockMvc;

    private Freecom_taxlegends freecom_taxlegends;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_taxlegendsResource freecom_taxlegendsResource = new Freecom_taxlegendsResource();
        ReflectionTestUtils.setField(freecom_taxlegendsResource, "freecom_taxlegendsService", freecom_taxlegendsService);
        this.restFreecom_taxlegendsMockMvc = MockMvcBuilders.standaloneSetup(freecom_taxlegendsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_taxlegends = new Freecom_taxlegends();
        freecom_taxlegends.setVersion(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createFreecom_taxlegends() throws Exception {
        int databaseSizeBeforeCreate = freecom_taxlegendsRepository.findAll().size();

        // Create the Freecom_taxlegends

        restFreecom_taxlegendsMockMvc.perform(post("/api/freecom-taxlegends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_taxlegends)))
                .andExpect(status().isCreated());

        // Validate the Freecom_taxlegends in the database
        List<Freecom_taxlegends> freecom_taxlegends = freecom_taxlegendsRepository.findAll();
        assertThat(freecom_taxlegends).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_taxlegends testFreecom_taxlegends = freecom_taxlegends.get(freecom_taxlegends.size() - 1);
        assertThat(testFreecom_taxlegends.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_taxlegendsRepository.findAll().size();
        // set the field null
        freecom_taxlegends.setVersion(null);

        // Create the Freecom_taxlegends, which fails.

        restFreecom_taxlegendsMockMvc.perform(post("/api/freecom-taxlegends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_taxlegends)))
                .andExpect(status().isBadRequest());

        List<Freecom_taxlegends> freecom_taxlegends = freecom_taxlegendsRepository.findAll();
        assertThat(freecom_taxlegends).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_taxlegends() throws Exception {
        // Initialize the database
        freecom_taxlegendsRepository.saveAndFlush(freecom_taxlegends);

        // Get all the freecom_taxlegends
        restFreecom_taxlegendsMockMvc.perform(get("/api/freecom-taxlegends?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_taxlegends.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_taxlegends() throws Exception {
        // Initialize the database
        freecom_taxlegendsRepository.saveAndFlush(freecom_taxlegends);

        // Get the freecom_taxlegends
        restFreecom_taxlegendsMockMvc.perform(get("/api/freecom-taxlegends/{id}", freecom_taxlegends.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_taxlegends.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_taxlegends() throws Exception {
        // Get the freecom_taxlegends
        restFreecom_taxlegendsMockMvc.perform(get("/api/freecom-taxlegends/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_taxlegends() throws Exception {
        // Initialize the database
        freecom_taxlegendsService.save(freecom_taxlegends);

        int databaseSizeBeforeUpdate = freecom_taxlegendsRepository.findAll().size();

        // Update the freecom_taxlegends
        Freecom_taxlegends updatedFreecom_taxlegends = new Freecom_taxlegends();
        updatedFreecom_taxlegends.setId(freecom_taxlegends.getId());
        updatedFreecom_taxlegends.setVersion(UPDATED_VERSION);

        restFreecom_taxlegendsMockMvc.perform(put("/api/freecom-taxlegends")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_taxlegends)))
                .andExpect(status().isOk());

        // Validate the Freecom_taxlegends in the database
        List<Freecom_taxlegends> freecom_taxlegends = freecom_taxlegendsRepository.findAll();
        assertThat(freecom_taxlegends).hasSize(databaseSizeBeforeUpdate);
        Freecom_taxlegends testFreecom_taxlegends = freecom_taxlegends.get(freecom_taxlegends.size() - 1);
        assertThat(testFreecom_taxlegends.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void deleteFreecom_taxlegends() throws Exception {
        // Initialize the database
        freecom_taxlegendsService.save(freecom_taxlegends);

        int databaseSizeBeforeDelete = freecom_taxlegendsRepository.findAll().size();

        // Get the freecom_taxlegends
        restFreecom_taxlegendsMockMvc.perform(delete("/api/freecom-taxlegends/{id}", freecom_taxlegends.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_taxlegends> freecom_taxlegends = freecom_taxlegendsRepository.findAll();
        assertThat(freecom_taxlegends).hasSize(databaseSizeBeforeDelete - 1);
    }
}
