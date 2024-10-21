package com.thinkitdevit.querybatcher.service;

import com.thinkitdevit.querybatcher.model.ExportConfigBucket;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExportCsvService {

    private final ExportConfigBucket exportConfigBucket;

}
