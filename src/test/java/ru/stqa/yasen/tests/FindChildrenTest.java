package ru.stqa.yasen.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.yasen.ElementList;
import ru.stqa.yasen.testenv.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

class FindChildrenTest extends TestBase {

  @Test
  void canFindChildrenAndGetTheirText() {
    // arrange
    String url = env.createPage("<p>Not a child</p><ul><li><p>One</p></li><li><p>Two</p></li><li><p>Three</p></li></ul>");

    // act
    ElementList list = mainWin.open(url).$("ul").$$("li p");

    // assert
    //assertThat(list).hasSize(3);
    assertThat(list.get(0).text()).isEqualTo("One");
    assertThat(list.get(1).text()).isEqualTo("Two");
    assertThat(list.get(2).text()).isEqualTo("Three");
  }
}
