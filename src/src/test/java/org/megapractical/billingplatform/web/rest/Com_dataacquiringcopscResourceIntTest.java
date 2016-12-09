package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_dataacquiringcopsc;
import org.megapractical.billingplatform.repository.Com_dataacquiringcopscRepository;
import org.megapractical.billingplatform.service.Com_dataacquiringcopscService;

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
 * Test class for the Com_dataacquiringcopscResource REST controller.
 *
 * @see Com_dataacquiringcopscResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_dataacquiringcopscResourceIntTest {

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
    private Com_dataacquiringcopscRepository com_dataacquiringcopscRepository;

    @Inject
    private Com_dataacquiringcopscService com_dataacquiringcopscService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_dataacquiringcopscMockMvc;

    private Com_dataacquiringcopsc com_dataacquiringcopsc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_dataacquiringcopscResource com_dataacquiringcopscResource = new Com_dataacquiringcopscResource();
        ReflectionTestUtils.setField(com_dataacquiringcopscResource, "com_dataacquiringcopscService", com_dataacquiringcopscService);
        this.restCom_dataacquiringcopscMockMvc = MockMvcBuilders.standaloneSetup(com_dataacquiringcopscResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_dataacquiringcopsc = new Com_dataacquiringcopsc();
        com_dataacquiringcopsc.setName(DEFAULT_NAME);
        com_dataacquiringcopsc.setLast_name(DEFAULT_LAST_NAME);
        com_dataacquiringcopsc.setMother_last_name(DEFAULT_MOTHER_LAST_NAME);
        com_dataacquiringcopsc.setRfc(DEFAULT_RFC);
        com_dataacquiringcopsc.setCurp(DEFAULT_CURP);
        com_dataacquiringcopsc.setPercentage(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createCom_dataacquiringcopsc() throws Exception {
        int databaseSizeBeforeCreate = com_dataacquiringcopscRepository.findAll().size();

        // Create the Com_dataacquiringcopsc

        restCom_dataacquiringcopscMockMvc.perform(post("/api/com-dataacquiringcopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataacquiringcopsc)))
                .andExpect(status().isCreated());

        // Validate the Com_dataacquiringcopsc in the database
        List<Com_dataacquiringcopsc> com_dataacquiringcopscs = com_dataacquiringcopscRepository.findAll();
        assertThat(com_dataacquiringcopscs).hasSize(databaseSizeBeforeCreate + 1);
        Com_dataacquiringcopsc testCom_dataacquiringcopsc = com_dataacquiringcopscs.get(com_dataacquiringcopscs.size() - 1);
        assertThat(testCom_dataacquiringcopsc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCom_dataacquiringcopsc.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCom_dataacquiringcopsc.getMother_last_name()).isEqualTo(DEFAULT_MOTHER_LAST_NAME);
        assertThat(testCom_dataacquiringcopsc.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testCom_dataacquiringcopsc.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testCom_dataacquiringcopsc.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataacquiringcopscRepository.findAll().size();
        // set the field null
        com_dataacquiringcopsc.setName(null);

        // Create the Com_dataacquiringcopsc, which fails.

        restCom_dataacquiringcopscMockMvc.perform(post("/api/com-dataacquiringcopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataacquiringcopsc)))
                .andExpect(status().isBadRequest());

        List<Com_dataacquiringcopsc> com_dataacquiringcopscs = com_dataacquiringcopscRepository.findAll();
        assertThat(com_dataacquiringcopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataacquiringcopscRepository.findAll().size();
        // set the field null
        com_dataacquiringcopsc.setRfc(null);

        // Create the Com_dataacquiringcopsc, which fails.

        restCom_dataacquiringcopscMockMvc.perform(post("/api/com-dataacquiringcopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataacquiringcopsc)))
                .andExpect(status().isBadRequest());

        List<Com_dataacquiringcopsc> com_dataacquiringcopscs = com_dataacquiringcopscRepository.findAll();
        assertThat(com_dataacquiringcopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataacquiringcopscRepository.findAll().size();
        // set the field null
        com_dataacquiringcopsc.setPercentage(null);

        // Create the Com_dataacquiringcopsc, which fails.

        restCom_dataacquiringcopscMockMvc.perform(post("/api/com-dataacquiringcopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataacquiringcopsc)))
                .andExpect(status().isBadRequest());

        List<Com_dataacquiringcopsc> com_dataacquiringcopscs = com_dataacquiringcopscRepository.findAll();
        assertThat(com_dataacquiringcopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_dataacquiringcopscs() throws Exception {
        // Initialize the database
        com_dataacquiringcopscRepository.saveAndFlush(com_dataacquiringcopsc);

        // Get all the com_dataacquiringcopscs
        restCom_dataacquiringcopscMockMvc.perform(get("/api/com-dataacquiringcopscs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_dataacquiringcopsc.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].mother_last_name").value(hasItem(DEFAULT_MOTHER_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
                .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())));
    }

    @Test
    @Transactional
    public void getCom_dataacquiringcopsc() throws Exception {
        // Initialize the database
        com_dataacquiringcopscRepository.saveAndFlush(com_dataacquiringcopsc);

        // Get the com_dataacquiringcopsc
        restCom_dataacquiringcopscMockMvc.perform(get("/api/com-dataacquiringcopscs/{id}", com_dataacquiringcopsc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_dataacquiringcopsc.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.mother_last_name").value(DEFAULT_MOTHER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_dataacquiringcopsc() throws Exception {
        // Get the com_dataacquiringcopsc
        restCom_dataacquiringcopscMockMvc.perform(get("/api/com-dataacquiringcopscs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_dataacquiringcopsc() throws Exception {
        // Initialize the database
        com_dataacquiringcopscService.save(com_dataacquiringcopsc);

        int databaseSizeBeforeUpdate = com_dataacquiringcopscRepository.findAll().size();

        // Update the com_dataacquiringcopsc
        Com_dataacquiringcopsc updatedCom_dataacquiringcopsc = new Com_dataacquiringcopsc();
        updatedCom_dataacquiringcopsc.setId(com_dataacquiringcopsc.getId());
        updatedCom_dataacquiringcopsc.setName(UPDATED_NAME);
        updatedCom_dataacquiringcopsc.setLast_name(UPDATED_LAST_NAME);
        updatedCom_dataacquiringcopsc.setMother_last_name(UPDATED_MOTHER_LAST_NAME);
        updatedCom_dataacquiringcopsc.setRfc(UPDATED_RFC);
        updatedCom_dataacquiringcopsc.setCurp(UPDATED_CURP);
        updatedCom_dataacquiringcopsc.setPercentage(UPDATED_PERCENTAGE);

        restCom_dataacquiringcopscMockMvc.perform(put("/api/com-dataacquiringcopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_dataacquiringcopsc)))
                .andExpect(status().isOk());

        // Validate the Com_dataacquiringcopsc in the database
        List<Com_dataacquiringcopsc> com_dataacquiringcopscs = com_dataacquiringcopscRepository.findAll();
        assertThat(com_dataacquiringcopscs).hasSize(databaseSizeBeforeUpdate);
        Com_dataacquiringcopsc testCom_dataacquiringcopsc = com_dataacquiringcopscs.get(com_dataacquiringcopscs.size() - 1);
        assertThat(testCom_dataacquiringcopsc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCom_dataacquiringcopsc.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCom_dataacquiringcopsc.getMother_last_name()).isEqualTo(UPDATED_MOTHER_LAST_NAME);
        assertThat(testCom_dataacquiringcopsc.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testCom_dataacquiringcopsc.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testCom_dataacquiringcopsc.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void deleteCom_dataacquiringcopsc() throws Exception {
        // Initialize the database
        com_dataacquiringcopscService.save(com_dataacquiringcopsc);

        int databaseSizeBeforeDelete = com_dataacquiringcopscRepository.findAll().size();

        // Get the com_dataacquiringcopsc
        restCom_dataacquiringcopscMockMvc.perform(delete("/api/com-dataacquiringcopscs/{id}", com_dataacquiringcopsc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_dataacquiringcopsc> com_dataacquiringcopscs = com_dataacquiringcopscRepository.findAll();
        assertThat(com_dataacquiringcopscs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
