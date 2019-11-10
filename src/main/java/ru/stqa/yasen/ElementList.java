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
  private Object invalidated;

  ElementList(ElementContext context, By locator) {
    this.context = context;
    this.locator = locator;
    this.listContext = new ElementListContext(this);
    System.out.println("Initial invalidated " + invalidated);
  }

  void invalidate() {
    invalidated = null;
    System.out.println("Setting invalidated to null " + invalidated);
  }

  void activate() {
    context.activate();
  }

  @Override
  protected List<ElementInList> delegate() {
    System.out.println("Is invalidated " + invalidated);
    if (invalidated == null) {
      List<WebElement> elements = context.findAllBy(locator);
      int toCopy = Math.min(items.size(), elements.size());
      if (items.size() > toCopy) {
        System.out.println("Deleting extra elements");
        items.subList(toCopy, items.size()).clear();
      }
      for (int i = 0; i < toCopy; i++) {
        items.get(i).element = elements.get(i);
      }
      if (elements.size() > toCopy) {
        System.out.println("Adding extra elements " + elements.size() + " > " + toCopy);
        for (int i = toCopy; i < elements.size(); i++) {
          items.add(new ElementInList(listContext, elements.get(i), i));
        }
      }
      invalidated = "OK";
      System.out.println("List loaded " + invalidated);
    }
    return items;
  }
}
