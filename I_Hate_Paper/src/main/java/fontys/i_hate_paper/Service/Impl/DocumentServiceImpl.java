package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.Configuration.DocumentStorageProperties;
import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Exception.DocumentServiceException;
import fontys.i_hate_paper.Models.Document;
import fontys.i_hate_paper.Repository.DocumentRepository;
import fontys.i_hate_paper.Service.DocumentService;
import fontys.i_hate_paper.Mapper.DocumentMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository docRepository;
    private final DocumentStorageProperties props;
    private static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class); //this is not a constructor. this isnt a bean either


    public DocumentServiceImpl(DocumentRepository docRepository, DocumentStorageProperties props) {
        this.docRepository = docRepository;
        this.props = props;
    }

    @Override
    public Resource getDocumentByUUID(String uuid) {

        Optional<Document> optionalDoc = docRepository.findByFileUUID(uuid);

        if (optionalDoc.isEmpty()) {
            logger.error("Document with uuid " + uuid + " not found");
            throw new DocumentServiceException("Document not found for uuid: " + uuid);
        }

        Document doc = optionalDoc.get();

        String filePath = props.getUploadPath() + File.separator + doc.getDocumentStoredName();

        try {
            File file = new File(filePath);

            if (!file.exists()) {
                logger.error("Document not found for file: " + filePath);
                throw new FileNotFoundException("File not found on disk: " + filePath);
            }

            return new UrlResource(file.toURI());
        }
        catch (MalformedURLException e) {
            logger.error("Document path not found: " + filePath);
            throw new DocumentServiceException("Could not read file: " + filePath, e);
        }
        catch (FileNotFoundException e){
            logger.error("Document file not found: " + filePath);
            throw new DocumentServiceException("Could not read file: " + filePath, e);
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
        String filePath = props.getUploadPath() + File.separator + NameInFolder;

        try (FileOutputStream output = new FileOutputStream(filePath))
        {
            output.write(incoming_doc.getBytes());
            logger.info("Saving document with name {}", incoming_doc.getOriginalFilename());

        } catch (IOException e) {
            logger.error("Failed to save document with name {}", incoming_doc.getOriginalFilename(), e);
            throw new DocumentServiceException("Failed to save document: " + incoming_doc.getOriginalFilename(), e);
        }
        documentDTO.setFileUUID(Uuid);
        documentDTO.setDocumentStoredName(NameInFolder);
        Document file = DocumentMapper.toDoc(documentDTO);
        file.setDeadline(deadline);
        docRepository.save(file);
        return DocumentMapper.toDocDTO(file);

    }

    @Override
    public DocumentDTO checkReturn(DocumentDTO documentDTO) {
        Document file = DocumentMapper.toDoc(documentDTO);
        if (docRepository.findById(file.getId()).isPresent()) {
            return DocumentMapper.toDocDTO(file);
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
