package freelance.storage.memory;

import freelance.domain.models.entity.User;
import freelance.domain.models.objetValue.Email;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
@Repository
@SuppressWarnings("unchecked")
public class UserRepositoryImpl implements UserRepository {
    Map<UserId,User> stores= new HashMap<>();
    @Override
    public Stream<User> findAll() {
        return stores.values().stream();
    }

    @Override
    public Stream<User> findBy(User example) {
        return null;
    }

    @Override
    public Optional<User> findById(UserId userId) {
    return stores.values()
                .stream()
                .filter(user -> user.getId().equals(userId))
                .findAny();
    }

    @Override
    public <S extends User> S save(S entity) {
        User user;
        if (entity.getId()==null){
            UserId nextId = nextId();
             user= new User(nextId, entity.getFirstName(), entity.getLastName(), entity.getEmail(),entity.getPassWord(),entity.getProfiles(), entity.isActive());
            stores.put(nextId,user);
        }else {
            user=entity;
            stores.put(entity.getId(),entity);
        }

        return (S)user;
    }

    @Override
    public void deleteById(UserId userId) {

    }
    private UserId nextId(){
        return stores
                .keySet()
                .stream()
                .map(UserId::id)
                .max(Long::compareTo)
                .map(last->new UserId(last+1))
                .orElse(new UserId(1L));
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return stores.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny();
    }
}
