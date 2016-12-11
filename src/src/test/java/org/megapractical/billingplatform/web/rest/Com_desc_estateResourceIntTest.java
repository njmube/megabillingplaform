package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Com_desc_estate;
import org.megapractical.billingplatform.repository.Com_desc_estateRepository;
import org.megapractical.billingplatform.service.Com_desc_estateService;

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
 * Test class for the Com_desc_estateResource REST controller.
 *
 * @see Com_desc_estateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Com_desc_estateResourceIntTest {

    private static final String DEFAULT_STREET = "AAAAA";
    private static final String UPDATED_STREET = "BBBBB";
    private static final String DEFAULT_NOEXT = "AAAAA";
    private static final String UPDATED_NOEXT = "BBBBB";
    private static final String DEFAULT_NOINT = "AAAAA";
    private static final String UPDATED_NOINT = "BBBBB";
    private static final String DEFAULT_LOCALE = "AAAAA";
    private static final String UPDATED_LOCALE = "BBBBB";
    private static final String DEFAULT_REFERENCE = "AAAAA";
    private static final String UPDATED_REFERENCE = "BBBBB";

    @Inject
    private Com_desc_estateRepository com_desc_estateRepository;

    @Inject
    private Com_desc_estateService com_desc_estateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCom_desc_estateMockMvc;

    private Com_desc_estate com_desc_estate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Com_desc_estateResource com_desc_estateResource = new Com_desc_estateResource();
        ReflectionTestUtils.setField(com_desc_estateResource, "com_desc_estateService", com_desc_estateService);
        this.restCom_desc_estateMockMvc = MockMvcBuilders.standaloneSetup(com_desc_estateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        com_desc_estate = new Com_desc_estate();
        com_desc_estate.setStreet(DEFAULT_STREET);
        com_desc_estate.setNoext(DEFAULT_NOEXT);
        com_desc_estate.setNoint(DEFAULT_NOINT);
        com_desc_estate.setLocale(DEFAULT_LOCALE);
        com_desc_estate.setReference(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createCom_desc_estate() throws Exception {
        int databaseSizeBeforeCreate = com_desc_estateRepository.findAll().size();

        // Create the Com_desc_estate

        restCom_desc_estateMockMvc.perform(post("/api/com-desc-estates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_desc_estate)))
                .andExpect(status().isCreated());

        // Validate the Com_desc_estate in the database
        List<Com_desc_estate> com_desc_estates = com_desc_estateRepository.findAll();
        assertThat(com_desc_estates).hasSize(databaseSizeBeforeCreate + 1);
        Com_desc_estate testCom_desc_estate = com_desc_estates.get(com_desc_estates.size() - 1);
        assertThat(testCom_desc_estate.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testCom_desc_estate.getNoext()).isEqualTo(DEFAULT_NOEXT);
        assertThat(testCom_desc_estate.getNoint()).isEqualTo(DEFAULT_NOINT);
        assertThat(testCom_desc_estate.getLocale()).isEqualTo(DEFAULT_LOCALE);
        assertThat(testCom_desc_estate.getReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = com_desc_estateRepository.findAll().size();
        // set the field null
        com_desc_estate.setStreet(null);

        // Create the Com_desc_estate, which fails.

        restCom_desc_estateMockMvc.perform(post("/api/com-desc-estates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(com_desc_estate)))
                .andExpect(status().isBadRequest());

        List<Com_desc_estate> com_desc_estates = com_desc_estateRepository.findAll();
        assertThat(com_desc_estates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCom_desc_estates() throws Exception {
        // Initialize the database
        com_desc_estateRepository.saveAndFlush(com_desc_estate);

        // Get all the com_desc_estates
        restCom_desc_estateMockMvc.perform(get("/api/com-desc-estates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(com_desc_estate.getId().intValue())))
                .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())))
                .andExpect(jsonPath("$.[*].noext").value(hasItem(DEFAULT_NOEXT.toString())))
                .andExpect(jsonPath("$.[*].noint").value(hasItem(DEFAULT_NOINT.toString())))
                .andExpect(jsonPath("$.[*].locale").value(hasItem(DEFAULT_LOCALE.toString())))
                .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())));
    }

    @Test
    @Transactional
    public void getCom_desc_estate() throws Exception {
        // Initialize the database
        com_desc_estateRepository.saveAndFlush(com_desc_estate);

        // Get the com_desc_estate
        restCom_desc_estateMockMvc.perform(get("/api/com-desc-estates/{id}", com_desc_estate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(com_desc_estate.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()))
            .andExpect(jsonPath("$.noext").value(DEFAULT_NOEXT.toString()))
            .andExpect(jsonPath("$.noint").value(DEFAULT_NOINT.toString()))
            .andExpect(jsonPath("$.locale").value(DEFAULT_LOCALE.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCom_desc_estate() throws Exception {
        // Get the com_desc_estate
        restCom_desc_estateMockMvc.perform(get("/api/com-desc-estates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCom_desc_estate() throws Exception {
        // Initialize the database
        com_desc_estateService.save(com_desc_estate);

        int databaseSizeBeforeUpdate = com_desc_estateRepository.findAll().size();

        // Update the com_desc_estate
        Com_desc_estate updatedCom_desc_estate = new Com_desc_estate();
        updatedCom_desc_estate.setId(com_desc_estate.getId());
        updatedCom_desc_estate.setStreet(UPDATED_STREET);
        updatedCom_desc_estate.setNoext(UPDATED_NOEXT);
        updatedCom_desc_estate.setNoint(UPDATED_NOINT);
        updatedCom_desc_estate.setLocale(UPDATED_LOCALE);
        updatedCom_desc_estate.setReference(UPDATED_REFERENCE);

        restCom_desc_estateMockMvc.perform(put("/api/com-desc-estates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCom_desc_estate)))
                .andExpect(status().isOk());

        // Validate the Com_desc_estate in the database
        List<Com_desc_estate> com_desc_estates = com_desc_estateRepository.findAll();
        assertThat(com_desc_estates).hasSize(databaseSizeBeforeUpdate);
        Com_desc_estate testCom_desc_estate = com_desc_estates.get(com_desc_estates.size() - 1);
        assertThat(testCom_desc_estate.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testCom_desc_estate.getNoext()).isEqualTo(UPDATED_NOEXT);
        assertThat(testCom_desc_estate.getNoint()).isEqualTo(UPDATED_NOINT);
        assertThat(testCom_desc_estate.getLocale()).isEqualTo(UPDATED_LOCALE);
        assertThat(testCom_desc_estate.getReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void deleteCom_desc_estate() throws Exception {
        // Initialize the database
        com_desc_estateService.save(com_desc_estate);

        int databaseSizeBeforeDelete = com_desc_estateRepository.findAll().size();

        // Get the com_desc_estate
        restCom_desc_estateMockMvc.perform(delete("/api/com-desc-estates/{id}", com_desc_estate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Com_desc_estate> com_desc_estates = com_desc_estateRepository.findAll();
        assertThat(com_desc_estates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
