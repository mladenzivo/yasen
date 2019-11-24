package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

abstract class AbstractElementImpl implements Element, ElementContext {

  @Override
  public Element $(String cssSelector) {
    return new StandaloneElement(this, new SimpleFinder(By.cssSelector(cssSelector)));
  }

  @Override
  public Element $t(String text) {
    return new StandaloneElement(this, new ByTextFinder(text));
  }

  @Override
  public Element $t(String tag, String text) {
    return new StandaloneElement(this, new ByTextFinder(tag, text));
  }

  @Override
  public ElementList $$(String cssSelector) {
    return new ElementListImpl(this, new SimpleFinder(By.cssSelector(cssSelector)));
  }

  @Override
  public void executeScript(String script, Object... args) {
    getJavascriptExecutor().executeScript(script, this, args);
  }

  @Override
  public WebElement findFirstBy(Finder finder) {
    return finder.findFirstIn(getWebElement());
  }

  @Override
  public List<WebElement> findAllBy(Finder finder) {
    return finder.findAllIn(getWebElement());
  }
}
