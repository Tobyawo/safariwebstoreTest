package com.decagon.safariwebstore.service;

import com.decagon.safariwebstore.model.Category;
import com.decagon.safariwebstore.model.Product;
import com.decagon.safariwebstore.model.ProductPage;
import com.decagon.safariwebstore.model.SubCategory;
import com.decagon.safariwebstore.payload.request.ProductRequest;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> getAllProducts(ProductPage productPage);
    Page<Product> getProductsByCategory(ProductPage productPage, String categoryName);
    Page<Product> getProductsByCategoryAndSubCategory(ProductPage productPage,
                                                      String categoryName, String subCategoryName);
    Product saveProduct(ProductRequest productRequest);

}
