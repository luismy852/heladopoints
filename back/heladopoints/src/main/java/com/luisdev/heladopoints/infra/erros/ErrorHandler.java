package com.luisdev.heladopoints.infra.erros;

import com.luisdev.heladopoints.exception.DuplicateReceiptException;
import com.luisdev.heladopoints.exception.EmailAlreadyExistsException;
import com.luisdev.heladopoints.exception.InvalidReceiptException;
import com.luisdev.heladopoints.exception.OcrProccessingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleBadRequest(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(ValidationErrorData::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(InvalidReceiptException.class)
    public ResponseEntity handleInvalidReceipt(InvalidReceiptException e) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
    }

    @ExceptionHandler(OcrProccessingException.class)
    public ResponseEntity handleOcrProcessingError(OcrProccessingException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity handleIllegalArgument(EmailAlreadyExistsException e) {
        return ResponseEntity.badRequest().body("Este correo electrónico ya se encuentra registrado.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manejarError400(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record ValidationErrorData(String campo, String error) {
        public ValidationErrorData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body("The file is too large! Maximum allowed is 10MB.");
    }

    @ExceptionHandler(DuplicateReceiptException.class)
    public ResponseEntity<String> handleDuplicate (){
        return ResponseEntity.badRequest().body("Está factura ya fue subida.");
    }
}
