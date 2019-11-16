package ru.stqa.yasen.expect;

import org.openqa.selenium.support.ui.FluentWait;
import ru.stqa.yasen.Window;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;

public class WindowExpectations {

  private Window window;
  private Duration duration;

  WindowExpectations(Window window, Duration duration) {
    this.window = window;
    this.duration = duration;
  }

  public WindowExpectations in(Duration duration) {
    return new WindowExpectations(window, duration);
  }

  public WindowExpectations hasUrl(String url) {
    new FluentWait<>(window).withTimeout(duration).until(w -> Objects.equals(w.url(), url));
    return this;
  }

  public WindowExpectations hasTitle(String title) {
    new FluentWait<>(window).withTimeout(duration).until(w -> Objects.equals(w.title(), title));
    return this;
  }

  public WindowExpectations hasTitleContaining(String fragment) {
    new FluentWait<>(window).withTimeout(duration).until(w -> w.title().contains(fragment));
    return this;
  }

  public void then(Consumer<Window> consumer) {
    consumer.accept(window);
  }
}
