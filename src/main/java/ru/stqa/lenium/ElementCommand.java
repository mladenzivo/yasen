package ru.stqa.lenium;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Function;
import java.util.function.Supplier;

class ElementCommand<R> implements Supplier<R> {

  private final Element element;
  private final Function<WebElement, R> command;

  ElementCommand(Element element, Function<WebElement, R> command) {
    this.element = element;
    this.command = command;
  }

  @Override
  public R get() {
    try {
      return new TimeBasedTrier<R>(5000).tryTo(() -> {
        try {
          return command.apply(element.getWebElement());
        } catch (StaleElementReferenceException e) {
          element.invalidate();
          throw e;
        }
      });
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}

