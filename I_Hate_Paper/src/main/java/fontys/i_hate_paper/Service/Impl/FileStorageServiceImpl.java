package fontys.i_hate_paper.Service.Impl;


import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Models.Document;
import fontys.i_hate_paper.Service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;


public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public void uploadDocument(DocumentDTO dto) {

    }

    @Override
    public void saveDocument(DocumentDTO document) {
        MultipartFile file = document.getFile();
        try{
            byte[] potatoes = file.getBytes();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {

        }



    }
}
