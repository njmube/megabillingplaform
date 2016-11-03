package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_donees;
import org.megapractical.billingplatform.repository.Com_doneesRepository;
import org.megapractical.billingplatform.service.Com_doneesService;

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
 * Test class for the Com_doneesResource REST controller.
 *
 * @see Com_doneesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_doneesResourceIntTest {

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
    private Com_doneesRepository com_doneesRepository;

    @Inject
    private Com_doneesService com_doneesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_doneesMockMvc;

    private Com_donees com_donees;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_doneesResource com_doneesResource = new Com_doneesResource();
        ReflectionTestUtils.setField(com_doneesResource, "com_doneesService", com_doneesService);
        this.restCom_doneesMockMvc = MockMvcBuilders.standaloneSetup(com_doneesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_donees = new Com_donees();
        com_donees.setVersion(DEFAULT_VERSION);
        com_donees.setNo_authorization(DEFAULT_NO_AUTHORIZATION);
        com_donees.setDate_authorization(DEFAULT_DATE_AUTHORIZATION);
        com_donees.setLegend(DEFAULT_LEGEND);
    }

    @Test
    @Transactional
    public void createCom_donees() throws Exception {
        int databaseSizeBeforeCreate = com_doneesRepository.findAll().size();

        // Create the Com_donees

        restCom_doneesMockMvc.perform(post("/api/com-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_donees)))
                .andExpect(status().isCreated());

        // Validate the Com_donees in the database
        List<Com_donees> com_donees = com_doneesRepository.findAll();
        assertThat(com_donees).hasSize(databaseSizeBeforeCreate + 1);
        Com_donees testCom_donees = com_donees.get(com_donees.size() - 1);
        assertThat(testCom_donees.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_donees.getNo_authorization()).isEqualTo(DEFAULT_NO_AUTHORIZATION);
        assertThat(testCom_donees.getDate_authorization()).isEqualTo(DEFAULT_DATE_AUTHORIZATION);
        assertThat(testCom_donees.getLegend()).isEqualTo(DEFAULT_LEGEND);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_doneesRepository.findAll().size();
        // set the field null
        com_donees.setVersion(null);

        // Create the Com_donees, which fails.

        restCom_doneesMockMvc.perform(post("/api/com-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_donees)))
                .andExpect(status().isBadRequest());

        List<Com_donees> com_donees = com_doneesRepository.findAll();
        assertThat(com_donees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNo_authorizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_doneesRepository.findAll().size();
        // set the field null
        com_donees.setNo_authorization(null);

        // Create the Com_donees, which fails.

        restCom_doneesMockMvc.perform(post("/api/com-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_donees)))
                .andExpect(status().isBadRequest());

        List<Com_donees> com_donees = com_doneesRepository.findAll();
        assertThat(com_donees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_authorizationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_doneesRepository.findAll().size();
        // set the field null
        com_donees.setDate_authorization(null);

        // Create the Com_donees, which fails.

        restCom_doneesMockMvc.perform(post("/api/com-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_donees)))
                .andExpect(status().isBadRequest());

        List<Com_donees> com_donees = com_doneesRepository.findAll();
        assertThat(com_donees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLegendIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_doneesRepository.findAll().size();
        // set the field null
        com_donees.setLegend(null);

        // Create the Com_donees, which fails.

        restCom_doneesMockMvc.perform(post("/api/com-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_donees)))
                .andExpect(status().isBadRequest());

        List<Com_donees> com_donees = com_doneesRepository.findAll();
        assertThat(com_donees).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_donees() throws Exception {
        // Initialize the database
        com_doneesRepository.saveAndFlush(com_donees);

        // Get all the com_donees
        restCom_doneesMockMvc.perform(get("/api/com-donees?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_donees.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].no_authorization").value(hasItem(DEFAULT_NO_AUTHORIZATION.toString())))
                .andExpect(jsonPath("$.[*].date_authorization").value(hasItem(DEFAULT_DATE_AUTHORIZATION_STR)))
                .andExpect(jsonPath("$.[*].legend").value(hasItem(DEFAULT_LEGEND.toString())));
    }

    @Test
    @Transactional
    public void getCom_donees() throws Exception {
        // Initialize the database
        com_doneesRepository.saveAndFlush(com_donees);

        // Get the com_donees
        restCom_doneesMockMvc.perform(get("/api/com-donees/{id}", com_donees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_donees.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.no_authorization").value(DEFAULT_NO_AUTHORIZATION.toString()))
            .andExpect(jsonPath("$.date_authorization").value(DEFAULT_DATE_AUTHORIZATION_STR))
            .andExpect(jsonPath("$.legend").value(DEFAULT_LEGEND.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_donees() throws Exception {
        // Get the com_donees
        restCom_doneesMockMvc.perform(get("/api/com-donees/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_donees() throws Exception {
        // Initialize the database
        com_doneesService.save(com_donees);

        int databaseSizeBeforeUpdate = com_doneesRepository.findAll().size();

        // Update the com_donees
        Com_donees updatedCom_donees = new Com_donees();
        updatedCom_donees.setId(com_donees.getId());
        updatedCom_donees.setVersion(UPDATED_VERSION);
        updatedCom_donees.setNo_authorization(UPDATED_NO_AUTHORIZATION);
        updatedCom_donees.setDate_authorization(UPDATED_DATE_AUTHORIZATION);
        updatedCom_donees.setLegend(UPDATED_LEGEND);

        restCom_doneesMockMvc.perform(put("/api/com-donees")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_donees)))
                .andExpect(status().isOk());

        // Validate the Com_donees in the database
        List<Com_donees> com_donees = com_doneesRepository.findAll();
        assertThat(com_donees).hasSize(databaseSizeBeforeUpdate);
        Com_donees testCom_donees = com_donees.get(com_donees.size() - 1);
        assertThat(testCom_donees.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_donees.getNo_authorization()).isEqualTo(UPDATED_NO_AUTHORIZATION);
        assertThat(testCom_donees.getDate_authorization()).isEqualTo(UPDATED_DATE_AUTHORIZATION);
        assertThat(testCom_donees.getLegend()).isEqualTo(UPDATED_LEGEND);
    }

    @Test
    @Transactional
    public void deleteCom_donees() throws Exception {
        // Initialize the database
        com_doneesService.save(com_donees);

        int databaseSizeBeforeDelete = com_doneesRepository.findAll().size();

        // Get the com_donees
        restCom_doneesMockMvc.perform(delete("/api/com-donees/{id}", com_donees.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_donees> com_donees = com_doneesRepository.findAll();
        assertThat(com_donees).hasSize(databaseSizeBeforeDelete - 1);
    }
}
