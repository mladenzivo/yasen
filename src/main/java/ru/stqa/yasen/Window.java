package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Window implements CanBeActivated, ElementContext {

  final Browser browser;
  final WebDriver driver;
  private final String windowHandle;

  Window(Browser browser, String windowHandle) {
    this.browser = browser;
    this.driver = browser.driver;
    this.windowHandle = windowHandle;
  }

  public Element $(String cssSelector) {
    return new StandaloneElement(this, new SimpleFinder(By.cssSelector(cssSelector)));
  }

  public Element $t(String text) {
    return new StandaloneElement(this, new ByTextFinder(text));
  }

  public Element $t(String tag, String text) {
    return new StandaloneElement(this, new ByTextFinder(tag, text));
  }

  public ElementList $$(String cssSelector) {
    return new ElementListImpl(this, new SimpleFinder(By.cssSelector(cssSelector)));
  }

  public Object executeScript(String script, Object... args) {
    return getJavascriptExecutor().executeScript(script, args);
  }

  private void execute(Runnable command) {
    activate();
    browser.execute(command);
  }

  private <R> R execute(Supplier<R> command) {
    activate();
    return browser.execute(command);
  }

  private <A> void execute(Consumer<A> command, A arg) {
    activate();
    browser.execute(command, arg);
  }

  private <A, R> R execute(Function<A, R> command, A arg) {
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

  public void maximize() {
    execute(() -> browser.driver.manage().window().maximize());
  }

  public Window open(String url) {
    execute(browser.driver::get, url);
    return this;
  }

  public String title() {
    return execute(browser.driver::getTitle);
  }

  public void activate() {
    browser.selectWindow(this.windowHandle);
  }

  @Override
  public String toString() {
    return String.format("<%s>", windowHandle);
  }

  public String url() {
    return execute(browser.driver::getCurrentUrl);
  }

  @Override
  public WebElement findFirstBy(Finder finder) {
    return finder.findFirstIn(driver);
  }

  @Override
  public List<WebElement> findAllBy(Finder finder) {
    return finder.findAllIn(driver);
  }

  @Override
  public JavascriptExecutor getJavascriptExecutor() {
    return new JavascriptExecutor() {

      @Override
      public Object executeScript(String script, Object... args) {
        activate();
        Object[] modifiedArgs = Stream.of(args)
          .map(arg -> arg instanceof Element ? ((Element) arg).getWebElement() : arg)
          .toArray();
        return ((JavascriptExecutor) driver).executeScript(script, modifiedArgs);
      }

      @Override
      public Object executeAsyncScript(String script, Object... args) {
        activate();
        Object[] modifiedArgs = Stream.of(args)
          .map(arg -> arg instanceof Element ? ((Element) arg).getWebElement() : arg)
          .toArray();
        return ((JavascriptExecutor) driver).executeAsyncScript(script, modifiedArgs);
      }
    };
  }

  @Override
  public void invalidate() {
    throw new UnsupportedOperationException("Window cannot be invalidated");
  }
}
