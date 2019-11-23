package ru.stqa.yasen;

import com.google.common.collect.ForwardingList;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class FilteredElementList extends ForwardingList<ElementInList> implements ElementList {

  private final ElementList elementList;
  private final Predicate<Element> predicate;

  private List<ElementInList> filteredItems;

  public FilteredElementList(ElementList elementList, Predicate<Element> predicate) {
    super();
    this.elementList = elementList;
    this.predicate = predicate;
  }

  @Override
  protected List<ElementInList> delegate() {
    if (filteredItems == null) {
      filteredItems = elementList.stream().filter(predicate).collect(Collectors.toList());
    }
    return filteredItems;
  }

  @Override
  public ElementList filter(Predicate<Element> predicate) {
    return new FilteredElementList(this, predicate);
  }

  @Override
  public void activate() {
    elementList.activate();
  }

  @Override
  public void invalidate() {
    filteredItems = null;
    elementList.invalidate();
  }

}
