package com.algoarena.dao.repo;

import com.algoarena.dao.entity.Submissions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubmissionsRepo extends CrudRepository<Submissions, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Submissions s SET s.submissionId = :submissionid WHERE s.token IN :tokens")
    int updateFieldByTokens(@Param("submissionid") UUID submissionId, @Param("tokens") List<String> tokens);


    @Query("SELECT s.statusId FROM Submissions s WHERE s.submissionId IN :submissionId")
    List<Integer> findStatusIdBySubmissionId(@Param("submissionId") UUID submissionId);
}
