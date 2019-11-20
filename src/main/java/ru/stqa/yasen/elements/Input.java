package ru.stqa.yasen.elements;

import org.openqa.selenium.WebElement;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.ElementCommand;
import ru.stqa.yasen.ElementWrapper;

public class Input extends ElementWrapper {

  public Input(Element element) {
    super(element);
  }

  public void clear() {
    new ElementCommand(this, "clear" , WebElement::clear).run();
  }

  public String value() {
    return attribute("value");
  }
}
