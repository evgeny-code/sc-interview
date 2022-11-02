package org.scytec.interview.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

// Упрощенный объект клана
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Clan {
    private long id;     // id клана
    private String name; // имя клана
    private AtomicInteger gold;    // текущее количество золота в казне клана

    public int addGold(int amount) {
        return gold.addAndGet(amount);
    }
}