package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_ecc_11_concept;
import org.megapractical.billingplatform.repository.Com_ecc_11_conceptRepository;
import org.megapractical.billingplatform.service.Com_ecc_11_conceptService;

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
 * Test class for the Com_ecc_11_conceptResource REST controller.
 *
 * @see Com_ecc_11_conceptResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_ecc_11_conceptResourceIntTest {

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
    private Com_ecc_11_conceptRepository com_ecc_11_conceptRepository;

    @Inject
    private Com_ecc_11_conceptService com_ecc_11_conceptService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_ecc_11_conceptMockMvc;

    private Com_ecc_11_concept com_ecc_11_concept;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_ecc_11_conceptResource com_ecc_11_conceptResource = new Com_ecc_11_conceptResource();
        ReflectionTestUtils.setField(com_ecc_11_conceptResource, "com_ecc_11_conceptService", com_ecc_11_conceptService);
        this.restCom_ecc_11_conceptMockMvc = MockMvcBuilders.standaloneSetup(com_ecc_11_conceptResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_ecc_11_concept = new Com_ecc_11_concept();
        com_ecc_11_concept.setIdentifier(DEFAULT_IDENTIFIER);
        com_ecc_11_concept.setDate(DEFAULT_DATE);
        com_ecc_11_concept.setRfc(DEFAULT_RFC);
        com_ecc_11_concept.setKey_station(DEFAULT_KEY_STATION);
        com_ecc_11_concept.setQuantity(DEFAULT_QUANTITY);
        com_ecc_11_concept.setUnit(DEFAULT_UNIT);
        com_ecc_11_concept.setFuel_name(DEFAULT_FUEL_NAME);
        com_ecc_11_concept.setFolio_operation(DEFAULT_FOLIO_OPERATION);
        com_ecc_11_concept.setUnit_value(DEFAULT_UNIT_VALUE);
        com_ecc_11_concept.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createCom_ecc_11_concept() throws Exception {
        int databaseSizeBeforeCreate = com_ecc_11_conceptRepository.findAll().size();

        // Create the Com_ecc_11_concept

        restCom_ecc_11_conceptMockMvc.perform(post("/api/com-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_concept)))
                .andExpect(status().isCreated());

        // Validate the Com_ecc_11_concept in the database
        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeCreate + 1);
        Com_ecc_11_concept testCom_ecc_11_concept = com_ecc_11_concepts.get(com_ecc_11_concepts.size() - 1);
        assertThat(testCom_ecc_11_concept.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testCom_ecc_11_concept.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCom_ecc_11_concept.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testCom_ecc_11_concept.getKey_station()).isEqualTo(DEFAULT_KEY_STATION);
        assertThat(testCom_ecc_11_concept.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testCom_ecc_11_concept.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testCom_ecc_11_concept.getFuel_name()).isEqualTo(DEFAULT_FUEL_NAME);
        assertThat(testCom_ecc_11_concept.getFolio_operation()).isEqualTo(DEFAULT_FOLIO_OPERATION);
        assertThat(testCom_ecc_11_concept.getUnit_value()).isEqualTo(DEFAULT_UNIT_VALUE);
        assertThat(testCom_ecc_11_concept.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_conceptRepository.findAll().size();
        // set the field null
        com_ecc_11_concept.setIdentifier(null);

        // Create the Com_ecc_11_concept, which fails.

        restCom_ecc_11_conceptMockMvc.perform(post("/api/com-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_concept)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_conceptRepository.findAll().size();
        // set the field null
        com_ecc_11_concept.setDate(null);

        // Create the Com_ecc_11_concept, which fails.

        restCom_ecc_11_conceptMockMvc.perform(post("/api/com-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_concept)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_conceptRepository.findAll().size();
        // set the field null
        com_ecc_11_concept.setRfc(null);

        // Create the Com_ecc_11_concept, which fails.

        restCom_ecc_11_conceptMockMvc.perform(post("/api/com-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_concept)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKey_stationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_conceptRepository.findAll().size();
        // set the field null
        com_ecc_11_concept.setKey_station(null);

        // Create the Com_ecc_11_concept, which fails.

        restCom_ecc_11_conceptMockMvc.perform(post("/api/com-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_concept)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_conceptRepository.findAll().size();
        // set the field null
        com_ecc_11_concept.setQuantity(null);

        // Create the Com_ecc_11_concept, which fails.

        restCom_ecc_11_conceptMockMvc.perform(post("/api/com-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_concept)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFuel_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_conceptRepository.findAll().size();
        // set the field null
        com_ecc_11_concept.setFuel_name(null);

        // Create the Com_ecc_11_concept, which fails.

        restCom_ecc_11_conceptMockMvc.perform(post("/api/com-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_concept)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolio_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_conceptRepository.findAll().size();
        // set the field null
        com_ecc_11_concept.setFolio_operation(null);

        // Create the Com_ecc_11_concept, which fails.

        restCom_ecc_11_conceptMockMvc.perform(post("/api/com-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_concept)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnit_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_conceptRepository.findAll().size();
        // set the field null
        com_ecc_11_concept.setUnit_value(null);

        // Create the Com_ecc_11_concept, which fails.

        restCom_ecc_11_conceptMockMvc.perform(post("/api/com-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_concept)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_ecc_11_concepts() throws Exception {
        // Initialize the database
        com_ecc_11_conceptRepository.saveAndFlush(com_ecc_11_concept);

        // Get all the com_ecc_11_concepts
        restCom_ecc_11_conceptMockMvc.perform(get("/api/com-ecc-11-concepts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_ecc_11_concept.getId().intValue())))
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
    public void getCom_ecc_11_concept() throws Exception {
        // Initialize the database
        com_ecc_11_conceptRepository.saveAndFlush(com_ecc_11_concept);

        // Get the com_ecc_11_concept
        restCom_ecc_11_conceptMockMvc.perform(get("/api/com-ecc-11-concepts/{id}", com_ecc_11_concept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_ecc_11_concept.getId().intValue()))
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
    public void getNonExistingCom_ecc_11_concept() throws Exception {
        // Get the com_ecc_11_concept
        restCom_ecc_11_conceptMockMvc.perform(get("/api/com-ecc-11-concepts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_ecc_11_concept() throws Exception {
        // Initialize the database
        com_ecc_11_conceptService.save(com_ecc_11_concept);

        int databaseSizeBeforeUpdate = com_ecc_11_conceptRepository.findAll().size();

        // Update the com_ecc_11_concept
        Com_ecc_11_concept updatedCom_ecc_11_concept = new Com_ecc_11_concept();
        updatedCom_ecc_11_concept.setId(com_ecc_11_concept.getId());
        updatedCom_ecc_11_concept.setIdentifier(UPDATED_IDENTIFIER);
        updatedCom_ecc_11_concept.setDate(UPDATED_DATE);
        updatedCom_ecc_11_concept.setRfc(UPDATED_RFC);
        updatedCom_ecc_11_concept.setKey_station(UPDATED_KEY_STATION);
        updatedCom_ecc_11_concept.setQuantity(UPDATED_QUANTITY);
        updatedCom_ecc_11_concept.setUnit(UPDATED_UNIT);
        updatedCom_ecc_11_concept.setFuel_name(UPDATED_FUEL_NAME);
        updatedCom_ecc_11_concept.setFolio_operation(UPDATED_FOLIO_OPERATION);
        updatedCom_ecc_11_concept.setUnit_value(UPDATED_UNIT_VALUE);
        updatedCom_ecc_11_concept.setAmount(UPDATED_AMOUNT);

        restCom_ecc_11_conceptMockMvc.perform(put("/api/com-ecc-11-concepts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_ecc_11_concept)))
                .andExpect(status().isOk());

        // Validate the Com_ecc_11_concept in the database
        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeUpdate);
        Com_ecc_11_concept testCom_ecc_11_concept = com_ecc_11_concepts.get(com_ecc_11_concepts.size() - 1);
        assertThat(testCom_ecc_11_concept.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testCom_ecc_11_concept.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCom_ecc_11_concept.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testCom_ecc_11_concept.getKey_station()).isEqualTo(UPDATED_KEY_STATION);
        assertThat(testCom_ecc_11_concept.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testCom_ecc_11_concept.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testCom_ecc_11_concept.getFuel_name()).isEqualTo(UPDATED_FUEL_NAME);
        assertThat(testCom_ecc_11_concept.getFolio_operation()).isEqualTo(UPDATED_FOLIO_OPERATION);
        assertThat(testCom_ecc_11_concept.getUnit_value()).isEqualTo(UPDATED_UNIT_VALUE);
        assertThat(testCom_ecc_11_concept.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteCom_ecc_11_concept() throws Exception {
        // Initialize the database
        com_ecc_11_conceptService.save(com_ecc_11_concept);

        int databaseSizeBeforeDelete = com_ecc_11_conceptRepository.findAll().size();

        // Get the com_ecc_11_concept
        restCom_ecc_11_conceptMockMvc.perform(delete("/api/com-ecc-11-concepts/{id}", com_ecc_11_concept.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_ecc_11_concept> com_ecc_11_concepts = com_ecc_11_conceptRepository.findAll();
        assertThat(com_ecc_11_concepts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
