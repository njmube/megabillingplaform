package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Ring_pack;
import org.megapractical.billingplatform.repository.Ring_packRepository;
import org.megapractical.billingplatform.service.Ring_packService;

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
 * Test class for the Ring_packResource REST controller.
 *
 * @see Ring_packResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Ring_packResourceIntTest {

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final String DEFAULT_REFERENCE_CODE = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_REFERENCE_CODE = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final Integer DEFAULT_RINGS = 1;
    private static final Integer UPDATED_RINGS = 2;

    @Inject
    private Ring_packRepository ring_packRepository;

    @Inject
    private Ring_packService ring_packService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRing_packMockMvc;

    private Ring_pack ring_pack;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Ring_packResource ring_packResource = new Ring_packResource();
        ReflectionTestUtils.setField(ring_packResource, "ring_packService", ring_packService);
        this.restRing_packMockMvc = MockMvcBuilders.standaloneSetup(ring_packResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        ring_pack = new Ring_pack();
        ring_pack.setCurrency(DEFAULT_CURRENCY);
        ring_pack.setDescription(DEFAULT_DESCRIPTION);
        ring_pack.setPrice(DEFAULT_PRICE);
        ring_pack.setReference_code(DEFAULT_REFERENCE_CODE);
        ring_pack.setRings(DEFAULT_RINGS);
    }

    @Test
    @Transactional
    public void createRing_pack() throws Exception {
        int databaseSizeBeforeCreate = ring_packRepository.findAll().size();

        // Create the Ring_pack

        restRing_packMockMvc.perform(post("/api/ring-packs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ring_pack)))
                .andExpect(status().isCreated());

        // Validate the Ring_pack in the database
        List<Ring_pack> ring_packs = ring_packRepository.findAll();
        assertThat(ring_packs).hasSize(databaseSizeBeforeCreate + 1);
        Ring_pack testRing_pack = ring_packs.get(ring_packs.size() - 1);
        assertThat(testRing_pack.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testRing_pack.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRing_pack.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testRing_pack.getReference_code()).isEqualTo(DEFAULT_REFERENCE_CODE);
        assertThat(testRing_pack.getRings()).isEqualTo(DEFAULT_RINGS);
    }

    @Test
    @Transactional
    public void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = ring_packRepository.findAll().size();
        // set the field null
        ring_pack.setCurrency(null);

        // Create the Ring_pack, which fails.

        restRing_packMockMvc.perform(post("/api/ring-packs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ring_pack)))
                .andExpect(status().isBadRequest());

        List<Ring_pack> ring_packs = ring_packRepository.findAll();
        assertThat(ring_packs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ring_packRepository.findAll().size();
        // set the field null
        ring_pack.setDescription(null);

        // Create the Ring_pack, which fails.

        restRing_packMockMvc.perform(post("/api/ring-packs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ring_pack)))
                .andExpect(status().isBadRequest());

        List<Ring_pack> ring_packs = ring_packRepository.findAll();
        assertThat(ring_packs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = ring_packRepository.findAll().size();
        // set the field null
        ring_pack.setPrice(null);

        // Create the Ring_pack, which fails.

        restRing_packMockMvc.perform(post("/api/ring-packs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ring_pack)))
                .andExpect(status().isBadRequest());

        List<Ring_pack> ring_packs = ring_packRepository.findAll();
        assertThat(ring_packs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReference_codeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ring_packRepository.findAll().size();
        // set the field null
        ring_pack.setReference_code(null);

        // Create the Ring_pack, which fails.

        restRing_packMockMvc.perform(post("/api/ring-packs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ring_pack)))
                .andExpect(status().isBadRequest());

        List<Ring_pack> ring_packs = ring_packRepository.findAll();
        assertThat(ring_packs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRingsIsRequired() throws Exception {
        int databaseSizeBeforeTest = ring_packRepository.findAll().size();
        // set the field null
        ring_pack.setRings(null);

        // Create the Ring_pack, which fails.

        restRing_packMockMvc.perform(post("/api/ring-packs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(ring_pack)))
                .andExpect(status().isBadRequest());

        List<Ring_pack> ring_packs = ring_packRepository.findAll();
        assertThat(ring_packs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRing_packs() throws Exception {
        // Initialize the database
        ring_packRepository.saveAndFlush(ring_pack);

        // Get all the ring_packs
        restRing_packMockMvc.perform(get("/api/ring-packs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(ring_pack.getId().intValue())))
                .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
                .andExpect(jsonPath("$.[*].reference_code").value(hasItem(DEFAULT_REFERENCE_CODE.toString())))
                .andExpect(jsonPath("$.[*].rings").value(hasItem(DEFAULT_RINGS)));
    }

    @Test
    @Transactional
    public void getRing_pack() throws Exception {
        // Initialize the database
        ring_packRepository.saveAndFlush(ring_pack);

        // Get the ring_pack
        restRing_packMockMvc.perform(get("/api/ring-packs/{id}", ring_pack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(ring_pack.getId().intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.reference_code").value(DEFAULT_REFERENCE_CODE.toString()))
            .andExpect(jsonPath("$.rings").value(DEFAULT_RINGS));
    }

    @Test
    @Transactional
    public void getNonExistingRing_pack() throws Exception {
        // Get the ring_pack
        restRing_packMockMvc.perform(get("/api/ring-packs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRing_pack() throws Exception {
        // Initialize the database
        ring_packService.save(ring_pack);

        int databaseSizeBeforeUpdate = ring_packRepository.findAll().size();

        // Update the ring_pack
        Ring_pack updatedRing_pack = new Ring_pack();
        updatedRing_pack.setId(ring_pack.getId());
        updatedRing_pack.setCurrency(UPDATED_CURRENCY);
        updatedRing_pack.setDescription(UPDATED_DESCRIPTION);
        updatedRing_pack.setPrice(UPDATED_PRICE);
        updatedRing_pack.setReference_code(UPDATED_REFERENCE_CODE);
        updatedRing_pack.setRings(UPDATED_RINGS);

        restRing_packMockMvc.perform(put("/api/ring-packs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedRing_pack)))
                .andExpect(status().isOk());

        // Validate the Ring_pack in the database
        List<Ring_pack> ring_packs = ring_packRepository.findAll();
        assertThat(ring_packs).hasSize(databaseSizeBeforeUpdate);
        Ring_pack testRing_pack = ring_packs.get(ring_packs.size() - 1);
        assertThat(testRing_pack.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testRing_pack.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRing_pack.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testRing_pack.getReference_code()).isEqualTo(UPDATED_REFERENCE_CODE);
        assertThat(testRing_pack.getRings()).isEqualTo(UPDATED_RINGS);
    }

    @Test
    @Transactional
    public void deleteRing_pack() throws Exception {
        // Initialize the database
        ring_packService.save(ring_pack);

        int databaseSizeBeforeDelete = ring_packRepository.findAll().size();

        // Get the ring_pack
        restRing_packMockMvc.perform(delete("/api/ring-packs/{id}", ring_pack.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Ring_pack> ring_packs = ring_packRepository.findAll();
        assertThat(ring_packs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
