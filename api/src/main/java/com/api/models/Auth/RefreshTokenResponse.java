package com.api.models.Auth;

public class RefreshTokenResponse extends LoginRequest {
     String refreshToken;

     public String getRefreshToken() {
          return refreshToken;
     }

     public void setRefreshToken(String refreshToken) {
          this.refreshToken = refreshToken;
     }
}
