package com.retrospective.domain;

import lombok.Data;
import lombok.Getter;
import org.springframework.cache.annotation.Cacheable;

import java.util.HashMap;
import java.util.Map;

@Cacheable
@Data
public class RetroStore {

    @Getter
    private static Map<String, Retrospective> retrospectives = new HashMap<>();


    public static Retrospective getRetrospective(String retroId) {
        return retrospectives.get(retroId);
    }

}
