package org.scytec.interview.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LockByKey<T> {
    private Set<T> usedKeys = ConcurrentHashMap.newKeySet();

    public boolean tryLock(T key) {
        return usedKeys.add(key);
    }

    public void lock(T key) {
        while (!tryLock(key)) {
        }
    }

    public void unlock(T key) {
        usedKeys.remove(key);
    }
}