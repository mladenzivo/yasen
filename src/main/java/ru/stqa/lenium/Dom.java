package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Dom<T extends Dom> {

  protected final Browser browser;
  protected final WebDriver driver;

  Dom(Browser browser) {
    this.browser = browser;
    this.driver = browser.driver;
  }

  public Element $(String cssSelector) {
    return new Element(this, By.cssSelector(cssSelector));
  }

  protected abstract T activate();

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

}
