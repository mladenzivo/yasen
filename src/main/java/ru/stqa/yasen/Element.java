package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

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
    return new ElementList(new ChildElementContext(this), By.cssSelector(cssSelector));
  }

  public <W extends ElementWrapper> W as(Class<W> cls) {
    try {
      return cls.getConstructor(Element.class).newInstance(this);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  public void click() {
    new ElementCommand(this, "click", WebElement::click).run();
  }

  public void sendKeys(CharSequence text) {
    new ElementCommandWithParameter<>(this, "sendKeys", WebElement::sendKeys, text).run();
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

  public String text() {
    return new ElementInspector<>(this, "text", WebElement::getText).get();
  }

  public String attribute(String name) {
    return new ElementInspectorWithParameter<>(this, "attribute", WebElement::getAttribute, name).get();
  }
}
