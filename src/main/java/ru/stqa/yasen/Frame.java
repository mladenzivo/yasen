package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.LimitExceededException;
import ru.stqa.trier.TimeBasedTrier;

import java.util.List;

public class Frame implements ElementContext {

  private final Logger log = LoggerFactory.getLogger(Frame.class);

  private final Window context;
  private final Finder finder;

  private WebElement frameElement;

  public Frame(Window context, Finder finder) {
    this.context = context;
    this.finder = finder;
  }

  public Element $(String cssSelector) {
    return new StandaloneElement(this, new SimpleFinder(By.cssSelector(cssSelector)));
  }

  public void activate() {
    context.activate();
    context.driver.switchTo().frame(getFrameElement());
  }

  public WebElement getFrameElement() {
    if (frameElement == null) {
      log.debug("EL '{}' is to be found", this);
      try {
        frameElement = new TimeBasedTrier<>(5000).tryTo(() -> {
          try {
            context.activate();
            return context.findFirstBy(finder);
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
    return frameElement;
  }

  @Override
  public WebElement findFirstBy(Finder finder) {
    activate();
    return finder.findFirstIn(context.driver);
  }

  @Override
  public List<WebElement> findAllBy(Finder finder) {
    activate();
    return finder.findAllIn(context.driver);
  }

  @Override
  public JavascriptExecutor getJavascriptExecutor() {
    return null;
  }

  @Override
  public void invalidate() {
    frameElement = null;
  }

  @Override
  public String toString() {
    return String.format("%s.$(%s)", context, finder);
  }
}
