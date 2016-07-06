package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_tax_retentions;
import org.megapractical.billingplatform.repository.Free_tax_retentionsRepository;
import org.megapractical.billingplatform.service.Free_tax_retentionsService;

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
 * Test class for the Free_tax_retentionsResource REST controller.
 *
 * @see Free_tax_retentionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_tax_retentionsResourceIntTest {


    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    @Inject
    private Free_tax_retentionsRepository free_tax_retentionsRepository;

    @Inject
    private Free_tax_retentionsService free_tax_retentionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_tax_retentionsMockMvc;

    private Free_tax_retentions free_tax_retentions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_tax_retentionsResource free_tax_retentionsResource = new Free_tax_retentionsResource();
        ReflectionTestUtils.setField(free_tax_retentionsResource, "free_tax_retentionsService", free_tax_retentionsService);
        this.restFree_tax_retentionsMockMvc = MockMvcBuilders.standaloneSetup(free_tax_retentionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_tax_retentions = new Free_tax_retentions();
        free_tax_retentions.setAmount(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createFree_tax_retentions() throws Exception {
        int databaseSizeBeforeCreate = free_tax_retentionsRepository.findAll().size();

        // Create the Free_tax_retentions

        restFree_tax_retentionsMockMvc.perform(post("/api/free-tax-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_tax_retentions)))
                .andExpect(status().isCreated());

        // Validate the Free_tax_retentions in the database
        List<Free_tax_retentions> free_tax_retentions = free_tax_retentionsRepository.findAll();
        assertThat(free_tax_retentions).hasSize(databaseSizeBeforeCreate + 1);
        Free_tax_retentions testFree_tax_retentions = free_tax_retentions.get(free_tax_retentions.size() - 1);
        assertThat(testFree_tax_retentions.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_tax_retentionsRepository.findAll().size();
        // set the field null
        free_tax_retentions.setAmount(null);

        // Create the Free_tax_retentions, which fails.

        restFree_tax_retentionsMockMvc.perform(post("/api/free-tax-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_tax_retentions)))
                .andExpect(status().isBadRequest());

        List<Free_tax_retentions> free_tax_retentions = free_tax_retentionsRepository.findAll();
        assertThat(free_tax_retentions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFree_tax_retentions() throws Exception {
        // Initialize the database
        free_tax_retentionsRepository.saveAndFlush(free_tax_retentions);

        // Get all the free_tax_retentions
        restFree_tax_retentionsMockMvc.perform(get("/api/free-tax-retentions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_tax_retentions.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getFree_tax_retentions() throws Exception {
        // Initialize the database
        free_tax_retentionsRepository.saveAndFlush(free_tax_retentions);

        // Get the free_tax_retentions
        restFree_tax_retentionsMockMvc.perform(get("/api/free-tax-retentions/{id}", free_tax_retentions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_tax_retentions.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFree_tax_retentions() throws Exception {
        // Get the free_tax_retentions
        restFree_tax_retentionsMockMvc.perform(get("/api/free-tax-retentions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFree_tax_retentions() throws Exception {
        // Initialize the database
        free_tax_retentionsService.save(free_tax_retentions);

        int databaseSizeBeforeUpdate = free_tax_retentionsRepository.findAll().size();

        // Update the free_tax_retentions
        Free_tax_retentions updatedFree_tax_retentions = new Free_tax_retentions();
        updatedFree_tax_retentions.setId(free_tax_retentions.getId());
        updatedFree_tax_retentions.setAmount(UPDATED_AMOUNT);

        restFree_tax_retentionsMockMvc.perform(put("/api/free-tax-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_tax_retentions)))
                .andExpect(status().isOk());

        // Validate the Free_tax_retentions in the database
        List<Free_tax_retentions> free_tax_retentions = free_tax_retentionsRepository.findAll();
        assertThat(free_tax_retentions).hasSize(databaseSizeBeforeUpdate);
        Free_tax_retentions testFree_tax_retentions = free_tax_retentions.get(free_tax_retentions.size() - 1);
        assertThat(testFree_tax_retentions.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void deleteFree_tax_retentions() throws Exception {
        // Initialize the database
        free_tax_retentionsService.save(free_tax_retentions);

        int databaseSizeBeforeDelete = free_tax_retentionsRepository.findAll().size();

        // Get the free_tax_retentions
        restFree_tax_retentionsMockMvc.perform(delete("/api/free-tax-retentions/{id}", free_tax_retentions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_tax_retentions> free_tax_retentions = free_tax_retentionsRepository.findAll();
        assertThat(free_tax_retentions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
