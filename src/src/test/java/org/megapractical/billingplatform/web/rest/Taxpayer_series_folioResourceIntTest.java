package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Taxpayer_series_folio;
import org.megapractical.billingplatform.repository.Taxpayer_series_folioRepository;
import org.megapractical.billingplatform.service.Taxpayer_series_folioService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Taxpayer_series_folioResource REST controller.
 *
 * @see Taxpayer_series_folioResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Taxpayer_series_folioResourceIntTest {

    private static final String DEFAULT_SERIE = "AAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_FOLIO_START = 1;
    private static final Integer UPDATED_FOLIO_START = 2;

    private static final Integer DEFAULT_FOLIO_END = 1;
    private static final Integer UPDATED_FOLIO_END = 2;

    private static final Integer DEFAULT_FOLIO_CURRENT = 1;
    private static final Integer UPDATED_FOLIO_CURRENT = 2;

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    @Inject
    private Taxpayer_series_folioRepository taxpayer_series_folioRepository;

    @Inject
    private Taxpayer_series_folioService taxpayer_series_folioService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTaxpayer_series_folioMockMvc;

    private Taxpayer_series_folio taxpayer_series_folio;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Taxpayer_series_folioResource taxpayer_series_folioResource = new Taxpayer_series_folioResource();
        ReflectionTestUtils.setField(taxpayer_series_folioResource, "taxpayer_series_folioService", taxpayer_series_folioService);
        this.restTaxpayer_series_folioMockMvc = MockMvcBuilders.standaloneSetup(taxpayer_series_folioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        taxpayer_series_folio = new Taxpayer_series_folio();
        taxpayer_series_folio.setSerie(DEFAULT_SERIE);
        taxpayer_series_folio.setFolio_start(DEFAULT_FOLIO_START);
        taxpayer_series_folio.setFolio_end(DEFAULT_FOLIO_END);
        taxpayer_series_folio.setFolio_current(DEFAULT_FOLIO_CURRENT);
        taxpayer_series_folio.setDate_creation(DEFAULT_DATE_CREATION);
        taxpayer_series_folio.setEnable(DEFAULT_ENABLE);
    }

    @Test
    @Transactional
    public void createTaxpayer_series_folio() throws Exception {
        int databaseSizeBeforeCreate = taxpayer_series_folioRepository.findAll().size();

        // Create the Taxpayer_series_folio

        restTaxpayer_series_folioMockMvc.perform(post("/api/taxpayer-series-folios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_series_folio)))
                .andExpect(status().isCreated());

        // Validate the Taxpayer_series_folio in the database
        List<Taxpayer_series_folio> taxpayer_series_folios = taxpayer_series_folioRepository.findAll();
        assertThat(taxpayer_series_folios).hasSize(databaseSizeBeforeCreate + 1);
        Taxpayer_series_folio testTaxpayer_series_folio = taxpayer_series_folios.get(taxpayer_series_folios.size() - 1);
        assertThat(testTaxpayer_series_folio.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testTaxpayer_series_folio.getFolio_start()).isEqualTo(DEFAULT_FOLIO_START);
        assertThat(testTaxpayer_series_folio.getFolio_end()).isEqualTo(DEFAULT_FOLIO_END);
        assertThat(testTaxpayer_series_folio.getFolio_current()).isEqualTo(DEFAULT_FOLIO_CURRENT);
        assertThat(testTaxpayer_series_folio.getDate_creation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testTaxpayer_series_folio.isEnable()).isEqualTo(DEFAULT_ENABLE);
    }

    @Test
    @Transactional
    public void checkSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_series_folioRepository.findAll().size();
        // set the field null
        taxpayer_series_folio.setSerie(null);

        // Create the Taxpayer_series_folio, which fails.

        restTaxpayer_series_folioMockMvc.perform(post("/api/taxpayer-series-folios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_series_folio)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_series_folio> taxpayer_series_folios = taxpayer_series_folioRepository.findAll();
        assertThat(taxpayer_series_folios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolio_startIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_series_folioRepository.findAll().size();
        // set the field null
        taxpayer_series_folio.setFolio_start(null);

        // Create the Taxpayer_series_folio, which fails.

        restTaxpayer_series_folioMockMvc.perform(post("/api/taxpayer-series-folios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_series_folio)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_series_folio> taxpayer_series_folios = taxpayer_series_folioRepository.findAll();
        assertThat(taxpayer_series_folios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolio_endIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_series_folioRepository.findAll().size();
        // set the field null
        taxpayer_series_folio.setFolio_end(null);

        // Create the Taxpayer_series_folio, which fails.

        restTaxpayer_series_folioMockMvc.perform(post("/api/taxpayer-series-folios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_series_folio)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_series_folio> taxpayer_series_folios = taxpayer_series_folioRepository.findAll();
        assertThat(taxpayer_series_folios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFolio_currentIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_series_folioRepository.findAll().size();
        // set the field null
        taxpayer_series_folio.setFolio_current(null);

        // Create the Taxpayer_series_folio, which fails.

        restTaxpayer_series_folioMockMvc.perform(post("/api/taxpayer-series-folios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_series_folio)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_series_folio> taxpayer_series_folios = taxpayer_series_folioRepository.findAll();
        assertThat(taxpayer_series_folios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_creationIsRequired() throws Exception {
        int databaseSizeBeforeTest = taxpayer_series_folioRepository.findAll().size();
        // set the field null
        taxpayer_series_folio.setDate_creation(null);

        // Create the Taxpayer_series_folio, which fails.

        restTaxpayer_series_folioMockMvc.perform(post("/api/taxpayer-series-folios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(taxpayer_series_folio)))
                .andExpect(status().isBadRequest());

        List<Taxpayer_series_folio> taxpayer_series_folios = taxpayer_series_folioRepository.findAll();
        assertThat(taxpayer_series_folios).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTaxpayer_series_folios() throws Exception {
        // Initialize the database
        taxpayer_series_folioRepository.saveAndFlush(taxpayer_series_folio);

        // Get all the taxpayer_series_folios
        restTaxpayer_series_folioMockMvc.perform(get("/api/taxpayer-series-folios?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(taxpayer_series_folio.getId().intValue())))
                .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE.toString())))
                .andExpect(jsonPath("$.[*].folio_start").value(hasItem(DEFAULT_FOLIO_START)))
                .andExpect(jsonPath("$.[*].folio_end").value(hasItem(DEFAULT_FOLIO_END)))
                .andExpect(jsonPath("$.[*].folio_current").value(hasItem(DEFAULT_FOLIO_CURRENT)))
                .andExpect(jsonPath("$.[*].date_creation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
                .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getTaxpayer_series_folio() throws Exception {
        // Initialize the database
        taxpayer_series_folioRepository.saveAndFlush(taxpayer_series_folio);

        // Get the taxpayer_series_folio
        restTaxpayer_series_folioMockMvc.perform(get("/api/taxpayer-series-folios/{id}", taxpayer_series_folio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(taxpayer_series_folio.getId().intValue()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE.toString()))
            .andExpect(jsonPath("$.folio_start").value(DEFAULT_FOLIO_START))
            .andExpect(jsonPath("$.folio_end").value(DEFAULT_FOLIO_END))
            .andExpect(jsonPath("$.folio_current").value(DEFAULT_FOLIO_CURRENT))
            .andExpect(jsonPath("$.date_creation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTaxpayer_series_folio() throws Exception {
        // Get the taxpayer_series_folio
        restTaxpayer_series_folioMockMvc.perform(get("/api/taxpayer-series-folios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxpayer_series_folio() throws Exception {
        // Initialize the database
        taxpayer_series_folioService.save(taxpayer_series_folio);

        int databaseSizeBeforeUpdate = taxpayer_series_folioRepository.findAll().size();

        // Update the taxpayer_series_folio
        Taxpayer_series_folio updatedTaxpayer_series_folio = new Taxpayer_series_folio();
        updatedTaxpayer_series_folio.setId(taxpayer_series_folio.getId());
        updatedTaxpayer_series_folio.setSerie(UPDATED_SERIE);
        updatedTaxpayer_series_folio.setFolio_start(UPDATED_FOLIO_START);
        updatedTaxpayer_series_folio.setFolio_end(UPDATED_FOLIO_END);
        updatedTaxpayer_series_folio.setFolio_current(UPDATED_FOLIO_CURRENT);
        updatedTaxpayer_series_folio.setDate_creation(UPDATED_DATE_CREATION);
        updatedTaxpayer_series_folio.setEnable(UPDATED_ENABLE);

        restTaxpayer_series_folioMockMvc.perform(put("/api/taxpayer-series-folios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTaxpayer_series_folio)))
                .andExpect(status().isOk());

        // Validate the Taxpayer_series_folio in the database
        List<Taxpayer_series_folio> taxpayer_series_folios = taxpayer_series_folioRepository.findAll();
        assertThat(taxpayer_series_folios).hasSize(databaseSizeBeforeUpdate);
        Taxpayer_series_folio testTaxpayer_series_folio = taxpayer_series_folios.get(taxpayer_series_folios.size() - 1);
        assertThat(testTaxpayer_series_folio.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testTaxpayer_series_folio.getFolio_start()).isEqualTo(UPDATED_FOLIO_START);
        assertThat(testTaxpayer_series_folio.getFolio_end()).isEqualTo(UPDATED_FOLIO_END);
        assertThat(testTaxpayer_series_folio.getFolio_current()).isEqualTo(UPDATED_FOLIO_CURRENT);
        assertThat(testTaxpayer_series_folio.getDate_creation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testTaxpayer_series_folio.isEnable()).isEqualTo(UPDATED_ENABLE);
    }

    @Test
    @Transactional
    public void deleteTaxpayer_series_folio() throws Exception {
        // Initialize the database
        taxpayer_series_folioService.save(taxpayer_series_folio);

        int databaseSizeBeforeDelete = taxpayer_series_folioRepository.findAll().size();

        // Get the taxpayer_series_folio
        restTaxpayer_series_folioMockMvc.perform(delete("/api/taxpayer-series-folios/{id}", taxpayer_series_folio.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Taxpayer_series_folio> taxpayer_series_folios = taxpayer_series_folioRepository.findAll();
        assertThat(taxpayer_series_folios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
