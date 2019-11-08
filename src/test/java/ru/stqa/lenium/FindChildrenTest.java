package ru.stqa.lenium;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindChildrenTest extends TestBase {

  @Test
  void canFindAChildAndGetItsText() {
    // arrange
    String url = env.createPage("<p>Not a child</p><div><p>Hello, world!</p></div>");

    // act
    String text = mainWin.open(url).$("div").$("p").text();

    // assert
    assertThat(text).isEqualTo("Hello, world!");
  }

  @Test
  void canFindAChildThatIsNotImmediatelyPresent() {
    // arrange
    String url = env.createPage("source",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + "$(\"div\").append(\"<p>Hello, world!</p>\")"
        + "}, 1000);"
        + "});",
      "<p>Not a child</p><div></div>");

    // act
    String text = mainWin.open(url).$("div").$("p").text();

    // assert
    assertThat(text).isEqualTo("Hello, world!");
  }
}
