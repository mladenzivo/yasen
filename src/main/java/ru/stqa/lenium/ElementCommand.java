package ru.stqa.lenium;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Function;
import java.util.function.Supplier;

class ElementCommand<R> implements Supplier<R> {

  private Element.WebElementSupplier elementSupplier;
  private Function<WebElement, R> command;

  ElementCommand(Element.WebElementSupplier elementSupplier, Function<WebElement, R> command) {
    this.elementSupplier = elementSupplier;
    this.command = command;
  }

  @Override
  public R get() {
    try {
      return new TimeBasedTrier<R>(5000).tryTo(() -> {
        try {
          return command.apply(elementSupplier.get());
        } catch (StaleElementReferenceException e) {
          elementSupplier.invalidate();
          throw e;
        }
      });
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}

