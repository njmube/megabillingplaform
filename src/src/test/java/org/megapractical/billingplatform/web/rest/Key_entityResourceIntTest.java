package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Key_entity;
import org.megapractical.billingplatform.repository.Key_entityRepository;
import org.megapractical.billingplatform.service.Key_entityService;

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
 * Test class for the Key_entityResource REST controller.
 *
 * @see Key_entityResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Key_entityResourceIntTest {

    private static final String DEFAULT_KEY = "AAAAA";
    private static final String UPDATED_KEY = "BBBBB";

    @Inject
    private Key_entityRepository key_entityRepository;

    @Inject
    private Key_entityService key_entityService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restKey_entityMockMvc;

    private Key_entity key_entity;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Key_entityResource key_entityResource = new Key_entityResource();
        ReflectionTestUtils.setField(key_entityResource, "key_entityService", key_entityService);
        this.restKey_entityMockMvc = MockMvcBuilders.standaloneSetup(key_entityResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        key_entity = new Key_entity();
        key_entity.setKey(DEFAULT_KEY);
    }

    @Test
    @Transactional
    public void createKey_entity() throws Exception {
        int databaseSizeBeforeCreate = key_entityRepository.findAll().size();

        // Create the Key_entity

        restKey_entityMockMvc.perform(post("/api/key-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(key_entity)))
                .andExpect(status().isCreated());

        // Validate the Key_entity in the database
        List<Key_entity> key_entities = key_entityRepository.findAll();
        assertThat(key_entities).hasSize(databaseSizeBeforeCreate + 1);
        Key_entity testKey_entity = key_entities.get(key_entities.size() - 1);
        assertThat(testKey_entity.getKey()).isEqualTo(DEFAULT_KEY);
    }

    @Test
    @Transactional
    public void checkKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = key_entityRepository.findAll().size();
        // set the field null
        key_entity.setKey(null);

        // Create the Key_entity, which fails.

        restKey_entityMockMvc.perform(post("/api/key-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(key_entity)))
                .andExpect(status().isBadRequest());

        List<Key_entity> key_entities = key_entityRepository.findAll();
        assertThat(key_entities).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKey_entities() throws Exception {
        // Initialize the database
        key_entityRepository.saveAndFlush(key_entity);

        // Get all the key_entities
        restKey_entityMockMvc.perform(get("/api/key-entities?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(key_entity.getId().intValue())))
                .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())));
    }

    @Test
    @Transactional
    public void getKey_entity() throws Exception {
        // Initialize the database
        key_entityRepository.saveAndFlush(key_entity);

        // Get the key_entity
        restKey_entityMockMvc.perform(get("/api/key-entities/{id}", key_entity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(key_entity.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKey_entity() throws Exception {
        // Get the key_entity
        restKey_entityMockMvc.perform(get("/api/key-entities/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKey_entity() throws Exception {
        // Initialize the database
        key_entityService.save(key_entity);

        int databaseSizeBeforeUpdate = key_entityRepository.findAll().size();

        // Update the key_entity
        Key_entity updatedKey_entity = new Key_entity();
        updatedKey_entity.setId(key_entity.getId());
        updatedKey_entity.setKey(UPDATED_KEY);

        restKey_entityMockMvc.perform(put("/api/key-entities")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedKey_entity)))
                .andExpect(status().isOk());

        // Validate the Key_entity in the database
        List<Key_entity> key_entities = key_entityRepository.findAll();
        assertThat(key_entities).hasSize(databaseSizeBeforeUpdate);
        Key_entity testKey_entity = key_entities.get(key_entities.size() - 1);
        assertThat(testKey_entity.getKey()).isEqualTo(UPDATED_KEY);
    }

    @Test
    @Transactional
    public void deleteKey_entity() throws Exception {
        // Initialize the database
        key_entityService.save(key_entity);

        int databaseSizeBeforeDelete = key_entityRepository.findAll().size();

        // Get the key_entity
        restKey_entityMockMvc.perform(delete("/api/key-entities/{id}", key_entity.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Key_entity> key_entities = key_entityRepository.findAll();
        assertThat(key_entities).hasSize(databaseSizeBeforeDelete - 1);
    }
}
