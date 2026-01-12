package fontys.i_hate_paper.Controller;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Exception.StorageException;
import fontys.i_hate_paper.Service.DocumentService;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:5173")
public class DocumentStreamController {

    private final DocumentService documentService;

    public DocumentStreamController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<DocumentDTO> getDocument(@ModelAttribute DocumentDTO dto) {
        DocumentDTO done = documentService.saveDocument(dto);
        //pass id here from the previous api call
        return ResponseEntity.ok(done);
    }

    @GetMapping("/ha")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("hi");
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<DocumentDTO> getDocumentById(@PathVariable String uuid) {

        return null; //TODO: implement this later
    }

    @GetMapping
    public ResponseEntity<List<DocumentDTO>> getAllDocuments() {
        List<DocumentDTO> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/{uuid}/file")
    public ResponseEntity<Resource> getFile(@PathVariable String uuid) {

        Resource file = documentService.getDocumentFileByUUID(uuid);
        String filename = file.getFilename();
        String extension = getExtension(filename);

        MediaType mediaType;

        if (extension.equals("png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (extension.equals("jpg") || extension.equals("jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else if (extension.equals("pdf")) {
            mediaType = MediaType.APPLICATION_PDF;
        } else {
            throw new StorageException("INVALID_FILE_STATE", "Invalid stored file type");
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header("Content-Disposition", "inline")
                .body(file);
    }
    //will this work?
    public String getExtension(String filename) {

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
