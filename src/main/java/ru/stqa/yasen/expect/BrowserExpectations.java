package ru.stqa.yasen.expect;

import org.openqa.selenium.support.ui.FluentWait;
import ru.stqa.yasen.Browser;

import java.time.Duration;
import java.util.function.Consumer;

public class BrowserExpectations {

  private final Browser browser;
  private final Duration duration;

  BrowserExpectations(Browser browser, Duration duration) {
    this.browser = browser;
    this.duration = duration;
  }

  public BrowserExpectations in(Duration duration) {
    return new BrowserExpectations(browser, duration);
  }

  public BrowserExpectations hasNumberOfWindows(int num) {
    new FluentWait<>(browser).withTimeout(duration).until(b -> b.windows().size() == num);
    return this;
  }

  public BrowserExpectations hasNumberOfWindowsGreaterThan(int num) {
    new FluentWait<>(browser).withTimeout(duration).until(b -> b.windows().size() > num);
    return this;
  }

  public BrowserExpectations hasNumberOfWindowsLessThan(int num) {
    new FluentWait<>(browser).withTimeout(duration).until(b -> b.windows().size() < num);
    return this;
  }

  public void then(Consumer<Browser> consumer) {
    consumer.accept(browser);
  }
}
