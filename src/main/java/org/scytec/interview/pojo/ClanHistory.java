package org.scytec.interview.pojo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClanHistory {
    private long id;
    private LocalDateTime dateTime;
    private String action;

    private long clanId;
    private long userId;
    private long taskId;

    private int goldFrom;
    private int goldTo;
}
