package com.miwtech.gildedrose.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ViewLogEntity {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime viewTime;
    @OneToOne
    private ItemEntity itemEntity;
}
