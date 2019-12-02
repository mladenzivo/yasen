package ru.stqa.yasen.expect;

import ru.stqa.yasen.Browser;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.ElementList;
import ru.stqa.yasen.Window;

import java.time.Duration;

public class Expect {

  private static final Duration DEFAULT_WAIT_TIME = Duration.ofSeconds(5);

  private final Duration duration;

  public Expect() {
    this(DEFAULT_WAIT_TIME);
  }

  public Expect(Duration duration) {
    this.duration = duration;
  }

  public BrowserExpectations that(Browser browser) {
    return new BrowserExpectations(browser, duration);
  }

  public WindowExpectations that(Window window) {
    return new WindowExpectations(window, duration);
  }

  public ElementExpectations that(Element element) {
    return new ElementExpectations(element, duration);
  }

  public ElementListExpectations that(ElementList elements
  ) {
    return new ElementListExpectations(elements, duration);
  }
}
