package freelance.infrastructure.queryService;

import freelance.domain.core.objetValue.RibId;
import freelance.domain.output.repository.RibRepository;
import freelance.application.query.RibQueryService;
import freelance.application.query.model.Rib;
import freelance.application.query.query.SearchRibQuery;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;
@Service
public class RibQueryServiceImpl implements RibQueryService {
    RibRepository ribRepository;

    RibQueryServiceImpl(RibRepository ribRepository){
        this.ribRepository=ribRepository;
    }
    @Override
    public Stream<Rib> searchByIban(String iban) {
        return this.ribRepository.findAll()
                     .filter(rib -> rib.getIban().value().equals(iban))
                     .map(ZMapUtils::toQuery);
    }

    @Override
    public Stream<Rib> searchByUserName(String userName) {
        return this.ribRepository.findAll()
                .filter(rib -> rib.getUsername().equals(userName))
                .map(ZMapUtils::toQuery);
    }

    @Override
    public Stream<Rib> search(SearchRibQuery searchRibQuery) {
        return Stream.empty();
    }

    @Override
    public Optional<Rib> getById(Long id) {
        return this.ribRepository.findAll()
                .filter(rib -> rib.getId().equals(new RibId(id)))
                .findAny()
                .map(ZMapUtils::toQuery);
    }

    @Override
    public Stream<Rib> getAll() {
        return this.ribRepository.findAll()
                .map(ZMapUtils::toQuery);
    }
}
