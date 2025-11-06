package fontys.i_hate_paper.Service;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Models.Document;

public interface FileStorageService {
    //read about file handling in java
    public void uploadDocument(DocumentDTO dto);
    public void saveDocument(Document document);
}
