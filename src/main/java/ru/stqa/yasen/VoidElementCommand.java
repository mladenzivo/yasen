package ru.stqa.yasen;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.stqa.trier.TimeBasedTrier;

import java.util.function.Consumer;

class VoidElementCommand implements Runnable {

  private final Logger log = LoggerFactory.getLogger(VoidElementCommand.class);

  private final Element element;
  private final String commandName;
  private final Consumer<WebElement> command;

  VoidElementCommand(Element element, String commandName, Consumer<WebElement> command) {
    this.element = element;
    this.commandName = commandName;
    this.command = command;
  }

  @Override
  public void run() {
    try {
      log.debug("CMD ==> '{}'.{}()", element, commandName);
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
    } catch (Throwable e) {
      log.warn("WTF??!!", e);
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}

