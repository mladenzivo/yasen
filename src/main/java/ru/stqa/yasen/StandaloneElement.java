package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.TimeBasedTrier;

class StandaloneElement extends Element {

  private final Logger log = LoggerFactory.getLogger(StandaloneElement.class);

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
      log.debug("EL '{}' is to be found", this);
      try {
        element = new TimeBasedTrier<>(500000).tryTo(() -> {
          try {
            return context.findFirstBy(locator);
          } catch (StaleElementReferenceException e) {
            log.debug("Oops! Context for '{}' has gone and should be recovered!", this);
            context.invalidate();
            throw e;
          }
        });
        log.debug("EL '{}' has been found", this);
      } catch (Throwable e) {
        log.warn("EL {} cannot be found: {}", this, e);
      }
    } else {
      log.debug("EL '{}' has already been found in past", this);
    }
    return element;
  }

  @Override
  public String toString() {
    return String.format("%s.$(%s)", context, locator);
  }
}
