package ru.stqa.yasen;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.TimeBasedTrier;

class ElementInList extends Element {

  private final Logger log = LoggerFactory.getLogger(ElementInList.class);

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
      log.debug("EL '{}' is to be found", this);
      try {
        element = new TimeBasedTrier<>(5000).tryTo(() -> context.get(index));
      } catch (Throwable e) {
        log.warn("EL {} cannot be found: {}", this, e);
      }
      log.debug("EL '{}' has been found", this);
    }
    return element;
  }

  @Override
  public String toString() {
    return String.format("%s[%s]", context, index);
  }
}
