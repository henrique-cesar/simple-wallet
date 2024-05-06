package io.github.henriquecesar.wallet.transaction.visitor;

import io.github.henriquecesar.wallet.domain.Transaction;
import io.github.henriquecesar.wallet.domain.Visitor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class DefineDadosComplementaresVisitor implements Visitor<Transaction, Void>  {

    @Override
    public Void visit(Transaction element) {
        if (element.getDadosComplementares() == null) {
            element.setDadosComplementares(new HashMap<>());
        }

        element.getDadosComplementares().put("id_externo", UUID.randomUUID().toString());

        return null;

    }
}
