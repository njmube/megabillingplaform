package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_product_key;
import org.megapractical.billingplatform.repository.Freecom_product_keyRepository;
import org.megapractical.billingplatform.service.Freecom_product_keyService;

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
 * Test class for the Freecom_product_keyResource REST controller.
 *
 * @see Freecom_product_keyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_product_keyResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    @Inject
    private Freecom_product_keyRepository freecom_product_keyRepository;

    @Inject
    private Freecom_product_keyService freecom_product_keyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_product_keyMockMvc;

    private Freecom_product_key freecom_product_key;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_product_keyResource freecom_product_keyResource = new Freecom_product_keyResource();
        ReflectionTestUtils.setField(freecom_product_keyResource, "freecom_product_keyService", freecom_product_keyService);
        this.restFreecom_product_keyMockMvc = MockMvcBuilders.standaloneSetup(freecom_product_keyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_product_key = new Freecom_product_key();
        freecom_product_key.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createFreecom_product_key() throws Exception {
        int databaseSizeBeforeCreate = freecom_product_keyRepository.findAll().size();

        // Create the Freecom_product_key

        restFreecom_product_keyMockMvc.perform(post("/api/freecom-product-keys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_product_key)))
                .andExpect(status().isCreated());

        // Validate the Freecom_product_key in the database
        List<Freecom_product_key> freecom_product_keys = freecom_product_keyRepository.findAll();
        assertThat(freecom_product_keys).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_product_key testFreecom_product_key = freecom_product_keys.get(freecom_product_keys.size() - 1);
        assertThat(testFreecom_product_key.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_product_keyRepository.findAll().size();
        // set the field null
        freecom_product_key.setCode(null);

        // Create the Freecom_product_key, which fails.

        restFreecom_product_keyMockMvc.perform(post("/api/freecom-product-keys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_product_key)))
                .andExpect(status().isBadRequest());

        List<Freecom_product_key> freecom_product_keys = freecom_product_keyRepository.findAll();
        assertThat(freecom_product_keys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_product_keys() throws Exception {
        // Initialize the database
        freecom_product_keyRepository.saveAndFlush(freecom_product_key);

        // Get all the freecom_product_keys
        restFreecom_product_keyMockMvc.perform(get("/api/freecom-product-keys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_product_key.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_product_key() throws Exception {
        // Initialize the database
        freecom_product_keyRepository.saveAndFlush(freecom_product_key);

        // Get the freecom_product_key
        restFreecom_product_keyMockMvc.perform(get("/api/freecom-product-keys/{id}", freecom_product_key.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_product_key.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_product_key() throws Exception {
        // Get the freecom_product_key
        restFreecom_product_keyMockMvc.perform(get("/api/freecom-product-keys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_product_key() throws Exception {
        // Initialize the database
        freecom_product_keyService.save(freecom_product_key);

        int databaseSizeBeforeUpdate = freecom_product_keyRepository.findAll().size();

        // Update the freecom_product_key
        Freecom_product_key updatedFreecom_product_key = new Freecom_product_key();
        updatedFreecom_product_key.setId(freecom_product_key.getId());
        updatedFreecom_product_key.setCode(UPDATED_CODE);

        restFreecom_product_keyMockMvc.perform(put("/api/freecom-product-keys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_product_key)))
                .andExpect(status().isOk());

        // Validate the Freecom_product_key in the database
        List<Freecom_product_key> freecom_product_keys = freecom_product_keyRepository.findAll();
        assertThat(freecom_product_keys).hasSize(databaseSizeBeforeUpdate);
        Freecom_product_key testFreecom_product_key = freecom_product_keys.get(freecom_product_keys.size() - 1);
        assertThat(testFreecom_product_key.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteFreecom_product_key() throws Exception {
        // Initialize the database
        freecom_product_keyService.save(freecom_product_key);

        int databaseSizeBeforeDelete = freecom_product_keyRepository.findAll().size();

        // Get the freecom_product_key
        restFreecom_product_keyMockMvc.perform(delete("/api/freecom-product-keys/{id}", freecom_product_key.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_product_key> freecom_product_keys = freecom_product_keyRepository.findAll();
        assertThat(freecom_product_keys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
