package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.stqa.trier.TimeBasedTrier;

class WebElementSupplier {

  private final ElementContext context;
  private final By locator;

  WebElementSupplier(ElementContext context, By locator) {
    this.context = context;
    this.locator = locator;
  }

  ElementContext getContext() {
    return context;
  }

  WebElement getWebElement() {
    try {
      return new TimeBasedTrier<WebElement>(5000).tryTo(() -> context.getElementBy(locator));
    } catch (Throwable e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }



  void invalidate() {
    context.invalidate();
  }
}
