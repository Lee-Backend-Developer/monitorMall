package com.api.monitormall.request;

import lombok.Builder;
import lombok.Data;

@Data
public class ProductCreate {
    // 기본 정보
    private String name;
    private int price;
    private String brand;
    private double size;
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
    public ProductCreate(String name, int price, String brand, double size, boolean speaker, boolean usb, boolean vga, boolean dvi, boolean hdmi, boolean dp, String img01, String img02, String img03, String img04, String img05) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.size = size;
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
}
