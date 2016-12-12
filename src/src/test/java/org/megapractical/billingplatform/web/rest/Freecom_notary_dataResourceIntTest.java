package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_notary_data;
import org.megapractical.billingplatform.repository.Freecom_notary_dataRepository;
import org.megapractical.billingplatform.service.Freecom_notary_dataService;

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
 * Test class for the Freecom_notary_dataResource REST controller.
 *
 * @see Freecom_notary_dataResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_notary_dataResourceIntTest {

    private static final String DEFAULT_CURP = "AAAAA";
    private static final String UPDATED_CURP = "BBBBB";

    private static final Integer DEFAULT_NOTARYNUMBER = 1;
    private static final Integer UPDATED_NOTARYNUMBER = 2;
    private static final String DEFAULT_ASCRIPTION = "AAAAA";
    private static final String UPDATED_ASCRIPTION = "BBBBB";

    @Inject
    private Freecom_notary_dataRepository freecom_notary_dataRepository;

    @Inject
    private Freecom_notary_dataService freecom_notary_dataService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_notary_dataMockMvc;

    private Freecom_notary_data freecom_notary_data;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_notary_dataResource freecom_notary_dataResource = new Freecom_notary_dataResource();
        ReflectionTestUtils.setField(freecom_notary_dataResource, "freecom_notary_dataService", freecom_notary_dataService);
        this.restFreecom_notary_dataMockMvc = MockMvcBuilders.standaloneSetup(freecom_notary_dataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_notary_data = new Freecom_notary_data();
        freecom_notary_data.setCurp(DEFAULT_CURP);
        freecom_notary_data.setNotarynumber(DEFAULT_NOTARYNUMBER);
        freecom_notary_data.setAscription(DEFAULT_ASCRIPTION);
    }

    @Test
    @Transactional
    public void createFreecom_notary_data() throws Exception {
        int databaseSizeBeforeCreate = freecom_notary_dataRepository.findAll().size();

        // Create the Freecom_notary_data

        restFreecom_notary_dataMockMvc.perform(post("/api/freecom-notary-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_notary_data)))
                .andExpect(status().isCreated());

        // Validate the Freecom_notary_data in the database
        List<Freecom_notary_data> freecom_notary_data = freecom_notary_dataRepository.findAll();
        assertThat(freecom_notary_data).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_notary_data testFreecom_notary_data = freecom_notary_data.get(freecom_notary_data.size() - 1);
        assertThat(testFreecom_notary_data.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testFreecom_notary_data.getNotarynumber()).isEqualTo(DEFAULT_NOTARYNUMBER);
        assertThat(testFreecom_notary_data.getAscription()).isEqualTo(DEFAULT_ASCRIPTION);
    }

    @Test
    @Transactional
    public void checkCurpIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_notary_dataRepository.findAll().size();
        // set the field null
        freecom_notary_data.setCurp(null);

        // Create the Freecom_notary_data, which fails.

        restFreecom_notary_dataMockMvc.perform(post("/api/freecom-notary-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_notary_data)))
                .andExpect(status().isBadRequest());

        List<Freecom_notary_data> freecom_notary_data = freecom_notary_dataRepository.findAll();
        assertThat(freecom_notary_data).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotarynumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_notary_dataRepository.findAll().size();
        // set the field null
        freecom_notary_data.setNotarynumber(null);

        // Create the Freecom_notary_data, which fails.

        restFreecom_notary_dataMockMvc.perform(post("/api/freecom-notary-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_notary_data)))
                .andExpect(status().isBadRequest());

        List<Freecom_notary_data> freecom_notary_data = freecom_notary_dataRepository.findAll();
        assertThat(freecom_notary_data).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_notary_data() throws Exception {
        // Initialize the database
        freecom_notary_dataRepository.saveAndFlush(freecom_notary_data);

        // Get all the freecom_notary_data
        restFreecom_notary_dataMockMvc.perform(get("/api/freecom-notary-data?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_notary_data.getId().intValue())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
                .andExpect(jsonPath("$.[*].notarynumber").value(hasItem(DEFAULT_NOTARYNUMBER)))
                .andExpect(jsonPath("$.[*].ascription").value(hasItem(DEFAULT_ASCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_notary_data() throws Exception {
        // Initialize the database
        freecom_notary_dataRepository.saveAndFlush(freecom_notary_data);

        // Get the freecom_notary_data
        restFreecom_notary_dataMockMvc.perform(get("/api/freecom-notary-data/{id}", freecom_notary_data.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_notary_data.getId().intValue()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.notarynumber").value(DEFAULT_NOTARYNUMBER))
            .andExpect(jsonPath("$.ascription").value(DEFAULT_ASCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_notary_data() throws Exception {
        // Get the freecom_notary_data
        restFreecom_notary_dataMockMvc.perform(get("/api/freecom-notary-data/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_notary_data() throws Exception {
        // Initialize the database
        freecom_notary_dataService.save(freecom_notary_data);

        int databaseSizeBeforeUpdate = freecom_notary_dataRepository.findAll().size();

        // Update the freecom_notary_data
        Freecom_notary_data updatedFreecom_notary_data = new Freecom_notary_data();
        updatedFreecom_notary_data.setId(freecom_notary_data.getId());
        updatedFreecom_notary_data.setCurp(UPDATED_CURP);
        updatedFreecom_notary_data.setNotarynumber(UPDATED_NOTARYNUMBER);
        updatedFreecom_notary_data.setAscription(UPDATED_ASCRIPTION);

        restFreecom_notary_dataMockMvc.perform(put("/api/freecom-notary-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_notary_data)))
                .andExpect(status().isOk());

        // Validate the Freecom_notary_data in the database
        List<Freecom_notary_data> freecom_notary_data = freecom_notary_dataRepository.findAll();
        assertThat(freecom_notary_data).hasSize(databaseSizeBeforeUpdate);
        Freecom_notary_data testFreecom_notary_data = freecom_notary_data.get(freecom_notary_data.size() - 1);
        assertThat(testFreecom_notary_data.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testFreecom_notary_data.getNotarynumber()).isEqualTo(UPDATED_NOTARYNUMBER);
        assertThat(testFreecom_notary_data.getAscription()).isEqualTo(UPDATED_ASCRIPTION);
    }

    @Test
    @Transactional
    public void deleteFreecom_notary_data() throws Exception {
        // Initialize the database
        freecom_notary_dataService.save(freecom_notary_data);

        int databaseSizeBeforeDelete = freecom_notary_dataRepository.findAll().size();

        // Get the freecom_notary_data
        restFreecom_notary_dataMockMvc.perform(delete("/api/freecom-notary-data/{id}", freecom_notary_data.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_notary_data> freecom_notary_data = freecom_notary_dataRepository.findAll();
        assertThat(freecom_notary_data).hasSize(databaseSizeBeforeDelete - 1);
    }
}
