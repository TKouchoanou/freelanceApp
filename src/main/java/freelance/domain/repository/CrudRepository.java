package freelance.domain.repository;



import freelance.domain.exception.DomainException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface CrudRepository<T,ID> {

    Stream<T> findAll();
    Stream<T> findBy(T example);
    Optional<T> findById(ID id);
    <S extends T> S save(S entity);
    void deleteById(ID id);
    <S extends T> List<S> saveAll(Iterable<S> entities);
    default T getById(ID id){
      return this.findById(id)
              .orElseThrow(()-> new DomainException("entity "+getEntityName()+" with id "+id+" not found "));
    }

    default String getEntityName() {
        return "";
    }
}