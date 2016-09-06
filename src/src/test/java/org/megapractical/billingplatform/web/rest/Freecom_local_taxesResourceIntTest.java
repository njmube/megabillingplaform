package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_local_taxes;
import org.megapractical.billingplatform.repository.Freecom_local_taxesRepository;
import org.megapractical.billingplatform.service.Freecom_local_taxesService;

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
 * Test class for the Freecom_local_taxesResource REST controller.
 *
 * @see Freecom_local_taxesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_local_taxesResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";

    private static final BigDecimal DEFAULT_TOTAL_LOCAL_RETENTIONS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_LOCAL_RETENTIONS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_LOCAL_TRANSFERED = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_LOCAL_TRANSFERED = new BigDecimal(2);

    @Inject
    private Freecom_local_taxesRepository freecom_local_taxesRepository;

    @Inject
    private Freecom_local_taxesService freecom_local_taxesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_local_taxesMockMvc;

    private Freecom_local_taxes freecom_local_taxes;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_local_taxesResource freecom_local_taxesResource = new Freecom_local_taxesResource();
        ReflectionTestUtils.setField(freecom_local_taxesResource, "freecom_local_taxesService", freecom_local_taxesService);
        this.restFreecom_local_taxesMockMvc = MockMvcBuilders.standaloneSetup(freecom_local_taxesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_local_taxes = new Freecom_local_taxes();
        freecom_local_taxes.setVersion(DEFAULT_VERSION);
        freecom_local_taxes.setTotal_local_retentions(DEFAULT_TOTAL_LOCAL_RETENTIONS);
        freecom_local_taxes.setTotal_local_transfered(DEFAULT_TOTAL_LOCAL_TRANSFERED);
    }

    @Test
    @Transactional
    public void createFreecom_local_taxes() throws Exception {
        int databaseSizeBeforeCreate = freecom_local_taxesRepository.findAll().size();

        // Create the Freecom_local_taxes

        restFreecom_local_taxesMockMvc.perform(post("/api/freecom-local-taxes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_taxes)))
                .andExpect(status().isCreated());

        // Validate the Freecom_local_taxes in the database
        List<Freecom_local_taxes> freecom_local_taxes = freecom_local_taxesRepository.findAll();
        assertThat(freecom_local_taxes).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_local_taxes testFreecom_local_taxes = freecom_local_taxes.get(freecom_local_taxes.size() - 1);
        assertThat(testFreecom_local_taxes.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_local_taxes.getTotal_local_retentions()).isEqualTo(DEFAULT_TOTAL_LOCAL_RETENTIONS);
        assertThat(testFreecom_local_taxes.getTotal_local_transfered()).isEqualTo(DEFAULT_TOTAL_LOCAL_TRANSFERED);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_local_taxesRepository.findAll().size();
        // set the field null
        freecom_local_taxes.setVersion(null);

        // Create the Freecom_local_taxes, which fails.

        restFreecom_local_taxesMockMvc.perform(post("/api/freecom-local-taxes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_taxes)))
                .andExpect(status().isBadRequest());

        List<Freecom_local_taxes> freecom_local_taxes = freecom_local_taxesRepository.findAll();
        assertThat(freecom_local_taxes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotal_local_retentionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_local_taxesRepository.findAll().size();
        // set the field null
        freecom_local_taxes.setTotal_local_retentions(null);

        // Create the Freecom_local_taxes, which fails.

        restFreecom_local_taxesMockMvc.perform(post("/api/freecom-local-taxes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_taxes)))
                .andExpect(status().isBadRequest());

        List<Freecom_local_taxes> freecom_local_taxes = freecom_local_taxesRepository.findAll();
        assertThat(freecom_local_taxes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotal_local_transferedIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_local_taxesRepository.findAll().size();
        // set the field null
        freecom_local_taxes.setTotal_local_transfered(null);

        // Create the Freecom_local_taxes, which fails.

        restFreecom_local_taxesMockMvc.perform(post("/api/freecom-local-taxes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_local_taxes)))
                .andExpect(status().isBadRequest());

        List<Freecom_local_taxes> freecom_local_taxes = freecom_local_taxesRepository.findAll();
        assertThat(freecom_local_taxes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_local_taxes() throws Exception {
        // Initialize the database
        freecom_local_taxesRepository.saveAndFlush(freecom_local_taxes);

        // Get all the freecom_local_taxes
        restFreecom_local_taxesMockMvc.perform(get("/api/freecom-local-taxes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_local_taxes.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].total_local_retentions").value(hasItem(DEFAULT_TOTAL_LOCAL_RETENTIONS.intValue())))
                .andExpect(jsonPath("$.[*].total_local_transfered").value(hasItem(DEFAULT_TOTAL_LOCAL_TRANSFERED.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_local_taxes() throws Exception {
        // Initialize the database
        freecom_local_taxesRepository.saveAndFlush(freecom_local_taxes);

        // Get the freecom_local_taxes
        restFreecom_local_taxesMockMvc.perform(get("/api/freecom-local-taxes/{id}", freecom_local_taxes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_local_taxes.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.total_local_retentions").value(DEFAULT_TOTAL_LOCAL_RETENTIONS.intValue()))
            .andExpect(jsonPath("$.total_local_transfered").value(DEFAULT_TOTAL_LOCAL_TRANSFERED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_local_taxes() throws Exception {
        // Get the freecom_local_taxes
        restFreecom_local_taxesMockMvc.perform(get("/api/freecom-local-taxes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_local_taxes() throws Exception {
        // Initialize the database
        freecom_local_taxesService.save(freecom_local_taxes);

        int databaseSizeBeforeUpdate = freecom_local_taxesRepository.findAll().size();

        // Update the freecom_local_taxes
        Freecom_local_taxes updatedFreecom_local_taxes = new Freecom_local_taxes();
        updatedFreecom_local_taxes.setId(freecom_local_taxes.getId());
        updatedFreecom_local_taxes.setVersion(UPDATED_VERSION);
        updatedFreecom_local_taxes.setTotal_local_retentions(UPDATED_TOTAL_LOCAL_RETENTIONS);
        updatedFreecom_local_taxes.setTotal_local_transfered(UPDATED_TOTAL_LOCAL_TRANSFERED);

        restFreecom_local_taxesMockMvc.perform(put("/api/freecom-local-taxes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_local_taxes)))
                .andExpect(status().isOk());

        // Validate the Freecom_local_taxes in the database
        List<Freecom_local_taxes> freecom_local_taxes = freecom_local_taxesRepository.findAll();
        assertThat(freecom_local_taxes).hasSize(databaseSizeBeforeUpdate);
        Freecom_local_taxes testFreecom_local_taxes = freecom_local_taxes.get(freecom_local_taxes.size() - 1);
        assertThat(testFreecom_local_taxes.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_local_taxes.getTotal_local_retentions()).isEqualTo(UPDATED_TOTAL_LOCAL_RETENTIONS);
        assertThat(testFreecom_local_taxes.getTotal_local_transfered()).isEqualTo(UPDATED_TOTAL_LOCAL_TRANSFERED);
    }

    @Test
    @Transactional
    public void deleteFreecom_local_taxes() throws Exception {
        // Initialize the database
        freecom_local_taxesService.save(freecom_local_taxes);

        int databaseSizeBeforeDelete = freecom_local_taxesRepository.findAll().size();

        // Get the freecom_local_taxes
        restFreecom_local_taxesMockMvc.perform(delete("/api/freecom-local-taxes/{id}", freecom_local_taxes.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_local_taxes> freecom_local_taxes = freecom_local_taxesRepository.findAll();
        assertThat(freecom_local_taxes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
