package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_ecc11_transfer;
import org.megapractical.billingplatform.repository.Freecom_ecc11_transferRepository;
import org.megapractical.billingplatform.service.Freecom_ecc11_transferService;

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
 * Test class for the Freecom_ecc11_transferResource REST controller.
 *
 * @see Freecom_ecc11_transferResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_ecc11_transferResourceIntTest {

    private static final String DEFAULT_TYPE_TAX = "AAAA";
    private static final String UPDATED_TYPE_TAX = "BBBB";

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Freecom_ecc11_transferRepository freecom_ecc11_transferRepository;

    @Inject
    private Freecom_ecc11_transferService freecom_ecc11_transferService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_ecc11_transferMockMvc;

    private Freecom_ecc11_transfer freecom_ecc11_transfer;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_ecc11_transferResource freecom_ecc11_transferResource = new Freecom_ecc11_transferResource();
        ReflectionTestUtils.setField(freecom_ecc11_transferResource, "freecom_ecc11_transferService", freecom_ecc11_transferService);
        this.restFreecom_ecc11_transferMockMvc = MockMvcBuilders.standaloneSetup(freecom_ecc11_transferResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_ecc11_transfer = new Freecom_ecc11_transfer();
        freecom_ecc11_transfer.setType_tax(DEFAULT_TYPE_TAX);
        freecom_ecc11_transfer.setRate(DEFAULT_RATE);
        freecom_ecc11_transfer.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createFreecom_ecc11_transfer() throws Exception {
        int databaseSizeBeforeCreate = freecom_ecc11_transferRepository.findAll().size();

        // Create the Freecom_ecc11_transfer

        restFreecom_ecc11_transferMockMvc.perform(post("/api/freecom-ecc-11-transfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_transfer)))
                .andExpect(status().isCreated());

        // Validate the Freecom_ecc11_transfer in the database
        List<Freecom_ecc11_transfer> freecom_ecc11_transfers = freecom_ecc11_transferRepository.findAll();
        assertThat(freecom_ecc11_transfers).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_ecc11_transfer testFreecom_ecc11_transfer = freecom_ecc11_transfers.get(freecom_ecc11_transfers.size() - 1);
        assertThat(testFreecom_ecc11_transfer.getType_tax()).isEqualTo(DEFAULT_TYPE_TAX);
        assertThat(testFreecom_ecc11_transfer.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testFreecom_ecc11_transfer.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkType_taxIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_transferRepository.findAll().size();
        // set the field null
        freecom_ecc11_transfer.setType_tax(null);

        // Create the Freecom_ecc11_transfer, which fails.

        restFreecom_ecc11_transferMockMvc.perform(post("/api/freecom-ecc-11-transfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_transfer)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_transfer> freecom_ecc11_transfers = freecom_ecc11_transferRepository.findAll();
        assertThat(freecom_ecc11_transfers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_transferRepository.findAll().size();
        // set the field null
        freecom_ecc11_transfer.setRate(null);

        // Create the Freecom_ecc11_transfer, which fails.

        restFreecom_ecc11_transferMockMvc.perform(post("/api/freecom-ecc-11-transfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_transfer)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_transfer> freecom_ecc11_transfers = freecom_ecc11_transferRepository.findAll();
        assertThat(freecom_ecc11_transfers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11_transferRepository.findAll().size();
        // set the field null
        freecom_ecc11_transfer.setAmount(null);

        // Create the Freecom_ecc11_transfer, which fails.

        restFreecom_ecc11_transferMockMvc.perform(post("/api/freecom-ecc-11-transfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11_transfer)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11_transfer> freecom_ecc11_transfers = freecom_ecc11_transferRepository.findAll();
        assertThat(freecom_ecc11_transfers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_ecc11_transfers() throws Exception {
        // Initialize the database
        freecom_ecc11_transferRepository.saveAndFlush(freecom_ecc11_transfer);

        // Get all the freecom_ecc11_transfers
        restFreecom_ecc11_transferMockMvc.perform(get("/api/freecom-ecc-11-transfers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_ecc11_transfer.getId().intValue())))
                .andExpect(jsonPath("$.[*].type_tax").value(hasItem(DEFAULT_TYPE_TAX.toString())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_ecc11_transfer() throws Exception {
        // Initialize the database
        freecom_ecc11_transferRepository.saveAndFlush(freecom_ecc11_transfer);

        // Get the freecom_ecc11_transfer
        restFreecom_ecc11_transferMockMvc.perform(get("/api/freecom-ecc-11-transfers/{id}", freecom_ecc11_transfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_ecc11_transfer.getId().intValue()))
            .andExpect(jsonPath("$.type_tax").value(DEFAULT_TYPE_TAX.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_ecc11_transfer() throws Exception {
        // Get the freecom_ecc11_transfer
        restFreecom_ecc11_transferMockMvc.perform(get("/api/freecom-ecc-11-transfers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_ecc11_transfer() throws Exception {
        // Initialize the database
        freecom_ecc11_transferService.save(freecom_ecc11_transfer);

        int databaseSizeBeforeUpdate = freecom_ecc11_transferRepository.findAll().size();

        // Update the freecom_ecc11_transfer
        Freecom_ecc11_transfer updatedFreecom_ecc11_transfer = new Freecom_ecc11_transfer();
        updatedFreecom_ecc11_transfer.setId(freecom_ecc11_transfer.getId());
        updatedFreecom_ecc11_transfer.setType_tax(UPDATED_TYPE_TAX);
        updatedFreecom_ecc11_transfer.setRate(UPDATED_RATE);
        updatedFreecom_ecc11_transfer.setAmount(UPDATED_AMOUNT);

        restFreecom_ecc11_transferMockMvc.perform(put("/api/freecom-ecc-11-transfers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_ecc11_transfer)))
                .andExpect(status().isOk());

        // Validate the Freecom_ecc11_transfer in the database
        List<Freecom_ecc11_transfer> freecom_ecc11_transfers = freecom_ecc11_transferRepository.findAll();
        assertThat(freecom_ecc11_transfers).hasSize(databaseSizeBeforeUpdate);
        Freecom_ecc11_transfer testFreecom_ecc11_transfer = freecom_ecc11_transfers.get(freecom_ecc11_transfers.size() - 1);
        assertThat(testFreecom_ecc11_transfer.getType_tax()).isEqualTo(UPDATED_TYPE_TAX);
        assertThat(testFreecom_ecc11_transfer.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testFreecom_ecc11_transfer.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteFreecom_ecc11_transfer() throws Exception {
        // Initialize the database
        freecom_ecc11_transferService.save(freecom_ecc11_transfer);

        int databaseSizeBeforeDelete = freecom_ecc11_transferRepository.findAll().size();

        // Get the freecom_ecc11_transfer
        restFreecom_ecc11_transferMockMvc.perform(delete("/api/freecom-ecc-11-transfers/{id}", freecom_ecc11_transfer.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_ecc11_transfer> freecom_ecc11_transfers = freecom_ecc11_transferRepository.findAll();
        assertThat(freecom_ecc11_transfers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
