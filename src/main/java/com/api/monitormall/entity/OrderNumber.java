package com.api.monitormall.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class OrderNumber {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long orderNumberId;

    @OneToMany(mappedBy = "orderNumber")
    private final List<Orders> orderList = new ArrayList<>();

}
