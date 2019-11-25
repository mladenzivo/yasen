package com.seleniumeasy.intermediate;

import com.seleniumeasy.SeleniumEasyTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.ElementList;
import ru.stqa.yasen.jquery.Select2;

import static org.assertj.core.api.Assertions.assertThat;

class JQuerySelectDropdown extends SeleniumEasyTestBase {

  @BeforeEach
  void openTargetPage() {
    openMenu("Input Forms", "JQuery Select dropdown");
  }

  @Test
  void singleSelect() {
    Element activator = mainWin.$("[aria-labelledby='select2-country-container']");
    Element dropdown = mainWin.$(".select2-dropdown");
    Element container = mainWin.$("#select2-country-container");

    activator.click();
    dropdown.$("#select2-country-results").$t("New Zealand").click();
    expect.that(container).hasText("New Zealand");

    activator.click();
    dropdown.$(".select2-search__field").sendKeys("India" + Keys.ENTER);
    expect.that(container).hasText("India");
  }

  @Test
  void multiSelect() {
    Element select =  mainWin.$(".select2-selection--multiple");
    Element activator = select.$(".select2-search--inline");
    Element dropdown = mainWin.$(".select2-dropdown");
    ElementList selected = select.$$("li.select2-selection__choice");

    activator.click();
    activator.$(".select2-search__field").sendKeys("Alaska" + Keys.ENTER);
    expect.that(selected).hasSize(1);

    activator.click();
    dropdown.$(".select2-results__options").$t("Maryland").click();
    expect.that(selected).hasSize(2);

    activator.click();
    activator.$(".select2-search__field").sendKeys("Montana");
    dropdown.$$(".select2-results__option").get(0).click();
    expect.that(selected).hasSize(3);

    while (! selected.isEmpty()) {
      selected.get(0).$(".select2-selection__choice__remove").click();
    }
  }

  @Test
  void singleSelectWithJQuery() {
    Element select = mainWin.$("#country");
    Element container = mainWin.$("#select2-country-container");

    select.executeScript("$(arguments[0]).val(arguments[1]); $(arguments[0]).trigger('change');", "India");
    expect.that(container).hasText("India");
  }

  @Test
  void singleSelectWithSpecialClass() {
    Select2 select2 = mainWin.$("#country").as(Select2::new);
    Element container = mainWin.$("#select2-country-container");

    assertThat(select2.allOptions()).hasSize(11);

    select2.select("India");
    expect.that(container).hasText("India");
    assertThat(select2.selectedOptions())
      .extracting(Select2.Option::value).containsExactly("India");

    select2.select("Japan");
    expect.that(container).hasText("Japan");
    assertThat(select2.selectedOptions())
      .extracting(Select2.Option::text).containsExactly("Japan");
  }
}
