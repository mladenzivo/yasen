package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

interface ElementContext extends CanBeActivated, CanBeInvalidated {

  WebElement findFirstBy(By locator);
  List<WebElement> findAllBy(By locator);

}
