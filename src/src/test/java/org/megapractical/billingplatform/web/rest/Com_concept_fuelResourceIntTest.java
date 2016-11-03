package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_concept_fuel;
import org.megapractical.billingplatform.repository.Com_concept_fuelRepository;
import org.megapractical.billingplatform.service.Com_concept_fuelService;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Com_concept_fuelResource REST controller.
 *
 * @see Com_concept_fuelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_concept_fuelResourceIntTest {

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
    private Com_concept_fuelRepository com_concept_fuelRepository;

    @Inject
    private Com_concept_fuelService com_concept_fuelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_concept_fuelMockMvc;

    private Com_concept_fuel com_concept_fuel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_concept_fuelResource com_concept_fuelResource = new Com_concept_fuelResource();
        ReflectionTestUtils.setField(com_concept_fuelResource, "com_concept_fuelService", com_concept_fuelService);
        this.restCom_concept_fuelMockMvc = MockMvcBuilders.standaloneSetup(com_concept_fuelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_concept_fuel = new Com_concept_fuel();
        com_concept_fuel.setIdentifier(DEFAULT_IDENTIFIER);
        com_concept_fuel.setDate_expedition(DEFAULT_DATE_EXPEDITION);
        com_concept_fuel.setRfc(DEFAULT_RFC);
        com_concept_fuel.setKey_station(DEFAULT_KEY_STATION);
        com_concept_fuel.setQuantity(DEFAULT_QUANTITY);
        com_concept_fuel.setFuel_name(DEFAULT_FUEL_NAME);
        com_concept_fuel.setFolio_operation(DEFAULT_FOLIO_OPERATION);
        com_concept_fuel.setUnit_value(DEFAULT_UNIT_VALUE);
        com_concept_fuel.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createCom_concept_fuel() throws Exception {
        int databaseSizeBeforeCreate = com_concept_fuelRepository.findAll().size();

        // Create the Com_concept_fuel

        restCom_concept_fuelMockMvc.perform(post("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_concept_fuel)))
                .andExpect(status().isCreated());

        // Validate the Com_concept_fuel in the database
        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeCreate + 1);
        Com_concept_fuel testCom_concept_fuel = com_concept_fuels.get(com_concept_fuels.size() - 1);
        assertThat(testCom_concept_fuel.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testCom_concept_fuel.getDate_expedition()).isEqualTo(DEFAULT_DATE_EXPEDITION);
        assertThat(testCom_concept_fuel.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testCom_concept_fuel.getKey_station()).isEqualTo(DEFAULT_KEY_STATION);
        assertThat(testCom_concept_fuel.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCom_concept_fuel.getFuel_name()).isEqualTo(DEFAULT_FUEL_NAME);
        assertThat(testCom_concept_fuel.getFolio_operation()).isEqualTo(DEFAULT_FOLIO_OPERATION);
        assertThat(testCom_concept_fuel.getUnit_value()).isEqualTo(DEFAULT_UNIT_VALUE);
        assertThat(testCom_concept_fuel.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_concept_fuelRepository.findAll().size();
        // set the field null
        com_concept_fuel.setIdentifier(null);

        // Create the Com_concept_fuel, which fails.

        restCom_concept_fuelMockMvc.perform(post("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_expeditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_concept_fuelRepository.findAll().size();
        // set the field null
        com_concept_fuel.setDate_expedition(null);

        // Create the Com_concept_fuel, which fails.

        restCom_concept_fuelMockMvc.perform(post("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_concept_fuelRepository.findAll().size();
        // set the field null
        com_concept_fuel.setRfc(null);

        // Create the Com_concept_fuel, which fails.

        restCom_concept_fuelMockMvc.perform(post("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKey_stationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_concept_fuelRepository.findAll().size();
        // set the field null
        com_concept_fuel.setKey_station(null);

        // Create the Com_concept_fuel, which fails.

        restCom_concept_fuelMockMvc.perform(post("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_concept_fuelRepository.findAll().size();
        // set the field null
        com_concept_fuel.setQuantity(null);

        // Create the Com_concept_fuel, which fails.

        restCom_concept_fuelMockMvc.perform(post("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFuel_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_concept_fuelRepository.findAll().size();
        // set the field null
        com_concept_fuel.setFuel_name(null);

        // Create the Com_concept_fuel, which fails.

        restCom_concept_fuelMockMvc.perform(post("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolio_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_concept_fuelRepository.findAll().size();
        // set the field null
        com_concept_fuel.setFolio_operation(null);

        // Create the Com_concept_fuel, which fails.

        restCom_concept_fuelMockMvc.perform(post("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnit_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_concept_fuelRepository.findAll().size();
        // set the field null
        com_concept_fuel.setUnit_value(null);

        // Create the Com_concept_fuel, which fails.

        restCom_concept_fuelMockMvc.perform(post("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_concept_fuelRepository.findAll().size();
        // set the field null
        com_concept_fuel.setAmount(null);

        // Create the Com_concept_fuel, which fails.

        restCom_concept_fuelMockMvc.perform(post("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_concept_fuel)))
                .andExpect(status().isBadRequest());

        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_concept_fuels() throws Exception {
        // Initialize the database
        com_concept_fuelRepository.saveAndFlush(com_concept_fuel);

        // Get all the com_concept_fuels
        restCom_concept_fuelMockMvc.perform(get("/api/com-concept-fuels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_concept_fuel.getId().intValue())))
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
    public void getCom_concept_fuel() throws Exception {
        // Initialize the database
        com_concept_fuelRepository.saveAndFlush(com_concept_fuel);

        // Get the com_concept_fuel
        restCom_concept_fuelMockMvc.perform(get("/api/com-concept-fuels/{id}", com_concept_fuel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_concept_fuel.getId().intValue()))
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
    public void getNonExistingCom_concept_fuel() throws Exception {
        // Get the com_concept_fuel
        restCom_concept_fuelMockMvc.perform(get("/api/com-concept-fuels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_concept_fuel() throws Exception {
        // Initialize the database
        com_concept_fuelService.save(com_concept_fuel);

        int databaseSizeBeforeUpdate = com_concept_fuelRepository.findAll().size();

        // Update the com_concept_fuel
        Com_concept_fuel updatedCom_concept_fuel = new Com_concept_fuel();
        updatedCom_concept_fuel.setId(com_concept_fuel.getId());
        updatedCom_concept_fuel.setIdentifier(UPDATED_IDENTIFIER);
        updatedCom_concept_fuel.setDate_expedition(UPDATED_DATE_EXPEDITION);
        updatedCom_concept_fuel.setRfc(UPDATED_RFC);
        updatedCom_concept_fuel.setKey_station(UPDATED_KEY_STATION);
        updatedCom_concept_fuel.setQuantity(UPDATED_QUANTITY);
        updatedCom_concept_fuel.setFuel_name(UPDATED_FUEL_NAME);
        updatedCom_concept_fuel.setFolio_operation(UPDATED_FOLIO_OPERATION);
        updatedCom_concept_fuel.setUnit_value(UPDATED_UNIT_VALUE);
        updatedCom_concept_fuel.setAmount(UPDATED_AMOUNT);

        restCom_concept_fuelMockMvc.perform(put("/api/com-concept-fuels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_concept_fuel)))
                .andExpect(status().isOk());

        // Validate the Com_concept_fuel in the database
        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeUpdate);
        Com_concept_fuel testCom_concept_fuel = com_concept_fuels.get(com_concept_fuels.size() - 1);
        assertThat(testCom_concept_fuel.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testCom_concept_fuel.getDate_expedition()).isEqualTo(UPDATED_DATE_EXPEDITION);
        assertThat(testCom_concept_fuel.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testCom_concept_fuel.getKey_station()).isEqualTo(UPDATED_KEY_STATION);
        assertThat(testCom_concept_fuel.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCom_concept_fuel.getFuel_name()).isEqualTo(UPDATED_FUEL_NAME);
        assertThat(testCom_concept_fuel.getFolio_operation()).isEqualTo(UPDATED_FOLIO_OPERATION);
        assertThat(testCom_concept_fuel.getUnit_value()).isEqualTo(UPDATED_UNIT_VALUE);
        assertThat(testCom_concept_fuel.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteCom_concept_fuel() throws Exception {
        // Initialize the database
        com_concept_fuelService.save(com_concept_fuel);

        int databaseSizeBeforeDelete = com_concept_fuelRepository.findAll().size();

        // Get the com_concept_fuel
        restCom_concept_fuelMockMvc.perform(delete("/api/com-concept-fuels/{id}", com_concept_fuel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_concept_fuel> com_concept_fuels = com_concept_fuelRepository.findAll();
        assertThat(com_concept_fuels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
