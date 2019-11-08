package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

interface ElementContext extends CanBeActivated {
  WebElement findFirstBy(By locator);
  List<WebElement> findAllBy(By locator);
  void invalidate();
}
