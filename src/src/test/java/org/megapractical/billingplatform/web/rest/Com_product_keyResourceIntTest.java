package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_product_key;
import org.megapractical.billingplatform.repository.Com_product_keyRepository;
import org.megapractical.billingplatform.service.Com_product_keyService;

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
 * Test class for the Com_product_keyResource REST controller.
 *
 * @see Com_product_keyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_product_keyResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    @Inject
    private Com_product_keyRepository com_product_keyRepository;

    @Inject
    private Com_product_keyService com_product_keyService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_product_keyMockMvc;

    private Com_product_key com_product_key;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_product_keyResource com_product_keyResource = new Com_product_keyResource();
        ReflectionTestUtils.setField(com_product_keyResource, "com_product_keyService", com_product_keyService);
        this.restCom_product_keyMockMvc = MockMvcBuilders.standaloneSetup(com_product_keyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_product_key = new Com_product_key();
        com_product_key.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createCom_product_key() throws Exception {
        int databaseSizeBeforeCreate = com_product_keyRepository.findAll().size();

        // Create the Com_product_key

        restCom_product_keyMockMvc.perform(post("/api/com-product-keys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_product_key)))
                .andExpect(status().isCreated());

        // Validate the Com_product_key in the database
        List<Com_product_key> com_product_keys = com_product_keyRepository.findAll();
        assertThat(com_product_keys).hasSize(databaseSizeBeforeCreate + 1);
        Com_product_key testCom_product_key = com_product_keys.get(com_product_keys.size() - 1);
        assertThat(testCom_product_key.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_product_keyRepository.findAll().size();
        // set the field null
        com_product_key.setCode(null);

        // Create the Com_product_key, which fails.

        restCom_product_keyMockMvc.perform(post("/api/com-product-keys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_product_key)))
                .andExpect(status().isBadRequest());

        List<Com_product_key> com_product_keys = com_product_keyRepository.findAll();
        assertThat(com_product_keys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_product_keys() throws Exception {
        // Initialize the database
        com_product_keyRepository.saveAndFlush(com_product_key);

        // Get all the com_product_keys
        restCom_product_keyMockMvc.perform(get("/api/com-product-keys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_product_key.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getCom_product_key() throws Exception {
        // Initialize the database
        com_product_keyRepository.saveAndFlush(com_product_key);

        // Get the com_product_key
        restCom_product_keyMockMvc.perform(get("/api/com-product-keys/{id}", com_product_key.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_product_key.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_product_key() throws Exception {
        // Get the com_product_key
        restCom_product_keyMockMvc.perform(get("/api/com-product-keys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_product_key() throws Exception {
        // Initialize the database
        com_product_keyService.save(com_product_key);

        int databaseSizeBeforeUpdate = com_product_keyRepository.findAll().size();

        // Update the com_product_key
        Com_product_key updatedCom_product_key = new Com_product_key();
        updatedCom_product_key.setId(com_product_key.getId());
        updatedCom_product_key.setCode(UPDATED_CODE);

        restCom_product_keyMockMvc.perform(put("/api/com-product-keys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_product_key)))
                .andExpect(status().isOk());

        // Validate the Com_product_key in the database
        List<Com_product_key> com_product_keys = com_product_keyRepository.findAll();
        assertThat(com_product_keys).hasSize(databaseSizeBeforeUpdate);
        Com_product_key testCom_product_key = com_product_keys.get(com_product_keys.size() - 1);
        assertThat(testCom_product_key.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteCom_product_key() throws Exception {
        // Initialize the database
        com_product_keyService.save(com_product_key);

        int databaseSizeBeforeDelete = com_product_keyRepository.findAll().size();

        // Get the com_product_key
        restCom_product_keyMockMvc.perform(delete("/api/com-product-keys/{id}", com_product_key.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_product_key> com_product_keys = com_product_keyRepository.findAll();
        assertThat(com_product_keys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
