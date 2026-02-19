package com.api.services.interfaces;

import com.api.models.Auth.LoginRequest;
import com.api.models.Auth.LoginResponse;

public interface IAuthService {
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse refreshToken(String refreshToken);
}
