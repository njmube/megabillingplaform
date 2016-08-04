package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_info_customs_destruction;
import org.megapractical.billingplatform.repository.Freecom_info_customs_destructionRepository;
import org.megapractical.billingplatform.service.Freecom_info_customs_destructionService;

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
 * Test class for the Freecom_info_customs_destructionResource REST controller.
 *
 * @see Freecom_info_customs_destructionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_info_customs_destructionResourceIntTest {

    private static final String DEFAULT_NUMPEDIMP = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMPEDIMP = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_EXPEDITION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EXPEDITION = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_CUSTOMS = "AAAAA";
    private static final String UPDATED_CUSTOMS = "BBBBB";

    @Inject
    private Freecom_info_customs_destructionRepository freecom_info_customs_destructionRepository;

    @Inject
    private Freecom_info_customs_destructionService freecom_info_customs_destructionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_info_customs_destructionMockMvc;

    private Freecom_info_customs_destruction freecom_info_customs_destruction;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_info_customs_destructionResource freecom_info_customs_destructionResource = new Freecom_info_customs_destructionResource();
        ReflectionTestUtils.setField(freecom_info_customs_destructionResource, "freecom_info_customs_destructionService", freecom_info_customs_destructionService);
        this.restFreecom_info_customs_destructionMockMvc = MockMvcBuilders.standaloneSetup(freecom_info_customs_destructionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_info_customs_destruction = new Freecom_info_customs_destruction();
        freecom_info_customs_destruction.setNumpedimp(DEFAULT_NUMPEDIMP);
        freecom_info_customs_destruction.setDate_expedition(DEFAULT_DATE_EXPEDITION);
        freecom_info_customs_destruction.setCustoms(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void createFreecom_info_customs_destruction() throws Exception {
        int databaseSizeBeforeCreate = freecom_info_customs_destructionRepository.findAll().size();

        // Create the Freecom_info_customs_destruction

        restFreecom_info_customs_destructionMockMvc.perform(post("/api/freecom-info-customs-destructions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_info_customs_destruction)))
                .andExpect(status().isCreated());

        // Validate the Freecom_info_customs_destruction in the database
        List<Freecom_info_customs_destruction> freecom_info_customs_destructions = freecom_info_customs_destructionRepository.findAll();
        assertThat(freecom_info_customs_destructions).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_info_customs_destruction testFreecom_info_customs_destruction = freecom_info_customs_destructions.get(freecom_info_customs_destructions.size() - 1);
        assertThat(testFreecom_info_customs_destruction.getNumpedimp()).isEqualTo(DEFAULT_NUMPEDIMP);
        assertThat(testFreecom_info_customs_destruction.getDate_expedition()).isEqualTo(DEFAULT_DATE_EXPEDITION);
        assertThat(testFreecom_info_customs_destruction.getCustoms()).isEqualTo(DEFAULT_CUSTOMS);
    }

    @Test
    @Transactional
    public void checkNumpedimpIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_info_customs_destructionRepository.findAll().size();
        // set the field null
        freecom_info_customs_destruction.setNumpedimp(null);

        // Create the Freecom_info_customs_destruction, which fails.

        restFreecom_info_customs_destructionMockMvc.perform(post("/api/freecom-info-customs-destructions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_info_customs_destruction)))
                .andExpect(status().isBadRequest());

        List<Freecom_info_customs_destruction> freecom_info_customs_destructions = freecom_info_customs_destructionRepository.findAll();
        assertThat(freecom_info_customs_destructions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDate_expeditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_info_customs_destructionRepository.findAll().size();
        // set the field null
        freecom_info_customs_destruction.setDate_expedition(null);

        // Create the Freecom_info_customs_destruction, which fails.

        restFreecom_info_customs_destructionMockMvc.perform(post("/api/freecom-info-customs-destructions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_info_customs_destruction)))
                .andExpect(status().isBadRequest());

        List<Freecom_info_customs_destruction> freecom_info_customs_destructions = freecom_info_customs_destructionRepository.findAll();
        assertThat(freecom_info_customs_destructions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCustomsIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_info_customs_destructionRepository.findAll().size();
        // set the field null
        freecom_info_customs_destruction.setCustoms(null);

        // Create the Freecom_info_customs_destruction, which fails.

        restFreecom_info_customs_destructionMockMvc.perform(post("/api/freecom-info-customs-destructions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_info_customs_destruction)))
                .andExpect(status().isBadRequest());

        List<Freecom_info_customs_destruction> freecom_info_customs_destructions = freecom_info_customs_destructionRepository.findAll();
        assertThat(freecom_info_customs_destructions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_info_customs_destructions() throws Exception {
        // Initialize the database
        freecom_info_customs_destructionRepository.saveAndFlush(freecom_info_customs_destruction);

        // Get all the freecom_info_customs_destructions
        restFreecom_info_customs_destructionMockMvc.perform(get("/api/freecom-info-customs-destructions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_info_customs_destruction.getId().intValue())))
                .andExpect(jsonPath("$.[*].numpedimp").value(hasItem(DEFAULT_NUMPEDIMP.toString())))
                .andExpect(jsonPath("$.[*].date_expedition").value(hasItem(DEFAULT_DATE_EXPEDITION.toString())))
                .andExpect(jsonPath("$.[*].customs").value(hasItem(DEFAULT_CUSTOMS.toString())));
    }

    @Test
    @Transactional
    public void getFreecom_info_customs_destruction() throws Exception {
        // Initialize the database
        freecom_info_customs_destructionRepository.saveAndFlush(freecom_info_customs_destruction);

        // Get the freecom_info_customs_destruction
        restFreecom_info_customs_destructionMockMvc.perform(get("/api/freecom-info-customs-destructions/{id}", freecom_info_customs_destruction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_info_customs_destruction.getId().intValue()))
            .andExpect(jsonPath("$.numpedimp").value(DEFAULT_NUMPEDIMP.toString()))
            .andExpect(jsonPath("$.date_expedition").value(DEFAULT_DATE_EXPEDITION.toString()))
            .andExpect(jsonPath("$.customs").value(DEFAULT_CUSTOMS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_info_customs_destruction() throws Exception {
        // Get the freecom_info_customs_destruction
        restFreecom_info_customs_destructionMockMvc.perform(get("/api/freecom-info-customs-destructions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_info_customs_destruction() throws Exception {
        // Initialize the database
        freecom_info_customs_destructionService.save(freecom_info_customs_destruction);

        int databaseSizeBeforeUpdate = freecom_info_customs_destructionRepository.findAll().size();

        // Update the freecom_info_customs_destruction
        Freecom_info_customs_destruction updatedFreecom_info_customs_destruction = new Freecom_info_customs_destruction();
        updatedFreecom_info_customs_destruction.setId(freecom_info_customs_destruction.getId());
        updatedFreecom_info_customs_destruction.setNumpedimp(UPDATED_NUMPEDIMP);
        updatedFreecom_info_customs_destruction.setDate_expedition(UPDATED_DATE_EXPEDITION);
        updatedFreecom_info_customs_destruction.setCustoms(UPDATED_CUSTOMS);

        restFreecom_info_customs_destructionMockMvc.perform(put("/api/freecom-info-customs-destructions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_info_customs_destruction)))
                .andExpect(status().isOk());

        // Validate the Freecom_info_customs_destruction in the database
        List<Freecom_info_customs_destruction> freecom_info_customs_destructions = freecom_info_customs_destructionRepository.findAll();
        assertThat(freecom_info_customs_destructions).hasSize(databaseSizeBeforeUpdate);
        Freecom_info_customs_destruction testFreecom_info_customs_destruction = freecom_info_customs_destructions.get(freecom_info_customs_destructions.size() - 1);
        assertThat(testFreecom_info_customs_destruction.getNumpedimp()).isEqualTo(UPDATED_NUMPEDIMP);
        assertThat(testFreecom_info_customs_destruction.getDate_expedition()).isEqualTo(UPDATED_DATE_EXPEDITION);
        assertThat(testFreecom_info_customs_destruction.getCustoms()).isEqualTo(UPDATED_CUSTOMS);
    }

    @Test
    @Transactional
    public void deleteFreecom_info_customs_destruction() throws Exception {
        // Initialize the database
        freecom_info_customs_destructionService.save(freecom_info_customs_destruction);

        int databaseSizeBeforeDelete = freecom_info_customs_destructionRepository.findAll().size();

        // Get the freecom_info_customs_destruction
        restFreecom_info_customs_destructionMockMvc.perform(delete("/api/freecom-info-customs-destructions/{id}", freecom_info_customs_destruction.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_info_customs_destruction> freecom_info_customs_destructions = freecom_info_customs_destructionRepository.findAll();
        assertThat(freecom_info_customs_destructions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
