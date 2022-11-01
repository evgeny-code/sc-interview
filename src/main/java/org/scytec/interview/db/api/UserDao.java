package org.scytec.interview.db.api;

import org.scytec.interview.pojo.User;

import java.util.Collection;
import java.util.List;

public interface UserDao {
    List<User> findAll();

    User findById(Long id);

    void insert(User user);

    void update(User user);

    void update(Collection<User> users);

    void deleteById(Long id);
}
