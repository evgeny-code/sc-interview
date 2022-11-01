package org.scytec.interview.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Упрощенный объект клана
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Clan {
    private long id;     // id клана
    private String name; // имя клана
    private int gold;    // текущее количество золота в казне клана

    public void addGold(int amount) {
        gold += amount;
    }
}