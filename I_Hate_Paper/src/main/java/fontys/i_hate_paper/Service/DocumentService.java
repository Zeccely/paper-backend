package fontys.i_hate_paper.Service;

import fontys.i_hate_paper.DTOs.DocumentDTO;

import java.util.List;

public interface DocumentService {
    DocumentDTO addAFile(DocumentDTO documentDTO);
    List<DocumentDTO> getTheFiles();
    void saveDocument(DocumentDTO document);
    DocumentDTO checkReturn(DocumentDTO documentDTO); //this part should be in the logic of adding a file

}
