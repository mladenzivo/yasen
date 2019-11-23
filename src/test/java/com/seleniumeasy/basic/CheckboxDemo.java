package com.seleniumeasy.basic;

import com.seleniumeasy.SeleniumEasyTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stqa.yasen.elements.Button;
import ru.stqa.yasen.elements.CheckBox;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.ElementList;

import static org.assertj.core.api.Assertions.assertThat;

class CheckboxDemo extends SeleniumEasyTestBase {

  @BeforeEach
  void openTargetPage() {
    openMenu("Input Forms", "Checkbox Demo");
  }

  @Test
  void singleCheckboxDemo() {
    CheckBox checkbox = mainWin.$("#isAgeSelected").as(CheckBox::new);
    Element txtbox = mainWin.$("#txtAge");

    checkbox.click();
    expect.that(txtbox).hasText("Success - Check box is checked");

    checkbox.check();
    expect.that(txtbox).hasText("Success - Check box is checked");

    checkbox.uncheck();
    expect.that(txtbox).hasText("");

    checkbox.uncheck();
    expect.that(txtbox).hasText("");

    checkbox.check();
    expect.that(txtbox).hasText("Success - Check box is checked");
  }

  @Test
  void multipleCheckboxDemo() {
    ElementList checkboxes = mainWin.$$(".cb1-element");
    Button button = mainWin.$("#check1").as(Button::new);

    button.click();

    checkboxes.forEach(chb ->
      assertThat(chb.as(CheckBox::new).isChecked()).isTrue());
    expect.that(button).hasText("Uncheck All");

    checkboxes.get(0).click();
    expect.that(button).hasText("Check All");
  }
}
