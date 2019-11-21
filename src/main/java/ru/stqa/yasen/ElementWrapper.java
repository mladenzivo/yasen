package ru.stqa.yasen;

import org.openqa.selenium.WebElement;

public abstract class ElementWrapper implements Element {

  protected final Element element;

  public ElementWrapper(Element element) {
    this.element = element;
  }

  @Override
  public WebElement getWebElement() {
    return element.getWebElement();
  }

  @Override
  public void invalidate() {
    element.invalidate();
  }

  @Override
  public void activate() {
    element.activate();
  }
}
