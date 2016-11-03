package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_apaw;
import org.megapractical.billingplatform.repository.Com_apawRepository;
import org.megapractical.billingplatform.service.Com_apawService;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Com_apawResource REST controller.
 *
 * @see Com_apawResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_apawResourceIntTest {

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
    private Com_apawRepository com_apawRepository;

    @Inject
    private Com_apawService com_apawService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_apawMockMvc;

    private Com_apaw com_apaw;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_apawResource com_apawResource = new Com_apawResource();
        ReflectionTestUtils.setField(com_apawResource, "com_apawService", com_apawService);
        this.restCom_apawMockMvc = MockMvcBuilders.standaloneSetup(com_apawResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_apaw = new Com_apaw();
        com_apaw.setVersion(DEFAULT_VERSION);
        com_apaw.setOthers_well_type(DEFAULT_OTHERS_WELL_TYPE);
        com_apaw.setOthers_acquired_title(DEFAULT_OTHERS_ACQUIRED_TITLE);
        com_apaw.setSubtotal(DEFAULT_SUBTOTAL);
        com_apaw.setIva(DEFAULT_IVA);
        com_apaw.setDate_acquisition(DEFAULT_DATE_ACQUISITION);
    }

    @Test
    @Transactional
    public void createCom_apaw() throws Exception {
        int databaseSizeBeforeCreate = com_apawRepository.findAll().size();

        // Create the Com_apaw

        restCom_apawMockMvc.perform(post("/api/com-apaws")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_apaw)))
                .andExpect(status().isCreated());

        // Validate the Com_apaw in the database
        List<Com_apaw> com_apaws = com_apawRepository.findAll();
        assertThat(com_apaws).hasSize(databaseSizeBeforeCreate + 1);
        Com_apaw testCom_apaw = com_apaws.get(com_apaws.size() - 1);
        assertThat(testCom_apaw.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_apaw.getOthers_well_type()).isEqualTo(DEFAULT_OTHERS_WELL_TYPE);
        assertThat(testCom_apaw.getOthers_acquired_title()).isEqualTo(DEFAULT_OTHERS_ACQUIRED_TITLE);
        assertThat(testCom_apaw.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testCom_apaw.getIva()).isEqualTo(DEFAULT_IVA);
        assertThat(testCom_apaw.getDate_acquisition()).isEqualTo(DEFAULT_DATE_ACQUISITION);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_apawRepository.findAll().size();
        // set the field null
        com_apaw.setVersion(null);

        // Create the Com_apaw, which fails.

        restCom_apawMockMvc.perform(post("/api/com-apaws")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_apaw)))
                .andExpect(status().isBadRequest());

        List<Com_apaw> com_apaws = com_apawRepository.findAll();
        assertThat(com_apaws).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_acquisitionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_apawRepository.findAll().size();
        // set the field null
        com_apaw.setDate_acquisition(null);

        // Create the Com_apaw, which fails.

        restCom_apawMockMvc.perform(post("/api/com-apaws")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_apaw)))
                .andExpect(status().isBadRequest());

        List<Com_apaw> com_apaws = com_apawRepository.findAll();
        assertThat(com_apaws).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_apaws() throws Exception {
        // Initialize the database
        com_apawRepository.saveAndFlush(com_apaw);

        // Get all the com_apaws
        restCom_apawMockMvc.perform(get("/api/com-apaws?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_apaw.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].others_well_type").value(hasItem(DEFAULT_OTHERS_WELL_TYPE.toString())))
                .andExpect(jsonPath("$.[*].others_acquired_title").value(hasItem(DEFAULT_OTHERS_ACQUIRED_TITLE.toString())))
                .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
                .andExpect(jsonPath("$.[*].iva").value(hasItem(DEFAULT_IVA.intValue())))
                .andExpect(jsonPath("$.[*].date_acquisition").value(hasItem(DEFAULT_DATE_ACQUISITION_STR)));
    }

    @Test
    @Transactional
    public void getCom_apaw() throws Exception {
        // Initialize the database
        com_apawRepository.saveAndFlush(com_apaw);

        // Get the com_apaw
        restCom_apawMockMvc.perform(get("/api/com-apaws/{id}", com_apaw.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_apaw.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.others_well_type").value(DEFAULT_OTHERS_WELL_TYPE.toString()))
            .andExpect(jsonPath("$.others_acquired_title").value(DEFAULT_OTHERS_ACQUIRED_TITLE.toString()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.iva").value(DEFAULT_IVA.intValue()))
            .andExpect(jsonPath("$.date_acquisition").value(DEFAULT_DATE_ACQUISITION_STR));
    }

    @Test
    @Transactional
    public void getNonExistingCom_apaw() throws Exception {
        // Get the com_apaw
        restCom_apawMockMvc.perform(get("/api/com-apaws/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_apaw() throws Exception {
        // Initialize the database
        com_apawService.save(com_apaw);

        int databaseSizeBeforeUpdate = com_apawRepository.findAll().size();

        // Update the com_apaw
        Com_apaw updatedCom_apaw = new Com_apaw();
        updatedCom_apaw.setId(com_apaw.getId());
        updatedCom_apaw.setVersion(UPDATED_VERSION);
        updatedCom_apaw.setOthers_well_type(UPDATED_OTHERS_WELL_TYPE);
        updatedCom_apaw.setOthers_acquired_title(UPDATED_OTHERS_ACQUIRED_TITLE);
        updatedCom_apaw.setSubtotal(UPDATED_SUBTOTAL);
        updatedCom_apaw.setIva(UPDATED_IVA);
        updatedCom_apaw.setDate_acquisition(UPDATED_DATE_ACQUISITION);

        restCom_apawMockMvc.perform(put("/api/com-apaws")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_apaw)))
                .andExpect(status().isOk());

        // Validate the Com_apaw in the database
        List<Com_apaw> com_apaws = com_apawRepository.findAll();
        assertThat(com_apaws).hasSize(databaseSizeBeforeUpdate);
        Com_apaw testCom_apaw = com_apaws.get(com_apaws.size() - 1);
        assertThat(testCom_apaw.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_apaw.getOthers_well_type()).isEqualTo(UPDATED_OTHERS_WELL_TYPE);
        assertThat(testCom_apaw.getOthers_acquired_title()).isEqualTo(UPDATED_OTHERS_ACQUIRED_TITLE);
        assertThat(testCom_apaw.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testCom_apaw.getIva()).isEqualTo(UPDATED_IVA);
        assertThat(testCom_apaw.getDate_acquisition()).isEqualTo(UPDATED_DATE_ACQUISITION);
    }

    @Test
    @Transactional
    public void deleteCom_apaw() throws Exception {
        // Initialize the database
        com_apawService.save(com_apaw);

        int databaseSizeBeforeDelete = com_apawRepository.findAll().size();

        // Get the com_apaw
        restCom_apawMockMvc.perform(delete("/api/com-apaws/{id}", com_apaw.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_apaw> com_apaws = com_apawRepository.findAll();
        assertThat(com_apaws).hasSize(databaseSizeBeforeDelete - 1);
    }
}
