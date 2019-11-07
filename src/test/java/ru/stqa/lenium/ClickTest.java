package ru.stqa.lenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClickTest extends TestBase {

  private Window mainWin;

  @BeforeEach
  void checkPreconditions() {
    mainWin = browser.currentWindow();
  }

  @Test
  void canClickElementThatIsNotImmediatelyPresent() {
    // arrange
    String target = env.createPage("target", "<p>Hello, world!</p>");
    String url = env.createPage("source",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + String.format("$(\"p\").append(\"<a href='%s'>click</a>\")", target)
        + "}, 1000);"
        + "});",
      "<p></p>");

    // act
    mainWin.open(url).$("a").click();

    // assert
    assertThat(mainWin.title()).isEqualTo("target");
  }
}
