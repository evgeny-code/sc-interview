package org.scytec.interview.db.impl;

import org.jooq.Query;
import org.scytec.interview.db.api.ClanHistoryDao;
import org.scytec.interview.db.model.tables.ClanHistory;
import org.scytec.interview.db.model.tables.records.ClanHistoryRecord;
import org.scytec.interview.services.DBCH;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public enum ClanHistoryDaoImpl implements ClanHistoryDao {
    INSTANCE;

    @Override
    public org.scytec.interview.pojo.ClanHistory findById(Long id) {
        ClanHistoryRecord clanHistoryRecord = DBCH.INSTANCE.getDslContext().fetchOne(ClanHistory.CLAN_HISTORY, ClanHistory.CLAN_HISTORY.ID.eq(id));

        return org.scytec.interview.pojo.ClanHistory.builder()
                .id(clanHistoryRecord.getId())
                .dateTime(clanHistoryRecord.getDateTime())
                .action(clanHistoryRecord.getAction())
                .clanId(clanHistoryRecord.getClanId())
                .userId(clanHistoryRecord.getUserId())
                .taskId(clanHistoryRecord.getTaskId())
                .goldFrom(clanHistoryRecord.getGoldFrom())
                .goldTo(clanHistoryRecord.getGoldTo())
                .build();
    }

    @Override
    public List<org.scytec.interview.pojo.ClanHistory> findByClanId(Long clanId) {
        List<org.scytec.interview.pojo.ClanHistory> result = new ArrayList<>();
        for (ClanHistoryRecord clanHistoryRecord : DBCH.INSTANCE.getDslContext().selectFrom(ClanHistory.CLAN_HISTORY).where(ClanHistory.CLAN_HISTORY.CLAN_ID.eq(clanId)).fetch()) {
            result.add(org.scytec.interview.pojo.ClanHistory.builder()
                    .id(clanHistoryRecord.getId())
                    .dateTime(clanHistoryRecord.getDateTime())
                    .action(clanHistoryRecord.getAction())
                    .clanId(clanHistoryRecord.getClanId())
                    .userId(clanHistoryRecord.getUserId())
                    .taskId(clanHistoryRecord.getTaskId())
                    .goldFrom(clanHistoryRecord.getGoldFrom())
                    .goldTo(clanHistoryRecord.getGoldTo())
                    .build());
        }
        return result;
    }

    @Override
    public void insert(org.scytec.interview.pojo.ClanHistory clanHistory) {
        ClanHistoryRecord clanHistoryRecord = DBCH.INSTANCE.getDslContext().newRecord(ClanHistory.CLAN_HISTORY);

        clanHistoryRecord.setDateTime(LocalDateTime.now());
        clanHistoryRecord.setAction(clanHistory.getAction());
        clanHistoryRecord.setClanId(clanHistory.getClanId());
        clanHistoryRecord.setUserId(clanHistory.getUserId());
        clanHistoryRecord.setTaskId(clanHistory.getTaskId());
        clanHistoryRecord.setGoldFrom(clanHistory.getGoldFrom());
        clanHistoryRecord.setGoldTo(clanHistory.getGoldTo());

        clanHistoryRecord.store();
    }

    @Override
    public void insert(Collection<org.scytec.interview.pojo.ClanHistory> clanHistoryCollection) {
        List<Query> queries = new ArrayList<>();
        for (org.scytec.interview.pojo.ClanHistory clanHistory : clanHistoryCollection) {
            queries.add(DBCH.INSTANCE.getDslContext().insertInto(ClanHistory.CLAN_HISTORY,
                            ClanHistory.CLAN_HISTORY.DATE_TIME,
                            ClanHistory.CLAN_HISTORY.ACTION,
                            ClanHistory.CLAN_HISTORY.CLAN_ID,
                            ClanHistory.CLAN_HISTORY.USER_ID,
                            ClanHistory.CLAN_HISTORY.TASK_ID,
                            ClanHistory.CLAN_HISTORY.GOLD_FROM,
                            ClanHistory.CLAN_HISTORY.GOLD_TO)
                    .values(clanHistory.getDateTime(), clanHistory.getAction(), clanHistory.getClanId(),
                            clanHistory.getUserId(), clanHistory.getTaskId(), clanHistory.getGoldFrom(),
                            clanHistory.getGoldTo()));
        }

        DBCH.INSTANCE.getDslContext().batch(queries).execute();
    }
}
