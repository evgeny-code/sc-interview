package org.scytec.interview.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String name;
    private AtomicInteger gold;

    public int addGold(int amount) {
        return gold.addAndGet(amount);
    }
}
