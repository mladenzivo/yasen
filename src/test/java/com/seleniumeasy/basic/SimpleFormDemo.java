package com.seleniumeasy.basic;

import com.seleniumeasy.SeleniumEasyTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stqa.yasen.Element;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleFormDemo extends SeleniumEasyTestBase {

  @BeforeEach
  void openTargetPage() {
    openMenu("Input Forms", "Simple Form Demo");
  }

  @Test
  void singleInputField() {
    Element form = mainWin.$("#get-input");

    form.$("#user-message").sendKeys("test");
    form.$("button").click();

    expect.that(mainWin.$("#display")).hasText("test");
  }

  @Test
  void twoInputFields() {
    Element form = mainWin.$("#gettotal");

    form.$("#sum1").sendKeys("2");
    form.$("#sum2").sendKeys("2");
    form.$("button").click();

    expect.that(mainWin.$("#displayvalue")).hasText("4");
  }

}
