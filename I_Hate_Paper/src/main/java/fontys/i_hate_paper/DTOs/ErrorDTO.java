package fontys.i_hate_paper.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    public String code;
    public String message;
    public String field;
}
