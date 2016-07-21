package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_process_type;
import org.megapractical.billingplatform.repository.C_process_typeRepository;
import org.megapractical.billingplatform.service.C_process_typeService;

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
 * Test class for the C_process_typeResource REST controller.
 *
 * @see C_process_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_process_typeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_process_typeRepository c_process_typeRepository;

    @Inject
    private C_process_typeService c_process_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_process_typeMockMvc;

    private C_process_type c_process_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_process_typeResource c_process_typeResource = new C_process_typeResource();
        ReflectionTestUtils.setField(c_process_typeResource, "c_process_typeService", c_process_typeService);
        this.restC_process_typeMockMvc = MockMvcBuilders.standaloneSetup(c_process_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_process_type = new C_process_type();
        c_process_type.setName(DEFAULT_NAME);
        c_process_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_process_type() throws Exception {
        int databaseSizeBeforeCreate = c_process_typeRepository.findAll().size();

        // Create the C_process_type

        restC_process_typeMockMvc.perform(post("/api/c-process-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_process_type)))
                .andExpect(status().isCreated());

        // Validate the C_process_type in the database
        List<C_process_type> c_process_types = c_process_typeRepository.findAll();
        assertThat(c_process_types).hasSize(databaseSizeBeforeCreate + 1);
        C_process_type testC_process_type = c_process_types.get(c_process_types.size() - 1);
        assertThat(testC_process_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_process_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_process_typeRepository.findAll().size();
        // set the field null
        c_process_type.setName(null);

        // Create the C_process_type, which fails.

        restC_process_typeMockMvc.perform(post("/api/c-process-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_process_type)))
                .andExpect(status().isBadRequest());

        List<C_process_type> c_process_types = c_process_typeRepository.findAll();
        assertThat(c_process_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_process_types() throws Exception {
        // Initialize the database
        c_process_typeRepository.saveAndFlush(c_process_type);

        // Get all the c_process_types
        restC_process_typeMockMvc.perform(get("/api/c-process-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_process_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_process_type() throws Exception {
        // Initialize the database
        c_process_typeRepository.saveAndFlush(c_process_type);

        // Get the c_process_type
        restC_process_typeMockMvc.perform(get("/api/c-process-types/{id}", c_process_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_process_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_process_type() throws Exception {
        // Get the c_process_type
        restC_process_typeMockMvc.perform(get("/api/c-process-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_process_type() throws Exception {
        // Initialize the database
        c_process_typeService.save(c_process_type);

        int databaseSizeBeforeUpdate = c_process_typeRepository.findAll().size();

        // Update the c_process_type
        C_process_type updatedC_process_type = new C_process_type();
        updatedC_process_type.setId(c_process_type.getId());
        updatedC_process_type.setName(UPDATED_NAME);
        updatedC_process_type.setDescription(UPDATED_DESCRIPTION);

        restC_process_typeMockMvc.perform(put("/api/c-process-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_process_type)))
                .andExpect(status().isOk());

        // Validate the C_process_type in the database
        List<C_process_type> c_process_types = c_process_typeRepository.findAll();
        assertThat(c_process_types).hasSize(databaseSizeBeforeUpdate);
        C_process_type testC_process_type = c_process_types.get(c_process_types.size() - 1);
        assertThat(testC_process_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_process_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_process_type() throws Exception {
        // Initialize the database
        c_process_typeService.save(c_process_type);

        int databaseSizeBeforeDelete = c_process_typeRepository.findAll().size();

        // Get the c_process_type
        restC_process_typeMockMvc.perform(delete("/api/c-process-types/{id}", c_process_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_process_type> c_process_types = c_process_typeRepository.findAll();
        assertThat(c_process_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
