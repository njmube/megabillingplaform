package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_partial_construction_services;
import org.megapractical.billingplatform.repository.Freecom_partial_construction_servicesRepository;
import org.megapractical.billingplatform.service.Freecom_partial_construction_servicesService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_partial_construction_servicesResource REST controller.
 *
 * @see Freecom_partial_construction_servicesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_partial_construction_servicesResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";
    private static final String DEFAULT_NOEXT = "AAAAA";
    private static final String UPDATED_NOEXT = "BBBBB";
    private static final String DEFAULT_NOINT = "AAAAA";
    private static final String UPDATED_NOINT = "BBBBB";
    private static final String DEFAULT_LOCATION = "AAAAA";
    private static final String UPDATED_LOCATION = "BBBBB";
    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";
    private static final String DEFAULT_NUMPERLICOAUT = "AAAAA";
    private static final String UPDATED_NUMPERLICOAUT = "BBBBB";

    @Inject
    private Freecom_partial_construction_servicesRepository freecom_partial_construction_servicesRepository;

    @Inject
    private Freecom_partial_construction_servicesService freecom_partial_construction_servicesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_partial_construction_servicesMockMvc;

    private Freecom_partial_construction_services freecom_partial_construction_services;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_partial_construction_servicesResource freecom_partial_construction_servicesResource = new Freecom_partial_construction_servicesResource();
        ReflectionTestUtils.setField(freecom_partial_construction_servicesResource, "freecom_partial_construction_servicesService", freecom_partial_construction_servicesService);
        this.restFreecom_partial_construction_servicesMockMvc = MockMvcBuilders.standaloneSetup(freecom_partial_construction_servicesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_partial_construction_services = new Freecom_partial_construction_services();
        freecom_partial_construction_services.setVersion(DEFAULT_VERSION);
        freecom_partial_construction_services.setStreet(DEFAULT_STREET);
        freecom_partial_construction_services.setNoext(DEFAULT_NOEXT);
        freecom_partial_construction_services.setNoint(DEFAULT_NOINT);
        freecom_partial_construction_services.setLocation(DEFAULT_LOCATION);
        freecom_partial_construction_services.setReference(DEFAULT_REFERENCE);
        freecom_partial_construction_services.setNumperlicoaut(DEFAULT_NUMPERLICOAUT);
    }

    @Test
    @Transactional
    public void createFreecom_partial_construction_services() throws Exception {
        int databaseSizeBeforeCreate = freecom_partial_construction_servicesRepository.findAll().size();

        // Create the Freecom_partial_construction_services

        restFreecom_partial_construction_servicesMockMvc.perform(post("/api/freecom-partial-construction-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_partial_construction_services)))
                .andExpect(status().isCreated());

        // Validate the Freecom_partial_construction_services in the database
        List<Freecom_partial_construction_services> freecom_partial_construction_services = freecom_partial_construction_servicesRepository.findAll();
        assertThat(freecom_partial_construction_services).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_partial_construction_services testFreecom_partial_construction_services = freecom_partial_construction_services.get(freecom_partial_construction_services.size() - 1);
        assertThat(testFreecom_partial_construction_services.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_partial_construction_services.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testFreecom_partial_construction_services.getNoext()).isEqualTo(DEFAULT_NOEXT);
        assertThat(testFreecom_partial_construction_services.getNoint()).isEqualTo(DEFAULT_NOINT);
        assertThat(testFreecom_partial_construction_services.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testFreecom_partial_construction_services.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testFreecom_partial_construction_services.getNumperlicoaut()).isEqualTo(DEFAULT_NUMPERLICOAUT);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_partial_construction_servicesRepository.findAll().size();
        // set the field null
        freecom_partial_construction_services.setVersion(null);

        // Create the Freecom_partial_construction_services, which fails.

        restFreecom_partial_construction_servicesMockMvc.perform(post("/api/freecom-partial-construction-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_partial_construction_services)))
                .andExpect(status().isBadRequest());

        List<Freecom_partial_construction_services> freecom_partial_construction_services = freecom_partial_construction_servicesRepository.findAll();
        assertThat(freecom_partial_construction_services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_partial_construction_servicesRepository.findAll().size();
        // set the field null
        freecom_partial_construction_services.setStreet(null);

        // Create the Freecom_partial_construction_services, which fails.

        restFreecom_partial_construction_servicesMockMvc.perform(post("/api/freecom-partial-construction-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_partial_construction_services)))
                .andExpect(status().isBadRequest());

        List<Freecom_partial_construction_services> freecom_partial_construction_services = freecom_partial_construction_servicesRepository.findAll();
        assertThat(freecom_partial_construction_services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumperlicoautIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_partial_construction_servicesRepository.findAll().size();
        // set the field null
        freecom_partial_construction_services.setNumperlicoaut(null);

        // Create the Freecom_partial_construction_services, which fails.

        restFreecom_partial_construction_servicesMockMvc.perform(post("/api/freecom-partial-construction-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_partial_construction_services)))
                .andExpect(status().isBadRequest());

        List<Freecom_partial_construction_services> freecom_partial_construction_services = freecom_partial_construction_servicesRepository.findAll();
        assertThat(freecom_partial_construction_services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_partial_construction_services() throws Exception {
        // Initialize the database
        freecom_partial_construction_servicesRepository.saveAndFlush(freecom_partial_construction_services);

        // Get all the freecom_partial_construction_services
        restFreecom_partial_construction_servicesMockMvc.perform(get("/api/freecom-partial-construction-services?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_partial_construction_services.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].noext").value(hasItem(DEFAULT_NOEXT.toString())))
                .andExpect(jsonPath("$.[*].noint").value(hasItem(DEFAULT_NOINT.toString())))
                .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].numperlicoaut").value(hasItem(DEFAULT_NUMPERLICOAUT.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_partial_construction_services() throws Exception {
        // Initialize the database
        freecom_partial_construction_servicesRepository.saveAndFlush(freecom_partial_construction_services);

        // Get the freecom_partial_construction_services
        restFreecom_partial_construction_servicesMockMvc.perform(get("/api/freecom-partial-construction-services/{id}", freecom_partial_construction_services.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_partial_construction_services.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.noext").value(DEFAULT_NOEXT.toString()))
            .andExpect(jsonPath("$.noint").value(DEFAULT_NOINT.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.numperlicoaut").value(DEFAULT_NUMPERLICOAUT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_partial_construction_services() throws Exception {
        // Get the freecom_partial_construction_services
        restFreecom_partial_construction_servicesMockMvc.perform(get("/api/freecom-partial-construction-services/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_partial_construction_services() throws Exception {
        // Initialize the database
        freecom_partial_construction_servicesService.save(freecom_partial_construction_services);

        int databaseSizeBeforeUpdate = freecom_partial_construction_servicesRepository.findAll().size();

        // Update the freecom_partial_construction_services
        Freecom_partial_construction_services updatedFreecom_partial_construction_services = new Freecom_partial_construction_services();
        updatedFreecom_partial_construction_services.setId(freecom_partial_construction_services.getId());
        updatedFreecom_partial_construction_services.setVersion(UPDATED_VERSION);
        updatedFreecom_partial_construction_services.setStreet(UPDATED_STREET);
        updatedFreecom_partial_construction_services.setNoext(UPDATED_NOEXT);
        updatedFreecom_partial_construction_services.setNoint(UPDATED_NOINT);
        updatedFreecom_partial_construction_services.setLocation(UPDATED_LOCATION);
        updatedFreecom_partial_construction_services.setReference(UPDATED_REFERENCE);
        updatedFreecom_partial_construction_services.setNumperlicoaut(UPDATED_NUMPERLICOAUT);

        restFreecom_partial_construction_servicesMockMvc.perform(put("/api/freecom-partial-construction-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_partial_construction_services)))
                .andExpect(status().isOk());

        // Validate the Freecom_partial_construction_services in the database
        List<Freecom_partial_construction_services> freecom_partial_construction_services = freecom_partial_construction_servicesRepository.findAll();
        assertThat(freecom_partial_construction_services).hasSize(databaseSizeBeforeUpdate);
        Freecom_partial_construction_services testFreecom_partial_construction_services = freecom_partial_construction_services.get(freecom_partial_construction_services.size() - 1);
        assertThat(testFreecom_partial_construction_services.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_partial_construction_services.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testFreecom_partial_construction_services.getNoext()).isEqualTo(UPDATED_NOEXT);
        assertThat(testFreecom_partial_construction_services.getNoint()).isEqualTo(UPDATED_NOINT);
        assertThat(testFreecom_partial_construction_services.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testFreecom_partial_construction_services.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testFreecom_partial_construction_services.getNumperlicoaut()).isEqualTo(UPDATED_NUMPERLICOAUT);
    }

    @Test
    @Transactional
    public void deleteFreecom_partial_construction_services() throws Exception {
        // Initialize the database
        freecom_partial_construction_servicesService.save(freecom_partial_construction_services);

        int databaseSizeBeforeDelete = freecom_partial_construction_servicesRepository.findAll().size();

        // Get the freecom_partial_construction_services
        restFreecom_partial_construction_servicesMockMvc.perform(delete("/api/freecom-partial-construction-services/{id}", freecom_partial_construction_services.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_partial_construction_services> freecom_partial_construction_services = freecom_partial_construction_servicesRepository.findAll();
        assertThat(freecom_partial_construction_services).hasSize(databaseSizeBeforeDelete - 1);
    }
}
