package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.Exception.StorageException;
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

@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class); //this is not a constructor. this isn't a bean either


    @Override
    public void storeDocument(MultipartFile incoming_doc, String fileName) {

        String filePath = System.getenv("UPLOAD_PATH") + File.separator + fileName;

        try (FileOutputStream output = new FileOutputStream(filePath))
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

        String filePath = Paths.get(System.getenv("UPLOAD_PATH"), storedName).toString();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                logger.error("Document not found for file: {}", filePath);
                throw new StorageException("DOCUMENT_NOT_FOUND","File not found on disk: " + filePath);
            }
            //checksum is good to check if the picture really is the same
            return new UrlResource(file.toURI());
        }
        catch (MalformedURLException e) {
            logger.error("Document path not found: {}", filePath);
            throw new StorageException("PATH_NOT_FOUND","Could not read file: " + filePath, e);
        }
    }
}
