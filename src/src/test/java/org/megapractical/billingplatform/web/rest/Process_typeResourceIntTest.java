package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Process_type;
import org.megapractical.billingplatform.repository.Process_typeRepository;
import org.megapractical.billingplatform.service.Process_typeService;

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
 * Test class for the Process_typeResource REST controller.
 *
 * @see Process_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Process_typeResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Process_typeRepository process_typeRepository;

    @Inject
    private Process_typeService process_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProcess_typeMockMvc;

    private Process_type process_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Process_typeResource process_typeResource = new Process_typeResource();
        ReflectionTestUtils.setField(process_typeResource, "process_typeService", process_typeService);
        this.restProcess_typeMockMvc = MockMvcBuilders.standaloneSetup(process_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        process_type = new Process_type();
        process_type.setName(DEFAULT_NAME);
        process_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProcess_type() throws Exception {
        int databaseSizeBeforeCreate = process_typeRepository.findAll().size();

        // Create the Process_type

        restProcess_typeMockMvc.perform(post("/api/process-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(process_type)))
                .andExpect(status().isCreated());

        // Validate the Process_type in the database
        List<Process_type> process_types = process_typeRepository.findAll();
        assertThat(process_types).hasSize(databaseSizeBeforeCreate + 1);
        Process_type testProcess_type = process_types.get(process_types.size() - 1);
        assertThat(testProcess_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProcess_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = process_typeRepository.findAll().size();
        // set the field null
        process_type.setName(null);

        // Create the Process_type, which fails.

        restProcess_typeMockMvc.perform(post("/api/process-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(process_type)))
                .andExpect(status().isBadRequest());

        List<Process_type> process_types = process_typeRepository.findAll();
        assertThat(process_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcess_types() throws Exception {
        // Initialize the database
        process_typeRepository.saveAndFlush(process_type);

        // Get all the process_types
        restProcess_typeMockMvc.perform(get("/api/process-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(process_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getProcess_type() throws Exception {
        // Initialize the database
        process_typeRepository.saveAndFlush(process_type);

        // Get the process_type
        restProcess_typeMockMvc.perform(get("/api/process-types/{id}", process_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(process_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProcess_type() throws Exception {
        // Get the process_type
        restProcess_typeMockMvc.perform(get("/api/process-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcess_type() throws Exception {
        // Initialize the database
        process_typeService.save(process_type);

        int databaseSizeBeforeUpdate = process_typeRepository.findAll().size();

        // Update the process_type
        Process_type updatedProcess_type = new Process_type();
        updatedProcess_type.setId(process_type.getId());
        updatedProcess_type.setName(UPDATED_NAME);
        updatedProcess_type.setDescription(UPDATED_DESCRIPTION);

        restProcess_typeMockMvc.perform(put("/api/process-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProcess_type)))
                .andExpect(status().isOk());

        // Validate the Process_type in the database
        List<Process_type> process_types = process_typeRepository.findAll();
        assertThat(process_types).hasSize(databaseSizeBeforeUpdate);
        Process_type testProcess_type = process_types.get(process_types.size() - 1);
        assertThat(testProcess_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcess_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteProcess_type() throws Exception {
        // Initialize the database
        process_typeService.save(process_type);

        int databaseSizeBeforeDelete = process_typeRepository.findAll().size();

        // Get the process_type
        restProcess_typeMockMvc.perform(delete("/api/process-types/{id}", process_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Process_type> process_types = process_typeRepository.findAll();
        assertThat(process_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
