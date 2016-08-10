package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Freecom_ecc11;
import org.megapractical.billingplatform.repository.Freecom_ecc11Repository;
import org.megapractical.billingplatform.service.Freecom_ecc11Service;

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
 * Test class for the Freecom_ecc11Resource REST controller.
 *
 * @see Freecom_ecc11Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Freecom_ecc11ResourceIntTest {

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
    private Freecom_ecc11Repository freecom_ecc11Repository;

    @Inject
    private Freecom_ecc11Service freecom_ecc11Service;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restFreecom_ecc11MockMvc;

    private Freecom_ecc11 freecom_ecc11;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Freecom_ecc11Resource freecom_ecc11Resource = new Freecom_ecc11Resource();
        ReflectionTestUtils.setField(freecom_ecc11Resource, "freecom_ecc11Service", freecom_ecc11Service);
        this.restFreecom_ecc11MockMvc = MockMvcBuilders.standaloneSetup(freecom_ecc11Resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        freecom_ecc11 = new Freecom_ecc11();
        freecom_ecc11.setVersion(DEFAULT_VERSION);
        freecom_ecc11.setType_operation(DEFAULT_TYPE_OPERATION);
        freecom_ecc11.setNumber_account(DEFAULT_NUMBER_ACCOUNT);
        freecom_ecc11.setSubtotal(DEFAULT_SUBTOTAL);
        freecom_ecc11.setTotal(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void createFreecom_ecc11() throws Exception {
        int databaseSizeBeforeCreate = freecom_ecc11Repository.findAll().size();

        // Create the Freecom_ecc11

        restFreecom_ecc11MockMvc.perform(post("/api/freecom-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11)))
                .andExpect(status().isCreated());

        // Validate the Freecom_ecc11 in the database
        List<Freecom_ecc11> freecom_ecc11S = freecom_ecc11Repository.findAll();
        assertThat(freecom_ecc11S).hasSize(databaseSizeBeforeCreate + 1);
        Freecom_ecc11 testFreecom_ecc11 = freecom_ecc11S.get(freecom_ecc11S.size() - 1);
        assertThat(testFreecom_ecc11.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testFreecom_ecc11.getType_operation()).isEqualTo(DEFAULT_TYPE_OPERATION);
        assertThat(testFreecom_ecc11.getNumber_account()).isEqualTo(DEFAULT_NUMBER_ACCOUNT);
        assertThat(testFreecom_ecc11.getSubtotal()).isEqualTo(DEFAULT_SUBTOTAL);
        assertThat(testFreecom_ecc11.getTotal()).isEqualTo(DEFAULT_TOTAL);
    }

    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11Repository.findAll().size();
        // set the field null
        freecom_ecc11.setVersion(null);

        // Create the Freecom_ecc11, which fails.

        restFreecom_ecc11MockMvc.perform(post("/api/freecom-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11> freecom_ecc11S = freecom_ecc11Repository.findAll();
        assertThat(freecom_ecc11S).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkType_operationIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11Repository.findAll().size();
        // set the field null
        freecom_ecc11.setType_operation(null);

        // Create the Freecom_ecc11, which fails.

        restFreecom_ecc11MockMvc.perform(post("/api/freecom-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11> freecom_ecc11S = freecom_ecc11Repository.findAll();
        assertThat(freecom_ecc11S).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumber_accountIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11Repository.findAll().size();
        // set the field null
        freecom_ecc11.setNumber_account(null);

        // Create the Freecom_ecc11, which fails.

        restFreecom_ecc11MockMvc.perform(post("/api/freecom-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11> freecom_ecc11S = freecom_ecc11Repository.findAll();
        assertThat(freecom_ecc11S).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubtotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11Repository.findAll().size();
        // set the field null
        freecom_ecc11.setSubtotal(null);

        // Create the Freecom_ecc11, which fails.

        restFreecom_ecc11MockMvc.perform(post("/api/freecom-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11> freecom_ecc11S = freecom_ecc11Repository.findAll();
        assertThat(freecom_ecc11S).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = freecom_ecc11Repository.findAll().size();
        // set the field null
        freecom_ecc11.setTotal(null);

        // Create the Freecom_ecc11, which fails.

        restFreecom_ecc11MockMvc.perform(post("/api/freecom-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(freecom_ecc11)))
                .andExpect(status().isBadRequest());

        List<Freecom_ecc11> freecom_ecc11S = freecom_ecc11Repository.findAll();
        assertThat(freecom_ecc11S).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFreecom_ecc11S() throws Exception {
        // Initialize the database
        freecom_ecc11Repository.saveAndFlush(freecom_ecc11);

        // Get all the freecom_ecc11S
        restFreecom_ecc11MockMvc.perform(get("/api/freecom-ecc-11-s?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(freecom_ecc11.getId().intValue())))
                .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].type_operation").value(hasItem(DEFAULT_TYPE_OPERATION.toString())))
                .andExpect(jsonPath("$.[*].number_account").value(hasItem(DEFAULT_NUMBER_ACCOUNT.toString())))
                .andExpect(jsonPath("$.[*].subtotal").value(hasItem(DEFAULT_SUBTOTAL.intValue())))
                .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.intValue())));
    }

    @Test
    @Transactional
    public void getFreecom_ecc11() throws Exception {
        // Initialize the database
        freecom_ecc11Repository.saveAndFlush(freecom_ecc11);

        // Get the freecom_ecc11
        restFreecom_ecc11MockMvc.perform(get("/api/freecom-ecc-11-s/{id}", freecom_ecc11.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(freecom_ecc11.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
            .andExpect(jsonPath("$.type_operation").value(DEFAULT_TYPE_OPERATION.toString()))
            .andExpect(jsonPath("$.number_account").value(DEFAULT_NUMBER_ACCOUNT.toString()))
            .andExpect(jsonPath("$.subtotal").value(DEFAULT_SUBTOTAL.intValue()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFreecom_ecc11() throws Exception {
        // Get the freecom_ecc11
        restFreecom_ecc11MockMvc.perform(get("/api/freecom-ecc-11-s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreecom_ecc11() throws Exception {
        // Initialize the database
        freecom_ecc11Service.save(freecom_ecc11);

        int databaseSizeBeforeUpdate = freecom_ecc11Repository.findAll().size();

        // Update the freecom_ecc11
        Freecom_ecc11 updatedFreecom_ecc11 = new Freecom_ecc11();
        updatedFreecom_ecc11.setId(freecom_ecc11.getId());
        updatedFreecom_ecc11.setVersion(UPDATED_VERSION);
        updatedFreecom_ecc11.setType_operation(UPDATED_TYPE_OPERATION);
        updatedFreecom_ecc11.setNumber_account(UPDATED_NUMBER_ACCOUNT);
        updatedFreecom_ecc11.setSubtotal(UPDATED_SUBTOTAL);
        updatedFreecom_ecc11.setTotal(UPDATED_TOTAL);

        restFreecom_ecc11MockMvc.perform(put("/api/freecom-ecc-11-s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFreecom_ecc11)))
                .andExpect(status().isOk());

        // Validate the Freecom_ecc11 in the database
        List<Freecom_ecc11> freecom_ecc11S = freecom_ecc11Repository.findAll();
        assertThat(freecom_ecc11S).hasSize(databaseSizeBeforeUpdate);
        Freecom_ecc11 testFreecom_ecc11 = freecom_ecc11S.get(freecom_ecc11S.size() - 1);
        assertThat(testFreecom_ecc11.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testFreecom_ecc11.getType_operation()).isEqualTo(UPDATED_TYPE_OPERATION);
        assertThat(testFreecom_ecc11.getNumber_account()).isEqualTo(UPDATED_NUMBER_ACCOUNT);
        assertThat(testFreecom_ecc11.getSubtotal()).isEqualTo(UPDATED_SUBTOTAL);
        assertThat(testFreecom_ecc11.getTotal()).isEqualTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void deleteFreecom_ecc11() throws Exception {
        // Initialize the database
        freecom_ecc11Service.save(freecom_ecc11);

        int databaseSizeBeforeDelete = freecom_ecc11Repository.findAll().size();

        // Get the freecom_ecc11
        restFreecom_ecc11MockMvc.perform(delete("/api/freecom-ecc-11-s/{id}", freecom_ecc11.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Freecom_ecc11> freecom_ecc11S = freecom_ecc11Repository.findAll();
        assertThat(freecom_ecc11S).hasSize(databaseSizeBeforeDelete - 1);
    }
}
