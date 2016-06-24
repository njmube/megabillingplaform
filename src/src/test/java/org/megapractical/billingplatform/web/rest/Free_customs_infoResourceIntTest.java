package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Free_customs_info;
import org.megapractical.billingplatform.repository.Free_customs_infoRepository;
import org.megapractical.billingplatform.service.Free_customs_infoService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Free_customs_infoResource REST controller.
 *
 * @see Free_customs_infoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Free_customs_infoResourceIntTest {

    private static final String DEFAULT_NUMBER_DOC = "AAAAA";
    private static final String UPDATED_NUMBER_DOC = "BBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CUSTOMS = "AAAAA";
    private static final String UPDATED_CUSTOMS = "BBBBB";

    @Inject
    private Free_customs_infoRepository free_customs_infoRepository;

    @Inject
    private Free_customs_infoService free_customs_infoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFree_customs_infoMockMvc;

    private Free_customs_info free_customs_info;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Free_customs_infoResource free_customs_infoResource = new Free_customs_infoResource();
        ReflectionTestUtils.setField(free_customs_infoResource, "free_customs_infoService", free_customs_infoService);
        this.restFree_customs_infoMockMvc = MockMvcBuilders.standaloneSetup(free_customs_infoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        free_customs_info = new Free_customs_info();
        free_customs_info.setNumber_doc(DEFAULT_NUMBER_DOC);
        free_customs_info.setDate(DEFAULT_DATE);
        free_customs_info.setCustoms(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void createFree_customs_info() throws Exception {
        int databaseSizeBeforeCreate = free_customs_infoRepository.findAll().size();

        // Create the Free_customs_info

        restFree_customs_infoMockMvc.perform(post("/api/free-customs-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_customs_info)))
                .andExpect(status().isCreated());

        // Validate the Free_customs_info in the database
        List<Free_customs_info> free_customs_infos = free_customs_infoRepository.findAll();
        assertThat(free_customs_infos).hasSize(databaseSizeBeforeCreate + 1);
        Free_customs_info testFree_customs_info = free_customs_infos.get(free_customs_infos.size() - 1);
        assertThat(testFree_customs_info.getNumber_doc()).isEqualTo(DEFAULT_NUMBER_DOC);
        assertThat(testFree_customs_info.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testFree_customs_info.getCustoms()).isEqualTo(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void checkNumber_docIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_customs_infoRepository.findAll().size();
        // set the field null
        free_customs_info.setNumber_doc(null);

        // Create the Free_customs_info, which fails.

        restFree_customs_infoMockMvc.perform(post("/api/free-customs-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_customs_info)))
                .andExpect(status().isBadRequest());

        List<Free_customs_info> free_customs_infos = free_customs_infoRepository.findAll();
        assertThat(free_customs_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = free_customs_infoRepository.findAll().size();
        // set the field null
        free_customs_info.setDate(null);

        // Create the Free_customs_info, which fails.

        restFree_customs_infoMockMvc.perform(post("/api/free-customs-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(free_customs_info)))
                .andExpect(status().isBadRequest());

        List<Free_customs_info> free_customs_infos = free_customs_infoRepository.findAll();
        assertThat(free_customs_infos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFree_customs_infos() throws Exception {
        // Initialize the database
        free_customs_infoRepository.saveAndFlush(free_customs_info);

        // Get all the free_customs_infos
        restFree_customs_infoMockMvc.perform(get("/api/free-customs-infos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(free_customs_info.getId().intValue())))
                .andExpect(jsonPath("$.[*].number_doc").value(hasItem(DEFAULT_NUMBER_DOC.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].customs").value(hasItem(DEFAULT_CUSTOMS.toString())));
    }

    @Test
    @Transactional
    public void getFree_customs_info() throws Exception {
        // Initialize the database
        free_customs_infoRepository.saveAndFlush(free_customs_info);

        // Get the free_customs_info
        restFree_customs_infoMockMvc.perform(get("/api/free-customs-infos/{id}", free_customs_info.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(free_customs_info.getId().intValue()))
            .andExpect(jsonPath("$.number_doc").value(DEFAULT_NUMBER_DOC.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.customs").value(DEFAULT_CUSTOMS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFree_customs_info() throws Exception {
        // Get the free_customs_info
        restFree_customs_infoMockMvc.perform(get("/api/free-customs-infos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFree_customs_info() throws Exception {
        // Initialize the database
        free_customs_infoService.save(free_customs_info);

        int databaseSizeBeforeUpdate = free_customs_infoRepository.findAll().size();

        // Update the free_customs_info
        Free_customs_info updatedFree_customs_info = new Free_customs_info();
        updatedFree_customs_info.setId(free_customs_info.getId());
        updatedFree_customs_info.setNumber_doc(UPDATED_NUMBER_DOC);
        updatedFree_customs_info.setDate(UPDATED_DATE);
        updatedFree_customs_info.setCustoms(UPDATED_CUSTOMS);

        restFree_customs_infoMockMvc.perform(put("/api/free-customs-infos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFree_customs_info)))
                .andExpect(status().isOk());

        // Validate the Free_customs_info in the database
        List<Free_customs_info> free_customs_infos = free_customs_infoRepository.findAll();
        assertThat(free_customs_infos).hasSize(databaseSizeBeforeUpdate);
        Free_customs_info testFree_customs_info = free_customs_infos.get(free_customs_infos.size() - 1);
        assertThat(testFree_customs_info.getNumber_doc()).isEqualTo(UPDATED_NUMBER_DOC);
        assertThat(testFree_customs_info.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testFree_customs_info.getCustoms()).isEqualTo(UPDATED_CUSTOMS);
    }

    @Test
    @Transactional
    public void deleteFree_customs_info() throws Exception {
        // Initialize the database
        free_customs_infoService.save(free_customs_info);

        int databaseSizeBeforeDelete = free_customs_infoRepository.findAll().size();

        // Get the free_customs_info
        restFree_customs_infoMockMvc.perform(delete("/api/free-customs-infos/{id}", free_customs_info.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Free_customs_info> free_customs_infos = free_customs_infoRepository.findAll();
        assertThat(free_customs_infos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
