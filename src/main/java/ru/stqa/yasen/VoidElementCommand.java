package ru.stqa.yasen;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Consumer;

class VoidElementCommand implements Runnable {

  private final Element element;
  private final Consumer<WebElement> command;

  VoidElementCommand(Element element, Consumer<WebElement> command) {
    this.element = element;
    this.command = command;
  }

  @Override
  public void run() {
    try {
      new TimeBasedTrier(5000).tryTo(() -> {
        try {
          command.accept(element.getWebElement());
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

