package fontys.i_hate_paper.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DocumentDTO {
    private String Title;
    private String Description;
    private Integer CategoryID;
    private Date Added_At = new Date();
    private boolean Is_Handled = false;


}
