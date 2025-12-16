package fontys.i_hate_paper.Exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String code;

    public NotFoundException(String code, String message) {
        super(message);
        this.code = code;
    }

    public NotFoundException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}

