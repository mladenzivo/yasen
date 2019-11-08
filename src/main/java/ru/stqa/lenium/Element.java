package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Element {

  private final WebElementSupplier elementSupplier;

  Element(ElementContext context, By locator) {
    elementSupplier = new WebElementSupplier(context, locator);
  }

  public Element $(String cssSelector) {
    return new Element(new ChildElementContext(elementSupplier), By.cssSelector(cssSelector));
  }

  public void clear() {
    new VoidElementCommand(elementSupplier, WebElement::clear).run();
  }

  public void click() {
    new VoidElementCommand(elementSupplier, WebElement::click).run();
  }

  public void sendKeys(String text) {
    new ElementCommand<Void>(elementSupplier, el -> { el.sendKeys(text); return null; }).get();
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
    return new ElementCommand<String>(elementSupplier, WebElement::getText).get();
  }
}
