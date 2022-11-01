package org.scytec.interview.services;

import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public enum JsonMapper {
    INSTANCE;

    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public String toJson(Object o) {
        return mapper.writeValueAsString(o);
    }

    public <T> T fromJson(String json, Class<T> type) throws IOException {
        return mapper.readValue(json, type);
    }
}
