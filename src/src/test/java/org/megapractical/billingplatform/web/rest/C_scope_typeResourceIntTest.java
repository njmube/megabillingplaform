package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_scope_type;
import org.megapractical.billingplatform.repository.C_scope_typeRepository;
import org.megapractical.billingplatform.service.C_scope_typeService;

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
 * Test class for the C_scope_typeResource REST controller.
 *
 * @see C_scope_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_scope_typeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_scope_typeRepository c_scope_typeRepository;

    @Inject
    private C_scope_typeService c_scope_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_scope_typeMockMvc;

    private C_scope_type c_scope_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_scope_typeResource c_scope_typeResource = new C_scope_typeResource();
        ReflectionTestUtils.setField(c_scope_typeResource, "c_scope_typeService", c_scope_typeService);
        this.restC_scope_typeMockMvc = MockMvcBuilders.standaloneSetup(c_scope_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_scope_type = new C_scope_type();
        c_scope_type.setName(DEFAULT_NAME);
        c_scope_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_scope_type() throws Exception {
        int databaseSizeBeforeCreate = c_scope_typeRepository.findAll().size();

        // Create the C_scope_type

        restC_scope_typeMockMvc.perform(post("/api/c-scope-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_scope_type)))
                .andExpect(status().isCreated());

        // Validate the C_scope_type in the database
        List<C_scope_type> c_scope_types = c_scope_typeRepository.findAll();
        assertThat(c_scope_types).hasSize(databaseSizeBeforeCreate + 1);
        C_scope_type testC_scope_type = c_scope_types.get(c_scope_types.size() - 1);
        assertThat(testC_scope_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_scope_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_scope_typeRepository.findAll().size();
        // set the field null
        c_scope_type.setName(null);

        // Create the C_scope_type, which fails.

        restC_scope_typeMockMvc.perform(post("/api/c-scope-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_scope_type)))
                .andExpect(status().isBadRequest());

        List<C_scope_type> c_scope_types = c_scope_typeRepository.findAll();
        assertThat(c_scope_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_scope_types() throws Exception {
        // Initialize the database
        c_scope_typeRepository.saveAndFlush(c_scope_type);

        // Get all the c_scope_types
        restC_scope_typeMockMvc.perform(get("/api/c-scope-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_scope_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_scope_type() throws Exception {
        // Initialize the database
        c_scope_typeRepository.saveAndFlush(c_scope_type);

        // Get the c_scope_type
        restC_scope_typeMockMvc.perform(get("/api/c-scope-types/{id}", c_scope_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_scope_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_scope_type() throws Exception {
        // Get the c_scope_type
        restC_scope_typeMockMvc.perform(get("/api/c-scope-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_scope_type() throws Exception {
        // Initialize the database
        c_scope_typeService.save(c_scope_type);

        int databaseSizeBeforeUpdate = c_scope_typeRepository.findAll().size();

        // Update the c_scope_type
        C_scope_type updatedC_scope_type = new C_scope_type();
        updatedC_scope_type.setId(c_scope_type.getId());
        updatedC_scope_type.setName(UPDATED_NAME);
        updatedC_scope_type.setDescription(UPDATED_DESCRIPTION);

        restC_scope_typeMockMvc.perform(put("/api/c-scope-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_scope_type)))
                .andExpect(status().isOk());

        // Validate the C_scope_type in the database
        List<C_scope_type> c_scope_types = c_scope_typeRepository.findAll();
        assertThat(c_scope_types).hasSize(databaseSizeBeforeUpdate);
        C_scope_type testC_scope_type = c_scope_types.get(c_scope_types.size() - 1);
        assertThat(testC_scope_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_scope_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_scope_type() throws Exception {
        // Initialize the database
        c_scope_typeService.save(c_scope_type);

        int databaseSizeBeforeDelete = c_scope_typeRepository.findAll().size();

        // Get the c_scope_type
        restC_scope_typeMockMvc.perform(delete("/api/c-scope-types/{id}", c_scope_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_scope_type> c_scope_types = c_scope_typeRepository.findAll();
        assertThat(c_scope_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
