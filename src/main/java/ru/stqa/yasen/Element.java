package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public abstract class Element {

  abstract WebElement getWebElement();

  abstract void invalidate();

  abstract void activate();

  public Element $(String cssSelector) {
    return new StandaloneElement(new ChildElementContext(this), By.cssSelector(cssSelector));
  }

  public ElementList $$(String cssSelector) {
    return new ElementList(new ChildElementContext(this), By.cssSelector(cssSelector));
  }

  public void clear() {
    new VoidElementCommand(this, "clear" ,WebElement::clear).run();
  }

  public void click() {
    new VoidElementCommand(this, "click", WebElement::click).run();
  }

  public void sendKeys(CharSequence text) {
    new VoidElementCommandWithParameter<>(this, "sendKeys", WebElement::sendKeys, text).run();
  }

//  public void sendKeysSlowly(String text, int delay) {
//    WebElement element = context.findElement(locator);
//    Actions actions = new Actions(context.browser.driver);
//    for (char c : text.toCharArray()) {
//      actions.sendKeys(element, Character.toString(c)).pause(delay);
//    }
//    actions.perform();
//  }

  public String text() {
    return new ElementCommand<>(this, WebElement::getText).get();
  }

  public String value() {
    return new ElementCommand<>(this, el -> el.getAttribute("value")).get();
  }
}
