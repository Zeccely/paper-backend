package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Exception.StorageException;
import fontys.i_hate_paper.Models.Document;
import fontys.i_hate_paper.Repository.DocumentRepository;
import fontys.i_hate_paper.Service.CategoryService;
import fontys.i_hate_paper.Service.DocumentService;
import fontys.i_hate_paper.Mapper.DocumentMapper;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository docRepository;
    private final DocumentMapper documentMapper;
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class); //this is not a constructor. this isn't a bean either


    public DocumentServiceImpl(DocumentRepository docRepository, DocumentMapper documentMapper, CategoryService categoryService) {
        this.docRepository = docRepository;
        this.documentMapper = documentMapper;
        this.categoryService = categoryService;
    }

    @Override
    public Resource getDocumentByUUID(String uuid) {

        Optional<Document> optionalDoc = docRepository.findByFileUUID(uuid);

        if (optionalDoc.isEmpty()) {
            logger.error("Document with uuid {} not found", uuid);
            throw new StorageException("DOCUMENT_NOT_FOUND","Document not found for uuid: " + uuid);
        }

        Document doc = optionalDoc.get();

        String filePath = Paths.get(System.getenv("UPLOAD_PATH"), doc.getDocumentStoredName()).toString();

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


    @Override
    public DocumentDTO saveDocument(DocumentDTO documentDTO) {

        String incDeadLine = documentDTO.getDeadline();
        LocalDate deadline = null;

        if (incDeadLine != null && !incDeadLine.isBlank()) {
            deadline = LocalDate.parse(incDeadLine);
        }


        MultipartFile incoming_doc = documentDTO.getFile();
        String Uuid = UUID.randomUUID().toString();
        String NameInFolder = Uuid + "." + getFileExtension(incoming_doc.getOriginalFilename());
        String filePath = System.getenv("UPLOAD_PATH") + File.separator + NameInFolder;

        try (FileOutputStream output = new FileOutputStream(filePath))
        {
            output.write(incoming_doc.getBytes());
            logger.info("Saving document with name {}", incoming_doc.getOriginalFilename());

        } catch (IOException e) {
            logger.error("Failed to save document with name {}", incoming_doc.getOriginalFilename(), e);
            throw new StorageException("DOCUMENT_SAVE_FAILED", "Failed to save document: " + incoming_doc.getOriginalFilename(), e);
        }
        documentDTO.setFileUUID(Uuid);
        documentDTO.setDocumentStoredName(NameInFolder);
        Document file = documentMapper.toDoc(documentDTO);
        file.setCategory(categoryService.getOrCreateCategory(documentDTO.getCategoryName()));
        file.setDeadline(deadline);
        docRepository.save(file);
        return documentMapper.toDocDTO(file);

    }

    @Override
    public List<DocumentDTO> getAllDocuments() {
        List<DocumentDTO> documents = new ArrayList<>();
        for (Document file : docRepository.findAll()) {
            DocumentDTO dtoFiles = documentMapper.toDocDTO(file);
            documents.add(dtoFiles);
        }
        return documents;
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
