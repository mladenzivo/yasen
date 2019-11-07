package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

class TopLevelElementContext implements ElementContext {

  private Window window;
  private WebElement element;

  TopLevelElementContext(Window window) {
    this.window = window;
  }

  @Override
  public WebElement getElementBy(By locator) {
    if (element == null) {
      window.activate();
      element = window.driver.findElement(locator);
    }
    return element;
  }

  @Override
  public void invalidate() {
    element = null;
  }
}
