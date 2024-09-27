package com.example.jsontransformation.JoltService;

import com.example.jsontransformation.Entity.TransformedData;
import com.example.jsontransformation.Entity.TransformedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransformedDataService {

    private final TransformedDataRepository transformedDataRepository;

    @Autowired
    public TransformedDataService(TransformedDataRepository transformedDataRepository) {
        this.transformedDataRepository = transformedDataRepository;
    }

    @Cacheable(value = "transformed_json")
    public TransformedData saveTransformedData(Object transformedJson) {
        TransformedData transformedData = new TransformedData();
        transformedData.setTransformedJson(transformedJson != null ? transformedJson.toString() : null);
        return transformedDataRepository.save(transformedData);
    }
}

