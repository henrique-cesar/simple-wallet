package io.github.henriquecesar.wallet.domain;

public interface Visitor<T, U> {

    public U visit(final T element);

}
