package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_ecc11_concept;
import org.megapractical.billingplatform.repository.Freecom_ecc11_conceptRepository;
import org.megapractical.billingplatform.service.Freecom_ecc11_conceptService;

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
 * Test class for the Freecom_ecc11_conceptResource REST controller.
 *
 * @see Freecom_ecc11_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_ecc11_conceptResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_IDENTIFIER = "AAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";
    private static final String DEFAULT_KEY_STATION = "AAAAAAAAAA";
    private static final String UPDATED_KEY_STATION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final String DEFAULT_UNIT = "AAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_FUEL_NAME = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_FUEL_NAME = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_FOLIO_OPERATION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_FOLIO_OPERATION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_UNIT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Freecom_ecc11_conceptRepository freecom_ecc11_conceptRepository;

    @Inject
    private Freecom_ecc11_conceptService freecom_ecc11_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_ecc11_conceptMockMvc;

    private Freecom_ecc11_concept freecom_ecc11_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_ecc11_conceptResource freecom_ecc11_conceptResource = new Freecom_ecc11_conceptResource();
        ReflectionTestUtils.setField(freecom_ecc11_conceptResource, "freecom_ecc11_conceptService", freecom_ecc11_conceptService);
        this.restFreecom_ecc11_conceptMockMvc = MockMvcBuilders.standaloneSetup(freecom_ecc11_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_ecc11_concept = new Freecom_ecc11_concept();
        freecom_ecc11_concept.setIdentifier(DEFAULT_IDENTIFIER);
        freecom_ecc11_concept.setDate(DEFAULT_DATE);
        freecom_ecc11_concept.setRfc(DEFAULT_RFC);
        freecom_ecc11_concept.setKey_station(DEFAULT_KEY_STATION);
        freecom_ecc11_concept.setQuantity(DEFAULT_QUANTITY);
        freecom_ecc11_concept.setUnit(DEFAULT_UNIT);
        freecom_ecc11_concept.setFuel_name(DEFAULT_FUEL_NAME);
        freecom_ecc11_concept.setFolio_operation(DEFAULT_FOLIO_OPERATION);
        freecom_ecc11_concept.setUnit_value(DEFAULT_UNIT_VALUE);
        freecom_ecc11_concept.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createFreecom_ecc11_concept() throws Exception {
        int databaseSizeBeforeCreate = freecom_ecc11_conceptRepository.findAll().size();

        // Create the Freecom_ecc11_concept

        restFreecom_ecc11_conceptMockMvc.perform(post("/api/freecom-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_concept)))
                .andExpect(status().isCreated());

        // Validate the Freecom_ecc11_concept in the database
        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_ecc11_concept testFreecom_ecc11_concept = freecom_ecc11_concepts.get(freecom_ecc11_concepts.size() - 1);
        assertThat(testFreecom_ecc11_concept.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testFreecom_ecc11_concept.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFreecom_ecc11_concept.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFreecom_ecc11_concept.getKey_station()).isEqualTo(DEFAULT_KEY_STATION);
        assertThat(testFreecom_ecc11_concept.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testFreecom_ecc11_concept.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testFreecom_ecc11_concept.getFuel_name()).isEqualTo(DEFAULT_FUEL_NAME);
        assertThat(testFreecom_ecc11_concept.getFolio_operation()).isEqualTo(DEFAULT_FOLIO_OPERATION);
        assertThat(testFreecom_ecc11_concept.getUnit_value()).isEqualTo(DEFAULT_UNIT_VALUE);
        assertThat(testFreecom_ecc11_concept.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_conceptRepository.findAll().size();
        // set the field null
        freecom_ecc11_concept.setIdentifier(null);

        // Create the Freecom_ecc11_concept, which fails.

        restFreecom_ecc11_conceptMockMvc.perform(post("/api/freecom-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_concept)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_conceptRepository.findAll().size();
        // set the field null
        freecom_ecc11_concept.setDate(null);

        // Create the Freecom_ecc11_concept, which fails.

        restFreecom_ecc11_conceptMockMvc.perform(post("/api/freecom-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_concept)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_conceptRepository.findAll().size();
        // set the field null
        freecom_ecc11_concept.setRfc(null);

        // Create the Freecom_ecc11_concept, which fails.

        restFreecom_ecc11_conceptMockMvc.perform(post("/api/freecom-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_concept)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKey_stationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_conceptRepository.findAll().size();
        // set the field null
        freecom_ecc11_concept.setKey_station(null);

        // Create the Freecom_ecc11_concept, which fails.

        restFreecom_ecc11_conceptMockMvc.perform(post("/api/freecom-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_concept)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_conceptRepository.findAll().size();
        // set the field null
        freecom_ecc11_concept.setQuantity(null);

        // Create the Freecom_ecc11_concept, which fails.

        restFreecom_ecc11_conceptMockMvc.perform(post("/api/freecom-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_concept)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFuel_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_conceptRepository.findAll().size();
        // set the field null
        freecom_ecc11_concept.setFuel_name(null);

        // Create the Freecom_ecc11_concept, which fails.

        restFreecom_ecc11_conceptMockMvc.perform(post("/api/freecom-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_concept)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolio_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_conceptRepository.findAll().size();
        // set the field null
        freecom_ecc11_concept.setFolio_operation(null);

        // Create the Freecom_ecc11_concept, which fails.

        restFreecom_ecc11_conceptMockMvc.perform(post("/api/freecom-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_concept)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnit_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_conceptRepository.findAll().size();
        // set the field null
        freecom_ecc11_concept.setUnit_value(null);

        // Create the Freecom_ecc11_concept, which fails.

        restFreecom_ecc11_conceptMockMvc.perform(post("/api/freecom-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_concept)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_ecc11_concepts() throws Exception {
        // Initialize the database
        freecom_ecc11_conceptRepository.saveAndFlush(freecom_ecc11_concept);

        // Get all the freecom_ecc11_concepts
        restFreecom_ecc11_conceptMockMvc.perform(get("/api/freecom-ecc-11-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_ecc11_concept.getId().intValue())))
                .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].key_station").value(hasItem(DEFAULT_KEY_STATION.toString())))
                .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
                .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
                .andExpect(jsonPath("$.[*].fuel_name").value(hasItem(DEFAULT_FUEL_NAME.toString())))
                .andExpect(jsonPath("$.[*].folio_operation").value(hasItem(DEFAULT_FOLIO_OPERATION.toString())))
                .andExpect(jsonPath("$.[*].unit_value").value(hasItem(DEFAULT_UNIT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_ecc11_concept() throws Exception {
        // Initialize the database
        freecom_ecc11_conceptRepository.saveAndFlush(freecom_ecc11_concept);

        // Get the freecom_ecc11_concept
        restFreecom_ecc11_conceptMockMvc.perform(get("/api/freecom-ecc-11-concepts/{id}", freecom_ecc11_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_ecc11_concept.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.key_station").value(DEFAULT_KEY_STATION.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.fuel_name").value(DEFAULT_FUEL_NAME.toString()))
            .andExpect(jsonPath("$.folio_operation").value(DEFAULT_FOLIO_OPERATION.toString()))
            .andExpect(jsonPath("$.unit_value").value(DEFAULT_UNIT_VALUE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_ecc11_concept() throws Exception {
        // Get the freecom_ecc11_concept
        restFreecom_ecc11_conceptMockMvc.perform(get("/api/freecom-ecc-11-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_ecc11_concept() throws Exception {
        // Initialize the database
        freecom_ecc11_conceptService.save(freecom_ecc11_concept);

        int databaseSizeBeforeUpdate = freecom_ecc11_conceptRepository.findAll().size();

        // Update the freecom_ecc11_concept
        Freecom_ecc11_concept updatedFreecom_ecc11_concept = new Freecom_ecc11_concept();
        updatedFreecom_ecc11_concept.setId(freecom_ecc11_concept.getId());
        updatedFreecom_ecc11_concept.setIdentifier(UPDATED_IDENTIFIER);
        updatedFreecom_ecc11_concept.setDate(UPDATED_DATE);
        updatedFreecom_ecc11_concept.setRfc(UPDATED_RFC);
        updatedFreecom_ecc11_concept.setKey_station(UPDATED_KEY_STATION);
        updatedFreecom_ecc11_concept.setQuantity(UPDATED_QUANTITY);
        updatedFreecom_ecc11_concept.setUnit(UPDATED_UNIT);
        updatedFreecom_ecc11_concept.setFuel_name(UPDATED_FUEL_NAME);
        updatedFreecom_ecc11_concept.setFolio_operation(UPDATED_FOLIO_OPERATION);
        updatedFreecom_ecc11_concept.setUnit_value(UPDATED_UNIT_VALUE);
        updatedFreecom_ecc11_concept.setAmount(UPDATED_AMOUNT);

        restFreecom_ecc11_conceptMockMvc.perform(put("/api/freecom-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_ecc11_concept)))
                .andExpect(status().isOk());

        // Validate the Freecom_ecc11_concept in the database
        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeUpdate);
        Freecom_ecc11_concept testFreecom_ecc11_concept = freecom_ecc11_concepts.get(freecom_ecc11_concepts.size() - 1);
        assertThat(testFreecom_ecc11_concept.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testFreecom_ecc11_concept.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFreecom_ecc11_concept.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFreecom_ecc11_concept.getKey_station()).isEqualTo(UPDATED_KEY_STATION);
        assertThat(testFreecom_ecc11_concept.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testFreecom_ecc11_concept.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testFreecom_ecc11_concept.getFuel_name()).isEqualTo(UPDATED_FUEL_NAME);
        assertThat(testFreecom_ecc11_concept.getFolio_operation()).isEqualTo(UPDATED_FOLIO_OPERATION);
        assertThat(testFreecom_ecc11_concept.getUnit_value()).isEqualTo(UPDATED_UNIT_VALUE);
        assertThat(testFreecom_ecc11_concept.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteFreecom_ecc11_concept() throws Exception {
        // Initialize the database
        freecom_ecc11_conceptService.save(freecom_ecc11_concept);

        int databaseSizeBeforeDelete = freecom_ecc11_conceptRepository.findAll().size();

        // Get the freecom_ecc11_concept
        restFreecom_ecc11_conceptMockMvc.perform(delete("/api/freecom-ecc-11-concepts/{id}", freecom_ecc11_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_ecc11_concept> freecom_ecc11_concepts = freecom_ecc11_conceptRepository.findAll();
        assertThat(freecom_ecc11_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
