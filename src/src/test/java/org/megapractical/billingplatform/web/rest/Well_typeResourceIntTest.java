package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Well_type;
import org.megapractical.billingplatform.repository.Well_typeRepository;
import org.megapractical.billingplatform.service.Well_typeService;

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
 * Test class for the Well_typeResource REST controller.
 *
 * @see Well_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Well_typeResourceIntTest {

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    @Inject
    private Well_typeRepository well_typeRepository;

    @Inject
    private Well_typeService well_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restWell_typeMockMvc;

    private Well_type well_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Well_typeResource well_typeResource = new Well_typeResource();
        ReflectionTestUtils.setField(well_typeResource, "well_typeService", well_typeService);
        this.restWell_typeMockMvc = MockMvcBuilders.standaloneSetup(well_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        well_type = new Well_type();
        well_type.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createWell_type() throws Exception {
        int databaseSizeBeforeCreate = well_typeRepository.findAll().size();

        // Create the Well_type

        restWell_typeMockMvc.perform(post("/api/well-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(well_type)))
                .andExpect(status().isCreated());

        // Validate the Well_type in the database
        List<Well_type> well_types = well_typeRepository.findAll();
        assertThat(well_types).hasSize(databaseSizeBeforeCreate + 1);
        Well_type testWell_type = well_types.get(well_types.size() - 1);
        assertThat(testWell_type.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = well_typeRepository.findAll().size();
        // set the field null
        well_type.setCode(null);

        // Create the Well_type, which fails.

        restWell_typeMockMvc.perform(post("/api/well-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(well_type)))
                .andExpect(status().isBadRequest());

        List<Well_type> well_types = well_typeRepository.findAll();
        assertThat(well_types).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWell_types() throws Exception {
        // Initialize the database
        well_typeRepository.saveAndFlush(well_type);

        // Get all the well_types
        restWell_typeMockMvc.perform(get("/api/well-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(well_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getWell_type() throws Exception {
        // Initialize the database
        well_typeRepository.saveAndFlush(well_type);

        // Get the well_type
        restWell_typeMockMvc.perform(get("/api/well-types/{id}", well_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(well_type.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWell_type() throws Exception {
        // Get the well_type
        restWell_typeMockMvc.perform(get("/api/well-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWell_type() throws Exception {
        // Initialize the database
        well_typeService.save(well_type);

        int databaseSizeBeforeUpdate = well_typeRepository.findAll().size();

        // Update the well_type
        Well_type updatedWell_type = new Well_type();
        updatedWell_type.setId(well_type.getId());
        updatedWell_type.setCode(UPDATED_CODE);

        restWell_typeMockMvc.perform(put("/api/well-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedWell_type)))
                .andExpect(status().isOk());

        // Validate the Well_type in the database
        List<Well_type> well_types = well_typeRepository.findAll();
        assertThat(well_types).hasSize(databaseSizeBeforeUpdate);
        Well_type testWell_type = well_types.get(well_types.size() - 1);
        assertThat(testWell_type.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteWell_type() throws Exception {
        // Initialize the database
        well_typeService.save(well_type);

        int databaseSizeBeforeDelete = well_typeRepository.findAll().size();

        // Get the well_type
        restWell_typeMockMvc.perform(delete("/api/well-types/{id}", well_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Well_type> well_types = well_typeRepository.findAll();
        assertThat(well_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
