package fontys.i_hate_paper.Controller;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class DocumentController {

    private DocumentService documentService;

    @PostMapping("/addfile")
    public ResponseEntity<DocumentDTO> addAFile(@RequestBody DocumentDTO documentDTO) {
        DocumentDTO thing = documentService.addAFile(documentDTO);
        return new ResponseEntity<>(thing, HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<List<DocumentDTO>> getTheFiles() {
        List<DocumentDTO> files = documentService.getTheFiles();
        return ResponseEntity.ok(files);
    }
}
