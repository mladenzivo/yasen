package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.trier.LimitExceededException;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Supplier;

public class Element {

  private ElementContext context;
  private By locator;

  Element(ElementContext context, By locator) {
    this.context = context;
    this.locator = locator;
  }

  private class WebElementSupplier implements Supplier<WebElement> {
    @Override
    public WebElement get() {
      try {
        return new TimeBasedTrier<WebElement>(5000).tryTo(() -> context.getElementBy(locator));
      } catch (LimitExceededException | InterruptedException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
  }

  private class EnclosedElementContext implements ElementContext {
    private WebElement element;

    @Override
    public WebElement getElementBy(By locator) {
      if (element == null) {
        element = context.getElementBy(locator);
      }
      return element;
    }
  }

  public Element $(String cssSelector) {
    return new Element(new EnclosedElementContext(), By.cssSelector(cssSelector));
  }

  public void clear() {
    new ElementCommand<Void>(new WebElementSupplier(), WebElement::clear).get();
  }

  public void click() {
    new ElementCommand<Void>(new WebElementSupplier(), WebElement::click).get();
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
