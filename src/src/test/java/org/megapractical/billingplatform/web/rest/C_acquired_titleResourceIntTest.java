package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_acquired_title;
import org.megapractical.billingplatform.repository.C_acquired_titleRepository;
import org.megapractical.billingplatform.service.C_acquired_titleService;

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
 * Test class for the C_acquired_titleResource REST controller.
 *
 * @see C_acquired_titleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_acquired_titleResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private C_acquired_titleRepository c_acquired_titleRepository;

    @Inject
    private C_acquired_titleService c_acquired_titleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_acquired_titleMockMvc;

    private C_acquired_title c_acquired_title;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_acquired_titleResource c_acquired_titleResource = new C_acquired_titleResource();
        ReflectionTestUtils.setField(c_acquired_titleResource, "c_acquired_titleService", c_acquired_titleService);
        this.restC_acquired_titleMockMvc = MockMvcBuilders.standaloneSetup(c_acquired_titleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_acquired_title = new C_acquired_title();
        c_acquired_title.setCode(DEFAULT_CODE);
        c_acquired_title.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createC_acquired_title() throws Exception {
        int databaseSizeBeforeCreate = c_acquired_titleRepository.findAll().size();

        // Create the C_acquired_title

        restC_acquired_titleMockMvc.perform(post("/api/c-acquired-titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_acquired_title)))
                .andExpect(status().isCreated());

        // Validate the C_acquired_title in the database
        List<C_acquired_title> c_acquired_titles = c_acquired_titleRepository.findAll();
        assertThat(c_acquired_titles).hasSize(databaseSizeBeforeCreate + 1);
        C_acquired_title testC_acquired_title = c_acquired_titles.get(c_acquired_titles.size() - 1);
        assertThat(testC_acquired_title.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testC_acquired_title.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_acquired_titleRepository.findAll().size();
        // set the field null
        c_acquired_title.setCode(null);

        // Create the C_acquired_title, which fails.

        restC_acquired_titleMockMvc.perform(post("/api/c-acquired-titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_acquired_title)))
                .andExpect(status().isBadRequest());

        List<C_acquired_title> c_acquired_titles = c_acquired_titleRepository.findAll();
        assertThat(c_acquired_titles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_acquired_titleRepository.findAll().size();
        // set the field null
        c_acquired_title.setName(null);

        // Create the C_acquired_title, which fails.

        restC_acquired_titleMockMvc.perform(post("/api/c-acquired-titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_acquired_title)))
                .andExpect(status().isBadRequest());

        List<C_acquired_title> c_acquired_titles = c_acquired_titleRepository.findAll();
        assertThat(c_acquired_titles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_acquired_titles() throws Exception {
        // Initialize the database
        c_acquired_titleRepository.saveAndFlush(c_acquired_title);

        // Get all the c_acquired_titles
        restC_acquired_titleMockMvc.perform(get("/api/c-acquired-titles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_acquired_title.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getC_acquired_title() throws Exception {
        // Initialize the database
        c_acquired_titleRepository.saveAndFlush(c_acquired_title);

        // Get the c_acquired_title
        restC_acquired_titleMockMvc.perform(get("/api/c-acquired-titles/{id}", c_acquired_title.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_acquired_title.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_acquired_title() throws Exception {
        // Get the c_acquired_title
        restC_acquired_titleMockMvc.perform(get("/api/c-acquired-titles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_acquired_title() throws Exception {
        // Initialize the database
        c_acquired_titleService.save(c_acquired_title);

        int databaseSizeBeforeUpdate = c_acquired_titleRepository.findAll().size();

        // Update the c_acquired_title
        C_acquired_title updatedC_acquired_title = new C_acquired_title();
        updatedC_acquired_title.setId(c_acquired_title.getId());
        updatedC_acquired_title.setCode(UPDATED_CODE);
        updatedC_acquired_title.setName(UPDATED_NAME);

        restC_acquired_titleMockMvc.perform(put("/api/c-acquired-titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_acquired_title)))
                .andExpect(status().isOk());

        // Validate the C_acquired_title in the database
        List<C_acquired_title> c_acquired_titles = c_acquired_titleRepository.findAll();
        assertThat(c_acquired_titles).hasSize(databaseSizeBeforeUpdate);
        C_acquired_title testC_acquired_title = c_acquired_titles.get(c_acquired_titles.size() - 1);
        assertThat(testC_acquired_title.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testC_acquired_title.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteC_acquired_title() throws Exception {
        // Initialize the database
        c_acquired_titleService.save(c_acquired_title);

        int databaseSizeBeforeDelete = c_acquired_titleRepository.findAll().size();

        // Get the c_acquired_title
        restC_acquired_titleMockMvc.perform(delete("/api/c-acquired-titles/{id}", c_acquired_title.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_acquired_title> c_acquired_titles = c_acquired_titleRepository.findAll();
        assertThat(c_acquired_titles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
