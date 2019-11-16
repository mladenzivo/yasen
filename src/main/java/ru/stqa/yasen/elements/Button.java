package ru.stqa.yasen.elements;

import ru.stqa.yasen.Element;

public class Button extends Input {

  public Button(Element element) {
    super(element);
  }

  public String text() {
    return value();
  }
}
