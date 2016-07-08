package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Cfdi_types;
import org.megapractical.billingplatform.repository.Cfdi_typesRepository;
import org.megapractical.billingplatform.service.Cfdi_typesService;

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
 * Test class for the Cfdi_typesResource REST controller.
 *
 * @see Cfdi_typesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Cfdi_typesResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Cfdi_typesRepository cfdi_typesRepository;

    @Inject
    private Cfdi_typesService cfdi_typesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCfdi_typesMockMvc;

    private Cfdi_types cfdi_types;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cfdi_typesResource cfdi_typesResource = new Cfdi_typesResource();
        ReflectionTestUtils.setField(cfdi_typesResource, "cfdi_typesService", cfdi_typesService);
        this.restCfdi_typesMockMvc = MockMvcBuilders.standaloneSetup(cfdi_typesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cfdi_types = new Cfdi_types();
        cfdi_types.setName(DEFAULT_NAME);
        cfdi_types.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCfdi_types() throws Exception {
        int databaseSizeBeforeCreate = cfdi_typesRepository.findAll().size();

        // Create the Cfdi_types

        restCfdi_typesMockMvc.perform(post("/api/cfdi-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi_types)))
                .andExpect(status().isCreated());

        // Validate the Cfdi_types in the database
        List<Cfdi_types> cfdi_types = cfdi_typesRepository.findAll();
        assertThat(cfdi_types).hasSize(databaseSizeBeforeCreate + 1);
        Cfdi_types testCfdi_types = cfdi_types.get(cfdi_types.size() - 1);
        assertThat(testCfdi_types.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCfdi_types.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfdi_typesRepository.findAll().size();
        // set the field null
        cfdi_types.setName(null);

        // Create the Cfdi_types, which fails.

        restCfdi_typesMockMvc.perform(post("/api/cfdi-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi_types)))
                .andExpect(status().isBadRequest());

        List<Cfdi_types> cfdi_types = cfdi_typesRepository.findAll();
        assertThat(cfdi_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCfdi_types() throws Exception {
        // Initialize the database
        cfdi_typesRepository.saveAndFlush(cfdi_types);

        // Get all the cfdi_types
        restCfdi_typesMockMvc.perform(get("/api/cfdi-types?sort=id,desc&filtername= "))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cfdi_types.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCfdi_types() throws Exception {
        // Initialize the database
        cfdi_typesRepository.saveAndFlush(cfdi_types);

        // Get the cfdi_types
        restCfdi_typesMockMvc.perform(get("/api/cfdi-types/{id}", cfdi_types.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cfdi_types.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCfdi_types() throws Exception {
        // Get the cfdi_types
        restCfdi_typesMockMvc.perform(get("/api/cfdi-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCfdi_types() throws Exception {
        // Initialize the database
        cfdi_typesService.save(cfdi_types);

        int databaseSizeBeforeUpdate = cfdi_typesRepository.findAll().size();

        // Update the cfdi_types
        Cfdi_types updatedCfdi_types = new Cfdi_types();
        updatedCfdi_types.setId(cfdi_types.getId());
        updatedCfdi_types.setName(UPDATED_NAME);
        updatedCfdi_types.setDescription(UPDATED_DESCRIPTION);

        restCfdi_typesMockMvc.perform(put("/api/cfdi-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCfdi_types)))
                .andExpect(status().isOk());

        // Validate the Cfdi_types in the database
        List<Cfdi_types> cfdi_types = cfdi_typesRepository.findAll();
        assertThat(cfdi_types).hasSize(databaseSizeBeforeUpdate);
        Cfdi_types testCfdi_types = cfdi_types.get(cfdi_types.size() - 1);
        assertThat(testCfdi_types.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCfdi_types.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteCfdi_types() throws Exception {
        // Initialize the database
        cfdi_typesService.save(cfdi_types);

        int databaseSizeBeforeDelete = cfdi_typesRepository.findAll().size();

        // Get the cfdi_types
        restCfdi_typesMockMvc.perform(delete("/api/cfdi-types/{id}", cfdi_types.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cfdi_types> cfdi_types = cfdi_typesRepository.findAll();
        assertThat(cfdi_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
