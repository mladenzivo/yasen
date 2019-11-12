package ru.stqa.yasen;

import com.google.common.collect.ForwardingList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ElementList extends ForwardingList<ElementInList> {

  private final ElementContext context;
  private final By locator;

  private ElementListContext listContext;
  private List<ElementInList> items = new ArrayList<>();
  private boolean loaded;

  ElementList(ElementContext context, By locator) {
    this.context = context;
    this.locator = locator;
    this.listContext = new ElementListContext(this);
  }

  void invalidate() {
    loaded = false;
  }

  void activate() {
    context.activate();
  }

  @Override
  protected List<ElementInList> delegate() {
    if (! loaded) {
      List<WebElement> elements = context.findAllBy(locator);
      int toCopy = Math.min(items.size(), elements.size());
      if (items.size() > toCopy) {
        items.subList(toCopy, items.size()).clear();
      }
      for (int i = 0; i < toCopy; i++) {
        items.get(i).element = elements.get(i);
      }
      if (elements.size() > toCopy) {
        for (int i = toCopy; i < elements.size(); i++) {
          items.add(new ElementInList(listContext, elements.get(i), i));
        }
      }
      loaded = true;
    }
    return items;
  }
}
