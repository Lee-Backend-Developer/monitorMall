package com.api.monitormall.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long productId;

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
}
