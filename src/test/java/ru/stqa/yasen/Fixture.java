package ru.stqa.yasen;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class Fixture implements ExtensionContext.Store.CloseableResource {

  private Map<Thread, TestEnvironment> envs = new ConcurrentHashMap<>();
  private Map<Thread, Browser> drivers = new ConcurrentHashMap<>();

  TestEnvironment getTestEnv() {
    return envs.computeIfAbsent(Thread.currentThread(), (t) -> new TestEnvironment());
  }

  Browser getBrowser() {
    return drivers.computeIfAbsent(Thread.currentThread(), (t) -> new Browser(new FirefoxDriver()));
  }

  @Override
  public void close() {
    drivers.forEach((k, browser) -> browser.close());
    envs.forEach((k, env) -> env.cleanup());
  }
}
