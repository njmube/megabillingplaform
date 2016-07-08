package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.File_type;
import org.megapractical.billingplatform.repository.File_typeRepository;
import org.megapractical.billingplatform.service.File_typeService;

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
 * Test class for the File_typeResource REST controller.
 *
 * @see File_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class File_typeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private File_typeRepository file_typeRepository;

    @Inject
    private File_typeService file_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFile_typeMockMvc;

    private File_type file_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        File_typeResource file_typeResource = new File_typeResource();
        ReflectionTestUtils.setField(file_typeResource, "file_typeService", file_typeService);
        this.restFile_typeMockMvc = MockMvcBuilders.standaloneSetup(file_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        file_type = new File_type();
        file_type.setName(DEFAULT_NAME);
        file_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFile_type() throws Exception {
        int databaseSizeBeforeCreate = file_typeRepository.findAll().size();

        // Create the File_type

        restFile_typeMockMvc.perform(post("/api/file-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(file_type)))
                .andExpect(status().isCreated());

        // Validate the File_type in the database
        List<File_type> file_types = file_typeRepository.findAll();
        assertThat(file_types).hasSize(databaseSizeBeforeCreate + 1);
        File_type testFile_type = file_types.get(file_types.size() - 1);
        assertThat(testFile_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFile_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFile_types() throws Exception {
        // Initialize the database
        file_typeRepository.saveAndFlush(file_type);

        // Get all the file_types
        restFile_typeMockMvc.perform(get("/api/file-types?sort=id,desc&filtername= "))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(file_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getFile_type() throws Exception {
        // Initialize the database
        file_typeRepository.saveAndFlush(file_type);

        // Get the file_type
        restFile_typeMockMvc.perform(get("/api/file-types/{id}", file_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(file_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFile_type() throws Exception {
        // Get the file_type
        restFile_typeMockMvc.perform(get("/api/file-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFile_type() throws Exception {
        // Initialize the database
        file_typeService.save(file_type);

        int databaseSizeBeforeUpdate = file_typeRepository.findAll().size();

        // Update the file_type
        File_type updatedFile_type = new File_type();
        updatedFile_type.setId(file_type.getId());
        updatedFile_type.setName(UPDATED_NAME);
        updatedFile_type.setDescription(UPDATED_DESCRIPTION);

        restFile_typeMockMvc.perform(put("/api/file-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFile_type)))
                .andExpect(status().isOk());

        // Validate the File_type in the database
        List<File_type> file_types = file_typeRepository.findAll();
        assertThat(file_types).hasSize(databaseSizeBeforeUpdate);
        File_type testFile_type = file_types.get(file_types.size() - 1);
        assertThat(testFile_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFile_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteFile_type() throws Exception {
        // Initialize the database
        file_typeService.save(file_type);

        int databaseSizeBeforeDelete = file_typeRepository.findAll().size();

        // Get the file_type
        restFile_typeMockMvc.perform(delete("/api/file-types/{id}", file_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<File_type> file_types = file_typeRepository.findAll();
        assertThat(file_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
