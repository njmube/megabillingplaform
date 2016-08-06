package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_concept_fuel;
import org.megapractical.billingplatform.repository.Freecom_concept_fuelRepository;
import org.megapractical.billingplatform.service.Freecom_concept_fuelService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_concept_fuelResource REST controller.
 *
 * @see Freecom_concept_fuelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_concept_fuelResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_IDENTIFIER = "AAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATE_EXPEDITION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_EXPEDITION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_EXPEDITION_STR = dateTimeFormatter.format(DEFAULT_DATE_EXPEDITION);
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";
    private static final String DEFAULT_KEY_STATION = "AAAAAAAAAA";
    private static final String UPDATED_KEY_STATION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final String DEFAULT_FUEL_NAME = "AAAAA";
    private static final String UPDATED_FUEL_NAME = "BBBBB";
    private static final String DEFAULT_FOLIO_OPERATION = "AAAAA";
    private static final String UPDATED_FOLIO_OPERATION = "BBBBB";

    private static final BigDecimal DEFAULT_UNIT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Freecom_concept_fuelRepository freecom_concept_fuelRepository;

    @Inject
    private Freecom_concept_fuelService freecom_concept_fuelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_concept_fuelMockMvc;

    private Freecom_concept_fuel freecom_concept_fuel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_concept_fuelResource freecom_concept_fuelResource = new Freecom_concept_fuelResource();
        ReflectionTestUtils.setField(freecom_concept_fuelResource, "freecom_concept_fuelService", freecom_concept_fuelService);
        this.restFreecom_concept_fuelMockMvc = MockMvcBuilders.standaloneSetup(freecom_concept_fuelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_concept_fuel = new Freecom_concept_fuel();
        freecom_concept_fuel.setIdentifier(DEFAULT_IDENTIFIER);
        freecom_concept_fuel.setDate_expedition(DEFAULT_DATE_EXPEDITION);
        freecom_concept_fuel.setRfc(DEFAULT_RFC);
        freecom_concept_fuel.setKey_station(DEFAULT_KEY_STATION);
        freecom_concept_fuel.setQuantity(DEFAULT_QUANTITY);
        freecom_concept_fuel.setFuel_name(DEFAULT_FUEL_NAME);
        freecom_concept_fuel.setFolio_operation(DEFAULT_FOLIO_OPERATION);
        freecom_concept_fuel.setUnit_value(DEFAULT_UNIT_VALUE);
        freecom_concept_fuel.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createFreecom_concept_fuel() throws Exception {
        int databaseSizeBeforeCreate = freecom_concept_fuelRepository.findAll().size();

        // Create the Freecom_concept_fuel

        restFreecom_concept_fuelMockMvc.perform(post("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_concept_fuel)))
                .andExpect(status().isCreated());

        // Validate the Freecom_concept_fuel in the database
        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_concept_fuel testFreecom_concept_fuel = freecom_concept_fuels.get(freecom_concept_fuels.size() - 1);
        assertThat(testFreecom_concept_fuel.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testFreecom_concept_fuel.getDate_expedition()).isEqualTo(DEFAULT_DATE_EXPEDITION);
        assertThat(testFreecom_concept_fuel.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFreecom_concept_fuel.getKey_station()).isEqualTo(DEFAULT_KEY_STATION);
        assertThat(testFreecom_concept_fuel.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testFreecom_concept_fuel.getFuel_name()).isEqualTo(DEFAULT_FUEL_NAME);
        assertThat(testFreecom_concept_fuel.getFolio_operation()).isEqualTo(DEFAULT_FOLIO_OPERATION);
        assertThat(testFreecom_concept_fuel.getUnit_value()).isEqualTo(DEFAULT_UNIT_VALUE);
        assertThat(testFreecom_concept_fuel.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_concept_fuelRepository.findAll().size();
        // set the field null
        freecom_concept_fuel.setIdentifier(null);

        // Create the Freecom_concept_fuel, which fails.

        restFreecom_concept_fuelMockMvc.perform(post("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_expeditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_concept_fuelRepository.findAll().size();
        // set the field null
        freecom_concept_fuel.setDate_expedition(null);

        // Create the Freecom_concept_fuel, which fails.

        restFreecom_concept_fuelMockMvc.perform(post("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_concept_fuelRepository.findAll().size();
        // set the field null
        freecom_concept_fuel.setRfc(null);

        // Create the Freecom_concept_fuel, which fails.

        restFreecom_concept_fuelMockMvc.perform(post("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKey_stationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_concept_fuelRepository.findAll().size();
        // set the field null
        freecom_concept_fuel.setKey_station(null);

        // Create the Freecom_concept_fuel, which fails.

        restFreecom_concept_fuelMockMvc.perform(post("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_concept_fuelRepository.findAll().size();
        // set the field null
        freecom_concept_fuel.setQuantity(null);

        // Create the Freecom_concept_fuel, which fails.

        restFreecom_concept_fuelMockMvc.perform(post("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFuel_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_concept_fuelRepository.findAll().size();
        // set the field null
        freecom_concept_fuel.setFuel_name(null);

        // Create the Freecom_concept_fuel, which fails.

        restFreecom_concept_fuelMockMvc.perform(post("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolio_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_concept_fuelRepository.findAll().size();
        // set the field null
        freecom_concept_fuel.setFolio_operation(null);

        // Create the Freecom_concept_fuel, which fails.

        restFreecom_concept_fuelMockMvc.perform(post("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnit_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_concept_fuelRepository.findAll().size();
        // set the field null
        freecom_concept_fuel.setUnit_value(null);

        // Create the Freecom_concept_fuel, which fails.

        restFreecom_concept_fuelMockMvc.perform(post("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_concept_fuelRepository.findAll().size();
        // set the field null
        freecom_concept_fuel.setAmount(null);

        // Create the Freecom_concept_fuel, which fails.

        restFreecom_concept_fuelMockMvc.perform(post("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_concept_fuels() throws Exception {
        // Initialize the database
        freecom_concept_fuelRepository.saveAndFlush(freecom_concept_fuel);

        // Get all the freecom_concept_fuels
        restFreecom_concept_fuelMockMvc.perform(get("/api/freecom-concept-fuels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_concept_fuel.getId().intValue())))
                .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
                .andExpect(jsonPath("$.[*].date_expedition").value(hasItem(DEFAULT_DATE_EXPEDITION_STR)))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].key_station").value(hasItem(DEFAULT_KEY_STATION.toString())))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
                .andExpect(jsonPath("$.[*].fuel_name").value(hasItem(DEFAULT_FUEL_NAME.toString())))
                .andExpect(jsonPath("$.[*].folio_operation").value(hasItem(DEFAULT_FOLIO_OPERATION.toString())))
                .andExpect(jsonPath("$.[*].unit_value").value(hasItem(DEFAULT_UNIT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_concept_fuel() throws Exception {
        // Initialize the database
        freecom_concept_fuelRepository.saveAndFlush(freecom_concept_fuel);

        // Get the freecom_concept_fuel
        restFreecom_concept_fuelMockMvc.perform(get("/api/freecom-concept-fuels/{id}", freecom_concept_fuel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_concept_fuel.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.date_expedition").value(DEFAULT_DATE_EXPEDITION_STR))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.key_station").value(DEFAULT_KEY_STATION.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.fuel_name").value(DEFAULT_FUEL_NAME.toString()))
            .andExpect(jsonPath("$.folio_operation").value(DEFAULT_FOLIO_OPERATION.toString()))
            .andExpect(jsonPath("$.unit_value").value(DEFAULT_UNIT_VALUE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_concept_fuel() throws Exception {
        // Get the freecom_concept_fuel
        restFreecom_concept_fuelMockMvc.perform(get("/api/freecom-concept-fuels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_concept_fuel() throws Exception {
        // Initialize the database
        freecom_concept_fuelService.save(freecom_concept_fuel);

        int databaseSizeBeforeUpdate = freecom_concept_fuelRepository.findAll().size();

        // Update the freecom_concept_fuel
        Freecom_concept_fuel updatedFreecom_concept_fuel = new Freecom_concept_fuel();
        updatedFreecom_concept_fuel.setId(freecom_concept_fuel.getId());
        updatedFreecom_concept_fuel.setIdentifier(UPDATED_IDENTIFIER);
        updatedFreecom_concept_fuel.setDate_expedition(UPDATED_DATE_EXPEDITION);
        updatedFreecom_concept_fuel.setRfc(UPDATED_RFC);
        updatedFreecom_concept_fuel.setKey_station(UPDATED_KEY_STATION);
        updatedFreecom_concept_fuel.setQuantity(UPDATED_QUANTITY);
        updatedFreecom_concept_fuel.setFuel_name(UPDATED_FUEL_NAME);
        updatedFreecom_concept_fuel.setFolio_operation(UPDATED_FOLIO_OPERATION);
        updatedFreecom_concept_fuel.setUnit_value(UPDATED_UNIT_VALUE);
        updatedFreecom_concept_fuel.setAmount(UPDATED_AMOUNT);

        restFreecom_concept_fuelMockMvc.perform(put("/api/freecom-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_concept_fuel)))
                .andExpect(status().isOk());

        // Validate the Freecom_concept_fuel in the database
        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeUpdate);
        Freecom_concept_fuel testFreecom_concept_fuel = freecom_concept_fuels.get(freecom_concept_fuels.size() - 1);
        assertThat(testFreecom_concept_fuel.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testFreecom_concept_fuel.getDate_expedition()).isEqualTo(UPDATED_DATE_EXPEDITION);
        assertThat(testFreecom_concept_fuel.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFreecom_concept_fuel.getKey_station()).isEqualTo(UPDATED_KEY_STATION);
        assertThat(testFreecom_concept_fuel.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testFreecom_concept_fuel.getFuel_name()).isEqualTo(UPDATED_FUEL_NAME);
        assertThat(testFreecom_concept_fuel.getFolio_operation()).isEqualTo(UPDATED_FOLIO_OPERATION);
        assertThat(testFreecom_concept_fuel.getUnit_value()).isEqualTo(UPDATED_UNIT_VALUE);
        assertThat(testFreecom_concept_fuel.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteFreecom_concept_fuel() throws Exception {
        // Initialize the database
        freecom_concept_fuelService.save(freecom_concept_fuel);

        int databaseSizeBeforeDelete = freecom_concept_fuelRepository.findAll().size();

        // Get the freecom_concept_fuel
        restFreecom_concept_fuelMockMvc.perform(delete("/api/freecom-concept-fuels/{id}", freecom_concept_fuel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_concept_fuel> freecom_concept_fuels = freecom_concept_fuelRepository.findAll();
        assertThat(freecom_concept_fuels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
