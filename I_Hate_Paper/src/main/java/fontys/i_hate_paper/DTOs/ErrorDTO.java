package fontys.i_hate_paper.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    public String code;
    public String message;

    //Hi. I know what you're thinking. this is not necessary! but I wanted to make fun things happen when errors happen
    //like gifs appearing for specific errors. or just a custom page. and apparently this is the way to atleast start
}
