package ru.stqa.lenium;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Consumer;

class VoidElementCommand implements Runnable {

  private Element.WebElementSupplier elementSupplier;
  private Consumer<WebElement> command;

  VoidElementCommand(Element.WebElementSupplier elementSupplier, Consumer<WebElement> command) {
    this.elementSupplier = elementSupplier;
    this.command = command;
  }

  @Override
  public void run() {
    try {
      new TimeBasedTrier(5000).tryTo(() -> {
        try {
          command.accept(elementSupplier.get());
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

