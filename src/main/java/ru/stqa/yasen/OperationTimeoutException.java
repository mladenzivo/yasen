package ru.stqa.yasen;

import ru.stqa.trier.LimitExceededException;

public class OperationTimeoutException extends RuntimeException {

  private LimitExceededException cause;

  public OperationTimeoutException(LimitExceededException cause) {
    this.cause = cause;
  }
}
