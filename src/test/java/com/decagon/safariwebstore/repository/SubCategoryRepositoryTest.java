//package com.decagon.safariwebstore.repository;
//
//import com.decagon.safariwebstore.model.Category;
//import com.decagon.safariwebstore.model.CategoryDTO;
//import com.decagon.safariwebstore.model.SubCategory;
//import com.decagon.safariwebstore.model.SubCategoryDTO;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
////@DataJpaTest
//class SubCategoryRepositoryTest {
//
//    @Autowired
//    private CategoryRepository categoryRepositoryUnderTest;
//
//    @Autowired
//    private SubCategoryRepository subCategoryRepositoryUnderTest;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @AfterEach
//    void tearDown() {
//        subCategoryRepositoryUnderTest.deleteAll();
//        categoryRepositoryUnderTest.deleteAll();
//    }
//
//    @Test
//    void findByNameAndCategory() {
//        // given
//        Category clothes = modelMapper.map(new CategoryDTO("clothes"), Category.class);
//        categoryRepositoryUnderTest.save(clothes);
//        Category shoes = modelMapper.map(new CategoryDTO("shoes"), Category.class);
//        categoryRepositoryUnderTest.save(shoes);
//        Category accessories = modelMapper.map(new CategoryDTO("accessories"), Category.class);
//        categoryRepositoryUnderTest.save(accessories);
//
//        SubCategory dresses = modelMapper.map(new SubCategoryDTO("dresses", clothes), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(dresses);
//        SubCategory denim = modelMapper.map(new SubCategoryDTO("denim", clothes), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(denim);
//        SubCategory flats = modelMapper.map(new SubCategoryDTO("flats", shoes), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(flats);
//        SubCategory watches = modelMapper.map(new SubCategoryDTO("watches", accessories), SubCategory.class);
//        subCategoryRepositoryUnderTest.save(watches);
//
//        // when
//        Optional<SubCategory> clothesSubcategory = subCategoryRepositoryUnderTest
//                .findByNameAndCategory("dresses", clothes);
//        Optional<SubCategory> clothesSubcategory1 = subCategoryRepositoryUnderTest
//                .findByNameAndCategory("denim", clothes);
//        Optional<SubCategory> shoesSubcategory = subCategoryRepositoryUnderTest
//                .findByNameAndCategory("flats", shoes);
//        Optional<SubCategory> accessoriesSubcategory = subCategoryRepositoryUnderTest
//                .findByNameAndCategory("watches", accessories);
//
//        // then
//        assertAll(
//                () -> assertTrue(clothesSubcategory.isPresent()),
//                () -> assertTrue(clothesSubcategory1.isPresent()),
//                () -> assertTrue(shoesSubcategory.isPresent()),
//                () -> assertTrue(accessoriesSubcategory.isPresent())
//        );
//
//    }
//}