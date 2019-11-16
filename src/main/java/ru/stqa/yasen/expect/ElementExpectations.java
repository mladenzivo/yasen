package ru.stqa.yasen.expect;

import org.openqa.selenium.support.ui.FluentWait;
import ru.stqa.yasen.Element;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Consumer;

public class ElementExpectations {

  private Element element;
  private Duration duration;

  ElementExpectations(Element element, Duration duration) {
    this.element = element;
    this.duration = duration;
  }

  public ElementExpectations in(Duration duration) {
    return new ElementExpectations(element, duration);
  }

  public ElementExpectations hasText(String text) {
    new FluentWait<>(element).withTimeout(duration).until(e -> Objects.equals(e.text(), text));
    return this;
  }

  public ElementExpectations hasTextContaining(String fragment) {
    new FluentWait<>(element).withTimeout(duration).until(e -> e.text().contains(fragment));
    return this;
  }

  public ElementExpectations hasAttribute(String name) {
    new FluentWait<>(element).withTimeout(duration).until(e -> e.attribute(name) != null);
    return this;
  }

  public ElementExpectations hasAttribute(String name, String value) {
    new FluentWait<>(element).withTimeout(duration).until(e -> Objects.equals(e.attribute(name), value));
    return this;
  }

  public void then(Consumer<Element> consumer) {
    consumer.accept(element);
  }
}
