package ru.stqa.yasen;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.LimitExceededException;
import ru.stqa.trier.TimeBasedTrier;

public class ElementInList implements Element {

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
  public void invalidate() {
    element = null;
    context.invalidate();
  }

  @Override
  public void activate() {
    context.activate();
  }

  @Override
  public WebElement getWebElement() {
    if (element == null) {
      log.debug("EL '{}' is to be found", this);
      try {
        element = new TimeBasedTrier<>(5000).tryTo(() -> context.get(index));
      } catch (LimitExceededException e) {
        log.debug("EL {} cannot be found: {}", this, e);
        throw new ElementLookupTimeoutException(e);
      } catch (Throwable e) {
        log.warn("WTF??!!", e);
        throw new RuntimeException(e);
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
