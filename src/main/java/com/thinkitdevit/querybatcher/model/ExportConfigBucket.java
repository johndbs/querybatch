package com.thinkitdevit.querybatcher.model;

import java.util.HashMap;
import java.util.Map;

public class ExportConfigBucket {

    private Map<String, ExportConfig> exportConfigMap = new HashMap<>();


    public ExportConfig getExportConfig(String name) {
        return exportConfigMap.get(name);
    }

    public void addExportConfig(String name, ExportConfig exportConfig) {
        exportConfigMap.put(name, exportConfig);
    }

}
