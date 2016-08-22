package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_key_pediment;
import org.megapractical.billingplatform.repository.C_key_pedimentRepository;
import org.megapractical.billingplatform.service.C_key_pedimentService;

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
 * Test class for the C_key_pedimentResource REST controller.
 *
 * @see C_key_pedimentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_key_pedimentResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private C_key_pedimentRepository c_key_pedimentRepository;

    @Inject
    private C_key_pedimentService c_key_pedimentService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_key_pedimentMockMvc;

    private C_key_pediment c_key_pediment;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_key_pedimentResource c_key_pedimentResource = new C_key_pedimentResource();
        ReflectionTestUtils.setField(c_key_pedimentResource, "c_key_pedimentService", c_key_pedimentService);
        this.restC_key_pedimentMockMvc = MockMvcBuilders.standaloneSetup(c_key_pedimentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_key_pediment = new C_key_pediment();
        c_key_pediment.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createC_key_pediment() throws Exception {
        int databaseSizeBeforeCreate = c_key_pedimentRepository.findAll().size();

        // Create the C_key_pediment

        restC_key_pedimentMockMvc.perform(post("/api/c-key-pediments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_key_pediment)))
                .andExpect(status().isCreated());

        // Validate the C_key_pediment in the database
        List<C_key_pediment> c_key_pediments = c_key_pedimentRepository.findAll();
        assertThat(c_key_pediments).hasSize(databaseSizeBeforeCreate + 1);
        C_key_pediment testC_key_pediment = c_key_pediments.get(c_key_pediments.size() - 1);
        assertThat(testC_key_pediment.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_key_pedimentRepository.findAll().size();
        // set the field null
        c_key_pediment.setValue(null);

        // Create the C_key_pediment, which fails.

        restC_key_pedimentMockMvc.perform(post("/api/c-key-pediments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_key_pediment)))
                .andExpect(status().isBadRequest());

        List<C_key_pediment> c_key_pediments = c_key_pedimentRepository.findAll();
        assertThat(c_key_pediments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_key_pediments() throws Exception {
        // Initialize the database
        c_key_pedimentRepository.saveAndFlush(c_key_pediment);

        // Get all the c_key_pediments
        restC_key_pedimentMockMvc.perform(get("/api/c-key-pediments?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_key_pediment.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getC_key_pediment() throws Exception {
        // Initialize the database
        c_key_pedimentRepository.saveAndFlush(c_key_pediment);

        // Get the c_key_pediment
        restC_key_pedimentMockMvc.perform(get("/api/c-key-pediments/{id}", c_key_pediment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_key_pediment.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_key_pediment() throws Exception {
        // Get the c_key_pediment
        restC_key_pedimentMockMvc.perform(get("/api/c-key-pediments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_key_pediment() throws Exception {
        // Initialize the database
        c_key_pedimentService.save(c_key_pediment);

        int databaseSizeBeforeUpdate = c_key_pedimentRepository.findAll().size();

        // Update the c_key_pediment
        C_key_pediment updatedC_key_pediment = new C_key_pediment();
        updatedC_key_pediment.setId(c_key_pediment.getId());
        updatedC_key_pediment.setValue(UPDATED_VALUE);

        restC_key_pedimentMockMvc.perform(put("/api/c-key-pediments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_key_pediment)))
                .andExpect(status().isOk());

        // Validate the C_key_pediment in the database
        List<C_key_pediment> c_key_pediments = c_key_pedimentRepository.findAll();
        assertThat(c_key_pediments).hasSize(databaseSizeBeforeUpdate);
        C_key_pediment testC_key_pediment = c_key_pediments.get(c_key_pediments.size() - 1);
        assertThat(testC_key_pediment.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteC_key_pediment() throws Exception {
        // Initialize the database
        c_key_pedimentService.save(c_key_pediment);

        int databaseSizeBeforeDelete = c_key_pedimentRepository.findAll().size();

        // Get the c_key_pediment
        restC_key_pedimentMockMvc.perform(delete("/api/c-key-pediments/{id}", c_key_pediment.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_key_pediment> c_key_pediments = c_key_pedimentRepository.findAll();
        assertThat(c_key_pediments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
