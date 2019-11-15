package ru.stqa.yasen.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.yasen.testenv.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class NavigationTest extends TestBase {

  @Test
  void canOpenPage() {
    // arrange
    String url = env.createPage("canOpenPage", "<p>Hello, world!</p>");

    // act
    mainWin.open(url);

    // assert
    assertThat(mainWin.title()).isEqualTo("canOpenPage");
  }

}
