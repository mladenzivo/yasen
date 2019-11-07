package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.trier.LimitExceededException;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Supplier;

public class Element implements Supplier<WebElement> {

  private ElementContext context;
  private By locator;

  Element(ElementContext context, By locator) {
    this.context = context;
    this.locator = locator;
  }

  @Override
  public WebElement get() {
    try {
      return new TimeBasedTrier<WebElement>(5000).tryTo(() -> context.getElementBy(locator));
    } catch (LimitExceededException | InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public void clear() {
    new ElementCommand(this, WebElement::clear).run();
  }

  public void click() {
    new ElementCommand(this, WebElement::click).run();
  }

  public void sendKeys(String text) {
    new ElementCommand(this, el -> el.sendKeys(text)).run();
  }

//  public void sendKeysSlowly(String text, int delay) {
//    WebElement element = context.findElement(locator);
//    Actions actions = new Actions(context.browser.driver);
//    for (char c : text.toCharArray()) {
//      actions.sendKeys(element, Character.toString(c)).pause(delay);
//    }
//    actions.perform();
//  }

//  public String text() {
//    return new ElementCommand(this, WebElement::getText).get();
//  }
}
