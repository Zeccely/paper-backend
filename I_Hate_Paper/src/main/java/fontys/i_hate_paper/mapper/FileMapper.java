package fontys.i_hate_paper.mapper;

import fontys.i_hate_paper.DTOs.FileDTO;
import fontys.i_hate_paper.Models.File;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileMapper {
    public static FileDTO toFileDTO(File file) {
        return new FileDTO(

        );
    }
    public static File toFile(FileDTO fileDTO) {
        return new File(

        );
    }
}
