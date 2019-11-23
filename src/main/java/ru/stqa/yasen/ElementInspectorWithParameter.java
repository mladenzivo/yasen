package ru.stqa.yasen;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ElementInspectorWithParameter<T, R> implements Supplier<R> {

  private final Logger log = LoggerFactory.getLogger(ElementInspectorWithParameter.class);

  private final Element element;
  private final String commandName;
  private final BiFunction<WebElement, T, R> command;
  private final T parameter;

  ElementInspectorWithParameter(Element element, String commandName, BiFunction<WebElement, T, R> command, T parameter) {
    this.element = element;
    this.commandName = commandName;
    this.command = command;
    this.parameter = parameter;
  }

  @Override
  public R get() {
    log.debug("CMD ==> '{}'.{}('{}')", element, commandName, parameter);
    try {
      return new TimeBasedTrier<R>(5000).ignoring(res -> false).tryTo(() -> {
        try {
          WebElement target = element.getWebElement();
          log.debug("# '{}'.{}('{}')", element, commandName, parameter);
          R result = command.apply(target, parameter);
          log.debug("CMD <== '{}'.{}('{}') = '{}'", element, commandName, parameter, result);
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

