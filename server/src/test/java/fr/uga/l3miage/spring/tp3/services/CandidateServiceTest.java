package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;


@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CandidateServiceTest {

    @Autowired
    private CandidateService candidateService;

    @MockBean
    private CandidateComponent candidateComponent;



    //J'ai essayé tout pour faire des tests sur l'exeption mais je crois qu'il n'y a plus a le faire puisque tous les niveaux
    //en dessous implementent déjà l'exeption, c'est ce qui m'a semblé le plus logique parce que vraiment je ne pouvais pas
    //tester une exeption sans l'autre mais l'autre est déjà testé dans le component

    @Test
    void testGetCandidateAverage() throws CandidateNotFoundException {

        //Test de la recuperation d'une moyenne

        // Mock CandidateComponent
        CandidateComponent candidateComponent = Mockito.mock(CandidateComponent.class);

        ExamEntity exam = ExamEntity
                .builder()
                .weight(1)
                .build();


        CandidateEvaluationGridEntity grid1 = CandidateEvaluationGridEntity
                .builder()
                .grade(12.00)
                .examEntity(exam)
                .build();
        CandidateEvaluationGridEntity grid2 = CandidateEvaluationGridEntity
                .builder()
                .grade(8.00)
                .examEntity(exam)
                .build();


        CandidateEntity candidateEntity = CandidateEntity.builder()
                .id(1L)
                .candidateEvaluationGridEntities(Set.of(grid1,grid2))
                .build();


        when(candidateComponent.getCandidatById(1L)).thenReturn(candidateEntity);

        CandidateService candidateService = new CandidateService(candidateComponent);


        double expectedAverage = (12.00 + 8.00) / 2;
        assertThat(candidateService.getCandidateAverage(1L)).isEqualTo(expectedAverage);

        verify(candidateComponent, times(1)).getCandidatById(1L);
    }

    @Test
    void testGetCandidateAverageWrongAverage() throws CandidateNotFoundException {

        //Test de la recuperation d'une moyenne, cette fois ci le test ne passe pas parce qu'on ne tien pas en compte le poids
        //de l'exam. Cela garanti que la fonction est bien faite parce qu'elle elle le tient en compte

        // Mock CandidateComponent
        CandidateComponent candidateComponent = Mockito.mock(CandidateComponent.class);

        ExamEntity exam1 = ExamEntity
                .builder()
                .weight(1)
                .build();

        ExamEntity exam2 = ExamEntity
                .builder()
                .weight(2)
                .build();


        CandidateEvaluationGridEntity grid1 = CandidateEvaluationGridEntity
                .builder()
                .grade(12.00)
                .examEntity(exam1)
                .build();
        CandidateEvaluationGridEntity grid2 = CandidateEvaluationGridEntity
                .builder()
                .grade(8.00)
                .examEntity(exam2)
                .build();


        CandidateEntity candidateEntity = CandidateEntity.builder()
                .id(1L)
                .candidateEvaluationGridEntities(Set.of(grid1,grid2))
                .build();


        when(candidateComponent.getCandidatById(1L)).thenReturn(candidateEntity);

        CandidateService candidateService = new CandidateService(candidateComponent);


        double expectedAverage = (12.00 + 8.00) / 2;
        assertThat(candidateService.getCandidateAverage(1L)).isEqualTo(expectedAverage);

        verify(candidateComponent, times(1)).getCandidatById(1L);
    }





}



