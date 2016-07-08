package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Tax_types;
import org.megapractical.billingplatform.repository.Tax_typesRepository;
import org.megapractical.billingplatform.service.Tax_typesService;

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
 * Test class for the Tax_typesResource REST controller.
 *
 * @see Tax_typesResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Tax_typesResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Tax_typesRepository tax_typesRepository;

    @Inject
    private Tax_typesService tax_typesService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTax_typesMockMvc;

    private Tax_types tax_types;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Tax_typesResource tax_typesResource = new Tax_typesResource();
        ReflectionTestUtils.setField(tax_typesResource, "tax_typesService", tax_typesService);
        this.restTax_typesMockMvc = MockMvcBuilders.standaloneSetup(tax_typesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        tax_types = new Tax_types();
        tax_types.setName(DEFAULT_NAME);
        tax_types.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTax_types() throws Exception {
        int databaseSizeBeforeCreate = tax_typesRepository.findAll().size();

        // Create the Tax_types

        restTax_typesMockMvc.perform(post("/api/tax-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_types)))
                .andExpect(status().isCreated());

        // Validate the Tax_types in the database
        List<Tax_types> tax_types = tax_typesRepository.findAll();
        assertThat(tax_types).hasSize(databaseSizeBeforeCreate + 1);
        Tax_types testTax_types = tax_types.get(tax_types.size() - 1);
        assertThat(testTax_types.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTax_types.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tax_typesRepository.findAll().size();
        // set the field null
        tax_types.setName(null);

        // Create the Tax_types, which fails.

        restTax_typesMockMvc.perform(post("/api/tax-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tax_types)))
                .andExpect(status().isBadRequest());

        List<Tax_types> tax_types = tax_typesRepository.findAll();
        assertThat(tax_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTax_types() throws Exception {
        // Initialize the database
        tax_typesRepository.saveAndFlush(tax_types);

        // Get all the tax_types
        restTax_typesMockMvc.perform(get("/api/tax-types?sort=id,desc&filtername= "))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tax_types.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTax_types() throws Exception {
        // Initialize the database
        tax_typesRepository.saveAndFlush(tax_types);

        // Get the tax_types
        restTax_typesMockMvc.perform(get("/api/tax-types/{id}", tax_types.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tax_types.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTax_types() throws Exception {
        // Get the tax_types
        restTax_typesMockMvc.perform(get("/api/tax-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTax_types() throws Exception {
        // Initialize the database
        tax_typesService.save(tax_types);

        int databaseSizeBeforeUpdate = tax_typesRepository.findAll().size();

        // Update the tax_types
        Tax_types updatedTax_types = new Tax_types();
        updatedTax_types.setId(tax_types.getId());
        updatedTax_types.setName(UPDATED_NAME);
        updatedTax_types.setDescription(UPDATED_DESCRIPTION);

        restTax_typesMockMvc.perform(put("/api/tax-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTax_types)))
                .andExpect(status().isOk());

        // Validate the Tax_types in the database
        List<Tax_types> tax_types = tax_typesRepository.findAll();
        assertThat(tax_types).hasSize(databaseSizeBeforeUpdate);
        Tax_types testTax_types = tax_types.get(tax_types.size() - 1);
        assertThat(testTax_types.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTax_types.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteTax_types() throws Exception {
        // Initialize the database
        tax_typesService.save(tax_types);

        int databaseSizeBeforeDelete = tax_typesRepository.findAll().size();

        // Get the tax_types
        restTax_typesMockMvc.perform(delete("/api/tax-types/{id}", tax_types.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Tax_types> tax_types = tax_typesRepository.findAll();
        assertThat(tax_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
