package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Supplier;

public class Element {

  private ElementContext context;
  private By locator;

  Element(ElementContext context, By locator) {
    this.context = context;
    this.locator = locator;
  }

  class WebElementSupplier implements Supplier<WebElement> {
    @Override
    public WebElement get() {
      try {
        return new TimeBasedTrier<WebElement>(5000).tryTo(() -> context.getElementBy(locator));
      } catch (Throwable e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }

    public void invalidate() {
      context.invalidate();
    }
  }

  public Element $(String cssSelector) {
    return new Element(new ChildElementContext(context), By.cssSelector(cssSelector));
  }

  public void clear() {
    new VoidElementCommand(new WebElementSupplier(), WebElement::clear).run();
  }

  public void click() {
    new VoidElementCommand(new WebElementSupplier(), WebElement::click).run();
  }

  public void sendKeys(String text) {
    new ElementCommand<Void>(new WebElementSupplier(), el -> { el.sendKeys(text); return null; }).get();
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
    return new ElementCommand<String>(new WebElementSupplier(), WebElement::getText).get();
  }
}
