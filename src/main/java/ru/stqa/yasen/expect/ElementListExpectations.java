package ru.stqa.yasen.expect;

import org.openqa.selenium.support.ui.FluentWait;
import ru.stqa.yasen.ElementList;

import java.time.Duration;
import java.util.function.Consumer;

public class ElementListExpectations {

  private ElementList elements;
  private Duration duration;

  ElementListExpectations(ElementList elements, Duration duration) {
    this.elements = elements;
    this.duration = duration;
  }

  public ElementListExpectations in(Duration duration) {
    return new ElementListExpectations(elements, duration);
  }

  public ElementListExpectations hasSize(int size) {
    new FluentWait<>(elements).withTimeout(duration).until(l -> l.size() == size);
    return this;
  }

  public void then(Consumer<ElementList> consumer) {
    consumer.accept(elements);
  }
}
