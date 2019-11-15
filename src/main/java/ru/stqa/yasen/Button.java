package ru.stqa.yasen;

public class Button extends Input {

  public Button(Element element) {
    super(element);
  }

  public String text() {
    return value();
  }
}
