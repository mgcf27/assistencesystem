package com.miguel.assistencesystem.infrastructure.config;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.session")
public record SecuritySessionProperties(
		Duration timeout,
		Duration refreshThreshold) {}
