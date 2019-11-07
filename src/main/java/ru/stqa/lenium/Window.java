package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Window {

  final Browser browser;
  final WebDriver driver;
  private final String windowHandle;

  Window(Browser browser, String windowHandle) {
    this.browser = browser;
    this.driver = browser.driver;
    this.windowHandle = windowHandle;
  }

  public Element $(String cssSelector) {
    return new Element(this, By.cssSelector(cssSelector));
  }

  WebElement findElement(By locator) {
    activate();
    return browser.driver.findElement(locator);
  }

  protected void execute(Runnable command) {
    activate();
    browser.execute(command);
  }

  protected <R> R execute(Supplier<R> command) {
    activate();
    return browser.execute(command);
  }

  protected <A> void execute(Consumer<A> command, A arg) {
    activate();
    browser.execute(command, arg);
  }

  protected <A, R> R execute(Function<A, R> command, A arg) {
    activate();
    return browser.execute(command, arg);
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
