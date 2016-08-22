package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_commodity;
import org.megapractical.billingplatform.repository.Freecom_commodityRepository;
import org.megapractical.billingplatform.service.Freecom_commodityService;

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
 * Test class for the Freecom_commodityResource REST controller.
 *
 * @see Freecom_commodityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_commodityResourceIntTest {

    private static final String DEFAULT_NOIDENTIFICATION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NOIDENTIFICATION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_CUSTOMS_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTOMS_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CUSTOMS_UNIT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTOMS_UNIT_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DOLLAR_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DOLLAR_VALUE = new BigDecimal(2);

    @Inject
    private Freecom_commodityRepository freecom_commodityRepository;

    @Inject
    private Freecom_commodityService freecom_commodityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_commodityMockMvc;

    private Freecom_commodity freecom_commodity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_commodityResource freecom_commodityResource = new Freecom_commodityResource();
        ReflectionTestUtils.setField(freecom_commodityResource, "freecom_commodityService", freecom_commodityService);
        this.restFreecom_commodityMockMvc = MockMvcBuilders.standaloneSetup(freecom_commodityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_commodity = new Freecom_commodity();
        freecom_commodity.setNoidentification(DEFAULT_NOIDENTIFICATION);
        freecom_commodity.setCustoms_quantity(DEFAULT_CUSTOMS_QUANTITY);
        freecom_commodity.setCustoms_unit_value(DEFAULT_CUSTOMS_UNIT_VALUE);
        freecom_commodity.setDollar_value(DEFAULT_DOLLAR_VALUE);
    }

    @Test
    @Transactional
    public void createFreecom_commodity() throws Exception {
        int databaseSizeBeforeCreate = freecom_commodityRepository.findAll().size();

        // Create the Freecom_commodity

        restFreecom_commodityMockMvc.perform(post("/api/freecom-commodities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_commodity)))
                .andExpect(status().isCreated());

        // Validate the Freecom_commodity in the database
        List<Freecom_commodity> freecom_commodities = freecom_commodityRepository.findAll();
        assertThat(freecom_commodities).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_commodity testFreecom_commodity = freecom_commodities.get(freecom_commodities.size() - 1);
        assertThat(testFreecom_commodity.getNoidentification()).isEqualTo(DEFAULT_NOIDENTIFICATION);
        assertThat(testFreecom_commodity.getCustoms_quantity()).isEqualTo(DEFAULT_CUSTOMS_QUANTITY);
        assertThat(testFreecom_commodity.getCustoms_unit_value()).isEqualTo(DEFAULT_CUSTOMS_UNIT_VALUE);
        assertThat(testFreecom_commodity.getDollar_value()).isEqualTo(DEFAULT_DOLLAR_VALUE);
    }

    @Test
    @Transactional
    public void checkNoidentificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_commodityRepository.findAll().size();
        // set the field null
        freecom_commodity.setNoidentification(null);

        // Create the Freecom_commodity, which fails.

        restFreecom_commodityMockMvc.perform(post("/api/freecom-commodities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_commodity)))
                .andExpect(status().isBadRequest());

        List<Freecom_commodity> freecom_commodities = freecom_commodityRepository.findAll();
        assertThat(freecom_commodities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDollar_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_commodityRepository.findAll().size();
        // set the field null
        freecom_commodity.setDollar_value(null);

        // Create the Freecom_commodity, which fails.

        restFreecom_commodityMockMvc.perform(post("/api/freecom-commodities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_commodity)))
                .andExpect(status().isBadRequest());

        List<Freecom_commodity> freecom_commodities = freecom_commodityRepository.findAll();
        assertThat(freecom_commodities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_commodities() throws Exception {
        // Initialize the database
        freecom_commodityRepository.saveAndFlush(freecom_commodity);

        // Get all the freecom_commodities
        restFreecom_commodityMockMvc.perform(get("/api/freecom-commodities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_commodity.getId().intValue())))
                .andExpect(jsonPath("$.[*].noidentification").value(hasItem(DEFAULT_NOIDENTIFICATION.toString())))
                .andExpect(jsonPath("$.[*].customs_quantity").value(hasItem(DEFAULT_CUSTOMS_QUANTITY.intValue())))
                .andExpect(jsonPath("$.[*].customs_unit_value").value(hasItem(DEFAULT_CUSTOMS_UNIT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].dollar_value").value(hasItem(DEFAULT_DOLLAR_VALUE.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_commodity() throws Exception {
        // Initialize the database
        freecom_commodityRepository.saveAndFlush(freecom_commodity);

        // Get the freecom_commodity
        restFreecom_commodityMockMvc.perform(get("/api/freecom-commodities/{id}", freecom_commodity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_commodity.getId().intValue()))
            .andExpect(jsonPath("$.noidentification").value(DEFAULT_NOIDENTIFICATION.toString()))
            .andExpect(jsonPath("$.customs_quantity").value(DEFAULT_CUSTOMS_QUANTITY.intValue()))
            .andExpect(jsonPath("$.customs_unit_value").value(DEFAULT_CUSTOMS_UNIT_VALUE.intValue()))
            .andExpect(jsonPath("$.dollar_value").value(DEFAULT_DOLLAR_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_commodity() throws Exception {
        // Get the freecom_commodity
        restFreecom_commodityMockMvc.perform(get("/api/freecom-commodities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_commodity() throws Exception {
        // Initialize the database
        freecom_commodityService.save(freecom_commodity);

        int databaseSizeBeforeUpdate = freecom_commodityRepository.findAll().size();

        // Update the freecom_commodity
        Freecom_commodity updatedFreecom_commodity = new Freecom_commodity();
        updatedFreecom_commodity.setId(freecom_commodity.getId());
        updatedFreecom_commodity.setNoidentification(UPDATED_NOIDENTIFICATION);
        updatedFreecom_commodity.setCustoms_quantity(UPDATED_CUSTOMS_QUANTITY);
        updatedFreecom_commodity.setCustoms_unit_value(UPDATED_CUSTOMS_UNIT_VALUE);
        updatedFreecom_commodity.setDollar_value(UPDATED_DOLLAR_VALUE);

        restFreecom_commodityMockMvc.perform(put("/api/freecom-commodities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_commodity)))
                .andExpect(status().isOk());

        // Validate the Freecom_commodity in the database
        List<Freecom_commodity> freecom_commodities = freecom_commodityRepository.findAll();
        assertThat(freecom_commodities).hasSize(databaseSizeBeforeUpdate);
        Freecom_commodity testFreecom_commodity = freecom_commodities.get(freecom_commodities.size() - 1);
        assertThat(testFreecom_commodity.getNoidentification()).isEqualTo(UPDATED_NOIDENTIFICATION);
        assertThat(testFreecom_commodity.getCustoms_quantity()).isEqualTo(UPDATED_CUSTOMS_QUANTITY);
        assertThat(testFreecom_commodity.getCustoms_unit_value()).isEqualTo(UPDATED_CUSTOMS_UNIT_VALUE);
        assertThat(testFreecom_commodity.getDollar_value()).isEqualTo(UPDATED_DOLLAR_VALUE);
    }

    @Test
    @Transactional
    public void deleteFreecom_commodity() throws Exception {
        // Initialize the database
        freecom_commodityService.save(freecom_commodity);

        int databaseSizeBeforeDelete = freecom_commodityRepository.findAll().size();

        // Get the freecom_commodity
        restFreecom_commodityMockMvc.perform(delete("/api/freecom-commodities/{id}", freecom_commodity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_commodity> freecom_commodities = freecom_commodityRepository.findAll();
        assertThat(freecom_commodities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
