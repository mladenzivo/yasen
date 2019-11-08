package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

abstract class AbstractElementContext implements ElementContext {

  private final SearchContext searchContext;
  private final Runnable activator;
  private final Runnable invalidator;

  AbstractElementContext(SearchContext searchContext, Runnable activator, Runnable invalidator) {
    this.searchContext = searchContext;
    this.activator = activator;
    this.invalidator = invalidator;
  }

  @Override
  public WebElement findFirstBy(By locator) {
    activate();
    return searchContext.findElement(locator);
  }

  @Override
  public List<WebElement> findAllBy(By locator) {
    activate();
    return searchContext.findElements(locator);
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
