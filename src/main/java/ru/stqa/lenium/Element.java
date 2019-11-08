package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import ru.stqa.trier.TimeBasedTrier;

public class Element {

  private final ElementContext context;
  private final By locator;

  private WebElement element;

  Element(ElementContext context, By locator) {
    this.context = context;
    this.locator = locator;
  }

  WebElement getWebElement() {
    if (element == null) {
      try {
        element = new TimeBasedTrier<>(5000).tryTo(() -> {
          try {
            return context.findFirstBy(locator);
          } catch (StaleElementReferenceException e) {
            context.invalidate();
            throw e;
          }
        });
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }
    return element;
  }

  void invalidate() {
    element = null;
  }

  void activate() {
    context.activate();
  }

  public Element $(String cssSelector) {
    return new Element(new ChildElementContext(this), By.cssSelector(cssSelector));
  }

  public ElementList $$(String cssSelector) {
    return new ElementList(new ChildElementContext(this), By.cssSelector(cssSelector));
  }

  public void clear() {
    new VoidElementCommand(this, WebElement::clear).run();
  }

  public void click() {
    new VoidElementCommand(this, WebElement::click).run();
  }

  public void sendKeys(String text) {
    new ElementCommand<Void>(this, el -> { el.sendKeys(text); return null; }).get();
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
    return new ElementCommand<String>(this, WebElement::getText).get();
  }
}
