package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

interface ElementContext extends CanBeActivated {
  WebElement findElementBy(By locator);
  void invalidate();
}
