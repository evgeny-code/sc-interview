package org.scytec.interview.db.impl;

import org.jooq.Query;
import org.scytec.interview.db.api.ClanDao;
import org.scytec.interview.db.model.tables.Clan;
import org.scytec.interview.db.model.tables.records.ClanRecord;
import org.scytec.interview.services.DBCH;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public enum ClanDaoImpl implements ClanDao {
    INSTANCE;

    @Override
    public List<org.scytec.interview.pojo.Clan> findAll() {
        return DBCH.INSTANCE.getDslContext().fetch(Clan.CLAN).stream()
                .map(clanRecord -> org.scytec.interview.pojo.Clan.builder()
                        .id(clanRecord.getId())
                        .name(clanRecord.getName())
                        .gold(new AtomicInteger(clanRecord.getGold()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public org.scytec.interview.pojo.Clan findById(Long id) {
        ClanRecord clanRecord = DBCH.INSTANCE.getDslContext()
                .fetchOne(Clan.CLAN, Clan.CLAN.ID.eq(id));

        return org.scytec.interview.pojo.Clan.builder()
                .id(clanRecord.getId())
                .name(clanRecord.getName())
                .gold(new AtomicInteger(clanRecord.getGold()))
                .build();
    }

    @Override
    public void insert(org.scytec.interview.pojo.Clan clan) {
        ClanRecord clanRecord = DBCH.INSTANCE.getDslContext().newRecord(Clan.CLAN);

        clanRecord.setName(clan.getName());
        clanRecord.setGold(clan.getGold().get());

        clanRecord.store();
    }


    @Override
    public void update(org.scytec.interview.pojo.Clan clan) {
        DBCH.INSTANCE.getDslContext().update(Clan.CLAN)
                .set(Clan.CLAN.NAME, clan.getName())
                .set(Clan.CLAN.GOLD, clan.getGold().get())
                .where(Clan.CLAN.ID.eq(clan.getId()))
                .execute();
    }

    @Override
    public void update(Collection<org.scytec.interview.pojo.Clan> clans) {
        List<Query> queries = new ArrayList<>();
        for (org.scytec.interview.pojo.Clan clan : clans) {
            queries.add(DBCH.INSTANCE.getDslContext().update(Clan.CLAN)
                    .set(Clan.CLAN.NAME, clan.getName())
                    .set(Clan.CLAN.GOLD, clan.getGold().get())
                    .where(Clan.CLAN.ID.eq(clan.getId())));
        }

        DBCH.INSTANCE.getDslContext().batch(queries).execute();
    }

    @Override
    public void deleteById(Long id) {
        DBCH.INSTANCE.getDslContext().delete(Clan.CLAN)
                .where(Clan.CLAN.ID.eq(id))
                .execute();
    }

}
