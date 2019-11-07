package ru.stqa.lenium;

import org.openqa.selenium.WebElement;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Consumer;
import java.util.function.Supplier;

class VoidElementCommand implements Runnable {

  private Supplier<WebElement> elementSupplier;
  private Consumer<WebElement> command;

  VoidElementCommand(Supplier<WebElement> elementSupplier, Consumer<WebElement> command) {
    this.elementSupplier = elementSupplier;
    this.command = command;
  }

  @Override
  public void run() {
    try {
      new TimeBasedTrier(60000).tryTo(() -> command.accept(elementSupplier.get()));
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}

