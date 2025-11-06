package fontys.i_hate_paper.Controller;

import fontys.i_hate_paper.DTOs.DocumentDTO;
import fontys.i_hate_paper.Service.FileStorageService;
import fontys.i_hate_paper.mapper.DocumentMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class DocumentUploadDownloadController {

    FileStorageService fileStorageService;

    @PostMapping(path = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public DocumentDTO getDocument(@RequestPart("file") MultipartFile file, @RequestPart("data") DocumentDTO dto) {
        dto.setFileName(file.getOriginalFilename());
        fileStorageService.saveDocument(DocumentMapper.toFile(dto));
        return dto;
    }

}
