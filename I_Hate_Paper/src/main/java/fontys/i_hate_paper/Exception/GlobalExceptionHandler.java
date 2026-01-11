package fontys.i_hate_paper.Exception;

import fontys.i_hate_paper.DTOs.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(NotFoundException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setCode(ex.getCode());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }
    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorDTO> handleStorageException(StorageException ex) {
        ErrorDTO error = new ErrorDTO();
        error.setCode(ex.getCode());
        error.setMessage(ex.getMessage());
        return ResponseEntity.status(500).body(error);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDTO> handleBadRequest(ValidationException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode(ex.getCode());
        errorDTO.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleUnknown(Exception ex) {
        ErrorDTO error = new ErrorDTO();
        error.setCode("INTERNAL_ERROR");
        error.setMessage("Something went wrong");
        return ResponseEntity.status(500).body(error);
    }

    @ExceptionHandler (MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorDTO> handleMaxUploadSize(MaxUploadSizeExceededException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setCode("FILE_SIZE_LIMIT_EXCEEDED");
        errorDTO.setMessage("Maximum Upload Size Exceeded");
        return ResponseEntity.badRequest().body(errorDTO);
    }
}

//DRY is violated

