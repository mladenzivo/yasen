package ru.stqa.lenium;

class TopLevelElementContext extends AbstractElementContext {

  TopLevelElementContext(Window window) {
    super(window.driver::findElement, window::activate, () -> { throw new UnsupportedOperationException(); });
  }
}
