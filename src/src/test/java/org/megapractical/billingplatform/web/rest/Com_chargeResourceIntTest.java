package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_charge;
import org.megapractical.billingplatform.repository.Com_chargeRepository;
import org.megapractical.billingplatform.service.Com_chargeService;

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
 * Test class for the Com_chargeResource REST controller.
 *
 * @see Com_chargeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_chargeResourceIntTest {

    private static final String DEFAULT_CODECHARGE = "AAAAA";
    private static final String UPDATED_CODECHARGE = "BBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Com_chargeRepository com_chargeRepository;

    @Inject
    private Com_chargeService com_chargeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_chargeMockMvc;

    private Com_charge com_charge;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_chargeResource com_chargeResource = new Com_chargeResource();
        ReflectionTestUtils.setField(com_chargeResource, "com_chargeService", com_chargeService);
        this.restCom_chargeMockMvc = MockMvcBuilders.standaloneSetup(com_chargeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_charge = new Com_charge();
        com_charge.setCodecharge(DEFAULT_CODECHARGE);
        com_charge.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createCom_charge() throws Exception {
        int databaseSizeBeforeCreate = com_chargeRepository.findAll().size();

        // Create the Com_charge

        restCom_chargeMockMvc.perform(post("/api/com-charges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_charge)))
                .andExpect(status().isCreated());

        // Validate the Com_charge in the database
        List<Com_charge> com_charges = com_chargeRepository.findAll();
        assertThat(com_charges).hasSize(databaseSizeBeforeCreate + 1);
        Com_charge testCom_charge = com_charges.get(com_charges.size() - 1);
        assertThat(testCom_charge.getCodecharge()).isEqualTo(DEFAULT_CODECHARGE);
        assertThat(testCom_charge.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkCodechargeIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_chargeRepository.findAll().size();
        // set the field null
        com_charge.setCodecharge(null);

        // Create the Com_charge, which fails.

        restCom_chargeMockMvc.perform(post("/api/com-charges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_charge)))
                .andExpect(status().isBadRequest());

        List<Com_charge> com_charges = com_chargeRepository.findAll();
        assertThat(com_charges).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_chargeRepository.findAll().size();
        // set the field null
        com_charge.setAmount(null);

        // Create the Com_charge, which fails.

        restCom_chargeMockMvc.perform(post("/api/com-charges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_charge)))
                .andExpect(status().isBadRequest());

        List<Com_charge> com_charges = com_chargeRepository.findAll();
        assertThat(com_charges).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_charges() throws Exception {
        // Initialize the database
        com_chargeRepository.saveAndFlush(com_charge);

        // Get all the com_charges
        restCom_chargeMockMvc.perform(get("/api/com-charges?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_charge.getId().intValue())))
                .andExpect(jsonPath("$.[*].codecharge").value(hasItem(DEFAULT_CODECHARGE.toString())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getCom_charge() throws Exception {
        // Initialize the database
        com_chargeRepository.saveAndFlush(com_charge);

        // Get the com_charge
        restCom_chargeMockMvc.perform(get("/api/com-charges/{id}", com_charge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_charge.getId().intValue()))
            .andExpect(jsonPath("$.codecharge").value(DEFAULT_CODECHARGE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_charge() throws Exception {
        // Get the com_charge
        restCom_chargeMockMvc.perform(get("/api/com-charges/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_charge() throws Exception {
        // Initialize the database
        com_chargeService.save(com_charge);

        int databaseSizeBeforeUpdate = com_chargeRepository.findAll().size();

        // Update the com_charge
        Com_charge updatedCom_charge = new Com_charge();
        updatedCom_charge.setId(com_charge.getId());
        updatedCom_charge.setCodecharge(UPDATED_CODECHARGE);
        updatedCom_charge.setAmount(UPDATED_AMOUNT);

        restCom_chargeMockMvc.perform(put("/api/com-charges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_charge)))
                .andExpect(status().isOk());

        // Validate the Com_charge in the database
        List<Com_charge> com_charges = com_chargeRepository.findAll();
        assertThat(com_charges).hasSize(databaseSizeBeforeUpdate);
        Com_charge testCom_charge = com_charges.get(com_charges.size() - 1);
        assertThat(testCom_charge.getCodecharge()).isEqualTo(UPDATED_CODECHARGE);
        assertThat(testCom_charge.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteCom_charge() throws Exception {
        // Initialize the database
        com_chargeService.save(com_charge);

        int databaseSizeBeforeDelete = com_chargeRepository.findAll().size();

        // Get the com_charge
        restCom_chargeMockMvc.perform(delete("/api/com-charges/{id}", com_charge.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_charge> com_charges = com_chargeRepository.findAll();
        assertThat(com_charges).hasSize(databaseSizeBeforeDelete - 1);
    }
}
