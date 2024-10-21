package com.thinkitdevit.querybatcher.config;

import com.thinkitdevit.querybatcher.model.ExportConfig;
import com.thinkitdevit.querybatcher.model.ExportConfigBucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Configuration
public class ExportConfigLoader {

    @Bean
    public ExportConfigBucket exportConfigBucket(ResourcePatternResolver resourcePatternResolver) throws IOException {

        ExportConfigBucket bucket = new ExportConfigBucket();

        Resource[] resources = resourcePatternResolver.getResources("classpath:csvexport/*.yml");

        Yaml yaml = new Yaml(new Constructor(ExportConfig.class, new LoaderOptions()));

        Arrays.stream(resources).forEach(resource -> {
            log.info("Loading export config from {}", resource.getFilename());

            try {
                ExportConfig config = yaml.load(resource.getInputStream());
                bucket.addExportConfig(config.getExport().getName(), config);
            } catch (IOException e) {
                log.error("Error while reading export config {}", resource.getFilename(),e);
                throw new RuntimeException(e);
            }

        });

        return bucket;
    }

}
