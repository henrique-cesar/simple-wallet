package io.github.henriquecesar.wallet.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CanalOrigem {
    API("API"),
    SQS("SQS"),
    KAFKA("KAFKA");

    private final String value;

    public String getValue() {
        return value;
    }

    CanalOrigem(String value) {
        this.value = value;
    }

    private static final Map<String, CanalOrigem> mapString = new HashMap<>();

    static {
        for (final CanalOrigem canalOrigem : EnumSet.allOf(CanalOrigem.class)) {
            mapString.put(canalOrigem.getValue().toLowerCase(), canalOrigem);
        }
    }

    public static CanalOrigem entryOf(String value) {
        return mapString.get(value.toLowerCase());
    }
}
