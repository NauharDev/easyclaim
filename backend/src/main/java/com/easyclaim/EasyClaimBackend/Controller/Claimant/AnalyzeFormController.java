package com.easyclaim.EasyClaimBackend.Controller.Claimant;

import com.easyclaim.EasyClaimBackend.UseCase.Claimant.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/claimant")
@RequiredArgsConstructor
public class AnalyzeFormController {

    @Value("${ocr.endpoint}")
    public String endpoint;

    @Value("${ocr.key}")
    public String key;

    private final OcrService service;

    @PostMapping("/key-value")
    public HashMap<String, String> getKeyValuePairs(@RequestParam(value="file") MultipartFile file) {
        return service.getKeyValuePairs(file, endpoint, key);
    }

    @PostMapping("/table")
    public HashMap<String, HashMap<String, String>> getTables(@RequestParam(value="file") MultipartFile file) {
        return service.getTables(file, endpoint, key);
    }


}
