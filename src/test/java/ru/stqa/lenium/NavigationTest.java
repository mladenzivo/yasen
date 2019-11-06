package ru.stqa.lenium;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NavigationTest extends TestBase {

  @Test
  void canOpenPage() {
    String url = env.createPage("canOpenPage", "<p>Hello, world!</p>");
    Window win = browser.currentWindow();
    win.open(url);
    assertThat(win.title()).isEqualTo("canOpenPage");
  }

}
