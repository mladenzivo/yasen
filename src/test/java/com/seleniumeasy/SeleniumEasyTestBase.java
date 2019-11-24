package com.seleniumeasy;

import org.junit.jupiter.api.BeforeEach;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.testenv.TestBase;

public class SeleniumEasyTestBase extends TestBase {

  @BeforeEach
  void openHomePage() {
    if (! mainWin.url().startsWith("https://www.seleniumeasy.com/test/")) {
      mainWin.open("https://www.seleniumeasy.com/test/");
    }
  }

  protected void openMenu(String... path) {
    Element menu = mainWin.$("#treemenu");
    for (String sectionTitle: path) {
      menu.$t("a", sectionTitle).click();
    }
  }
}
