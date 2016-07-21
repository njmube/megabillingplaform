package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_committee_type;
import org.megapractical.billingplatform.repository.C_committee_typeRepository;
import org.megapractical.billingplatform.service.C_committee_typeService;

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
 * Test class for the C_committee_typeResource REST controller.
 *
 * @see C_committee_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_committee_typeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_committee_typeRepository c_committee_typeRepository;

    @Inject
    private C_committee_typeService c_committee_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_committee_typeMockMvc;

    private C_committee_type c_committee_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_committee_typeResource c_committee_typeResource = new C_committee_typeResource();
        ReflectionTestUtils.setField(c_committee_typeResource, "c_committee_typeService", c_committee_typeService);
        this.restC_committee_typeMockMvc = MockMvcBuilders.standaloneSetup(c_committee_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_committee_type = new C_committee_type();
        c_committee_type.setName(DEFAULT_NAME);
        c_committee_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_committee_type() throws Exception {
        int databaseSizeBeforeCreate = c_committee_typeRepository.findAll().size();

        // Create the C_committee_type

        restC_committee_typeMockMvc.perform(post("/api/c-committee-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_committee_type)))
                .andExpect(status().isCreated());

        // Validate the C_committee_type in the database
        List<C_committee_type> c_committee_types = c_committee_typeRepository.findAll();
        assertThat(c_committee_types).hasSize(databaseSizeBeforeCreate + 1);
        C_committee_type testC_committee_type = c_committee_types.get(c_committee_types.size() - 1);
        assertThat(testC_committee_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_committee_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_committee_typeRepository.findAll().size();
        // set the field null
        c_committee_type.setName(null);

        // Create the C_committee_type, which fails.

        restC_committee_typeMockMvc.perform(post("/api/c-committee-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_committee_type)))
                .andExpect(status().isBadRequest());

        List<C_committee_type> c_committee_types = c_committee_typeRepository.findAll();
        assertThat(c_committee_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_committee_types() throws Exception {
        // Initialize the database
        c_committee_typeRepository.saveAndFlush(c_committee_type);

        // Get all the c_committee_types
        restC_committee_typeMockMvc.perform(get("/api/c-committee-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_committee_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_committee_type() throws Exception {
        // Initialize the database
        c_committee_typeRepository.saveAndFlush(c_committee_type);

        // Get the c_committee_type
        restC_committee_typeMockMvc.perform(get("/api/c-committee-types/{id}", c_committee_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_committee_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_committee_type() throws Exception {
        // Get the c_committee_type
        restC_committee_typeMockMvc.perform(get("/api/c-committee-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_committee_type() throws Exception {
        // Initialize the database
        c_committee_typeService.save(c_committee_type);

        int databaseSizeBeforeUpdate = c_committee_typeRepository.findAll().size();

        // Update the c_committee_type
        C_committee_type updatedC_committee_type = new C_committee_type();
        updatedC_committee_type.setId(c_committee_type.getId());
        updatedC_committee_type.setName(UPDATED_NAME);
        updatedC_committee_type.setDescription(UPDATED_DESCRIPTION);

        restC_committee_typeMockMvc.perform(put("/api/c-committee-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_committee_type)))
                .andExpect(status().isOk());

        // Validate the C_committee_type in the database
        List<C_committee_type> c_committee_types = c_committee_typeRepository.findAll();
        assertThat(c_committee_types).hasSize(databaseSizeBeforeUpdate);
        C_committee_type testC_committee_type = c_committee_types.get(c_committee_types.size() - 1);
        assertThat(testC_committee_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_committee_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_committee_type() throws Exception {
        // Initialize the database
        c_committee_typeService.save(c_committee_type);

        int databaseSizeBeforeDelete = c_committee_typeRepository.findAll().size();

        // Get the c_committee_type
        restC_committee_typeMockMvc.perform(delete("/api/c-committee-types/{id}", c_committee_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_committee_type> c_committee_types = c_committee_typeRepository.findAll();
        assertThat(c_committee_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
