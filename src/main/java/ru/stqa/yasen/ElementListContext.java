package ru.stqa.yasen;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

interface ElementListContext extends CanBeActivated, CanBeInvalidated {

  WebElement getWebElement(int index);

  JavascriptExecutor getJavascriptExecutor();

}
