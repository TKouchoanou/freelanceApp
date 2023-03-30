package freelance.infrastructure.dataaccess.memory;

import freelance.domain.core.objetValue.File;
import freelance.domain.output.files.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileLocalStorageService implements FileStorageService {
    private final String storageLocation;



    public FileLocalStorageService(@Value("${storage.directory}")String storageLocation) {
        this.storageLocation = storageLocation;
    }

    @Override
    @PostConstruct
    public void init() {
        try {
           Files.createDirectories(Paths.get(storageLocation));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize LocalFileStorageService", e);
        }
    }

    @Override
    public File store(File file) {

        UUID fileId=UUID.randomUUID();
        String filename = path(fileId, file.context());
        Path filePath = Paths.get(storageLocation, filename);
        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.file());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store file " + filename, e);
        }
        return new File(fileId, file.file(), file.context());
    }

    @Override
    public File replace(File file) {
        String filename = path(file.id(), file.context());
        Path filePath = Paths.get(storageLocation, filename);
        try {
            Files.write(filePath, file.file());
        } catch (IOException e) {
            throw new RuntimeException("Failed to replace file " + filename, e);
        }
        return new File(file.id(), file.file(), filename);
    }

    @Override
    public File load(UUID fileId,String context) {
        String filename = path(fileId, context);
        Path filePath = Paths.get(storageLocation, filename);
        if (!Files.exists(filePath.getParent())) {
            throw new RuntimeException("File " + filename + " not found");
        }
        byte[] fileContent;
        try {
            fileContent= Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file " + filename, e);
        }
        return new File(fileId, fileContent, filePath.getFileName().toString());
    }
    public static File load1(UUID fileId,String context) {
        String filename = context+"/"+fileId;
        Path filePath = Paths.get("/home/citydevweb/PT/Freelance/src/main/resources/storage/", filename);
        if (!Files.exists(filePath.getParent())) {
            throw new RuntimeException("File " + filename + " not found");
        }
        byte[] fileContent;
        try {
            fileContent= Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load file " + filename, e);
        }
        return new File(fileId, fileContent, filePath.getFileName().toString());
    }
    public static void main(String[] args){
    //  String field="/home/citydevweb/PT/Freelance/src/main/resources/storage/billing";
       UUID id= UUID.fromString("272280fb-28a2-453c-a4fb-d6a7d28234f8");
        File file=load1(id,"billing");

        String files= file.context();
    }

    @Override
    public void delete(UUID fileId,String context) {
        String filename  = path(fileId, context);
        Path filePath = Paths.get(storageLocation, filename);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file " + filename, e);
        }
    }
    private String path(UUID fileId, String context){
        return  context+"/"+fileId;
    }
}

/*
   private final Path rootLocation;

    public LocalStorageService(String rootDir) {
        this.rootLocation = Paths.get(rootDir);
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location: " + rootLocation, e);
        }
    }

    @Override
    public freelance.domain.core.objetValue.File store(freelance.domain.core.objetValue.File file) {
        return null;
    }

    @Override
    public freelance.domain.core.objetValue.File replace(freelance.domain.core.objetValue.File file) {
        return null;
    }

    @Override
    public Path load(UUID fileId) {
        return null;
    }

    @Override
    public File store(File file) {
        UUID fileId = UUID.randomUUID();
        Path destination = rootLocation.resolve(fileId.toString());
        try {
            Files.copy(file.getInputStream(), destination);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + file.getFilename(), e);
        }
        return new File(fileId, file.getFilename(), destination.toAbsolutePath().toString());
    }

    @Override
    public File replace(File file) {
        Path destination = rootLocation.resolve(file.getId().toString());
        try {
            Files.copy(file.getInputStream(), destination);
        } catch (IOException e) {
            throw new RuntimeException("Failed to replace file: " + file.getId(), e);
        }
        return new File(file.getId(), file.getFilename(), destination.toAbsolutePath().toString());
    }

    @Override
    public Path load(UUID fileId) {
        return rootLocation.resolve(fileId.toString());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(rootLocation, 1)
                    .filter(path -> !path.equals(rootLocation))
                    .map(rootLocation::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Failed to list stored files", e);
        }
    }

    @Override
    public void delete(UUID fileId) {
        Path file = load(fileId);
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + fileId, e);
        }
    }

    private static class File {
        private final UUID id;
        private final String filename;
        private final String location;

        private File(UUID id, String filename, String location) {
            this.id = id;
            this.filename = filename;
            this.location = location;
        }

        public UUID getId() {
            return id;
        }

        public String getFilename() {
            return filename;
        }

        public InputStream getInputStream() throws IOException {
            if (location != null) {
                return new BufferedInputStream(Files.newInputStream(Paths.get(location)));
            }
            throw new RuntimeException();
        }
    }
*/