package ru.stqa.yasen;

import org.openqa.selenium.WebElement;
import ru.stqa.trier.TimeBasedTrier;

class ElementInList extends Element {

  private final ElementListContext context;
  WebElement element;
  private final int index;

  ElementInList(ElementListContext context, WebElement initial, int index) {
    this.context = context;
    this.element = initial;
    this.index = index;
  }

  @Override
  void invalidate() {
    element = null;
    context.invalidate();
  }

  @Override
  void activate() {
    context.activate();
  }

  @Override
  WebElement getWebElement() {
    if (element == null) {
      try {
        element = new TimeBasedTrier<>(5000).tryTo(() -> context.get(index));
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }
    return element;
  }
}
