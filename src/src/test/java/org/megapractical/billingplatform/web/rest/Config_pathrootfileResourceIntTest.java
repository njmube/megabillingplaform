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

    private static final String DEFAULT_PATHROOT_FREE_CERTIFICATE = "AAAAA";
    private static final String UPDATED_PATHROOT_FREE_CERTIFICATE = "BBBBB";
    private static final String DEFAULT_PATHROOT_FREE_LOGO = "AAAAA";
    private static final String UPDATED_PATHROOT_FREE_LOGO = "BBBBB";
    private static final String DEFAULT_PATHROOT_FREE_CFDI = "AAAAA";
    private static final String UPDATED_PATHROOT_FREE_CFDI = "BBBBB";
    private static final String DEFAULT_PATHROOT_CERTIFICATE = "AAAAA";
    private static final String UPDATED_PATHROOT_CERTIFICATE = "BBBBB";
    private static final String DEFAULT_PATHROOT_LOGO = "AAAAA";
    private static final String UPDATED_PATHROOT_LOGO = "BBBBB";
    private static final String DEFAULT_PATHROOT_CFDI = "AAAAA";
    private static final String UPDATED_PATHROOT_CFDI = "BBBBB";

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
        config_pathrootfile.setPathrootFreeCertificate(DEFAULT_PATHROOT_FREE_CERTIFICATE);
        config_pathrootfile.setPathrootFreeLogo(DEFAULT_PATHROOT_FREE_LOGO);
        config_pathrootfile.setPathrootFreeCfdi(DEFAULT_PATHROOT_FREE_CFDI);
        config_pathrootfile.setPathrootCertificate(DEFAULT_PATHROOT_CERTIFICATE);
        config_pathrootfile.setPathrootLogo(DEFAULT_PATHROOT_LOGO);
        config_pathrootfile.setPathrootCfdi(DEFAULT_PATHROOT_CFDI);
    }

    @Test
    @Transactional
    public void createConfig_pathrootfile() throws Exception {
        /*
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
        assertThat(testConfig_pathrootfile.getPathrootFreeCertificate()).isEqualTo(DEFAULT_PATHROOT_FREE_CERTIFICATE);
        assertThat(testConfig_pathrootfile.getPathrootFreeLogo()).isEqualTo(DEFAULT_PATHROOT_FREE_LOGO);
        assertThat(testConfig_pathrootfile.getPathrootFreeCfdi()).isEqualTo(DEFAULT_PATHROOT_FREE_CFDI);
        assertThat(testConfig_pathrootfile.getPathrootCertificate()).isEqualTo(DEFAULT_PATHROOT_CERTIFICATE);
        assertThat(testConfig_pathrootfile.getPathrootLogo()).isEqualTo(DEFAULT_PATHROOT_LOGO);
        assertThat(testConfig_pathrootfile.getPathrootCfdi()).isEqualTo(DEFAULT_PATHROOT_CFDI);*/
    }

    @Test
    @Transactional
    public void checkPathrootFreeCertificateIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = config_pathrootfileRepository.findAll().size();
        // set the field null
        config_pathrootfile.setPathrootFreeCertificate(null);

        // Create the Config_pathrootfile, which fails.

        restConfig_pathrootfileMockMvc.perform(post("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config_pathrootfile)))
                .andExpect(status().isBadRequest());

        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void checkPathrootFreeLogoIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = config_pathrootfileRepository.findAll().size();
        // set the field null
        config_pathrootfile.setPathrootFreeLogo(null);

        // Create the Config_pathrootfile, which fails.

        restConfig_pathrootfileMockMvc.perform(post("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config_pathrootfile)))
                .andExpect(status().isBadRequest());

        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeTest);
        */
    }

    @Test
    @Transactional
    public void checkPathrootFreeCfdiIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = config_pathrootfileRepository.findAll().size();
        // set the field null
        config_pathrootfile.setPathrootFreeCfdi(null);

        // Create the Config_pathrootfile, which fails.

        restConfig_pathrootfileMockMvc.perform(post("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config_pathrootfile)))
                .andExpect(status().isBadRequest());

        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeTest);
        */
    }

    @Test
    @Transactional
    public void checkPathrootCertificateIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = config_pathrootfileRepository.findAll().size();
        // set the field null
        config_pathrootfile.setPathrootCertificate(null);

        // Create the Config_pathrootfile, which fails.

        restConfig_pathrootfileMockMvc.perform(post("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config_pathrootfile)))
                .andExpect(status().isBadRequest());

        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeTest);
        */
    }

    @Test
    @Transactional
    public void checkPathrootLogoIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = config_pathrootfileRepository.findAll().size();
        // set the field null
        config_pathrootfile.setPathrootLogo(null);

        // Create the Config_pathrootfile, which fails.

        restConfig_pathrootfileMockMvc.perform(post("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config_pathrootfile)))
                .andExpect(status().isBadRequest());

        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeTest);
        */
    }

    @Test
    @Transactional
    public void checkPathrootCfdiIsRequired() throws Exception {
        /*
        int databaseSizeBeforeTest = config_pathrootfileRepository.findAll().size();
        // set the field null
        config_pathrootfile.setPathrootCfdi(null);

        // Create the Config_pathrootfile, which fails.

        restConfig_pathrootfileMockMvc.perform(post("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(config_pathrootfile)))
                .andExpect(status().isBadRequest());

        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeTest);*/
    }

    @Test
    @Transactional
    public void getAllConfig_pathrootfiles() throws Exception {
        /*
        // Initialize the database
        config_pathrootfileRepository.saveAndFlush(config_pathrootfile);

        // Get all the config_pathrootfiles
        restConfig_pathrootfileMockMvc.perform(get("/api/config-pathrootfiles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(config_pathrootfile.getId().intValue())))
                .andExpect(jsonPath("$.[*].pathrootFreeCertificate").value(hasItem(DEFAULT_PATHROOT_FREE_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].pathrootFreeLogo").value(hasItem(DEFAULT_PATHROOT_FREE_LOGO.toString())))
                .andExpect(jsonPath("$.[*].pathrootFreeCfdi").value(hasItem(DEFAULT_PATHROOT_FREE_CFDI.toString())))
                .andExpect(jsonPath("$.[*].pathrootCertificate").value(hasItem(DEFAULT_PATHROOT_CERTIFICATE.toString())))
                .andExpect(jsonPath("$.[*].pathrootLogo").value(hasItem(DEFAULT_PATHROOT_LOGO.toString())))
                .andExpect(jsonPath("$.[*].pathrootCfdi").value(hasItem(DEFAULT_PATHROOT_CFDI.toString())));*/
    }

    @Test
    @Transactional
    public void getConfig_pathrootfile() throws Exception {
        /*
        // Initialize the database
        config_pathrootfileRepository.saveAndFlush(config_pathrootfile);

        // Get the config_pathrootfile
        restConfig_pathrootfileMockMvc.perform(get("/api/config-pathrootfiles/{id}", config_pathrootfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(config_pathrootfile.getId().intValue()))
            .andExpect(jsonPath("$.pathrootFreeCertificate").value(DEFAULT_PATHROOT_FREE_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.pathrootFreeLogo").value(DEFAULT_PATHROOT_FREE_LOGO.toString()))
            .andExpect(jsonPath("$.pathrootFreeCfdi").value(DEFAULT_PATHROOT_FREE_CFDI.toString()))
            .andExpect(jsonPath("$.pathrootCertificate").value(DEFAULT_PATHROOT_CERTIFICATE.toString()))
            .andExpect(jsonPath("$.pathrootLogo").value(DEFAULT_PATHROOT_LOGO.toString()))
            .andExpect(jsonPath("$.pathrootCfdi").value(DEFAULT_PATHROOT_CFDI.toString()));*/
    }

    @Test
    @Transactional
    public void getNonExistingConfig_pathrootfile() throws Exception {
        /*
        // Get the config_pathrootfile
        restConfig_pathrootfileMockMvc.perform(get("/api/config-pathrootfiles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());*/
    }

    @Test
    @Transactional
    public void updateConfig_pathrootfile() throws Exception {
        /*
        // Initialize the database
        config_pathrootfileService.save(config_pathrootfile);

        int databaseSizeBeforeUpdate = config_pathrootfileRepository.findAll().size();

        // Update the config_pathrootfile
        Config_pathrootfile updatedConfig_pathrootfile = new Config_pathrootfile();
        updatedConfig_pathrootfile.setId(config_pathrootfile.getId());
        updatedConfig_pathrootfile.setPathrootFreeCertificate(UPDATED_PATHROOT_FREE_CERTIFICATE);
        updatedConfig_pathrootfile.setPathrootFreeLogo(UPDATED_PATHROOT_FREE_LOGO);
        updatedConfig_pathrootfile.setPathrootFreeCfdi(UPDATED_PATHROOT_FREE_CFDI);
        updatedConfig_pathrootfile.setPathrootCertificate(UPDATED_PATHROOT_CERTIFICATE);
        updatedConfig_pathrootfile.setPathrootLogo(UPDATED_PATHROOT_LOGO);
        updatedConfig_pathrootfile.setPathrootCfdi(UPDATED_PATHROOT_CFDI);

        restConfig_pathrootfileMockMvc.perform(put("/api/config-pathrootfiles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedConfig_pathrootfile)))
                .andExpect(status().isOk());

        // Validate the Config_pathrootfile in the database
        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeUpdate);
        Config_pathrootfile testConfig_pathrootfile = config_pathrootfiles.get(config_pathrootfiles.size() - 1);
        assertThat(testConfig_pathrootfile.getPathrootFreeCertificate()).isEqualTo(UPDATED_PATHROOT_FREE_CERTIFICATE);
        assertThat(testConfig_pathrootfile.getPathrootFreeLogo()).isEqualTo(UPDATED_PATHROOT_FREE_LOGO);
        assertThat(testConfig_pathrootfile.getPathrootFreeCfdi()).isEqualTo(UPDATED_PATHROOT_FREE_CFDI);
        assertThat(testConfig_pathrootfile.getPathrootCertificate()).isEqualTo(UPDATED_PATHROOT_CERTIFICATE);
        assertThat(testConfig_pathrootfile.getPathrootLogo()).isEqualTo(UPDATED_PATHROOT_LOGO);
        assertThat(testConfig_pathrootfile.getPathrootCfdi()).isEqualTo(UPDATED_PATHROOT_CFDI);*/
    }

    @Test
    @Transactional
    public void deleteConfig_pathrootfile() throws Exception {
        /*
        // Initialize the database
        config_pathrootfileService.save(config_pathrootfile);

        int databaseSizeBeforeDelete = config_pathrootfileRepository.findAll().size();

        // Get the config_pathrootfile
        restConfig_pathrootfileMockMvc.perform(delete("/api/config-pathrootfiles/{id}", config_pathrootfile.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Config_pathrootfile> config_pathrootfiles = config_pathrootfileRepository.findAll();
        assertThat(config_pathrootfiles).hasSize(databaseSizeBeforeDelete - 1);*/
    }
}
