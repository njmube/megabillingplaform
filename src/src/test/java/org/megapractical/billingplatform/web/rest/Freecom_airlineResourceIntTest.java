package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_airline;
import org.megapractical.billingplatform.repository.Freecom_airlineRepository;
import org.megapractical.billingplatform.service.Freecom_airlineService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_airlineResource REST controller.
 *
 * @see Freecom_airlineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_airlineResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    private static final BigDecimal DEFAULT_TUA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TUA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_CHARGE = new BigDecimal(2);

    @Inject
    private Freecom_airlineRepository freecom_airlineRepository;

    @Inject
    private Freecom_airlineService freecom_airlineService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_airlineMockMvc;

    private Freecom_airline freecom_airline;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_airlineResource freecom_airlineResource = new Freecom_airlineResource();
        ReflectionTestUtils.setField(freecom_airlineResource, "freecom_airlineService", freecom_airlineService);
        this.restFreecom_airlineMockMvc = MockMvcBuilders.standaloneSetup(freecom_airlineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_airline = new Freecom_airline();
        freecom_airline.setVersion(DEFAULT_VERSION);
        freecom_airline.setTua(DEFAULT_TUA);
        freecom_airline.setTotal_charge(DEFAULT_TOTAL_CHARGE);
    }

    @Test
    @Transactional
    public void createFreecom_airline() throws Exception {
        int databaseSizeBeforeCreate = freecom_airlineRepository.findAll().size();

        // Create the Freecom_airline

        restFreecom_airlineMockMvc.perform(post("/api/freecom-airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_airline)))
                .andExpect(status().isCreated());

        // Validate the Freecom_airline in the database
        List<Freecom_airline> freecom_airlines = freecom_airlineRepository.findAll();
        assertThat(freecom_airlines).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_airline testFreecom_airline = freecom_airlines.get(freecom_airlines.size() - 1);
        assertThat(testFreecom_airline.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_airline.getTua()).isEqualTo(DEFAULT_TUA);
        assertThat(testFreecom_airline.getTotal_charge()).isEqualTo(DEFAULT_TOTAL_CHARGE);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_airlineRepository.findAll().size();
        // set the field null
        freecom_airline.setVersion(null);

        // Create the Freecom_airline, which fails.

        restFreecom_airlineMockMvc.perform(post("/api/freecom-airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_airline)))
                .andExpect(status().isBadRequest());

        List<Freecom_airline> freecom_airlines = freecom_airlineRepository.findAll();
        assertThat(freecom_airlines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTuaIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_airlineRepository.findAll().size();
        // set the field null
        freecom_airline.setTua(null);

        // Create the Freecom_airline, which fails.

        restFreecom_airlineMockMvc.perform(post("/api/freecom-airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_airline)))
                .andExpect(status().isBadRequest());

        List<Freecom_airline> freecom_airlines = freecom_airlineRepository.findAll();
        assertThat(freecom_airlines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotal_chargeIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_airlineRepository.findAll().size();
        // set the field null
        freecom_airline.setTotal_charge(null);

        // Create the Freecom_airline, which fails.

        restFreecom_airlineMockMvc.perform(post("/api/freecom-airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_airline)))
                .andExpect(status().isBadRequest());

        List<Freecom_airline> freecom_airlines = freecom_airlineRepository.findAll();
        assertThat(freecom_airlines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_airlines() throws Exception {
        // Initialize the database
        freecom_airlineRepository.saveAndFlush(freecom_airline);

        // Get all the freecom_airlines
        restFreecom_airlineMockMvc.perform(get("/api/freecom-airlines?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_airline.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].tua").value(hasItem(DEFAULT_TUA.intValue())))
                .andExpect(jsonPath("$.[*].total_charge").value(hasItem(DEFAULT_TOTAL_CHARGE.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_airline() throws Exception {
        // Initialize the database
        freecom_airlineRepository.saveAndFlush(freecom_airline);

        // Get the freecom_airline
        restFreecom_airlineMockMvc.perform(get("/api/freecom-airlines/{id}", freecom_airline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_airline.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.tua").value(DEFAULT_TUA.intValue()))
            .andExpect(jsonPath("$.total_charge").value(DEFAULT_TOTAL_CHARGE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_airline() throws Exception {
        // Get the freecom_airline
        restFreecom_airlineMockMvc.perform(get("/api/freecom-airlines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_airline() throws Exception {
        // Initialize the database
        freecom_airlineService.save(freecom_airline);

        int databaseSizeBeforeUpdate = freecom_airlineRepository.findAll().size();

        // Update the freecom_airline
        Freecom_airline updatedFreecom_airline = new Freecom_airline();
        updatedFreecom_airline.setId(freecom_airline.getId());
        updatedFreecom_airline.setVersion(UPDATED_VERSION);
        updatedFreecom_airline.setTua(UPDATED_TUA);
        updatedFreecom_airline.setTotal_charge(UPDATED_TOTAL_CHARGE);

        restFreecom_airlineMockMvc.perform(put("/api/freecom-airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_airline)))
                .andExpect(status().isOk());

        // Validate the Freecom_airline in the database
        List<Freecom_airline> freecom_airlines = freecom_airlineRepository.findAll();
        assertThat(freecom_airlines).hasSize(databaseSizeBeforeUpdate);
        Freecom_airline testFreecom_airline = freecom_airlines.get(freecom_airlines.size() - 1);
        assertThat(testFreecom_airline.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_airline.getTua()).isEqualTo(UPDATED_TUA);
        assertThat(testFreecom_airline.getTotal_charge()).isEqualTo(UPDATED_TOTAL_CHARGE);
    }

    @Test
    @Transactional
    public void deleteFreecom_airline() throws Exception {
        // Initialize the database
        freecom_airlineService.save(freecom_airline);

        int databaseSizeBeforeDelete = freecom_airlineRepository.findAll().size();

        // Get the freecom_airline
        restFreecom_airlineMockMvc.perform(delete("/api/freecom-airlines/{id}", freecom_airline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_airline> freecom_airlines = freecom_airlineRepository.findAll();
        assertThat(freecom_airlines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
