package ru.stqa.yasen;

import ru.stqa.trier.LimitExceededException;

public class ElementLookupTimeoutException extends RuntimeException {

  private LimitExceededException cause;

  public ElementLookupTimeoutException(LimitExceededException cause) {
    this.cause = cause;
  }
}
