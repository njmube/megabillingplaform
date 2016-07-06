package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_tax_transfered;
import org.megapractical.billingplatform.repository.Free_tax_transferedRepository;
import org.megapractical.billingplatform.service.Free_tax_transferedService;

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
 * Test class for the Free_tax_transferedResource REST controller.
 *
 * @see Free_tax_transferedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_tax_transferedResourceIntTest {


    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Free_tax_transferedRepository free_tax_transferedRepository;

    @Inject
    private Free_tax_transferedService free_tax_transferedService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_tax_transferedMockMvc;

    private Free_tax_transfered free_tax_transfered;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_tax_transferedResource free_tax_transferedResource = new Free_tax_transferedResource();
        ReflectionTestUtils.setField(free_tax_transferedResource, "free_tax_transferedService", free_tax_transferedService);
        this.restFree_tax_transferedMockMvc = MockMvcBuilders.standaloneSetup(free_tax_transferedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_tax_transfered = new Free_tax_transfered();
        free_tax_transfered.setRate(DEFAULT_RATE);
        free_tax_transfered.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createFree_tax_transfered() throws Exception {
        int databaseSizeBeforeCreate = free_tax_transferedRepository.findAll().size();

        // Create the Free_tax_transfered

        restFree_tax_transferedMockMvc.perform(post("/api/free-tax-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_tax_transfered)))
                .andExpect(status().isCreated());

        // Validate the Free_tax_transfered in the database
        List<Free_tax_transfered> free_tax_transfereds = free_tax_transferedRepository.findAll();
        assertThat(free_tax_transfereds).hasSize(databaseSizeBeforeCreate + 1);
        Free_tax_transfered testFree_tax_transfered = free_tax_transfereds.get(free_tax_transfereds.size() - 1);
        assertThat(testFree_tax_transfered.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testFree_tax_transfered.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_tax_transferedRepository.findAll().size();
        // set the field null
        free_tax_transfered.setAmount(null);

        // Create the Free_tax_transfered, which fails.

        restFree_tax_transferedMockMvc.perform(post("/api/free-tax-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_tax_transfered)))
                .andExpect(status().isBadRequest());

        List<Free_tax_transfered> free_tax_transfereds = free_tax_transferedRepository.findAll();
        assertThat(free_tax_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFree_tax_transfereds() throws Exception {
        // Initialize the database
        free_tax_transferedRepository.saveAndFlush(free_tax_transfered);

        // Get all the free_tax_transfereds
        restFree_tax_transferedMockMvc.perform(get("/api/free-tax-transfereds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_tax_transfered.getId().intValue())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getFree_tax_transfered() throws Exception {
        // Initialize the database
        free_tax_transferedRepository.saveAndFlush(free_tax_transfered);

        // Get the free_tax_transfered
        restFree_tax_transferedMockMvc.perform(get("/api/free-tax-transfereds/{id}", free_tax_transfered.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_tax_transfered.getId().intValue()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFree_tax_transfered() throws Exception {
        // Get the free_tax_transfered
        restFree_tax_transferedMockMvc.perform(get("/api/free-tax-transfereds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFree_tax_transfered() throws Exception {
        // Initialize the database
        free_tax_transferedService.save(free_tax_transfered);

        int databaseSizeBeforeUpdate = free_tax_transferedRepository.findAll().size();

        // Update the free_tax_transfered
        Free_tax_transfered updatedFree_tax_transfered = new Free_tax_transfered();
        updatedFree_tax_transfered.setId(free_tax_transfered.getId());
        updatedFree_tax_transfered.setRate(UPDATED_RATE);
        updatedFree_tax_transfered.setAmount(UPDATED_AMOUNT);

        restFree_tax_transferedMockMvc.perform(put("/api/free-tax-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_tax_transfered)))
                .andExpect(status().isOk());

        // Validate the Free_tax_transfered in the database
        List<Free_tax_transfered> free_tax_transfereds = free_tax_transferedRepository.findAll();
        assertThat(free_tax_transfereds).hasSize(databaseSizeBeforeUpdate);
        Free_tax_transfered testFree_tax_transfered = free_tax_transfereds.get(free_tax_transfereds.size() - 1);
        assertThat(testFree_tax_transfered.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testFree_tax_transfered.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteFree_tax_transfered() throws Exception {
        // Initialize the database
        free_tax_transferedService.save(free_tax_transfered);

        int databaseSizeBeforeDelete = free_tax_transferedRepository.findAll().size();

        // Get the free_tax_transfered
        restFree_tax_transferedMockMvc.perform(delete("/api/free-tax-transfereds/{id}", free_tax_transfered.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_tax_transfered> free_tax_transfereds = free_tax_transferedRepository.findAll();
        assertThat(free_tax_transfereds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
