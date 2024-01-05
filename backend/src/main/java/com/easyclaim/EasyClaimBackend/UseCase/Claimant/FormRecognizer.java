package com.easyclaim.EasyClaimBackend.UseCase.Claimant;

import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult;

import java.io.File;
import java.util.HashMap;

public interface FormRecognizer {

    AnalyzeResult analyzeDocument(File file, String endpoint, String key);
    HashMap<String, String> getKeyValuePairs(AnalyzeResult result);

    HashMap<String, HashMap<String, String>> getTableContent(AnalyzeResult result);
}
