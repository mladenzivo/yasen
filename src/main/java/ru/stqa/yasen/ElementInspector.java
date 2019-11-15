package ru.stqa.yasen;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Function;
import java.util.function.Supplier;

class ElementInspector<R> implements Supplier<R> {

  private final Logger log = LoggerFactory.getLogger(ElementInspector.class);

  private final Element element;
  private final String commandName;
  private final Function<WebElement, R> command;

  ElementInspector(Element element, String commandName, Function<WebElement, R> command) {
    this.element = element;
    this.commandName = commandName;
    this.command = command;
  }

  @Override
  public R get() {
    log.debug("CMD ==> '{}'.{}()", element, commandName);
    try {
      return new TimeBasedTrier<R>(5000).ignoring(res -> false).tryTo(() -> {
        try {
          WebElement target = element.getWebElement();
          log.debug("# '{}'.{}()", element, commandName);
          R result = command.apply(target);
          log.debug("CMD <== '{}'.{}() = '{}'", element, commandName, result);
          return result;
        } catch (StaleElementReferenceException e) {
          log.debug("Oops! Element '{}' has gone and should be recovered!", element);
          element.invalidate();
          throw e;
        }
      });
    } catch (Throwable e) {
      log.warn("WTF??!!", e);
      throw new RuntimeException(e);
    }
  }
}

