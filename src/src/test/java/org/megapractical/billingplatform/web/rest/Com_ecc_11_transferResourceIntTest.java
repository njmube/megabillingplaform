package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_ecc_11_transfer;
import org.megapractical.billingplatform.repository.Com_ecc_11_transferRepository;
import org.megapractical.billingplatform.service.Com_ecc_11_transferService;

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
 * Test class for the Com_ecc_11_transferResource REST controller.
 *
 * @see Com_ecc_11_transferResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_ecc_11_transferResourceIntTest {

    private static final String DEFAULT_TYPE_TAX = "AAAA";
    private static final String UPDATED_TYPE_TAX = "BBBB";

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Com_ecc_11_transferRepository com_ecc_11_transferRepository;

    @Inject
    private Com_ecc_11_transferService com_ecc_11_transferService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_ecc_11_transferMockMvc;

    private Com_ecc_11_transfer com_ecc_11_transfer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_ecc_11_transferResource com_ecc_11_transferResource = new Com_ecc_11_transferResource();
        ReflectionTestUtils.setField(com_ecc_11_transferResource, "com_ecc_11_transferService", com_ecc_11_transferService);
        this.restCom_ecc_11_transferMockMvc = MockMvcBuilders.standaloneSetup(com_ecc_11_transferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_ecc_11_transfer = new Com_ecc_11_transfer();
        com_ecc_11_transfer.setType_tax(DEFAULT_TYPE_TAX);
        com_ecc_11_transfer.setRate(DEFAULT_RATE);
        com_ecc_11_transfer.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createCom_ecc_11_transfer() throws Exception {
        int databaseSizeBeforeCreate = com_ecc_11_transferRepository.findAll().size();

        // Create the Com_ecc_11_transfer

        restCom_ecc_11_transferMockMvc.perform(post("/api/com-ecc-11-transfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_transfer)))
                .andExpect(status().isCreated());

        // Validate the Com_ecc_11_transfer in the database
        List<Com_ecc_11_transfer> com_ecc_11_transfers = com_ecc_11_transferRepository.findAll();
        assertThat(com_ecc_11_transfers).hasSize(databaseSizeBeforeCreate + 1);
        Com_ecc_11_transfer testCom_ecc_11_transfer = com_ecc_11_transfers.get(com_ecc_11_transfers.size() - 1);
        assertThat(testCom_ecc_11_transfer.getType_tax()).isEqualTo(DEFAULT_TYPE_TAX);
        assertThat(testCom_ecc_11_transfer.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testCom_ecc_11_transfer.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkType_taxIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_transferRepository.findAll().size();
        // set the field null
        com_ecc_11_transfer.setType_tax(null);

        // Create the Com_ecc_11_transfer, which fails.

        restCom_ecc_11_transferMockMvc.perform(post("/api/com-ecc-11-transfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_transfer)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_transfer> com_ecc_11_transfers = com_ecc_11_transferRepository.findAll();
        assertThat(com_ecc_11_transfers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_transferRepository.findAll().size();
        // set the field null
        com_ecc_11_transfer.setRate(null);

        // Create the Com_ecc_11_transfer, which fails.

        restCom_ecc_11_transferMockMvc.perform(post("/api/com-ecc-11-transfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_transfer)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_transfer> com_ecc_11_transfers = com_ecc_11_transferRepository.findAll();
        assertThat(com_ecc_11_transfers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11_transferRepository.findAll().size();
        // set the field null
        com_ecc_11_transfer.setAmount(null);

        // Create the Com_ecc_11_transfer, which fails.

        restCom_ecc_11_transferMockMvc.perform(post("/api/com-ecc-11-transfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11_transfer)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11_transfer> com_ecc_11_transfers = com_ecc_11_transferRepository.findAll();
        assertThat(com_ecc_11_transfers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_ecc_11_transfers() throws Exception {
        // Initialize the database
        com_ecc_11_transferRepository.saveAndFlush(com_ecc_11_transfer);

        // Get all the com_ecc_11_transfers
        restCom_ecc_11_transferMockMvc.perform(get("/api/com-ecc-11-transfers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_ecc_11_transfer.getId().intValue())))
                .andExpect(jsonPath("$.[*].type_tax").value(hasItem(DEFAULT_TYPE_TAX.toString())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getCom_ecc_11_transfer() throws Exception {
        // Initialize the database
        com_ecc_11_transferRepository.saveAndFlush(com_ecc_11_transfer);

        // Get the com_ecc_11_transfer
        restCom_ecc_11_transferMockMvc.perform(get("/api/com-ecc-11-transfers/{id}", com_ecc_11_transfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_ecc_11_transfer.getId().intValue()))
            .andExpect(jsonPath("$.type_tax").value(DEFAULT_TYPE_TAX.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_ecc_11_transfer() throws Exception {
        // Get the com_ecc_11_transfer
        restCom_ecc_11_transferMockMvc.perform(get("/api/com-ecc-11-transfers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_ecc_11_transfer() throws Exception {
        // Initialize the database
        com_ecc_11_transferService.save(com_ecc_11_transfer);

        int databaseSizeBeforeUpdate = com_ecc_11_transferRepository.findAll().size();

        // Update the com_ecc_11_transfer
        Com_ecc_11_transfer updatedCom_ecc_11_transfer = new Com_ecc_11_transfer();
        updatedCom_ecc_11_transfer.setId(com_ecc_11_transfer.getId());
        updatedCom_ecc_11_transfer.setType_tax(UPDATED_TYPE_TAX);
        updatedCom_ecc_11_transfer.setRate(UPDATED_RATE);
        updatedCom_ecc_11_transfer.setAmount(UPDATED_AMOUNT);

        restCom_ecc_11_transferMockMvc.perform(put("/api/com-ecc-11-transfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_ecc_11_transfer)))
                .andExpect(status().isOk());

        // Validate the Com_ecc_11_transfer in the database
        List<Com_ecc_11_transfer> com_ecc_11_transfers = com_ecc_11_transferRepository.findAll();
        assertThat(com_ecc_11_transfers).hasSize(databaseSizeBeforeUpdate);
        Com_ecc_11_transfer testCom_ecc_11_transfer = com_ecc_11_transfers.get(com_ecc_11_transfers.size() - 1);
        assertThat(testCom_ecc_11_transfer.getType_tax()).isEqualTo(UPDATED_TYPE_TAX);
        assertThat(testCom_ecc_11_transfer.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testCom_ecc_11_transfer.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteCom_ecc_11_transfer() throws Exception {
        // Initialize the database
        com_ecc_11_transferService.save(com_ecc_11_transfer);

        int databaseSizeBeforeDelete = com_ecc_11_transferRepository.findAll().size();

        // Get the com_ecc_11_transfer
        restCom_ecc_11_transferMockMvc.perform(delete("/api/com-ecc-11-transfers/{id}", com_ecc_11_transfer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_ecc_11_transfer> com_ecc_11_transfers = com_ecc_11_transferRepository.findAll();
        assertThat(com_ecc_11_transfers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
