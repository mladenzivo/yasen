package ru.stqa.yasen;

class TopLevelElementContext extends AbstractElementContext {

  private Window window;

  TopLevelElementContext(Window window) {
    super(() -> window.driver, window::activate, () -> { throw new UnsupportedOperationException(); });
    this.window = window;
  }

  @Override
  public String toString() {
    return window.toString();
  }
}
