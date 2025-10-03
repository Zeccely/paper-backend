package fontys.i_hate_paper.Controller;

import fontys.i_hate_paper.DTOs.FileDTO;
import fontys.i_hate_paper.Service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/add_file")
public class FileController {

    private FileService fileService;

    @PostMapping
    public ResponseEntity<FileDTO> addAFile(@RequestBody FileDTO fileDTO) {
        FileDTO thing = fileService.addAFile(fileDTO);
        return new ResponseEntity<>(thing, HttpStatus.CREATED);
    };
    @GetMapping
    public ResponseEntity<List<FileDTO>> getTheFiles() {
        List<FileDTO> files = fileService.getTheFiles();
        return ResponseEntity.ok(files);
    }
}
