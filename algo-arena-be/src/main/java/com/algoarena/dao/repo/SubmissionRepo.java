package com.algoarena.dao.repo;

import com.algoarena.dao.entity.Submission;
import com.algoarena.usersubmissions.SubmissionsDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubmissionRepo extends CrudRepository<Submission, UUID> {

    @Query("SELECT s.id FROM Submission s WHERE s.status in :status")
    List<UUID> findIdByStatus(@Param("status") String Status, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Submission s SET s.status = :status WHERE id = :id")
    int updateStatus(@Param("id") UUID submissionId, @Param("status") String status);

    List<Submission> findTop10ByProblemIdOrderByCreatedAtDesc(UUID problemId);

    @Query("SELECT s.status FROM Submission s WHERE s.id = :id")
    String findStatusById(@Param("id") UUID id);

    List<Submission> findByUsername(String username);

    @Query("SELECT new com.algoarena.usersubmissions.SubmissionsDTO(s.problemId, p.name, s.status, s.createdAt, s.code) FROM Submission s INNER JOIN s.problem p WHERE s.problemId = p.id AND s.username = :username ORDER BY s.createdAt DESC")
    List<SubmissionsDTO> findSubmissionsDTOByUsername(@Param("username") String username);

    @Query("SELECT distinct (s.problemId) FROM Submission s WHERE s.username = :username")
    List<UUID> findProblemIdByUsername(String username);
}
