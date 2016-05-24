package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Voucher_type;
import org.megapractical.billingplatform.repository.Voucher_typeRepository;
import org.megapractical.billingplatform.service.Voucher_typeService;

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
 * Test class for the Voucher_typeResource REST controller.
 *
 * @see Voucher_typeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Voucher_typeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Voucher_typeRepository voucher_typeRepository;

    @Inject
    private Voucher_typeService voucher_typeService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVoucher_typeMockMvc;

    private Voucher_type voucher_type;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Voucher_typeResource voucher_typeResource = new Voucher_typeResource();
        ReflectionTestUtils.setField(voucher_typeResource, "voucher_typeService", voucher_typeService);
        this.restVoucher_typeMockMvc = MockMvcBuilders.standaloneSetup(voucher_typeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        voucher_type = new Voucher_type();
        voucher_type.setName(DEFAULT_NAME);
        voucher_type.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createVoucher_type() throws Exception {
        int databaseSizeBeforeCreate = voucher_typeRepository.findAll().size();

        // Create the Voucher_type

        restVoucher_typeMockMvc.perform(post("/api/voucher-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(voucher_type)))
                .andExpect(status().isCreated());

        // Validate the Voucher_type in the database
        List<Voucher_type> voucher_types = voucher_typeRepository.findAll();
        assertThat(voucher_types).hasSize(databaseSizeBeforeCreate + 1);
        Voucher_type testVoucher_type = voucher_types.get(voucher_types.size() - 1);
        assertThat(testVoucher_type.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVoucher_type.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllVoucher_types() throws Exception {
        // Initialize the database
        voucher_typeRepository.saveAndFlush(voucher_type);

        // Get all the voucher_types
        restVoucher_typeMockMvc.perform(get("/api/voucher-types?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(voucher_type.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getVoucher_type() throws Exception {
        // Initialize the database
        voucher_typeRepository.saveAndFlush(voucher_type);

        // Get the voucher_type
        restVoucher_typeMockMvc.perform(get("/api/voucher-types/{id}", voucher_type.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(voucher_type.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoucher_type() throws Exception {
        // Get the voucher_type
        restVoucher_typeMockMvc.perform(get("/api/voucher-types/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoucher_type() throws Exception {
        // Initialize the database
        voucher_typeService.save(voucher_type);

        int databaseSizeBeforeUpdate = voucher_typeRepository.findAll().size();

        // Update the voucher_type
        Voucher_type updatedVoucher_type = new Voucher_type();
        updatedVoucher_type.setId(voucher_type.getId());
        updatedVoucher_type.setName(UPDATED_NAME);
        updatedVoucher_type.setDescription(UPDATED_DESCRIPTION);

        restVoucher_typeMockMvc.perform(put("/api/voucher-types")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedVoucher_type)))
                .andExpect(status().isOk());

        // Validate the Voucher_type in the database
        List<Voucher_type> voucher_types = voucher_typeRepository.findAll();
        assertThat(voucher_types).hasSize(databaseSizeBeforeUpdate);
        Voucher_type testVoucher_type = voucher_types.get(voucher_types.size() - 1);
        assertThat(testVoucher_type.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVoucher_type.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteVoucher_type() throws Exception {
        // Initialize the database
        voucher_typeService.save(voucher_type);

        int databaseSizeBeforeDelete = voucher_typeRepository.findAll().size();

        // Get the voucher_type
        restVoucher_typeMockMvc.perform(delete("/api/voucher-types/{id}", voucher_type.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Voucher_type> voucher_types = voucher_typeRepository.findAll();
        assertThat(voucher_types).hasSize(databaseSizeBeforeDelete - 1);
    }
}
