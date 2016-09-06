package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_local_retentions;
import org.megapractical.billingplatform.repository.Freecom_local_retentionsRepository;
import org.megapractical.billingplatform.service.Freecom_local_retentionsService;

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
 * Test class for the Freecom_local_retentionsResource REST controller.
 *
 * @see Freecom_local_retentionsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_local_retentionsResourceIntTest {

    private static final String DEFAULT_IMPLOCRETENTIONS = "AAAAA";
    private static final String UPDATED_IMPLOCRETENTIONS = "BBBBB";

    private static final BigDecimal DEFAULT_RETENTIONRATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RETENTIONRATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNTRETENTIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNTRETENTIONS = new BigDecimal(2);

    @Inject
    private Freecom_local_retentionsRepository freecom_local_retentionsRepository;

    @Inject
    private Freecom_local_retentionsService freecom_local_retentionsService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_local_retentionsMockMvc;

    private Freecom_local_retentions freecom_local_retentions;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_local_retentionsResource freecom_local_retentionsResource = new Freecom_local_retentionsResource();
        ReflectionTestUtils.setField(freecom_local_retentionsResource, "freecom_local_retentionsService", freecom_local_retentionsService);
        this.restFreecom_local_retentionsMockMvc = MockMvcBuilders.standaloneSetup(freecom_local_retentionsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_local_retentions = new Freecom_local_retentions();
        freecom_local_retentions.setImplocretentions(DEFAULT_IMPLOCRETENTIONS);
        freecom_local_retentions.setRetentionrate(DEFAULT_RETENTIONRATE);
        freecom_local_retentions.setAmountretentions(DEFAULT_AMOUNTRETENTIONS);
    }

    @Test
    @Transactional
    public void createFreecom_local_retentions() throws Exception {
        int databaseSizeBeforeCreate = freecom_local_retentionsRepository.findAll().size();

        // Create the Freecom_local_retentions

        restFreecom_local_retentionsMockMvc.perform(post("/api/freecom-local-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_retentions)))
                .andExpect(status().isCreated());

        // Validate the Freecom_local_retentions in the database
        List<Freecom_local_retentions> freecom_local_retentions = freecom_local_retentionsRepository.findAll();
        assertThat(freecom_local_retentions).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_local_retentions testFreecom_local_retentions = freecom_local_retentions.get(freecom_local_retentions.size() - 1);
        assertThat(testFreecom_local_retentions.getImplocretentions()).isEqualTo(DEFAULT_IMPLOCRETENTIONS);
        assertThat(testFreecom_local_retentions.getRetentionrate()).isEqualTo(DEFAULT_RETENTIONRATE);
        assertThat(testFreecom_local_retentions.getAmountretentions()).isEqualTo(DEFAULT_AMOUNTRETENTIONS);
    }

    @Test
    @Transactional
    public void checkImplocretentionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_local_retentionsRepository.findAll().size();
        // set the field null
        freecom_local_retentions.setImplocretentions(null);

        // Create the Freecom_local_retentions, which fails.

        restFreecom_local_retentionsMockMvc.perform(post("/api/freecom-local-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_retentions)))
                .andExpect(status().isBadRequest());

        List<Freecom_local_retentions> freecom_local_retentions = freecom_local_retentionsRepository.findAll();
        assertThat(freecom_local_retentions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetentionrateIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_local_retentionsRepository.findAll().size();
        // set the field null
        freecom_local_retentions.setRetentionrate(null);

        // Create the Freecom_local_retentions, which fails.

        restFreecom_local_retentionsMockMvc.perform(post("/api/freecom-local-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_retentions)))
                .andExpect(status().isBadRequest());

        List<Freecom_local_retentions> freecom_local_retentions = freecom_local_retentionsRepository.findAll();
        assertThat(freecom_local_retentions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountretentionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_local_retentionsRepository.findAll().size();
        // set the field null
        freecom_local_retentions.setAmountretentions(null);

        // Create the Freecom_local_retentions, which fails.

        restFreecom_local_retentionsMockMvc.perform(post("/api/freecom-local-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_retentions)))
                .andExpect(status().isBadRequest());

        List<Freecom_local_retentions> freecom_local_retentions = freecom_local_retentionsRepository.findAll();
        assertThat(freecom_local_retentions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_local_retentions() throws Exception {
        // Initialize the database
        freecom_local_retentionsRepository.saveAndFlush(freecom_local_retentions);

        // Get all the freecom_local_retentions
        restFreecom_local_retentionsMockMvc.perform(get("/api/freecom-local-retentions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_local_retentions.getId().intValue())))
                .andExpect(jsonPath("$.[*].implocretentions").value(hasItem(DEFAULT_IMPLOCRETENTIONS.toString())))
                .andExpect(jsonPath("$.[*].retentionrate").value(hasItem(DEFAULT_RETENTIONRATE.intValue())))
                .andExpect(jsonPath("$.[*].amountretentions").value(hasItem(DEFAULT_AMOUNTRETENTIONS.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_local_retentions() throws Exception {
        // Initialize the database
        freecom_local_retentionsRepository.saveAndFlush(freecom_local_retentions);

        // Get the freecom_local_retentions
        restFreecom_local_retentionsMockMvc.perform(get("/api/freecom-local-retentions/{id}", freecom_local_retentions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_local_retentions.getId().intValue()))
            .andExpect(jsonPath("$.implocretentions").value(DEFAULT_IMPLOCRETENTIONS.toString()))
            .andExpect(jsonPath("$.retentionrate").value(DEFAULT_RETENTIONRATE.intValue()))
            .andExpect(jsonPath("$.amountretentions").value(DEFAULT_AMOUNTRETENTIONS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_local_retentions() throws Exception {
        // Get the freecom_local_retentions
        restFreecom_local_retentionsMockMvc.perform(get("/api/freecom-local-retentions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_local_retentions() throws Exception {
        // Initialize the database
        freecom_local_retentionsService.save(freecom_local_retentions);

        int databaseSizeBeforeUpdate = freecom_local_retentionsRepository.findAll().size();

        // Update the freecom_local_retentions
        Freecom_local_retentions updatedFreecom_local_retentions = new Freecom_local_retentions();
        updatedFreecom_local_retentions.setId(freecom_local_retentions.getId());
        updatedFreecom_local_retentions.setImplocretentions(UPDATED_IMPLOCRETENTIONS);
        updatedFreecom_local_retentions.setRetentionrate(UPDATED_RETENTIONRATE);
        updatedFreecom_local_retentions.setAmountretentions(UPDATED_AMOUNTRETENTIONS);

        restFreecom_local_retentionsMockMvc.perform(put("/api/freecom-local-retentions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_local_retentions)))
                .andExpect(status().isOk());

        // Validate the Freecom_local_retentions in the database
        List<Freecom_local_retentions> freecom_local_retentions = freecom_local_retentionsRepository.findAll();
        assertThat(freecom_local_retentions).hasSize(databaseSizeBeforeUpdate);
        Freecom_local_retentions testFreecom_local_retentions = freecom_local_retentions.get(freecom_local_retentions.size() - 1);
        assertThat(testFreecom_local_retentions.getImplocretentions()).isEqualTo(UPDATED_IMPLOCRETENTIONS);
        assertThat(testFreecom_local_retentions.getRetentionrate()).isEqualTo(UPDATED_RETENTIONRATE);
        assertThat(testFreecom_local_retentions.getAmountretentions()).isEqualTo(UPDATED_AMOUNTRETENTIONS);
    }

    @Test
    @Transactional
    public void deleteFreecom_local_retentions() throws Exception {
        // Initialize the database
        freecom_local_retentionsService.save(freecom_local_retentions);

        int databaseSizeBeforeDelete = freecom_local_retentionsRepository.findAll().size();

        // Get the freecom_local_retentions
        restFreecom_local_retentionsMockMvc.perform(delete("/api/freecom-local-retentions/{id}", freecom_local_retentions.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_local_retentions> freecom_local_retentions = freecom_local_retentionsRepository.findAll();
        assertThat(freecom_local_retentions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
