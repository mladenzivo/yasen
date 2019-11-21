package ru.stqa.yasen;

import java.util.List;
import java.util.function.Predicate;

public interface ElementList extends List<ElementInList>, CanBeActivated, CanBeInvalidated {

  ElementList filter(Predicate<Element> predicate);

}
