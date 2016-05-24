package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_location;
import org.megapractical.billingplatform.repository.C_locationRepository;
import org.megapractical.billingplatform.service.C_locationService;

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
 * Test class for the C_locationResource REST controller.
 *
 * @see C_locationResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_locationResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_locationRepository c_locationRepository;

    @Inject
    private C_locationService c_locationService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_locationMockMvc;

    private C_location c_location;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_locationResource c_locationResource = new C_locationResource();
        ReflectionTestUtils.setField(c_locationResource, "c_locationService", c_locationService);
        this.restC_locationMockMvc = MockMvcBuilders.standaloneSetup(c_locationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_location = new C_location();
        c_location.setName(DEFAULT_NAME);
        c_location.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_location() throws Exception {
        int databaseSizeBeforeCreate = c_locationRepository.findAll().size();

        // Create the C_location

        restC_locationMockMvc.perform(post("/api/c-locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_location)))
                .andExpect(status().isCreated());

        // Validate the C_location in the database
        List<C_location> c_locations = c_locationRepository.findAll();
        assertThat(c_locations).hasSize(databaseSizeBeforeCreate + 1);
        C_location testC_location = c_locations.get(c_locations.size() - 1);
        assertThat(testC_location.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_location.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllC_locations() throws Exception {
        // Initialize the database
        c_locationRepository.saveAndFlush(c_location);

        // Get all the c_locations
        restC_locationMockMvc.perform(get("/api/c-locations?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_location.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_location() throws Exception {
        // Initialize the database
        c_locationRepository.saveAndFlush(c_location);

        // Get the c_location
        restC_locationMockMvc.perform(get("/api/c-locations/{id}", c_location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_location.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_location() throws Exception {
        // Get the c_location
        restC_locationMockMvc.perform(get("/api/c-locations/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_location() throws Exception {
        // Initialize the database
        c_locationService.save(c_location);

        int databaseSizeBeforeUpdate = c_locationRepository.findAll().size();

        // Update the c_location
        C_location updatedC_location = new C_location();
        updatedC_location.setId(c_location.getId());
        updatedC_location.setName(UPDATED_NAME);
        updatedC_location.setDescription(UPDATED_DESCRIPTION);

        restC_locationMockMvc.perform(put("/api/c-locations")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_location)))
                .andExpect(status().isOk());

        // Validate the C_location in the database
        List<C_location> c_locations = c_locationRepository.findAll();
        assertThat(c_locations).hasSize(databaseSizeBeforeUpdate);
        C_location testC_location = c_locations.get(c_locations.size() - 1);
        assertThat(testC_location.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_location.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_location() throws Exception {
        // Initialize the database
        c_locationService.save(c_location);

        int databaseSizeBeforeDelete = c_locationRepository.findAll().size();

        // Get the c_location
        restC_locationMockMvc.perform(delete("/api/c-locations/{id}", c_location.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_location> c_locations = c_locationRepository.findAll();
        assertThat(c_locations).hasSize(databaseSizeBeforeDelete - 1);
    }
}
