package ru.stqa.lenium;

import org.openqa.selenium.WebElement;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

class ElementCommand<R> implements Supplier<R> {

  private Supplier<WebElement> elementSupplier;
  private Function<WebElement, R> command;

  ElementCommand(Supplier<WebElement> elementSupplier, Consumer<WebElement> command) {
    this.elementSupplier = elementSupplier;
    this.command = el -> { command.accept(el); return null; };
  }

  ElementCommand(Supplier<WebElement> elementSupplier, Function<WebElement, R> command) {
    this.elementSupplier = elementSupplier;
    this.command = command;
  }

  @Override
  public R get() {
    return command.apply(elementSupplier.get());
  }
}

