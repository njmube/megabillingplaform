package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_tax_type;
import org.megapractical.billingplatform.repository.Freecom_tax_typeRepository;
import org.megapractical.billingplatform.service.Freecom_tax_typeService;

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
 * Test class for the Freecom_tax_typeResource REST controller.
 *
 * @see Freecom_tax_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_tax_typeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Freecom_tax_typeRepository freecom_tax_typeRepository;

    @Inject
    private Freecom_tax_typeService freecom_tax_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_tax_typeMockMvc;

    private Freecom_tax_type freecom_tax_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_tax_typeResource freecom_tax_typeResource = new Freecom_tax_typeResource();
        ReflectionTestUtils.setField(freecom_tax_typeResource, "freecom_tax_typeService", freecom_tax_typeService);
        this.restFreecom_tax_typeMockMvc = MockMvcBuilders.standaloneSetup(freecom_tax_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_tax_type = new Freecom_tax_type();
        freecom_tax_type.setName(DEFAULT_NAME);
        freecom_tax_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFreecom_tax_type() throws Exception {
        int databaseSizeBeforeCreate = freecom_tax_typeRepository.findAll().size();

        // Create the Freecom_tax_type

        restFreecom_tax_typeMockMvc.perform(post("/api/freecom-tax-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tax_type)))
                .andExpect(status().isCreated());

        // Validate the Freecom_tax_type in the database
        List<Freecom_tax_type> freecom_tax_types = freecom_tax_typeRepository.findAll();
        assertThat(freecom_tax_types).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_tax_type testFreecom_tax_type = freecom_tax_types.get(freecom_tax_types.size() - 1);
        assertThat(testFreecom_tax_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFreecom_tax_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_tax_typeRepository.findAll().size();
        // set the field null
        freecom_tax_type.setName(null);

        // Create the Freecom_tax_type, which fails.

        restFreecom_tax_typeMockMvc.perform(post("/api/freecom-tax-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_tax_type)))
                .andExpect(status().isBadRequest());

        List<Freecom_tax_type> freecom_tax_types = freecom_tax_typeRepository.findAll();
        assertThat(freecom_tax_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_tax_types() throws Exception {
        // Initialize the database
        freecom_tax_typeRepository.saveAndFlush(freecom_tax_type);

        // Get all the freecom_tax_types
        restFreecom_tax_typeMockMvc.perform(get("/api/freecom-tax-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_tax_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_tax_type() throws Exception {
        // Initialize the database
        freecom_tax_typeRepository.saveAndFlush(freecom_tax_type);

        // Get the freecom_tax_type
        restFreecom_tax_typeMockMvc.perform(get("/api/freecom-tax-types/{id}", freecom_tax_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_tax_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_tax_type() throws Exception {
        // Get the freecom_tax_type
        restFreecom_tax_typeMockMvc.perform(get("/api/freecom-tax-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_tax_type() throws Exception {
        // Initialize the database
        freecom_tax_typeService.save(freecom_tax_type);

        int databaseSizeBeforeUpdate = freecom_tax_typeRepository.findAll().size();

        // Update the freecom_tax_type
        Freecom_tax_type updatedFreecom_tax_type = new Freecom_tax_type();
        updatedFreecom_tax_type.setId(freecom_tax_type.getId());
        updatedFreecom_tax_type.setName(UPDATED_NAME);
        updatedFreecom_tax_type.setDescription(UPDATED_DESCRIPTION);

        restFreecom_tax_typeMockMvc.perform(put("/api/freecom-tax-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_tax_type)))
                .andExpect(status().isOk());

        // Validate the Freecom_tax_type in the database
        List<Freecom_tax_type> freecom_tax_types = freecom_tax_typeRepository.findAll();
        assertThat(freecom_tax_types).hasSize(databaseSizeBeforeUpdate);
        Freecom_tax_type testFreecom_tax_type = freecom_tax_types.get(freecom_tax_types.size() - 1);
        assertThat(testFreecom_tax_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFreecom_tax_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteFreecom_tax_type() throws Exception {
        // Initialize the database
        freecom_tax_typeService.save(freecom_tax_type);

        int databaseSizeBeforeDelete = freecom_tax_typeRepository.findAll().size();

        // Get the freecom_tax_type
        restFreecom_tax_typeMockMvc.perform(delete("/api/freecom-tax-types/{id}", freecom_tax_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_tax_type> freecom_tax_types = freecom_tax_typeRepository.findAll();
        assertThat(freecom_tax_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
