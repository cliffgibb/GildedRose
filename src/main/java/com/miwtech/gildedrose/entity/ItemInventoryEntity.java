package com.miwtech.gildedrose.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ItemInventoryEntity {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private ItemEntity itemEntity;
    private int totalAvailable;

}
