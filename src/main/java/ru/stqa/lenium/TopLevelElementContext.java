package ru.stqa.lenium;

class TopLevelElementContext extends AbstractElementContext {

  TopLevelElementContext(Window window) {
    super(window.driver, window::activate, () -> { throw new UnsupportedOperationException(); });
  }
}
