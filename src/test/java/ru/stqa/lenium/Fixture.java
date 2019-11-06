package ru.stqa.lenium;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Fixture implements ExtensionContext.Store.CloseableResource {

  private Map<Thread, TestEnvironment> envs = new ConcurrentHashMap<>();
  private Map<Thread, WebDriver> drivers = new ConcurrentHashMap<>();

  TestEnvironment getTestEnv() {
    return envs.computeIfAbsent(Thread.currentThread(), (t) -> new TestEnvironment());
  }

  WebDriver getDriver() {
    return drivers.computeIfAbsent(Thread.currentThread(), (t) -> new FirefoxDriver());
  }

  @Override
  public void close() {
    drivers.forEach((k, driver) -> driver.quit());
    envs.forEach((k, env) -> env.cleanup());
  }
}
