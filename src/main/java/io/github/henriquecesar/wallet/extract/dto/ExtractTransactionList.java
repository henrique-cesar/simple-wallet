package io.github.henriquecesar.wallet.extract.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.henriquecesar.wallet.transaction.dto.TransactionOutput;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExtractTransactionList {
    private int pageNumber;
    private int totalPages;
    private List<TransactionOutput> transactions;

    public ExtractTransactionList(Page<TransactionOutput> pageable) {
        if (pageable == null) return;
        this.transactions = pageable.getContent();
        this.pageNumber = pageable.getNumber();
        this.totalPages = pageable.getTotalPages();
    }
}