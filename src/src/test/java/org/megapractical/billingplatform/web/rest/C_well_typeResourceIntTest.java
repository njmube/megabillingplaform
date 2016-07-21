package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_well_type;
import org.megapractical.billingplatform.repository.C_well_typeRepository;
import org.megapractical.billingplatform.service.C_well_typeService;

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
 * Test class for the C_well_typeResource REST controller.
 *
 * @see C_well_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_well_typeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private C_well_typeRepository c_well_typeRepository;

    @Inject
    private C_well_typeService c_well_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_well_typeMockMvc;

    private C_well_type c_well_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_well_typeResource c_well_typeResource = new C_well_typeResource();
        ReflectionTestUtils.setField(c_well_typeResource, "c_well_typeService", c_well_typeService);
        this.restC_well_typeMockMvc = MockMvcBuilders.standaloneSetup(c_well_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_well_type = new C_well_type();
        c_well_type.setCode(DEFAULT_CODE);
        c_well_type.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createC_well_type() throws Exception {
        int databaseSizeBeforeCreate = c_well_typeRepository.findAll().size();

        // Create the C_well_type

        restC_well_typeMockMvc.perform(post("/api/c-well-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_well_type)))
                .andExpect(status().isCreated());

        // Validate the C_well_type in the database
        List<C_well_type> c_well_types = c_well_typeRepository.findAll();
        assertThat(c_well_types).hasSize(databaseSizeBeforeCreate + 1);
        C_well_type testC_well_type = c_well_types.get(c_well_types.size() - 1);
        assertThat(testC_well_type.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testC_well_type.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_well_typeRepository.findAll().size();
        // set the field null
        c_well_type.setCode(null);

        // Create the C_well_type, which fails.

        restC_well_typeMockMvc.perform(post("/api/c-well-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_well_type)))
                .andExpect(status().isBadRequest());

        List<C_well_type> c_well_types = c_well_typeRepository.findAll();
        assertThat(c_well_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_well_typeRepository.findAll().size();
        // set the field null
        c_well_type.setName(null);

        // Create the C_well_type, which fails.

        restC_well_typeMockMvc.perform(post("/api/c-well-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_well_type)))
                .andExpect(status().isBadRequest());

        List<C_well_type> c_well_types = c_well_typeRepository.findAll();
        assertThat(c_well_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_well_types() throws Exception {
        // Initialize the database
        c_well_typeRepository.saveAndFlush(c_well_type);

        // Get all the c_well_types
        restC_well_typeMockMvc.perform(get("/api/c-well-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_well_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getC_well_type() throws Exception {
        // Initialize the database
        c_well_typeRepository.saveAndFlush(c_well_type);

        // Get the c_well_type
        restC_well_typeMockMvc.perform(get("/api/c-well-types/{id}", c_well_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_well_type.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_well_type() throws Exception {
        // Get the c_well_type
        restC_well_typeMockMvc.perform(get("/api/c-well-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_well_type() throws Exception {
        // Initialize the database
        c_well_typeService.save(c_well_type);

        int databaseSizeBeforeUpdate = c_well_typeRepository.findAll().size();

        // Update the c_well_type
        C_well_type updatedC_well_type = new C_well_type();
        updatedC_well_type.setId(c_well_type.getId());
        updatedC_well_type.setCode(UPDATED_CODE);
        updatedC_well_type.setName(UPDATED_NAME);

        restC_well_typeMockMvc.perform(put("/api/c-well-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_well_type)))
                .andExpect(status().isOk());

        // Validate the C_well_type in the database
        List<C_well_type> c_well_types = c_well_typeRepository.findAll();
        assertThat(c_well_types).hasSize(databaseSizeBeforeUpdate);
        C_well_type testC_well_type = c_well_types.get(c_well_types.size() - 1);
        assertThat(testC_well_type.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testC_well_type.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteC_well_type() throws Exception {
        // Initialize the database
        c_well_typeService.save(c_well_type);

        int databaseSizeBeforeDelete = c_well_typeRepository.findAll().size();

        // Get the c_well_type
        restC_well_typeMockMvc.perform(delete("/api/c-well-types/{id}", c_well_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_well_type> c_well_types = c_well_typeRepository.findAll();
        assertThat(c_well_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
