package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.General_data;
import org.megapractical.billingplatform.repository.General_dataRepository;
import org.megapractical.billingplatform.service.General_dataService;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the General_dataResource REST controller.
 *
 * @see General_dataResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class General_dataResourceIntTest {

    private static final String DEFAULT_PLATFORM_NAME = "AAAAA";
    private static final String UPDATED_PLATFORM_NAME = "BBBBB";
    private static final String DEFAULT_FORMAT_DATE = "AAAAA";
    private static final String UPDATED_FORMAT_DATE = "BBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_ADREES = "AAAAA";
    private static final String UPDATED_ADREES = "BBBBB";
    private static final String DEFAULT_PHONES = "AAAAA";
    private static final String UPDATED_PHONES = "BBBBB";
    private static final String DEFAULT_PATH_ROOT = "AAAAA";
    private static final String UPDATED_PATH_ROOT = "BBBBB";

    @Inject
    private General_dataRepository general_dataRepository;

    @Inject
    private General_dataService general_dataService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGeneral_dataMockMvc;

    private General_data general_data;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        General_dataResource general_dataResource = new General_dataResource();
        ReflectionTestUtils.setField(general_dataResource, "general_dataService", general_dataService);
        this.restGeneral_dataMockMvc = MockMvcBuilders.standaloneSetup(general_dataResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        general_data = new General_data();
        general_data.setPlatform_name(DEFAULT_PLATFORM_NAME);
        general_data.setFormat_date(DEFAULT_FORMAT_DATE);
        general_data.setLogo(DEFAULT_LOGO);
        general_data.setLogoContentType(DEFAULT_LOGO_CONTENT_TYPE);
        general_data.setAdrees(DEFAULT_ADREES);
        general_data.setPhones(DEFAULT_PHONES);
        general_data.setPath_root(DEFAULT_PATH_ROOT);
    }

    @Test
    @Transactional
    public void createGeneral_data() throws Exception {
        int databaseSizeBeforeCreate = general_dataRepository.findAll().size();

        // Create the General_data

        restGeneral_dataMockMvc.perform(post("/api/general-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(general_data)))
                .andExpect(status().isCreated());

        // Validate the General_data in the database
        List<General_data> general_data = general_dataRepository.findAll();
        assertThat(general_data).hasSize(databaseSizeBeforeCreate + 1);
        General_data testGeneral_data = general_data.get(general_data.size() - 1);
        assertThat(testGeneral_data.getPlatform_name()).isEqualTo(DEFAULT_PLATFORM_NAME);
        assertThat(testGeneral_data.getFormat_date()).isEqualTo(DEFAULT_FORMAT_DATE);
        assertThat(testGeneral_data.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testGeneral_data.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testGeneral_data.getAdrees()).isEqualTo(DEFAULT_ADREES);
        assertThat(testGeneral_data.getPhones()).isEqualTo(DEFAULT_PHONES);
        assertThat(testGeneral_data.getPath_root()).isEqualTo(DEFAULT_PATH_ROOT);
    }

    @Test
    @Transactional
    public void getAllGeneral_data() throws Exception {
        // Initialize the database
        general_dataRepository.saveAndFlush(general_data);

        // Get all the general_data
        restGeneral_dataMockMvc.perform(get("/api/general-data?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(general_data.getId().intValue())))
                .andExpect(jsonPath("$.[*].platform_name").value(hasItem(DEFAULT_PLATFORM_NAME.toString())))
                .andExpect(jsonPath("$.[*].format_date").value(hasItem(DEFAULT_FORMAT_DATE.toString())))
                .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
                .andExpect(jsonPath("$.[*].adrees").value(hasItem(DEFAULT_ADREES.toString())))
                .andExpect(jsonPath("$.[*].phones").value(hasItem(DEFAULT_PHONES.toString())))
                .andExpect(jsonPath("$.[*].path_root").value(hasItem(DEFAULT_PATH_ROOT.toString())));
    }

    @Test
    @Transactional
    public void getGeneral_data() throws Exception {
        // Initialize the database
        general_dataRepository.saveAndFlush(general_data);

        // Get the general_data
        restGeneral_dataMockMvc.perform(get("/api/general-data/{id}", general_data.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(general_data.getId().intValue()))
            .andExpect(jsonPath("$.platform_name").value(DEFAULT_PLATFORM_NAME.toString()))
            .andExpect(jsonPath("$.format_date").value(DEFAULT_FORMAT_DATE.toString()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.adrees").value(DEFAULT_ADREES.toString()))
            .andExpect(jsonPath("$.phones").value(DEFAULT_PHONES.toString()))
            .andExpect(jsonPath("$.path_root").value(DEFAULT_PATH_ROOT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeneral_data() throws Exception {
        // Get the general_data
        restGeneral_dataMockMvc.perform(get("/api/general-data/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeneral_data() throws Exception {
        // Initialize the database
        general_dataService.save(general_data);

        int databaseSizeBeforeUpdate = general_dataRepository.findAll().size();

        // Update the general_data
        General_data updatedGeneral_data = new General_data();
        updatedGeneral_data.setId(general_data.getId());
        updatedGeneral_data.setPlatform_name(UPDATED_PLATFORM_NAME);
        updatedGeneral_data.setFormat_date(UPDATED_FORMAT_DATE);
        updatedGeneral_data.setLogo(UPDATED_LOGO);
        updatedGeneral_data.setLogoContentType(UPDATED_LOGO_CONTENT_TYPE);
        updatedGeneral_data.setAdrees(UPDATED_ADREES);
        updatedGeneral_data.setPhones(UPDATED_PHONES);
        updatedGeneral_data.setPath_root(UPDATED_PATH_ROOT);

        restGeneral_dataMockMvc.perform(put("/api/general-data")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedGeneral_data)))
                .andExpect(status().isOk());

        // Validate the General_data in the database
        List<General_data> general_data = general_dataRepository.findAll();
        assertThat(general_data).hasSize(databaseSizeBeforeUpdate);
        General_data testGeneral_data = general_data.get(general_data.size() - 1);
        assertThat(testGeneral_data.getPlatform_name()).isEqualTo(UPDATED_PLATFORM_NAME);
        assertThat(testGeneral_data.getFormat_date()).isEqualTo(UPDATED_FORMAT_DATE);
        assertThat(testGeneral_data.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testGeneral_data.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testGeneral_data.getAdrees()).isEqualTo(UPDATED_ADREES);
        assertThat(testGeneral_data.getPhones()).isEqualTo(UPDATED_PHONES);
        assertThat(testGeneral_data.getPath_root()).isEqualTo(UPDATED_PATH_ROOT);
    }

    @Test
    @Transactional
    public void deleteGeneral_data() throws Exception {
        // Initialize the database
        general_dataService.save(general_data);

        int databaseSizeBeforeDelete = general_dataRepository.findAll().size();

        // Get the general_data
        restGeneral_dataMockMvc.perform(delete("/api/general-data/{id}", general_data.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<General_data> general_data = general_dataRepository.findAll();
        assertThat(general_data).hasSize(databaseSizeBeforeDelete - 1);
    }
}
