package fontys.i_hate_paper.Controller;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Service.DocumentService;
import fontys.i_hate_paper.Service.FileStorageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class DocumentUploadDownloadController {

    private final DocumentService documentService;
    private final FileStorageService fileStorageService;

    public DocumentUploadDownloadController(DocumentService documentService, FileStorageService fileStorageService) {
        this.documentService = documentService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String getDocument(@ModelAttribute DocumentDTO dto) {
        fileStorageService.saveDocument(dto);
        documentService.addAFile(dto);

        return "ok";
    }

}
