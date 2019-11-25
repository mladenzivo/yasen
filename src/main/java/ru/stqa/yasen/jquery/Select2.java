package ru.stqa.yasen.jquery;

import ru.stqa.yasen.Element;
import ru.stqa.yasen.ElementWrapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Select2 extends ElementWrapper {

  public static class Option {
    private final String value;
    private final String text;

    public Option(String value, String text) {
      this.value = value;
      this.text = text;
    }

    public String value() {
      return value;
    }

    public String text() {
      return text;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  public Select2(Element element) {
    super(element);
  }

  public Select2 select(String value) {
    element.executeScript("$(arguments[0]).val(arguments[1]); $(arguments[0]).trigger('change');", value);
    return this;
  }

  public List<Option> allOptions() {
    return ((List<Map<String, Object>>) element.executeScript("var data = [], adapter = $(arguments[0]).data().select2.dataAdapter;; $(arguments[0]).find('option').each(function() { data.push(adapter.item($(this)) );}); return data"))
      .stream()
      .map(props -> new Option((String) props.get("id"), (String) props.get("text")))
      .collect(Collectors.toList());
  }

  public List<Option> selectedOptions() {
    return ((List<Map<String, Object>>) element.executeScript("return $(arguments[0]).select2('data')"))
      .stream()
      .map(props -> new Option((String) props.get("id"), (String) props.get("text")))
      .collect(Collectors.toList());
  }
}
