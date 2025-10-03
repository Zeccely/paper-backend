package fontys.i_hate_paper.Models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private String Title;
    private FileType FileType;
    private String Description;
    private LocalDateTime Deadline;
    private Date Added_At;
    private boolean Is_Handled;
    private String picture_path;


}
