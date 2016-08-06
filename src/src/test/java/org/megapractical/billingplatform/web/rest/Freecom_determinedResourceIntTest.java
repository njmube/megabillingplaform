package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_determined;
import org.megapractical.billingplatform.repository.Freecom_determinedRepository;
import org.megapractical.billingplatform.service.Freecom_determinedService;

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
 * Test class for the Freecom_determinedResource REST controller.
 *
 * @see Freecom_determinedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_determinedResourceIntTest {


    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Freecom_determinedRepository freecom_determinedRepository;

    @Inject
    private Freecom_determinedService freecom_determinedService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_determinedMockMvc;

    private Freecom_determined freecom_determined;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_determinedResource freecom_determinedResource = new Freecom_determinedResource();
        ReflectionTestUtils.setField(freecom_determinedResource, "freecom_determinedService", freecom_determinedService);
        this.restFreecom_determinedMockMvc = MockMvcBuilders.standaloneSetup(freecom_determinedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_determined = new Freecom_determined();
        freecom_determined.setRate(DEFAULT_RATE);
        freecom_determined.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createFreecom_determined() throws Exception {
        int databaseSizeBeforeCreate = freecom_determinedRepository.findAll().size();

        // Create the Freecom_determined

        restFreecom_determinedMockMvc.perform(post("/api/freecom-determineds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_determined)))
                .andExpect(status().isCreated());

        // Validate the Freecom_determined in the database
        List<Freecom_determined> freecom_determineds = freecom_determinedRepository.findAll();
        assertThat(freecom_determineds).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_determined testFreecom_determined = freecom_determineds.get(freecom_determineds.size() - 1);
        assertThat(testFreecom_determined.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testFreecom_determined.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_determinedRepository.findAll().size();
        // set the field null
        freecom_determined.setRate(null);

        // Create the Freecom_determined, which fails.

        restFreecom_determinedMockMvc.perform(post("/api/freecom-determineds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_determined)))
                .andExpect(status().isBadRequest());

        List<Freecom_determined> freecom_determineds = freecom_determinedRepository.findAll();
        assertThat(freecom_determineds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_determinedRepository.findAll().size();
        // set the field null
        freecom_determined.setAmount(null);

        // Create the Freecom_determined, which fails.

        restFreecom_determinedMockMvc.perform(post("/api/freecom-determineds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_determined)))
                .andExpect(status().isBadRequest());

        List<Freecom_determined> freecom_determineds = freecom_determinedRepository.findAll();
        assertThat(freecom_determineds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_determineds() throws Exception {
        // Initialize the database
        freecom_determinedRepository.saveAndFlush(freecom_determined);

        // Get all the freecom_determineds
        restFreecom_determinedMockMvc.perform(get("/api/freecom-determineds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_determined.getId().intValue())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_determined() throws Exception {
        // Initialize the database
        freecom_determinedRepository.saveAndFlush(freecom_determined);

        // Get the freecom_determined
        restFreecom_determinedMockMvc.perform(get("/api/freecom-determineds/{id}", freecom_determined.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_determined.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_determined() throws Exception {
        // Get the freecom_determined
        restFreecom_determinedMockMvc.perform(get("/api/freecom-determineds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_determined() throws Exception {
        // Initialize the database
        freecom_determinedService.save(freecom_determined);

        int databaseSizeBeforeUpdate = freecom_determinedRepository.findAll().size();

        // Update the freecom_determined
        Freecom_determined updatedFreecom_determined = new Freecom_determined();
        updatedFreecom_determined.setId(freecom_determined.getId());
        updatedFreecom_determined.setRate(UPDATED_RATE);
        updatedFreecom_determined.setAmount(UPDATED_AMOUNT);

        restFreecom_determinedMockMvc.perform(put("/api/freecom-determineds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_determined)))
                .andExpect(status().isOk());

        // Validate the Freecom_determined in the database
        List<Freecom_determined> freecom_determineds = freecom_determinedRepository.findAll();
        assertThat(freecom_determineds).hasSize(databaseSizeBeforeUpdate);
        Freecom_determined testFreecom_determined = freecom_determineds.get(freecom_determineds.size() - 1);
        assertThat(testFreecom_determined.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testFreecom_determined.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteFreecom_determined() throws Exception {
        // Initialize the database
        freecom_determinedService.save(freecom_determined);

        int databaseSizeBeforeDelete = freecom_determinedRepository.findAll().size();

        // Get the freecom_determined
        restFreecom_determinedMockMvc.perform(delete("/api/freecom-determineds/{id}", freecom_determined.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_determined> freecom_determineds = freecom_determinedRepository.findAll();
        assertThat(freecom_determineds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
