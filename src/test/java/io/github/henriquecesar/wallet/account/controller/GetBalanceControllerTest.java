package io.github.henriquecesar.wallet.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.henriquecesar.wallet.balance.dto.BalanceOutput;
import io.github.henriquecesar.wallet.core.service.BalanceService;
import io.github.henriquecesar.wallet.core.service.AuthorizationService;
import io.github.henriquecesar.wallet.domain.Account;
import io.github.henriquecesar.wallet.domain.Balance;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetBalanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalanceService accountService;

    @MockBean
    private AuthorizationService authorizationService;

    private final String URI_GET_BALANCE = "/api/accounts/{accountId}/balances/{balanceId}";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @SneakyThrows
    @DisplayName("Deve consultar o saldo com sucesso.")
    void testSuccess() {
        String accountId = UUID.randomUUID().toString();
        String balanceId = UUID.randomUUID().toString();
        Balance balance = mockBalance(accountId, balanceId, new BigDecimal("5.00"));

        Mockito.when(accountService.getBy(accountId, balanceId)).thenReturn(balance);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(URI_GET_BALANCE, accountId, balanceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        // Verifica o conteúdo da resposta
        String responseBody = result.getResponse().getContentAsString();
        BalanceOutput output = objectMapper.readValue(responseBody, BalanceOutput.class);

        assertNotNull(output);
        assertEquals(balance.getId(), output.getBalanceId());
        assertEquals(balance.getAccount().getId(), output.getUserId());
        assertEquals(balance.getTotal(), output.getAmount());
    }

    private Balance mockBalance(String accountId, String balanceId, BigDecimal value) {
        Balance balance = new Balance(balanceId, new Account(), value);
        balance.getAccount().setId(accountId);
        return balance;
    }

}