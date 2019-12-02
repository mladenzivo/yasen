package ru.stqa.yasen.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.testenv.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

class FrameTest extends TestBase {

  @Test
  void canReachElementInFrame() {
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
}
