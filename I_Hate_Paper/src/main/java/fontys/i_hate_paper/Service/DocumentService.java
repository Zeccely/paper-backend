package fontys.i_hate_paper.Service;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import org.springframework.core.io.Resource;


import java.util.List;

public interface DocumentService {
    Resource getDocumentByUUID(String UUID);
    DocumentDTO saveDocument(DocumentDTO document);
    List<DocumentDTO> getAllDocuments();
}
