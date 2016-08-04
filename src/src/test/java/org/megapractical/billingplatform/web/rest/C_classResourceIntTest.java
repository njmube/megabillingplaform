package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.C_class;
import org.megapractical.billingplatform.repository.C_classRepository;
import org.megapractical.billingplatform.service.C_classService;

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
 * Test class for the C_classResource REST controller.
 *
 * @see C_classResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class C_classResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private C_classRepository c_classRepository;

    @Inject
    private C_classService c_classService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restC_classMockMvc;

    private C_class c_class;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        C_classResource c_classResource = new C_classResource();
        ReflectionTestUtils.setField(c_classResource, "c_classService", c_classService);
        this.restC_classMockMvc = MockMvcBuilders.standaloneSetup(c_classResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        c_class = new C_class();
        c_class.setName(DEFAULT_NAME);
        c_class.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createC_class() throws Exception {
        int databaseSizeBeforeCreate = c_classRepository.findAll().size();

        // Create the C_class

        restC_classMockMvc.perform(post("/api/c-classes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_class)))
                .andExpect(status().isCreated());

        // Validate the C_class in the database
        List<C_class> c_classes = c_classRepository.findAll();
        assertThat(c_classes).hasSize(databaseSizeBeforeCreate + 1);
        C_class testC_class = c_classes.get(c_classes.size() - 1);
        assertThat(testC_class.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testC_class.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = c_classRepository.findAll().size();
        // set the field null
        c_class.setName(null);

        // Create the C_class, which fails.

        restC_classMockMvc.perform(post("/api/c-classes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(c_class)))
                .andExpect(status().isBadRequest());

        List<C_class> c_classes = c_classRepository.findAll();
        assertThat(c_classes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllC_classes() throws Exception {
        // Initialize the database
        c_classRepository.saveAndFlush(c_class);

        // Get all the c_classes
        restC_classMockMvc.perform(get("/api/c-classes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(c_class.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getC_class() throws Exception {
        // Initialize the database
        c_classRepository.saveAndFlush(c_class);

        // Get the c_class
        restC_classMockMvc.perform(get("/api/c-classes/{id}", c_class.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(c_class.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingC_class() throws Exception {
        // Get the c_class
        restC_classMockMvc.perform(get("/api/c-classes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateC_class() throws Exception {
        // Initialize the database
        c_classService.save(c_class);

        int databaseSizeBeforeUpdate = c_classRepository.findAll().size();

        // Update the c_class
        C_class updatedC_class = new C_class();
        updatedC_class.setId(c_class.getId());
        updatedC_class.setName(UPDATED_NAME);
        updatedC_class.setDescription(UPDATED_DESCRIPTION);

        restC_classMockMvc.perform(put("/api/c-classes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedC_class)))
                .andExpect(status().isOk());

        // Validate the C_class in the database
        List<C_class> c_classes = c_classRepository.findAll();
        assertThat(c_classes).hasSize(databaseSizeBeforeUpdate);
        C_class testC_class = c_classes.get(c_classes.size() - 1);
        assertThat(testC_class.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testC_class.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteC_class() throws Exception {
        // Initialize the database
        c_classService.save(c_class);

        int databaseSizeBeforeDelete = c_classRepository.findAll().size();

        // Get the c_class
        restC_classMockMvc.perform(delete("/api/c-classes/{id}", c_class.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<C_class> c_classes = c_classRepository.findAll();
        assertThat(c_classes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
