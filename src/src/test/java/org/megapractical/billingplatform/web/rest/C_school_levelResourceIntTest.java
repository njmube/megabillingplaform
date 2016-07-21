package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_school_level;
import org.megapractical.billingplatform.repository.C_school_levelRepository;
import org.megapractical.billingplatform.service.C_school_levelService;

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
 * Test class for the C_school_levelResource REST controller.
 *
 * @see C_school_levelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_school_levelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_school_levelRepository c_school_levelRepository;

    @Inject
    private C_school_levelService c_school_levelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_school_levelMockMvc;

    private C_school_level c_school_level;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_school_levelResource c_school_levelResource = new C_school_levelResource();
        ReflectionTestUtils.setField(c_school_levelResource, "c_school_levelService", c_school_levelService);
        this.restC_school_levelMockMvc = MockMvcBuilders.standaloneSetup(c_school_levelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_school_level = new C_school_level();
        c_school_level.setName(DEFAULT_NAME);
        c_school_level.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_school_level() throws Exception {
        int databaseSizeBeforeCreate = c_school_levelRepository.findAll().size();

        // Create the C_school_level

        restC_school_levelMockMvc.perform(post("/api/c-school-levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_school_level)))
                .andExpect(status().isCreated());

        // Validate the C_school_level in the database
        List<C_school_level> c_school_levels = c_school_levelRepository.findAll();
        assertThat(c_school_levels).hasSize(databaseSizeBeforeCreate + 1);
        C_school_level testC_school_level = c_school_levels.get(c_school_levels.size() - 1);
        assertThat(testC_school_level.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_school_level.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_school_levelRepository.findAll().size();
        // set the field null
        c_school_level.setName(null);

        // Create the C_school_level, which fails.

        restC_school_levelMockMvc.perform(post("/api/c-school-levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_school_level)))
                .andExpect(status().isBadRequest());

        List<C_school_level> c_school_levels = c_school_levelRepository.findAll();
        assertThat(c_school_levels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_school_levels() throws Exception {
        // Initialize the database
        c_school_levelRepository.saveAndFlush(c_school_level);

        // Get all the c_school_levels
        restC_school_levelMockMvc.perform(get("/api/c-school-levels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_school_level.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_school_level() throws Exception {
        // Initialize the database
        c_school_levelRepository.saveAndFlush(c_school_level);

        // Get the c_school_level
        restC_school_levelMockMvc.perform(get("/api/c-school-levels/{id}", c_school_level.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_school_level.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_school_level() throws Exception {
        // Get the c_school_level
        restC_school_levelMockMvc.perform(get("/api/c-school-levels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_school_level() throws Exception {
        // Initialize the database
        c_school_levelService.save(c_school_level);

        int databaseSizeBeforeUpdate = c_school_levelRepository.findAll().size();

        // Update the c_school_level
        C_school_level updatedC_school_level = new C_school_level();
        updatedC_school_level.setId(c_school_level.getId());
        updatedC_school_level.setName(UPDATED_NAME);
        updatedC_school_level.setDescription(UPDATED_DESCRIPTION);

        restC_school_levelMockMvc.perform(put("/api/c-school-levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_school_level)))
                .andExpect(status().isOk());

        // Validate the C_school_level in the database
        List<C_school_level> c_school_levels = c_school_levelRepository.findAll();
        assertThat(c_school_levels).hasSize(databaseSizeBeforeUpdate);
        C_school_level testC_school_level = c_school_levels.get(c_school_levels.size() - 1);
        assertThat(testC_school_level.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_school_level.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_school_level() throws Exception {
        // Initialize the database
        c_school_levelService.save(c_school_level);

        int databaseSizeBeforeDelete = c_school_levelRepository.findAll().size();

        // Get the c_school_level
        restC_school_levelMockMvc.perform(delete("/api/c-school-levels/{id}", c_school_level.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_school_level> c_school_levels = c_school_levelRepository.findAll();
        assertThat(c_school_levels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
