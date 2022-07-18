package com.hrabit64.hrabit64s_spring_note.config.Security;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private final String oauthID;
    private final Map<String,Object> attributes;
    private final String nameAttributeKey;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey ,String oauthID){
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.oauthID = oauthID;
    }

    public static OAuthAttributes of(String registrationId,String userNameAttributeName,
                                     Map<String,Object> attributes){
        return ofGithub(userNameAttributeName,attributes);
    }



    private static OAuthAttributes ofGithub(String userNameAttributeName, Map<String,Object> attributes){
        return OAuthAttributes.builder()
                .oauthID(attributes.get(userNameAttributeName).toString())
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .build();
    }
}