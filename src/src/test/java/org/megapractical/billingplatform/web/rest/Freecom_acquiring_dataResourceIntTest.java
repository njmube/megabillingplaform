package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_acquiring_data;
import org.megapractical.billingplatform.repository.Freecom_acquiring_dataRepository;
import org.megapractical.billingplatform.service.Freecom_acquiring_dataService;

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
 * Test class for the Freecom_acquiring_dataResource REST controller.
 *
 * @see Freecom_acquiring_dataResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_acquiring_dataResourceIntTest {

    private static final String DEFAULT_COPROSOCCONYUGAIE = "AAAAAAAAAA";
    private static final String UPDATED_COPROSOCCONYUGAIE = "BBBBBBBBBB";

    @Inject
    private Freecom_acquiring_dataRepository freecom_acquiring_dataRepository;

    @Inject
    private Freecom_acquiring_dataService freecom_acquiring_dataService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_acquiring_dataMockMvc;

    private Freecom_acquiring_data freecom_acquiring_data;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_acquiring_dataResource freecom_acquiring_dataResource = new Freecom_acquiring_dataResource();
        ReflectionTestUtils.setField(freecom_acquiring_dataResource, "freecom_acquiring_dataService", freecom_acquiring_dataService);
        this.restFreecom_acquiring_dataMockMvc = MockMvcBuilders.standaloneSetup(freecom_acquiring_dataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_acquiring_data = new Freecom_acquiring_data();
        freecom_acquiring_data.setCoprosocconyugaie(DEFAULT_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void createFreecom_acquiring_data() throws Exception {
        int databaseSizeBeforeCreate = freecom_acquiring_dataRepository.findAll().size();

        // Create the Freecom_acquiring_data

        restFreecom_acquiring_dataMockMvc.perform(post("/api/freecom-acquiring-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_acquiring_data)))
                .andExpect(status().isCreated());

        // Validate the Freecom_acquiring_data in the database
        List<Freecom_acquiring_data> freecom_acquiring_data = freecom_acquiring_dataRepository.findAll();
        assertThat(freecom_acquiring_data).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_acquiring_data testFreecom_acquiring_data = freecom_acquiring_data.get(freecom_acquiring_data.size() - 1);
        assertThat(testFreecom_acquiring_data.getCoprosocconyugaie()).isEqualTo(DEFAULT_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void checkCoprosocconyugaieIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_acquiring_dataRepository.findAll().size();
        // set the field null
        freecom_acquiring_data.setCoprosocconyugaie(null);

        // Create the Freecom_acquiring_data, which fails.

        restFreecom_acquiring_dataMockMvc.perform(post("/api/freecom-acquiring-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_acquiring_data)))
                .andExpect(status().isBadRequest());

        List<Freecom_acquiring_data> freecom_acquiring_data = freecom_acquiring_dataRepository.findAll();
        assertThat(freecom_acquiring_data).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_acquiring_data() throws Exception {
        // Initialize the database
        freecom_acquiring_dataRepository.saveAndFlush(freecom_acquiring_data);

        // Get all the freecom_acquiring_data
        restFreecom_acquiring_dataMockMvc.perform(get("/api/freecom-acquiring-data?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_acquiring_data.getId().intValue())))
                .andExpect(jsonPath("$.[*].coprosocconyugaie").value(hasItem(DEFAULT_COPROSOCCONYUGAIE.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_acquiring_data() throws Exception {
        // Initialize the database
        freecom_acquiring_dataRepository.saveAndFlush(freecom_acquiring_data);

        // Get the freecom_acquiring_data
        restFreecom_acquiring_dataMockMvc.perform(get("/api/freecom-acquiring-data/{id}", freecom_acquiring_data.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_acquiring_data.getId().intValue()))
            .andExpect(jsonPath("$.coprosocconyugaie").value(DEFAULT_COPROSOCCONYUGAIE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_acquiring_data() throws Exception {
        // Get the freecom_acquiring_data
        restFreecom_acquiring_dataMockMvc.perform(get("/api/freecom-acquiring-data/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_acquiring_data() throws Exception {
        // Initialize the database
        freecom_acquiring_dataService.save(freecom_acquiring_data);

        int databaseSizeBeforeUpdate = freecom_acquiring_dataRepository.findAll().size();

        // Update the freecom_acquiring_data
        Freecom_acquiring_data updatedFreecom_acquiring_data = new Freecom_acquiring_data();
        updatedFreecom_acquiring_data.setId(freecom_acquiring_data.getId());
        updatedFreecom_acquiring_data.setCoprosocconyugaie(UPDATED_COPROSOCCONYUGAIE);

        restFreecom_acquiring_dataMockMvc.perform(put("/api/freecom-acquiring-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_acquiring_data)))
                .andExpect(status().isOk());

        // Validate the Freecom_acquiring_data in the database
        List<Freecom_acquiring_data> freecom_acquiring_data = freecom_acquiring_dataRepository.findAll();
        assertThat(freecom_acquiring_data).hasSize(databaseSizeBeforeUpdate);
        Freecom_acquiring_data testFreecom_acquiring_data = freecom_acquiring_data.get(freecom_acquiring_data.size() - 1);
        assertThat(testFreecom_acquiring_data.getCoprosocconyugaie()).isEqualTo(UPDATED_COPROSOCCONYUGAIE);
    }

    @Test
    @Transactional
    public void deleteFreecom_acquiring_data() throws Exception {
        // Initialize the database
        freecom_acquiring_dataService.save(freecom_acquiring_data);

        int databaseSizeBeforeDelete = freecom_acquiring_dataRepository.findAll().size();

        // Get the freecom_acquiring_data
        restFreecom_acquiring_dataMockMvc.perform(delete("/api/freecom-acquiring-data/{id}", freecom_acquiring_data.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_acquiring_data> freecom_acquiring_data = freecom_acquiring_dataRepository.findAll();
        assertThat(freecom_acquiring_data).hasSize(databaseSizeBeforeDelete - 1);
    }
}
