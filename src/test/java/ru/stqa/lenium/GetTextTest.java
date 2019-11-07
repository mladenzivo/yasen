package ru.stqa.lenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetTextTest extends TestBase {

  private Window mainWin;

  @BeforeEach
  void checkPreconditions() {
    mainWin = browser.currentWindow();
  }

  @Test
  void canGetTextOfAnElement() {
    // arrange
    String url = env.createPage("<p>Hello, world!</p>");

    // act
    String text = mainWin.open(url).$("p").text();

    // assert
    assertThat(text).isEqualTo("Hello, world!");
  }

  @Test
  void canGetTextOfAnElementThatIsNotImmediatelyPresent() {
    // arrange
    String url = env.createPage("source",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + "$(\"div\").append(\"<p>Hello, world!</p>\")"
        + "}, 1000);"
        + "});",
      "<div></div>");

    // act
    String text = mainWin.open(url).$("p").text();

    // assert
    assertThat(text).isEqualTo("Hello, world!");
  }
}
