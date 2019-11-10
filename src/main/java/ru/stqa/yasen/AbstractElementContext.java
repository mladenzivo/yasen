package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Supplier;

abstract class AbstractElementContext implements ElementContext {

  private final Supplier<SearchContext> searchContextSupplier;
  private final Runnable activator;
  private final Runnable invalidator;

  AbstractElementContext(Supplier<SearchContext> searchContextSupplier, Runnable activator, Runnable invalidator) {
    this.searchContextSupplier = searchContextSupplier;
    this.activator = activator;
    this.invalidator = invalidator;
  }

  @Override
  public WebElement findFirstBy(By locator) {
    activate();
    return searchContextSupplier.get().findElement(locator);
  }

  @Override
  public List<WebElement> findAllBy(By locator) {
    activate();
    return searchContextSupplier.get().findElements(locator);
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
