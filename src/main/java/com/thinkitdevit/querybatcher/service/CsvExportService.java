package com.thinkitdevit.querybatcher.service;

import com.thinkitdevit.querybatcher.model.ExportConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CsvExportService {


    private String outputDir = "./";

    public void writeResultCsv(Map<String, Map<String, Long>> result) throws IOException {
//        // write result to csv
//        try(Writer writer = new FileWriter("result.csv")) {
//
//            CSVWriter csvWriter = new CSVWriter(writer);
//
//
//
//            writer.write("Query,Count\n");
//            for (Map.Entry<String, Long> entry : result.entrySet()) {
//                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
//            }
//        }
//
    }

    public void writeResultCsv(ExportConfig config, Map<String, Object> queriesResults, Map<String, Object> inputs) {

        // TODO implement the method to format the file name
//        String outputFile = config.getExport().getFilename().getTemplate();
//
//        config.getExport().getFilename().getParameters().forEach(parameter -> {
//            outputFile = outputFile.replace("{" + parameter.getName() + "}", inputs.get(parameter.getOrigin().getName()).toString());
//        });
//

        String outputFile = outputDir + "out.csv";

        /*String outputFile = outputDir + config.getExport().getFilename().getTemplate();

        for(ExportConfig.Parameter parameter :config.getExport().getFilename().getParameters()){
            String replacement = null;
            ExportConfig.Origin origin = parameter.getOrigin();
            if("inputs".equals(origin.getType())){
                replacement = inputs.get(origin.getName()).toString();
            }else{
                throw new RuntimeException("Unsupported origin type: " + origin.getType());
            }
            outputFile= outputFile.replace("<" + parameter.getName() + ">", replacement);

        }*/

        // opencsv writer


        try (Writer writer = new FileWriter(outputFile)) {

            // write the headers
            writer.write(config.getExport().getColumns().stream()
                    .sorted((column1, column2) -> column1.getPosition() - column2.getPosition())
                    .map(ExportConfig.Column::getLabel)
                    .collect(Collectors.joining(";")) + "\n");


            // write the data

            String dataLine = config.getExport().getColumns().stream()
                    .sorted((column1, column2) -> column1.getPosition() - column2.getPosition())
                    .map(col -> {
                        ExportConfig.Origin origin = col.getOrigin();
                        if (origin.getType().equals("queries")) {
                            return queriesResults.get(origin.getName()).toString();
                        } else if (origin.getType().equals("inputs")) {
                            return inputs.get(origin.getName()).toString();
                        } else {
                            throw new RuntimeException("Unsupported origin type: " + origin.getType());
                        }
                    })
                    .collect(Collectors.joining(";"))
                    + "\n";

            writer.write(dataLine);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
