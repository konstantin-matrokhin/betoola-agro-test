package ru.kmatrokhin.betoolatest.exception;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {
  private final ErrorCode code;

  public EntityNotFoundException(ErrorCode code, String message) {
    super(message);
    this.code = code;
  }

}
