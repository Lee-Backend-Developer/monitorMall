package com.api.monitormall.service;

import com.api.monitormall.entity.Product;
import com.api.monitormall.exception.ProductNotFount;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.ProductCreate;
import com.api.monitormall.request.ProductEdit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("제품이 저장 되어야함")
    @Test
    void save_O() {
        // given
        ProductCreate request = ProductCreate.builder()
                .name("테스트 27인치")
                .price(300000)
                .brand("dell")
                .inch(27)
                .speaker(true)
                .usb(true)
                .dp(true)
                .img01("/image/product.jpg")
                .build();

        // when
        productService.createProduct(request);

        // then
        assertEquals(1L, productRepository.count());
    }

    @DisplayName("수정이 되어야함")
    @Test
    void edit_O() {
        // given
        Product product = getProduct();

        ProductEdit request = ProductEdit.builder()
                .name(product.getName())
                .price(product.getPrice())
                .brand(product.getBrand())
                .inch(product.getInch())
                .speaker(product.isSpeaker())
                .usb(product.isUsb())
                .dp(product.isDp())
                .img01(product.getImg01())
                .build();

        // when
        request.setName("수정이 되었습니다.");
        productService.edit(product.getProductId(), request);

        // then
        Product findProduct = productRepository.findById(product.getProductId()).orElseThrow(ProductNotFount::new);
        assertEquals(findProduct.getName(), "수정이 되었습니다.");
    }

    @DisplayName("삭제가 되어야함")
    @Test
    void delete_O() {
        // given
        Product product = getProduct();

        // when
        Long productId = product.getProductId();
        productService.deleteProduct(productId);

        // then
        assertEquals(0, productRepository.count());
    }

    private Product getProduct() {
        Product product = Product.builder()
                .name("테스트 27인치")
                .price(300000)
                .brand("dell")
                .inch(27)
                .speaker(true)
                .usb(true)
                .dp(true)
                .img01("/image/product.jpg")
                .build();
        productRepository.save(product);
        return product;
    }
}