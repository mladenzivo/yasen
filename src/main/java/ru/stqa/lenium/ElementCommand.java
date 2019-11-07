package ru.stqa.lenium;

import org.openqa.selenium.WebElement;

import java.util.function.Consumer;
import java.util.function.Supplier;

class ElementCommand implements Runnable {

  private Supplier<WebElement> elementSupplier;
  private Consumer<WebElement> command;

  ElementCommand(Supplier<WebElement> elementSupplier, Consumer<WebElement> command) {
    this.elementSupplier = elementSupplier;
    this.command = command;
  }

  @Override
  public void run() {
    command.accept(elementSupplier.get());
  }
}

