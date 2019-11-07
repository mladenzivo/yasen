package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.function.Function;

abstract class AbstractElementContext implements ElementContext {

  private final Function<By, WebElement> finder;
  private final Runnable activator;

  private WebElement element;

  AbstractElementContext(Function<By, WebElement> finder, Runnable activator) {
    this.finder = finder;
    this.activator = activator;
  }

  @Override
  public WebElement getElementBy(By locator) {
    if (element == null) {
      activate();
      element = finder.apply(locator);
    }
    return element;
  }

  @Override
  public void activate() {
    activator.run();
  }

  @Override
  public void invalidate() {
    element = null;
  }
}
