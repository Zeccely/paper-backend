package fontys.i_hate_paper.Service.Impl;


import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Models.Document;
import fontys.i_hate_paper.Service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Override
    public void uploadDocument(DocumentDTO dto) {

    }

    @Override
    public void saveDocument(DocumentDTO document) {
        MultipartFile incoming_doc = document.getFile();
        String filePath = "C:\\Users\\jepis\\Desktop\\Dokks" + File.separator + incoming_doc.getOriginalFilename();

        try (FileOutputStream output = new FileOutputStream(filePath))
        {

            output.write(incoming_doc.getBytes());

        } catch (IOException e) {
            System.out.println(e.getMessage()); //stack trace/logger
        } //I can't finally close? it seems the FileOutputStream closes itself?


    }
}
