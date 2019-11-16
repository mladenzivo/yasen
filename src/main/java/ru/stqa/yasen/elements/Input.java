package ru.stqa.yasen.elements;

import ru.stqa.yasen.Element;
import ru.stqa.yasen.ElementWrapper;

public class Input extends ElementWrapper {

  public Input(Element element) {
    super(element);
  }

  public String value() {
    return attribute("value");
  }
}
