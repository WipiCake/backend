package com.wipi.service.front;

import com.wipi.model.details.CustomOauth2UserDetails;
import com.wipi.model.details.GoogleUserDetails;
import com.wipi.model.details.Oauth2UserInfo;
import com.wipi.model.entity.UserEntity;
import com.wipi.service.persist.UserService;
import com.wipi.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserService userService;
    private final String defaultRole = "ROLE_ADMIN";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();
        Oauth2UserInfo oauth2UserInfo = getOauth2UserInfo(provider, oAuth2User.getAttributes());

        String providerId = oauth2UserInfo.getProviderId();
        String email = oauth2UserInfo.getEmail();
        String loginId = provider + "_" + providerId;
        String name = oauth2UserInfo.getName();

        UserEntity userEntity = userService.findByEmail(email)
                .orElseGet(() -> createAndSaveUser(name, email, provider, providerId));

        return new CustomOauth2UserDetails(userEntity, oAuth2User.getAttributes());
    }

    private Oauth2UserInfo getOauth2UserInfo(String provider, Map<String, Object> attributes) {
        if ("google".equals(provider)) {
            log.info("구글 로그인");
            return new GoogleUserDetails(attributes);
        }



        throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 provider: " + provider);
    }

    private UserEntity createAndSaveUser(String name, String email, String provider, String providerId) {
        UserEntity newUser = new UserEntity();
        newUser.setUserId(ServiceUtil.createUserId());
        newUser.setName(name);
        newUser.setRole(defaultRole);
        newUser.setEmail(email);
        newUser.setPassword(null);
        newUser.setProvider(provider);
        newUser.setProviderId(providerId);
        return userService.saveOauthUser(newUser);
    }

}
