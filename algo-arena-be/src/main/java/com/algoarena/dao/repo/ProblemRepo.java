package com.algoarena.dao.repo;

import com.algoarena.dao.entity.Problem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProblemRepo extends CrudRepository<Problem, UUID> {
    @Query("SELECT p.id FROM Problem p WHERE p.name = :name")
    UUID findIdByName(@Param("name") String name);

    @Query("SELECT p.name FROM Problem p WHERE p.id = :id")
    String findNameById(@Param("id") UUID id);
}
