package com.example.jsontransformation.Entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransformedDataRepository extends CrudRepository<TransformedData, Long> {

}

