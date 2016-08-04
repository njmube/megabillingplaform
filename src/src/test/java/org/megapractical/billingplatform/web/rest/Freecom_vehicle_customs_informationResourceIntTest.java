package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_vehicle_customs_information;
import org.megapractical.billingplatform.repository.Freecom_vehicle_customs_informationRepository;
import org.megapractical.billingplatform.service.Freecom_vehicle_customs_informationService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_vehicle_customs_informationResource REST controller.
 *
 * @see Freecom_vehicle_customs_informationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_vehicle_customs_informationResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAA";
    private static final String UPDATED_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_DATE_EXPEDITION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPEDITION = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CUSTOMS = "AAAAA";
    private static final String UPDATED_CUSTOMS = "BBBBB";

    @Inject
    private Freecom_vehicle_customs_informationRepository freecom_vehicle_customs_informationRepository;

    @Inject
    private Freecom_vehicle_customs_informationService freecom_vehicle_customs_informationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_vehicle_customs_informationMockMvc;

    private Freecom_vehicle_customs_information freecom_vehicle_customs_information;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_vehicle_customs_informationResource freecom_vehicle_customs_informationResource = new Freecom_vehicle_customs_informationResource();
        ReflectionTestUtils.setField(freecom_vehicle_customs_informationResource, "freecom_vehicle_customs_informationService", freecom_vehicle_customs_informationService);
        this.restFreecom_vehicle_customs_informationMockMvc = MockMvcBuilders.standaloneSetup(freecom_vehicle_customs_informationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_vehicle_customs_information = new Freecom_vehicle_customs_information();
        freecom_vehicle_customs_information.setNumber(DEFAULT_NUMBER);
        freecom_vehicle_customs_information.setDate_expedition(DEFAULT_DATE_EXPEDITION);
        freecom_vehicle_customs_information.setCustoms(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void createFreecom_vehicle_customs_information() throws Exception {
        int databaseSizeBeforeCreate = freecom_vehicle_customs_informationRepository.findAll().size();

        // Create the Freecom_vehicle_customs_information

        restFreecom_vehicle_customs_informationMockMvc.perform(post("/api/freecom-vehicle-customs-informations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_vehicle_customs_information)))
                .andExpect(status().isCreated());

        // Validate the Freecom_vehicle_customs_information in the database
        List<Freecom_vehicle_customs_information> freecom_vehicle_customs_informations = freecom_vehicle_customs_informationRepository.findAll();
        assertThat(freecom_vehicle_customs_informations).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_vehicle_customs_information testFreecom_vehicle_customs_information = freecom_vehicle_customs_informations.get(freecom_vehicle_customs_informations.size() - 1);
        assertThat(testFreecom_vehicle_customs_information.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testFreecom_vehicle_customs_information.getDate_expedition()).isEqualTo(DEFAULT_DATE_EXPEDITION);
        assertThat(testFreecom_vehicle_customs_information.getCustoms()).isEqualTo(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_vehicle_customs_informationRepository.findAll().size();
        // set the field null
        freecom_vehicle_customs_information.setNumber(null);

        // Create the Freecom_vehicle_customs_information, which fails.

        restFreecom_vehicle_customs_informationMockMvc.perform(post("/api/freecom-vehicle-customs-informations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_vehicle_customs_information)))
                .andExpect(status().isBadRequest());

        List<Freecom_vehicle_customs_information> freecom_vehicle_customs_informations = freecom_vehicle_customs_informationRepository.findAll();
        assertThat(freecom_vehicle_customs_informations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_expeditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_vehicle_customs_informationRepository.findAll().size();
        // set the field null
        freecom_vehicle_customs_information.setDate_expedition(null);

        // Create the Freecom_vehicle_customs_information, which fails.

        restFreecom_vehicle_customs_informationMockMvc.perform(post("/api/freecom-vehicle-customs-informations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_vehicle_customs_information)))
                .andExpect(status().isBadRequest());

        List<Freecom_vehicle_customs_information> freecom_vehicle_customs_informations = freecom_vehicle_customs_informationRepository.findAll();
        assertThat(freecom_vehicle_customs_informations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_vehicle_customs_informations() throws Exception {
        // Initialize the database
        freecom_vehicle_customs_informationRepository.saveAndFlush(freecom_vehicle_customs_information);

        // Get all the freecom_vehicle_customs_informations
        restFreecom_vehicle_customs_informationMockMvc.perform(get("/api/freecom-vehicle-customs-informations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_vehicle_customs_information.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].date_expedition").value(hasItem(DEFAULT_DATE_EXPEDITION.toString())))
                .andExpect(jsonPath("$.[*].customs").value(hasItem(DEFAULT_CUSTOMS.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_vehicle_customs_information() throws Exception {
        // Initialize the database
        freecom_vehicle_customs_informationRepository.saveAndFlush(freecom_vehicle_customs_information);

        // Get the freecom_vehicle_customs_information
        restFreecom_vehicle_customs_informationMockMvc.perform(get("/api/freecom-vehicle-customs-informations/{id}", freecom_vehicle_customs_information.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_vehicle_customs_information.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.date_expedition").value(DEFAULT_DATE_EXPEDITION.toString()))
            .andExpect(jsonPath("$.customs").value(DEFAULT_CUSTOMS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_vehicle_customs_information() throws Exception {
        // Get the freecom_vehicle_customs_information
        restFreecom_vehicle_customs_informationMockMvc.perform(get("/api/freecom-vehicle-customs-informations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_vehicle_customs_information() throws Exception {
        // Initialize the database
        freecom_vehicle_customs_informationService.save(freecom_vehicle_customs_information);

        int databaseSizeBeforeUpdate = freecom_vehicle_customs_informationRepository.findAll().size();

        // Update the freecom_vehicle_customs_information
        Freecom_vehicle_customs_information updatedFreecom_vehicle_customs_information = new Freecom_vehicle_customs_information();
        updatedFreecom_vehicle_customs_information.setId(freecom_vehicle_customs_information.getId());
        updatedFreecom_vehicle_customs_information.setNumber(UPDATED_NUMBER);
        updatedFreecom_vehicle_customs_information.setDate_expedition(UPDATED_DATE_EXPEDITION);
        updatedFreecom_vehicle_customs_information.setCustoms(UPDATED_CUSTOMS);

        restFreecom_vehicle_customs_informationMockMvc.perform(put("/api/freecom-vehicle-customs-informations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_vehicle_customs_information)))
                .andExpect(status().isOk());

        // Validate the Freecom_vehicle_customs_information in the database
        List<Freecom_vehicle_customs_information> freecom_vehicle_customs_informations = freecom_vehicle_customs_informationRepository.findAll();
        assertThat(freecom_vehicle_customs_informations).hasSize(databaseSizeBeforeUpdate);
        Freecom_vehicle_customs_information testFreecom_vehicle_customs_information = freecom_vehicle_customs_informations.get(freecom_vehicle_customs_informations.size() - 1);
        assertThat(testFreecom_vehicle_customs_information.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testFreecom_vehicle_customs_information.getDate_expedition()).isEqualTo(UPDATED_DATE_EXPEDITION);
        assertThat(testFreecom_vehicle_customs_information.getCustoms()).isEqualTo(UPDATED_CUSTOMS);
    }

    @Test
    @Transactional
    public void deleteFreecom_vehicle_customs_information() throws Exception {
        // Initialize the database
        freecom_vehicle_customs_informationService.save(freecom_vehicle_customs_information);

        int databaseSizeBeforeDelete = freecom_vehicle_customs_informationRepository.findAll().size();

        // Get the freecom_vehicle_customs_information
        restFreecom_vehicle_customs_informationMockMvc.perform(delete("/api/freecom-vehicle-customs-informations/{id}", freecom_vehicle_customs_information.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_vehicle_customs_information> freecom_vehicle_customs_informations = freecom_vehicle_customs_informationRepository.findAll();
        assertThat(freecom_vehicle_customs_informations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
