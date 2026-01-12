package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.Configuration.DocumentStorageProperties;
import fontys.i_hate_paper.Exception.StorageException;
import fontys.i_hate_paper.Exception.ValidationException;
import fontys.i_hate_paper.Service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class); //this is not a constructor. this isn't a bean either
    private final List<String> allowedExtensions = List.of("pdf", "png", "jpg", "jpeg");
    private final DocumentStorageProperties docProps;

    public StorageServiceImpl(DocumentStorageProperties docProps) {
        this.docProps = docProps;
    }

    @Override
    public void storeDocument(MultipartFile incoming_doc, String fileName) {

        try (FileOutputStream output = new FileOutputStream(System.getProperty("user.dir") + File.separator +docProps.getUploadPath()+File.separator+fileName))
        {
            output.write(incoming_doc.getBytes());
            logger.info("Saving document with name {}", incoming_doc.getOriginalFilename());

        } catch (IOException e) {
            logger.error("Failed to save document with name {}", incoming_doc.getOriginalFilename(), e);
            throw new StorageException("DOCUMENT_SAVE_FAILED", "Failed to save document: " + incoming_doc.getOriginalFilename(), e);
        }
    }

    @Override
    public Resource loadAsResource(String storedName) {

        try {
            File file = new File(Paths.get(docProps.getUploadPath(),storedName).toString());
            if (!file.exists()) {
                logger.error("Document not found for file: {}", docProps.getUploadPath()+storedName);
                throw new StorageException("DOCUMENT_NOT_FOUND", "File not found on disk: " + docProps.getUploadPath()+storedName);
            }
            //checksum is good to check if the picture really is the same
            return new UrlResource(file.toURI());
        }
        catch (MalformedURLException e) {
            logger.error("Document path not found: {}", docProps.getUploadPath()+storedName);
            throw new StorageException("PATH_NOT_FOUND", "Could not read file: " + docProps.getUploadPath()+storedName, e);
        }
    }

    public void validateExtension(MultipartFile file) {

        String originalName = file.getOriginalFilename();

        if (originalName== null || !originalName.contains(".")) {
            throw new ValidationException("INVALID_FILE", "Invalid file");
        }

        String extension = getFileExtension(originalName);

        if (extension.isBlank()) {
            throw new ValidationException("INVALID_FILE_EXTENSION", "There is no extension");
        }

        if (!allowedExtensions.contains(extension)) {
            throw new ValidationException("INVALID_FILE_EXTENSION", "Invalid file extension: " + extension);
        }

    }

    public String getFileExtension(String filename) {

        if (filename == null) {
            return null;
        }
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex >= 0) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }
}
