package com.api.monitormall.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductEdit {
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
}
