package ru.stqa.yasen;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.BiConsumer;

public class ElementCommandWithParameter<T> implements Runnable {

  private final Logger log = LoggerFactory.getLogger(ElementCommandWithParameter.class);

  private final Element element;
  private final String commandName;
  private final BiConsumer<WebElement, T> command;
  private final T parameter;

  ElementCommandWithParameter(Element element, String commandName, BiConsumer<WebElement, T> command, T parameter) {
    this.element = element;
    this.commandName = commandName;
    this.command = command;
    this.parameter = parameter;
  }

  @Override
  public void run() {
    log.debug("CMD ==> '{}'.{}('{}')", element, commandName, parameter);
    try {
      new TimeBasedTrier<>(5000).tryTo(() -> {
        try {
          WebElement target = element.getWebElement();
          log.debug("# '{}'.{}('{}')", element, commandName, parameter);
          command.accept(target, parameter);
          log.debug("CMD <== '{}'.{}('{}') - OK", element, commandName, parameter);
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

