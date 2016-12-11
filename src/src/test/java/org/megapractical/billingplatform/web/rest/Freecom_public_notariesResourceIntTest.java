package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_public_notaries;
import org.megapractical.billingplatform.repository.Freecom_public_notariesRepository;
import org.megapractical.billingplatform.service.Freecom_public_notariesService;

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
 * Test class for the Freecom_public_notariesResource REST controller.
 *
 * @see Freecom_public_notariesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_public_notariesResourceIntTest {

    private static final String DEFAULT_VERSION = "AAA";
    private static final String UPDATED_VERSION = "BBB";

    @Inject
    private Freecom_public_notariesRepository freecom_public_notariesRepository;

    @Inject
    private Freecom_public_notariesService freecom_public_notariesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_public_notariesMockMvc;

    private Freecom_public_notaries freecom_public_notaries;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_public_notariesResource freecom_public_notariesResource = new Freecom_public_notariesResource();
        ReflectionTestUtils.setField(freecom_public_notariesResource, "freecom_public_notariesService", freecom_public_notariesService);
        this.restFreecom_public_notariesMockMvc = MockMvcBuilders.standaloneSetup(freecom_public_notariesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_public_notaries = new Freecom_public_notaries();
        freecom_public_notaries.setVersion(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void createFreecom_public_notaries() throws Exception {
        int databaseSizeBeforeCreate = freecom_public_notariesRepository.findAll().size();

        // Create the Freecom_public_notaries

        restFreecom_public_notariesMockMvc.perform(post("/api/freecom-public-notaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_public_notaries)))
                .andExpect(status().isCreated());

        // Validate the Freecom_public_notaries in the database
        List<Freecom_public_notaries> freecom_public_notaries = freecom_public_notariesRepository.findAll();
        assertThat(freecom_public_notaries).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_public_notaries testFreecom_public_notaries = freecom_public_notaries.get(freecom_public_notaries.size() - 1);
        assertThat(testFreecom_public_notaries.getVersion()).isEqualTo(DEFAULT_VERSION);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_public_notariesRepository.findAll().size();
        // set the field null
        freecom_public_notaries.setVersion(null);

        // Create the Freecom_public_notaries, which fails.

        restFreecom_public_notariesMockMvc.perform(post("/api/freecom-public-notaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_public_notaries)))
                .andExpect(status().isBadRequest());

        List<Freecom_public_notaries> freecom_public_notaries = freecom_public_notariesRepository.findAll();
        assertThat(freecom_public_notaries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_public_notaries() throws Exception {
        // Initialize the database
        freecom_public_notariesRepository.saveAndFlush(freecom_public_notaries);

        // Get all the freecom_public_notaries
        restFreecom_public_notariesMockMvc.perform(get("/api/freecom-public-notaries?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_public_notaries.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_public_notaries() throws Exception {
        // Initialize the database
        freecom_public_notariesRepository.saveAndFlush(freecom_public_notaries);

        // Get the freecom_public_notaries
        restFreecom_public_notariesMockMvc.perform(get("/api/freecom-public-notaries/{id}", freecom_public_notaries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_public_notaries.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_public_notaries() throws Exception {
        // Get the freecom_public_notaries
        restFreecom_public_notariesMockMvc.perform(get("/api/freecom-public-notaries/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_public_notaries() throws Exception {
        // Initialize the database
        freecom_public_notariesService.save(freecom_public_notaries);

        int databaseSizeBeforeUpdate = freecom_public_notariesRepository.findAll().size();

        // Update the freecom_public_notaries
        Freecom_public_notaries updatedFreecom_public_notaries = new Freecom_public_notaries();
        updatedFreecom_public_notaries.setId(freecom_public_notaries.getId());
        updatedFreecom_public_notaries.setVersion(UPDATED_VERSION);

        restFreecom_public_notariesMockMvc.perform(put("/api/freecom-public-notaries")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_public_notaries)))
                .andExpect(status().isOk());

        // Validate the Freecom_public_notaries in the database
        List<Freecom_public_notaries> freecom_public_notaries = freecom_public_notariesRepository.findAll();
        assertThat(freecom_public_notaries).hasSize(databaseSizeBeforeUpdate);
        Freecom_public_notaries testFreecom_public_notaries = freecom_public_notaries.get(freecom_public_notaries.size() - 1);
        assertThat(testFreecom_public_notaries.getVersion()).isEqualTo(UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void deleteFreecom_public_notaries() throws Exception {
        // Initialize the database
        freecom_public_notariesService.save(freecom_public_notaries);

        int databaseSizeBeforeDelete = freecom_public_notariesRepository.findAll().size();

        // Get the freecom_public_notaries
        restFreecom_public_notariesMockMvc.perform(delete("/api/freecom-public-notaries/{id}", freecom_public_notaries.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_public_notaries> freecom_public_notaries = freecom_public_notariesRepository.findAll();
        assertThat(freecom_public_notaries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
