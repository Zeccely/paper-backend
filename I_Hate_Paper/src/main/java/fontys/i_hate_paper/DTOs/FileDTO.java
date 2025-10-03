package fontys.i_hate_paper.DTOs;

import fontys.i_hate_paper.Models.FileType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor

public class FileDTO {
    private String Title;
    private FileType FileType;
    private String Description;
    private LocalDateTime Deadline;
    private Date Added_At;
    private boolean Is_Handled;
    private String picture_path;
}
