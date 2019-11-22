package com.seleniumeasy.basic;

import com.seleniumeasy.SeleniumEasyTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.stqa.yasen.Element;

class BootstrapModals extends SeleniumEasyTestBase {

  @BeforeEach
  void openTargetPage() {
    openMenu("Alerts & Modals", "Bootstrap Modals");
  }

  @Test
  void singleModalExample() {
    Element activator = mainWin.$("a[href='#myModal0'");
    Element modal = mainWin.$("#myModal0");
    Element cross = modal.$(".modal-header .close");
    Element closeButton = modal.$$(".modal-footer .btn").get(0);
    Element saveButton = modal.$$(".modal-footer .btn").get(1);

    activator.click();
    cross.click();
    expect.that(modal).becomesNotVisible();

    activator.click();
    closeButton.click();
    expect.that(modal).becomesNotVisible();

    activator.click();
    saveButton.click();
    expect.that(modal).becomesNotVisible();
  }

  @Test
  void multipleModalExample() {
    Element activator = mainWin.$("a[href='#myModal'");
    Element modal = mainWin.$("#myModal");
    Element activator2 = mainWin.$("a[href='#myModal2'");
    Element modal2 = mainWin.$("#myModal2");

    Element cross = modal2.$(".modal-header .close");
    Element closeButton = modal2.$$(".modal-footer .btn").get(0);
    Element saveButton = modal2.$$(".modal-footer .btn").get(1);

    activator.click();
    activator2.click();
    cross.click();
    expect.that(modal2).becomesNotVisible();

    activator2.click();
    closeButton.click();
    expect.that(modal2).becomesNotVisible();

    activator2.click();
    saveButton.click();
    expect.that(modal2).becomesNotVisible();
    expect.that(modal).becomesNotVisible();
  }
}
