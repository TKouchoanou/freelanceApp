package freelance.storage.memory;

import freelance.domain.models.entity.Rib;
import freelance.domain.models.objetValue.RibId;
import freelance.domain.repository.RibRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
@Repository
@SuppressWarnings("unchecked")
public class RibRepositoryImpl implements RibRepository {
    Map<RibId,Rib>  stores= new HashMap<>();
    @Override
    public Stream<Rib> findAll() {
        return stores.values().stream();
    }

    @Override
    public Stream<Rib> findBy(Rib example) {
        return stores.values()
                .stream()
                .filter(rib -> rib.getCleRib()==null || rib.getCleRib().equals(example.getCleRib()))
                .filter(rib -> rib.getBic()==null || rib.getBic().equals(example.getBic()))
                .filter(rib -> rib.getIban()==null || rib.getIban().equals(example.getIban()))
                .filter(rib -> rib.getId()==null || rib.getId().equals(example.getId()))
                .filter(rib -> rib.getUsername()==null || rib.getUsername().equals(example.getUsername()))
                ;
    }

    @Override
    public Optional<Rib> findById(RibId ribId) {
        return stores.values()
                .stream()
                .filter(rib -> rib.getId().equals(ribId))
                .findAny();
    }

    @Override
    public <S extends Rib> S save(S rib) {
        Rib saved;
        if(rib.getId()==null){
            RibId id=nextId();
            saved=new Rib(id,rib.getUsername(),rib.getIban(),rib.getBic(),rib.getCleRib()); //akuegnitordé
        }else {
            saved=new Rib(rib.getId(),rib.getUsername(),rib.getIban(),rib.getBic(),rib.getCleRib());
        }
        stores.put(saved.getId(),saved);
        return (S) saved;
    }
    public  RibId nextId(){
      return stores
              .keySet()
              .stream()
              .map(RibId::value)
              .max(Long::compareTo)
              .map(last->new RibId(last+1))
              .orElse(new RibId(1L));
    }

    @Override
    public void deleteById(RibId ribId) {
        stores.remove(ribId);
    }

    @Override
    public <S extends Rib> List<S> saveAll(Iterable<S> entities) {
     return StreamSupport.stream(entities.spliterator(),false)
               .map(this::save)
               .toList();
    }
}
