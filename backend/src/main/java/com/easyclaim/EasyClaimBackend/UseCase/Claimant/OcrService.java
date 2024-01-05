package com.easyclaim.EasyClaimBackend.UseCase.Claimant;

import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OcrService {

    private final FormRecognizer ocrModel;


    private File convertFile(MultipartFile file) {
        File newFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (OutputStream os = new FileOutputStream(newFile)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return newFile;
    }

    public HashMap<String, String> getKeyValuePairs(MultipartFile file, String endpoint, String key) {
        File newFile = convertFile(file);
        AnalyzeResult result = ocrModel.analyzeDocument(newFile, endpoint, key);
        return ocrModel.getKeyValuePairs(result);
    }

    public HashMap<String, HashMap<String, String>> getTables(MultipartFile file, String endpoint, String key) {
        File newFile = convertFile(file);
        AnalyzeResult result = ocrModel.analyzeDocument(newFile, endpoint, key);
        return ocrModel.getTableContent(result);
    }
}
