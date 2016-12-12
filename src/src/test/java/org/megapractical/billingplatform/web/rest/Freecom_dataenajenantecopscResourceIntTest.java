package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_dataenajenantecopsc;
import org.megapractical.billingplatform.repository.Freecom_dataenajenantecopscRepository;
import org.megapractical.billingplatform.service.Freecom_dataenajenantecopscService;

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
 * Test class for the Freecom_dataenajenantecopscResource REST controller.
 *
 * @see Freecom_dataenajenantecopscResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_dataenajenantecopscResourceIntTest {

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
    private Freecom_dataenajenantecopscRepository freecom_dataenajenantecopscRepository;

    @Inject
    private Freecom_dataenajenantecopscService freecom_dataenajenantecopscService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_dataenajenantecopscMockMvc;

    private Freecom_dataenajenantecopsc freecom_dataenajenantecopsc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_dataenajenantecopscResource freecom_dataenajenantecopscResource = new Freecom_dataenajenantecopscResource();
        ReflectionTestUtils.setField(freecom_dataenajenantecopscResource, "freecom_dataenajenantecopscService", freecom_dataenajenantecopscService);
        this.restFreecom_dataenajenantecopscMockMvc = MockMvcBuilders.standaloneSetup(freecom_dataenajenantecopscResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_dataenajenantecopsc = new Freecom_dataenajenantecopsc();
        freecom_dataenajenantecopsc.setName(DEFAULT_NAME);
        freecom_dataenajenantecopsc.setLast_name(DEFAULT_LAST_NAME);
        freecom_dataenajenantecopsc.setMother_last_name(DEFAULT_MOTHER_LAST_NAME);
        freecom_dataenajenantecopsc.setRfc(DEFAULT_RFC);
        freecom_dataenajenantecopsc.setCurp(DEFAULT_CURP);
        freecom_dataenajenantecopsc.setPercentage(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createFreecom_dataenajenantecopsc() throws Exception {
        int databaseSizeBeforeCreate = freecom_dataenajenantecopscRepository.findAll().size();

        // Create the Freecom_dataenajenantecopsc

        restFreecom_dataenajenantecopscMockMvc.perform(post("/api/freecom-dataenajenantecopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataenajenantecopsc)))
                .andExpect(status().isCreated());

        // Validate the Freecom_dataenajenantecopsc in the database
        List<Freecom_dataenajenantecopsc> freecom_dataenajenantecopscs = freecom_dataenajenantecopscRepository.findAll();
        assertThat(freecom_dataenajenantecopscs).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_dataenajenantecopsc testFreecom_dataenajenantecopsc = freecom_dataenajenantecopscs.get(freecom_dataenajenantecopscs.size() - 1);
        assertThat(testFreecom_dataenajenantecopsc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFreecom_dataenajenantecopsc.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testFreecom_dataenajenantecopsc.getMother_last_name()).isEqualTo(DEFAULT_MOTHER_LAST_NAME);
        assertThat(testFreecom_dataenajenantecopsc.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testFreecom_dataenajenantecopsc.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testFreecom_dataenajenantecopsc.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataenajenantecopscRepository.findAll().size();
        // set the field null
        freecom_dataenajenantecopsc.setName(null);

        // Create the Freecom_dataenajenantecopsc, which fails.

        restFreecom_dataenajenantecopscMockMvc.perform(post("/api/freecom-dataenajenantecopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataenajenantecopsc)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataenajenantecopsc> freecom_dataenajenantecopscs = freecom_dataenajenantecopscRepository.findAll();
        assertThat(freecom_dataenajenantecopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataenajenantecopscRepository.findAll().size();
        // set the field null
        freecom_dataenajenantecopsc.setRfc(null);

        // Create the Freecom_dataenajenantecopsc, which fails.

        restFreecom_dataenajenantecopscMockMvc.perform(post("/api/freecom-dataenajenantecopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataenajenantecopsc)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataenajenantecopsc> freecom_dataenajenantecopscs = freecom_dataenajenantecopscRepository.findAll();
        assertThat(freecom_dataenajenantecopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_dataenajenantecopscRepository.findAll().size();
        // set the field null
        freecom_dataenajenantecopsc.setPercentage(null);

        // Create the Freecom_dataenajenantecopsc, which fails.

        restFreecom_dataenajenantecopscMockMvc.perform(post("/api/freecom-dataenajenantecopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_dataenajenantecopsc)))
                .andExpect(status().isBadRequest());

        List<Freecom_dataenajenantecopsc> freecom_dataenajenantecopscs = freecom_dataenajenantecopscRepository.findAll();
        assertThat(freecom_dataenajenantecopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_dataenajenantecopscs() throws Exception {
        // Initialize the database
        freecom_dataenajenantecopscRepository.saveAndFlush(freecom_dataenajenantecopsc);

        // Get all the freecom_dataenajenantecopscs
        restFreecom_dataenajenantecopscMockMvc.perform(get("/api/freecom-dataenajenantecopscs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_dataenajenantecopsc.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].mother_last_name").value(hasItem(DEFAULT_MOTHER_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
                .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_dataenajenantecopsc() throws Exception {
        // Initialize the database
        freecom_dataenajenantecopscRepository.saveAndFlush(freecom_dataenajenantecopsc);

        // Get the freecom_dataenajenantecopsc
        restFreecom_dataenajenantecopscMockMvc.perform(get("/api/freecom-dataenajenantecopscs/{id}", freecom_dataenajenantecopsc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_dataenajenantecopsc.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.mother_last_name").value(DEFAULT_MOTHER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_dataenajenantecopsc() throws Exception {
        // Get the freecom_dataenajenantecopsc
        restFreecom_dataenajenantecopscMockMvc.perform(get("/api/freecom-dataenajenantecopscs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_dataenajenantecopsc() throws Exception {
        // Initialize the database
        freecom_dataenajenantecopscService.save(freecom_dataenajenantecopsc);

        int databaseSizeBeforeUpdate = freecom_dataenajenantecopscRepository.findAll().size();

        // Update the freecom_dataenajenantecopsc
        Freecom_dataenajenantecopsc updatedFreecom_dataenajenantecopsc = new Freecom_dataenajenantecopsc();
        updatedFreecom_dataenajenantecopsc.setId(freecom_dataenajenantecopsc.getId());
        updatedFreecom_dataenajenantecopsc.setName(UPDATED_NAME);
        updatedFreecom_dataenajenantecopsc.setLast_name(UPDATED_LAST_NAME);
        updatedFreecom_dataenajenantecopsc.setMother_last_name(UPDATED_MOTHER_LAST_NAME);
        updatedFreecom_dataenajenantecopsc.setRfc(UPDATED_RFC);
        updatedFreecom_dataenajenantecopsc.setCurp(UPDATED_CURP);
        updatedFreecom_dataenajenantecopsc.setPercentage(UPDATED_PERCENTAGE);

        restFreecom_dataenajenantecopscMockMvc.perform(put("/api/freecom-dataenajenantecopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_dataenajenantecopsc)))
                .andExpect(status().isOk());

        // Validate the Freecom_dataenajenantecopsc in the database
        List<Freecom_dataenajenantecopsc> freecom_dataenajenantecopscs = freecom_dataenajenantecopscRepository.findAll();
        assertThat(freecom_dataenajenantecopscs).hasSize(databaseSizeBeforeUpdate);
        Freecom_dataenajenantecopsc testFreecom_dataenajenantecopsc = freecom_dataenajenantecopscs.get(freecom_dataenajenantecopscs.size() - 1);
        assertThat(testFreecom_dataenajenantecopsc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFreecom_dataenajenantecopsc.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testFreecom_dataenajenantecopsc.getMother_last_name()).isEqualTo(UPDATED_MOTHER_LAST_NAME);
        assertThat(testFreecom_dataenajenantecopsc.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testFreecom_dataenajenantecopsc.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testFreecom_dataenajenantecopsc.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void deleteFreecom_dataenajenantecopsc() throws Exception {
        // Initialize the database
        freecom_dataenajenantecopscService.save(freecom_dataenajenantecopsc);

        int databaseSizeBeforeDelete = freecom_dataenajenantecopscRepository.findAll().size();

        // Get the freecom_dataenajenantecopsc
        restFreecom_dataenajenantecopscMockMvc.perform(delete("/api/freecom-dataenajenantecopscs/{id}", freecom_dataenajenantecopsc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_dataenajenantecopsc> freecom_dataenajenantecopscs = freecom_dataenajenantecopscRepository.findAll();
        assertThat(freecom_dataenajenantecopscs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
