package com.thinkitdevit.querybatcher.rest;

import com.thinkitdevit.querybatcher.model.ExportConfig;
import com.thinkitdevit.querybatcher.model.ExportConfigBucket;
import com.thinkitdevit.querybatcher.service.CsvExportService;
import com.thinkitdevit.querybatcher.service.NativeQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/query")
@RequiredArgsConstructor
@Slf4j
public class QueryController {

    private final ExportConfigBucket exportConfigBucket;
    private final NativeQueryService nativeQueryService;
    private final CsvExportService csvExportService;


    @PostMapping("/process")
    public ResponseEntity<String> processQueries(
            @RequestParam(value="startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") LocalDateTime startDate,
            @RequestParam(value="endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") LocalDateTime endDate
    ) {





        // If the startDate is not provided, the default start date is the current date at 00:00:00
        if(startDate ==null){
            startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        // if the endDate is not provided, the default end date tomorrow at 00:00:00
        if(endDate ==null){
            endDate = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }


        // Get the export configurations
        ExportConfig config = exportConfigBucket.getExportConfig("travels");


        Map<String, Object> inputs = new HashMap<>();
        inputs.put("startdate", startDate);
        inputs.put("enddate", endDate);

        // Process the queries
        Map<String, Object> queriesResults = nativeQueryService.executeQueries(config, inputs);



        // Write the result to a CSV file
        try {
            csvExportService.writeResultCsv(config, queriesResults, inputs);
        } catch (Exception e) {
            log.error("Error writing to CSV file", e);
            return ResponseEntity.internalServerError().body("Error writing to CSV file");
        }


        return ResponseEntity.ok("Queries processed successfully");
    }

}
