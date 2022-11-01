package org.scytec.interview.db.impl;

import org.scytec.interview.db.api.TaskDao;
import org.scytec.interview.db.model.tables.Task;
import org.scytec.interview.db.model.tables.records.TaskRecord;
import org.scytec.interview.services.DBCH;

import java.util.List;
import java.util.stream.Collectors;

public enum TaskDaoImpl implements TaskDao {
    INSTANCE;

    @Override
    public List<org.scytec.interview.pojo.Task> findAll() {
        return DBCH.INSTANCE.getDslContext().fetch(Task.TASK).stream()
                .map(taskRecord -> org.scytec.interview.pojo.Task.builder()
                        .id(taskRecord.getId())
                        .name(taskRecord.getName())
                        .reward(taskRecord.getReward())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public org.scytec.interview.pojo.Task findById(Long id) {
        TaskRecord taskRecord = DBCH.INSTANCE.getDslContext()
                .fetchOne(Task.TASK, Task.TASK.ID.eq(id));

        return org.scytec.interview.pojo.Task.builder()
                .id(taskRecord.getId())
                .name(taskRecord.getName())
                .reward(taskRecord.getReward())
                .build();
    }

    @Override
    public void insert(org.scytec.interview.pojo.Task task) {
        TaskRecord taskRecord = DBCH.INSTANCE.getDslContext().newRecord(Task.TASK);

        taskRecord.setName(task.getName());
        taskRecord.setReward(task.getReward());

        taskRecord.store();
    }


    @Override
    public void update(org.scytec.interview.pojo.Task task) {
        DBCH.INSTANCE.getDslContext().update(Task.TASK)
                .set(Task.TASK.NAME, task.getName())
                .set(Task.TASK.REWARD, task.getReward())
                .where(Task.TASK.ID.eq(task.getId()))
                .execute();
    }

    @Override
    public void deleteById(Long id) {
        DBCH.INSTANCE.getDslContext().delete(Task.TASK)
                .where(Task.TASK.ID.eq(id))
                .execute();
    }

}
