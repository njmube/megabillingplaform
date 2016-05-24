package org.megapractical.billingplatform.web.rest;

import org.megapractical.billingplatform.MegabillingplatformApp;
import org.megapractical.billingplatform.domain.Category_product;
import org.megapractical.billingplatform.repository.Category_productRepository;
import org.megapractical.billingplatform.service.Category_productService;

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
 * Test class for the Category_productResource REST controller.
 *
 * @see Category_productResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MegabillingplatformApp.class)
@WebAppConfiguration
@IntegrationTest
public class Category_productResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private Category_productRepository category_productRepository;

    @Inject
    private Category_productService category_productService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCategory_productMockMvc;

    private Category_product category_product;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Category_productResource category_productResource = new Category_productResource();
        ReflectionTestUtils.setField(category_productResource, "category_productService", category_productService);
        this.restCategory_productMockMvc = MockMvcBuilders.standaloneSetup(category_productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        category_product = new Category_product();
        category_product.setName(DEFAULT_NAME);
        category_product.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCategory_product() throws Exception {
        int databaseSizeBeforeCreate = category_productRepository.findAll().size();

        // Create the Category_product

        restCategory_productMockMvc.perform(post("/api/category-products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(category_product)))
                .andExpect(status().isCreated());

        // Validate the Category_product in the database
        List<Category_product> category_products = category_productRepository.findAll();
        assertThat(category_products).hasSize(databaseSizeBeforeCreate + 1);
        Category_product testCategory_product = category_products.get(category_products.size() - 1);
        assertThat(testCategory_product.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCategory_product.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategory_products() throws Exception {
        // Initialize the database
        category_productRepository.saveAndFlush(category_product);

        // Get all the category_products
        restCategory_productMockMvc.perform(get("/api/category-products?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(category_product.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getCategory_product() throws Exception {
        // Initialize the database
        category_productRepository.saveAndFlush(category_product);

        // Get the category_product
        restCategory_productMockMvc.perform(get("/api/category-products/{id}", category_product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(category_product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategory_product() throws Exception {
        // Get the category_product
        restCategory_productMockMvc.perform(get("/api/category-products/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategory_product() throws Exception {
        // Initialize the database
        category_productService.save(category_product);

        int databaseSizeBeforeUpdate = category_productRepository.findAll().size();

        // Update the category_product
        Category_product updatedCategory_product = new Category_product();
        updatedCategory_product.setId(category_product.getId());
        updatedCategory_product.setName(UPDATED_NAME);
        updatedCategory_product.setDescription(UPDATED_DESCRIPTION);

        restCategory_productMockMvc.perform(put("/api/category-products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedCategory_product)))
                .andExpect(status().isOk());

        // Validate the Category_product in the database
        List<Category_product> category_products = category_productRepository.findAll();
        assertThat(category_products).hasSize(databaseSizeBeforeUpdate);
        Category_product testCategory_product = category_products.get(category_products.size() - 1);
        assertThat(testCategory_product.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCategory_product.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteCategory_product() throws Exception {
        // Initialize the database
        category_productService.save(category_product);

        int databaseSizeBeforeDelete = category_productRepository.findAll().size();

        // Get the category_product
        restCategory_productMockMvc.perform(delete("/api/category-products/{id}", category_product.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Category_product> category_products = category_productRepository.findAll();
        assertThat(category_products).hasSize(databaseSizeBeforeDelete - 1);
    }
}
