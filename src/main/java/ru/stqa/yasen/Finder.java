package ru.stqa.yasen;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public interface Finder {

  WebElement findFirstIn(SearchContext searchContext);
  List<WebElement> findAllIn(SearchContext searchContext);

}
