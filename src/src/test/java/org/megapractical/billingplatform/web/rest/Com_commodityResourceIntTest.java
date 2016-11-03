package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_commodity;
import org.megapractical.billingplatform.repository.Com_commodityRepository;
import org.megapractical.billingplatform.service.Com_commodityService;

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
 * Test class for the Com_commodityResource REST controller.
 *
 * @see Com_commodityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_commodityResourceIntTest {

    private static final String DEFAULT_NOIDENTIFICATION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NOIDENTIFICATION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_CUSTOMS_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTOMS_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CUSTOMS_UNIT_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUSTOMS_UNIT_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DOLLAR_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DOLLAR_VALUE = new BigDecimal(2);

    @Inject
    private Com_commodityRepository com_commodityRepository;

    @Inject
    private Com_commodityService com_commodityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_commodityMockMvc;

    private Com_commodity com_commodity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_commodityResource com_commodityResource = new Com_commodityResource();
        ReflectionTestUtils.setField(com_commodityResource, "com_commodityService", com_commodityService);
        this.restCom_commodityMockMvc = MockMvcBuilders.standaloneSetup(com_commodityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_commodity = new Com_commodity();
        com_commodity.setNoidentification(DEFAULT_NOIDENTIFICATION);
        com_commodity.setCustoms_quantity(DEFAULT_CUSTOMS_QUANTITY);
        com_commodity.setCustoms_unit_value(DEFAULT_CUSTOMS_UNIT_VALUE);
        com_commodity.setDollar_value(DEFAULT_DOLLAR_VALUE);
    }

    @Test
    @Transactional
    public void createCom_commodity() throws Exception {
        int databaseSizeBeforeCreate = com_commodityRepository.findAll().size();

        // Create the Com_commodity

        restCom_commodityMockMvc.perform(post("/api/com-commodities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_commodity)))
                .andExpect(status().isCreated());

        // Validate the Com_commodity in the database
        List<Com_commodity> com_commodities = com_commodityRepository.findAll();
        assertThat(com_commodities).hasSize(databaseSizeBeforeCreate + 1);
        Com_commodity testCom_commodity = com_commodities.get(com_commodities.size() - 1);
        assertThat(testCom_commodity.getNoidentification()).isEqualTo(DEFAULT_NOIDENTIFICATION);
        assertThat(testCom_commodity.getCustoms_quantity()).isEqualTo(DEFAULT_CUSTOMS_QUANTITY);
        assertThat(testCom_commodity.getCustoms_unit_value()).isEqualTo(DEFAULT_CUSTOMS_UNIT_VALUE);
        assertThat(testCom_commodity.getDollar_value()).isEqualTo(DEFAULT_DOLLAR_VALUE);
    }

    @Test
    @Transactional
    public void checkNoidentificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_commodityRepository.findAll().size();
        // set the field null
        com_commodity.setNoidentification(null);

        // Create the Com_commodity, which fails.

        restCom_commodityMockMvc.perform(post("/api/com-commodities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_commodity)))
                .andExpect(status().isBadRequest());

        List<Com_commodity> com_commodities = com_commodityRepository.findAll();
        assertThat(com_commodities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDollar_valueIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_commodityRepository.findAll().size();
        // set the field null
        com_commodity.setDollar_value(null);

        // Create the Com_commodity, which fails.

        restCom_commodityMockMvc.perform(post("/api/com-commodities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_commodity)))
                .andExpect(status().isBadRequest());

        List<Com_commodity> com_commodities = com_commodityRepository.findAll();
        assertThat(com_commodities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_commodities() throws Exception {
        // Initialize the database
        com_commodityRepository.saveAndFlush(com_commodity);

        // Get all the com_commodities
        restCom_commodityMockMvc.perform(get("/api/com-commodities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_commodity.getId().intValue())))
                .andExpect(jsonPath("$.[*].noidentification").value(hasItem(DEFAULT_NOIDENTIFICATION.toString())))
                .andExpect(jsonPath("$.[*].customs_quantity").value(hasItem(DEFAULT_CUSTOMS_QUANTITY.intValue())))
                .andExpect(jsonPath("$.[*].customs_unit_value").value(hasItem(DEFAULT_CUSTOMS_UNIT_VALUE.intValue())))
                .andExpect(jsonPath("$.[*].dollar_value").value(hasItem(DEFAULT_DOLLAR_VALUE.intValue())));
    }

    @Test
    @Transactional
    public void getCom_commodity() throws Exception {
        // Initialize the database
        com_commodityRepository.saveAndFlush(com_commodity);

        // Get the com_commodity
        restCom_commodityMockMvc.perform(get("/api/com-commodities/{id}", com_commodity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_commodity.getId().intValue()))
            .andExpect(jsonPath("$.noidentification").value(DEFAULT_NOIDENTIFICATION.toString()))
            .andExpect(jsonPath("$.customs_quantity").value(DEFAULT_CUSTOMS_QUANTITY.intValue()))
            .andExpect(jsonPath("$.customs_unit_value").value(DEFAULT_CUSTOMS_UNIT_VALUE.intValue()))
            .andExpect(jsonPath("$.dollar_value").value(DEFAULT_DOLLAR_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_commodity() throws Exception {
        // Get the com_commodity
        restCom_commodityMockMvc.perform(get("/api/com-commodities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_commodity() throws Exception {
        // Initialize the database
        com_commodityService.save(com_commodity);

        int databaseSizeBeforeUpdate = com_commodityRepository.findAll().size();

        // Update the com_commodity
        Com_commodity updatedCom_commodity = new Com_commodity();
        updatedCom_commodity.setId(com_commodity.getId());
        updatedCom_commodity.setNoidentification(UPDATED_NOIDENTIFICATION);
        updatedCom_commodity.setCustoms_quantity(UPDATED_CUSTOMS_QUANTITY);
        updatedCom_commodity.setCustoms_unit_value(UPDATED_CUSTOMS_UNIT_VALUE);
        updatedCom_commodity.setDollar_value(UPDATED_DOLLAR_VALUE);

        restCom_commodityMockMvc.perform(put("/api/com-commodities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_commodity)))
                .andExpect(status().isOk());

        // Validate the Com_commodity in the database
        List<Com_commodity> com_commodities = com_commodityRepository.findAll();
        assertThat(com_commodities).hasSize(databaseSizeBeforeUpdate);
        Com_commodity testCom_commodity = com_commodities.get(com_commodities.size() - 1);
        assertThat(testCom_commodity.getNoidentification()).isEqualTo(UPDATED_NOIDENTIFICATION);
        assertThat(testCom_commodity.getCustoms_quantity()).isEqualTo(UPDATED_CUSTOMS_QUANTITY);
        assertThat(testCom_commodity.getCustoms_unit_value()).isEqualTo(UPDATED_CUSTOMS_UNIT_VALUE);
        assertThat(testCom_commodity.getDollar_value()).isEqualTo(UPDATED_DOLLAR_VALUE);
    }

    @Test
    @Transactional
    public void deleteCom_commodity() throws Exception {
        // Initialize the database
        com_commodityService.save(com_commodity);

        int databaseSizeBeforeDelete = com_commodityRepository.findAll().size();

        // Get the com_commodity
        restCom_commodityMockMvc.perform(delete("/api/com-commodities/{id}", com_commodity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_commodity> com_commodities = com_commodityRepository.findAll();
        assertThat(com_commodities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
