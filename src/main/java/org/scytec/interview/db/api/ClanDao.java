package org.scytec.interview.db.api;

import org.scytec.interview.pojo.Clan;

import java.util.Collection;
import java.util.List;

public interface ClanDao {
    List<Clan> findAll();

    Clan findById(Long id);

    void insert(Clan clan);

    void update(Clan clan);

    void update(Collection<Clan> clans);

    void deleteById(Long id);
}
