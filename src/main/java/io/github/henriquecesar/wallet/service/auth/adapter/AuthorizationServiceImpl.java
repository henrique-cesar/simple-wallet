package io.github.henriquecesar.wallet.service.auth.adapter;

import io.github.henriquecesar.wallet.core.UserInfo;
import io.github.henriquecesar.wallet.core.exception.UnauthorizedException;
import io.github.henriquecesar.wallet.core.service.AuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Override
    public void validate(UserInfo userInfo, String userId) {
        if (userInfo == null || userId == null || !userId.equals(userInfo.getId())) {
            throw new UnauthorizedException();
        }
    }

}
