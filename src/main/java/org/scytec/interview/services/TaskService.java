package org.scytec.interview.services;


import org.scytec.interview.pojo.Action;
import org.scytec.interview.pojo.Clan;
import org.scytec.interview.pojo.ClanHistory;
import org.scytec.interview.pojo.Task;

import java.time.LocalDateTime;

public enum TaskService {
    INSTANCE;

    public void completeTask(long clanId, long taskId) {
        Task task = DbFacade.INSTANCE.getTask(taskId);

        // if (success)
        {
            try {
                DbFacade.INSTANCE.clanLock.lock(clanId);

                Clan clan = DbFacade.INSTANCE.getClan(clanId);
                int goldFrom = clan.getGold();

                clan.addGold(task.getReward());
                DbFacade.INSTANCE.updateClan(clan);


                DbFacade.INSTANCE.clanHistorySetForSave
                        .add(ClanHistory.builder()
                                .action(Action.COMPLETE_TASK.name())
                                .dateTime(LocalDateTime.now())
                                .clanId(clanId)
                                .taskId(taskId)
                                .goldFrom(goldFrom)
                                .goldTo(clan.getGold())
                                .build());

            } finally {
                DbFacade.INSTANCE.clanLock.unlock(clanId);
            }
        }
    }


}