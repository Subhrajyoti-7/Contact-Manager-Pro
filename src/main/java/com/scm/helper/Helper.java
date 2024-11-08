package com.scm.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class Helper {

    // @Autowired
    // private UserService userService;

    // public String getEmailOfLoggedInUser(Principal principal) {
    // // if provider is SELF then we directly get the email
    // // if provider is Google or GitHub then we get the provider-id

    // if (!principal.getName().contains("@gmail.com")) {
    // // OAuth2
    // String providerId = principal.getName();
    // Optional<Users> user = userService.getEmailByProviderId(providerId);
    // if (user.isPresent()) {
    // return user.get().getUsername();
    // }
    // return null;
    // }
    // // SELF
    // return principal.getName();
    // }

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken token) {
            // OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            OAuth2User authUser = (OAuth2User) authentication.getPrincipal();
            String username = "";
            String clientId = token.getAuthorizedClientRegistrationId();

            if (clientId.equalsIgnoreCase("google")) {
                username = authUser.getAttribute("email");

            } else if (clientId.equalsIgnoreCase("github")) {
                username = (authUser.getAttribute("email") != null ? authUser.getAttribute("email")
                        : authUser.getAttribute("login") + "@gmail.com");

            } else if (clientId.equalsIgnoreCase("facebook")) {
                // To do

            }
            return username;
        } else {
            return authentication.getName();
        }
    }

    public static String getLinkForEmailVerification(String token, String baseUrl) {
        String link = baseUrl + "/auth/verify-email?token=" + token;
        return link;
    }
}
