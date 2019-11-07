package ru.stqa.lenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(FixtureSetup.class)
class TestBase {
  TestEnvironment env;
  Browser browser;

  @BeforeEach
  void initFixture(Fixture fixture) {
    env = fixture.getTestEnv();
    browser = fixture.getBrowser();

    Window currentWindow = browser.currentWindow();
    browser.windows().stream()
      .filter(w -> ! w.equals(currentWindow))
      .forEach(Window::close);
  }

}
