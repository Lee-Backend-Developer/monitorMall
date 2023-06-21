package com.api.monitormall.service;

import com.api.monitormall.entity.Product;
import com.api.monitormall.repository.ProductRepository;
import com.api.monitormall.request.ProductCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .size(create.getSize())
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

}
