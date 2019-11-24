package ru.stqa.yasen.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.testenv.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

class FindElementTest extends TestBase {

  @Test
  void canFindAnElementByCssSelector() {
    // arrange
    String url = env.createPage("<p>Not a child</p><div><p>Hello, world!</p></div>");

    // act
    String text = mainWin.open(url).$("div p").text();

    // assert
    assertThat(text).isEqualTo("Hello, world!");
  }

  @Test
  void canFindAnElementByVisibleText() {
    // arrange
    String url = env.createPage("<div id='block1'>Text <span style='display:none'>with invisible part</span></div><div id='block2'>Text</div>");

    // act
    Element found = mainWin.open(url).$t("Text");

    // assert
    assertThat(found.attribute("id")).isEqualTo("block1");
  }
}
