package io.github.henriquecesar.wallet.core.service;

import io.github.henriquecesar.wallet.core.UserInfo;

public interface AuthorizationService {

    void validate(UserInfo userInfo, String userId);

}
