package com.seleniumeasy.basic;

import com.seleniumeasy.SeleniumEasyTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stqa.yasen.*;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WindowPopupModal extends SeleniumEasyTestBase {

  @BeforeEach
  void openTargetPage() {
    openMenu("Alerts & Modals", "Window Popup Modal");
  }

  @Test
  void singleWindowPopup() {
    mainWin.$t("Follow On Twitter").click();
    Window twitterWin = browser.findNewWindow();

    expect.that(twitterWin)
      .hasTitleContaining("Selenium Easy (@seleniumeasy)")
      .hasUrl("https://twitter.com/intent/follow?screen_name=seleniumeasy");
    twitterWin.close();

    mainWin.$t("Like us On Facebook").click();
    Window facebookWin = browser.findNewWindow();
    expect.that(facebookWin)
      .hasTitleContaining("Selenium Easy")
      .hasUrl("https://web.facebook.com/seleniumeasy?_rdc=1&_rdr");
    facebookWin.close();
  }

  @Test
  void multipleWindowModal() {
    mainWin.$t("Follow Twitter & Facebook").click();
    Set<Window> twoWindows = browser.findNewWindows(2);

    assertThat(browser.windows()).hasSize(3);
    twoWindows.forEach(Window::close);

    mainWin.$t("Follow All").click();
    Set<Window> threeWindows = browser.findNewWindows(3);

    assertThat(browser.windows()).hasSize(4);
    threeWindows.forEach(Window::close);
  }
}
