package ru.stqa.lenium;

import java.util.Objects;

public class Window extends Dom<Window> {

  private final String windowHandle;

  Window(Browser browser, String windowHandle) {
    super(browser);
    this.windowHandle = windowHandle;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Window window = (Window) o;
    return Objects.equals(windowHandle, window.windowHandle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(windowHandle);
  }

  public void close() {
    activate();
    browser.closeWindow(windowHandle);
  }

  public Window open(String url) {
    execute(browser.driver::get, url);
    return this;
  }

  public String title() {
    return execute(browser.driver::getTitle);
  };

  protected Window activate() {
    browser.selectWindow(this.windowHandle);
    return this;
  }

}
