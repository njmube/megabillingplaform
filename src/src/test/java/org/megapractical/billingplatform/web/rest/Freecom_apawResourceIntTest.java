package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_apaw;
import org.megapractical.billingplatform.repository.Freecom_apawRepository;
import org.megapractical.billingplatform.service.Freecom_apawService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_apawResource REST controller.
 *
 * @see Freecom_apawResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_apawResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_OTHERS_WELL_TYPE = "AAAAA";
    private static final String UPDATED_OTHERS_WELL_TYPE = "BBBBB";
    private static final String DEFAULT_OTHERS_ACQUIRED_TITLE = "AAAAA";
    private static final String UPDATED_OTHERS_ACQUIRED_TITLE = "BBBBB";

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_IVA = new BigDecimal(1);
    private static final BigDecimal UPDATED_IVA = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_DATE_ACQUISITION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_ACQUISITION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_ACQUISITION_STR = dateTimeFormatter.format(DEFAULT_DATE_ACQUISITION);

    @Inject
    private Freecom_apawRepository freecom_apawRepository;

    @Inject
    private Freecom_apawService freecom_apawService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_apawMockMvc;

    private Freecom_apaw freecom_apaw;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_apawResource freecom_apawResource = new Freecom_apawResource();
        ReflectionTestUtils.setField(freecom_apawResource, "freecom_apawService", freecom_apawService);
        this.restFreecom_apawMockMvc = MockMvcBuilders.standaloneSetup(freecom_apawResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_apaw = new Freecom_apaw();
        freecom_apaw.setVersion(DEFAULT_VERSION);
        freecom_apaw.setOthers_well_type(DEFAULT_OTHERS_WELL_TYPE);
        freecom_apaw.setOthers_acquired_title(DEFAULT_OTHERS_ACQUIRED_TITLE);
        freecom_apaw.setSubtotal(DEFAULT_SUBTOTAL);
        freecom_apaw.setIva(DEFAULT_IVA);
        freecom_apaw.setDate_acquisition(DEFAULT_DATE_ACQUISITION);
    }

    @Test
    @Transactional
    public void createFreecom_apaw() throws Exception {
        int databaseSizeBeforeCreate = freecom_apawRepository.findAll().size();

        // Create the Freecom_apaw

        restFreecom_apawMockMvc.perform(post("/api/freecom-apaws")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_apaw)))
                .andExpect(status().isCreated());

        // Validate the Freecom_apaw in the database
        List<Freecom_apaw> freecom_apaws = freecom_apawRepository.findAll();
        assertThat(freecom_apaws).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_apaw testFreecom_apaw = freecom_apaws.get(freecom_apaws.size() - 1);
        assertThat(testFreecom_apaw.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_apaw.getOthers_well_type()).isEqualTo(DEFAULT_OTHERS_WELL_TYPE);
        assertThat(testFreecom_apaw.getOthers_acquired_title()).isEqualTo(DEFAULT_OTHERS_ACQUIRED_TITLE);
        assertThat(testFreecom_apaw.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testFreecom_apaw.getIva()).isEqualTo(DEFAULT_IVA);
        assertThat(testFreecom_apaw.getDate_acquisition()).isEqualTo(DEFAULT_DATE_ACQUISITION);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_apawRepository.findAll().size();
        // set the field null
        freecom_apaw.setVersion(null);

        // Create the Freecom_apaw, which fails.

        restFreecom_apawMockMvc.perform(post("/api/freecom-apaws")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_apaw)))
                .andExpect(status().isBadRequest());

        List<Freecom_apaw> freecom_apaws = freecom_apawRepository.findAll();
        assertThat(freecom_apaws).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_acquisitionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_apawRepository.findAll().size();
        // set the field null
        freecom_apaw.setDate_acquisition(null);

        // Create the Freecom_apaw, which fails.

        restFreecom_apawMockMvc.perform(post("/api/freecom-apaws")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_apaw)))
                .andExpect(status().isBadRequest());

        List<Freecom_apaw> freecom_apaws = freecom_apawRepository.findAll();
        assertThat(freecom_apaws).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_apaws() throws Exception {
        // Initialize the database
        freecom_apawRepository.saveAndFlush(freecom_apaw);

        // Get all the freecom_apaws
        restFreecom_apawMockMvc.perform(get("/api/freecom-apaws?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_apaw.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].others_well_type").value(hasItem(DEFAULT_OTHERS_WELL_TYPE.toString())))
                .andExpect(jsonPath("$.[*].others_acquired_title").value(hasItem(DEFAULT_OTHERS_ACQUIRED_TITLE.toString())))
                .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
                .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA.intValue())))
                .andExpect(jsonPath("$.[*].date_acquisition").value(hasItem(DEFAULT_DATE_ACQUISITION_STR)));
    }

    @Test
    @Transactional
    public void getFreecom_apaw() throws Exception {
        // Initialize the database
        freecom_apawRepository.saveAndFlush(freecom_apaw);

        // Get the freecom_apaw
        restFreecom_apawMockMvc.perform(get("/api/freecom-apaws/{id}", freecom_apaw.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_apaw.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.others_well_type").value(DEFAULT_OTHERS_WELL_TYPE.toString()))
            .andExpect(jsonPath("$.others_acquired_title").value(DEFAULT_OTHERS_ACQUIRED_TITLE.toString()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA.intValue()))
            .andExpect(jsonPath("$.date_acquisition").value(DEFAULT_DATE_ACQUISITION_STR));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_apaw() throws Exception {
        // Get the freecom_apaw
        restFreecom_apawMockMvc.perform(get("/api/freecom-apaws/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_apaw() throws Exception {
        // Initialize the database
        freecom_apawService.save(freecom_apaw);

        int databaseSizeBeforeUpdate = freecom_apawRepository.findAll().size();

        // Update the freecom_apaw
        Freecom_apaw updatedFreecom_apaw = new Freecom_apaw();
        updatedFreecom_apaw.setId(freecom_apaw.getId());
        updatedFreecom_apaw.setVersion(UPDATED_VERSION);
        updatedFreecom_apaw.setOthers_well_type(UPDATED_OTHERS_WELL_TYPE);
        updatedFreecom_apaw.setOthers_acquired_title(UPDATED_OTHERS_ACQUIRED_TITLE);
        updatedFreecom_apaw.setSubtotal(UPDATED_SUBTOTAL);
        updatedFreecom_apaw.setIva(UPDATED_IVA);
        updatedFreecom_apaw.setDate_acquisition(UPDATED_DATE_ACQUISITION);

        restFreecom_apawMockMvc.perform(put("/api/freecom-apaws")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_apaw)))
                .andExpect(status().isOk());

        // Validate the Freecom_apaw in the database
        List<Freecom_apaw> freecom_apaws = freecom_apawRepository.findAll();
        assertThat(freecom_apaws).hasSize(databaseSizeBeforeUpdate);
        Freecom_apaw testFreecom_apaw = freecom_apaws.get(freecom_apaws.size() - 1);
        assertThat(testFreecom_apaw.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_apaw.getOthers_well_type()).isEqualTo(UPDATED_OTHERS_WELL_TYPE);
        assertThat(testFreecom_apaw.getOthers_acquired_title()).isEqualTo(UPDATED_OTHERS_ACQUIRED_TITLE);
        assertThat(testFreecom_apaw.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testFreecom_apaw.getIva()).isEqualTo(UPDATED_IVA);
        assertThat(testFreecom_apaw.getDate_acquisition()).isEqualTo(UPDATED_DATE_ACQUISITION);
    }

    @Test
    @Transactional
    public void deleteFreecom_apaw() throws Exception {
        // Initialize the database
        freecom_apawService.save(freecom_apaw);

        int databaseSizeBeforeDelete = freecom_apawRepository.findAll().size();

        // Get the freecom_apaw
        restFreecom_apawMockMvc.perform(delete("/api/freecom-apaws/{id}", freecom_apaw.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_apaw> freecom_apaws = freecom_apawRepository.findAll();
        assertThat(freecom_apaws).hasSize(databaseSizeBeforeDelete - 1);
    }
}
