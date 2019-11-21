package ru.stqa.yasen.elements;

import ru.stqa.yasen.Element;
import ru.stqa.yasen.ElementList;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MultiSelect extends Input {

  public MultiSelect(Element element) {
    super(element);
  }

  public MultiSelect selectByValue(String... values) {
    String selector = Stream.of(values).map(v -> String.format("option[value='%s']", v)).collect(Collectors.joining(", "));
    element.$$(selector).forEach(Element::click);
    return this;
  }

  public ElementList selectedOptions() {
    return element.$$("option").filter(Element::isSelected);
  }
}
