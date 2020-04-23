package com.miwtech.gildedrose.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Lob
    private String description;
    private BigDecimal price;

    public ItemEntity(String name, String description, int price) {
        BigDecimal priceValue = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
        this.name = name;
        this.description = description;
        this.price = priceValue;
    }
}
