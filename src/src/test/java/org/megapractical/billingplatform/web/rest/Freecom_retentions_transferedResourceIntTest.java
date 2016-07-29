package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_retentions_transfered;
import org.megapractical.billingplatform.repository.Freecom_retentions_transferedRepository;
import org.megapractical.billingplatform.service.Freecom_retentions_transferedService;

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
 * Test class for the Freecom_retentions_transferedResource REST controller.
 *
 * @see Freecom_retentions_transferedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_retentions_transferedResourceIntTest {

    private static final String DEFAULT_IMPLOCRETENTIONS = "AAAAA";
    private static final String UPDATED_IMPLOCRETENTIONS = "BBBBB";

    private static final BigDecimal DEFAULT_RETENTIONRATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RETENTIONRATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNTRETENTIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNTRETENTIONS = new BigDecimal(2);
    private static final String DEFAULT_IMPLOCTRANSFERED = "AAAAA";
    private static final String UPDATED_IMPLOCTRANSFERED = "BBBBB";

    private static final BigDecimal DEFAULT_TRANSFEREDRATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSFEREDRATE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNTTRANSFERED = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNTTRANSFERED = new BigDecimal(2);

    @Inject
    private Freecom_retentions_transferedRepository freecom_retentions_transferedRepository;

    @Inject
    private Freecom_retentions_transferedService freecom_retentions_transferedService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_retentions_transferedMockMvc;

    private Freecom_retentions_transfered freecom_retentions_transfered;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_retentions_transferedResource freecom_retentions_transferedResource = new Freecom_retentions_transferedResource();
        ReflectionTestUtils.setField(freecom_retentions_transferedResource, "freecom_retentions_transferedService", freecom_retentions_transferedService);
        this.restFreecom_retentions_transferedMockMvc = MockMvcBuilders.standaloneSetup(freecom_retentions_transferedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_retentions_transfered = new Freecom_retentions_transfered();
        freecom_retentions_transfered.setImplocretentions(DEFAULT_IMPLOCRETENTIONS);
        freecom_retentions_transfered.setRetentionrate(DEFAULT_RETENTIONRATE);
        freecom_retentions_transfered.setAmountretentions(DEFAULT_AMOUNTRETENTIONS);
        freecom_retentions_transfered.setImploctransfered(DEFAULT_IMPLOCTRANSFERED);
        freecom_retentions_transfered.setTransferedrate(DEFAULT_TRANSFEREDRATE);
        freecom_retentions_transfered.setAmounttransfered(DEFAULT_AMOUNTTRANSFERED);
    }

    @Test
    @Transactional
    public void createFreecom_retentions_transfered() throws Exception {
        int databaseSizeBeforeCreate = freecom_retentions_transferedRepository.findAll().size();

        // Create the Freecom_retentions_transfered

        restFreecom_retentions_transferedMockMvc.perform(post("/api/freecom-retentions-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_retentions_transfered)))
                .andExpect(status().isCreated());

        // Validate the Freecom_retentions_transfered in the database
        List<Freecom_retentions_transfered> freecom_retentions_transfereds = freecom_retentions_transferedRepository.findAll();
        assertThat(freecom_retentions_transfereds).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_retentions_transfered testFreecom_retentions_transfered = freecom_retentions_transfereds.get(freecom_retentions_transfereds.size() - 1);
        assertThat(testFreecom_retentions_transfered.getImplocretentions()).isEqualTo(DEFAULT_IMPLOCRETENTIONS);
        assertThat(testFreecom_retentions_transfered.getRetentionrate()).isEqualTo(DEFAULT_RETENTIONRATE);
        assertThat(testFreecom_retentions_transfered.getAmountretentions()).isEqualTo(DEFAULT_AMOUNTRETENTIONS);
        assertThat(testFreecom_retentions_transfered.getImploctransfered()).isEqualTo(DEFAULT_IMPLOCTRANSFERED);
        assertThat(testFreecom_retentions_transfered.getTransferedrate()).isEqualTo(DEFAULT_TRANSFEREDRATE);
        assertThat(testFreecom_retentions_transfered.getAmounttransfered()).isEqualTo(DEFAULT_AMOUNTTRANSFERED);
    }

    @Test
    @Transactional
    public void checkImplocretentionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_retentions_transferedRepository.findAll().size();
        // set the field null
        freecom_retentions_transfered.setImplocretentions(null);

        // Create the Freecom_retentions_transfered, which fails.

        restFreecom_retentions_transferedMockMvc.perform(post("/api/freecom-retentions-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_retentions_transfered)))
                .andExpect(status().isBadRequest());

        List<Freecom_retentions_transfered> freecom_retentions_transfereds = freecom_retentions_transferedRepository.findAll();
        assertThat(freecom_retentions_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRetentionrateIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_retentions_transferedRepository.findAll().size();
        // set the field null
        freecom_retentions_transfered.setRetentionrate(null);

        // Create the Freecom_retentions_transfered, which fails.

        restFreecom_retentions_transferedMockMvc.perform(post("/api/freecom-retentions-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_retentions_transfered)))
                .andExpect(status().isBadRequest());

        List<Freecom_retentions_transfered> freecom_retentions_transfereds = freecom_retentions_transferedRepository.findAll();
        assertThat(freecom_retentions_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountretentionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_retentions_transferedRepository.findAll().size();
        // set the field null
        freecom_retentions_transfered.setAmountretentions(null);

        // Create the Freecom_retentions_transfered, which fails.

        restFreecom_retentions_transferedMockMvc.perform(post("/api/freecom-retentions-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_retentions_transfered)))
                .andExpect(status().isBadRequest());

        List<Freecom_retentions_transfered> freecom_retentions_transfereds = freecom_retentions_transferedRepository.findAll();
        assertThat(freecom_retentions_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImploctransferedIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_retentions_transferedRepository.findAll().size();
        // set the field null
        freecom_retentions_transfered.setImploctransfered(null);

        // Create the Freecom_retentions_transfered, which fails.

        restFreecom_retentions_transferedMockMvc.perform(post("/api/freecom-retentions-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_retentions_transfered)))
                .andExpect(status().isBadRequest());

        List<Freecom_retentions_transfered> freecom_retentions_transfereds = freecom_retentions_transferedRepository.findAll();
        assertThat(freecom_retentions_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransferedrateIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_retentions_transferedRepository.findAll().size();
        // set the field null
        freecom_retentions_transfered.setTransferedrate(null);

        // Create the Freecom_retentions_transfered, which fails.

        restFreecom_retentions_transferedMockMvc.perform(post("/api/freecom-retentions-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_retentions_transfered)))
                .andExpect(status().isBadRequest());

        List<Freecom_retentions_transfered> freecom_retentions_transfereds = freecom_retentions_transferedRepository.findAll();
        assertThat(freecom_retentions_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmounttransferedIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_retentions_transferedRepository.findAll().size();
        // set the field null
        freecom_retentions_transfered.setAmounttransfered(null);

        // Create the Freecom_retentions_transfered, which fails.

        restFreecom_retentions_transferedMockMvc.perform(post("/api/freecom-retentions-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_retentions_transfered)))
                .andExpect(status().isBadRequest());

        List<Freecom_retentions_transfered> freecom_retentions_transfereds = freecom_retentions_transferedRepository.findAll();
        assertThat(freecom_retentions_transfereds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_retentions_transfereds() throws Exception {
        // Initialize the database
        freecom_retentions_transferedRepository.saveAndFlush(freecom_retentions_transfered);

        // Get all the freecom_retentions_transfereds
        restFreecom_retentions_transferedMockMvc.perform(get("/api/freecom-retentions-transfereds?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_retentions_transfered.getId().intValue())))
                .andExpect(jsonPath("$.[*].implocretentions").value(hasItem(DEFAULT_IMPLOCRETENTIONS.toString())))
                .andExpect(jsonPath("$.[*].retentionrate").value(hasItem(DEFAULT_RETENTIONRATE.intValue())))
                .andExpect(jsonPath("$.[*].amountretentions").value(hasItem(DEFAULT_AMOUNTRETENTIONS.intValue())))
                .andExpect(jsonPath("$.[*].imploctransfered").value(hasItem(DEFAULT_IMPLOCTRANSFERED.toString())))
                .andExpect(jsonPath("$.[*].transferedrate").value(hasItem(DEFAULT_TRANSFEREDRATE.intValue())))
                .andExpect(jsonPath("$.[*].amounttransfered").value(hasItem(DEFAULT_AMOUNTTRANSFERED.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_retentions_transfered() throws Exception {
        // Initialize the database
        freecom_retentions_transferedRepository.saveAndFlush(freecom_retentions_transfered);

        // Get the freecom_retentions_transfered
        restFreecom_retentions_transferedMockMvc.perform(get("/api/freecom-retentions-transfereds/{id}", freecom_retentions_transfered.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_retentions_transfered.getId().intValue()))
            .andExpect(jsonPath("$.implocretentions").value(DEFAULT_IMPLOCRETENTIONS.toString()))
            .andExpect(jsonPath("$.retentionrate").value(DEFAULT_RETENTIONRATE.intValue()))
            .andExpect(jsonPath("$.amountretentions").value(DEFAULT_AMOUNTRETENTIONS.intValue()))
            .andExpect(jsonPath("$.imploctransfered").value(DEFAULT_IMPLOCTRANSFERED.toString()))
            .andExpect(jsonPath("$.transferedrate").value(DEFAULT_TRANSFEREDRATE.intValue()))
            .andExpect(jsonPath("$.amounttransfered").value(DEFAULT_AMOUNTTRANSFERED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_retentions_transfered() throws Exception {
        // Get the freecom_retentions_transfered
        restFreecom_retentions_transferedMockMvc.perform(get("/api/freecom-retentions-transfereds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_retentions_transfered() throws Exception {
        // Initialize the database
        freecom_retentions_transferedService.save(freecom_retentions_transfered);

        int databaseSizeBeforeUpdate = freecom_retentions_transferedRepository.findAll().size();

        // Update the freecom_retentions_transfered
        Freecom_retentions_transfered updatedFreecom_retentions_transfered = new Freecom_retentions_transfered();
        updatedFreecom_retentions_transfered.setId(freecom_retentions_transfered.getId());
        updatedFreecom_retentions_transfered.setImplocretentions(UPDATED_IMPLOCRETENTIONS);
        updatedFreecom_retentions_transfered.setRetentionrate(UPDATED_RETENTIONRATE);
        updatedFreecom_retentions_transfered.setAmountretentions(UPDATED_AMOUNTRETENTIONS);
        updatedFreecom_retentions_transfered.setImploctransfered(UPDATED_IMPLOCTRANSFERED);
        updatedFreecom_retentions_transfered.setTransferedrate(UPDATED_TRANSFEREDRATE);
        updatedFreecom_retentions_transfered.setAmounttransfered(UPDATED_AMOUNTTRANSFERED);

        restFreecom_retentions_transferedMockMvc.perform(put("/api/freecom-retentions-transfereds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_retentions_transfered)))
                .andExpect(status().isOk());

        // Validate the Freecom_retentions_transfered in the database
        List<Freecom_retentions_transfered> freecom_retentions_transfereds = freecom_retentions_transferedRepository.findAll();
        assertThat(freecom_retentions_transfereds).hasSize(databaseSizeBeforeUpdate);
        Freecom_retentions_transfered testFreecom_retentions_transfered = freecom_retentions_transfereds.get(freecom_retentions_transfereds.size() - 1);
        assertThat(testFreecom_retentions_transfered.getImplocretentions()).isEqualTo(UPDATED_IMPLOCRETENTIONS);
        assertThat(testFreecom_retentions_transfered.getRetentionrate()).isEqualTo(UPDATED_RETENTIONRATE);
        assertThat(testFreecom_retentions_transfered.getAmountretentions()).isEqualTo(UPDATED_AMOUNTRETENTIONS);
        assertThat(testFreecom_retentions_transfered.getImploctransfered()).isEqualTo(UPDATED_IMPLOCTRANSFERED);
        assertThat(testFreecom_retentions_transfered.getTransferedrate()).isEqualTo(UPDATED_TRANSFEREDRATE);
        assertThat(testFreecom_retentions_transfered.getAmounttransfered()).isEqualTo(UPDATED_AMOUNTTRANSFERED);
    }

    @Test
    @Transactional
    public void deleteFreecom_retentions_transfered() throws Exception {
        // Initialize the database
        freecom_retentions_transferedService.save(freecom_retentions_transfered);

        int databaseSizeBeforeDelete = freecom_retentions_transferedRepository.findAll().size();

        // Get the freecom_retentions_transfered
        restFreecom_retentions_transferedMockMvc.perform(delete("/api/freecom-retentions-transfereds/{id}", freecom_retentions_transfered.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_retentions_transfered> freecom_retentions_transfereds = freecom_retentions_transferedRepository.findAll();
        assertThat(freecom_retentions_transfereds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
