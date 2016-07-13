package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_donees;
import org.megapractical.billingplatform.repository.Freecom_doneesRepository;
import org.megapractical.billingplatform.service.Freecom_doneesService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_doneesResource REST controller.
 *
 * @see Freecom_doneesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_doneesResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_VERSION = "AAA";
    private static final String UPDATED_VERSION = "BBB";
    private static final String DEFAULT_NO_AUTHORIZATION = "AAAAA";
    private static final String UPDATED_NO_AUTHORIZATION = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATE_AUTHORIZATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_AUTHORIZATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_AUTHORIZATION_STR = dateTimeFormatter.format(DEFAULT_DATE_AUTHORIZATION);
    private static final String DEFAULT_LEGEND = "AAAAA";
    private static final String UPDATED_LEGEND = "BBBBB";

    @Inject
    private Freecom_doneesRepository freecom_doneesRepository;

    @Inject
    private Freecom_doneesService freecom_doneesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_doneesMockMvc;

    private Freecom_donees freecom_donees;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_doneesResource freecom_doneesResource = new Freecom_doneesResource();
        ReflectionTestUtils.setField(freecom_doneesResource, "freecom_doneesService", freecom_doneesService);
        this.restFreecom_doneesMockMvc = MockMvcBuilders.standaloneSetup(freecom_doneesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_donees = new Freecom_donees();
        freecom_donees.setVersion(DEFAULT_VERSION);
        freecom_donees.setNo_authorization(DEFAULT_NO_AUTHORIZATION);
        freecom_donees.setDate_authorization(DEFAULT_DATE_AUTHORIZATION);
        freecom_donees.setLegend(DEFAULT_LEGEND);
    }

    @Test
    @Transactional
    public void createFreecom_donees() throws Exception {
        int databaseSizeBeforeCreate = freecom_doneesRepository.findAll().size();

        // Create the Freecom_donees

        restFreecom_doneesMockMvc.perform(post("/api/freecom-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_donees)))
                .andExpect(status().isCreated());

        // Validate the Freecom_donees in the database
        List<Freecom_donees> freecom_donees = freecom_doneesRepository.findAll();
        assertThat(freecom_donees).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_donees testFreecom_donees = freecom_donees.get(freecom_donees.size() - 1);
        assertThat(testFreecom_donees.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_donees.getNo_authorization()).isEqualTo(DEFAULT_NO_AUTHORIZATION);
        assertThat(testFreecom_donees.getDate_authorization()).isEqualTo(DEFAULT_DATE_AUTHORIZATION);
        assertThat(testFreecom_donees.getLegend()).isEqualTo(DEFAULT_LEGEND);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_doneesRepository.findAll().size();
        // set the field null
        freecom_donees.setVersion(null);

        // Create the Freecom_donees, which fails.

        restFreecom_doneesMockMvc.perform(post("/api/freecom-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_donees)))
                .andExpect(status().isBadRequest());

        List<Freecom_donees> freecom_donees = freecom_doneesRepository.findAll();
        assertThat(freecom_donees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNo_authorizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_doneesRepository.findAll().size();
        // set the field null
        freecom_donees.setNo_authorization(null);

        // Create the Freecom_donees, which fails.

        restFreecom_doneesMockMvc.perform(post("/api/freecom-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_donees)))
                .andExpect(status().isBadRequest());

        List<Freecom_donees> freecom_donees = freecom_doneesRepository.findAll();
        assertThat(freecom_donees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_authorizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_doneesRepository.findAll().size();
        // set the field null
        freecom_donees.setDate_authorization(null);

        // Create the Freecom_donees, which fails.

        restFreecom_doneesMockMvc.perform(post("/api/freecom-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_donees)))
                .andExpect(status().isBadRequest());

        List<Freecom_donees> freecom_donees = freecom_doneesRepository.findAll();
        assertThat(freecom_donees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegendIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_doneesRepository.findAll().size();
        // set the field null
        freecom_donees.setLegend(null);

        // Create the Freecom_donees, which fails.

        restFreecom_doneesMockMvc.perform(post("/api/freecom-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_donees)))
                .andExpect(status().isBadRequest());

        List<Freecom_donees> freecom_donees = freecom_doneesRepository.findAll();
        assertThat(freecom_donees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_donees() throws Exception {
        // Initialize the database
        freecom_doneesRepository.saveAndFlush(freecom_donees);

        // Get all the freecom_donees
        restFreecom_doneesMockMvc.perform(get("/api/freecom-donees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_donees.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].no_authorization").value(hasItem(DEFAULT_NO_AUTHORIZATION.toString())))
                .andExpect(jsonPath("$.[*].date_authorization").value(hasItem(DEFAULT_DATE_AUTHORIZATION_STR)))
                .andExpect(jsonPath("$.[*].legend").value(hasItem(DEFAULT_LEGEND.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_donees() throws Exception {
        // Initialize the database
        freecom_doneesRepository.saveAndFlush(freecom_donees);

        // Get the freecom_donees
        restFreecom_doneesMockMvc.perform(get("/api/freecom-donees/{id}", freecom_donees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_donees.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.no_authorization").value(DEFAULT_NO_AUTHORIZATION.toString()))
            .andExpect(jsonPath("$.date_authorization").value(DEFAULT_DATE_AUTHORIZATION_STR))
            .andExpect(jsonPath("$.legend").value(DEFAULT_LEGEND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_donees() throws Exception {
        // Get the freecom_donees
        restFreecom_doneesMockMvc.perform(get("/api/freecom-donees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_donees() throws Exception {
        // Initialize the database
        freecom_doneesService.save(freecom_donees);

        int databaseSizeBeforeUpdate = freecom_doneesRepository.findAll().size();

        // Update the freecom_donees
        Freecom_donees updatedFreecom_donees = new Freecom_donees();
        updatedFreecom_donees.setId(freecom_donees.getId());
        updatedFreecom_donees.setVersion(UPDATED_VERSION);
        updatedFreecom_donees.setNo_authorization(UPDATED_NO_AUTHORIZATION);
        updatedFreecom_donees.setDate_authorization(UPDATED_DATE_AUTHORIZATION);
        updatedFreecom_donees.setLegend(UPDATED_LEGEND);

        restFreecom_doneesMockMvc.perform(put("/api/freecom-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_donees)))
                .andExpect(status().isOk());

        // Validate the Freecom_donees in the database
        List<Freecom_donees> freecom_donees = freecom_doneesRepository.findAll();
        assertThat(freecom_donees).hasSize(databaseSizeBeforeUpdate);
        Freecom_donees testFreecom_donees = freecom_donees.get(freecom_donees.size() - 1);
        assertThat(testFreecom_donees.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_donees.getNo_authorization()).isEqualTo(UPDATED_NO_AUTHORIZATION);
        assertThat(testFreecom_donees.getDate_authorization()).isEqualTo(UPDATED_DATE_AUTHORIZATION);
        assertThat(testFreecom_donees.getLegend()).isEqualTo(UPDATED_LEGEND);
    }

    @Test
    @Transactional
    public void deleteFreecom_donees() throws Exception {
        // Initialize the database
        freecom_doneesService.save(freecom_donees);

        int databaseSizeBeforeDelete = freecom_doneesRepository.findAll().size();

        // Get the freecom_donees
        restFreecom_doneesMockMvc.perform(delete("/api/freecom-donees/{id}", freecom_donees.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_donees> freecom_donees = freecom_doneesRepository.findAll();
        assertThat(freecom_donees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
