package ru.stqa.yasen;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.LimitExceededException;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Consumer;

public class ElementCommand implements Runnable {

  private final Logger log = LoggerFactory.getLogger(ElementCommand.class);

  private final Element element;
  private final String commandName;
  private final Consumer<WebElement> command;

  public ElementCommand(Element element, String commandName, Consumer<WebElement> command) {
    this.element = element;
    this.commandName = commandName;
    this.command = command;
  }

  @Override
  public void run() {
    log.debug("CMD ==> '{}'.{}()", element, commandName);
    try {
      new TimeBasedTrier(5000).tryTo(() -> {
        try {
          WebElement target = element.getWebElement();
          log.debug("# '{}'.{}()", element, commandName);
          command.accept(target);
          log.debug("CMD <== '{}'.{}() - OK", element, commandName);
        } catch (StaleElementReferenceException e) {
          log.debug("Oops! Element '{}' has gone and should be recovered!", element);
          element.invalidate();
          throw e;
        }
      });
    } catch (LimitExceededException e) {
      throw new OperationTimeoutException(e);
    } catch (Throwable e) {
      log.warn("WTF??!!", e);
      throw new RuntimeException(e);
    }
  }
}

