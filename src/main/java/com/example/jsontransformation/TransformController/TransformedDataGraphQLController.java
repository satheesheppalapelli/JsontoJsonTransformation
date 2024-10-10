package com.example.jsontransformation.TransformController;

import com.example.jsontransformation.Entity.TransformedData;
import com.example.jsontransformation.Repository.TransformedDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class TransformedDataGraphQLController {

    @Autowired
    private TransformedDataRepository transformedDataRepository;

    @QueryMapping
    public Iterable<TransformedData> getAllTransformedData() {
        return transformedDataRepository.findAll();
    }

    @QueryMapping
    public Optional<TransformedData> getTransformedDataById(@Argument Long id) {
        return transformedDataRepository.findById(id);
    }
}
