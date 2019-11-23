package com.seleniumeasy.basic;

import com.seleniumeasy.SeleniumEasyTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stqa.yasen.Element;
import ru.stqa.yasen.elements.MultiSelect;
import ru.stqa.yasen.elements.SingleSelect;

import static org.assertj.core.api.Assertions.assertThat;

class SelectDropdownList extends SeleniumEasyTestBase {

  @BeforeEach
  void openTargetPage() {
    openMenu("Input Forms", "Select Dropdown List");
  }

  @Test
  void selectListDemo() {
    SingleSelect select = mainWin.$("#select-demo").as(SingleSelect::new);

    select.selectByValue("Friday");
    assertThat(select.selectedOption()).isPresent().get().extracting(Element::text).isEqualTo("Friday");
  }

  @Test
  void multiSelectListDemo() {
    MultiSelect select = mainWin.$("#multi-select").as(MultiSelect::new);

    select.selectByValue("Florida", "New York", "Washington");
    assertThat(select.selectedOptions().stream().map(Element::text).toArray()).containsExactly("Florida", "New York", "Washington");
    assertThat(select.selectedOptions().get(0).text()).isEqualTo("Florida");
  }

}
