package org.scytec.interview.pojo;

import lombok.Data;

@Data
public class HttpQuery {
    private long userId;
    private long clanId;
    private long taskId;
    private int gold;

    public static HttpQuery of(String query) {
        HttpQuery result = new HttpQuery();
        for (String param : query.split("&")) {
            String[] parts = param.split("=");
            if (2 == parts.length) {
                String key = parts[0];
                String value = parts[1];

                if ("gold".equalsIgnoreCase(key)) {
                    result.setGold(Integer.parseInt(value));
                }

                if ("clanid".equalsIgnoreCase(key)) {
                    result.setClanId(Long.parseLong(value));
                }

                if ("taskid".equalsIgnoreCase(key)) {
                    result.setTaskId(Long.parseLong(value));
                }

                if ("userid".equalsIgnoreCase(key)) {
                    result.setUserId(Long.parseLong(value));
                }
            }
        }

        return result;
    }
}
