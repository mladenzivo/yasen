package ru.stqa.yasen;

public class Input extends ElementWrapper {

  public Input(Element element) {
    super(element);
  }

  public String value() {
    return attribute("value");
  }
}
