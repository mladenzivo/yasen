package ru.stqa.yasen;

import org.openqa.selenium.WebElement;

class ElementListContext implements CanBeActivated, CanBeInvalidated {

  private final ElementList list;

  ElementListContext(ElementList list) {
    this.list = list;
  }

  WebElement get(int index) {
    return list.get(index).getWebElement();
  }

  @Override
  public void activate() {
    list.activate();
  }

  @Override
  public void invalidate() {
    list.invalidate();
  }
}
