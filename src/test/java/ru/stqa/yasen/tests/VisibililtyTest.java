package ru.stqa.yasen.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.yasen.testenv.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

class VisibililtyTest extends TestBase {

  @Test
  void invisibleElementIsInvisible() {
    // arrange
    String url = env.createPage("test", "<p><a style='display: none'>click me</a></p>");
    mainWin.open(url);

    // act & assert
    assertThat(mainWin.$("p").isVisible()).isTrue();
    assertThat(mainWin.$("a").isVisible()).isFalse();
  }

  @Test
  void canWaitForElementVisibility() {
    // arrange
    String target = env.createPage("target", "<p>Hello, world!</p>");
    String url = env.createPage("source",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + String.format("$(\"p\").append(\"<a href='%s'>click</a>\")", target)
        + "}, 1000);"
        + "});",
      "<p></p>");

    // act & assert
    expect.that(mainWin.open(url).$("a")).becomesVisible();
  }

  @Test
  void canWaitForElementInvisibility() {
    // arrange
    String url = env.createPage("test",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + "$(\"span\").hide()"
        + "}, 1000);"
        + "});",
      "<p><span>Hello, world!</span></p>");

    // act & assert
    expect.that(mainWin.open(url).$("span")).becomesNotVisible();
  }
}
