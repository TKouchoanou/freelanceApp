package freelance.infrastructure.queryService;

import freelance.domain.core.objetValue.Profile;
import freelance.domain.core.objetValue.UserId;
import freelance.domain.output.repository.UserRepository;
import freelance.application.query.UserQueryService;
import freelance.application.query.model.User;
import freelance.application.query.query.SearchUserQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
public class UserQueryServiceImpl implements UserQueryService {
    UserRepository userRepository;
    UserQueryServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository
                .findAll()
                .map(this::toQuery)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> geUserById(Long id) {
        return userRepository
                .findById(new UserId(id))
                .map(ZMapUtils::toQuery);
    }

    @Override
    public Stream<User> searchUser(SearchUserQuery query) {
        return Stream.empty();
    }

    User toQuery(freelance.domain.core.entity.User user){
     return    User.builder()
                .email(user.getEmail().value())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .isActive(user.isActive())
                .id(user.getId().id())
                .profiles(user.getProfiles().stream().map(Profile::name).collect(Collectors.toSet()))
                .build();
    }

}
