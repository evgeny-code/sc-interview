package org.scytec.interview.actions;

import lombok.RequiredArgsConstructor;
import org.scytec.interview.db.api.ClanDao;
import org.scytec.interview.db.api.TaskDao;
import org.scytec.interview.db.api.UserDao;
import org.scytec.interview.db.impl.ClanDaoImpl;
import org.scytec.interview.db.impl.TaskDaoImpl;
import org.scytec.interview.db.impl.UserDaoImpl;
import org.scytec.interview.pojo.Clan;
import org.scytec.interview.pojo.Task;
import org.scytec.interview.pojo.User;

import java.util.UUID;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class GenerateRandomData implements Callable<Void> {

    private ClanDao clanDao = ClanDaoImpl.INSTANCE;
    private UserDao userDao = UserDaoImpl.INSTANCE;
    private TaskDao taskDao = TaskDaoImpl.INSTANCE;

    @Override
    public Void call() throws Exception {
        for (int i = 0; i < 10; i++) {
            clanDao.insert(Clan.builder()
                    .name("Clan #" + UUID.randomUUID())
                    .gold(100 + 10 * i)
                    .build());
        }

        for (int i = 0; i < 10; i++) {
            userDao.insert(User.builder()
                    .name("User #" + UUID.randomUUID())
                    .gold(10 + i)
                    .build());
        }

        for (int i = 0; i < 10; i++) {
            taskDao.insert(Task.builder()
                    .name("Task #" + UUID.randomUUID())
                    .reward(20 + 2 * i)
                    .build());
        }

        return null;
    }
}
