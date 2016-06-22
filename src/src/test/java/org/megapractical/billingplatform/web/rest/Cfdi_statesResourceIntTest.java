package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Cfdi_states;
import org.megapractical.billingplatform.repository.Cfdi_statesRepository;
import org.megapractical.billingplatform.service.Cfdi_statesService;

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
 * Test class for the Cfdi_statesResource REST controller.
 *
 * @see Cfdi_statesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Cfdi_statesResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Cfdi_statesRepository cfdi_statesRepository;

    @Inject
    private Cfdi_statesService cfdi_statesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCfdi_statesMockMvc;

    private Cfdi_states cfdi_states;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cfdi_statesResource cfdi_statesResource = new Cfdi_statesResource();
        ReflectionTestUtils.setField(cfdi_statesResource, "cfdi_statesService", cfdi_statesService);
        this.restCfdi_statesMockMvc = MockMvcBuilders.standaloneSetup(cfdi_statesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cfdi_states = new Cfdi_states();
        cfdi_states.setName(DEFAULT_NAME);
        cfdi_states.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCfdi_states() throws Exception {
        int databaseSizeBeforeCreate = cfdi_statesRepository.findAll().size();

        // Create the Cfdi_states

        restCfdi_statesMockMvc.perform(post("/api/cfdi-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi_states)))
                .andExpect(status().isCreated());

        // Validate the Cfdi_states in the database
        List<Cfdi_states> cfdi_states = cfdi_statesRepository.findAll();
        assertThat(cfdi_states).hasSize(databaseSizeBeforeCreate + 1);
        Cfdi_states testCfdi_states = cfdi_states.get(cfdi_states.size() - 1);
        assertThat(testCfdi_states.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCfdi_states.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfdi_statesRepository.findAll().size();
        // set the field null
        cfdi_states.setName(null);

        // Create the Cfdi_states, which fails.

        restCfdi_statesMockMvc.perform(post("/api/cfdi-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi_states)))
                .andExpect(status().isBadRequest());

        List<Cfdi_states> cfdi_states = cfdi_statesRepository.findAll();
        assertThat(cfdi_states).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCfdi_states() throws Exception {
        // Initialize the database
        cfdi_statesRepository.saveAndFlush(cfdi_states);

        // Get all the cfdi_states
        restCfdi_statesMockMvc.perform(get("/api/cfdi-states?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cfdi_states.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCfdi_states() throws Exception {
        // Initialize the database
        cfdi_statesRepository.saveAndFlush(cfdi_states);

        // Get the cfdi_states
        restCfdi_statesMockMvc.perform(get("/api/cfdi-states/{id}", cfdi_states.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cfdi_states.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCfdi_states() throws Exception {
        // Get the cfdi_states
        restCfdi_statesMockMvc.perform(get("/api/cfdi-states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCfdi_states() throws Exception {
        // Initialize the database
        cfdi_statesService.save(cfdi_states);

        int databaseSizeBeforeUpdate = cfdi_statesRepository.findAll().size();

        // Update the cfdi_states
        Cfdi_states updatedCfdi_states = new Cfdi_states();
        updatedCfdi_states.setId(cfdi_states.getId());
        updatedCfdi_states.setName(UPDATED_NAME);
        updatedCfdi_states.setDescription(UPDATED_DESCRIPTION);

        restCfdi_statesMockMvc.perform(put("/api/cfdi-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCfdi_states)))
                .andExpect(status().isOk());

        // Validate the Cfdi_states in the database
        List<Cfdi_states> cfdi_states = cfdi_statesRepository.findAll();
        assertThat(cfdi_states).hasSize(databaseSizeBeforeUpdate);
        Cfdi_states testCfdi_states = cfdi_states.get(cfdi_states.size() - 1);
        assertThat(testCfdi_states.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCfdi_states.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteCfdi_states() throws Exception {
        // Initialize the database
        cfdi_statesService.save(cfdi_states);

        int databaseSizeBeforeDelete = cfdi_statesRepository.findAll().size();

        // Get the cfdi_states
        restCfdi_statesMockMvc.perform(delete("/api/cfdi-states/{id}", cfdi_states.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cfdi_states> cfdi_states = cfdi_statesRepository.findAll();
        assertThat(cfdi_states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
