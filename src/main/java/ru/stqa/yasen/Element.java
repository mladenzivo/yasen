package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;

public interface Element extends CanBeActivated, CanBeInvalidated {

  WebElement getWebElement();

  default Element $(String cssSelector) {
    return new StandaloneElement(new ChildElementContext(this), By.cssSelector(cssSelector));
  }

  default Element $t(String text) {
    return new StandaloneElement(new ChildElementContext(this), By.xpath(String.format(".//*[normalize-space(.)='%s']", text)));
  }

  default ElementList $$(String cssSelector) {
    return new ElementListImpl(new ChildElementContext(this), By.cssSelector(cssSelector));
  }

  default <W extends ElementWrapper> W as(Class<W> cls) {
    try {
      return cls.getConstructor(Element.class).newInstance(this);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  default Element click() {
    new ElementCommand(this, "click", WebElement::click).run();
    return this;
  }

  default Element sendKeys(CharSequence text) {
    new ElementCommandWithParameter<>(this, "sendKeys", WebElement::sendKeys, text).run();
    return this;
  }

//  default void sendKeysSlowly(String text, int delay) {
//    WebElement element = context.findElement(locator);
//    Actions actions = new Actions(context.browser.driver);
//    for (char c : text.toCharArray()) {
//      actions.sendKeys(element, Character.toString(c)).pause(delay);
//    }
//    actions.perform();
//  }

  default boolean isVisible() {
    return new ElementInspector<>(this, "isVisible", WebElement::isDisplayed).get();
  }

  default boolean isSelected() {
    return new ElementInspector<>(this, "isSelected", WebElement::isSelected).get();
  }

  default String text() {
    return new ElementInspector<>(this, "text", WebElement::getText).get();
  }

  default String tagName() {
    return new ElementInspector<>(this, "tagName", WebElement::getTagName).get();
  }

  default String attribute(String name) {
    return new ElementInspectorWithParameter<>(this, "attribute", WebElement::getAttribute, name).get();
  }

  default String cssProperty(String name) {
    return new ElementInspectorWithParameter<>(this, "cssProperty", WebElement::getCssValue, name).get();
  }

  default Point location() {
    return new ElementInspector<>(this, "location", WebElement::getLocation).get();
  }

  default Dimension size() {
    return new ElementInspector<>(this, "size", WebElement::getSize).get();
  }

  default boolean isPresent() {
    invalidate();
    try {
      getWebElement();
      return true;
    } catch (ElementLookupTimeoutException ex) {
      return false;
    }
  }
}
