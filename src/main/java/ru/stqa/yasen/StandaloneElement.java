package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import ru.stqa.trier.TimeBasedTrier;

class StandaloneElement extends Element {

  private final ElementContext context;
  private final By locator;

  private WebElement element;

  StandaloneElement(ElementContext context, By locator) {
    this.context = context;
    this.locator = locator;
  }

  @Override
  void invalidate() {
    element = null;
  }

  @Override
  void activate() {
    context.activate();
  }

  @Override
  WebElement getWebElement() {
    if (element == null) {
      try {
        element = new TimeBasedTrier<>(500000).tryTo(() -> {
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
}
