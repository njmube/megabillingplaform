package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_system;
import org.megapractical.billingplatform.repository.C_systemRepository;
import org.megapractical.billingplatform.service.C_systemService;

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
 * Test class for the C_systemResource REST controller.
 *
 * @see C_systemResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_systemResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_systemRepository c_systemRepository;

    @Inject
    private C_systemService c_systemService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_systemMockMvc;

    private C_system c_system;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_systemResource c_systemResource = new C_systemResource();
        ReflectionTestUtils.setField(c_systemResource, "c_systemService", c_systemService);
        this.restC_systemMockMvc = MockMvcBuilders.standaloneSetup(c_systemResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_system = new C_system();
        c_system.setName(DEFAULT_NAME);
        c_system.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_system() throws Exception {
        int databaseSizeBeforeCreate = c_systemRepository.findAll().size();

        // Create the C_system

        restC_systemMockMvc.perform(post("/api/c-systems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_system)))
                .andExpect(status().isCreated());

        // Validate the C_system in the database
        List<C_system> c_systems = c_systemRepository.findAll();
        assertThat(c_systems).hasSize(databaseSizeBeforeCreate + 1);
        C_system testC_system = c_systems.get(c_systems.size() - 1);
        assertThat(testC_system.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_system.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_systemRepository.findAll().size();
        // set the field null
        c_system.setName(null);

        // Create the C_system, which fails.

        restC_systemMockMvc.perform(post("/api/c-systems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_system)))
                .andExpect(status().isBadRequest());

        List<C_system> c_systems = c_systemRepository.findAll();
        assertThat(c_systems).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_systems() throws Exception {
        // Initialize the database
        c_systemRepository.saveAndFlush(c_system);

        // Get all the c_systems
        restC_systemMockMvc.perform(get("/api/c-systems?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_system.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_system() throws Exception {
        // Initialize the database
        c_systemRepository.saveAndFlush(c_system);

        // Get the c_system
        restC_systemMockMvc.perform(get("/api/c-systems/{id}", c_system.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_system.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_system() throws Exception {
        // Get the c_system
        restC_systemMockMvc.perform(get("/api/c-systems/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_system() throws Exception {
        // Initialize the database
        c_systemService.save(c_system);

        int databaseSizeBeforeUpdate = c_systemRepository.findAll().size();

        // Update the c_system
        C_system updatedC_system = new C_system();
        updatedC_system.setId(c_system.getId());
        updatedC_system.setName(UPDATED_NAME);
        updatedC_system.setDescription(UPDATED_DESCRIPTION);

        restC_systemMockMvc.perform(put("/api/c-systems")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_system)))
                .andExpect(status().isOk());

        // Validate the C_system in the database
        List<C_system> c_systems = c_systemRepository.findAll();
        assertThat(c_systems).hasSize(databaseSizeBeforeUpdate);
        C_system testC_system = c_systems.get(c_systems.size() - 1);
        assertThat(testC_system.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_system.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_system() throws Exception {
        // Initialize the database
        c_systemService.save(c_system);

        int databaseSizeBeforeDelete = c_systemRepository.findAll().size();

        // Get the c_system
        restC_systemMockMvc.perform(delete("/api/c-systems/{id}", c_system.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_system> c_systems = c_systemRepository.findAll();
        assertThat(c_systems).hasSize(databaseSizeBeforeDelete - 1);
    }
}
