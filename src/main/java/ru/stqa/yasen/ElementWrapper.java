package ru.stqa.yasen;

import org.openqa.selenium.WebElement;

public abstract class ElementWrapper extends Element {

  protected final Element element;

  public ElementWrapper(Element element) {
    this.element = element;
  }

  @Override
  WebElement getWebElement() {
    return element.getWebElement();
  }

  @Override
  void invalidate() {
    element.invalidate();
  }

  @Override
  void activate() {
    element.activate();
  }
}
