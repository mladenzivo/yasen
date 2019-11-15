package ru.stqa.yasen.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stqa.yasen.Window;
import ru.stqa.yasen.testenv.TestBase;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class WindowsTest extends TestBase {

  @BeforeEach
  void checkPreconditions() {
    assertThat(browser.windows()).hasSize(1);
  }

  @Test
  void canObtainCurrentWindowTwice() {
    // act
    Window win1 = browser.currentWindow();
    Window win2 = browser.currentWindow();

    // assert
    assertThat(win1).isSameAs(win2);
  }

  @Test
  void canOpenAndCloseWindow() {
    // act
    Window newWin = browser.openNewWindow();

    // assert
    assertThat(browser.windows()).hasSize(2);
    assertThat(browser.currentWindow()).isEqualTo(newWin);
    assertThat(newWin).isNotEqualTo(mainWin);

    // act
    newWin.close();

    // assert
    assertThat(browser.windows()).hasSize(1);
    assertThat(browser.currentWindow()).isEqualTo(mainWin);
  }

  @Test
  void canOpenAndCloseWindowInDifferentOrder() {
    // act
    Window newWin = browser.openNewWindow();

    // assert
    assertThat(browser.windows()).hasSize(2);
    assertThat(browser.currentWindow()).isEqualTo(newWin);
    assertThat(newWin).isNotEqualTo(mainWin);

    // act
    mainWin.close();

    // assert
    assertThat(browser.windows()).hasSize(1);
    assertThat(browser.currentWindow()).isEqualTo(newWin);
  }

  @Test
  void canOpenUrlInNewWindow() {
    // arrange
    String url = env.createPage("canOpenUrlInNewWindow", "<p>Hello, world!</p>");

    // act
    Window newWin = browser.openInNewWindow(url);

    // assert
    assertThat(browser.windows()).hasSize(2);
    assertThat(newWin).isNotSameAs(mainWin);
    assertThat(newWin.title()).isEqualTo("canOpenUrlInNewWindow");
    assertThat(mainWin.title()).isNotEqualTo("canOpenUrlInNewWindow");
  }

  @Test
  void canFindNewWindow() {
    // arrange
    String target = env.createPage("target", "<p>Hello, world!</p>");
    String url = env.createPage("source", String.format("<p><a href='%s' target='_blank'>click</a></p>", target));

    // act
    mainWin.open(url).$("a").click();
    Window newWin = browser.findNewWindow();

    // assert
    assertThat(browser.windows()).hasSize(2);
    assertThat(newWin).isNotEqualTo(mainWin);
  }

  @Test
  void canGetTitleOfMultipleWindows() {
    // arrange
    String url1 = env.createPage("page1", "<p>Hello, world!</p>");
    String url2 = env.createPage("page2", "<p>Hello, world!</p>");

    // act
    mainWin.open(url1);
    Window newWin = browser.openInNewWindow(url2);

    // assert
    assertThat(mainWin.title()).isEqualTo("page1");
    assertThat(newWin.title()).isEqualTo("page2");
  }

  @Test
  void canGetTitleOfMultipleWindowsOpenedByClick() {
    // arrange
    String target = env.createPage("target", "<p>Hello, world!</p>");
    String url = env.createPage("source", String.format("<p><a href='%s' target='_blank'>click</a></p>", target));

    // act
    mainWin.open(url).$("a").click();
    Window newWin = browser.findNewWindow();

    // assert
    assertThat(mainWin.title()).isEqualTo("source");
    assertThat(newWin.title()).isEqualTo("target");
  }

  @Test
  void canFindNewWindows() {
    // arrange
    String target = env.createPage("target", "<p>Hello, world!</p>");
    String url = env.createPage("source", String.format("<p><a href='%s' target='_blank'>click</a></p>", target));

    // act
    mainWin.open(url).$("a").click();
    Set<Window> newWins = browser.findNewWindows();

    // assert
    assertThat(newWins).hasSizeGreaterThanOrEqualTo(1);
    newWins.forEach(win -> assertThat(win).isNotEqualTo(mainWin));
  }

  @Test
  void canWaitForNewWindow() {
    // arrange
    String target = env.createPage("target", "<p>Hello, world!</p>");
    String url = env.createPage("source", String.format(
      "<p><a onclick='setTimeout(function() { window.open(\"%s\") }, 1000)'>click</a></p>",
      target));

    // act
    mainWin.open(url).$("a").click();
    Window newWin = browser.findNewWindow();

    // assert
    assertThat(browser.windows()).hasSize(2);
    assertThat(newWin).isNotEqualTo(mainWin);
  }
}
