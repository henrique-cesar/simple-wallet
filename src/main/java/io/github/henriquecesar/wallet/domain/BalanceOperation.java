package io.github.henriquecesar.wallet.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum BalanceOperation {
    CREDIT("credit"),
    DEBIT("debit");

    private final String value;

    public String getValue() {
        return value;
    }

    BalanceOperation(String value) {
        this.value = value;
    }

    private static final Map<String, BalanceOperation> mapString = new HashMap<>();

    static {
        for (final BalanceOperation balanceOperation : EnumSet.allOf(BalanceOperation.class)) {
            mapString.put(balanceOperation.getValue().toLowerCase(), balanceOperation);
        }
    }

    public static BalanceOperation entryOf(String value) {
        return mapString.get(value.toLowerCase());
    }

}
