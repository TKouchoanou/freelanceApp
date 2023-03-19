package freelance.storage.memory;

import freelance.domain.models.entity.Freelance;
import freelance.domain.models.objetValue.FreelanceId;
import freelance.domain.models.objetValue.UserId;
import freelance.domain.repository.FreelanceRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
@Repository
@SuppressWarnings("unchecked")
public class FreelanceRepositoryImpl implements FreelanceRepository {
    Map<FreelanceId, Freelance> stores= new HashMap<>();
    @Override
    public Stream<Freelance> findAll() {
        return stores.values().stream();
    }

    @Override
    public Stream<Freelance> findBy(Freelance example) {
        return stores.values().stream()
                .filter(fl-> example.getId()!=null && example.getId().equals(fl.getId()))
                .filter(fl->example.getUserId()!=null && example.getUserId().equals(fl.getUserId()))
                .filter(fl->example.getRibId()!=null && example.getRibId().equals(fl.getRibId()));
    }

    @Override
    public Optional<Freelance> findById(FreelanceId freelanceId) {
        return stores.values().stream()
                .filter(fl->fl.getId().equals(freelanceId))
                .findAny();
    }

    @Override
    public <S extends Freelance> S save(S freelance) {
        Freelance saved;
        if(freelance.getId()==null){
            FreelanceId id=nextId();
            saved=new Freelance(id,freelance.getUserId(),freelance.getRibId(),freelance.getCompanyId(),freelance.getCreatedDate(),freelance.getUpdatedDate()); //akuegnitordé man mon agni hon do dokou gnon tété
        }else {
            saved=new Freelance(freelance.getId(),freelance.getUserId(),freelance.getRibId(),freelance.getCompanyId(),freelance.getCreatedDate(),freelance.getUpdatedDate());
        }
        stores.put(saved.getId(),saved);
        return (S) saved;
    }

    @Override
    public void deleteById(FreelanceId freelanceId) {

    }

    @Override
    public <S extends Freelance> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Freelance> findByUserId(UserId userId) {
        return stores.values()
                .stream()
                .filter(f->userId.equals(f.getUserId()))
                .findAny();
    }
    private FreelanceId nextId(){
        return stores
                .keySet()
                .stream()
                .map(FreelanceId::value)
                .max(Long::compareTo)
                .map(last->new FreelanceId(last+1))
                .orElse(new FreelanceId(1L));
    }
}
