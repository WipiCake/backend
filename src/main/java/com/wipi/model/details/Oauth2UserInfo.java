package com.wipi.model.details;

public interface Oauth2UserInfo {
    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}
