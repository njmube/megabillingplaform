package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Cfdi_template;
import org.megapractical.billingplatform.repository.Cfdi_templateRepository;
import org.megapractical.billingplatform.service.Cfdi_templateService;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Cfdi_templateResource REST controller.
 *
 * @see Cfdi_templateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Cfdi_templateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final String DEFAULT_TEMPLATE = "AAAAA";
    private static final String UPDATED_TEMPLATE = "BBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Inject
    private Cfdi_templateRepository cfdi_templateRepository;

    @Inject
    private Cfdi_templateService cfdi_templateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCfdi_templateMockMvc;

    private Cfdi_template cfdi_template;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cfdi_templateResource cfdi_templateResource = new Cfdi_templateResource();
        ReflectionTestUtils.setField(cfdi_templateResource, "cfdi_templateService", cfdi_templateService);
        this.restCfdi_templateMockMvc = MockMvcBuilders.standaloneSetup(cfdi_templateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cfdi_template = new Cfdi_template();
        cfdi_template.setName(DEFAULT_NAME);
        cfdi_template.setDescription(DEFAULT_DESCRIPTION);
        cfdi_template.setTemplate(DEFAULT_TEMPLATE);
        cfdi_template.setFile(DEFAULT_FILE);
        cfdi_template.setFileContentType(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCfdi_template() throws Exception {
        int databaseSizeBeforeCreate = cfdi_templateRepository.findAll().size();

        // Create the Cfdi_template

        restCfdi_templateMockMvc.perform(post("/api/cfdi-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi_template)))
                .andExpect(status().isCreated());

        // Validate the Cfdi_template in the database
        List<Cfdi_template> cfdi_templates = cfdi_templateRepository.findAll();
        assertThat(cfdi_templates).hasSize(databaseSizeBeforeCreate + 1);
        Cfdi_template testCfdi_template = cfdi_templates.get(cfdi_templates.size() - 1);
        assertThat(testCfdi_template.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCfdi_template.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCfdi_template.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
        assertThat(testCfdi_template.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testCfdi_template.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllCfdi_templates() throws Exception {
        // Initialize the database
        cfdi_templateRepository.saveAndFlush(cfdi_template);

        // Get all the cfdi_templates
        restCfdi_templateMockMvc.perform(get("/api/cfdi-templates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cfdi_template.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE.toString())))
                .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    @Transactional
    public void getCfdi_template() throws Exception {
        // Initialize the database
        cfdi_templateRepository.saveAndFlush(cfdi_template);

        // Get the cfdi_template
        restCfdi_templateMockMvc.perform(get("/api/cfdi-templates/{id}", cfdi_template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cfdi_template.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getNonExistingCfdi_template() throws Exception {
        // Get the cfdi_template
        restCfdi_templateMockMvc.perform(get("/api/cfdi-templates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCfdi_template() throws Exception {
        // Initialize the database
        cfdi_templateService.save(cfdi_template);

        int databaseSizeBeforeUpdate = cfdi_templateRepository.findAll().size();

        // Update the cfdi_template
        Cfdi_template updatedCfdi_template = new Cfdi_template();
        updatedCfdi_template.setId(cfdi_template.getId());
        updatedCfdi_template.setName(UPDATED_NAME);
        updatedCfdi_template.setDescription(UPDATED_DESCRIPTION);
        updatedCfdi_template.setTemplate(UPDATED_TEMPLATE);
        updatedCfdi_template.setFile(UPDATED_FILE);
        updatedCfdi_template.setFileContentType(UPDATED_FILE_CONTENT_TYPE);

        restCfdi_templateMockMvc.perform(put("/api/cfdi-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCfdi_template)))
                .andExpect(status().isOk());

        // Validate the Cfdi_template in the database
        List<Cfdi_template> cfdi_templates = cfdi_templateRepository.findAll();
        assertThat(cfdi_templates).hasSize(databaseSizeBeforeUpdate);
        Cfdi_template testCfdi_template = cfdi_templates.get(cfdi_templates.size() - 1);
        assertThat(testCfdi_template.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCfdi_template.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCfdi_template.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
        assertThat(testCfdi_template.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testCfdi_template.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteCfdi_template() throws Exception {
        // Initialize the database
        cfdi_templateService.save(cfdi_template);

        int databaseSizeBeforeDelete = cfdi_templateRepository.findAll().size();

        // Get the cfdi_template
        restCfdi_templateMockMvc.perform(delete("/api/cfdi-templates/{id}", cfdi_template.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cfdi_template> cfdi_templates = cfdi_templateRepository.findAll();
        assertThat(cfdi_templates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
