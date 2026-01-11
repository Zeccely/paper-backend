package fontys.i_hate_paper.Service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void storeDocument(MultipartFile file, String fileName);
    Resource loadAsResource(String storedName);
    void validateExtension(MultipartFile file);
    String getFileExtension(String fileName);
}
