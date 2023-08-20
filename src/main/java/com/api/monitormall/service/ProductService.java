package com.api.monitormall.service;

import com.api.monitormall.entity.Product;
import com.api.monitormall.exception.ProductNotFount;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.ProductCreate;
import com.api.monitormall.request.ProductEdit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public void createProduct(ProductCreate create) {
        Product product = Product.builder()
                .name(create.getName())
                .price(create.getPrice())
                .brand(create.getBrand())
                .inch(create.getInch())
                .speaker(create.isSpeaker())
                .usb(create.isUsb())
                .vga(create.isVga())
                .dvi(create.isDvi())
                .hdmi(create.isHdmi())
                .dp(create.isDp())
                .img01(create.getImg01())
                .img02(create.getImg02())
                .img03(create.getImg03())
                .img04(create.getImg04())
                .img05(create.getImg05())
                .build();
        productRepository.save(product);
    }

    public List<Product> findProduct() {
        List<Product> product = productRepository.findAll();
        return product;
    }

    @Transactional
    public void deleteProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        productRepository.delete(product);
    }

    @Transactional
    public void edit(Long productId, ProductEdit request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductNotFount::new);


        product.edit(request);
    }
}
