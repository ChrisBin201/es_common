package com.chris.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfig {

//    @Bean
//    public AuditorAware<String> auditorProvider() {
//        String user = getUser();
//        return () -> Optional.ofNullable(Optional.ofNullable(user).orElse("UNKNOWN"));
//    }
//
//    private String getUser() {
////        if (DataUtils.notNull(SecurityContextHolder.getContext().getAuthentication())) {
////            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////            if (principal instanceof UserDetails) {
////                return ((UserDetails) principal).getUsername();
////            } else {
////                return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
////            }
////        }
//        return null;
//    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }

            // try catch is a workaround for ignore class cast exception when running tests

            try {
                UserDetailsInfo user = (UserDetailsInfo) authentication.getPrincipal();

                return Optional.of(user.getUsername());

            } catch (ClassCastException e) {
                return Optional.of("UNKNOWN");

            }
        };
    }

}
