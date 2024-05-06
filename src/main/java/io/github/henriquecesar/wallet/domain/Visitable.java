package io.github.henriquecesar.wallet.domain;

public interface Visitable<T> {

    public void accept(final Visitor<T, ?> visitor);

}
