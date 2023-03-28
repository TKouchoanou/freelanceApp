package service.query;

import domain.model.ZModelUtils;
import freelance.Application;
import freelance.domain.core.entity.Freelance;
import freelance.domain.core.objetValue.FreelanceId;
import freelance.domain.output.repository.CompanyRepository;
import freelance.domain.output.repository.FreelanceRepository;
import freelance.domain.output.repository.RibRepository;
import freelance.domain.output.repository.UserRepository;
import freelance.application.command.utils.AuthProvider;
import freelance.application.query.FreelanceQueryService;
import freelance.application.query.query.SearchFreelanceQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.stream.LongStream;

@SpringBootTest(classes = Application.class)
@DirtiesContext
public class FreelanceQueryServiceTest {
    @Autowired
    FreelanceQueryService freelanceQueryService;
    @Autowired
    FreelanceRepository freelanceRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RibRepository ribRepository;
    @Autowired
    AuthProvider authProvider;


    @BeforeEach
    void setUp() {
        LongStream.range(1, 11)
                .peek(l -> userRepository.save(ZModelUtils.createUser(l)))
                .peek(l -> ribRepository.save(ZModelUtils.createRibPersisted(l)))
                .mapToObj(ZModelUtils::createFreelanceWithPersistedRib)
                .forEach(freelance -> {
                    companyRepository.save(ZModelUtils.createCompany(freelance.getCompanyId().id(), "tk"));
                    freelanceRepository.save(freelance);

                });
        authProvider.setCurrentAuth(ZModelUtils.createAdminAuth());
    }

    @Test
    public void testGetAllFreelances() {
        Assertions.assertTrue(freelanceQueryService.getAllFreelances().size() > 0);
    }

    @Test
    public void testGetFreelanceById() {
        Freelance freelance = freelanceRepository.findById(new FreelanceId(5L)).get();
        Assertions.assertEquals(5L, freelanceQueryService.getFreelanceById(freelance.getId().value()).get().getId());
    }

    @Test
    public void testSearchFreelanceByRib() {
        Assertions.assertEquals(1, freelanceQueryService.searchFreelanceByRib(2L).toList().size());
    }

    @Test
    public void testSearchFreelanceByCompany() {
        Assertions.assertTrue(freelanceQueryService.searchFreelanceByCompany(1L).toList().size() > 0);
    }

    @Test
    public void searchFreelance() {
        Assertions.assertDoesNotThrow(() -> freelanceQueryService.searchFreelance(new SearchFreelanceQuery()));
    }
}
