package com.example.jsontransformation.Service;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
public class JoltService {
    private final Chainr chainr;

/*    public JoltService() throws IOException {
        URL joltSpecUrl = getClass().getResource("/jolt-spec.json");
        List<Object> specList = JsonUtils.jsonToList(joltSpecUrl.openStream());
        chainr = Chainr.fromSpec(specList);
    }*/

    public JoltService(@Value("${jolt.specification}") String joltSpecification) throws IOException {
        List<Object> specList = JsonUtils.jsonToList(joltSpecification);
        chainr = Chainr.fromSpec(specList);
    }

    public String transform(String inputJson) {
        Object inputObject = JsonUtils.jsonToObject(inputJson);
        Object transformedOutput = chainr.transform(inputObject);
        return JsonUtils.toJsonString(transformedOutput);
    }
}