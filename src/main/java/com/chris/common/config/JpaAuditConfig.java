package com.chris.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        String user = getUser();
        return () -> Optional.ofNullable(Optional.ofNullable(user).orElse("UNKNOWN"));
    }

    private String getUser() {
//        if (DataUtils.notNull(SecurityContextHolder.getContext().getAuthentication())) {
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (principal instanceof UserDetails) {
//                return ((UserDetails) principal).getUsername();
//            } else {
//                return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            }
//        }
        return null;
    }

}
