package fontys.i_hate_paper.Controller;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class DocumentUploadDownloadController {

    private final DocumentService documentService;

    public DocumentUploadDownloadController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<DocumentDTO> getDocument(@ModelAttribute DocumentDTO dto) {
        documentService.saveDocument(dto);
        documentService.addAFile(dto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
