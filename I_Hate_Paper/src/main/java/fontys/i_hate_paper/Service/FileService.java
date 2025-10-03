package fontys.i_hate_paper.Service;

import fontys.i_hate_paper.DTOs.FileDTO;

import java.util.List;

public interface FileService {
    FileDTO addAFile(FileDTO fileDTO);
    List<FileDTO> getTheFiles();
}
