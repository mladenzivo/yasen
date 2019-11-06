package ru.stqa.lenium;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WindowsTest extends TestBase {

  @Test
  void canObtainCurrentWindowTwice() {
    Window win1 = browser.currentWindow();
    Window win2 = browser.currentWindow();
    assertThat(win1).isSameAs(win2);
  }

  @Test
  void canOpenAndCloseWindow() {
    assertThat(browser.windows()).hasSize(1);
    Window mainWin = browser.currentWindow();

    Window newWin = browser.openNewWindow();
    assertThat(browser.windows()).hasSize(2);
    assertThat(newWin).isNotSameAs(mainWin);

    newWin.close();
    assertThat(browser.windows()).hasSize(1);
  }

  @Test
  void canOpenUrlInNewWindow() {
    assertThat(browser.windows()).hasSize(1);
    Window mainWin = browser.currentWindow();

    String url = env.createPage("canOpenUrlInNewWindow", "<p>Hello, world!</p>");
    Window newWin = browser.openInNewWindow(url);
    assertThat(browser.windows()).hasSize(2);
    assertThat(newWin).isNotSameAs(mainWin);

    assertThat(newWin.title()).isEqualTo("canOpenUrlInNewWindow");
    assertThat(mainWin.title()).isNotEqualTo("canOpenUrlInNewWindow");
  }

  @Test
  void canFindNewWindow() {
    assertThat(browser.windows()).hasSize(1);
    Window mainWin = browser.currentWindow();

    String target = env.createPage("target", "<p>Hello, world!</p>");
    String url = env.createPage("source", String.format("<p><a href='%s' target='_blank'>click</a></p>", target));
    mainWin.open(url).$("a").click();

    Window newWin = browser.findNewWindow();
    assertThat(browser.windows()).hasSize(2);
    assertThat(newWin).isNotSameAs(mainWin);

    assertThat(mainWin.title()).isEqualTo("source");
    assertThat(newWin.title()).isEqualTo("target");
  }

  @Test
  void canFindNewWindows() {
    String target = env.createPage("target", "<p>Hello, world!</p>");
    String url = env.createPage("source", String.format("<p><a href='%s' target='_blank'>click</a></p>", target));

    assertThat(browser.windows()).hasSize(1);
    Window mainWin = browser.currentWindow();

    mainWin.open(url).$("a").click();
    mainWin.open(url).$("a").click();

    Set<Window> newWins = browser.findNewWindows();
    assertThat(newWins).hasSize(2);
    assertThat(browser.windows()).hasSize(3);
    newWins.forEach(win -> {
      assertThat(win.title()).isEqualTo("target");
    });
  }

  @Test
  void canWaitForNewWindow() {
    assertThat(browser.windows()).hasSize(1);
    Window mainWin = browser.currentWindow();

    String target = env.createPage("target", "<p>Hello, world!</p>");
    String url = env.createPage("source", String.format(
            "<p><a onclick='setTimeout(function() { window.open(\"%s\") }, 1000)'>click</a></p>",
            target));
    mainWin.open(url).$("a").click();

    Window newWin = browser.findNewWindow();
    assertThat(browser.windows()).hasSize(2);
    assertThat(newWin).isNotSameAs(mainWin);

    assertThat(mainWin.title()).isEqualTo("source");
    assertThat(newWin.title()).isEqualTo("target");
  }

}
