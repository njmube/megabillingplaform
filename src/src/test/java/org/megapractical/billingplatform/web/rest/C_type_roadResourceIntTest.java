package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_type_road;
import org.megapractical.billingplatform.repository.C_type_roadRepository;
import org.megapractical.billingplatform.service.C_type_roadService;

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
 * Test class for the C_type_roadResource REST controller.
 *
 * @see C_type_roadResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_type_roadResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_type_roadRepository c_type_roadRepository;

    @Inject
    private C_type_roadService c_type_roadService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_type_roadMockMvc;

    private C_type_road c_type_road;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_type_roadResource c_type_roadResource = new C_type_roadResource();
        ReflectionTestUtils.setField(c_type_roadResource, "c_type_roadService", c_type_roadService);
        this.restC_type_roadMockMvc = MockMvcBuilders.standaloneSetup(c_type_roadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_type_road = new C_type_road();
        c_type_road.setName(DEFAULT_NAME);
        c_type_road.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_type_road() throws Exception {
        int databaseSizeBeforeCreate = c_type_roadRepository.findAll().size();

        // Create the C_type_road

        restC_type_roadMockMvc.perform(post("/api/c-type-roads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_type_road)))
                .andExpect(status().isCreated());

        // Validate the C_type_road in the database
        List<C_type_road> c_type_roads = c_type_roadRepository.findAll();
        assertThat(c_type_roads).hasSize(databaseSizeBeforeCreate + 1);
        C_type_road testC_type_road = c_type_roads.get(c_type_roads.size() - 1);
        assertThat(testC_type_road.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_type_road.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_type_roadRepository.findAll().size();
        // set the field null
        c_type_road.setName(null);

        // Create the C_type_road, which fails.

        restC_type_roadMockMvc.perform(post("/api/c-type-roads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_type_road)))
                .andExpect(status().isBadRequest());

        List<C_type_road> c_type_roads = c_type_roadRepository.findAll();
        assertThat(c_type_roads).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_type_roads() throws Exception {
        // Initialize the database
        c_type_roadRepository.saveAndFlush(c_type_road);

        // Get all the c_type_roads
        restC_type_roadMockMvc.perform(get("/api/c-type-roads?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_type_road.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_type_road() throws Exception {
        // Initialize the database
        c_type_roadRepository.saveAndFlush(c_type_road);

        // Get the c_type_road
        restC_type_roadMockMvc.perform(get("/api/c-type-roads/{id}", c_type_road.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_type_road.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_type_road() throws Exception {
        // Get the c_type_road
        restC_type_roadMockMvc.perform(get("/api/c-type-roads/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_type_road() throws Exception {
        // Initialize the database
        c_type_roadService.save(c_type_road);

        int databaseSizeBeforeUpdate = c_type_roadRepository.findAll().size();

        // Update the c_type_road
        C_type_road updatedC_type_road = new C_type_road();
        updatedC_type_road.setId(c_type_road.getId());
        updatedC_type_road.setName(UPDATED_NAME);
        updatedC_type_road.setDescription(UPDATED_DESCRIPTION);

        restC_type_roadMockMvc.perform(put("/api/c-type-roads")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_type_road)))
                .andExpect(status().isOk());

        // Validate the C_type_road in the database
        List<C_type_road> c_type_roads = c_type_roadRepository.findAll();
        assertThat(c_type_roads).hasSize(databaseSizeBeforeUpdate);
        C_type_road testC_type_road = c_type_roads.get(c_type_roads.size() - 1);
        assertThat(testC_type_road.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_type_road.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_type_road() throws Exception {
        // Initialize the database
        c_type_roadService.save(c_type_road);

        int databaseSizeBeforeDelete = c_type_roadRepository.findAll().size();

        // Get the c_type_road
        restC_type_roadMockMvc.perform(delete("/api/c-type-roads/{id}", c_type_road.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_type_road> c_type_roads = c_type_roadRepository.findAll();
        assertThat(c_type_roads).hasSize(databaseSizeBeforeDelete - 1);
    }
}
