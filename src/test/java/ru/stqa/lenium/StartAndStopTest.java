package ru.stqa.lenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StartAndStopTest extends TestEnvironment {

  @Test
  void canInitAndStopBrowser() {
    Browser browser = new Browser(new FirefoxDriver());
    assertThat(browser.capabilities().asMap()).containsEntry("browserName", "firefox");
    assertThat(browser.windows()).hasSize(1);

    browser.quit();
    assertThatExceptionOfType(NoSuchSessionException.class).isThrownBy(browser::windows);
  }
}
