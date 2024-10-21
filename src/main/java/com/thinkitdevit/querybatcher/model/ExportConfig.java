package com.thinkitdevit.querybatcher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExportConfig {

    private Export export;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Export{
        private String name;
        private Filename filename;
        private List<Column> columns;
        private List<Input> inputs;
        private List<Query> queries;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Filename {
        private String template;
        private List<Parameter> parameters;
        private String format;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Parameter {
        private String name;
        private Origin origin;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column {
        private String name;
        private int position;
        private String label;
        private Origin origin;
        private String format;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Origin {
        private String type;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Input {
        private String name;
        private String type;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Query {
        private String name;
        private String sql;
        private List<Parameter> parameters;
        private Output output;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Output {
        private String result;
        private String type;
    }

}
