package com.hib.hibenatemysql.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class ApplicationAuditAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        String userId = "";

        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            userId = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        return Optional.ofNullable(userId);
    }
}
