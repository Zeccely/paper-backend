package fontys.i_hate_paper.Exception;
//might make this a global handler or something because I keep writing logger logger logger as I go

public class DocumentServiceException extends RuntimeException {
    public DocumentServiceException(String message) {
        super(message);
    }
    public DocumentServiceException(String message, Throwable cause) {super(message, cause);}
}
