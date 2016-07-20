package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.repository.Audit_event_typeRepository;
import org.megapractical.billingplatform.service.Audit_event_typeService;

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
 * Test class for the Audit_event_typeResource REST controller.
 *
 * @see Audit_event_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Audit_event_typeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Audit_event_typeRepository audit_event_typeRepository;

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAudit_event_typeMockMvc;

    private Audit_event_type audit_event_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Audit_event_typeResource audit_event_typeResource = new Audit_event_typeResource();
        ReflectionTestUtils.setField(audit_event_typeResource, "audit_event_typeService", audit_event_typeService);
        this.restAudit_event_typeMockMvc = MockMvcBuilders.standaloneSetup(audit_event_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        audit_event_type = new Audit_event_type();
        audit_event_type.setName(DEFAULT_NAME);
        audit_event_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAudit_event_type() throws Exception {
        int databaseSizeBeforeCreate = audit_event_typeRepository.findAll().size();

        // Create the Audit_event_type

        restAudit_event_typeMockMvc.perform(post("/api/audit-event-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audit_event_type)))
                .andExpect(status().isCreated());

        // Validate the Audit_event_type in the database
        List<Audit_event_type> audit_event_types = audit_event_typeRepository.findAll();
        assertThat(audit_event_types).hasSize(databaseSizeBeforeCreate + 1);
        Audit_event_type testAudit_event_type = audit_event_types.get(audit_event_types.size() - 1);
        assertThat(testAudit_event_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAudit_event_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = audit_event_typeRepository.findAll().size();
        // set the field null
        audit_event_type.setName(null);

        // Create the Audit_event_type, which fails.

        restAudit_event_typeMockMvc.perform(post("/api/audit-event-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(audit_event_type)))
                .andExpect(status().isBadRequest());

        List<Audit_event_type> audit_event_types = audit_event_typeRepository.findAll();
        assertThat(audit_event_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAudit_event_types() throws Exception {
        /*
        // Initialize the database
        audit_event_typeRepository.saveAndFlush(audit_event_type);

        // Get all the audit_event_types
        restAudit_event_typeMockMvc.perform(get("/api/audit-event-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(audit_event_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));*/
    }

    @Test
    @Transactional
    public void getAudit_event_type() throws Exception {
        // Initialize the database
        audit_event_typeRepository.saveAndFlush(audit_event_type);

        // Get the audit_event_type
        restAudit_event_typeMockMvc.perform(get("/api/audit-event-types/{id}", audit_event_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(audit_event_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAudit_event_type() throws Exception {
        // Get the audit_event_type
        restAudit_event_typeMockMvc.perform(get("/api/audit-event-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAudit_event_type() throws Exception {
        // Initialize the database
        audit_event_typeService.save(audit_event_type);

        int databaseSizeBeforeUpdate = audit_event_typeRepository.findAll().size();

        // Update the audit_event_type
        Audit_event_type updatedAudit_event_type = new Audit_event_type();
        updatedAudit_event_type.setId(audit_event_type.getId());
        updatedAudit_event_type.setName(UPDATED_NAME);
        updatedAudit_event_type.setDescription(UPDATED_DESCRIPTION);

        restAudit_event_typeMockMvc.perform(put("/api/audit-event-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAudit_event_type)))
                .andExpect(status().isOk());

        // Validate the Audit_event_type in the database
        List<Audit_event_type> audit_event_types = audit_event_typeRepository.findAll();
        assertThat(audit_event_types).hasSize(databaseSizeBeforeUpdate);
        Audit_event_type testAudit_event_type = audit_event_types.get(audit_event_types.size() - 1);
        assertThat(testAudit_event_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAudit_event_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteAudit_event_type() throws Exception {
        // Initialize the database
        audit_event_typeService.save(audit_event_type);

        int databaseSizeBeforeDelete = audit_event_typeRepository.findAll().size();

        // Get the audit_event_type
        restAudit_event_typeMockMvc.perform(delete("/api/audit-event-types/{id}", audit_event_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Audit_event_type> audit_event_types = audit_event_typeRepository.findAll();
        assertThat(audit_event_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
