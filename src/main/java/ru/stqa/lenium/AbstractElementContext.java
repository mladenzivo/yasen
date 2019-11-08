package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.function.Function;

abstract class AbstractElementContext implements ElementContext {

  private final Function<By, WebElement> finder;
  private final Runnable activator;
  private final Runnable invalidator;

  AbstractElementContext(Function<By, WebElement> finder, Runnable activator, Runnable invalidator) {
    this.finder = finder;
    this.activator = activator;
    this.invalidator = invalidator;
  }

  @Override
  public WebElement findElementBy(By locator) {
    activate();
    return finder.apply(locator);
  }

  @Override
  public void activate() {
    activator.run();
  }

  @Override
  public void invalidate() {
    invalidator.run();
  }
}
