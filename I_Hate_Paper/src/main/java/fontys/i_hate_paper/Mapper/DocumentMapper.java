package fontys.i_hate_paper.Mapper;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Models.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentMapper {
    public static DocumentDTO toDocDTO(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription(document.getDescription());
        documentDTO.setTitle(document.getTitle());
        documentDTO.setFileUUID(document.getFileUUID());

        return documentDTO;

    }
    public static Document toDoc(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setDescription(documentDTO.getDescription());
        document.setTitle(documentDTO.getTitle());
        document.setFileUUID(documentDTO.getFileUUID());
        document.setDocumentStoredName(documentDTO.getDocumentStoredName());

        return document;
    }
}
