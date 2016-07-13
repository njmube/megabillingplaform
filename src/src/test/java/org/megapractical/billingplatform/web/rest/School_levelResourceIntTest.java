package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.School_level;
import org.megapractical.billingplatform.repository.School_levelRepository;
import org.megapractical.billingplatform.service.School_levelService;

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
 * Test class for the School_levelResource REST controller.
 *
 * @see School_levelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class School_levelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private School_levelRepository school_levelRepository;

    @Inject
    private School_levelService school_levelService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSchool_levelMockMvc;

    private School_level school_level;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        School_levelResource school_levelResource = new School_levelResource();
        ReflectionTestUtils.setField(school_levelResource, "school_levelService", school_levelService);
        this.restSchool_levelMockMvc = MockMvcBuilders.standaloneSetup(school_levelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        school_level = new School_level();
        school_level.setName(DEFAULT_NAME);
        school_level.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSchool_level() throws Exception {
        int databaseSizeBeforeCreate = school_levelRepository.findAll().size();

        // Create the School_level

        restSchool_levelMockMvc.perform(post("/api/school-levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(school_level)))
                .andExpect(status().isCreated());

        // Validate the School_level in the database
        List<School_level> school_levels = school_levelRepository.findAll();
        assertThat(school_levels).hasSize(databaseSizeBeforeCreate + 1);
        School_level testSchool_level = school_levels.get(school_levels.size() - 1);
        assertThat(testSchool_level.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSchool_level.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = school_levelRepository.findAll().size();
        // set the field null
        school_level.setName(null);

        // Create the School_level, which fails.

        restSchool_levelMockMvc.perform(post("/api/school-levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(school_level)))
                .andExpect(status().isBadRequest());

        List<School_level> school_levels = school_levelRepository.findAll();
        assertThat(school_levels).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchool_levels() throws Exception {
        // Initialize the database
        school_levelRepository.saveAndFlush(school_level);

        // Get all the school_levels
        restSchool_levelMockMvc.perform(get("/api/school-levels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(school_level.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSchool_level() throws Exception {
        // Initialize the database
        school_levelRepository.saveAndFlush(school_level);

        // Get the school_level
        restSchool_levelMockMvc.perform(get("/api/school-levels/{id}", school_level.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(school_level.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchool_level() throws Exception {
        // Get the school_level
        restSchool_levelMockMvc.perform(get("/api/school-levels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchool_level() throws Exception {
        // Initialize the database
        school_levelService.save(school_level);

        int databaseSizeBeforeUpdate = school_levelRepository.findAll().size();

        // Update the school_level
        School_level updatedSchool_level = new School_level();
        updatedSchool_level.setId(school_level.getId());
        updatedSchool_level.setName(UPDATED_NAME);
        updatedSchool_level.setDescription(UPDATED_DESCRIPTION);

        restSchool_levelMockMvc.perform(put("/api/school-levels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedSchool_level)))
                .andExpect(status().isOk());

        // Validate the School_level in the database
        List<School_level> school_levels = school_levelRepository.findAll();
        assertThat(school_levels).hasSize(databaseSizeBeforeUpdate);
        School_level testSchool_level = school_levels.get(school_levels.size() - 1);
        assertThat(testSchool_level.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSchool_level.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteSchool_level() throws Exception {
        // Initialize the database
        school_levelService.save(school_level);

        int databaseSizeBeforeDelete = school_levelRepository.findAll().size();

        // Get the school_level
        restSchool_levelMockMvc.perform(delete("/api/school-levels/{id}", school_level.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<School_level> school_levels = school_levelRepository.findAll();
        assertThat(school_levels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
