package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_dataacquiringcopsc;
import org.megapractical.billingplatform.repository.Freecom_dataacquiringcopscRepository;
import org.megapractical.billingplatform.service.Freecom_dataacquiringcopscService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Freecom_dataacquiringcopscResource REST controller.
 *
 * @see Freecom_dataacquiringcopscResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_dataacquiringcopscResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_LAST_NAME = "AAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBB";
    private static final String DEFAULT_MOTHER_LAST_NAME = "AAAAA";
    private static final String UPDATED_MOTHER_LAST_NAME = "BBBBB";
    private static final String DEFAULT_RFC = "AAAAA";
    private static final String UPDATED_RFC = "BBBBB";
    private static final String DEFAULT_CURP = "AAAAA";
    private static final String UPDATED_CURP = "BBBBB";

    private static final BigDecimal DEFAULT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PERCENTAGE = new BigDecimal(2);

    @Inject
    private Freecom_dataacquiringcopscRepository freecom_dataacquiringcopscRepository;

    @Inject
    private Freecom_dataacquiringcopscService freecom_dataacquiringcopscService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_dataacquiringcopscMockMvc;

    private Freecom_dataacquiringcopsc freecom_dataacquiringcopsc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_dataacquiringcopscResource freecom_dataacquiringcopscResource = new Freecom_dataacquiringcopscResource();
        ReflectionTestUtils.setField(freecom_dataacquiringcopscResource, "freecom_dataacquiringcopscService", freecom_dataacquiringcopscService);
        this.restFreecom_dataacquiringcopscMockMvc = MockMvcBuilders.standaloneSetup(freecom_dataacquiringcopscResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_dataacquiringcopsc = new Freecom_dataacquiringcopsc();
        freecom_dataacquiringcopsc.setName(DEFAULT_NAME);
        freecom_dataacquiringcopsc.setLast_name(DEFAULT_LAST_NAME);
        freecom_dataacquiringcopsc.setMother_last_name(DEFAULT_MOTHER_LAST_NAME);
        freecom_dataacquiringcopsc.setRfc(DEFAULT_RFC);
        freecom_dataacquiringcopsc.setCurp(DEFAULT_CURP);
        freecom_dataacquiringcopsc.setPercentage(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createFreecom_dataacquiringcopsc() throws Exception {
        int databaseSizeBeforeCreate = freecom_dataacquiringcopscRepository.findAll().size();

        // Create the Freecom_dataacquiringcopsc

        restFreecom_dataacquiringcopscMockMvc.perform(post("/api/freecom-dataacquiringcopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataacquiringcopsc)))
                .andExpect(status().isCreated());

        // Validate the Freecom_dataacquiringcopsc in the database
        List<Freecom_dataacquiringcopsc> freecom_dataacquiringcopscs = freecom_dataacquiringcopscRepository.findAll();
        assertThat(freecom_dataacquiringcopscs).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_dataacquiringcopsc testFreecom_dataacquiringcopsc = freecom_dataacquiringcopscs.get(freecom_dataacquiringcopscs.size() - 1);
        assertThat(testFreecom_dataacquiringcopsc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFreecom_dataacquiringcopsc.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testFreecom_dataacquiringcopsc.getMother_last_name()).isEqualTo(DEFAULT_MOTHER_LAST_NAME);
        assertThat(testFreecom_dataacquiringcopsc.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFreecom_dataacquiringcopsc.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testFreecom_dataacquiringcopsc.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataacquiringcopscRepository.findAll().size();
        // set the field null
        freecom_dataacquiringcopsc.setName(null);

        // Create the Freecom_dataacquiringcopsc, which fails.

        restFreecom_dataacquiringcopscMockMvc.perform(post("/api/freecom-dataacquiringcopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataacquiringcopsc)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataacquiringcopsc> freecom_dataacquiringcopscs = freecom_dataacquiringcopscRepository.findAll();
        assertThat(freecom_dataacquiringcopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataacquiringcopscRepository.findAll().size();
        // set the field null
        freecom_dataacquiringcopsc.setRfc(null);

        // Create the Freecom_dataacquiringcopsc, which fails.

        restFreecom_dataacquiringcopscMockMvc.perform(post("/api/freecom-dataacquiringcopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataacquiringcopsc)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataacquiringcopsc> freecom_dataacquiringcopscs = freecom_dataacquiringcopscRepository.findAll();
        assertThat(freecom_dataacquiringcopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataacquiringcopscRepository.findAll().size();
        // set the field null
        freecom_dataacquiringcopsc.setPercentage(null);

        // Create the Freecom_dataacquiringcopsc, which fails.

        restFreecom_dataacquiringcopscMockMvc.perform(post("/api/freecom-dataacquiringcopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataacquiringcopsc)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataacquiringcopsc> freecom_dataacquiringcopscs = freecom_dataacquiringcopscRepository.findAll();
        assertThat(freecom_dataacquiringcopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_dataacquiringcopscs() throws Exception {
        // Initialize the database
        freecom_dataacquiringcopscRepository.saveAndFlush(freecom_dataacquiringcopsc);

        // Get all the freecom_dataacquiringcopscs
        restFreecom_dataacquiringcopscMockMvc.perform(get("/api/freecom-dataacquiringcopscs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_dataacquiringcopsc.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].mother_last_name").value(hasItem(DEFAULT_MOTHER_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
                .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_dataacquiringcopsc() throws Exception {
        // Initialize the database
        freecom_dataacquiringcopscRepository.saveAndFlush(freecom_dataacquiringcopsc);

        // Get the freecom_dataacquiringcopsc
        restFreecom_dataacquiringcopscMockMvc.perform(get("/api/freecom-dataacquiringcopscs/{id}", freecom_dataacquiringcopsc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_dataacquiringcopsc.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.mother_last_name").value(DEFAULT_MOTHER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_dataacquiringcopsc() throws Exception {
        // Get the freecom_dataacquiringcopsc
        restFreecom_dataacquiringcopscMockMvc.perform(get("/api/freecom-dataacquiringcopscs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_dataacquiringcopsc() throws Exception {
        // Initialize the database
        freecom_dataacquiringcopscService.save(freecom_dataacquiringcopsc);

        int databaseSizeBeforeUpdate = freecom_dataacquiringcopscRepository.findAll().size();

        // Update the freecom_dataacquiringcopsc
        Freecom_dataacquiringcopsc updatedFreecom_dataacquiringcopsc = new Freecom_dataacquiringcopsc();
        updatedFreecom_dataacquiringcopsc.setId(freecom_dataacquiringcopsc.getId());
        updatedFreecom_dataacquiringcopsc.setName(UPDATED_NAME);
        updatedFreecom_dataacquiringcopsc.setLast_name(UPDATED_LAST_NAME);
        updatedFreecom_dataacquiringcopsc.setMother_last_name(UPDATED_MOTHER_LAST_NAME);
        updatedFreecom_dataacquiringcopsc.setRfc(UPDATED_RFC);
        updatedFreecom_dataacquiringcopsc.setCurp(UPDATED_CURP);
        updatedFreecom_dataacquiringcopsc.setPercentage(UPDATED_PERCENTAGE);

        restFreecom_dataacquiringcopscMockMvc.perform(put("/api/freecom-dataacquiringcopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_dataacquiringcopsc)))
                .andExpect(status().isOk());

        // Validate the Freecom_dataacquiringcopsc in the database
        List<Freecom_dataacquiringcopsc> freecom_dataacquiringcopscs = freecom_dataacquiringcopscRepository.findAll();
        assertThat(freecom_dataacquiringcopscs).hasSize(databaseSizeBeforeUpdate);
        Freecom_dataacquiringcopsc testFreecom_dataacquiringcopsc = freecom_dataacquiringcopscs.get(freecom_dataacquiringcopscs.size() - 1);
        assertThat(testFreecom_dataacquiringcopsc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFreecom_dataacquiringcopsc.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testFreecom_dataacquiringcopsc.getMother_last_name()).isEqualTo(UPDATED_MOTHER_LAST_NAME);
        assertThat(testFreecom_dataacquiringcopsc.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFreecom_dataacquiringcopsc.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testFreecom_dataacquiringcopsc.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void deleteFreecom_dataacquiringcopsc() throws Exception {
        // Initialize the database
        freecom_dataacquiringcopscService.save(freecom_dataacquiringcopsc);

        int databaseSizeBeforeDelete = freecom_dataacquiringcopscRepository.findAll().size();

        // Get the freecom_dataacquiringcopsc
        restFreecom_dataacquiringcopscMockMvc.perform(delete("/api/freecom-dataacquiringcopscs/{id}", freecom_dataacquiringcopsc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_dataacquiringcopsc> freecom_dataacquiringcopscs = freecom_dataacquiringcopscRepository.findAll();
        assertThat(freecom_dataacquiringcopscs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
