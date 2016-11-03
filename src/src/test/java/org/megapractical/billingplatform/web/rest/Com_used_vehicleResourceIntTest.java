package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_used_vehicle;
import org.megapractical.billingplatform.repository.Com_used_vehicleRepository;
import org.megapractical.billingplatform.service.Com_used_vehicleService;

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
 * Test class for the Com_used_vehicleResource REST controller.
 *
 * @see Com_used_vehicleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_used_vehicleResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    private static final BigDecimal DEFAULT_ACQUISITION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACQUISITION_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_MONTO_ENAJENACION = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTO_ENAJENACION = new BigDecimal(2);
    private static final String DEFAULT_KEY_VEHICLE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_KEY_VEHICLE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_BRAND = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_BRAND = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_TYPE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_MODEL = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NUMBER_ENGINE = "AAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMBER_ENGINE = "BBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NO_SERIE = "AAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NO_SERIE = "BBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_NIV = "AAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NIV = "BBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALUE = new BigDecimal(2);

    @Inject
    private Com_used_vehicleRepository com_used_vehicleRepository;

    @Inject
    private Com_used_vehicleService com_used_vehicleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_used_vehicleMockMvc;

    private Com_used_vehicle com_used_vehicle;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_used_vehicleResource com_used_vehicleResource = new Com_used_vehicleResource();
        ReflectionTestUtils.setField(com_used_vehicleResource, "com_used_vehicleService", com_used_vehicleService);
        this.restCom_used_vehicleMockMvc = MockMvcBuilders.standaloneSetup(com_used_vehicleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_used_vehicle = new Com_used_vehicle();
        com_used_vehicle.setVersion(DEFAULT_VERSION);
        com_used_vehicle.setAcquisition_amount(DEFAULT_ACQUISITION_AMOUNT);
        com_used_vehicle.setMonto_enajenacion(DEFAULT_MONTO_ENAJENACION);
        com_used_vehicle.setKey_vehicle(DEFAULT_KEY_VEHICLE);
        com_used_vehicle.setBrand(DEFAULT_BRAND);
        com_used_vehicle.setType(DEFAULT_TYPE);
        com_used_vehicle.setModel(DEFAULT_MODEL);
        com_used_vehicle.setNumber_engine(DEFAULT_NUMBER_ENGINE);
        com_used_vehicle.setNo_serie(DEFAULT_NO_SERIE);
        com_used_vehicle.setNiv(DEFAULT_NIV);
        com_used_vehicle.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createCom_used_vehicle() throws Exception {
        int databaseSizeBeforeCreate = com_used_vehicleRepository.findAll().size();

        // Create the Com_used_vehicle

        restCom_used_vehicleMockMvc.perform(post("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_used_vehicle)))
                .andExpect(status().isCreated());

        // Validate the Com_used_vehicle in the database
        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeCreate + 1);
        Com_used_vehicle testCom_used_vehicle = com_used_vehicles.get(com_used_vehicles.size() - 1);
        assertThat(testCom_used_vehicle.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_used_vehicle.getAcquisition_amount()).isEqualTo(DEFAULT_ACQUISITION_AMOUNT);
        assertThat(testCom_used_vehicle.getMonto_enajenacion()).isEqualTo(DEFAULT_MONTO_ENAJENACION);
        assertThat(testCom_used_vehicle.getKey_vehicle()).isEqualTo(DEFAULT_KEY_VEHICLE);
        assertThat(testCom_used_vehicle.getBrand()).isEqualTo(DEFAULT_BRAND);
        assertThat(testCom_used_vehicle.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCom_used_vehicle.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testCom_used_vehicle.getNumber_engine()).isEqualTo(DEFAULT_NUMBER_ENGINE);
        assertThat(testCom_used_vehicle.getNo_serie()).isEqualTo(DEFAULT_NO_SERIE);
        assertThat(testCom_used_vehicle.getNiv()).isEqualTo(DEFAULT_NIV);
        assertThat(testCom_used_vehicle.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_used_vehicleRepository.findAll().size();
        // set the field null
        com_used_vehicle.setVersion(null);

        // Create the Com_used_vehicle, which fails.

        restCom_used_vehicleMockMvc.perform(post("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_used_vehicle)))
                .andExpect(status().isBadRequest());

        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAcquisition_amountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_used_vehicleRepository.findAll().size();
        // set the field null
        com_used_vehicle.setAcquisition_amount(null);

        // Create the Com_used_vehicle, which fails.

        restCom_used_vehicleMockMvc.perform(post("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_used_vehicle)))
                .andExpect(status().isBadRequest());

        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonto_enajenacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_used_vehicleRepository.findAll().size();
        // set the field null
        com_used_vehicle.setMonto_enajenacion(null);

        // Create the Com_used_vehicle, which fails.

        restCom_used_vehicleMockMvc.perform(post("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_used_vehicle)))
                .andExpect(status().isBadRequest());

        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKey_vehicleIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_used_vehicleRepository.findAll().size();
        // set the field null
        com_used_vehicle.setKey_vehicle(null);

        // Create the Com_used_vehicle, which fails.

        restCom_used_vehicleMockMvc.perform(post("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_used_vehicle)))
                .andExpect(status().isBadRequest());

        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBrandIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_used_vehicleRepository.findAll().size();
        // set the field null
        com_used_vehicle.setBrand(null);

        // Create the Com_used_vehicle, which fails.

        restCom_used_vehicleMockMvc.perform(post("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_used_vehicle)))
                .andExpect(status().isBadRequest());

        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_used_vehicleRepository.findAll().size();
        // set the field null
        com_used_vehicle.setType(null);

        // Create the Com_used_vehicle, which fails.

        restCom_used_vehicleMockMvc.perform(post("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_used_vehicle)))
                .andExpect(status().isBadRequest());

        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_used_vehicleRepository.findAll().size();
        // set the field null
        com_used_vehicle.setModel(null);

        // Create the Com_used_vehicle, which fails.

        restCom_used_vehicleMockMvc.perform(post("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_used_vehicle)))
                .andExpect(status().isBadRequest());

        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNo_serieIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_used_vehicleRepository.findAll().size();
        // set the field null
        com_used_vehicle.setNo_serie(null);

        // Create the Com_used_vehicle, which fails.

        restCom_used_vehicleMockMvc.perform(post("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_used_vehicle)))
                .andExpect(status().isBadRequest());

        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNivIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_used_vehicleRepository.findAll().size();
        // set the field null
        com_used_vehicle.setNiv(null);

        // Create the Com_used_vehicle, which fails.

        restCom_used_vehicleMockMvc.perform(post("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_used_vehicle)))
                .andExpect(status().isBadRequest());

        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_used_vehicles() throws Exception {
        // Initialize the database
        com_used_vehicleRepository.saveAndFlush(com_used_vehicle);

        // Get all the com_used_vehicles
        restCom_used_vehicleMockMvc.perform(get("/api/com-used-vehicles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_used_vehicle.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].acquisition_amount").value(hasItem(DEFAULT_ACQUISITION_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].monto_enajenacion").value(hasItem(DEFAULT_MONTO_ENAJENACION.intValue())))
                .andExpect(jsonPath("$.[*].key_vehicle").value(hasItem(DEFAULT_KEY_VEHICLE.toString())))
                .andExpect(jsonPath("$.[*].brand").value(hasItem(DEFAULT_BRAND.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
                .andExpect(jsonPath("$.[*].number_engine").value(hasItem(DEFAULT_NUMBER_ENGINE.toString())))
                .andExpect(jsonPath("$.[*].no_serie").value(hasItem(DEFAULT_NO_SERIE.toString())))
                .andExpect(jsonPath("$.[*].niv").value(hasItem(DEFAULT_NIV.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }

    @Test
    @Transactional
    public void getCom_used_vehicle() throws Exception {
        // Initialize the database
        com_used_vehicleRepository.saveAndFlush(com_used_vehicle);

        // Get the com_used_vehicle
        restCom_used_vehicleMockMvc.perform(get("/api/com-used-vehicles/{id}", com_used_vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_used_vehicle.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.acquisition_amount").value(DEFAULT_ACQUISITION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.monto_enajenacion").value(DEFAULT_MONTO_ENAJENACION.intValue()))
            .andExpect(jsonPath("$.key_vehicle").value(DEFAULT_KEY_VEHICLE.toString()))
            .andExpect(jsonPath("$.brand").value(DEFAULT_BRAND.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.number_engine").value(DEFAULT_NUMBER_ENGINE.toString()))
            .andExpect(jsonPath("$.no_serie").value(DEFAULT_NO_SERIE.toString()))
            .andExpect(jsonPath("$.niv").value(DEFAULT_NIV.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_used_vehicle() throws Exception {
        // Get the com_used_vehicle
        restCom_used_vehicleMockMvc.perform(get("/api/com-used-vehicles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_used_vehicle() throws Exception {
        // Initialize the database
        com_used_vehicleService.save(com_used_vehicle);

        int databaseSizeBeforeUpdate = com_used_vehicleRepository.findAll().size();

        // Update the com_used_vehicle
        Com_used_vehicle updatedCom_used_vehicle = new Com_used_vehicle();
        updatedCom_used_vehicle.setId(com_used_vehicle.getId());
        updatedCom_used_vehicle.setVersion(UPDATED_VERSION);
        updatedCom_used_vehicle.setAcquisition_amount(UPDATED_ACQUISITION_AMOUNT);
        updatedCom_used_vehicle.setMonto_enajenacion(UPDATED_MONTO_ENAJENACION);
        updatedCom_used_vehicle.setKey_vehicle(UPDATED_KEY_VEHICLE);
        updatedCom_used_vehicle.setBrand(UPDATED_BRAND);
        updatedCom_used_vehicle.setType(UPDATED_TYPE);
        updatedCom_used_vehicle.setModel(UPDATED_MODEL);
        updatedCom_used_vehicle.setNumber_engine(UPDATED_NUMBER_ENGINE);
        updatedCom_used_vehicle.setNo_serie(UPDATED_NO_SERIE);
        updatedCom_used_vehicle.setNiv(UPDATED_NIV);
        updatedCom_used_vehicle.setValue(UPDATED_VALUE);

        restCom_used_vehicleMockMvc.perform(put("/api/com-used-vehicles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_used_vehicle)))
                .andExpect(status().isOk());

        // Validate the Com_used_vehicle in the database
        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeUpdate);
        Com_used_vehicle testCom_used_vehicle = com_used_vehicles.get(com_used_vehicles.size() - 1);
        assertThat(testCom_used_vehicle.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_used_vehicle.getAcquisition_amount()).isEqualTo(UPDATED_ACQUISITION_AMOUNT);
        assertThat(testCom_used_vehicle.getMonto_enajenacion()).isEqualTo(UPDATED_MONTO_ENAJENACION);
        assertThat(testCom_used_vehicle.getKey_vehicle()).isEqualTo(UPDATED_KEY_VEHICLE);
        assertThat(testCom_used_vehicle.getBrand()).isEqualTo(UPDATED_BRAND);
        assertThat(testCom_used_vehicle.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCom_used_vehicle.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testCom_used_vehicle.getNumber_engine()).isEqualTo(UPDATED_NUMBER_ENGINE);
        assertThat(testCom_used_vehicle.getNo_serie()).isEqualTo(UPDATED_NO_SERIE);
        assertThat(testCom_used_vehicle.getNiv()).isEqualTo(UPDATED_NIV);
        assertThat(testCom_used_vehicle.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteCom_used_vehicle() throws Exception {
        // Initialize the database
        com_used_vehicleService.save(com_used_vehicle);

        int databaseSizeBeforeDelete = com_used_vehicleRepository.findAll().size();

        // Get the com_used_vehicle
        restCom_used_vehicleMockMvc.perform(delete("/api/com-used-vehicles/{id}", com_used_vehicle.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_used_vehicle> com_used_vehicles = com_used_vehicleRepository.findAll();
        assertThat(com_used_vehicles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
