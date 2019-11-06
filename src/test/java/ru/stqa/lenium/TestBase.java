package ru.stqa.lenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

@ExtendWith(FixtureSetup.class)
class TestBase {
  WebDriver driver;
  TestEnvironment env;
  Browser browser;

  @BeforeEach
  void initFixture(Fixture fixture) {
    env = fixture.getTestEnv();
    driver = fixture.getDriver();
    browser = new Browser(driver);

    Window currentWindow = browser.currentWindow();
    browser.windows().stream()
      .filter(w -> ! w.equals(currentWindow))
      .forEach(Window::close);
  }

}
