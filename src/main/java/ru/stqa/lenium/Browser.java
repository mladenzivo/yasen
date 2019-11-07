package ru.stqa.lenium;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Browser implements AutoCloseable {

  final WebDriver driver;

  private String currentWindowHandle;
  private final Map<String, Window> windows = new HashMap<>();
  private Stack<String> windowStack = new Stack<>();

  public Browser(WebDriver driver) {
    this.driver = driver;
    updateWindows();
    currentWindowHandle = driver.getWindowHandle();
    windowStack.push(currentWindowHandle);
  }

  private void updateWindows() {
    Set<String> currentWindows = driver.getWindowHandles();
    windows.keySet().retainAll(currentWindows);
    currentWindows.forEach(
      h -> windows.computeIfAbsent(h, h1 -> new Window(this, h1)));
  }

  public Set<Window> windows() {
    updateWindows();
    return new HashSet<>(windows.values());
  }

  public Capabilities capabilities() {
    return ((RemoteWebDriver) driver).getCapabilities();
  }

  public Window currentWindow() {
    return windows.get(currentWindowHandle);
  }

  public Window openNewWindow() {
    driver.switchTo().newWindow(WindowType.TAB);
    updateWindows();
    currentWindowHandle = driver.getWindowHandle();
    windowStack.push(currentWindowHandle);
    return currentWindow();
  }

  public Window openInNewWindow(String url) {
    driver.switchTo().newWindow(WindowType.TAB);
    updateWindows();
    currentWindowHandle = driver.getWindowHandle();
    windowStack.push(currentWindowHandle);
    return currentWindow().open(url);
  }

  void closeWindow(String windowHandle) {
    selectWindow(windowHandle);
    execute(driver::close);
    windowStack.remove(windowHandle);
    selectWindow(windowStack.peek());
  }

  Window selectWindow(String windowHandle) {
    if (! windowHandle.equals(currentWindowHandle)) {
      driver.switchTo().window(windowHandle);
      currentWindowHandle = windowHandle;
    }
    return currentWindow();
  }

  public Window findNewWindow() {
    return findNewWindow(Duration.ofSeconds(10));
  }

  public Window findNewWindow(Duration timeOut) {
    Set<String> knownWindows = new HashSet<>(windows.keySet());
    return new FluentWait<>(driver)
      .withTimeout(timeOut)
      .ignoring(NotFoundException.class)
      .until(d -> {
        updateWindows();
        return selectWindow(windows.keySet().stream()
          .filter(h -> ! knownWindows.contains(h))
          .findFirst()
          .map(win -> windowStack.push(win))
          .orElseThrow(NotFoundException::new));
      }
    );
  }

  public Set<Window> findNewWindows() {
    return findNewWindows(Duration.ofSeconds(10));
  }

  public Set<Window> findNewWindows(Duration timeOut) {
    Set<Window> knownWindows = new HashSet<>(windows.values());
    return new FluentWait<>(driver)
      .withTimeout(timeOut)
      .ignoring(NotFoundException.class)
      .until(d -> {
        updateWindows();
        Set<Window> result = windows.values().stream()
          .filter(h -> !knownWindows.contains(h))
          .collect(Collectors.toSet());
        return result.size() > 0 ? result : null;
      }
    );
  }

  public void quit() {
    driver.quit();
  }

  @Override
  public void close() {
    quit();
  }

  void execute(Runnable command) {
    command.run();
  }

  <R> R execute(Supplier<R> command) {
    return command.get();
  }

  <A> void execute(Consumer<A> command, A arg) {
    command.accept(arg);
  }

  <A, R> R execute(Function<A, R> command, A arg) {
    return command.apply(arg);
  }

}
