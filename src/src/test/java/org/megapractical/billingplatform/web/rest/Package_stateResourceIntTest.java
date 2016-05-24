package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Package_state;
import org.megapractical.billingplatform.repository.Package_stateRepository;
import org.megapractical.billingplatform.service.Package_stateService;

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
 * Test class for the Package_stateResource REST controller.
 *
 * @see Package_stateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Package_stateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Package_stateRepository package_stateRepository;

    @Inject
    private Package_stateService package_stateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPackage_stateMockMvc;

    private Package_state package_state;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Package_stateResource package_stateResource = new Package_stateResource();
        ReflectionTestUtils.setField(package_stateResource, "package_stateService", package_stateService);
        this.restPackage_stateMockMvc = MockMvcBuilders.standaloneSetup(package_stateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        package_state = new Package_state();
        package_state.setName(DEFAULT_NAME);
        package_state.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPackage_state() throws Exception {
        int databaseSizeBeforeCreate = package_stateRepository.findAll().size();

        // Create the Package_state

        restPackage_stateMockMvc.perform(post("/api/package-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(package_state)))
                .andExpect(status().isCreated());

        // Validate the Package_state in the database
        List<Package_state> package_states = package_stateRepository.findAll();
        assertThat(package_states).hasSize(databaseSizeBeforeCreate + 1);
        Package_state testPackage_state = package_states.get(package_states.size() - 1);
        assertThat(testPackage_state.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPackage_state.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPackage_states() throws Exception {
        // Initialize the database
        package_stateRepository.saveAndFlush(package_state);

        // Get all the package_states
        restPackage_stateMockMvc.perform(get("/api/package-states?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(package_state.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPackage_state() throws Exception {
        // Initialize the database
        package_stateRepository.saveAndFlush(package_state);

        // Get the package_state
        restPackage_stateMockMvc.perform(get("/api/package-states/{id}", package_state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(package_state.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPackage_state() throws Exception {
        // Get the package_state
        restPackage_stateMockMvc.perform(get("/api/package-states/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackage_state() throws Exception {
        // Initialize the database
        package_stateService.save(package_state);

        int databaseSizeBeforeUpdate = package_stateRepository.findAll().size();

        // Update the package_state
        Package_state updatedPackage_state = new Package_state();
        updatedPackage_state.setId(package_state.getId());
        updatedPackage_state.setName(UPDATED_NAME);
        updatedPackage_state.setDescription(UPDATED_DESCRIPTION);

        restPackage_stateMockMvc.perform(put("/api/package-states")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPackage_state)))
                .andExpect(status().isOk());

        // Validate the Package_state in the database
        List<Package_state> package_states = package_stateRepository.findAll();
        assertThat(package_states).hasSize(databaseSizeBeforeUpdate);
        Package_state testPackage_state = package_states.get(package_states.size() - 1);
        assertThat(testPackage_state.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPackage_state.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deletePackage_state() throws Exception {
        // Initialize the database
        package_stateService.save(package_state);

        int databaseSizeBeforeDelete = package_stateRepository.findAll().size();

        // Get the package_state
        restPackage_stateMockMvc.perform(delete("/api/package-states/{id}", package_state.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Package_state> package_states = package_stateRepository.findAll();
        assertThat(package_states).hasSize(databaseSizeBeforeDelete - 1);
    }
}
