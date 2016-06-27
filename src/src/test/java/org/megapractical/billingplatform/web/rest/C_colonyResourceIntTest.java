package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_colony;
import org.megapractical.billingplatform.repository.C_colonyRepository;
import org.megapractical.billingplatform.service.C_colonyService;

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
 * Test class for the C_colonyResource REST controller.
 *
 * @see C_colonyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_colonyResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_colonyRepository c_colonyRepository;

    @Inject
    private C_colonyService c_colonyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_colonyMockMvc;

    private C_colony c_colony;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_colonyResource c_colonyResource = new C_colonyResource();
        ReflectionTestUtils.setField(c_colonyResource, "c_colonyService", c_colonyService);
        this.restC_colonyMockMvc = MockMvcBuilders.standaloneSetup(c_colonyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_colony = new C_colony();
        c_colony.setName(DEFAULT_NAME);
        c_colony.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_colony() throws Exception {
        int databaseSizeBeforeCreate = c_colonyRepository.findAll().size();

        // Create the C_colony

        restC_colonyMockMvc.perform(post("/api/c-colonies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_colony)))
                .andExpect(status().isCreated());

        // Validate the C_colony in the database
        List<C_colony> c_colonies = c_colonyRepository.findAll();
        assertThat(c_colonies).hasSize(databaseSizeBeforeCreate + 1);
        C_colony testC_colony = c_colonies.get(c_colonies.size() - 1);
        assertThat(testC_colony.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_colony.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_colonyRepository.findAll().size();
        // set the field null
        c_colony.setName(null);

        // Create the C_colony, which fails.

        restC_colonyMockMvc.perform(post("/api/c-colonies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_colony)))
                .andExpect(status().isBadRequest());

        List<C_colony> c_colonies = c_colonyRepository.findAll();
        assertThat(c_colonies).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_colonies() throws Exception {
        // Initialize the database
        c_colonyRepository.saveAndFlush(c_colony);

        // Get all the c_colonies
        restC_colonyMockMvc.perform(get("/api/c-colonies?sort=id,desc&locationId=-1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_colony.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_colony() throws Exception {
        // Initialize the database
        c_colonyRepository.saveAndFlush(c_colony);

        // Get the c_colony
        restC_colonyMockMvc.perform(get("/api/c-colonies/{id}", c_colony.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_colony.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_colony() throws Exception {
        // Get the c_colony
        restC_colonyMockMvc.perform(get("/api/c-colonies/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_colony() throws Exception {
        // Initialize the database
        c_colonyService.save(c_colony);

        int databaseSizeBeforeUpdate = c_colonyRepository.findAll().size();

        // Update the c_colony
        C_colony updatedC_colony = new C_colony();
        updatedC_colony.setId(c_colony.getId());
        updatedC_colony.setName(UPDATED_NAME);
        updatedC_colony.setDescription(UPDATED_DESCRIPTION);

        restC_colonyMockMvc.perform(put("/api/c-colonies")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_colony)))
                .andExpect(status().isOk());

        // Validate the C_colony in the database
        List<C_colony> c_colonies = c_colonyRepository.findAll();
        assertThat(c_colonies).hasSize(databaseSizeBeforeUpdate);
        C_colony testC_colony = c_colonies.get(c_colonies.size() - 1);
        assertThat(testC_colony.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_colony.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_colony() throws Exception {
        // Initialize the database
        c_colonyService.save(c_colony);

        int databaseSizeBeforeDelete = c_colonyRepository.findAll().size();

        // Get the c_colony
        restC_colonyMockMvc.perform(delete("/api/c-colonies/{id}", c_colony.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_colony> c_colonies = c_colonyRepository.findAll();
        assertThat(c_colonies).hasSize(databaseSizeBeforeDelete - 1);
    }
}
