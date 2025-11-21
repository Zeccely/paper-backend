package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.Configuration.DocumentStorageProperties;
import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Models.Document;
import fontys.i_hate_paper.Repository.DocumentRepository;
import fontys.i_hate_paper.Service.DocumentService;
import fontys.i_hate_paper.mapper.DocumentMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository docRepository;
    private final DocumentStorageProperties props;

    public DocumentServiceImpl(DocumentRepository docRepository, DocumentStorageProperties props) {
        this.docRepository = docRepository;
        this.props = props;
    }

    public DocumentDTO addAFile(DocumentDTO documentDTO) {
        Document file = DocumentMapper.toFile(documentDTO); //make this a normal file first
        docRepository.save(file); //save this into database/list
        return DocumentMapper.toFileDTO(file); //give me back the dto of what I saved
    }


    public List<DocumentDTO> getTheFiles() {
        List<DocumentDTO> secrets = new ArrayList<>();
        for (Document file : docRepository.findAll()){
            secrets.add(DocumentMapper.toFileDTO(file));
        }
       return secrets;
    }

    @Override
    public void saveDocument(DocumentDTO documentDTO) {
        MultipartFile incoming_doc = documentDTO.getFile();
        String Title = documentDTO.getTitle();
        String Uuid = UUID.randomUUID().toString();
        String NameInFolder = Uuid + "." + getFileExtension(incoming_doc.getOriginalFilename());

        String filePath = props.getUploadPath() + File.separator + NameInFolder;

        try (FileOutputStream output = new FileOutputStream(filePath))
        {

            output.write(incoming_doc.getBytes());

        } catch (IOException e) {
            e.printStackTrace();//stack trace/logger
        }

        Document doc = new Document();
        doc.setTitle(Title);
        doc.setFileUUID(Uuid);
        doc.setDocumentStoredName(NameInFolder);
        docRepository.save(doc);
    }

    @Override
    public DocumentDTO checkReturn(DocumentDTO documentDTO) {
        Document file = DocumentMapper.toFile(documentDTO);
        if (docRepository.findById(file.getId()).isPresent()) {
            return DocumentMapper.toFileDTO(file);
        }
        return null;
    }

    String getFileExtension(String filename) {
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
