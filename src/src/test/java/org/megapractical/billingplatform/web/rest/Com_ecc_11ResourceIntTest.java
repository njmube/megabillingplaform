package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_ecc_11;
import org.megapractical.billingplatform.repository.Com_ecc_11Repository;
import org.megapractical.billingplatform.service.Com_ecc_11Service;

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
import java.math.BigDecimal;;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the Com_ecc_11Resource REST controller.
 *
 * @see Com_ecc_11Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_ecc_11ResourceIntTest {

    private static final String DEFAULT_VERSION = "AAAAA";
    private static final String UPDATED_VERSION = "BBBBB";
    private static final String DEFAULT_TYPE_OPERATION = "AAAAA";
    private static final String UPDATED_TYPE_OPERATION = "BBBBB";
    private static final String DEFAULT_NUMBER_ACCOUNT = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_NUMBER_ACCOUNT = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_SUBTOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUBTOTAL = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);

    @Inject
    private Com_ecc_11Repository com_ecc_11Repository;

    @Inject
    private Com_ecc_11Service com_ecc_11Service;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_ecc_11MockMvc;

    private Com_ecc_11 com_ecc_11;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_ecc_11Resource com_ecc_11Resource = new Com_ecc_11Resource();
        ReflectionTestUtils.setField(com_ecc_11Resource, "com_ecc_11Service", com_ecc_11Service);
        this.restCom_ecc_11MockMvc = MockMvcBuilders.standaloneSetup(com_ecc_11Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_ecc_11 = new Com_ecc_11();
        com_ecc_11.setVersion(DEFAULT_VERSION);
        com_ecc_11.setType_operation(DEFAULT_TYPE_OPERATION);
        com_ecc_11.setNumber_account(DEFAULT_NUMBER_ACCOUNT);
        com_ecc_11.setSubtotal(DEFAULT_SUBTOTAL);
        com_ecc_11.setTotal(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createCom_ecc_11() throws Exception {
        int databaseSizeBeforeCreate = com_ecc_11Repository.findAll().size();

        // Create the Com_ecc_11

        restCom_ecc_11MockMvc.perform(post("/api/com-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11)))
                .andExpect(status().isCreated());

        // Validate the Com_ecc_11 in the database
        List<Com_ecc_11> com_ecc_11S = com_ecc_11Repository.findAll();
        assertThat(com_ecc_11S).hasSize(databaseSizeBeforeCreate + 1);
        Com_ecc_11 testCom_ecc_11 = com_ecc_11S.get(com_ecc_11S.size() - 1);
        assertThat(testCom_ecc_11.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testCom_ecc_11.getType_operation()).isEqualTo(DEFAULT_TYPE_OPERATION);
        assertThat(testCom_ecc_11.getNumber_account()).isEqualTo(DEFAULT_NUMBER_ACCOUNT);
        assertThat(testCom_ecc_11.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testCom_ecc_11.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11Repository.findAll().size();
        // set the field null
        com_ecc_11.setVersion(null);

        // Create the Com_ecc_11, which fails.

        restCom_ecc_11MockMvc.perform(post("/api/com-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11> com_ecc_11S = com_ecc_11Repository.findAll();
        assertThat(com_ecc_11S).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkType_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11Repository.findAll().size();
        // set the field null
        com_ecc_11.setType_operation(null);

        // Create the Com_ecc_11, which fails.

        restCom_ecc_11MockMvc.perform(post("/api/com-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11> com_ecc_11S = com_ecc_11Repository.findAll();
        assertThat(com_ecc_11S).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumber_accountIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11Repository.findAll().size();
        // set the field null
        com_ecc_11.setNumber_account(null);

        // Create the Com_ecc_11, which fails.

        restCom_ecc_11MockMvc.perform(post("/api/com-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11> com_ecc_11S = com_ecc_11Repository.findAll();
        assertThat(com_ecc_11S).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubtotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11Repository.findAll().size();
        // set the field null
        com_ecc_11.setSubtotal(null);

        // Create the Com_ecc_11, which fails.

        restCom_ecc_11MockMvc.perform(post("/api/com-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11> com_ecc_11S = com_ecc_11Repository.findAll();
        assertThat(com_ecc_11S).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_ecc_11Repository.findAll().size();
        // set the field null
        com_ecc_11.setTotal(null);

        // Create the Com_ecc_11, which fails.

        restCom_ecc_11MockMvc.perform(post("/api/com-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_ecc_11)))
                .andExpect(status().isBadRequest());

        List<Com_ecc_11> com_ecc_11S = com_ecc_11Repository.findAll();
        assertThat(com_ecc_11S).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_ecc_11S() throws Exception {
        // Initialize the database
        com_ecc_11Repository.saveAndFlush(com_ecc_11);

        // Get all the com_ecc_11S
        restCom_ecc_11MockMvc.perform(get("/api/com-ecc-11-s?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_ecc_11.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].type_operation").value(hasItem(DEFAULT_TYPE_OPERATION.toString())))
                .andExpect(jsonPath("$.[*].number_account").value(hasItem(DEFAULT_NUMBER_ACCOUNT.toString())))
                .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getCom_ecc_11() throws Exception {
        // Initialize the database
        com_ecc_11Repository.saveAndFlush(com_ecc_11);

        // Get the com_ecc_11
        restCom_ecc_11MockMvc.perform(get("/api/com-ecc-11-s/{id}", com_ecc_11.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_ecc_11.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.type_operation").value(DEFAULT_TYPE_OPERATION.toString()))
            .andExpect(jsonPath("$.number_account").value(DEFAULT_NUMBER_ACCOUNT.toString()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_ecc_11() throws Exception {
        // Get the com_ecc_11
        restCom_ecc_11MockMvc.perform(get("/api/com-ecc-11-s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_ecc_11() throws Exception {
        // Initialize the database
        com_ecc_11Service.save(com_ecc_11);

        int databaseSizeBeforeUpdate = com_ecc_11Repository.findAll().size();

        // Update the com_ecc_11
        Com_ecc_11 updatedCom_ecc_11 = new Com_ecc_11();
        updatedCom_ecc_11.setId(com_ecc_11.getId());
        updatedCom_ecc_11.setVersion(UPDATED_VERSION);
        updatedCom_ecc_11.setType_operation(UPDATED_TYPE_OPERATION);
        updatedCom_ecc_11.setNumber_account(UPDATED_NUMBER_ACCOUNT);
        updatedCom_ecc_11.setSubtotal(UPDATED_SUBTOTAL);
        updatedCom_ecc_11.setTotal(UPDATED_TOTAL);

        restCom_ecc_11MockMvc.perform(put("/api/com-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_ecc_11)))
                .andExpect(status().isOk());

        // Validate the Com_ecc_11 in the database
        List<Com_ecc_11> com_ecc_11S = com_ecc_11Repository.findAll();
        assertThat(com_ecc_11S).hasSize(databaseSizeBeforeUpdate);
        Com_ecc_11 testCom_ecc_11 = com_ecc_11S.get(com_ecc_11S.size() - 1);
        assertThat(testCom_ecc_11.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testCom_ecc_11.getType_operation()).isEqualTo(UPDATED_TYPE_OPERATION);
        assertThat(testCom_ecc_11.getNumber_account()).isEqualTo(UPDATED_NUMBER_ACCOUNT);
        assertThat(testCom_ecc_11.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testCom_ecc_11.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void deleteCom_ecc_11() throws Exception {
        // Initialize the database
        com_ecc_11Service.save(com_ecc_11);

        int databaseSizeBeforeDelete = com_ecc_11Repository.findAll().size();

        // Get the com_ecc_11
        restCom_ecc_11MockMvc.perform(delete("/api/com-ecc-11-s/{id}", com_ecc_11.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_ecc_11> com_ecc_11S = com_ecc_11Repository.findAll();
        assertThat(com_ecc_11S).hasSize(databaseSizeBeforeDelete - 1);
    }
}
