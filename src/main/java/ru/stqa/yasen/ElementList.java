package ru.stqa.yasen;

import com.google.common.collect.ForwardingList;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Collectors;

public class ElementList extends ForwardingList<Element> {

  private final ElementContext context;
  private final By locator;

  private List<Element> elements;

  ElementList(ElementContext context, By locator) {
    this.context = context;
    this.locator = locator;
  }

  @Override
  protected List<Element> delegate() {
    if (elements == null) {
      elements = context.findAllBy(locator)
        .stream().map(el -> new Element(null, locator))
        .collect(Collectors.toList());
    }
    return elements;
  }
}
