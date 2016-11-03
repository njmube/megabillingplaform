package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_airline;
import org.megapractical.billingplatform.repository.Com_airlineRepository;
import org.megapractical.billingplatform.service.Com_airlineService;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Com_airlineResource REST controller.
 *
 * @see Com_airlineResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_airlineResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    private static final BigDecimal DEFAULT_TUA = new BigDecimal(1);
    private static final BigDecimal UPDATED_TUA = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_CHARGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_CHARGE = new BigDecimal(2);

    @Inject
    private Com_airlineRepository com_airlineRepository;

    @Inject
    private Com_airlineService com_airlineService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_airlineMockMvc;

    private Com_airline com_airline;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_airlineResource com_airlineResource = new Com_airlineResource();
        ReflectionTestUtils.setField(com_airlineResource, "com_airlineService", com_airlineService);
        this.restCom_airlineMockMvc = MockMvcBuilders.standaloneSetup(com_airlineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_airline = new Com_airline();
        com_airline.setVersion(DEFAULT_VERSION);
        com_airline.setTua(DEFAULT_TUA);
        com_airline.setTotal_charge(DEFAULT_TOTAL_CHARGE);
    }

    @Test
    @Transactional
    public void createCom_airline() throws Exception {
        int databaseSizeBeforeCreate = com_airlineRepository.findAll().size();

        // Create the Com_airline

        restCom_airlineMockMvc.perform(post("/api/com-airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_airline)))
                .andExpect(status().isCreated());

        // Validate the Com_airline in the database
        List<Com_airline> com_airlines = com_airlineRepository.findAll();
        assertThat(com_airlines).hasSize(databaseSizeBeforeCreate + 1);
        Com_airline testCom_airline = com_airlines.get(com_airlines.size() - 1);
        assertThat(testCom_airline.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_airline.getTua()).isEqualTo(DEFAULT_TUA);
        assertThat(testCom_airline.getTotal_charge()).isEqualTo(DEFAULT_TOTAL_CHARGE);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_airlineRepository.findAll().size();
        // set the field null
        com_airline.setVersion(null);

        // Create the Com_airline, which fails.

        restCom_airlineMockMvc.perform(post("/api/com-airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_airline)))
                .andExpect(status().isBadRequest());

        List<Com_airline> com_airlines = com_airlineRepository.findAll();
        assertThat(com_airlines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTuaIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_airlineRepository.findAll().size();
        // set the field null
        com_airline.setTua(null);

        // Create the Com_airline, which fails.

        restCom_airlineMockMvc.perform(post("/api/com-airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_airline)))
                .andExpect(status().isBadRequest());

        List<Com_airline> com_airlines = com_airlineRepository.findAll();
        assertThat(com_airlines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotal_chargeIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_airlineRepository.findAll().size();
        // set the field null
        com_airline.setTotal_charge(null);

        // Create the Com_airline, which fails.

        restCom_airlineMockMvc.perform(post("/api/com-airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_airline)))
                .andExpect(status().isBadRequest());

        List<Com_airline> com_airlines = com_airlineRepository.findAll();
        assertThat(com_airlines).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_airlines() throws Exception {
        // Initialize the database
        com_airlineRepository.saveAndFlush(com_airline);

        // Get all the com_airlines
        restCom_airlineMockMvc.perform(get("/api/com-airlines?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_airline.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].tua").value(hasItem(DEFAULT_TUA.intValue())))
                .andExpect(jsonPath("$.[*].total_charge").value(hasItem(DEFAULT_TOTAL_CHARGE.intValue())));
    }

    @Test
    @Transactional
    public void getCom_airline() throws Exception {
        // Initialize the database
        com_airlineRepository.saveAndFlush(com_airline);

        // Get the com_airline
        restCom_airlineMockMvc.perform(get("/api/com-airlines/{id}", com_airline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_airline.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.tua").value(DEFAULT_TUA.intValue()))
            .andExpect(jsonPath("$.total_charge").value(DEFAULT_TOTAL_CHARGE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_airline() throws Exception {
        // Get the com_airline
        restCom_airlineMockMvc.perform(get("/api/com-airlines/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_airline() throws Exception {
        // Initialize the database
        com_airlineService.save(com_airline);

        int databaseSizeBeforeUpdate = com_airlineRepository.findAll().size();

        // Update the com_airline
        Com_airline updatedCom_airline = new Com_airline();
        updatedCom_airline.setId(com_airline.getId());
        updatedCom_airline.setVersion(UPDATED_VERSION);
        updatedCom_airline.setTua(UPDATED_TUA);
        updatedCom_airline.setTotal_charge(UPDATED_TOTAL_CHARGE);

        restCom_airlineMockMvc.perform(put("/api/com-airlines")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_airline)))
                .andExpect(status().isOk());

        // Validate the Com_airline in the database
        List<Com_airline> com_airlines = com_airlineRepository.findAll();
        assertThat(com_airlines).hasSize(databaseSizeBeforeUpdate);
        Com_airline testCom_airline = com_airlines.get(com_airlines.size() - 1);
        assertThat(testCom_airline.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_airline.getTua()).isEqualTo(UPDATED_TUA);
        assertThat(testCom_airline.getTotal_charge()).isEqualTo(UPDATED_TOTAL_CHARGE);
    }

    @Test
    @Transactional
    public void deleteCom_airline() throws Exception {
        // Initialize the database
        com_airlineService.save(com_airline);

        int databaseSizeBeforeDelete = com_airlineRepository.findAll().size();

        // Get the com_airline
        restCom_airlineMockMvc.perform(delete("/api/com-airlines/{id}", com_airline.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_airline> com_airlines = com_airlineRepository.findAll();
        assertThat(com_airlines).hasSize(databaseSizeBeforeDelete - 1);
    }
}
