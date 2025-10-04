package fontys.i_hate_paper.Service.Impl;

import fontys.i_hate_paper.DTOs.FileDTO;
import fontys.i_hate_paper.Models.File;
import fontys.i_hate_paper.Service.FileService;
import fontys.i_hate_paper.mapper.FileMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service

public class FileServiceImpl implements FileService {

    private List<File> filesinmemory = new ArrayList<File>();
//adding the "constructor" for injection is not necessarydgfsjkc


    public FileDTO addAFile(FileDTO fileDTO) {
        File file = FileMapper.toFile(fileDTO); //make this a normal file first
        filesinmemory.add(file); //save this into database/list
        return FileMapper.toFileDTO(file); //give me back the dto of what I saved
    }


    public List<FileDTO> getTheFiles() {
        List<FileDTO> exposethemNOW = new ArrayList<>();
        for (File file : filesinmemory) {
            exposethemNOW.add(FileMapper.toFileDTO(file));
        }
        return exposethemNOW;
    }
}
