package io.github.henriquecesar.wallet.service.auth.client.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorizationClientConfiguration {

    @Bean
    public AuthorizationClientInterceptor authorizationClientInterceptor() {
        return new AuthorizationClientInterceptor();
    }

}
