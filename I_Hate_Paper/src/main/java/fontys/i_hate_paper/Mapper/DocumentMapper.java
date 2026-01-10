package fontys.i_hate_paper.Mapper;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Models.Document;
import org.springframework.stereotype.Component;


@Component
public class DocumentMapper {
    public DocumentDTO toDocDTO(Document document) {

        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription(document.getDescription());
        documentDTO.setTitle(document.getTitle());
        documentDTO.setFileUUID(document.getFileUUID());
        documentDTO.setDeadline(String.valueOf(document.getDeadline()));
        documentDTO.setCategoryName(document.getCategory().getName());
        documentDTO.setCategoryId(document.getCategory().getId());
        documentDTO.setDocumentStoredName(document.getDocumentStoredName());

        return documentDTO;
    }
    public Document toDoc(DocumentDTO documentDTO) {

        Document document = new Document();
        document.setDescription(documentDTO.getDescription());
        document.setTitle(documentDTO.getTitle());
        document.setFileUUID(documentDTO.getFileUUID());
        document.setDeadline(null);
        document.setDocumentStoredName(documentDTO.getDocumentStoredName());

        return document;
    }
}
