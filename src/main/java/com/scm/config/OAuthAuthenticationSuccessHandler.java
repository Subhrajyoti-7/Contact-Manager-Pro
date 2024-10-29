package com.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm.entity.Providers;
import com.scm.entity.Users;
import com.scm.helper.AppConstants;
import com.scm.repo.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepo userRepo;

    // To show log messages in the console
    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // logger.info("OAuthAuthenticationSuccessHandler");

        // DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        // logger.info(user.getName());
        // user.getAttributes().forEach((key, value) -> {
        // logger.info("{}: {}", key, value);
        // });
        // logger.info(user.getAuthorities().toString());

        // Get the user details from the OAuth provider
        OAuth2AuthenticationToken oAuthToken = (OAuth2AuthenticationToken) authentication;
        String clientId = oAuthToken.getAuthorizedClientRegistrationId();
        // logger.info(clientId);

        DefaultOAuth2User authUser = (DefaultOAuth2User) authentication.getPrincipal();
        Users user = new Users();
        user.setUid(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode("password" + Math.random() + passwordEncoder.encode("s@#.&$%")));

        if (clientId.equalsIgnoreCase("google")) {
            // For Google authentication
            user.setName(authUser.getAttribute("name"));
            user.setEmail(authUser.getAttribute("email").toString());
            user.setProvider(Providers.GOOGLE);
            user.setProviderId(authUser.getName());
            user.setProfilePic(authUser.getAttribute("picture").toString());
            user.setAbout("This profile is created using Google");

        } else if (clientId.equalsIgnoreCase("github")) {
            // For Github authentication
            user.setName(authUser.getAttribute("name"));
            user.setEmail(authUser.getAttribute("email") != null ? authUser.getAttribute("email").toString()
                    : authUser.getAttribute("login") + "@gmail.com");
            user.setProvider(Providers.GITHUB);
            user.setProviderId(authUser.getName());
            user.setProfilePic(authUser.getAttribute("avatar_url").toString());
            user.setAbout("This profile is created using GitHub");

        } else if (clientId.equalsIgnoreCase("facebook")) {
            // For Facebook authentication

        } else {
            // For any other authentication provider
            logger.error("Unknown authentication provider");
        }

        // Save the user details in the database
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            // User with the same email already exists
            response.sendRedirect(request.getContextPath() + "/user/dashboard");
            return;
        }
        userRepo.save(user);

        // Redirect to the home page after successful login
        // response.sendRedirect(request.getContextPath() + "/user/dashboard");
        // OR
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/dashboard");
    }

}
