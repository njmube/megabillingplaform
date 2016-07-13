package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Scope;
import org.megapractical.billingplatform.repository.ScopeRepository;
import org.megapractical.billingplatform.service.ScopeService;

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
 * Test class for the ScopeResource REST controller.
 *
 * @see ScopeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class ScopeResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private ScopeRepository scopeRepository;

    @Inject
    private ScopeService scopeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restScopeMockMvc;

    private Scope scope;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ScopeResource scopeResource = new ScopeResource();
        ReflectionTestUtils.setField(scopeResource, "scopeService", scopeService);
        this.restScopeMockMvc = MockMvcBuilders.standaloneSetup(scopeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        scope = new Scope();
        scope.setName(DEFAULT_NAME);
        scope.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createScope() throws Exception {
        int databaseSizeBeforeCreate = scopeRepository.findAll().size();

        // Create the Scope

        restScopeMockMvc.perform(post("/api/scopes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scope)))
                .andExpect(status().isCreated());

        // Validate the Scope in the database
        List<Scope> scopes = scopeRepository.findAll();
        assertThat(scopes).hasSize(databaseSizeBeforeCreate + 1);
        Scope testScope = scopes.get(scopes.size() - 1);
        assertThat(testScope.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testScope.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = scopeRepository.findAll().size();
        // set the field null
        scope.setName(null);

        // Create the Scope, which fails.

        restScopeMockMvc.perform(post("/api/scopes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(scope)))
                .andExpect(status().isBadRequest());

        List<Scope> scopes = scopeRepository.findAll();
        assertThat(scopes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScopes() throws Exception {
        // Initialize the database
        scopeRepository.saveAndFlush(scope);

        // Get all the scopes
        restScopeMockMvc.perform(get("/api/scopes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(scope.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getScope() throws Exception {
        // Initialize the database
        scopeRepository.saveAndFlush(scope);

        // Get the scope
        restScopeMockMvc.perform(get("/api/scopes/{id}", scope.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(scope.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScope() throws Exception {
        // Get the scope
        restScopeMockMvc.perform(get("/api/scopes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScope() throws Exception {
        // Initialize the database
        scopeService.save(scope);

        int databaseSizeBeforeUpdate = scopeRepository.findAll().size();

        // Update the scope
        Scope updatedScope = new Scope();
        updatedScope.setId(scope.getId());
        updatedScope.setName(UPDATED_NAME);
        updatedScope.setDescription(UPDATED_DESCRIPTION);

        restScopeMockMvc.perform(put("/api/scopes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedScope)))
                .andExpect(status().isOk());

        // Validate the Scope in the database
        List<Scope> scopes = scopeRepository.findAll();
        assertThat(scopes).hasSize(databaseSizeBeforeUpdate);
        Scope testScope = scopes.get(scopes.size() - 1);
        assertThat(testScope.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testScope.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteScope() throws Exception {
        // Initialize the database
        scopeService.save(scope);

        int databaseSizeBeforeDelete = scopeRepository.findAll().size();

        // Get the scope
        restScopeMockMvc.perform(delete("/api/scopes/{id}", scope.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Scope> scopes = scopeRepository.findAll();
        assertThat(scopes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
