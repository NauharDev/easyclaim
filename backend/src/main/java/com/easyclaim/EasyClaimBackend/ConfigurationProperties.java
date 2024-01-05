package com.easyclaim.EasyClaimBackend;

@org.springframework.boot.context.properties.ConfigurationProperties(prefix="ocr")
public record ConfigurationProperties(String endpoint, String key) {
}
