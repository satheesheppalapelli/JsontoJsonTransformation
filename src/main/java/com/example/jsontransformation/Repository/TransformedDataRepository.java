package com.example.jsontransformation.Repository;

import com.example.jsontransformation.Entity.TransformedData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransformedDataRepository extends CrudRepository<TransformedData, Long> {

}

