package ru.stqa.lenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

interface ElementContext {
  WebElement getElementBy(By locator);
}
