package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_tar;
import org.megapractical.billingplatform.repository.C_tarRepository;
import org.megapractical.billingplatform.service.C_tarService;

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
 * Test class for the C_tarResource REST controller.
 *
 * @see C_tarResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_tarResourceIntTest {

    private static final String DEFAULT_NAME = "AAA";
    private static final String UPDATED_NAME = "BBB";

    @Inject
    private C_tarRepository c_tarRepository;

    @Inject
    private C_tarService c_tarService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_tarMockMvc;

    private C_tar c_tar;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_tarResource c_tarResource = new C_tarResource();
        ReflectionTestUtils.setField(c_tarResource, "c_tarService", c_tarService);
        this.restC_tarMockMvc = MockMvcBuilders.standaloneSetup(c_tarResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_tar = new C_tar();
        c_tar.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createC_tar() throws Exception {
        int databaseSizeBeforeCreate = c_tarRepository.findAll().size();

        // Create the C_tar

        restC_tarMockMvc.perform(post("/api/c-tars")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_tar)))
                .andExpect(status().isCreated());

        // Validate the C_tar in the database
        List<C_tar> c_tars = c_tarRepository.findAll();
        assertThat(c_tars).hasSize(databaseSizeBeforeCreate + 1);
        C_tar testC_tar = c_tars.get(c_tars.size() - 1);
        assertThat(testC_tar.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_tarRepository.findAll().size();
        // set the field null
        c_tar.setName(null);

        // Create the C_tar, which fails.

        restC_tarMockMvc.perform(post("/api/c-tars")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_tar)))
                .andExpect(status().isBadRequest());

        List<C_tar> c_tars = c_tarRepository.findAll();
        assertThat(c_tars).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_tars() throws Exception {
        // Initialize the database
        c_tarRepository.saveAndFlush(c_tar);

        // Get all the c_tars
        restC_tarMockMvc.perform(get("/api/c-tars?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_tar.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getC_tar() throws Exception {
        // Initialize the database
        c_tarRepository.saveAndFlush(c_tar);

        // Get the c_tar
        restC_tarMockMvc.perform(get("/api/c-tars/{id}", c_tar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_tar.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_tar() throws Exception {
        // Get the c_tar
        restC_tarMockMvc.perform(get("/api/c-tars/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_tar() throws Exception {
        // Initialize the database
        c_tarService.save(c_tar);

        int databaseSizeBeforeUpdate = c_tarRepository.findAll().size();

        // Update the c_tar
        C_tar updatedC_tar = new C_tar();
        updatedC_tar.setId(c_tar.getId());
        updatedC_tar.setName(UPDATED_NAME);

        restC_tarMockMvc.perform(put("/api/c-tars")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_tar)))
                .andExpect(status().isOk());

        // Validate the C_tar in the database
        List<C_tar> c_tars = c_tarRepository.findAll();
        assertThat(c_tars).hasSize(databaseSizeBeforeUpdate);
        C_tar testC_tar = c_tars.get(c_tars.size() - 1);
        assertThat(testC_tar.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteC_tar() throws Exception {
        // Initialize the database
        c_tarService.save(c_tar);

        int databaseSizeBeforeDelete = c_tarRepository.findAll().size();

        // Get the c_tar
        restC_tarMockMvc.perform(delete("/api/c-tars/{id}", c_tar.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_tar> c_tars = c_tarRepository.findAll();
        assertThat(c_tars).hasSize(databaseSizeBeforeDelete - 1);
    }
}
