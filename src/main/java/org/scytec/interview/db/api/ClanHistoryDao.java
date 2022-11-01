package org.scytec.interview.db.api;

import org.scytec.interview.pojo.ClanHistory;

import java.util.Collection;
import java.util.List;

public interface ClanHistoryDao {
    ClanHistory findById(Long id);

    List<ClanHistory> findByClanId(Long clanId);

    void insert(ClanHistory clanHistory);

    void insert(Collection<ClanHistory> clanHistoryCollection);
}
