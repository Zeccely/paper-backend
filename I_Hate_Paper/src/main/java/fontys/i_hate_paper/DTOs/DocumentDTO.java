package fontys.i_hate_paper.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DocumentDTO {
    private String title;
    private String description;
    private Integer categoryID;
    private boolean is_Handled = false;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartFile file;
    private String fileUUID;
    private String documentStoredName;


}
