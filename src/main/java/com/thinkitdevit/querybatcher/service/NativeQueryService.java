package com.thinkitdevit.querybatcher.service;

import com.thinkitdevit.querybatcher.model.ExportConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Persistent;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NativeQueryService {

   // @PersistentContext
    private final EntityManager entityManager;


    public Long executeNativeQuery(String query) {
        Query nativeRequest = entityManager.createNativeQuery(query);
        return ((Number)nativeRequest.getSingleResult()).longValue();
    }

    public Map<String, Object> executeQueries(ExportConfig config, Map<String, Object> inputs) {
        Map<String, Object> results = new java.util.HashMap<>();

        config.getExport().getQueries().forEach(query -> {
            excuteQuery(inputs, query, results);
        });

        return results;
    }

    private void excuteQuery(Map<String, Object> inputs, ExportConfig.Query query, Map<String, Object> results) {
        Query nativeRequest = entityManager.createNativeQuery(query.getSql());
        query.getParameters().forEach(parameter -> {
            nativeRequest.setParameter(parameter.getName(), inputs.get(parameter.getOrigin().getName()));
        });
        if("single".equals(query.getOutput().getResult())){
            results.put(query.getName(), nativeRequest.getSingleResult());
        }else{
            throw new RuntimeException("Unsupported output type: " + query.getOutput().getType());
        }


    }

    /**
     *
     * @param query
     * @return
     */




}
