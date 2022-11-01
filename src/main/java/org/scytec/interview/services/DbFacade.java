package org.scytec.interview.services;

import org.scytec.interview.db.api.ClanDao;
import org.scytec.interview.db.api.ClanHistoryDao;
import org.scytec.interview.db.api.TaskDao;
import org.scytec.interview.db.api.UserDao;
import org.scytec.interview.db.impl.ClanDaoImpl;
import org.scytec.interview.db.impl.ClanHistoryDaoImpl;
import org.scytec.interview.db.impl.TaskDaoImpl;
import org.scytec.interview.db.impl.UserDaoImpl;
import org.scytec.interview.pojo.Clan;
import org.scytec.interview.pojo.ClanHistory;
import org.scytec.interview.pojo.Task;
import org.scytec.interview.pojo.User;
import org.scytec.interview.util.LockByKey;

import java.util.*;

public enum DbFacade {
    INSTANCE;

    private final TaskDao taskDao = TaskDaoImpl.INSTANCE;
    private final ClanDao clanDao = ClanDaoImpl.INSTANCE;
    private final UserDao userDao = UserDaoImpl.INSTANCE;
    private final ClanHistoryDao clanHistoryDao = ClanHistoryDaoImpl.INSTANCE;

    public final Map<Long, Task> taskCash = new WeakHashMap<>();

    public final Map<Long, User> userMap = new WeakHashMap<>();
    public final Set<User> modifiedUsers = Collections.synchronizedSet(new HashSet<>());
    public final LockByKey<Long> userLock = new LockByKey();

    public final Map<Long, Clan> clanMap = new WeakHashMap<>();
    public final Set<Clan> modifiedClans = Collections.synchronizedSet(new HashSet<>());
    public final LockByKey<Long> clanLock = new LockByKey();

    public final Set<ClanHistory> clanHistorySetForSave = Collections.synchronizedSet(new HashSet<>());


    public Task getTask(long taskId) {
        Task taskCash = DbFacade.INSTANCE.taskCash.get(taskId);
        if (null != taskCash) {
            return taskCash;
        }

        Task task = taskDao.findById(taskId);
        DbFacade.INSTANCE.taskCash.put(taskId, task);
        return task;
    }

    public User getUser(long userId) {
        User userCash = DbFacade.INSTANCE.userMap.get(userId);
        if (null != userCash) {
            return userCash;
        }

        User clan = userDao.findById(userId);
        DbFacade.INSTANCE.userMap.put(userId, clan);
        return clan;
    }

    public User updateUser(User user) {
        modifiedUsers.add(user);
        return user;
    }

    public Clan getClan(long clanId) {
        Clan clanCash = DbFacade.INSTANCE.clanMap.get(clanId);
        if (null != clanCash) {
            return clanCash;
        }

        Clan clan = clanDao.findById(clanId);
        DbFacade.INSTANCE.clanMap.put(clanId, clan);
        return clan;
    }

    public Clan updateClan(Clan clan) {
        modifiedClans.add(clan);
        return clan;
    }


    public void updateTogether(Clan clan, User user) {
        synchronized (modifiedClans) {
            synchronized (modifiedUsers) {
                modifiedClans.add(clan);
                modifiedUsers.add(user);
            }
        }
    }

    public void flushHistory() {
        synchronized (clanHistorySetForSave) {
            if (!clanHistorySetForSave.isEmpty()) {
                clanHistoryDao.insert(clanHistorySetForSave);
                clanHistorySetForSave.clear();
            }
        }
    }

    public void flushUsers() {
        synchronized (modifiedUsers) {
            if (!modifiedUsers.isEmpty()) {
                userDao.update(modifiedUsers);
                modifiedUsers.clear();
            }
        }
    }

    public void flushClans() {
        synchronized (modifiedClans) {
            if (!modifiedClans.isEmpty()) {
                clanDao.update(modifiedClans);
                modifiedClans.clear();
            }
        }
    }
}
