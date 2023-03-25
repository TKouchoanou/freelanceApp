package service.query;

import domain.model.ZModelUtils;
import freelance.Application;
import freelance.domain.repository.UserRepository;
import freelance.service.command.utils.AuthProvider;
import freelance.service.query.UserQueryService;
import freelance.service.query.model.User;
import freelance.service.query.query.SearchUserQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.stream.LongStream;

@SpringBootTest(classes = Application.class)
@Mapper(componentModel = "spring")
@DirtiesContext
public class UserQueryServiceTest {
    @Autowired
    UserQueryService userQueryService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthProvider authProvider;

    @BeforeEach
    void setUp(){
        LongStream.range(1,11)
                .mapToObj(ZModelUtils::createUser)
                .forEach(user -> userRepository.save(user));
        authProvider.setCurrentAuth(ZModelUtils.createAdminAuth());
    }
    @Test
    public void getAllUsersTest() {
        Assertions.assertDoesNotThrow(userQueryService::getAllUsers);
        Assertions.assertEquals(10,userQueryService.getAllUsers().size());
    }

    @Test
    public void geUserByIdTest() {
        Long anyId= userRepository.findAll().findAny().get().getId().id();
        Assertions.assertDoesNotThrow(()->userQueryService.geUserById(anyId));
        User user=userQueryService.geUserById(anyId).get();
        Assertions.assertEquals(anyId,user.getId());
    }

    @Test
    public void searchUserTest() {
      Assertions.assertDoesNotThrow(()->userQueryService.searchUser(new SearchUserQuery()));
    }
    /*
    @Test
    public void test(){
        Rib ribs=ZModelUtils.createRibPersisted(1L);
        Rib rib =QueryToDomainMapper.map(freelance
                .service.query
                .model.Rib
                .builder()
                .bic(ribs.getBic().value())
                .iban(ribs.getIban().value())
                .cleRib(ribs.getCleRib().value())
                .username(ribs.getUsername())
                .build());
        Assertions.assertEquals(ribs.getCleRib(),rib.getCleRib());
    }*/
}
