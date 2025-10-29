package fontys.i_hate_paper.mapper;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Models.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentMapper {
    public static DocumentDTO toFileDTO(Document file) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription(file.getDescription());
        documentDTO.setTitle(file.getTitle());
        documentDTO.setAdded_At(file.getAdded_At());
        documentDTO.setIs_Handled(file.isIs_Handled());
        return documentDTO;

    }
    public static Document toFile(DocumentDTO documentDTO) {
        Document file = new Document();
        file.setDescription(documentDTO.getDescription());
        file.setTitle(documentDTO.getTitle());
        file.setAdded_At(documentDTO.getAdded_At());

        return file;
    }
}
