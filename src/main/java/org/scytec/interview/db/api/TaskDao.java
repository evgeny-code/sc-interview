package org.scytec.interview.db.api;

import org.scytec.interview.pojo.Task;

import java.util.List;

public interface TaskDao {
    List<Task> findAll();

    Task findById(Long id);

    void insert(Task task);

    void update(Task task);

    void deleteById(Long id);
}
