package com.example.catering.models;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "menu_name")
    private String menuName;

    // 这里改成枚举
    @Column(name = "menu_type")
    private Integer menuType;

    @Column(name = "price")
    private BigDecimal price;

    // 图片地址
    @Column(name = "menuImage")
    private String menuImage;

    @Column(name = "description")
    private String description;

    // 这里改成枚举
    @Column(name = "menu_status")
    private Integer status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;
}
