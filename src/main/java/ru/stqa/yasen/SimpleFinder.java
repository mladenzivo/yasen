package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

class SimpleFinder implements Finder {

  private final By locator;

  SimpleFinder(By locator) {
    this.locator = locator;
  }

  @Override
  public WebElement findFirstIn(SearchContext searchContext) {
    return searchContext.findElement(locator);
  }

  @Override
  public List<WebElement> findAllIn(SearchContext searchContext) {
    return searchContext.findElements(locator);
  }

  @Override
  public String toString() {
    return locator.toString();
  }
}
