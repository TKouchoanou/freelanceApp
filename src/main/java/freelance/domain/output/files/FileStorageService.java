package freelance.domain.output.files;

import freelance.domain.core.objetValue.File;

import java.nio.file.Path;
import java.util.UUID;
import java.util.stream.Stream;

public interface FileStorageService {
    void init();
    File store(File file);

    File replace(File file);
    Path load(UUID fileId);
    Stream<Path> loadAll();
    void delete(UUID fileId);

}
