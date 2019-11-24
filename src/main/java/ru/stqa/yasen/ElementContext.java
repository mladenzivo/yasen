package ru.stqa.yasen;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

interface ElementContext extends CanBeActivated, CanBeInvalidated {

  WebElement findFirstBy(Finder finder);
  List<WebElement> findAllBy(Finder finder);

  JavascriptExecutor getJavascriptExecutor();
}
