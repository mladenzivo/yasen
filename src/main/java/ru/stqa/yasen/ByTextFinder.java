package ru.stqa.yasen;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

class ByTextFinder implements Finder {

  private final String tag;
  private final String text;

  ByTextFinder(String tag, String text) {
    this.tag = tag;
    this.text = text;
  }

  ByTextFinder(String text) {
    this("*", text);
  }

  @Override
  public WebElement findFirstIn(SearchContext searchContext) {
    String locator = String.format(".//%s[contains(normalize-space(.), normalize-space('%s'))]", tag, text);
    return searchContext.findElements(By.xpath(locator))
      .stream()
      .filter(el -> Objects.equals(el.getText(), text))
      .findFirst()
      .orElseThrow(NoSuchElementException::new);
  }

  @Override
  public List<WebElement> findAllIn(SearchContext searchContext) {
    return searchContext.findElements(By.xpath(String.format(".//%s[contains(normalize-space(.), normalize-space('%s'))]", tag, text)))
      .stream()
      .filter(el -> Objects.equals(el.getText(), text))
      .collect(Collectors.toList());
  }

  @Override
  public String toString() {
    return String.format("text: '%s'", text);
  }
}
