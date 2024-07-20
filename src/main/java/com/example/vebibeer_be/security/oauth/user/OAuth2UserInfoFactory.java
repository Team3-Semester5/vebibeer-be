package com.example.vebibeer_be.security.oauth.user;

import java.util.Map;

import com.example.vebibeer_be.exception.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase("")) {
            return new GoogleOAuth2UserInfo(attributes);
        } 
        else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
