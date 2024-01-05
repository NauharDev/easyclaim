package com.easyclaim.EasyClaimBackend.Controller;

import com.easyclaim.EasyClaimBackend.ConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class test {

    @Value("${ocr.endpoint}")
    public String endpoint;

    @Value("${ocr.key}")
    public String key;

    @GetMapping("/test")
    public Map<String, String> getConfigs() {
        return Map.of("key", key,
                "endpoint", endpoint);
    }
}
