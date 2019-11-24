package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

abstract class AbstractElementImpl implements Element, ElementContext {

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

  @Override
  public void executeScript(String script, Object... args) {
    getJavascriptExecutor().executeScript(script, this, args);
  }

  @Override
  public WebElement findFirstBy(By locator) {
    return getWebElement().findElement(locator);
  }

  @Override
  public List<WebElement> findAllBy(By locator) {
    return getWebElement().findElements(locator);
  }
}
