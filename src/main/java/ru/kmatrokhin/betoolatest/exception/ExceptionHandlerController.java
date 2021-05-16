package ru.kmatrokhin.betoolatest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kmatrokhin.betoolatest.openapi.model.ApiErrorDTO;

@RestControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(value = {EntityNotFoundException.class})
  public ResponseEntity<ApiErrorDTO> handleEntityNotFoundException(
      EntityNotFoundException exception
  ) {
    final var errorDTO = new ApiErrorDTO();
    errorDTO.setCode(exception.getCode().toString());
    if (exception.getMessage() != null) {
      errorDTO.setMessage(exception.getMessage());
    } else {
      errorDTO.setMessage(exception.getStackTrace()[0].toString());
    }
    return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<ApiErrorDTO> handleEntityNotFoundException(
      Exception exception
  ) {
    final var errorDTO = new ApiErrorDTO();
    errorDTO.setCode(ErrorCode.OTHER_EXCEPTION.toString());
    if (exception.getMessage() != null) {
      errorDTO.setMessage(exception.getMessage());
    } else {
      errorDTO.setMessage(exception.getStackTrace()[0].toString());
    }
    return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
  }

}
