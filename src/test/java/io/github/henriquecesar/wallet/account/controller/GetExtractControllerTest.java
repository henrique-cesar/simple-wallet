package io.github.henriquecesar.wallet.account.controller;

import br.com.fluentvalidator.context.ValidationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.henriquecesar.wallet.account.dto.ExtractOutput;
import io.github.henriquecesar.wallet.account.dto.TransactionInput;
import io.github.henriquecesar.wallet.account.validator.TransactionInputValidator;
import io.github.henriquecesar.wallet.core.constants.ApplicationConstants;
import io.github.henriquecesar.wallet.core.service.AccountService;
import io.github.henriquecesar.wallet.core.service.AuthorizationService;
import io.github.henriquecesar.wallet.domain.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetExtractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionInputValidator inputValidator;

    @MockBean
    private AuthorizationService authorizationService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String URI_GET_EXTRACT = "/api/users/{accountId}/balances/{balanceId}/extract";

    @Test
    @SneakyThrows
    @DisplayName("Deve consultar o status com sucesso.")
    void testSuccess() {
        String accountId = UUID.randomUUID().toString();
        String balanceId = UUID.randomUUID().toString();
        Extract extract = mockExtract(accountId, balanceId, TransactionType.RECARGA);

        Mockito.when(accountService.getExtract(accountId, balanceId)).thenReturn(extract);
        Mockito.when(inputValidator.validate(any(TransactionInput.class))).thenReturn(ValidationResult.ok());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(URI_GET_EXTRACT, accountId, balanceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ExtractOutput output = objectMapper.readValue(responseBody, ExtractOutput.class);

        assertNotNull(output);
        assertEquals(extract.getBalance().getAccount().getId(), output.getAccountId());
        assertEquals(extract.getBalance().getTotal(), output.getTotalBalance());
        assertEquals(extract.getTransactions().get(0).getId(), output.getTransactions().get(0).getId());
        assertEquals(extract.getTransactions().get(0).getType().getValue(), output.getTransactions().get(0).getType().getValue());
    }

    @SneakyThrows
    @ParameterizedTest
    @EnumSource(TransactionType.class)
    @DisplayName("Deve consultar o status com sucesso.")
    void testSuccess(TransactionType transactionType) {
        String accountId = UUID.randomUUID().toString();
        String balanceId = UUID.randomUUID().toString();
        Extract extract = mockExtract(accountId, balanceId, transactionType);

        Mockito.when(accountService.getExtract(accountId, balanceId, transactionType)).thenReturn(extract);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(URI_GET_EXTRACT.concat("?type={transactionType}"), accountId, balanceId, transactionType.getValue())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ExtractOutput output = objectMapper.readValue(responseBody, ExtractOutput.class);

        assertNotNull(output);
        assertEquals(extract.getBalance().getAccount().getId(), output.getAccountId());
        assertEquals(extract.getBalance().getTotal(), output.getTotalBalance());
        assertEquals(extract.getTransactions().get(0).getId(), output.getTransactions().get(0).getId());
        assertEquals(extract.getTransactions().get(0).getType().getValue(), output.getTransactions().get(0).getType().getValue());
    }

    private Extract mockExtract(String accountId, String balanceId, TransactionType transactionType) {
        Balance balance = new Balance(balanceId, new Account(), BigDecimal.TEN.setScale(ApplicationConstants.SCALE_MONEY, RoundingMode.DOWN));
        balance.getAccount().setId(accountId);

        Transaction transaction = mockTransaction(transactionType, new BigDecimal("1.99"));

        return new Extract(balance, List.of(transaction));
    }

    private Transaction mockTransaction(TransactionType transactionType, BigDecimal value) {
        Transaction transaction = new Transaction();
        transaction.setType(transactionType);
        transaction.setValue(value);

        return transaction;
    }



}