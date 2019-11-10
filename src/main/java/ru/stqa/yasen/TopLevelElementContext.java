package ru.stqa.yasen;

class TopLevelElementContext extends AbstractElementContext {

  TopLevelElementContext(Window window) {
    super(() -> window.driver, window::activate, () -> { throw new UnsupportedOperationException(); });
  }
}
