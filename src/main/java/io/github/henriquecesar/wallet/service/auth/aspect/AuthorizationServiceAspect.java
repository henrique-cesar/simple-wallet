package io.github.henriquecesar.wallet.service.auth.aspect;

import io.github.henriquecesar.wallet.core.UserInfo;
import io.github.henriquecesar.wallet.core.service.AuthorizationService;
import io.github.henriquecesar.wallet.service.auth.client.AuthorizationClient;
import io.github.henriquecesar.wallet.service.auth.dto.AuthorizationResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AuthorizationServiceAspect {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private AuthorizationClient authorizationClient;

    @Around("@annotation(NeedsAuthorization)")
    public Object onNeedsAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
        AuthorizationResponse authorization = authorizationClient.getAuthorization();

        Object[] args = joinPoint.getArgs();

        UserInfo userInfo = Arrays.stream(args)
                .filter(e -> e instanceof UserInfo)
                .map(e -> (UserInfo) e)
                .findFirst()
                .orElse(null);

        if (authorization != null && userInfo != null) {
            userInfo.setId(authorization.getUserId());
            userInfo.setUserEmail(authorization.getUserEmail());
            userInfo.setUserName(authorization.getUserName());
        } else {
            userInfo = null;
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof UserInfo) {
                args[i] = userInfo;
                break;
            }
        }

        return joinPoint.proceed(args);
    }



}
