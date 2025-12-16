package fontys.i_hate_paper.Controller;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Service.DocumentService;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
        DocumentDTO done = documentService.saveDocument(dto);

        return ResponseEntity.ok(done);
    }

    @GetMapping("/documents/{uuid}")
    public ResponseEntity<Resource> downloadDocument(@PathVariable String uuid) {
        Resource DTO = documentService.getDocumentByUUID(uuid);
        return ResponseEntity.ok(DTO);
    }

    @GetMapping("/documents")
    public ResponseEntity<List<DocumentDTO>> getAllDocuments() {
        List<DocumentDTO> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

}
