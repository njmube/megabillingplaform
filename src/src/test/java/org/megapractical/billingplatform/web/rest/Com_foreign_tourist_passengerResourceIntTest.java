package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_foreign_tourist_passenger;
import org.megapractical.billingplatform.repository.Com_foreign_tourist_passengerRepository;
import org.megapractical.billingplatform.service.Com_foreign_tourist_passengerService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Com_foreign_tourist_passengerResource REST controller.
 *
 * @see Com_foreign_tourist_passengerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_foreign_tourist_passengerResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATE_TRAFFIC = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_TRAFFIC = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_TRAFFIC_STR = dateTimeFormatter.format(DEFAULT_DATE_TRAFFIC);
    private static final String DEFAULT_TYPEID = "AAAAA";
    private static final String UPDATED_TYPEID = "BBBBB";
    private static final String DEFAULT_NUMERID = "AAAAA";
    private static final String UPDATED_NUMERID = "BBBBB";
    private static final String DEFAULT_NATIONALITY = "AAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBB";
    private static final String DEFAULT_TRANSPORTCOMPANY = "AAAAA";
    private static final String UPDATED_TRANSPORTCOMPANY = "BBBBB";
    private static final String DEFAULT_IDTRANSPORT = "AAAAA";
    private static final String UPDATED_IDTRANSPORT = "BBBBB";

    @Inject
    private Com_foreign_tourist_passengerRepository com_foreign_tourist_passengerRepository;

    @Inject
    private Com_foreign_tourist_passengerService com_foreign_tourist_passengerService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_foreign_tourist_passengerMockMvc;

    private Com_foreign_tourist_passenger com_foreign_tourist_passenger;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_foreign_tourist_passengerResource com_foreign_tourist_passengerResource = new Com_foreign_tourist_passengerResource();
        ReflectionTestUtils.setField(com_foreign_tourist_passengerResource, "com_foreign_tourist_passengerService", com_foreign_tourist_passengerService);
        this.restCom_foreign_tourist_passengerMockMvc = MockMvcBuilders.standaloneSetup(com_foreign_tourist_passengerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_foreign_tourist_passenger = new Com_foreign_tourist_passenger();
        com_foreign_tourist_passenger.setVersion(DEFAULT_VERSION);
        com_foreign_tourist_passenger.setDate_traffic(DEFAULT_DATE_TRAFFIC);
        com_foreign_tourist_passenger.setTypeid(DEFAULT_TYPEID);
        com_foreign_tourist_passenger.setNumerid(DEFAULT_NUMERID);
        com_foreign_tourist_passenger.setNationality(DEFAULT_NATIONALITY);
        com_foreign_tourist_passenger.setTransportcompany(DEFAULT_TRANSPORTCOMPANY);
        com_foreign_tourist_passenger.setIdtransport(DEFAULT_IDTRANSPORT);
    }

    @Test
    @Transactional
    public void createCom_foreign_tourist_passenger() throws Exception {
        int databaseSizeBeforeCreate = com_foreign_tourist_passengerRepository.findAll().size();

        // Create the Com_foreign_tourist_passenger

        restCom_foreign_tourist_passengerMockMvc.perform(post("/api/com-foreign-tourist-passengers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_tourist_passenger)))
                .andExpect(status().isCreated());

        // Validate the Com_foreign_tourist_passenger in the database
        List<Com_foreign_tourist_passenger> com_foreign_tourist_passengers = com_foreign_tourist_passengerRepository.findAll();
        assertThat(com_foreign_tourist_passengers).hasSize(databaseSizeBeforeCreate + 1);
        Com_foreign_tourist_passenger testCom_foreign_tourist_passenger = com_foreign_tourist_passengers.get(com_foreign_tourist_passengers.size() - 1);
        assertThat(testCom_foreign_tourist_passenger.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_foreign_tourist_passenger.getDate_traffic()).isEqualTo(DEFAULT_DATE_TRAFFIC);
        assertThat(testCom_foreign_tourist_passenger.getTypeid()).isEqualTo(DEFAULT_TYPEID);
        assertThat(testCom_foreign_tourist_passenger.getNumerid()).isEqualTo(DEFAULT_NUMERID);
        assertThat(testCom_foreign_tourist_passenger.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testCom_foreign_tourist_passenger.getTransportcompany()).isEqualTo(DEFAULT_TRANSPORTCOMPANY);
        assertThat(testCom_foreign_tourist_passenger.getIdtransport()).isEqualTo(DEFAULT_IDTRANSPORT);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_foreign_tourist_passengerRepository.findAll().size();
        // set the field null
        com_foreign_tourist_passenger.setVersion(null);

        // Create the Com_foreign_tourist_passenger, which fails.

        restCom_foreign_tourist_passengerMockMvc.perform(post("/api/com-foreign-tourist-passengers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_tourist_passenger)))
                .andExpect(status().isBadRequest());

        List<Com_foreign_tourist_passenger> com_foreign_tourist_passengers = com_foreign_tourist_passengerRepository.findAll();
        assertThat(com_foreign_tourist_passengers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_trafficIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_foreign_tourist_passengerRepository.findAll().size();
        // set the field null
        com_foreign_tourist_passenger.setDate_traffic(null);

        // Create the Com_foreign_tourist_passenger, which fails.

        restCom_foreign_tourist_passengerMockMvc.perform(post("/api/com-foreign-tourist-passengers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_tourist_passenger)))
                .andExpect(status().isBadRequest());

        List<Com_foreign_tourist_passenger> com_foreign_tourist_passengers = com_foreign_tourist_passengerRepository.findAll();
        assertThat(com_foreign_tourist_passengers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeidIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_foreign_tourist_passengerRepository.findAll().size();
        // set the field null
        com_foreign_tourist_passenger.setTypeid(null);

        // Create the Com_foreign_tourist_passenger, which fails.

        restCom_foreign_tourist_passengerMockMvc.perform(post("/api/com-foreign-tourist-passengers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_tourist_passenger)))
                .andExpect(status().isBadRequest());

        List<Com_foreign_tourist_passenger> com_foreign_tourist_passengers = com_foreign_tourist_passengerRepository.findAll();
        assertThat(com_foreign_tourist_passengers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeridIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_foreign_tourist_passengerRepository.findAll().size();
        // set the field null
        com_foreign_tourist_passenger.setNumerid(null);

        // Create the Com_foreign_tourist_passenger, which fails.

        restCom_foreign_tourist_passengerMockMvc.perform(post("/api/com-foreign-tourist-passengers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_tourist_passenger)))
                .andExpect(status().isBadRequest());

        List<Com_foreign_tourist_passenger> com_foreign_tourist_passengers = com_foreign_tourist_passengerRepository.findAll();
        assertThat(com_foreign_tourist_passengers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationalityIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_foreign_tourist_passengerRepository.findAll().size();
        // set the field null
        com_foreign_tourist_passenger.setNationality(null);

        // Create the Com_foreign_tourist_passenger, which fails.

        restCom_foreign_tourist_passengerMockMvc.perform(post("/api/com-foreign-tourist-passengers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_tourist_passenger)))
                .andExpect(status().isBadRequest());

        List<Com_foreign_tourist_passenger> com_foreign_tourist_passengers = com_foreign_tourist_passengerRepository.findAll();
        assertThat(com_foreign_tourist_passengers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransportcompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_foreign_tourist_passengerRepository.findAll().size();
        // set the field null
        com_foreign_tourist_passenger.setTransportcompany(null);

        // Create the Com_foreign_tourist_passenger, which fails.

        restCom_foreign_tourist_passengerMockMvc.perform(post("/api/com-foreign-tourist-passengers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_foreign_tourist_passenger)))
                .andExpect(status().isBadRequest());

        List<Com_foreign_tourist_passenger> com_foreign_tourist_passengers = com_foreign_tourist_passengerRepository.findAll();
        assertThat(com_foreign_tourist_passengers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_foreign_tourist_passengers() throws Exception {
        // Initialize the database
        com_foreign_tourist_passengerRepository.saveAndFlush(com_foreign_tourist_passenger);

        // Get all the com_foreign_tourist_passengers
        restCom_foreign_tourist_passengerMockMvc.perform(get("/api/com-foreign-tourist-passengers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_foreign_tourist_passenger.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].date_traffic").value(hasItem(DEFAULT_DATE_TRAFFIC_STR)))
                .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID.toString())))
                .andExpect(jsonPath("$.[*].numerid").value(hasItem(DEFAULT_NUMERID.toString())))
                .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())))
                .andExpect(jsonPath("$.[*].transportcompany").value(hasItem(DEFAULT_TRANSPORTCOMPANY.toString())))
                .andExpect(jsonPath("$.[*].idtransport").value(hasItem(DEFAULT_IDTRANSPORT.toString())));
    }

    @Test
    @Transactional
    public void getCom_foreign_tourist_passenger() throws Exception {
        // Initialize the database
        com_foreign_tourist_passengerRepository.saveAndFlush(com_foreign_tourist_passenger);

        // Get the com_foreign_tourist_passenger
        restCom_foreign_tourist_passengerMockMvc.perform(get("/api/com-foreign-tourist-passengers/{id}", com_foreign_tourist_passenger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_foreign_tourist_passenger.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.date_traffic").value(DEFAULT_DATE_TRAFFIC_STR))
            .andExpect(jsonPath("$.typeid").value(DEFAULT_TYPEID.toString()))
            .andExpect(jsonPath("$.numerid").value(DEFAULT_NUMERID.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()))
            .andExpect(jsonPath("$.transportcompany").value(DEFAULT_TRANSPORTCOMPANY.toString()))
            .andExpect(jsonPath("$.idtransport").value(DEFAULT_IDTRANSPORT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_foreign_tourist_passenger() throws Exception {
        // Get the com_foreign_tourist_passenger
        restCom_foreign_tourist_passengerMockMvc.perform(get("/api/com-foreign-tourist-passengers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_foreign_tourist_passenger() throws Exception {
        // Initialize the database
        com_foreign_tourist_passengerService.save(com_foreign_tourist_passenger);

        int databaseSizeBeforeUpdate = com_foreign_tourist_passengerRepository.findAll().size();

        // Update the com_foreign_tourist_passenger
        Com_foreign_tourist_passenger updatedCom_foreign_tourist_passenger = new Com_foreign_tourist_passenger();
        updatedCom_foreign_tourist_passenger.setId(com_foreign_tourist_passenger.getId());
        updatedCom_foreign_tourist_passenger.setVersion(UPDATED_VERSION);
        updatedCom_foreign_tourist_passenger.setDate_traffic(UPDATED_DATE_TRAFFIC);
        updatedCom_foreign_tourist_passenger.setTypeid(UPDATED_TYPEID);
        updatedCom_foreign_tourist_passenger.setNumerid(UPDATED_NUMERID);
        updatedCom_foreign_tourist_passenger.setNationality(UPDATED_NATIONALITY);
        updatedCom_foreign_tourist_passenger.setTransportcompany(UPDATED_TRANSPORTCOMPANY);
        updatedCom_foreign_tourist_passenger.setIdtransport(UPDATED_IDTRANSPORT);

        restCom_foreign_tourist_passengerMockMvc.perform(put("/api/com-foreign-tourist-passengers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_foreign_tourist_passenger)))
                .andExpect(status().isOk());

        // Validate the Com_foreign_tourist_passenger in the database
        List<Com_foreign_tourist_passenger> com_foreign_tourist_passengers = com_foreign_tourist_passengerRepository.findAll();
        assertThat(com_foreign_tourist_passengers).hasSize(databaseSizeBeforeUpdate);
        Com_foreign_tourist_passenger testCom_foreign_tourist_passenger = com_foreign_tourist_passengers.get(com_foreign_tourist_passengers.size() - 1);
        assertThat(testCom_foreign_tourist_passenger.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_foreign_tourist_passenger.getDate_traffic()).isEqualTo(UPDATED_DATE_TRAFFIC);
        assertThat(testCom_foreign_tourist_passenger.getTypeid()).isEqualTo(UPDATED_TYPEID);
        assertThat(testCom_foreign_tourist_passenger.getNumerid()).isEqualTo(UPDATED_NUMERID);
        assertThat(testCom_foreign_tourist_passenger.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testCom_foreign_tourist_passenger.getTransportcompany()).isEqualTo(UPDATED_TRANSPORTCOMPANY);
        assertThat(testCom_foreign_tourist_passenger.getIdtransport()).isEqualTo(UPDATED_IDTRANSPORT);
    }

    @Test
    @Transactional
    public void deleteCom_foreign_tourist_passenger() throws Exception {
        // Initialize the database
        com_foreign_tourist_passengerService.save(com_foreign_tourist_passenger);

        int databaseSizeBeforeDelete = com_foreign_tourist_passengerRepository.findAll().size();

        // Get the com_foreign_tourist_passenger
        restCom_foreign_tourist_passengerMockMvc.perform(delete("/api/com-foreign-tourist-passengers/{id}", com_foreign_tourist_passenger.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_foreign_tourist_passenger> com_foreign_tourist_passengers = com_foreign_tourist_passengerRepository.findAll();
        assertThat(com_foreign_tourist_passengers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
