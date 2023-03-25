package service.query;

import domain.model.ZModelUtils;
import freelance.Application;
import freelance.domain.repository.RibRepository;
import freelance.service.command.utils.AuthProvider;
import freelance.service.query.RibQueryService;
import freelance.service.query.query.SearchRibQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.stream.LongStream;
@SpringBootTest(classes = {Application.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RibQueryServiceTest{

    @Autowired
    RibQueryService ribQueryService;
    @Autowired
    RibRepository ribRepository;
    @Autowired
    AuthProvider authProvider;

    @BeforeEach
    void setUp(){
        System.out.println("\n setUp is playing ");
        LongStream.range(1,11)
                .mapToObj(ZModelUtils::createRibPersisted)
                .forEach(rib -> ribRepository.save(rib));
        authProvider.setCurrentAuth(ZModelUtils.createAdminAuth());
    }
    @Test
    public void testSearchByIban() {
        System.out.println("\n1 testSearchByIban ");
        String iban=ribRepository.findAll().findAny().get().getIban().value();
        Assertions.assertDoesNotThrow(()->ribQueryService.searchByIban(iban));
        Assertions.assertEquals(10,ribQueryService.searchByIban(iban).toList().size());
    }

    @Test
    public void testSearchByUserName() {
        System.out.println("\n2 testSearchByUserName ");
        String username=ribRepository.findAll().findAny().get().getUsername();
        Assertions.assertDoesNotThrow(()->ribQueryService.searchByUserName(username));
        Assertions.assertEquals(10,ribQueryService.searchByUserName(username).toList().size());
    }
    @Test
   
    public void testSearch() {
        System.out.println("\n3 testSearch ");
        Assertions.assertDoesNotThrow(()->ribQueryService.search(new SearchRibQuery()));
    }
}
