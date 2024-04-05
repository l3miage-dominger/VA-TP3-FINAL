package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface    CandidateEvaluationGridRepository extends JpaRepository<CandidateEvaluationGridEntity,Long> {

    Set<CandidateEvaluationGridEntity> findAllByGradeIsLessThanEqual(double grade);
}
