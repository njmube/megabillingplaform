package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_zip_code;
import org.megapractical.billingplatform.repository.C_zip_codeRepository;
import org.megapractical.billingplatform.service.C_zip_codeService;

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
 * Test class for the C_zip_codeResource REST controller.
 *
 * @see C_zip_codeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_zip_codeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private C_zip_codeRepository c_zip_codeRepository;

    @Inject
    private C_zip_codeService c_zip_codeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_zip_codeMockMvc;

    private C_zip_code c_zip_code;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_zip_codeResource c_zip_codeResource = new C_zip_codeResource();
        ReflectionTestUtils.setField(c_zip_codeResource, "c_zip_codeService", c_zip_codeService);
        this.restC_zip_codeMockMvc = MockMvcBuilders.standaloneSetup(c_zip_codeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_zip_code = new C_zip_code();
        c_zip_code.setCode(DEFAULT_CODE);
        c_zip_code.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createC_zip_code() throws Exception {
        int databaseSizeBeforeCreate = c_zip_codeRepository.findAll().size();

        // Create the C_zip_code

        restC_zip_codeMockMvc.perform(post("/api/c-zip-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_zip_code)))
                .andExpect(status().isCreated());

        // Validate the C_zip_code in the database
        List<C_zip_code> c_zip_codes = c_zip_codeRepository.findAll();
        assertThat(c_zip_codes).hasSize(databaseSizeBeforeCreate + 1);
        C_zip_code testC_zip_code = c_zip_codes.get(c_zip_codes.size() - 1);
        assertThat(testC_zip_code.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testC_zip_code.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllC_zip_codes() throws Exception {
        // Initialize the database
        c_zip_codeRepository.saveAndFlush(c_zip_code);

        // Get all the c_zip_codes
        restC_zip_codeMockMvc.perform(get("/api/c-zip-codes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_zip_code.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getC_zip_code() throws Exception {
        // Initialize the database
        c_zip_codeRepository.saveAndFlush(c_zip_code);

        // Get the c_zip_code
        restC_zip_codeMockMvc.perform(get("/api/c-zip-codes/{id}", c_zip_code.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_zip_code.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_zip_code() throws Exception {
        // Get the c_zip_code
        restC_zip_codeMockMvc.perform(get("/api/c-zip-codes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_zip_code() throws Exception {
        // Initialize the database
        c_zip_codeService.save(c_zip_code);

        int databaseSizeBeforeUpdate = c_zip_codeRepository.findAll().size();

        // Update the c_zip_code
        C_zip_code updatedC_zip_code = new C_zip_code();
        updatedC_zip_code.setId(c_zip_code.getId());
        updatedC_zip_code.setCode(UPDATED_CODE);
        updatedC_zip_code.setValue(UPDATED_VALUE);

        restC_zip_codeMockMvc.perform(put("/api/c-zip-codes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_zip_code)))
                .andExpect(status().isOk());

        // Validate the C_zip_code in the database
        List<C_zip_code> c_zip_codes = c_zip_codeRepository.findAll();
        assertThat(c_zip_codes).hasSize(databaseSizeBeforeUpdate);
        C_zip_code testC_zip_code = c_zip_codes.get(c_zip_codes.size() - 1);
        assertThat(testC_zip_code.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testC_zip_code.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteC_zip_code() throws Exception {
        // Initialize the database
        c_zip_codeService.save(c_zip_code);

        int databaseSizeBeforeDelete = c_zip_codeRepository.findAll().size();

        // Get the c_zip_code
        restC_zip_codeMockMvc.perform(delete("/api/c-zip-codes/{id}", c_zip_code.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_zip_code> c_zip_codes = c_zip_codeRepository.findAll();
        assertThat(c_zip_codes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
