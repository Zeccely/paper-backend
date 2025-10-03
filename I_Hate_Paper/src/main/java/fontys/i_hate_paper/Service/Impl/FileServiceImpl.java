package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.DTOs.FileDTO;
import fontys.i_hate_paper.Models.File;
import fontys.i_hate_paper.Service.FileService;
import fontys.i_hate_paper.mapper.FileMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private List<File> filesinmemory = new ArrayList<File>();
    private FileService fileService;


    public FileDTO addAFile(FileDTO fileDTO) {
        File filey = FileMapper.toFile(fileDTO); //make this a normal file first
        filesinmemory.add(filey); //save this into database/list
        return FileMapper.toFileDTO(filey); //give me back the dto of what I saved
    }


    public List<FileDTO> getTheFiles() {
        List<FileDTO> exposethemNOW = new ArrayList<>();
        for (File file : filesinmemory) {
            exposethemNOW.add(FileMapper.toFileDTO(file));
        }
        return exposethemNOW;
    }
}
