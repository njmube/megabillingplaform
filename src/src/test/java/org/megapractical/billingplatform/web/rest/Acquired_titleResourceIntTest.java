package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Acquired_title;
import org.megapractical.billingplatform.repository.Acquired_titleRepository;
import org.megapractical.billingplatform.service.Acquired_titleService;

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
 * Test class for the Acquired_titleResource REST controller.
 *
 * @see Acquired_titleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Acquired_titleResourceIntTest {

    private static final String DEFAULT_CODE = "AAA";
    private static final String UPDATED_CODE = "BBB";

    @Inject
    private Acquired_titleRepository acquired_titleRepository;

    @Inject
    private Acquired_titleService acquired_titleService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAcquired_titleMockMvc;

    private Acquired_title acquired_title;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Acquired_titleResource acquired_titleResource = new Acquired_titleResource();
        ReflectionTestUtils.setField(acquired_titleResource, "acquired_titleService", acquired_titleService);
        this.restAcquired_titleMockMvc = MockMvcBuilders.standaloneSetup(acquired_titleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        acquired_title = new Acquired_title();
        acquired_title.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createAcquired_title() throws Exception {
        int databaseSizeBeforeCreate = acquired_titleRepository.findAll().size();

        // Create the Acquired_title

        restAcquired_titleMockMvc.perform(post("/api/acquired-titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(acquired_title)))
                .andExpect(status().isCreated());

        // Validate the Acquired_title in the database
        List<Acquired_title> acquired_titles = acquired_titleRepository.findAll();
        assertThat(acquired_titles).hasSize(databaseSizeBeforeCreate + 1);
        Acquired_title testAcquired_title = acquired_titles.get(acquired_titles.size() - 1);
        assertThat(testAcquired_title.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = acquired_titleRepository.findAll().size();
        // set the field null
        acquired_title.setCode(null);

        // Create the Acquired_title, which fails.

        restAcquired_titleMockMvc.perform(post("/api/acquired-titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(acquired_title)))
                .andExpect(status().isBadRequest());

        List<Acquired_title> acquired_titles = acquired_titleRepository.findAll();
        assertThat(acquired_titles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAcquired_titles() throws Exception {
        // Initialize the database
        acquired_titleRepository.saveAndFlush(acquired_title);

        // Get all the acquired_titles
        restAcquired_titleMockMvc.perform(get("/api/acquired-titles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(acquired_title.getId().intValue())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getAcquired_title() throws Exception {
        // Initialize the database
        acquired_titleRepository.saveAndFlush(acquired_title);

        // Get the acquired_title
        restAcquired_titleMockMvc.perform(get("/api/acquired-titles/{id}", acquired_title.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(acquired_title.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAcquired_title() throws Exception {
        // Get the acquired_title
        restAcquired_titleMockMvc.perform(get("/api/acquired-titles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcquired_title() throws Exception {
        // Initialize the database
        acquired_titleService.save(acquired_title);

        int databaseSizeBeforeUpdate = acquired_titleRepository.findAll().size();

        // Update the acquired_title
        Acquired_title updatedAcquired_title = new Acquired_title();
        updatedAcquired_title.setId(acquired_title.getId());
        updatedAcquired_title.setCode(UPDATED_CODE);

        restAcquired_titleMockMvc.perform(put("/api/acquired-titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAcquired_title)))
                .andExpect(status().isOk());

        // Validate the Acquired_title in the database
        List<Acquired_title> acquired_titles = acquired_titleRepository.findAll();
        assertThat(acquired_titles).hasSize(databaseSizeBeforeUpdate);
        Acquired_title testAcquired_title = acquired_titles.get(acquired_titles.size() - 1);
        assertThat(testAcquired_title.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteAcquired_title() throws Exception {
        // Initialize the database
        acquired_titleService.save(acquired_title);

        int databaseSizeBeforeDelete = acquired_titleRepository.findAll().size();

        // Get the acquired_title
        restAcquired_titleMockMvc.perform(delete("/api/acquired-titles/{id}", acquired_title.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Acquired_title> acquired_titles = acquired_titleRepository.findAll();
        assertThat(acquired_titles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
