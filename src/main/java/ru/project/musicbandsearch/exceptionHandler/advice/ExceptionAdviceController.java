package ru.project.musicbandsearch.exceptionHandler.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.project.musicbandsearch.exceptionHandler.models.Forbidden;
import ru.project.musicbandsearch.exceptionHandler.models.MarketError;

@ControllerAdvice
public class ExceptionAdviceController {
    @ExceptionHandler
    public ResponseEntity<?> forbidden(Forbidden e){
        MarketError response = new MarketError(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
