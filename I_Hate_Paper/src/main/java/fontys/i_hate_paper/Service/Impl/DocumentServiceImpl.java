package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Exception.StorageException;
import fontys.i_hate_paper.Models.Document;
import fontys.i_hate_paper.Repository.DocumentRepository;
import fontys.i_hate_paper.Service.CategoryService;
import fontys.i_hate_paper.Service.DocumentService;
import fontys.i_hate_paper.Mapper.DocumentMapper;

import fontys.i_hate_paper.Service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final StorageService storageService;
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class); //this is not a constructor. this isn't a bean either


    public DocumentServiceImpl(DocumentRepository docRepository, DocumentMapper documentMapper, CategoryService categoryService, StorageService storageService) {
        this.docRepository = docRepository;
        this.documentMapper = documentMapper;
        this.categoryService = categoryService;
        this.storageService = storageService;
    }

    @Override
    public Resource getDocumentFileByUUID(String uuid) { //Unittest?

        Optional<Document> optionalDoc = docRepository.findByFileUUID(uuid);

        if (optionalDoc.isEmpty()) {
            logger.error("Document with uuid {} not found", uuid);
            throw new StorageException("DOCUMENT_NOT_FOUND","Document not found for uuid: " + uuid);
        }

        Document doc = optionalDoc.get();

        return storageService.loadAsResource(doc.getDocumentStoredName());
    }


    @Override
    public DocumentDTO saveDocument(DocumentDTO documentDTO) { //UNITTEST

        String incDeadLine = documentDTO.getDeadline();
        LocalDate deadline = null;

        if (incDeadLine != null && !incDeadLine.isBlank()) {
            deadline = LocalDate.parse(incDeadLine);
        }

        MultipartFile incoming_doc = documentDTO.getFile();
        String Uuid = UUID.randomUUID().toString();
        storageService.validateExtension(incoming_doc); //yay
        String NameInFolder = Uuid + "." + storageService.getFileExtension(incoming_doc.getOriginalFilename());

        storageService.storeDocument(incoming_doc, NameInFolder);

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

    public DocumentDTO getDocumentByUUID(String UUID) {

        Optional<Document> optionalDoc = docRepository.findByFileUUID(UUID);

        if (optionalDoc.isEmpty()) {
            logger.error("Document with uuid {} not found", UUID);
            throw new StorageException("DOCUMENT_NOT_FOUND","Document not found for uuid: " + UUID);
        }

        Document doc = optionalDoc.get();
        return documentMapper.toDocDTO(doc);
    }


}
