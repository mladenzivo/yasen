package ru.stqa.yasen.tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.Input;
import ru.stqa.yasen.testenv.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

class SendKeysTest extends TestBase {

  @Test
  void canSendKeysToElementThatIsNotImmediatelyPresent() {
    // arrange
    String url = env.createPage("source",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + "$(\"p\").append(\"<form><input type='text' /><form>\")"
        + "}, 1000);"
        + "});",
      "<p></p>");

    // act
    Element input = mainWin.open(url).$("input");
    input.sendKeys("test it");

    // assert
    assertThat(input.as(Input.class).value()).isEqualTo("test it");
  }

  @Test
  void canSendKeysToElementThatIsNotImmediatelyVisible() {
    // arrange
    String url = env.createPage("source",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + "$(\"form\").show()"
        + "}, 1000);"
        + "});",
      "<p><form><input type='text' /><form></p>");

    // act
    Element input = mainWin.open(url).$("input");
    input.sendKeys("test it");

    // assert
    assertThat(input.as(Input.class).value()).isEqualTo("test it");
  }

  @Test
  void canSendKeysToElementThatBecomesStale() throws InterruptedException {
    // arrange
    String target = env.createPage("subtarget",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + "$(\"p\").append(\"<form><input type='text' /><form>\")"
        + "}, 1000);"
        + "});",
      "<p></p>");
    String url = env.createPage("source",
      "$(document).ready(function() { "
        + "window.setTimeout(function() {"
        + String.format("$(\"p\").append(\"<form action='%s'><input type='text' /><form>\")", target)
        + "}, 1000);"
        + "});",
      "<p></p>");

    // act
    Element input = mainWin.open(url).$("input");
    input.sendKeys("test1" + Keys.ENTER);
    // TODO: replace with a proper expectation
    Thread.sleep(1000);
    input.sendKeys("test2");

    // assert
    assertThat(input.as(Input.class).value()).isEqualTo("test2");
  }
}
