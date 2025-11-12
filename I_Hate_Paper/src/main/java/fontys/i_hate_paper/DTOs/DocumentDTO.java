package fontys.i_hate_paper.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DocumentDTO {
    private String Title;
    private String Description;
    private Integer CategoryID;
    private boolean Is_Handled = false;
    private MultipartFile File;


}
