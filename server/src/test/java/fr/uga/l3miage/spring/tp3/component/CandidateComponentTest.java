package fr.uga.l3miage.spring.tp3.component;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import org.h2.engine.Setting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateComponentTest {

    @Autowired
    private CandidateComponent candidateComponent;
    @MockBean
    private CandidateEvaluationGridEntity candidateEvaluationGridEntity;
    @MockBean
    private CandidateRepository candidateRepository;



    //Debut des tests de getCandidateById()
    @Test
    void getCandidateByIdNotFound(){
        //given
        when(candidateRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when-then
        assertThrows(CandidateNotFoundException.class,()->candidateComponent.getCandidatById(1L));
    }

    @Test
    void getCandidateByIdFound(){
        //Test qui verfifie qu'on recoit le Candidate associé au ID qu'on a demandé

        //given
        CandidateEntity ernesto = CandidateEntity
                .builder()
                .id(1L)
                .build();
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(ernesto));
        //when - then
        assertDoesNotThrow(()->candidateComponent.getCandidatById(1L));

    }

    //Fin des tests de getCandidateById()

    //Debut des tests de getAllEliminatedCandidate()
    @Test
    void getAllEliminatedCandidatesVoid(){
        //On veut verifier que losrqu'on n'a pas d'eliminated candidates la fontion n'envoie rien

        //given
        CandidateEvaluationGridEntity grid1 = CandidateEvaluationGridEntity
                .builder()
                .grade(10.00)
                .build();

        CandidateEvaluationGridEntity grid2 = CandidateEvaluationGridEntity
                .builder()
                .grade(10.00)
                .build();


        CandidateEntity ernesto = CandidateEntity
                .builder()
                .id(1L)
                .candidateEvaluationGridEntities(Set.of(grid1,grid2))
                .build();

        when(candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(10.00)).thenReturn(Set.of());
        Set<CandidateEntity> results = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(10.00);

        //when -then
        assertDoesNotThrow(()->candidateComponent.getAllEliminatedCandidate());
        assertThat(results).hasSize(0);
        assertThat(results).isEmpty();

    }

    @Test
    void getAllEliminatedCandidatesOneEliminated(){
        //On veut verifier que losrqu'on n'a qu'un eliminated Candidate la liste comporte un seul element et c'est bien l'eliminated candidate

        //given
        CandidateEvaluationGridEntity grid1 = CandidateEvaluationGridEntity
                .builder()
                .grade(9.00)
                .build();

        CandidateEvaluationGridEntity grid2 = CandidateEvaluationGridEntity
                .builder()
                .grade(9.00)
                .build();


        CandidateEntity ernesto = CandidateEntity
                .builder()
                .id(1L)
                .candidateEvaluationGridEntities(Set.of(grid1,grid2))
                .build();

        CandidateEvaluationGridEntity grid3 = CandidateEvaluationGridEntity
                .builder()
                .grade(11.00)
                .build();

        CandidateEvaluationGridEntity grid4 = CandidateEvaluationGridEntity
                .builder()
                .grade(11.00)
                .build();


        CandidateEntity leonardo = CandidateEntity
                .builder()
                .id(2L)
                .candidateEvaluationGridEntities(Set.of(grid3,grid4))
                .build();

        when(candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(10.00)).thenReturn(Set.of(ernesto));
        Set<CandidateEntity> results = candidateRepository.findAllByCandidateEvaluationGridEntitiesGradeLessThan(10.00);

        //when -then
        assertDoesNotThrow(()->candidateComponent.getAllEliminatedCandidate());
        assertThat(results).hasSize(1);
        assertThat(results).containsOnly(ernesto);

    }

    //Fin des tests de getAllEliminatedCandidate()


}
