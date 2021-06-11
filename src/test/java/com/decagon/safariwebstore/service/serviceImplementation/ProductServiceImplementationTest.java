package com.decagon.safariwebstore.service.serviceImplementation;

import com.decagon.safariwebstore.exceptions.ResourceNotFoundException;
import com.decagon.safariwebstore.model.*;
import com.decagon.safariwebstore.payload.request.ProductRequest;
import com.decagon.safariwebstore.repository.*;
import com.decagon.safariwebstore.utils.MethodUtils;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@Slf4j
@ExtendWith(MockitoExtension.class)
class ProductServiceImplementationTest {

    private ProductPage productPage;

    private ModelMapper modelMapper;




    @Mock private ProductRepository productRepository;

    @Mock private CategoryRepository categoryRepository;

    @Mock private SubCategoryRepository subCategoryRepository;

    @Mock private SizeRepository sizeRepository;
    @Mock private ColorRepository colorRepository;
    @Mock private ProductImageRepository productImageRepository;
    @InjectMocks
    private ProductServiceImplementation productServiceUnderTest;

    @BeforeEach
    void setUp() {
        productPage = new ProductPage();
        modelMapper = new ModelMapper();
        productServiceUnderTest = new ProductServiceImplementation(productRepository,
                categoryRepository, subCategoryRepository, sizeRepository,
                colorRepository, productImageRepository);
    }

    @Test
    void canGetAllProducts() {
        // given
        Pageable pageable = MethodUtils.getPageable(productPage);

        // when
        productServiceUnderTest.getAllProducts(productPage);

        // then
        verify(productRepository).findAll(pageable);
    }

    @Test
    void canGetProductsByCategory() {
        // given
        Category clothes = modelMapper.map(new CategoryDTO("clothes"), Category.class);
        Pageable pageable = MethodUtils.getPageable(productPage);
        given(categoryRepository.findByName("clothes")).willReturn(java.util.Optional.of(clothes));

        // when
        productServiceUnderTest.getProductsByCategory(productPage, "clothes");

        // then
        verify(productRepository).findAllByCategory(clothes, pageable);

    }

    @Test
    void willThrowWhenCategoryIsNotFound1() {
        // given
        Category clothes = modelMapper.map(new CategoryDTO("clothes"), Category.class);
        Pageable pageable = MethodUtils.getPageable(productPage);

        // when
        // then
        assertThatThrownBy(() -> productServiceUnderTest.getProductsByCategory(productPage, "clothes"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found!");

        verify(productRepository, never()).findAllByCategory(clothes, pageable);
    }

    @Test
    void canGetProductsByCategoryAndSubCategory() {
        // given
        Category clothes = modelMapper.map(new CategoryDTO("clothes"), Category.class);
        SubCategory dresses = modelMapper.map(new SubCategoryDTO("dresses", clothes), SubCategory.class);
        Pageable pageable = MethodUtils.getPageable(productPage);
        given(categoryRepository.findByName("clothes")).willReturn(java.util.Optional.of(clothes));
        given(subCategoryRepository.findByNameAndCategory("dresses", clothes))
                .willReturn(java.util.Optional.of(dresses));

        // when
        productServiceUnderTest.getProductsByCategoryAndSubCategory(productPage,
                "clothes", "dresses");

        // then
        verify(productRepository).findAllByCategoryAndSubCategory(clothes, dresses, pageable);
    }

    @Test
    void willThrowWhenCategoryIsNotFound2() {
        // given
        Category clothes = modelMapper.map(new CategoryDTO("clothes"), Category.class);
        SubCategory dresses = modelMapper.map(new SubCategoryDTO("dresses", clothes), SubCategory.class);
        Pageable pageable = MethodUtils.getPageable(productPage);

        // when
        // then
        assertThatThrownBy(() -> productServiceUnderTest.getProductsByCategoryAndSubCategory(productPage,
                "clothes", "dresses"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Category not found!");

        verify(productRepository, never()).findAllByCategoryAndSubCategory(clothes, dresses, pageable);
    }

    @Test
    void willThrowWhenSubCategoryIsNotFound() {
        // given
        Category clothes = modelMapper.map(new CategoryDTO("clothes"), Category.class);
        SubCategory dresses = modelMapper.map(new SubCategoryDTO("dresses", clothes), SubCategory.class);
        Pageable pageable = MethodUtils.getPageable(productPage);
        given(categoryRepository.findByName("clothes")).willReturn(java.util.Optional.of(clothes));

        // when
        // then
        assertThatThrownBy(() -> productServiceUnderTest.getProductsByCategoryAndSubCategory(productPage,
                "clothes", "dresses"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Sub-Category not found!");

        verify(productRepository, never()).findAllByCategoryAndSubCategory(clothes, dresses, pageable);
    }

    @Test
    void saveProductTest() {
        List<String> category = new ArrayList<>();
        category.add("clothes");
        category.add("shoes");


        List<String> subCategories = new ArrayList<>();
        subCategories.add("denim");
        subCategories.add("sneakers");

        List<String> sizes = new ArrayList<>();
        sizes.add("L");
        sizes.add("XL");


        List<String> colors = new ArrayList<>();
        colors.add("#4533sd");
        colors.add("#adscaer");
        colors.add("#FFFFFF");

        List<String> images = new ArrayList<>();
        images.add("http://awsS3/image.jpg");
        images.add("http://awsS3/image2.jpg");


        List<Category> c_category = new ArrayList<>();
        c_category.add(new Category("clothes"));
        c_category.add(new Category("shoes"));

        List<SubCategory> c_subCategories = new ArrayList<>();
        c_subCategories.add(new SubCategory("denin", new Category("clothes")));
        c_subCategories.add(new SubCategory("sneakers", new Category("shoes")));

        List<Size> c_sizes = new ArrayList<>();
        c_sizes.add(new Size("L"));
        c_sizes.add(new Size("XL"));


        List<Color> c_colors = new ArrayList<>();
        c_colors.add(new Color("#4533sd"));
        c_colors.add(new Color("#adscaer"));
        c_colors.add(new Color("#FFFFFF"));

        List<ProductImage> c_images = new ArrayList<>();
        c_images.add(new ProductImage("http://awsS3/image.jpg"));
        c_images.add(new ProductImage("http://awsS3/image2.jpg"));

        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("L.V. shirt");
        productRequest.setPrice(18000);
        productRequest.setDescription("A nice product");
        productRequest.setCategory(category);
        productRequest.setSubCategory(subCategories);
        productRequest.setSizes(sizes);
        productRequest.setColors(colors);
        productRequest.setProductImages(images);

        Product product = new Product();
        product.setName("L.V. shirt");
        product.setPrice(18000);
        product.setDescription("A nice product");
        product.setCategory(c_category);
        product.setSubCategory(c_subCategories);
        product.setSizes(c_sizes);
        product.setColors(c_colors);
        product.setProductImages(c_images);


        Mockito.lenient().when(productServiceUnderTest.saveProduct(productRequest)).thenReturn(product);



    }
}