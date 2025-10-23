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
    private Category Category;
    private String Description;
    private LocalDateTime Deadline;
    private Date Added_At = new Date();
    private boolean Is_Handled = false;
    private String picture_path;


}
