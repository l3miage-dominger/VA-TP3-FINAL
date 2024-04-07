package fr.uga.l3miage.spring.tp3.repositories;
import static org.assertj.core.api.Assertions.assertThat;


import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.repositories.TestCenterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateRepositoryTest {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private TestCenterRepository testCenterRepository;

    @Test
    void testRequestFindAllByTestCenterEntityCode(){

        //given
        CandidateEntity ernesto = CandidateEntity
                .builder()
                .firstname("Ernesto")
                .lastname("Dominguez")
                .email("ernestodoinguez@outlook.com")
                .phoneNumber("06168429")
                .birthDate(LocalDate.of(2003,8,3))
                .hasExtraTime(false)
                .build();
        candidateRepository.save(ernesto);
        
        CandidateEntity rogelio = CandidateEntity
                .builder()
                .firstname("Rogelio")
                .lastname("Alvarez")
                .email("rogelioalvarez@outlook.com")
                .phoneNumber("06152636")
                .birthDate(LocalDate.of(2003,5,30))
                .hasExtraTime(true)
                .build();
        candidateRepository.save(rogelio);

        CandidateEntity max = CandidateEntity
                .builder()
                .firstname("Maximiliano")
                .lastname("Campos")
                .email("maxcampos@outlook.com")
                .phoneNumber("06152486")
                .birthDate(LocalDate.of(2003,7,2))
                .hasExtraTime(false)
                .build();
        candidateRepository.save(max);

        TestCenterEntity grenoble = TestCenterEntity
                .builder()
                .code(TestCenterCode.GRE)
                .university("UGA")
                .city("Grenoble")
                .candidateEntities(Set.of(ernesto))
                .build();
        testCenterRepository.save(grenoble);

        TestCenterEntity dijon = TestCenterEntity
                .builder()
                .code(TestCenterCode.DIJ)
                .university("UDI")
                .city("Dijon")
                .candidateEntities(Set.of(max, rogelio))
                .build();
        testCenterRepository.save(dijon);

        ernesto.setTestCenterEntity(grenoble);
        rogelio.setTestCenterEntity(dijon);
        max.setTestCenterEntity(dijon);

        candidateRepository.save(ernesto);
        candidateRepository.save(rogelio);
        candidateRepository.save(max);



        //when

        Set<CandidateEntity> candidateEntityResponses = candidateRepository.findAllByTestCenterEntityCode(TestCenterCode.DIJ);

        //then

        assertThat(candidateEntityResponses).hasSize(1);
    };



}
