package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.File_state;
import org.megapractical.billingplatform.repository.File_stateRepository;
import org.megapractical.billingplatform.service.File_stateService;

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
 * Test class for the File_stateResource REST controller.
 *
 * @see File_stateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class File_stateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private File_stateRepository file_stateRepository;

    @Inject
    private File_stateService file_stateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFile_stateMockMvc;

    private File_state file_state;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        File_stateResource file_stateResource = new File_stateResource();
        ReflectionTestUtils.setField(file_stateResource, "file_stateService", file_stateService);
        this.restFile_stateMockMvc = MockMvcBuilders.standaloneSetup(file_stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        file_state = new File_state();
        file_state.setName(DEFAULT_NAME);
        file_state.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFile_state() throws Exception {
        int databaseSizeBeforeCreate = file_stateRepository.findAll().size();

        // Create the File_state

        restFile_stateMockMvc.perform(post("/api/file-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(file_state)))
                .andExpect(status().isCreated());

        // Validate the File_state in the database
        List<File_state> file_states = file_stateRepository.findAll();
        assertThat(file_states).hasSize(databaseSizeBeforeCreate + 1);
        File_state testFile_state = file_states.get(file_states.size() - 1);
        assertThat(testFile_state.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFile_state.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFile_states() throws Exception {
        // Initialize the database
        file_stateRepository.saveAndFlush(file_state);

        // Get all the file_states
        restFile_stateMockMvc.perform(get("/api/file-states?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(file_state.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFile_state() throws Exception {
        // Initialize the database
        file_stateRepository.saveAndFlush(file_state);

        // Get the file_state
        restFile_stateMockMvc.perform(get("/api/file-states/{id}", file_state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(file_state.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFile_state() throws Exception {
        // Get the file_state
        restFile_stateMockMvc.perform(get("/api/file-states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFile_state() throws Exception {
        // Initialize the database
        file_stateService.save(file_state);

        int databaseSizeBeforeUpdate = file_stateRepository.findAll().size();

        // Update the file_state
        File_state updatedFile_state = new File_state();
        updatedFile_state.setId(file_state.getId());
        updatedFile_state.setName(UPDATED_NAME);
        updatedFile_state.setDescription(UPDATED_DESCRIPTION);

        restFile_stateMockMvc.perform(put("/api/file-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFile_state)))
                .andExpect(status().isOk());

        // Validate the File_state in the database
        List<File_state> file_states = file_stateRepository.findAll();
        assertThat(file_states).hasSize(databaseSizeBeforeUpdate);
        File_state testFile_state = file_states.get(file_states.size() - 1);
        assertThat(testFile_state.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFile_state.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteFile_state() throws Exception {
        // Initialize the database
        file_stateService.save(file_state);

        int databaseSizeBeforeDelete = file_stateRepository.findAll().size();

        // Get the file_state
        restFile_stateMockMvc.perform(delete("/api/file-states/{id}", file_state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<File_state> file_states = file_stateRepository.findAll();
        assertThat(file_states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
