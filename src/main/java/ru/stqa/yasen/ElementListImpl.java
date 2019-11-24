package ru.stqa.yasen;

import com.google.common.collect.ForwardingList;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

class ElementListImpl extends ForwardingList<ElementInList> implements ElementList, ElementListContext {

  private final Logger log = LoggerFactory.getLogger(ElementListImpl.class);

  private final ElementContext context;
  private final Finder finder;

  private List<ElementInList> items = new ArrayList<>();
  private boolean loaded;

  ElementListImpl(ElementContext context, Finder finder) {
    this.context = context;
    this.finder = finder;
  }

  public void invalidate() {
    loaded = false;
  }

  public void activate() {
    context.activate();
  }

  @Override
  protected List<ElementInList> delegate() {
    if (! loaded) {
      log.debug("L<E> '{}' is to be found", this);
      List<WebElement> elements = context.findAllBy(finder);
      int toCopy = Math.min(items.size(), elements.size());
      if (items.size() > toCopy) {
        items.subList(toCopy, items.size()).clear();
      }
      for (int i = 0; i < toCopy; i++) {
        items.get(i).element = elements.get(i);
      }
      if (elements.size() > toCopy) {
        for (int i = toCopy; i < elements.size(); i++) {
          items.add(new ElementInList(this, elements.get(i), i));
        }
      }
      loaded = true;
      log.debug("L<E> '{}' has been found", this);
    } else {
      log.debug("L<E> '{}' has already been found in past", this);
    }
    return items;
  }

  @Override
  public String toString() {
    return String.format("%s.$$(%s)", context, finder);
  }

  @Override
  public int size() {
    invalidate();
    return super.size();
  }

  @Override
  public boolean isEmpty() {
    invalidate();
    return super.isEmpty();
  }

  @Override
  public ElementList filter(Predicate<Element> predicate) {
    return new FilteredElementList(this, predicate);
  }

  @Override
  public WebElement getWebElement(int index)  {
    return get(index).getWebElement();
  }

  @Override
  public JavascriptExecutor getJavascriptExecutor() {
    return context.getJavascriptExecutor();
  }
}
