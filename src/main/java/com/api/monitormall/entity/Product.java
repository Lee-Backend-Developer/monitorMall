package com.api.monitormall.entity;

import com.api.monitormall.request.ProductEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import static lombok.AccessLevel.*;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    // 기본 정보
    private String name;
    private int price;
    private String brand;
    private double inch;
    private boolean speaker;
    private boolean usb;

    // 모니터 단자
    private boolean vga;
    private boolean dvi;
    private boolean hdmi;
    private boolean dp;

    // 이미지
    private String img01;
    private String img02;
    private String img03;
    private String img04;
    private String img05;

    @Builder
    public Product(Long productId, String name, int price, String brand, double inch, boolean speaker, boolean usb, boolean vga, boolean dvi, boolean hdmi, boolean dp, String img01, String img02, String img03, String img04, String img05) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.inch = inch;
        this.speaker = speaker;
        this.usb = usb;
        this.vga = vga;
        this.dvi = dvi;
        this.hdmi = hdmi;
        this.dp = dp;
        this.img01 = img01;
        this.img02 = img02;
        this.img03 = img03;
        this.img04 = img04;
        this.img05 = img05;
    }

    public void edit(ProductEdit edit){
        this.name = edit.getName();
        this.price = edit.getPrice();
        this.brand = edit.getBrand();
        this.inch = edit.getInch();
        this.speaker = edit.isSpeaker();
        this.usb = edit.isUsb();
        this.vga = edit.isVga();
        this.dvi = edit.isDvi();
        this.hdmi = edit.isHdmi();
        this.dp = edit.isDp();
        this.img01 = edit.getImg01();
        this.img02 = edit.getImg02();
        this.img03 = edit.getImg03();
        this.img04 = edit.getImg04();
        this.img05 = edit.getImg05();
    }
}
