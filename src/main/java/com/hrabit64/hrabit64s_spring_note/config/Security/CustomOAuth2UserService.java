package com.hrabit64.hrabit64s_spring_note.config.Security;

import com.hrabit64.hrabit64s_spring_note.config.GlobalConfig;
import com.hrabit64.hrabit64s_spring_note.service.ConfigsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private final ConfigsService settingService;

    @Autowired
    private final GlobalConfig globalConfig;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationID = userRequest.getClientRegistration().getRegistrationId();
        if(!registrationID.equals("github"))
            throw new OAuth2AuthenticationException
                    (new OAuth2Error("invalid_registrationID", "you can only use github for registrationID ", ""));

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(registrationID,userNameAttributeName,oAuth2User.getAttributes());
        logger.debug("Owner {} login!",attributes.getOauthID());
        if(!attributes.getOauthID().equals(globalConfig.getAdminID()))
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_user", "your not this blog owner", ""));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_OWNER")),attributes.getAttributes(), attributes.getNameAttributeKey());
    }
}
