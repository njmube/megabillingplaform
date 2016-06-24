package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Cfdi_type_doc;
import org.megapractical.billingplatform.repository.Cfdi_type_docRepository;
import org.megapractical.billingplatform.service.Cfdi_type_docService;

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
 * Test class for the Cfdi_type_docResource REST controller.
 *
 * @see Cfdi_type_docResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Cfdi_type_docResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Cfdi_type_docRepository cfdi_type_docRepository;

    @Inject
    private Cfdi_type_docService cfdi_type_docService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCfdi_type_docMockMvc;

    private Cfdi_type_doc cfdi_type_doc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Cfdi_type_docResource cfdi_type_docResource = new Cfdi_type_docResource();
        ReflectionTestUtils.setField(cfdi_type_docResource, "cfdi_type_docService", cfdi_type_docService);
        this.restCfdi_type_docMockMvc = MockMvcBuilders.standaloneSetup(cfdi_type_docResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        cfdi_type_doc = new Cfdi_type_doc();
        cfdi_type_doc.setName(DEFAULT_NAME);
        cfdi_type_doc.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCfdi_type_doc() throws Exception {
        int databaseSizeBeforeCreate = cfdi_type_docRepository.findAll().size();

        // Create the Cfdi_type_doc

        restCfdi_type_docMockMvc.perform(post("/api/cfdi-type-docs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi_type_doc)))
                .andExpect(status().isCreated());

        // Validate the Cfdi_type_doc in the database
        List<Cfdi_type_doc> cfdi_type_docs = cfdi_type_docRepository.findAll();
        assertThat(cfdi_type_docs).hasSize(databaseSizeBeforeCreate + 1);
        Cfdi_type_doc testCfdi_type_doc = cfdi_type_docs.get(cfdi_type_docs.size() - 1);
        assertThat(testCfdi_type_doc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCfdi_type_doc.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cfdi_type_docRepository.findAll().size();
        // set the field null
        cfdi_type_doc.setName(null);

        // Create the Cfdi_type_doc, which fails.

        restCfdi_type_docMockMvc.perform(post("/api/cfdi-type-docs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cfdi_type_doc)))
                .andExpect(status().isBadRequest());

        List<Cfdi_type_doc> cfdi_type_docs = cfdi_type_docRepository.findAll();
        assertThat(cfdi_type_docs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCfdi_type_docs() throws Exception {
        // Initialize the database
        cfdi_type_docRepository.saveAndFlush(cfdi_type_doc);

        // Get all the cfdi_type_docs
        restCfdi_type_docMockMvc.perform(get("/api/cfdi-type-docs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(cfdi_type_doc.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCfdi_type_doc() throws Exception {
        // Initialize the database
        cfdi_type_docRepository.saveAndFlush(cfdi_type_doc);

        // Get the cfdi_type_doc
        restCfdi_type_docMockMvc.perform(get("/api/cfdi-type-docs/{id}", cfdi_type_doc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(cfdi_type_doc.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCfdi_type_doc() throws Exception {
        // Get the cfdi_type_doc
        restCfdi_type_docMockMvc.perform(get("/api/cfdi-type-docs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCfdi_type_doc() throws Exception {
        // Initialize the database
        cfdi_type_docService.save(cfdi_type_doc);

        int databaseSizeBeforeUpdate = cfdi_type_docRepository.findAll().size();

        // Update the cfdi_type_doc
        Cfdi_type_doc updatedCfdi_type_doc = new Cfdi_type_doc();
        updatedCfdi_type_doc.setId(cfdi_type_doc.getId());
        updatedCfdi_type_doc.setName(UPDATED_NAME);
        updatedCfdi_type_doc.setDescription(UPDATED_DESCRIPTION);

        restCfdi_type_docMockMvc.perform(put("/api/cfdi-type-docs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCfdi_type_doc)))
                .andExpect(status().isOk());

        // Validate the Cfdi_type_doc in the database
        List<Cfdi_type_doc> cfdi_type_docs = cfdi_type_docRepository.findAll();
        assertThat(cfdi_type_docs).hasSize(databaseSizeBeforeUpdate);
        Cfdi_type_doc testCfdi_type_doc = cfdi_type_docs.get(cfdi_type_docs.size() - 1);
        assertThat(testCfdi_type_doc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCfdi_type_doc.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteCfdi_type_doc() throws Exception {
        // Initialize the database
        cfdi_type_docService.save(cfdi_type_doc);

        int databaseSizeBeforeDelete = cfdi_type_docRepository.findAll().size();

        // Get the cfdi_type_doc
        restCfdi_type_docMockMvc.perform(delete("/api/cfdi-type-docs/{id}", cfdi_type_doc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Cfdi_type_doc> cfdi_type_docs = cfdi_type_docRepository.findAll();
        assertThat(cfdi_type_docs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
