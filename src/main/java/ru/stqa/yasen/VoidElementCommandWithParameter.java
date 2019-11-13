package ru.stqa.yasen;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

class VoidElementCommandWithParameter<T> implements Runnable {

  private final Logger log = LoggerFactory.getLogger(VoidElementCommandWithParameter.class);

  private final Element element;
  private final String commandName;
  private final BiConsumer<WebElement, T> command;
  private T parameter;

  VoidElementCommandWithParameter(Element element, String commandName, BiConsumer<WebElement, T> command, T parameter) {
    this.element = element;
    this.commandName = commandName;
    this.command = command;
    this.parameter = parameter;
  }

  @Override
  public void run() {
    try {
      log.debug("CMD ==> '{}'.{}('{}')", element, commandName, parameter);
      new TimeBasedTrier(5000).tryTo(() -> {
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
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}

