package freelance.application.query.model;

import lombok.Builder;
import lombok.Getter;

import java.io.BufferedInputStream;
@Builder
@Getter
public class File {
    String name;
    BufferedInputStream inputStream;
}
