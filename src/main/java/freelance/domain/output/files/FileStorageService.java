package freelance.domain.output.files;

import freelance.domain.core.objetValue.File;

import java.util.UUID;

public interface FileStorageService {
    void init();
    File store(File file);

    File replace(File file);
    File load(UUID fileId,String context);
    void delete(UUID fileId,String context);

}
