package ru.stqa.yasen;

public class CheckBox extends Input {

  public CheckBox(Element element) {
    super(element);
  }

  public boolean isChecked() {
    return getWebElement().isSelected();
  }
}
