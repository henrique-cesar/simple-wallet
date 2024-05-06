package io.github.henriquecesar.wallet.service.auth.client;

import io.github.henriquecesar.wallet.service.auth.client.configuration.AuthorizationClientConfiguration;
import io.github.henriquecesar.wallet.service.auth.dto.AuthorizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "authorizationClient",
        url = "${authorization.client.base-url}",
        configuration = AuthorizationClientConfiguration.class
)
public interface AuthorizationClient {

    @GetMapping("/authorization")
    AuthorizationResponse getAuthorization();

}
