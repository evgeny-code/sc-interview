package org.scytec.interview.db.impl;

import org.jooq.Query;
import org.scytec.interview.db.api.UserDao;
import org.scytec.interview.db.model.tables.UserProfile;
import org.scytec.interview.db.model.tables.records.UserProfileRecord;
import org.scytec.interview.pojo.User;
import org.scytec.interview.services.DBCH;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public enum UserDaoImpl implements UserDao {
    INSTANCE;

    @Override
    public List<User> findAll() {
        return DBCH.INSTANCE.getDslContext().fetch(UserProfile.USER_PROFILE).stream()
                .map(userProfileRecord -> User.builder()
                        .id(userProfileRecord.getId())
                        .name(userProfileRecord.getName())
                        .gold(new AtomicInteger(userProfileRecord.getGold()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        UserProfileRecord userProfileRecord = DBCH.INSTANCE.getDslContext()
                .fetchOne(UserProfile.USER_PROFILE, UserProfile.USER_PROFILE.ID.eq(id));

        return User.builder()
                .id(userProfileRecord.getId())
                .name(userProfileRecord.getName())
                .gold(new AtomicInteger(userProfileRecord.getGold()))
                .build();
    }

    @Override
    public void insert(User user) {
        UserProfileRecord userProfileRecord = DBCH.INSTANCE.getDslContext().newRecord(UserProfile.USER_PROFILE);

        userProfileRecord.setName(user.getName());
        userProfileRecord.setGold(user.getGold().get());

        userProfileRecord.store();
    }


    @Override
    public void update(User user) {
        DBCH.INSTANCE.getDslContext().update(UserProfile.USER_PROFILE)
                .set(UserProfile.USER_PROFILE.NAME, user.getName())
                .set(UserProfile.USER_PROFILE.GOLD, user.getGold().get())
                .where(UserProfile.USER_PROFILE.ID.eq(user.getId()))
                .execute();
    }

    @Override
    public void update(Collection<User> users) {
        List<Query> queries = new ArrayList<>();
        for (User user : users) {
            queries.add(DBCH.INSTANCE.getDslContext()
                    .update(UserProfile.USER_PROFILE)
                    .set(UserProfile.USER_PROFILE.NAME, user.getName())
                    .set(UserProfile.USER_PROFILE.GOLD, user.getGold().get())
                    .where(UserProfile.USER_PROFILE.ID.eq(user.getId())));
        }

        DBCH.INSTANCE.getDslContext().batch(queries).execute();
    }

    @Override
    public void deleteById(Long id) {
        DBCH.INSTANCE.getDslContext().delete(UserProfile.USER_PROFILE)
                .where(UserProfile.USER_PROFILE.ID.eq(id))
                .execute();
    }

}
