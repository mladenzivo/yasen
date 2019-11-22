package com.seleniumeasy.intermediate;

import com.seleniumeasy.SeleniumEasyTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.ElementList;

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

    mainWin.executeScript("$(arguments[0]).val(arguments[1]); $(arguments[0]).trigger('change');", select, "India");
    expect.that(container).hasText("India");
  }

}
