package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Element {

  private Window dom;
  private By locator;

  public Element(Window dom, By locator) {
    this.dom = dom;
    this.locator = locator;
  }

  public void clear() {
    dom.findElement(locator).clear();
  }

  public void click() {
    dom.findElement(locator).click();
  }

  public void sendKeys(String text) {
    dom.findElement(locator).sendKeys(text);
  }

  public void sendKeysSlowly(String text, int delay) {
    WebElement element = dom.findElement(locator);
    Actions actions = new Actions(dom.browser.driver);
    for (char c : text.toCharArray()) {
      actions.sendKeys(element, Character.toString(c)).pause(delay);
    }
    actions.perform();
  }

  public String text() {
    return dom.findElement(locator).getText();
  }
}
