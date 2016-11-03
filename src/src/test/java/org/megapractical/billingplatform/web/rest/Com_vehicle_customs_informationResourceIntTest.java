package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_vehicle_customs_information;
import org.megapractical.billingplatform.repository.Com_vehicle_customs_informationRepository;
import org.megapractical.billingplatform.service.Com_vehicle_customs_informationService;

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
 * Test class for the Com_vehicle_customs_informationResource REST controller.
 *
 * @see Com_vehicle_customs_informationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_vehicle_customs_informationResourceIntTest {

    private static final String DEFAULT_NUMBER = "AAAAA";
    private static final String UPDATED_NUMBER = "BBBBB";

    private static final LocalDate DEFAULT_DATE_EXPEDITION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPEDITION = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CUSTOMS = "AAAAA";
    private static final String UPDATED_CUSTOMS = "BBBBB";

    @Inject
    private Com_vehicle_customs_informationRepository com_vehicle_customs_informationRepository;

    @Inject
    private Com_vehicle_customs_informationService com_vehicle_customs_informationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_vehicle_customs_informationMockMvc;

    private Com_vehicle_customs_information com_vehicle_customs_information;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_vehicle_customs_informationResource com_vehicle_customs_informationResource = new Com_vehicle_customs_informationResource();
        ReflectionTestUtils.setField(com_vehicle_customs_informationResource, "com_vehicle_customs_informationService", com_vehicle_customs_informationService);
        this.restCom_vehicle_customs_informationMockMvc = MockMvcBuilders.standaloneSetup(com_vehicle_customs_informationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_vehicle_customs_information = new Com_vehicle_customs_information();
        com_vehicle_customs_information.setNumber(DEFAULT_NUMBER);
        com_vehicle_customs_information.setDate_expedition(DEFAULT_DATE_EXPEDITION);
        com_vehicle_customs_information.setCustoms(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void createCom_vehicle_customs_information() throws Exception {
        int databaseSizeBeforeCreate = com_vehicle_customs_informationRepository.findAll().size();

        // Create the Com_vehicle_customs_information

        restCom_vehicle_customs_informationMockMvc.perform(post("/api/com-vehicle-customs-informations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_vehicle_customs_information)))
                .andExpect(status().isCreated());

        // Validate the Com_vehicle_customs_information in the database
        List<Com_vehicle_customs_information> com_vehicle_customs_informations = com_vehicle_customs_informationRepository.findAll();
        assertThat(com_vehicle_customs_informations).hasSize(databaseSizeBeforeCreate + 1);
        Com_vehicle_customs_information testCom_vehicle_customs_information = com_vehicle_customs_informations.get(com_vehicle_customs_informations.size() - 1);
        assertThat(testCom_vehicle_customs_information.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCom_vehicle_customs_information.getDate_expedition()).isEqualTo(DEFAULT_DATE_EXPEDITION);
        assertThat(testCom_vehicle_customs_information.getCustoms()).isEqualTo(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_vehicle_customs_informationRepository.findAll().size();
        // set the field null
        com_vehicle_customs_information.setNumber(null);

        // Create the Com_vehicle_customs_information, which fails.

        restCom_vehicle_customs_informationMockMvc.perform(post("/api/com-vehicle-customs-informations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_vehicle_customs_information)))
                .andExpect(status().isBadRequest());

        List<Com_vehicle_customs_information> com_vehicle_customs_informations = com_vehicle_customs_informationRepository.findAll();
        assertThat(com_vehicle_customs_informations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_expeditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_vehicle_customs_informationRepository.findAll().size();
        // set the field null
        com_vehicle_customs_information.setDate_expedition(null);

        // Create the Com_vehicle_customs_information, which fails.

        restCom_vehicle_customs_informationMockMvc.perform(post("/api/com-vehicle-customs-informations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_vehicle_customs_information)))
                .andExpect(status().isBadRequest());

        List<Com_vehicle_customs_information> com_vehicle_customs_informations = com_vehicle_customs_informationRepository.findAll();
        assertThat(com_vehicle_customs_informations).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_vehicle_customs_informations() throws Exception {
        // Initialize the database
        com_vehicle_customs_informationRepository.saveAndFlush(com_vehicle_customs_information);

        // Get all the com_vehicle_customs_informations
        restCom_vehicle_customs_informationMockMvc.perform(get("/api/com-vehicle-customs-informations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_vehicle_customs_information.getId().intValue())))
                .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].date_expedition").value(hasItem(DEFAULT_DATE_EXPEDITION.toString())))
                .andExpect(jsonPath("$.[*].customs").value(hasItem(DEFAULT_CUSTOMS.toString())));
    }

    @Test
    @Transactional
    public void getCom_vehicle_customs_information() throws Exception {
        // Initialize the database
        com_vehicle_customs_informationRepository.saveAndFlush(com_vehicle_customs_information);

        // Get the com_vehicle_customs_information
        restCom_vehicle_customs_informationMockMvc.perform(get("/api/com-vehicle-customs-informations/{id}", com_vehicle_customs_information.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_vehicle_customs_information.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.date_expedition").value(DEFAULT_DATE_EXPEDITION.toString()))
            .andExpect(jsonPath("$.customs").value(DEFAULT_CUSTOMS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_vehicle_customs_information() throws Exception {
        // Get the com_vehicle_customs_information
        restCom_vehicle_customs_informationMockMvc.perform(get("/api/com-vehicle-customs-informations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_vehicle_customs_information() throws Exception {
        // Initialize the database
        com_vehicle_customs_informationService.save(com_vehicle_customs_information);

        int databaseSizeBeforeUpdate = com_vehicle_customs_informationRepository.findAll().size();

        // Update the com_vehicle_customs_information
        Com_vehicle_customs_information updatedCom_vehicle_customs_information = new Com_vehicle_customs_information();
        updatedCom_vehicle_customs_information.setId(com_vehicle_customs_information.getId());
        updatedCom_vehicle_customs_information.setNumber(UPDATED_NUMBER);
        updatedCom_vehicle_customs_information.setDate_expedition(UPDATED_DATE_EXPEDITION);
        updatedCom_vehicle_customs_information.setCustoms(UPDATED_CUSTOMS);

        restCom_vehicle_customs_informationMockMvc.perform(put("/api/com-vehicle-customs-informations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_vehicle_customs_information)))
                .andExpect(status().isOk());

        // Validate the Com_vehicle_customs_information in the database
        List<Com_vehicle_customs_information> com_vehicle_customs_informations = com_vehicle_customs_informationRepository.findAll();
        assertThat(com_vehicle_customs_informations).hasSize(databaseSizeBeforeUpdate);
        Com_vehicle_customs_information testCom_vehicle_customs_information = com_vehicle_customs_informations.get(com_vehicle_customs_informations.size() - 1);
        assertThat(testCom_vehicle_customs_information.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCom_vehicle_customs_information.getDate_expedition()).isEqualTo(UPDATED_DATE_EXPEDITION);
        assertThat(testCom_vehicle_customs_information.getCustoms()).isEqualTo(UPDATED_CUSTOMS);
    }

    @Test
    @Transactional
    public void deleteCom_vehicle_customs_information() throws Exception {
        // Initialize the database
        com_vehicle_customs_informationService.save(com_vehicle_customs_information);

        int databaseSizeBeforeDelete = com_vehicle_customs_informationRepository.findAll().size();

        // Get the com_vehicle_customs_information
        restCom_vehicle_customs_informationMockMvc.perform(delete("/api/com-vehicle-customs-informations/{id}", com_vehicle_customs_information.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_vehicle_customs_information> com_vehicle_customs_informations = com_vehicle_customs_informationRepository.findAll();
        assertThat(com_vehicle_customs_informations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
