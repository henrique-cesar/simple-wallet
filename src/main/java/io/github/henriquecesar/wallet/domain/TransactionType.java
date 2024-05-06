package io.github.henriquecesar.wallet.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TransactionType {
    RECARGA("recarga"),
    TRANSFERENCIA("transferencia"),
    COMPRA("compra"),
    CANCELAMENTO("cancelamento"),
    ESTORNO("estorno");

    private final String value;

    public String getValue() {
        return value;
    }

    TransactionType(String value) {
        this.value = value;
    }

    private static final Map<String, TransactionType> mapString = new HashMap<>();

    static {
        for (final TransactionType balanceOperation : EnumSet.allOf(TransactionType.class)) {
            mapString.put(balanceOperation.getValue().toLowerCase(), balanceOperation);
        }
    }

    public static TransactionType entryOf(String value) {
        return mapString.get(value.toLowerCase());
    }
}
