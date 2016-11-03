package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_partial_construction_services;
import org.megapractical.billingplatform.repository.Com_partial_construction_servicesRepository;
import org.megapractical.billingplatform.service.Com_partial_construction_servicesService;

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
 * Test class for the Com_partial_construction_servicesResource REST controller.
 *
 * @see Com_partial_construction_servicesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_partial_construction_servicesResourceIntTest {

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
    private Com_partial_construction_servicesRepository com_partial_construction_servicesRepository;

    @Inject
    private Com_partial_construction_servicesService com_partial_construction_servicesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_partial_construction_servicesMockMvc;

    private Com_partial_construction_services com_partial_construction_services;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_partial_construction_servicesResource com_partial_construction_servicesResource = new Com_partial_construction_servicesResource();
        ReflectionTestUtils.setField(com_partial_construction_servicesResource, "com_partial_construction_servicesService", com_partial_construction_servicesService);
        this.restCom_partial_construction_servicesMockMvc = MockMvcBuilders.standaloneSetup(com_partial_construction_servicesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_partial_construction_services = new Com_partial_construction_services();
        com_partial_construction_services.setVersion(DEFAULT_VERSION);
        com_partial_construction_services.setStreet(DEFAULT_STREET);
        com_partial_construction_services.setNoext(DEFAULT_NOEXT);
        com_partial_construction_services.setNoint(DEFAULT_NOINT);
        com_partial_construction_services.setLocation(DEFAULT_LOCATION);
        com_partial_construction_services.setReference(DEFAULT_REFERENCE);
        com_partial_construction_services.setNumperlicoaut(DEFAULT_NUMPERLICOAUT);
    }

    @Test
    @Transactional
    public void createCom_partial_construction_services() throws Exception {
        int databaseSizeBeforeCreate = com_partial_construction_servicesRepository.findAll().size();

        // Create the Com_partial_construction_services

        restCom_partial_construction_servicesMockMvc.perform(post("/api/com-partial-construction-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_partial_construction_services)))
                .andExpect(status().isCreated());

        // Validate the Com_partial_construction_services in the database
        List<Com_partial_construction_services> com_partial_construction_services = com_partial_construction_servicesRepository.findAll();
        assertThat(com_partial_construction_services).hasSize(databaseSizeBeforeCreate + 1);
        Com_partial_construction_services testCom_partial_construction_services = com_partial_construction_services.get(com_partial_construction_services.size() - 1);
        assertThat(testCom_partial_construction_services.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_partial_construction_services.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testCom_partial_construction_services.getNoext()).isEqualTo(DEFAULT_NOEXT);
        assertThat(testCom_partial_construction_services.getNoint()).isEqualTo(DEFAULT_NOINT);
        assertThat(testCom_partial_construction_services.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testCom_partial_construction_services.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testCom_partial_construction_services.getNumperlicoaut()).isEqualTo(DEFAULT_NUMPERLICOAUT);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_partial_construction_servicesRepository.findAll().size();
        // set the field null
        com_partial_construction_services.setVersion(null);

        // Create the Com_partial_construction_services, which fails.

        restCom_partial_construction_servicesMockMvc.perform(post("/api/com-partial-construction-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_partial_construction_services)))
                .andExpect(status().isBadRequest());

        List<Com_partial_construction_services> com_partial_construction_services = com_partial_construction_servicesRepository.findAll();
        assertThat(com_partial_construction_services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_partial_construction_servicesRepository.findAll().size();
        // set the field null
        com_partial_construction_services.setStreet(null);

        // Create the Com_partial_construction_services, which fails.

        restCom_partial_construction_servicesMockMvc.perform(post("/api/com-partial-construction-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_partial_construction_services)))
                .andExpect(status().isBadRequest());

        List<Com_partial_construction_services> com_partial_construction_services = com_partial_construction_servicesRepository.findAll();
        assertThat(com_partial_construction_services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumperlicoautIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_partial_construction_servicesRepository.findAll().size();
        // set the field null
        com_partial_construction_services.setNumperlicoaut(null);

        // Create the Com_partial_construction_services, which fails.

        restCom_partial_construction_servicesMockMvc.perform(post("/api/com-partial-construction-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_partial_construction_services)))
                .andExpect(status().isBadRequest());

        List<Com_partial_construction_services> com_partial_construction_services = com_partial_construction_servicesRepository.findAll();
        assertThat(com_partial_construction_services).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_partial_construction_services() throws Exception {
        // Initialize the database
        com_partial_construction_servicesRepository.saveAndFlush(com_partial_construction_services);

        // Get all the com_partial_construction_services
        restCom_partial_construction_servicesMockMvc.perform(get("/api/com-partial-construction-services?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_partial_construction_services.getId().intValue())))
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
    public void getCom_partial_construction_services() throws Exception {
        // Initialize the database
        com_partial_construction_servicesRepository.saveAndFlush(com_partial_construction_services);

        // Get the com_partial_construction_services
        restCom_partial_construction_servicesMockMvc.perform(get("/api/com-partial-construction-services/{id}", com_partial_construction_services.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_partial_construction_services.getId().intValue()))
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
    public void getNonExistingCom_partial_construction_services() throws Exception {
        // Get the com_partial_construction_services
        restCom_partial_construction_servicesMockMvc.perform(get("/api/com-partial-construction-services/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_partial_construction_services() throws Exception {
        // Initialize the database
        com_partial_construction_servicesService.save(com_partial_construction_services);

        int databaseSizeBeforeUpdate = com_partial_construction_servicesRepository.findAll().size();

        // Update the com_partial_construction_services
        Com_partial_construction_services updatedCom_partial_construction_services = new Com_partial_construction_services();
        updatedCom_partial_construction_services.setId(com_partial_construction_services.getId());
        updatedCom_partial_construction_services.setVersion(UPDATED_VERSION);
        updatedCom_partial_construction_services.setStreet(UPDATED_STREET);
        updatedCom_partial_construction_services.setNoext(UPDATED_NOEXT);
        updatedCom_partial_construction_services.setNoint(UPDATED_NOINT);
        updatedCom_partial_construction_services.setLocation(UPDATED_LOCATION);
        updatedCom_partial_construction_services.setReference(UPDATED_REFERENCE);
        updatedCom_partial_construction_services.setNumperlicoaut(UPDATED_NUMPERLICOAUT);

        restCom_partial_construction_servicesMockMvc.perform(put("/api/com-partial-construction-services")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_partial_construction_services)))
                .andExpect(status().isOk());

        // Validate the Com_partial_construction_services in the database
        List<Com_partial_construction_services> com_partial_construction_services = com_partial_construction_servicesRepository.findAll();
        assertThat(com_partial_construction_services).hasSize(databaseSizeBeforeUpdate);
        Com_partial_construction_services testCom_partial_construction_services = com_partial_construction_services.get(com_partial_construction_services.size() - 1);
        assertThat(testCom_partial_construction_services.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_partial_construction_services.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testCom_partial_construction_services.getNoext()).isEqualTo(UPDATED_NOEXT);
        assertThat(testCom_partial_construction_services.getNoint()).isEqualTo(UPDATED_NOINT);
        assertThat(testCom_partial_construction_services.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testCom_partial_construction_services.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testCom_partial_construction_services.getNumperlicoaut()).isEqualTo(UPDATED_NUMPERLICOAUT);
    }

    @Test
    @Transactional
    public void deleteCom_partial_construction_services() throws Exception {
        // Initialize the database
        com_partial_construction_servicesService.save(com_partial_construction_services);

        int databaseSizeBeforeDelete = com_partial_construction_servicesRepository.findAll().size();

        // Get the com_partial_construction_services
        restCom_partial_construction_servicesMockMvc.perform(delete("/api/com-partial-construction-services/{id}", com_partial_construction_services.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_partial_construction_services> com_partial_construction_services = com_partial_construction_servicesRepository.findAll();
        assertThat(com_partial_construction_services).hasSize(databaseSizeBeforeDelete - 1);
    }
}
