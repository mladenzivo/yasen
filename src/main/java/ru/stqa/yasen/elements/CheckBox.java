package ru.stqa.yasen.elements;

import ru.stqa.yasen.Element;

public class CheckBox extends Input {

  public CheckBox(Element element) {
    super(element);
  }

  public boolean isChecked() {
    return getWebElement().isSelected();
  }

  public void check() {
    if (! isChecked()) {
      click();
    }
  }

  public void uncheck() {
    if (isChecked()) {
      click();
    }
  }
}
