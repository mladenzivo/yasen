package ru.stqa.yasen;

import org.openqa.selenium.*;

import java.lang.reflect.InvocationTargetException;

public abstract class Element {

  abstract WebElement getWebElement();

  abstract void invalidate();

  abstract void activate();

  public Element $(String cssSelector) {
    return new StandaloneElement(new ChildElementContext(this), By.cssSelector(cssSelector));
  }

  public Element $t(String text) {
    return new StandaloneElement(new ChildElementContext(this), By.xpath(String.format(".//*[normalize-space(.)='%s']", text)));
  }

  public ElementList $$(String cssSelector) {
    return new ElementListImpl(new ChildElementContext(this), By.cssSelector(cssSelector));
  }

  public <W extends ElementWrapper> W as(Class<W> cls) {
    try {
      return cls.getConstructor(Element.class).newInstance(this);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  public Element click() {
    new ElementCommand(this, "click", WebElement::click).run();
    return this;
  }

  public Element sendKeys(CharSequence text) {
    new ElementCommandWithParameter<>(this, "sendKeys", WebElement::sendKeys, text).run();
    return this;
  }

//  public void sendKeysSlowly(String text, int delay) {
//    WebElement element = context.findElement(locator);
//    Actions actions = new Actions(context.browser.driver);
//    for (char c : text.toCharArray()) {
//      actions.sendKeys(element, Character.toString(c)).pause(delay);
//    }
//    actions.perform();
//  }

  public boolean isVisible() {
    return new ElementInspector<>(this, "isVisible", WebElement::isDisplayed).get();
  }

  public boolean isSelected() {
    return new ElementInspector<>(this, "isSelected", WebElement::isSelected).get();
  }

  public String text() {
    return new ElementInspector<>(this, "text", WebElement::getText).get();
  }

  public String tagName() {
    return new ElementInspector<>(this, "tagName", WebElement::getTagName).get();
  }

  public String attribute(String name) {
    return new ElementInspectorWithParameter<>(this, "attribute", WebElement::getAttribute, name).get();
  }

  public String cssProperty(String name) {
    return new ElementInspectorWithParameter<>(this, "cssProperty", WebElement::getCssValue, name).get();
  }

  public Point location() {
    return new ElementInspector<>(this, "location", WebElement::getLocation).get();
  }

  public Dimension size() {
    return new ElementInspector<>(this, "size", WebElement::getSize).get();
  }

  public boolean isPresent() {
    invalidate();
    try {
      getWebElement();
      return true;
    } catch (ElementLookupTimeoutException ex) {
      return false;
    }
  }
}
