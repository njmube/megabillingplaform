package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_transit_type;
import org.megapractical.billingplatform.repository.C_transit_typeRepository;
import org.megapractical.billingplatform.service.C_transit_typeService;

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
 * Test class for the C_transit_typeResource REST controller.
 *
 * @see C_transit_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_transit_typeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_transit_typeRepository c_transit_typeRepository;

    @Inject
    private C_transit_typeService c_transit_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_transit_typeMockMvc;

    private C_transit_type c_transit_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_transit_typeResource c_transit_typeResource = new C_transit_typeResource();
        ReflectionTestUtils.setField(c_transit_typeResource, "c_transit_typeService", c_transit_typeService);
        this.restC_transit_typeMockMvc = MockMvcBuilders.standaloneSetup(c_transit_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_transit_type = new C_transit_type();
        c_transit_type.setName(DEFAULT_NAME);
        c_transit_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_transit_type() throws Exception {
        int databaseSizeBeforeCreate = c_transit_typeRepository.findAll().size();

        // Create the C_transit_type

        restC_transit_typeMockMvc.perform(post("/api/c-transit-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_transit_type)))
                .andExpect(status().isCreated());

        // Validate the C_transit_type in the database
        List<C_transit_type> c_transit_types = c_transit_typeRepository.findAll();
        assertThat(c_transit_types).hasSize(databaseSizeBeforeCreate + 1);
        C_transit_type testC_transit_type = c_transit_types.get(c_transit_types.size() - 1);
        assertThat(testC_transit_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_transit_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_transit_typeRepository.findAll().size();
        // set the field null
        c_transit_type.setName(null);

        // Create the C_transit_type, which fails.

        restC_transit_typeMockMvc.perform(post("/api/c-transit-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_transit_type)))
                .andExpect(status().isBadRequest());

        List<C_transit_type> c_transit_types = c_transit_typeRepository.findAll();
        assertThat(c_transit_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_transit_types() throws Exception {
        // Initialize the database
        c_transit_typeRepository.saveAndFlush(c_transit_type);

        // Get all the c_transit_types
        restC_transit_typeMockMvc.perform(get("/api/c-transit-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_transit_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_transit_type() throws Exception {
        // Initialize the database
        c_transit_typeRepository.saveAndFlush(c_transit_type);

        // Get the c_transit_type
        restC_transit_typeMockMvc.perform(get("/api/c-transit-types/{id}", c_transit_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_transit_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_transit_type() throws Exception {
        // Get the c_transit_type
        restC_transit_typeMockMvc.perform(get("/api/c-transit-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_transit_type() throws Exception {
        // Initialize the database
        c_transit_typeService.save(c_transit_type);

        int databaseSizeBeforeUpdate = c_transit_typeRepository.findAll().size();

        // Update the c_transit_type
        C_transit_type updatedC_transit_type = new C_transit_type();
        updatedC_transit_type.setId(c_transit_type.getId());
        updatedC_transit_type.setName(UPDATED_NAME);
        updatedC_transit_type.setDescription(UPDATED_DESCRIPTION);

        restC_transit_typeMockMvc.perform(put("/api/c-transit-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_transit_type)))
                .andExpect(status().isOk());

        // Validate the C_transit_type in the database
        List<C_transit_type> c_transit_types = c_transit_typeRepository.findAll();
        assertThat(c_transit_types).hasSize(databaseSizeBeforeUpdate);
        C_transit_type testC_transit_type = c_transit_types.get(c_transit_types.size() - 1);
        assertThat(testC_transit_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_transit_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_transit_type() throws Exception {
        // Initialize the database
        c_transit_typeService.save(c_transit_type);

        int databaseSizeBeforeDelete = c_transit_typeRepository.findAll().size();

        // Get the c_transit_type
        restC_transit_typeMockMvc.perform(delete("/api/c-transit-types/{id}", c_transit_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_transit_type> c_transit_types = c_transit_typeRepository.findAll();
        assertThat(c_transit_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
