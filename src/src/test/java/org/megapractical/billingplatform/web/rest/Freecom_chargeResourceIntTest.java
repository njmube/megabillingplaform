package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_charge;
import org.megapractical.billingplatform.repository.Freecom_chargeRepository;
import org.megapractical.billingplatform.service.Freecom_chargeService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_chargeResource REST controller.
 *
 * @see Freecom_chargeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_chargeResourceIntTest {

    private static final String DEFAULT_CODECHARGE = "AAAAA";
    private static final String UPDATED_CODECHARGE = "BBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Freecom_chargeRepository freecom_chargeRepository;

    @Inject
    private Freecom_chargeService freecom_chargeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_chargeMockMvc;

    private Freecom_charge freecom_charge;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_chargeResource freecom_chargeResource = new Freecom_chargeResource();
        ReflectionTestUtils.setField(freecom_chargeResource, "freecom_chargeService", freecom_chargeService);
        this.restFreecom_chargeMockMvc = MockMvcBuilders.standaloneSetup(freecom_chargeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_charge = new Freecom_charge();
        freecom_charge.setCodecharge(DEFAULT_CODECHARGE);
        freecom_charge.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createFreecom_charge() throws Exception {
        int databaseSizeBeforeCreate = freecom_chargeRepository.findAll().size();

        // Create the Freecom_charge

        restFreecom_chargeMockMvc.perform(post("/api/freecom-charges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_charge)))
                .andExpect(status().isCreated());

        // Validate the Freecom_charge in the database
        List<Freecom_charge> freecom_charges = freecom_chargeRepository.findAll();
        assertThat(freecom_charges).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_charge testFreecom_charge = freecom_charges.get(freecom_charges.size() - 1);
        assertThat(testFreecom_charge.getCodecharge()).isEqualTo(DEFAULT_CODECHARGE);
        assertThat(testFreecom_charge.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkCodechargeIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_chargeRepository.findAll().size();
        // set the field null
        freecom_charge.setCodecharge(null);

        // Create the Freecom_charge, which fails.

        restFreecom_chargeMockMvc.perform(post("/api/freecom-charges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_charge)))
                .andExpect(status().isBadRequest());

        List<Freecom_charge> freecom_charges = freecom_chargeRepository.findAll();
        assertThat(freecom_charges).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_chargeRepository.findAll().size();
        // set the field null
        freecom_charge.setAmount(null);

        // Create the Freecom_charge, which fails.

        restFreecom_chargeMockMvc.perform(post("/api/freecom-charges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_charge)))
                .andExpect(status().isBadRequest());

        List<Freecom_charge> freecom_charges = freecom_chargeRepository.findAll();
        assertThat(freecom_charges).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_charges() throws Exception {
        // Initialize the database
        freecom_chargeRepository.saveAndFlush(freecom_charge);

        // Get all the freecom_charges
        restFreecom_chargeMockMvc.perform(get("/api/freecom-charges?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_charge.getId().intValue())))
                .andExpect(jsonPath("$.[*].codecharge").value(hasItem(DEFAULT_CODECHARGE.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_charge() throws Exception {
        // Initialize the database
        freecom_chargeRepository.saveAndFlush(freecom_charge);

        // Get the freecom_charge
        restFreecom_chargeMockMvc.perform(get("/api/freecom-charges/{id}", freecom_charge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_charge.getId().intValue()))
            .andExpect(jsonPath("$.codecharge").value(DEFAULT_CODECHARGE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_charge() throws Exception {
        // Get the freecom_charge
        restFreecom_chargeMockMvc.perform(get("/api/freecom-charges/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_charge() throws Exception {
        // Initialize the database
        freecom_chargeService.save(freecom_charge);

        int databaseSizeBeforeUpdate = freecom_chargeRepository.findAll().size();

        // Update the freecom_charge
        Freecom_charge updatedFreecom_charge = new Freecom_charge();
        updatedFreecom_charge.setId(freecom_charge.getId());
        updatedFreecom_charge.setCodecharge(UPDATED_CODECHARGE);
        updatedFreecom_charge.setAmount(UPDATED_AMOUNT);

        restFreecom_chargeMockMvc.perform(put("/api/freecom-charges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_charge)))
                .andExpect(status().isOk());

        // Validate the Freecom_charge in the database
        List<Freecom_charge> freecom_charges = freecom_chargeRepository.findAll();
        assertThat(freecom_charges).hasSize(databaseSizeBeforeUpdate);
        Freecom_charge testFreecom_charge = freecom_charges.get(freecom_charges.size() - 1);
        assertThat(testFreecom_charge.getCodecharge()).isEqualTo(UPDATED_CODECHARGE);
        assertThat(testFreecom_charge.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteFreecom_charge() throws Exception {
        // Initialize the database
        freecom_chargeService.save(freecom_charge);

        int databaseSizeBeforeDelete = freecom_chargeRepository.findAll().size();

        // Get the freecom_charge
        restFreecom_chargeMockMvc.perform(delete("/api/freecom-charges/{id}", freecom_charge.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_charge> freecom_charges = freecom_chargeRepository.findAll();
        assertThat(freecom_charges).hasSize(databaseSizeBeforeDelete - 1);
    }
}
