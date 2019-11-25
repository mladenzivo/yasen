package ru.stqa.yasen.jquery;

import ru.stqa.yasen.Element;
import ru.stqa.yasen.ElementWrapper;

import java.util.Arrays;
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

  public Select2 select(Option... options) {
    element.executeScript("$(arguments[0]).val(arguments[1]).trigger('change');",
      Arrays.stream(options).map(Option::value).toArray(String[]::new));
    return this;
  }

  public Select2 selectByValue(String... values) {
    element.executeScript("$(arguments[0]).val(arguments[1]).trigger('change');", values);
    return this;
  }

  public Select2 selectByText(String... texts) {
    String script = "var texts = arguments[1], values = [], adapter = $(arguments[0]).data().select2.dataAdapter;"
       + "$(arguments[0]).find('option').each(function() { var item = adapter.item($(this)); if (texts.indexOf(item.text) >= 0) { values.push(item.id) } });"
       + "$(arguments[0]).val(values).trigger('change');";
    element.executeScript(script, texts);
    return this;
  }

  public List<Option> allOptions() {
    return ((List<Map<String, Object>>) element.executeScript("var data = [], adapter = $(arguments[0]).data().select2.dataAdapter; $(arguments[0]).find('option').each(function() { data.push(adapter.item($(this))) }); return data"))
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
