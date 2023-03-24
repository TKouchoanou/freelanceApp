package freelance.storage.memory.query;

import freelance.domain.models.objetValue.Profile;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.repository.UserRepository;
import freelance.service.query.UserQueryService;
import freelance.service.query.model.User;
import freelance.service.query.query.SearchUserQuery;
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

    User toQuery(freelance.domain.models.entity.User user){
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
