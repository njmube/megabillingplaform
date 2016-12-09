package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_dataenajenantecopsc;
import org.megapractical.billingplatform.repository.Com_dataenajenantecopscRepository;
import org.megapractical.billingplatform.service.Com_dataenajenantecopscService;

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
 * Test class for the Com_dataenajenantecopscResource REST controller.
 *
 * @see Com_dataenajenantecopscResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_dataenajenantecopscResourceIntTest {

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
    private Com_dataenajenantecopscRepository com_dataenajenantecopscRepository;

    @Inject
    private Com_dataenajenantecopscService com_dataenajenantecopscService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_dataenajenantecopscMockMvc;

    private Com_dataenajenantecopsc com_dataenajenantecopsc;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_dataenajenantecopscResource com_dataenajenantecopscResource = new Com_dataenajenantecopscResource();
        ReflectionTestUtils.setField(com_dataenajenantecopscResource, "com_dataenajenantecopscService", com_dataenajenantecopscService);
        this.restCom_dataenajenantecopscMockMvc = MockMvcBuilders.standaloneSetup(com_dataenajenantecopscResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_dataenajenantecopsc = new Com_dataenajenantecopsc();
        com_dataenajenantecopsc.setName(DEFAULT_NAME);
        com_dataenajenantecopsc.setLast_name(DEFAULT_LAST_NAME);
        com_dataenajenantecopsc.setMother_last_name(DEFAULT_MOTHER_LAST_NAME);
        com_dataenajenantecopsc.setRfc(DEFAULT_RFC);
        com_dataenajenantecopsc.setCurp(DEFAULT_CURP);
        com_dataenajenantecopsc.setPercentage(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void createCom_dataenajenantecopsc() throws Exception {
        int databaseSizeBeforeCreate = com_dataenajenantecopscRepository.findAll().size();

        // Create the Com_dataenajenantecopsc

        restCom_dataenajenantecopscMockMvc.perform(post("/api/com-dataenajenantecopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataenajenantecopsc)))
                .andExpect(status().isCreated());

        // Validate the Com_dataenajenantecopsc in the database
        List<Com_dataenajenantecopsc> com_dataenajenantecopscs = com_dataenajenantecopscRepository.findAll();
        assertThat(com_dataenajenantecopscs).hasSize(databaseSizeBeforeCreate + 1);
        Com_dataenajenantecopsc testCom_dataenajenantecopsc = com_dataenajenantecopscs.get(com_dataenajenantecopscs.size() - 1);
        assertThat(testCom_dataenajenantecopsc.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCom_dataenajenantecopsc.getLast_name()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testCom_dataenajenantecopsc.getMother_last_name()).isEqualTo(DEFAULT_MOTHER_LAST_NAME);
        assertThat(testCom_dataenajenantecopsc.getRfc()).isEqualTo(DEFAULT_RFC);
        assertThat(testCom_dataenajenantecopsc.getCurp()).isEqualTo(DEFAULT_CURP);
        assertThat(testCom_dataenajenantecopsc.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataenajenantecopscRepository.findAll().size();
        // set the field null
        com_dataenajenantecopsc.setName(null);

        // Create the Com_dataenajenantecopsc, which fails.

        restCom_dataenajenantecopscMockMvc.perform(post("/api/com-dataenajenantecopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataenajenantecopsc)))
                .andExpect(status().isBadRequest());

        List<Com_dataenajenantecopsc> com_dataenajenantecopscs = com_dataenajenantecopscRepository.findAll();
        assertThat(com_dataenajenantecopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRfcIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataenajenantecopscRepository.findAll().size();
        // set the field null
        com_dataenajenantecopsc.setRfc(null);

        // Create the Com_dataenajenantecopsc, which fails.

        restCom_dataenajenantecopscMockMvc.perform(post("/api/com-dataenajenantecopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataenajenantecopsc)))
                .andExpect(status().isBadRequest());

        List<Com_dataenajenantecopsc> com_dataenajenantecopscs = com_dataenajenantecopscRepository.findAll();
        assertThat(com_dataenajenantecopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_dataenajenantecopscRepository.findAll().size();
        // set the field null
        com_dataenajenantecopsc.setPercentage(null);

        // Create the Com_dataenajenantecopsc, which fails.

        restCom_dataenajenantecopscMockMvc.perform(post("/api/com-dataenajenantecopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_dataenajenantecopsc)))
                .andExpect(status().isBadRequest());

        List<Com_dataenajenantecopsc> com_dataenajenantecopscs = com_dataenajenantecopscRepository.findAll();
        assertThat(com_dataenajenantecopscs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_dataenajenantecopscs() throws Exception {
        // Initialize the database
        com_dataenajenantecopscRepository.saveAndFlush(com_dataenajenantecopsc);

        // Get all the com_dataenajenantecopscs
        restCom_dataenajenantecopscMockMvc.perform(get("/api/com-dataenajenantecopscs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_dataenajenantecopsc.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].last_name").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].mother_last_name").value(hasItem(DEFAULT_MOTHER_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].rfc").value(hasItem(DEFAULT_RFC.toString())))
                .andExpect(jsonPath("$.[*].curp").value(hasItem(DEFAULT_CURP.toString())))
                .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())));
    }

    @Test
    @Transactional
    public void getCom_dataenajenantecopsc() throws Exception {
        // Initialize the database
        com_dataenajenantecopscRepository.saveAndFlush(com_dataenajenantecopsc);

        // Get the com_dataenajenantecopsc
        restCom_dataenajenantecopscMockMvc.perform(get("/api/com-dataenajenantecopscs/{id}", com_dataenajenantecopsc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_dataenajenantecopsc.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.last_name").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.mother_last_name").value(DEFAULT_MOTHER_LAST_NAME.toString()))
            .andExpect(jsonPath("$.rfc").value(DEFAULT_RFC.toString()))
            .andExpect(jsonPath("$.curp").value(DEFAULT_CURP.toString()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_dataenajenantecopsc() throws Exception {
        // Get the com_dataenajenantecopsc
        restCom_dataenajenantecopscMockMvc.perform(get("/api/com-dataenajenantecopscs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_dataenajenantecopsc() throws Exception {
        // Initialize the database
        com_dataenajenantecopscService.save(com_dataenajenantecopsc);

        int databaseSizeBeforeUpdate = com_dataenajenantecopscRepository.findAll().size();

        // Update the com_dataenajenantecopsc
        Com_dataenajenantecopsc updatedCom_dataenajenantecopsc = new Com_dataenajenantecopsc();
        updatedCom_dataenajenantecopsc.setId(com_dataenajenantecopsc.getId());
        updatedCom_dataenajenantecopsc.setName(UPDATED_NAME);
        updatedCom_dataenajenantecopsc.setLast_name(UPDATED_LAST_NAME);
        updatedCom_dataenajenantecopsc.setMother_last_name(UPDATED_MOTHER_LAST_NAME);
        updatedCom_dataenajenantecopsc.setRfc(UPDATED_RFC);
        updatedCom_dataenajenantecopsc.setCurp(UPDATED_CURP);
        updatedCom_dataenajenantecopsc.setPercentage(UPDATED_PERCENTAGE);

        restCom_dataenajenantecopscMockMvc.perform(put("/api/com-dataenajenantecopscs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_dataenajenantecopsc)))
                .andExpect(status().isOk());

        // Validate the Com_dataenajenantecopsc in the database
        List<Com_dataenajenantecopsc> com_dataenajenantecopscs = com_dataenajenantecopscRepository.findAll();
        assertThat(com_dataenajenantecopscs).hasSize(databaseSizeBeforeUpdate);
        Com_dataenajenantecopsc testCom_dataenajenantecopsc = com_dataenajenantecopscs.get(com_dataenajenantecopscs.size() - 1);
        assertThat(testCom_dataenajenantecopsc.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCom_dataenajenantecopsc.getLast_name()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testCom_dataenajenantecopsc.getMother_last_name()).isEqualTo(UPDATED_MOTHER_LAST_NAME);
        assertThat(testCom_dataenajenantecopsc.getRfc()).isEqualTo(UPDATED_RFC);
        assertThat(testCom_dataenajenantecopsc.getCurp()).isEqualTo(UPDATED_CURP);
        assertThat(testCom_dataenajenantecopsc.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void deleteCom_dataenajenantecopsc() throws Exception {
        // Initialize the database
        com_dataenajenantecopscService.save(com_dataenajenantecopsc);

        int databaseSizeBeforeDelete = com_dataenajenantecopscRepository.findAll().size();

        // Get the com_dataenajenantecopsc
        restCom_dataenajenantecopscMockMvc.perform(delete("/api/com-dataenajenantecopscs/{id}", com_dataenajenantecopsc.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_dataenajenantecopsc> com_dataenajenantecopscs = com_dataenajenantecopscRepository.findAll();
        assertThat(com_dataenajenantecopscs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
