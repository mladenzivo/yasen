package ru.stqa.yasen.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.testenv.Page;
import ru.stqa.yasen.testenv.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

class FrameTest extends TestBase {

  @Test
  void canReachElementInAFrame() {
    // arrange
    String target = env.createPage("in frame", "<p id='hello'>Hello, frame!</p>");
    String url = env.createPage("has frames",
      String.format("<p>Hello, world!</p><iframe id='frm' src='%s'/>", target));

    // act
    Element pf = mainWin.open(url).$frame("#frm").$("p");

    // assert
    assertThat(pf.text()).isEqualTo("Hello, frame!");
    assertThat(mainWin.$("p").text()).isEqualTo("Hello, world!");
    assertThat(pf.attribute("id")).isEqualTo("hello");
  }

  @Test
  void canClickInAFrame() {
    // arrange
    Page page = env.allocatePage();
    String inFrame = env.createPage("in frame",
      String.format("<a href='%s' target='_top'>Click me</a>", page.url()));
    String url = env.createPage(page, "has frames",
      String.format("<p>Hello, world!</p><iframe id='frm' src='%s'/>", inFrame));

    // act
    Element link = mainWin.open(url).$frame("#frm").$("a");

    // assert
    link.click();
    link.click();
    link.click();
  }
}
