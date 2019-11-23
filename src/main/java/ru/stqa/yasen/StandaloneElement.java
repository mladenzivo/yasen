package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.LimitExceededException;
import ru.stqa.trier.TimeBasedTrier;

import java.util.List;

class StandaloneElement implements Element, ElementContext {

  private final Logger log = LoggerFactory.getLogger(StandaloneElement.class);

  private final ElementContext context;
  private final By locator;

  private WebElement element;

  StandaloneElement(ElementContext context, By locator) {
    this.context = context;
    this.locator = locator;
  }

  @Override
  public void invalidate() {
    element = null;
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
        element = new TimeBasedTrier<>(5000).tryTo(() -> {
          try {
            return context.findFirstBy(locator);
          } catch (StaleElementReferenceException e) {
            log.debug("Oops! Context for '{}' has gone and should be recovered!", this);
            context.invalidate();
            throw e;
          }
        });
        log.debug("EL '{}' has been found", this);
      } catch (LimitExceededException e) {
        log.debug("EL {} cannot be found:", this, e);
        throw new ElementLookupTimeoutException(e);
      } catch (Throwable e) {
        log.warn("WTF??!!", e);
        throw new RuntimeException(e);
      }
    } else {
      log.debug("EL '{}' has already been found in past", this);
    }
    return element;
  }

  @Override
  public WebElement findFirstBy(By locator) {
    return getWebElement().findElement(locator);
  }

  @Override
  public List<WebElement> findAllBy(By locator) {
    return getWebElement().findElements(locator);
  }

  @Override
  public Element $(String cssSelector) {
    return new StandaloneElement(this, By.cssSelector(cssSelector));
  }

  @Override
  public Element $t(String text) {
    return new StandaloneElement(this, By.xpath(String.format(".//*[normalize-space(.)='%s']", text)));
  }

  @Override
  public ElementList $$(String cssSelector) {
    return new ElementListImpl(this, By.cssSelector(cssSelector));
  }

  @Override
  public String toString() {
    return String.format("%s.$(%s)", context, locator);
  }
}
