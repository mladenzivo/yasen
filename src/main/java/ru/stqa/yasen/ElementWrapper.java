package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class ElementWrapper implements Element, ElementContext {

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

  @Override
  public WebElement findFirstBy(By locator) {
    return getWebElement().findElement(locator);
  }

  @Override
  public List<WebElement> findAllBy(By locator) {
    return getWebElement().findElements(locator);
  }

  @Override
  public Element $(String cssSelector) {
    return new StandaloneElement(this, By.cssSelector(cssSelector));
  }

  @Override
  public Element $t(String text) {
    return new StandaloneElement(this, By.xpath(String.format(".//*[normalize-space(.)='%s']", text)));
  }

  @Override
  public ElementList $$(String cssSelector) {
    return new ElementListImpl(this, By.cssSelector(cssSelector));
  }
}
