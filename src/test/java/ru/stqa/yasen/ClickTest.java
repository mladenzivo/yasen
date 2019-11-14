package ru.stqa.yasen;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ClickTest extends TestBase {

  @Test
  void cannotClickElementThatIsNotPresent() {
    // arrange
    String url = env.createPage("test", "<p>Hello, world!</p>");

    // act & assert
    assertThatExceptionOfType(OperationTimeoutException.class).isThrownBy(() ->
      mainWin.open(url).$("a").click());

    // assert more
    assertThat(mainWin.title()).isEqualTo("test");
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

  @Test
  void canClickElementThatIsNotImmediatelyVisible() {
    // arrange
    String target = env.createPage("target", "<p>Hello, world!</p>");
    String url = env.createPage("source",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + "$(\"a\").show()"
        + "}, 1000);"
        + "});",
      String.format("<p><a style='display:none' href='%s'>click</a></p>", target));

    // act
    mainWin.open(url).$("a").click();

    // assert
    assertThat(mainWin.title()).isEqualTo("target");
  }

  @Test
  void canClickElementThatBecomesStale() {
    // arrange
    String target = env.createPage("target", "<p>Hello, world!</p>");
    String subtarget = env.createPage("subtarget",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + String.format("$(\"p\").append(\"<a href='%s'>click 2</a>\")", target)
        + "}, 1000);"
        + "});",
      "<p></p>");
    String url = env.createPage("source",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + String.format("$(\"p\").append(\"<a href='%s'>click 1</a>\")", subtarget)
        + "}, 1000);"
        + "});",
      "<p></p>");

    // act
    Element link = mainWin.open(url).$("a");
    link.click();
    link.click();

    // assert
    assertThat(mainWin.title()).isEqualTo("target");
  }

  @Test
  void canClickElementInAListThatBecomesStale() {
    // arrange
    String target = env.createPage("target", "<p>Hello, world!</p>");
    String subtarget = env.createPage("subtarget",
      String.format("<ul><li><a href='%s'>link 3</a></li><li><a href='%s'>link 4</a></li></ul>", "nowhere.html", target));
    String url = env.createPage("source",
      String.format("<ul><li><a href='%s'>link 1</a></li><li><a href='%s'>link 2</a></li></ul>", subtarget, target));

    // act
    ElementList listItems = mainWin.open(url).$$("ul li");
    for (Element item : listItems) {
      item.$("a").click();
    }

    // assert
    assertThat(mainWin.title()).isEqualTo("target");
  }
}
