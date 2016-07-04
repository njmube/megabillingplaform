package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Config_pathrootfile;
import org.megapractical.billingplatform.repository.Config_pathrootfileRepository;
import org.megapractical.billingplatform.service.Config_pathrootfileService;

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
 * Test class for the Config_pathrootfileResource REST controller.
 *
 * @see Config_pathrootfileResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Config_pathrootfileResourceIntTest {

    private static final String DEFAULT_PATHROOTDEV = "AAAAA";
    private static final String UPDATED_PATHROOTDEV = "BBBBB";
    private static final String DEFAULT_PATHROOTPROD = "AAAAA";
    private static final String UPDATED_PATHROOTPROD = "BBBBB";

    @Inject
    private Config_pathrootfileRepository config_pathrootfileRepository;

    @Inject
    private Config_pathrootfileService config_pathrootfileService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restConfig_pathrootfileMockMvc;

    private Config_pathrootfile config_pathrootfile;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Config_pathrootfileResource config_pathrootfileResource = new Config_pathrootfileResource();
        ReflectionTestUtils.setField(config_pathrootfileResource, "config_pathrootfileService", config_pathrootfileService);
        this.restConfig_pathrootfileMockMvc = MockMvcBuilders.standaloneSetup(config_pathrootfileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        config_pathrootfile = new Config_pathrootfile();
        config_pathrootfile.setPathrootdev(DEFAULT_PATHROOTDEV);
        config_pathrootfile.setPathrootprod(DEFAULT_PATHROOTPROD);
    }

    @Test
    @Transactional
    public void createConfig_pathrootfile() throws Exception {
        int databaseSizeBeforeCreate = config_pathrootfileRepository.findAll().size();

        // Create the Config_pathrootfile

        restConfig_pathrootfileMockMvc.perform(post("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config_pathrootfile)))
                .andExpect(status().isCreated());

        // Validate the Config_pathrootfile in the database
        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeCreate + 1);
        Config_pathrootfile testConfig_pathrootfile = config_pathrootfiles.get(config_pathrootfiles.size() - 1);
        assertThat(testConfig_pathrootfile.getPathrootdev()).isEqualTo(DEFAULT_PATHROOTDEV);
        assertThat(testConfig_pathrootfile.getPathrootprod()).isEqualTo(DEFAULT_PATHROOTPROD);
    }

    @Test
    @Transactional
    public void checkPathrootdevIsRequired() throws Exception {
        int databaseSizeBeforeTest = config_pathrootfileRepository.findAll().size();
        // set the field null
        config_pathrootfile.setPathrootdev(null);

        // Create the Config_pathrootfile, which fails.

        restConfig_pathrootfileMockMvc.perform(post("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config_pathrootfile)))
                .andExpect(status().isBadRequest());

        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPathrootprodIsRequired() throws Exception {
        int databaseSizeBeforeTest = config_pathrootfileRepository.findAll().size();
        // set the field null
        config_pathrootfile.setPathrootprod(null);

        // Create the Config_pathrootfile, which fails.

        restConfig_pathrootfileMockMvc.perform(post("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config_pathrootfile)))
                .andExpect(status().isBadRequest());

        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConfig_pathrootfiles() throws Exception {
        // Initialize the database
        config_pathrootfileRepository.saveAndFlush(config_pathrootfile);

        // Get all the config_pathrootfiles
        restConfig_pathrootfileMockMvc.perform(get("/api/config-pathrootfiles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(config_pathrootfile.getId().intValue())))
                .andExpect(jsonPath("$.[*].pathrootdev").value(hasItem(DEFAULT_PATHROOTDEV.toString())))
                .andExpect(jsonPath("$.[*].pathrootprod").value(hasItem(DEFAULT_PATHROOTPROD.toString())));
    }

    @Test
    @Transactional
    public void getConfig_pathrootfile() throws Exception {
        // Initialize the database
        config_pathrootfileRepository.saveAndFlush(config_pathrootfile);

        // Get the config_pathrootfile
        restConfig_pathrootfileMockMvc.perform(get("/api/config-pathrootfiles/{id}", config_pathrootfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(config_pathrootfile.getId().intValue()))
            .andExpect(jsonPath("$.pathrootdev").value(DEFAULT_PATHROOTDEV.toString()))
            .andExpect(jsonPath("$.pathrootprod").value(DEFAULT_PATHROOTPROD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConfig_pathrootfile() throws Exception {
        // Get the config_pathrootfile
        restConfig_pathrootfileMockMvc.perform(get("/api/config-pathrootfiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConfig_pathrootfile() throws Exception {
        // Initialize the database
        config_pathrootfileService.save(config_pathrootfile);

        int databaseSizeBeforeUpdate = config_pathrootfileRepository.findAll().size();

        // Update the config_pathrootfile
        Config_pathrootfile updatedConfig_pathrootfile = new Config_pathrootfile();
        updatedConfig_pathrootfile.setId(config_pathrootfile.getId());
        updatedConfig_pathrootfile.setPathrootdev(UPDATED_PATHROOTDEV);
        updatedConfig_pathrootfile.setPathrootprod(UPDATED_PATHROOTPROD);

        restConfig_pathrootfileMockMvc.perform(put("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedConfig_pathrootfile)))
                .andExpect(status().isOk());

        // Validate the Config_pathrootfile in the database
        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeUpdate);
        Config_pathrootfile testConfig_pathrootfile = config_pathrootfiles.get(config_pathrootfiles.size() - 1);
        assertThat(testConfig_pathrootfile.getPathrootdev()).isEqualTo(UPDATED_PATHROOTDEV);
        assertThat(testConfig_pathrootfile.getPathrootprod()).isEqualTo(UPDATED_PATHROOTPROD);
    }

    @Test
    @Transactional
    public void deleteConfig_pathrootfile() throws Exception {
        // Initialize the database
        config_pathrootfileService.save(config_pathrootfile);

        int databaseSizeBeforeDelete = config_pathrootfileRepository.findAll().size();

        // Get the config_pathrootfile
        restConfig_pathrootfileMockMvc.perform(delete("/api/config-pathrootfiles/{id}", config_pathrootfile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
