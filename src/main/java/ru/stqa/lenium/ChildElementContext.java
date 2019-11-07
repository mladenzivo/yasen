package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

class ChildElementContext implements ElementContext {

  private ElementContext parent;
  private WebElement element;

  ChildElementContext(ElementContext parent) {
    this.parent = parent;
  }

  @Override
  public WebElement getElementBy(By locator) {
    if (element == null) {
      element = parent.getElementBy(locator);
    }
    return element;
  }

  @Override
  public void invalidate() {
    element = null;
  }
}
