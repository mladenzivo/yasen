package com.seleniumeasy.basic;

import com.seleniumeasy.SeleniumEasyTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stqa.yasen.Element;

import java.time.Duration;

class BootstrapAlerts extends SeleniumEasyTestBase {

  @BeforeEach
  void openTargetPage() {
    openMenu("Alerts & Modals", "Bootstrap Alerts");
  }

  @Test
  void autocloseableMessage() {
    Element alert = mainWin.$(".alert-autocloseable-success");

    mainWin.$("#autoclosable-btn-success").click();
    expect.that(alert)
      .becomesVisible()
      .in(Duration.ofSeconds(6)).becomesNotVisible();
  }

  @Test
  void normalMessages() {
    Element alert = mainWin.$(".alert-normal-success");

    mainWin.$("#normal-btn-success").click();
    alert.$(".close").click();
    expect.that(alert).becomesNotVisible();
  }
}
