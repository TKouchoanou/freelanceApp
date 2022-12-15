package domain.model;


import freelance.domain.exception.DomainException;
import freelance.domain.models.entity.Rib;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RibTest {
    @Test
    public void createRibWithSuccess(){
        Assertions.assertDoesNotThrow(()-> new Rib("username","FR7628521966142334508845009","ABCDEFGH","09"));
    }
    @Test
    public void createRibWithFailure(){
        Throwable badIban= Assertions.assertThrows(DomainException.class,()-> new Rib("username","28521966142334508845009","ABCDEFGH","09"));
        Throwable badBic= Assertions.assertThrows(DomainException.class,()-> new Rib("username","FR7628521966142334508845009","34CDEFGH5","06"));
        Throwable badCleRib= Assertions.assertThrows(DomainException.class,()-> new Rib("username","FR7628521966142334508845009","ABCDEFGH","0778"));
        Stream.of("invalid","iban").forEach(value->assertTrue(badIban.getMessage().contains(value)));
        Stream.of("invalid","bic").forEach(value->assertTrue(badBic.getMessage().contains(value)));
        Stream.of("invalid","cle", "value").forEach(value->assertTrue(badCleRib.getMessage().contains(value)));
    }
}
