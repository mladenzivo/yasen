package ru.stqa.yasen;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FindChildrenTest extends TestBase {

  @Test
  void canFindChildrenAndGetTheirText() {
    // arrange
    String url = env.createPage("<p>Not a child</p><ul><li><p>One</p></li><li><p>Two</p></li><li><p>Three</p></li></ul>");

    // act
    ElementList list = mainWin.open(url).$("ul").$$("li p");

    // assert
    assertThat(list).hasSize(3);
  }
}
