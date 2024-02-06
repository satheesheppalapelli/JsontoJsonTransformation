package com.example.jsontransformation.Entity;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "transformed_data")
public class TransformedData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "transformed_json")
    private String transformedJson;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransformedJson() {
        return transformedJson;
    }

    public void setTransformedJson(String transformedJson) {
        this.transformedJson = transformedJson;
    }

    // Getters and setters
}

