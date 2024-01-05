package com.easyclaim.EasyClaimBackend.UseCase.Claimant;

import com.azure.ai.formrecognizer.documentanalysis.models.*;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient;
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClientBuilder;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AzureFormRecognizer implements FormRecognizer {

    @Override
    public AnalyzeResult analyzeDocument(File file, String endpoint, String key) {
        DocumentAnalysisClient client = new DocumentAnalysisClientBuilder()
                .credential(new AzureKeyCredential(key)).endpoint(endpoint).buildClient();
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return client.beginAnalyzeDocument("prebuilt-document", BinaryData.fromBytes(fileContent))
                    .getFinalResult();

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public HashMap<String, String> getKeyValuePairs(AnalyzeResult result) {
        HashMap<String, String> resultMap = new HashMap<>();
        result.getKeyValuePairs().forEach(kvPair -> {
            if (kvPair.getValue() != null) {
                resultMap.put(kvPair.getKey().getContent(), kvPair.getValue().getContent());
            } else {
                resultMap.put(kvPair.getKey().getContent(), null);

            }
        });
        return resultMap;
    }

    @Override
    public HashMap<String, HashMap<String, String>> getTableContent(AnalyzeResult result) {
        HashMap<String, HashMap<String, String>> resultMap = new HashMap<>(
                Map.of(
                        "Loan A", new HashMap<>(),
                        "Loan B", new HashMap<>(),
                        "Loan C", new HashMap<>()
                )
        );
        Map<Integer, String> colMapper = new HashMap<>(Map.of
                (
                1, "Loan A",
                2, "Loan B",
                3, "Loan C"
                )
        );
        Map<Integer, String> rowMapper = new HashMap<>(Map.of(
                1, "Loan number",
                2, "Date of loan approval",
                3, "Original amount of loan",
                4, "Amount of insurance applied for",
                5, "Type or purpose of loan",
                6, "Date of first payment",
                7, "Interest rate",
                8, "Amount of monthly payment",
                9, "Date of Last loan payment was made prior to date of death",
                10, "Balance on the date of death"

        ));
        List<DocumentTable> tables = result.getTables();
        DocumentTable table = tables.get(0);
        table.getCells().forEach(cell -> {
            if (cell.getColumnIndex() != 0 && cell.getRowIndex() != 0 && cell.getRowIndex() <= 10) {
                resultMap.get(colMapper.get(cell.getColumnIndex())).put(rowMapper.get(cell.getRowIndex()),
                        cell.getContent());
            }
        });

        return resultMap;
    }
}

